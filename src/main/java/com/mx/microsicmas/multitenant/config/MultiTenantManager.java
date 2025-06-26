package com.mx.microsicmas.multitenant.config;

import com.mx.microsicmas.multitenant.exception.TenantNotFoundException;
import com.mx.microsicmas.multitenant.exception.TenantResolvingException;
import com.mx.microsicmas.multitenant.utils.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static java.lang.String.format;

@Slf4j
//@Profile("prod")
@Configuration
public class MultiTenantManager {

	@Value("${tenant.driverClassName}")
	private String driverClassName;

	@Value("${tenant.url}")
	private String url;

	@Value("${tenant.username}")
	private String username;

	@Value("${tenant.password}")
	private String password;

	private final ThreadLocal<String> currentTenant = new ThreadLocal<>();
	private final Map<Object, Object> tenantDataSources = new ConcurrentHashMap<>();
	private final DataSourceProperties properties;

	private Function<String, DataSourceProperties> tenantResolver;

	private AbstractRoutingDataSource multiTenantDataSource;

	public MultiTenantManager(DataSourceProperties properties) {
		this.properties = properties;
	}

	@Bean
	public DataSource dataSource() {

		multiTenantDataSource = new AbstractRoutingDataSource() {
			@Override
			protected Object determineCurrentLookupKey() {
				return currentTenant.get();
			}
		};
		multiTenantDataSource.setTargetDataSources(tenantDataSources);
		multiTenantDataSource.setDefaultTargetDataSource(defaultDataSource());
		multiTenantDataSource.afterPropertiesSet();
		return multiTenantDataSource;
	}

	public void setTenantResolver(Function<String, DataSourceProperties> tenantResolver) {
		this.tenantResolver = tenantResolver;
	}

	public void setCurrentTenant(String tenantId) throws SQLException, TenantNotFoundException, TenantResolvingException {
		if (tenantIsAbsent(tenantId)) {
			if (tenantResolver != null) {
				DataSourceProperties properties;
				try {
					properties = tenantResolver.apply(tenantId);
					log.debug("[d] Datasource properties resolved for tenant ID '{}'", tenantId);
				} catch (Exception e) {
					throw new TenantResolvingException(e, "Could not resolve the tenant!");
				}

				String url = properties.getUrl();
				String username = properties.getUsername();
				String password = properties.getPassword();

				addTenant(tenantId, url, username, password);
			} else {
				throw new TenantNotFoundException(format("Tenant %s not found!", tenantId));
			}
		}
		currentTenant.set(tenantId);
		TenantContext.setTenantId(tenantId);
	}

	public void addTenant(String tenantId, String url, String username, String password) throws SQLException {

		DataSource dataSource = DataSourceBuilder.create()
				.driverClassName(properties.getDriverClassName())
				.url(url)
				.username(username)
				.password(password)
				.build();

		// Check that new connection is 'live'. If not - throw exception
		try(Connection c = dataSource.getConnection()) {
			tenantDataSources.put(tenantId, dataSource);
			multiTenantDataSource.afterPropertiesSet();
			log.debug("[d] Tenant '{}' added.", tenantId);
		}
	}

	public DataSource removeTenant(String tenantId) {
		Object removedDataSource = tenantDataSources.remove(tenantId);
		multiTenantDataSource.afterPropertiesSet();
		return (DataSource) removedDataSource;
	}

	public boolean tenantIsAbsent(String tenantId) {
		return !tenantDataSources.containsKey(tenantId);
	}

	public Collection<Object> getTenantList() {
		return tenantDataSources.keySet();
	}

	private DriverManagerDataSource defaultDataSource() {
		log.debug("Load default tenant ");
/*
		DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();
		defaultDataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		defaultDataSource.setUrl("jdbc:oracle:thin:@192.168.1.97:49161:xe");
		defaultDataSource.setUsername("dgc");
		defaultDataSource.setPassword("DGC2050$#Q");
*/
		DriverManagerDataSource defaultDataSource = new DriverManagerDataSource();
		defaultDataSource.setDriverClassName(driverClassName);
		defaultDataSource.setUrl(url);
		defaultDataSource.setUsername(username);
		defaultDataSource.setPassword(password);

		return defaultDataSource;
	}

}

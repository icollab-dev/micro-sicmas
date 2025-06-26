
package com.mx.microsicmas.multitenant.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


@Slf4j
//@Profile("prod")
@Configuration
public class MultiTenantConfiguration {

    private final MultiTenantManager tenantManager;

    @Value("${tenant.datasources.dir}")
    private String dataSources;

    public static String dataSourceStatic;

    @PostConstruct
    public void init() {
        dataSourceStatic = dataSources;
    }
    public MultiTenantConfiguration(MultiTenantManager tenantManager) {
        this.tenantManager = tenantManager;
        this.tenantManager.setTenantResolver(MultiTenantConfiguration::tenantResolver);
    }

    public static DataSourceProperties tenantResolver(String tenantId) {
        //File filesX = new File("/opt/icollab/tenants");
        //System.out.println(dataSourceStatic);
        File filesX = new File(dataSourceStatic);
        //File filesX = new File(dataSources);
    	File[] files = null;
    	if (filesX.isDirectory()) {
    		files = filesX.listFiles();
    	}
        System.out.println("files=" + files);

    	
        if (files == null) {
            String msg = "[!] Tenant property files not found at ./tenants folder!";
            log.error(msg);
            throw new RuntimeException(msg);
        }

       log.info("files=" + files);

        for (File propertyFile : files) {
            Properties tenantProperties = new Properties();
            try {
                tenantProperties.load(new FileInputStream(propertyFile));
            } catch (IOException e) {
                String msg = "[!] Could not read tenant property file at ./tenants folder!";
                log.error(msg);
                throw new RuntimeException(msg, e);
            }

            String id = tenantProperties.getProperty("id");
            if (tenantId.equals(id)) {
                DataSourceProperties properties = new DataSourceProperties();
                properties.setUrl(tenantProperties.getProperty("url"));
                properties.setUsername(tenantProperties.getProperty("username"));
                properties.setPassword(tenantProperties.getProperty("password"));
                return properties;
            }
        }
        String msg = "[!] Any tenant property files not found at ./tenants folder!";
        log.error(msg);
        throw new RuntimeException(msg);
    }


}

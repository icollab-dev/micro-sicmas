package com.mx.microsicmas.multitenant.config;

import com.mx.microsicmas.multitenant.interceptor.MultiTenantInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Slf4j
//@Profile("prod")
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Value("${tenant.path}")
    private String tenantPath;

    public final MultiTenantManager tenantManager;

    WebMvcConfiguration(MultiTenantManager tenantManager){
        this.tenantManager = tenantManager;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ tag");    	
        log.debug(" => REGISTRY INTERCEPTOR TENANT WITH PATH {}",  tenantPath);
        log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ tag");        
        registry.addInterceptor(new MultiTenantInterceptor(tenantManager)).addPathPatterns(tenantPath);
    }
}
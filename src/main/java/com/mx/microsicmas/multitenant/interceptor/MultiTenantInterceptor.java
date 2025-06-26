package com.mx.microsicmas.multitenant.interceptor;

import com.mx.microsicmas.multitenant.config.MultiTenantManager;
import com.mx.microsicmas.multitenant.exception.InvalidDbPropertiesException;
import com.mx.microsicmas.multitenant.exception.InvalidTenantIdExeption;
import com.mx.microsicmas.multitenant.exception.TenantNotFoundException;
import com.mx.microsicmas.multitenant.exception.TenantResolvingException;
import com.mx.microsicmas.multitenant.utils.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.Enumeration;

@Slf4j
public class MultiTenantInterceptor extends HandlerInterceptorAdapter {

    private static final String TENANT_HEADER_NAME = "X-TENANT-ID";

    public final MultiTenantManager tenantManager;

    public MultiTenantInterceptor(MultiTenantManager tenantManager){
        this.tenantManager = tenantManager;
    }

    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws SQLException, TenantNotFoundException, TenantResolvingException {
        System.out.println("====================================");
    	
        //String tenantId = request.getHeader(TENANT_HEADER_NAME);
    	String tenantId = "aguila";
    	//Enumeration headerNames = request.getHeaderNames();
    	Enumeration headerNames = request.getParameterNames();
    	System.out.println("headerNames: " + headerNames.hasMoreElements());
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            System.out.println("key=" + key);
            String value = request.getParameter(key);
            if (key.equals(TENANT_HEADER_NAME)) {
              tenantId = value;
                System.out.println(">>>>>>>>> X-TENANT-ID=" + tenantId);
            }
        }

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>> X-TENANT-ID=" + tenantId);
        if ( tenantId != null) {
            TenantContext.setTenantId(tenantId);
            try {
                setTenant(tenantId);
            } catch (SQLException e) {
                log.error("Invalid DB Properties to {} - Error: {}", tenantId, e.getMessage());
                throw new InvalidDbPropertiesException();
            } catch (TenantNotFoundException e) {
                log.error("Invalid tenant ID {} - Error: {}", tenantId, e.getMessage());
                throw new InvalidTenantIdExeption();
            } catch (TenantResolvingException e) {
                log.error("Error to resolve tenant {} - Error: {}", tenantId, e.getMessage());
                throw new InvalidTenantIdExeption();
            }

        }else{
            log.error(" HEDAER NOT FOUND IN REQUEST " );
            throw new InvalidTenantIdExeption();
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        TenantContext.clear();
    }

    private void setTenant(String tenantId) throws SQLException, TenantNotFoundException, TenantResolvingException{
        tenantManager.setCurrentTenant(tenantId);
    }
}
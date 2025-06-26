package com.mx.microsicmas.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class HeadersRequestInterceptor implements RequestInterceptor {
    private static final String TENANT_HEADER_NAME = "X-TENANT-ID";
    private static final String USER_HEADER_NAME = "user";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        if (request == null) {
            return;
        }

        String tenant = request.getParameter(TENANT_HEADER_NAME);
        if (tenant == null) {
            tenant = request.getHeader(TENANT_HEADER_NAME);
        }
        //System.out.println("filter feing mmercado: tenant: " + tenant);
        requestTemplate.query(TENANT_HEADER_NAME, tenant);

        String user = request.getParameter(USER_HEADER_NAME);
        if (user == null) {
            user = request.getHeader(USER_HEADER_NAME);
        }
        //System.out.println("filter feing mmercado: user: " + user);
        requestTemplate.query(USER_HEADER_NAME, user);
    }
}
package de.codeboje.springbootbook.logging;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class RequestContextLoggingFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestContextLoggingFilter.class);
    
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String requestUUID = ((HttpServletRequest) request).getHeader(SBBLoggingConstants.REQUEST_UUID_HEADER);
            if (StringUtils.isEmpty(requestUUID)) {
                requestUUID = createId();
                LOGGER.info("Got request without {} and assign new {}", SBBLoggingConstants.REQUEST_UUID_HEADER, requestUUID);
                
                MDC.put(SBBLoggingConstants.REQUEST_UUID_HEADER, requestUUID);
            }
            else {
                MDC.put(SBBLoggingConstants.REQUEST_UUID_HEADER, requestUUID);
            }

            chain.doFilter(request, response);
        }
        finally {
            MDC.clear();
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    public static String createId() {
        UUID uuid = java.util.UUID.randomUUID();
        return uuid.toString().replace("-", ""); 
    }
}

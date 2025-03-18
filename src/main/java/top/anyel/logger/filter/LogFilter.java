package top.anyel.logger.filter;

/*
 * Author: Anyel EC
 * Github: https://github.com/Anyel-ec
 * Creation date: 18/03/2025
 */

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Enumeration;

@Component
public class LogFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No need for initialization
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        Enumeration<String> paramNames = httpRequest.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            if (paramName.startsWith("app.")) {
                logger.warn("Intento de acceso a propiedad bloqueada: {}", paramName);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // No need for cleanup
    }
}

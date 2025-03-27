package top.anyel.logger.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;

/*
 * Author: Anyel EC
 * Github: https://github.com/Anyel-ec
 * Creation date: 18/03/2025
 */

@Slf4j
@Component
public class LogFilter implements Filter {

    /*
     * This is a servlet filter that intercepts incoming HTTP requests.
     *
     * It checks if any request parameter name starts with "app." and logs a warning
     * indicating that an attempt to access a sensitive property was blocked.
     *
     * Importance for hiding secrets: MEDIUM.
     * While it does not modify property values, it adds an extra layer of security
     * by monitoring and logging suspicious attempts to access sensitive data.
     */
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
                log.warn("Intento de acceso a propiedad bloqueada: {}", paramName);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // No need for cleanup
    }
}

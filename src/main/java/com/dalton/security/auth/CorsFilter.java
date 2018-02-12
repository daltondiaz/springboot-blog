package com.dalton.security.auth;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dalton on 11/02/18.
 */
@Configuration
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response =  (HttpServletResponse) resp;

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        response.addHeader("Access-Control-Max-Age", "1728000");
        response.addHeader("Access-Control-Expose-Headers","Authorization");

        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig filterConfig)throws ServletException{}
}
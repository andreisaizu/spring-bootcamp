package com.deloittedigital.library.config;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class SimpleFilter implements Filter {

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println(
                "Starting a transaction for req : " +
                req.getRequestURI());

        filterchain.doFilter(request, response);
        System.out.println(
                "Committing a transaction for req " +
                req.getRequestURI());
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {}

}

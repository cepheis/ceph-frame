package com.cepheis.spring.aop;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CrossAccessFilter extends OncePerRequestFilter {

    public void crossAllows(HttpServletRequest request, HttpServletResponse response) {
        String o = request.getHeader("Origin");
        /* 允许跨域 */
        response.setHeader("Access-Control-Allow-Origin", o);
        /* 允许带cookie */
        response.setHeader("Access-Control-Allow-Credentials", "true");
        /* 允许请求的方式 */
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        /* 支持带头 */
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        /* 预检有效期 */
        //response.addHeader("Access-Control-Max-Age", "172800");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        crossAllows(request, response);
        chain.doFilter(request, response);
    }
}

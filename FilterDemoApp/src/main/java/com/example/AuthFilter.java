package com.example;

import java.io.*;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code if needed
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        boolean loggedIn = (session != null && session.getAttribute("username") != null);
        String requestURI = req.getRequestURI();

        // Allow login page without session
        if (loggedIn || requestURI.endsWith("login.html") || requestURI.endsWith("login")) {
            chain.doFilter(request, response); // Allow request to proceed
        } else {
            res.sendRedirect("login.html"); // Redirect to login if not logged in
        }
    }

    public void destroy() {
        // Cleanup code if needed
    }
}

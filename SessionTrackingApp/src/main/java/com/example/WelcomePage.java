package com.example;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class WelcomePage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get session
        HttpSession session = request.getSession(false);
        String name = null;
        if (session != null) {
            name = (String) session.getAttribute("username");
        }

        // Get cookie
        String cookieName = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("username")) {
                    cookieName = c.getValue();
                }
            }
        }

        // Display results
        if (name != null) {
            out.println("<h2>Welcome, " + name + "!</h2>");
            out.println("<p>Session active and Cookie value: " + cookieName + "</p>");
        } else {
            out.println("<h2>Session expired. Please login again.</h2>");
            out.println("<a href='index.html'>Login</a>");
        }
    }
}

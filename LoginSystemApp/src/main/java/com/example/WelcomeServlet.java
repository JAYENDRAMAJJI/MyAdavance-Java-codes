package com.example;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class WelcomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            String name = (String) session.getAttribute("username");
            out.println("<h2>Welcome, " + name + "!</h2>");
            out.println("<a href='logout'>Logout</a>");
        } else {
            out.println("<h3>Session expired. Please login again.</h3>");
            out.println("<a href='login.html'>Login</a>");
        }
    }
}
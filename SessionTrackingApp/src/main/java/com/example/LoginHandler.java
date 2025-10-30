package com.example;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginHandler extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get username from form
        String name = request.getParameter("username");

        // Create session and set attribute
        HttpSession session = request.getSession();
        session.setAttribute("username", name);

        // Create a cookie
        Cookie cookie = new Cookie("username", name);
        cookie.setMaxAge(300); // 5 minutes
        response.addCookie(cookie);

        out.println("<h3>Hello, " + name + "!</h3>");
        out.println("<p>Session created successfully!</p>");
        out.println("<a href='welcome'>Go to Welcome Page</a>");
    }
}

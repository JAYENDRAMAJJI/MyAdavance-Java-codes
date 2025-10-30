package com.example;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        // Simple login validation
        if (user.equals("admin") && pass.equals("12345")) {
            HttpSession session = request.getSession();
            session.setAttribute("username", user);
            response.sendRedirect("welcome.html");
        } else {
            out.println("<h3>Invalid Credentials!</h3>");
            out.println("<a href='login.html'>Try Again</a>");
        }
    }
}

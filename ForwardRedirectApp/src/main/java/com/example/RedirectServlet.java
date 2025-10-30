package com.example;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RedirectServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String value = request.getParameter("value");
        out.println("<h3>Request Redirected Successfully!</h3>");
        out.println("<p>The number is " + value + " and is greater than or equal to 50.</p>");
    }
}

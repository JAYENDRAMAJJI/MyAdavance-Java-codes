package com.example;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ErrorDemoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");

        if ("404".equals(type)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found!");
        } else if ("500".equals(type)) {
            throw new ServletException("Simulated Internal Server Error!");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request!");
        }
    }
}

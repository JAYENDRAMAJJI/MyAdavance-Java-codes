package com.example;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class InputServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int num = Integer.parseInt(request.getParameter("number"));

        // If number < 50 → forward
        // If number >= 50 → redirect
        if (num < 50) {
            RequestDispatcher rd = request.getRequestDispatcher("forward");
            rd.forward(request, response);
        } else {
            response.sendRedirect("redirect?value=" + num);
        }
    }
}

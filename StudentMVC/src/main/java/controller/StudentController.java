package controller;

import java.io.IOException;
import model.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student")
public class StudentController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        Student student = new Student("Jayendra", 21);

        request.setAttribute("student", student);

        request.getRequestDispatcher("/studentView.html").forward(request, response);
    }
}

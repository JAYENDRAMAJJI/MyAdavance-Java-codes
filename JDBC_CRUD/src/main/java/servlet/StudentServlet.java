package servlet;

import java.io.*;
import dao.StudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Student;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action.equals("insert")) {
            Student s = new Student();
            s.setName(request.getParameter("name"));
            s.setEmail(request.getParameter("email"));
            s.setCity(request.getParameter("city"));
            StudentDAO.insertStudent(s);
            response.sendRedirect("index.html");
        }

        if (action.equals("update")) {
            Student s = new Student();
            s.setId(Integer.parseInt(request.getParameter("id")));
            s.setName(request.getParameter("name"));
            s.setEmail(request.getParameter("email"));
            s.setCity(request.getParameter("city"));
            StudentDAO.updateStudent(s);
            response.sendRedirect("index.html");
        }

        if (action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            StudentDAO.deleteStudent(id);
            response.sendRedirect("index.html");
        }
    }
}

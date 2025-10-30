package mypackage.sessions;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/sessionDemo")
public class SessionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // --- HttpSession ---
        HttpSession session = request.getSession();
        Integer visitCount = (Integer) session.getAttribute("visitCount");

        if (visitCount == null) {
            visitCount = 1;
        } else {
            visitCount++;
        }
        session.setAttribute("visitCount", visitCount);

        // --- Cookie ---
        Cookie[] cookies = request.getCookies();
        int cookieCount = 0;

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("userVisits")) {
                    cookieCount = Integer.parseInt(c.getValue());
                }
            }
        }
        cookieCount++;
        Cookie visitCookie = new Cookie("userVisits", String.valueOf(cookieCount));
        visitCookie.setMaxAge(60 * 60 * 24); // 1 day
        response.addCookie(visitCookie);

        // --- Response Page ---
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Session Tracking</title></head><body>");
        out.println("<h2>Session Tracking Example</h2>");

        out.println("<p><b>Using HttpSession:</b> You have visited this page " + visitCount + " times.</p>");
        out.println("<p><b>Using Cookies:</b> You have visited this page " + cookieCount + " times.</p>");

        out.println("<br><a href='sessionDemo'>Reload Page</a>");
        out.println("</body></html>");
    }
}

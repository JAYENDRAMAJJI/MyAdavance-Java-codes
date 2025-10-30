<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>My First JSP Page</title>
</head>
<body>
    <h2>Welcome to JSP Demo Application!</h2>
    <%
        // This is a JSP Scriptlet
        String name = "Jayendra";
        out.println("<h3>Hello, " + name + "!</h3>");
    %>

    <p>Current Date & Time: <%= new java.util.Date() %></p>
</body>
</html>

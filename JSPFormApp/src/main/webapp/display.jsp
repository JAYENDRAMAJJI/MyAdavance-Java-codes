<%@ page import="com.bean.UserBean" %>
<jsp:useBean id="user" class="com.bean.UserBean" scope="request"/>
<jsp:setProperty property="*" name="user"/>

<!DOCTYPE html>
<html>
<head>
    <title>Display Page</title>
</head>
<body>
    <h2>User Details</h2>
    <p>Name: <jsp:getProperty property="name" name="user"/></p>
    <p>Email: <jsp:getProperty property="email" name="user"/></p>
</body>
</html>

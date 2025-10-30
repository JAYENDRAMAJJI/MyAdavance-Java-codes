package com.tags;

import java.io.*;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

public class HelloTag extends SimpleTagSupport {

    private String user; // attribute

    // Setter for the attribute
    public void setUser(String user) {
        this.user = user;
    }

    // Custom tag logic
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.println("<h3>Hello, " + user + "! Welcome to Custom Tags in JSP.</h3>");
    }
}

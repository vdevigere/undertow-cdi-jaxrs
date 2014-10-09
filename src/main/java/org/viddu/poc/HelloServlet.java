package org.viddu.poc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloServlet implements Servlet {

    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub

    }

    public ServletConfig getServletConfig() {
        // TODO Auto-generated method stub
        return null;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        PrintWriter writer = res.getWriter();
        writer.write("Hello Viddu");
        writer.flush();
    }

    public String getServletInfo() {
        // TODO Auto-generated method stub
        return null;
    }

    public void destroy() {
        // TODO Auto-generated method stub

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supmessaging.servlets;

import com.supmessaging.tools.CheckInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Martin
 */
public class Connection extends HttpServlet {
    
    public static final String jspView = "/WEB-INF/Connection.jsp";
    public static final String usernameField = "username";
    public static final String usernamePassword = "password";
    
    CheckInput checkInput = new CheckInput();
    
    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( jspView ).forward( request, response );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        String username = request.getParameter(usernameField);
        String password = request.getParameter(usernamePassword);
        
        errors.clear();
        request.setAttribute(usernameField, username);
        
        if(!"".equals(checkInput.validateUsername(username))) {
            errors.put( usernameField, checkInput.validateUsername(username) );
            request.removeAttribute(usernameField);
        }
        
        if(!"".equals(checkInput.validateUsername(password))) {
            errors.put( usernamePassword, checkInput.validatePassword(password) );
        }
        
        if(!errors.isEmpty()) {
            request.setAttribute("error", errors);
            this.getServletContext().getRequestDispatcher(jspView ).forward( request, response );
        }
        else {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("password", password);

            response.sendRedirect("dashboard");
        }
    }
}
package com.supmessaging.servlets;

import com.supmessaging.tools.ActionToolBar;
import com.supmessaging.tools.CheckInput;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Connection extends HttpServlet {
    
    public static final String jspView = "/WEB-INF/Connection.jsp";
    public static final String usernameField = "username";
    public static final String passwordField = "password";
    
    CheckInput checkInput = new CheckInput();
    
    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
        ActionToolBar myBeautifulToolbar = new ActionToolBar();
        
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);
        
        if(!sessionCreator.checkSessionExist()) {
            this.getServletContext().getRequestDispatcher( jspView ).forward( request, response );
        }
        else {
            response.sendRedirect("home/dashboard");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        String username = request.getParameter(usernameField);
        String password = request.getParameter(passwordField);
        SessionCreator sessionCreator = new SessionCreator(request);
        
        errors.clear();
        request.setAttribute(usernameField, username);
        
        if(!"".equals(checkInput.validateUsername(username))) {
            errors.put( usernameField, checkInput.validateUsername(username) );
            request.removeAttribute(usernameField);
        }
        
        if(!"".equals(checkInput.validatePassword(password))) {
            errors.put(passwordField, checkInput.validatePassword(password) );
        }
        
        if(!errors.isEmpty()) {
            request.setAttribute("error", errors);
            this.getServletContext().getRequestDispatcher(jspView).forward( request, response );
        }
        else {
            sessionCreator.createSession(username, 1);            
            response.sendRedirect("home/dashboard");
        }
    }
}
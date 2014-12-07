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
        CheckInput checkInput = new CheckInput(request, errors);
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        SessionCreator sessionCreator = new SessionCreator(request);
        
        request.setAttribute("username", username);
        
        checkInput.validateUsername(username, "username");
        checkInput.validatePassword(password, "password");
        
        if(!errors.isEmpty()) {
            request.setAttribute("error", errors);
            this.getServletContext().getRequestDispatcher(jspView).forward( request, response );
            errors.clear();
        }
        else {
            sessionCreator.createSession(username, 1);            
            response.sendRedirect("home/dashboard");
        }
    }
}
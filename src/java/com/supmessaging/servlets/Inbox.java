package com.supmessaging.servlets;

import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Inbox extends HttpServlet {
    
    public static final String jspView = "/WEB-INF/inbox.jsp";
    
    @Override
     public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {      
        SessionCreator sessionCreator = new SessionCreator(request);
        ActionToolbar myBeautifulToolbar = new ActionToolbar();
        
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);
        this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
     }
}

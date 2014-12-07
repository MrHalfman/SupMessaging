package com.supmessaging.servlets;

import com.supmessaging.tools.ActionToolBar;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Home", urlPatterns = {"/Home"})
public class Home extends HttpServlet {
    
    public static final String jspView = "/WEB-INF/home.jsp";
    
    @Override
     public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {      
        SessionCreator sessionCreator = new SessionCreator(request);
        ActionToolBar myBeautifulToolbar = new ActionToolBar();
        
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);
        this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
     }
}

package com.supmessaging.servlets;

import com.supmessaging.tools.CheckForm;
import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.ComplexRequest;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Contact extends HttpServlet {
    public static final String jspView = "/WEB-INF/contact.jsp";
    
    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
        ActionToolbar myBeautifulToolbar = new ActionToolbar();
        
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

        ComplexRequest complexRequest = new ComplexRequest();
        SessionCreator sessionCreator = new SessionCreator(request);
        ActionToolbar myBeautifulToolbar = new ActionToolbar();
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);
        Map<String, String> errors = new HashMap<>();
        CheckForm checkInput = new CheckForm(request, errors);
        
        String emailData = request.getParameter("email"); 
        String messageData = request.getParameter("message");
        
        request.setAttribute("email", emailData);
        request.setAttribute("message", messageData);
        
        checkInput.validateMail(emailData, "email", true);
        checkInput.nonEmpty(messageData, "message", true);
        
        if(!errors.isEmpty()) {
            request.setAttribute("error", errors);
            this.getServletContext().getRequestDispatcher(jspView).forward( request, response );
            errors.clear();
        }
        else {
            int idAnonyme = complexRequest.getSpecificUser(0);
            int idAdmin = complexRequest.getSpecificUser(2);
            
            complexRequest.sendAnonymMessage(messageData, idAnonyme, idAdmin, emailData, 0);
            
            response.sendRedirect("/SupMessaging"); 
        }
    }   
}

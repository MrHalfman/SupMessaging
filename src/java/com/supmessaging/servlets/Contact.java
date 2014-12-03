package com.supmessaging.servlets;

import com.supmessaging.tools.CheckInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Contact extends HttpServlet {
    public static final String jspView = "/WEB-INF/Contact.jsp";
    public static final String mailField = "email";
    public static final String textarea = "message";
    
    CheckInput checkInput = new CheckInput();
    
    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( jspView ).forward( request, response );
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String> errors = new HashMap<>();
        String emailData = request.getParameter(mailField); 
        String messageData = request.getParameter(textarea);
               
        errors.clear();
        request.setAttribute("email", emailData);
        request.setAttribute("message", messageData);
        
        if(!"".equals(checkInput.validateMail(emailData, true))) {
            errors.put( mailField, checkInput.validateMail(emailData, true) );
            request.removeAttribute("email");
        }
        
        if(!"".equals(checkInput.nonEmpty(messageData, true))) {
            errors.put( textarea, checkInput.nonEmpty(messageData, true) );
            request.removeAttribute("message");
        }
        
        if(!errors.isEmpty()) {
            request.setAttribute("error", errors);
            this.getServletContext().getRequestDispatcher(jspView).forward( request, response );
        }
        else {
            response.sendRedirect("/SupMessaging"); 
        }
    }   
}

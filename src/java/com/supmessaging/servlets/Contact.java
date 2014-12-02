/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supmessaging.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author adrienxp3
 */
public class Contact extends HttpServlet {
    public static final String jspView = "/WEB-INF/Contact.jsp";
    public static final String mail = "email";
    public static final String message = "message";
    
    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( jspView ).forward( request, response );
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //on récupere les parametre de POST du JSP
        String emailFinal = req.getParameter(mail); 
        String messageFinal = req.getParameter(message);
        
        //on affiche dans les log
        System.out.println(emailFinal); 
        System.out.println(messageFinal);
        
        //ça reaffiche mon formulaire après le POST
        this.getServletContext().getRequestDispatcher( jspView ).forward( req, resp ); 
    }   
}

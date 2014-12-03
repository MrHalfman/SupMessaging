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
import com.supmessaging.tools.SessionCreator;
/**
 *
 * @author adrienxp3
 */
public class Toolbar extends HttpServlet {
    public static final String jspView = "/WEB-INF/Toolbar.jsp";
    
    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( jspView ).forward( request, response );        
    }
    
    
    
}

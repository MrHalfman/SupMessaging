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
 * @author Martin
 */
public class Dashboard extends HttpServlet {

    public static final String jspView = "/WEB-INF/Dashboard.jsp";
    
    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
            this.getServletContext().getRequestDispatcher( jspView ).forward( request, response );
    }

    
}

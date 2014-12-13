package com.supmessaging.servlets;

import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.ComplexRequest;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Inbox extends HttpServlet {
    
    public static final String jspView = "/WEB-INF/inbox.jsp";
    
    @Override
     public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {      
        SessionCreator sessionCreator = new SessionCreator(request);
        ActionToolbar myBeautifulToolbar = new ActionToolbar();
        
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);      
        this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
     }
     
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        // ENVOI MESSAGE
        ComplexRequest inboxRequest = new ComplexRequest (); //ok
        
        String username = request.getParameter("username"); //probleme j'arrive pas a récuperer le username de la sessio en cours    
        int idUser = inboxRequest.getIdOfUser(username); //ok si username marche
        
        String receiver = "le username du destinataire";    // à faire
        int idReceiver = inboxRequest.getIdOfUser(receiver); 
        
        String corpus = request.getParameter("corpus"); // a faire
        
        
        inboxRequest.newMessage(corpus, idUser, idReceiver); // testé et approuvé
        
    }
}

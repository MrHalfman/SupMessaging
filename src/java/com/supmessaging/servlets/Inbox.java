package com.supmessaging.servlets;

import com.supmessaging.persistence.Messages;
import com.supmessaging.persistence.Users;
import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.ComplexRequest;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.util.List;
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
        
        ComplexRequest inboxRequest = new ComplexRequest ();
        List<Messages> sendMessages = inboxRequest.getMessagesSend(1, 0);
        for (Messages mess : sendMessages){
            String corpus = mess.getCorpus();
            System.out.println(corpus);
        }
        
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);      
        this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
     }
     
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // ENVOI MESSAGE
        ComplexRequest inboxRequest = new ComplexRequest (); //ok
        
        
        
        int idUser = inboxRequest.getIdOfUser((String) session.getAttribute("username")); //ok 
        
        String receiver = "le username du destinataire";    // à faire on pourrait recupere le nom 
        int idReceiver = inboxRequest.getIdOfUser(receiver); // du contact/destinataire du jsp et la mettre en variable ici
        
        String corpus = request.getParameter("corpus"); // a faire pareil
        
        
        inboxRequest.newMessage(corpus, idUser, idReceiver); // testé et approuvé la fonction marche faut juste de bon parametres
        
    }
}

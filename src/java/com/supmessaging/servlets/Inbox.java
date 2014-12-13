package com.supmessaging.servlets;

import com.supmessaging.persistence.Messages;
import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.ComplexRequest;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

public class Inbox extends HttpServlet {
    
    public static final String jspView = "/WEB-INF/inbox.jsp";
    
    @Override
     public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {      
        SessionCreator sessionCreator = new SessionCreator(request);
        ActionToolbar myBeautifulToolbar = new ActionToolbar();
        HttpSession session = request.getSession();
        
        try {
            //HttpSession session = request.getSession();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            Random rand = new Random();
            
            String UScsrf = rand.toString() + session.getAttribute("username"); // TODO add username
            
            byte[] digest = md5.digest(UScsrf.getBytes("UTF-8"));
            StringBuilder csrf = new StringBuilder();
            for (byte b : digest) {
                csrf.append(String.format("%02x", b & 0xff));
            }
            request.setAttribute("csrf", csrf);
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Inbox.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (sessionCreator.checkSessionExist()) {
            ComplexRequest inboxRequest = new ComplexRequest ();
            List<Messages> sendMessages = inboxRequest.getMessages(1, 0);
            for (Messages mess : sendMessages){
                String corpus = mess.getCorpus();
                System.out.println(corpus);
            }

            myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);      
            this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
        } else {
            response.sendRedirect("/SupMessaging");
        }
     }
     
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // ENVOI MESSAGE
        ComplexRequest inboxRequest = new ComplexRequest (); //ok
        
        int idUser = Integer.parseInt((String) session.getAttribute("userid")); //ok 
        
        String receiver = request.getParameter("receiver");    // à faire on pourrait recupere le nom 
        int idReceiver = inboxRequest.getIdOfUser(receiver); // du contact/destinataire du jsp et la mettre en variable ici
        
        String corpus = request.getParameter("message"); // a faire pareil
        
        inboxRequest.newMessage(corpus, idUser, idReceiver); // testé et approuvé la fonction marche faut juste de bon parametres
        
    }
}

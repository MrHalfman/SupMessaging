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
        
        
        
        
        //  LISTE CONTACT
        String username = (String) session.getAttribute("username"); // on recupere le curren username
        List<Integer> getIdOfContactConversation; // on crée une liste de int pour recuperer les id
        Users toto = new Users(); // on créé un user car on en a besoin pour savoir a qui correspond l'id
        
        
        ComplexRequest quest = new ComplexRequest(); //nouvelle requete
        getIdOfContactConversation = quest.getCommunicateContact(username); //on récupere la liste des id avec qui il existe une conversation
        
        for (Integer user : getIdOfContactConversation){        //on boucle dans la liste d'id       
               toto = quest.getUserById(user.intValue());       //on determine le user ici toto grace à l'id récuperé
               System.out.println (toto.getPseudo());           //on affiche le pseudo
               
               // si tu veux exploiter je vois pas comment ^^désolé j'ai pensé à ça mais a appronfondir
               //request.setAttribute("contact", toto.getPseudo()); 
            }

       
                
        
        
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
            List<Messages> messagesList = inboxRequest.getMessages(1, 0);
            request.setAttribute("messagesList", messagesList);

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
        ComplexRequest inboxRequest = new ComplexRequest ();      
        int idUser = Integer.parseInt((String) session.getAttribute("userid"));
        
        String receiver = request.getParameter("receiver");    // à faire on pourrait recupere le pseudo du contact 
        int idReceiver = inboxRequest.getIdOfUser(receiver); // du contact/destinataire du jsp et la mettre en variable ici
        
        String corpus = request.getParameter("message"); // à recuperer du jsp avec une value
        
        inboxRequest.newMessage(corpus, idUser, idReceiver); // testé et approuvé la fonction marche faut juste de bon parametres
        
    }
}

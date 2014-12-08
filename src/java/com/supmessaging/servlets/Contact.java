package com.supmessaging.servlets;

import com.supmessaging.persistence.HibernateUtil;
import com.supmessaging.tools.CheckInput;
import com.supmessaging.persistence.Messages;
import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Contact extends HttpServlet {
    public static final String jspView = "/WEB-INF/contact.jsp";
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory(); // Transmission avec la BDD
    public int idAnonyme = 0;
    public int idAdmin = 1;
    public int notRead = 0;
    public int read = 1;
    
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

        Map<String, String> errors = new HashMap<>();
        CheckInput checkInput = new CheckInput(request, errors);
        
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
            //on formate la date et l'heure pour l'envoyer en BDD
            String currentDate = dateFormat.format(date);
            Session sessionHibernate = sessionFactory.openSession();

            Messages contactAdmin = new Messages();

            contactAdmin.setDateMessage(currentDate);
            contactAdmin.setCorpus(messageData);
            contactAdmin.setIdUserAuthor(idAnonyme);    // 0 c'est l'id dans le table users pour l'anonyme
            contactAdmin.setIdUserReceiver(idAdmin);  // Ca sera toujours 1 ici car c'est l'id de l'admin        
            contactAdmin.setMail(emailData);
            contactAdmin.setReadMessage(notRead);     // Toujours 0 car pas encore lu

            Transaction tx = sessionHibernate.beginTransaction();
            sessionHibernate.saveOrUpdate(contactAdmin);

            tx.commit();
            sessionHibernate.flush();
            sessionHibernate.close();
            
            response.sendRedirect("/SupMessaging"); 
        }
    }   
}

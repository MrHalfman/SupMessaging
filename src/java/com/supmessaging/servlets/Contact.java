package com.supmessaging.servlets;

import com.supmessaging.persistence.HibernateUtil;
import com.supmessaging.tools.CheckInput;
import com.supmessaging.persistence.Messages;
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
    public static final String jspView = "/WEB-INF/Contact.jsp";
    public static final String mailField = "email";
    public static final String textarea = "message";
    
    CheckInput checkInput = new CheckInput();
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    
    //transmission avec la BDD
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    
    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
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
            //on formate la date et l'heure pour l'envoyer en BDD
            String currentDate = dateFormat.format(date);
            Session sessionHibernate = sessionFactory.openSession();

            Messages contactAdmin = new Messages();

            contactAdmin.setDateMessage(currentDate);
            contactAdmin.setCorpus(messageData);
            contactAdmin.setIdUserAuthor(0);    // 0 c'est l'id dans le table users pour l'anonyme
            contactAdmin.setIdUserReceiver(1);  // Ca sera toujours 1 ici car c'est l'id de l'admin        
            contactAdmin.setMail(emailData);
            contactAdmin.setReadMessage(0);     // Toujours 0 car pas encore lu

            Transaction tx = sessionHibernate.beginTransaction();
            sessionHibernate.saveOrUpdate(contactAdmin);

            tx.commit();
            sessionHibernate.flush();
            sessionHibernate.close();
            
            response.sendRedirect("/SupMessaging"); 
        }
    }   
}

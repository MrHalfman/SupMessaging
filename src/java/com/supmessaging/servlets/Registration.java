package com.supmessaging.servlets;

import com.supmessaging.persistence.HibernateUtil;
import com.supmessaging.persistence.Users;
import com.supmessaging.tools.ActionToolBar;
import com.supmessaging.tools.CheckInput;
import com.supmessaging.tools.Encryption;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Registration extends HttpServlet {
    public static final String jspView = "/WEB-INF/Registration.jsp";
    Users secretary = new Users();
    Encryption encryption = new Encryption();

    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
        ActionToolBar myBeautifulToolbar = new ActionToolBar();
        
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
        SessionCreator sessionCreator = new SessionCreator(request);
        
        String userName = request.getParameter("username");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String passwordOne = request.getParameter("passwordOne");
        String passwordTwo = request.getParameter("passwordTwo");
        
        request.setAttribute("username", userName);
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("email", email);
        
        checkInput.validateUsername(userName, "username");
        checkInput.nonEmpty(firstName, "firstName", false);
        checkInput.nonEmpty(firstName, "lastName", false);
        checkInput.validateMail(email, "email", false);
        checkInput.equalizationField(passwordOne, passwordTwo, "password");
 
        if(!errors.isEmpty()) {
            request.setAttribute("error", errors);
            this.getServletContext().getRequestDispatcher(jspView).forward( request, response );
            errors.clear();
        }
        else {
            
            byte[] salt = encryption.generateSalt();
            String encryptedPassword = null;
            
            try {
                encryptedPassword = encryption.encryptionPassword(salt, passwordTwo);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            firstName = checkInput.formatName(firstName);
            lastName = checkInput.formatName(lastName);
            
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session sessionHibernate = sessionFactory.openSession();
            
            secretary.setFirstname(firstName);
            secretary.setMail(email);
            secretary.setName(lastName);
            secretary.setPseudo(userName);
            secretary.setPassword(encryptedPassword);
            secretary.setSalt(new BigInteger(1, salt).toString(16));
            secretary.setRoleUser(1);

            Transaction tx = sessionHibernate.beginTransaction();
            sessionHibernate.saveOrUpdate(secretary);

            tx.commit();

            sessionHibernate.flush();
            sessionHibernate.close();
            
            sessionCreator.createSession(userName, 1);            
            response.sendRedirect("home/dashboard");
        }
    }   
}
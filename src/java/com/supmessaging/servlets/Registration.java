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
    public static final String usernameField = "username";
    public static final String firstNameField = "firstName";
    public static final String lastNameField = "lastName";
    public static final String emailField = "email";
    public static final String passwordField0ne = "passwordOne";
    public static final String passwordFieldTwo = "passwordTwo";
    
    CheckInput checkInput = new CheckInput();
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session sessionHibernate = sessionFactory.openSession();
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
        String userName = request.getParameter(usernameField);
        String firstName = request.getParameter(firstNameField);
        String lastName = request.getParameter(lastNameField);
        String email = request.getParameter(emailField);
        String passwordOne = request.getParameter(passwordField0ne);
        String passwordTwo = request.getParameter(passwordFieldTwo);
        SessionCreator sessionCreator = new SessionCreator(request);
        
        errors.clear();
        request.setAttribute(usernameField, userName);
        request.setAttribute(firstNameField, firstName);
        request.setAttribute(lastNameField, lastName);
        request.setAttribute(emailField, email);
        
        if(!"".equals(checkInput.validateUsername(userName))) {
            errors.put(usernameField, checkInput.validateUsername(userName));
            request.removeAttribute(usernameField);
        }
        
        if(!"".equals(checkInput.nonEmpty(firstName, false))) {
            errors.put(firstNameField, checkInput.nonEmpty(firstName, false));
            request.removeAttribute(firstNameField);
        }
        
        if(!"".equals(checkInput.nonEmpty(firstName, false))) {
            errors.put(lastNameField, checkInput.nonEmpty(firstName, false));
            request.removeAttribute(lastNameField);
        }
        
        if(!"".equals(checkInput.validateMail(email, false))) {
            errors.put(emailField, checkInput.validateMail(email, false));
            request.removeAttribute(emailField);
        }
        
        if(!"".equals(checkInput.equalizationField(passwordOne, passwordTwo))) {
            errors.put("password", checkInput.equalizationField(passwordOne, passwordTwo));
        }
        
        if(!errors.isEmpty()) {
            request.setAttribute("error", errors);
            this.getServletContext().getRequestDispatcher(jspView).forward( request, response );
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
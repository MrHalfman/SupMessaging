package com.supmessaging.servlets;

import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.CheckForm;
import com.supmessaging.tools.ComplexRequest;
import com.supmessaging.tools.Encryption;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Registration extends HttpServlet {
    public static final String jspView = "/WEB-INF/registration.jsp";
    Encryption encryption = new Encryption();

    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
        ActionToolbar myBeautifulToolbar = new ActionToolbar();
        
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);
        
        if(!sessionCreator.checkSessionExist()) {
            request.setAttribute("popup", true);
            this.getServletContext().getRequestDispatcher( jspView ).forward( request, response );
        }
        else {
            response.sendRedirect("/SupMessaging");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, UnsupportedEncodingException {
        
        SessionCreator sessionCreator = new SessionCreator(request);
        ActionToolbar myBeautifulToolbar = new ActionToolbar();
        ComplexRequest complexRequest = new ComplexRequest();
        
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);
        Map<String, String> errors = new HashMap<>();
        CheckForm checkInput = new CheckForm(request, errors);
        
        String username = request.getParameter("username");
        String firstname = request.getParameter("firstName");
        String lastname = request.getParameter("lastName");
        String email = request.getParameter("email");
        String passwordOne = request.getParameter("passwordOne");
        String passwordTwo = request.getParameter("passwordTwo");
        
        request.setAttribute("username", username);
        request.setAttribute("firstName", firstname);
        request.setAttribute("lastName", lastname);
        request.setAttribute("email", email);
        
        checkInput.validateUsername(username, "username", true);
        checkInput.nonEmpty(firstname, "firstName", false);
        checkInput.nonEmpty(firstname, "lastName", false);
        checkInput.validateMail(email, "email", false);
        checkInput.equalizationPassword(passwordOne, passwordTwo, "password");
 
        if(!errors.isEmpty()) {
            request.setAttribute("error", errors);
            this.getServletContext().getRequestDispatcher(jspView).forward( request, response );
            errors.clear();
        }
        else {
            String encryptedPassword = null;
            
            
            try {
                encryptedPassword = encryption.encryptionPassword(passwordTwo);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
            }

            firstname = checkInput.formatName(firstname);
            lastname = checkInput.formatName(lastname);
            
            complexRequest.registerAnUser(firstname, email, lastname, username, encryptedPassword);
            sessionCreator.createSession(0, username, 1);  // TODO : Add real uid
            response.sendRedirect("/SupMessaging");
        }
    }   
}
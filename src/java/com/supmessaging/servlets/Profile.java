package com.supmessaging.servlets;

import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.CheckForm;
import com.supmessaging.tools.SessionCreator;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.supmessaging.persistence.HibernateUtil;
import com.supmessaging.persistence.Users;
import com.supmessaging.tools.Encryption;
import com.supmessaging.tools.ComplexRequest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Profile extends HttpServlet {
    public static final String jspView = "/WEB-INF/profile.jsp";
    private ComplexRequest complexRequest = new ComplexRequest();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionCreator sessionCreator = new SessionCreator(request);
        ActionToolbar myBeautifulToolbar = new ActionToolbar();
        HttpSession session = request.getSession();
        myBeautifulToolbar.getAdaptedToolbar(sessionCreator, request);

        if(sessionCreator.checkSessionExist()) {
            
            Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = sessionHibernate.beginTransaction();

            Query queryTest = (Query) sessionHibernate.createQuery("FROM Users u WHERE u.pseudo = :pseudo");
            queryTest.setParameter("pseudo", session.getAttribute("username"));       

            List<Users> users = queryTest.list();

            for (Users user : users){
                // On set les données de l'utilisateur dans les champs
                request.setAttribute("firstName", user.getFirstname());
                request.setAttribute("lastName", user.getName());
                request.setAttribute("email", user.getMail());
            }
            
            tx.commit();
            sessionHibernate.close();
                       
            this.getServletContext().getRequestDispatcher( jspView ).forward( request, response );
        }
        else {
            response.sendRedirect("/SupMessaging");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Map<String, String> errorsData = new HashMap<>();
        Map<String, String> errorsPassword = new HashMap<>();
        
        CheckForm checkData = new CheckForm(request, errorsData);
        CheckForm checkPassword = new CheckForm(request, errorsPassword);
        Encryption encryption = new Encryption();
        checkPassword.queryUser((String) session.getAttribute("username"));
        
        // On récupère les modifications
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String oldPassword = request.getParameter("oldPassword");
        String newPasswordOne = request.getParameter("newPasswordOne");
        String newPasswordTwo = request.getParameter("newPasswordTwo");
        
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("email", email);
        
        checkData.nonEmpty(firstName, "firstName", false);
        checkData.nonEmpty(lastName, "lastName", false);
        checkData.validateMail(email, "email", false);
        
        if(!errorsData.isEmpty()) {
            
            request.setAttribute("error", errorsData);
            
            this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
            errorsData.clear();
            errorsPassword.clear();
        }
        else {
            if(!oldPassword.isEmpty() || !newPasswordOne.isEmpty() || !newPasswordTwo.isEmpty()) {
                checkPassword.equalizationPassword(newPasswordOne, newPasswordTwo, "newPassword");
                try {
                    checkPassword.validatePassword(oldPassword, "oldPassword", true);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if (!errorsPassword.isEmpty()) {
                    request.setAttribute("errorPassword", errorsPassword);
                }
                else {
                    String ecryptedPassword = null;
                    try {
                        ecryptedPassword = encryption.encryptionPassword(newPasswordTwo);
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    complexRequest.alterPassword(ecryptedPassword, request);
                }
            }
            
            complexRequest.alterInformations(lastName, firstName, email, request);
            this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
        }
    }
}

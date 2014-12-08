package com.supmessaging.servlets;

import com.supmessaging.tools.ActionToolbar;
import com.supmessaging.tools.CheckInput;
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
import java.util.List;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Profile extends HttpServlet {
    public static final String jspView = "/WEB-INF/profile.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> errors = new HashMap<>();
        CheckInput checkInput = new CheckInput(request, errors);
        
        // On récupère les modifications
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        
        errors.clear();
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("email", email);
        
        checkInput.nonEmpty(firstName, "firstName", false);
        checkInput.nonEmpty(lastName, "lastName", false);
        checkInput.validateMail(email, "email", false);
        
        if(!errors.isEmpty()) {
            request.setAttribute("error", errors);
            
            this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
            errors.clear();
        }
        else {
            alterInformations(lastName, firstName, email, request);
            this.getServletContext().getRequestDispatcher(jspView).forward(request, response);
        }
        
        /*
        String oldPassword = request.getParameter("oldPassword");
        String newPasswordOne = request.getParameter("newPasswordOne");
        String newPasswordTwo = request.getParameter("newPasswordTwo");
        */
    }
    
    public void alterInformations(String lastName, String firstName, String email, HttpServletRequest request){
        HttpSession session = request.getSession();
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();

            Query query = sessionHibernate.createQuery("update Users set "+  
                    "name = :name, " +
                    "firstname = :firstname, " +
                    "mail = :mail "  +  
                    "where pseudo = :pseudo");
     
            
            query.setParameter("name", lastName );
            query.setParameter("firstname", firstName);
            query.setParameter("mail", email);
            query.setParameter("pseudo", session.getAttribute("username"));
//            query.setParameter("password", "");
            System.out.println(query);
            int result = query.executeUpdate();
            tx.commit();
            sessionHibernate.close();
        
    }
    
    public void alterPassword(String newPassword, HttpServletRequest request) {
        
        HttpSession session = request.getSession();
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();

            Query query = sessionHibernate.createQuery("update Users set "+  
                    "password = :password, " +
                    "where pseudo = :pseudo");
     
            query.setParameter("pseudo", session.getAttribute("username"));
            query.setParameter("password", newPassword);
            System.out.println(query);
            int result = query.executeUpdate();
            tx.commit();
            sessionHibernate.close();
   
    }
    
    public void getCurrentPassword (HttpServletRequest request) {
        HttpSession session = request.getSession();
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = sessionHibernate.beginTransaction();

            Query queryTest = (Query) sessionHibernate.createQuery("FROM Users u WHERE u.pseudo = :pseudo");
            queryTest.setParameter("pseudo", session.getAttribute("username"));       

             List<Users> users = queryTest.list();

            for (Users user : users){
            System.out.println(user.getPassword());



            }
            tx.commit();
            sessionHibernate.close();
    
    }
}

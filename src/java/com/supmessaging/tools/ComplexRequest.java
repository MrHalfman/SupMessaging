package com.supmessaging.tools;

import com.supmessaging.persistence.HibernateUtil;
import com.supmessaging.persistence.Users;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ComplexRequest {
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
        int result = query.executeUpdate();
        tx.commit();
        sessionHibernate.close();
    }
    
    public void alterPassword(String newPassword, HttpServletRequest request) {

        HttpSession session = request.getSession();
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();

        Query query = sessionHibernate.createQuery("update Users set "+  
                "password = :password " +
                "where pseudo = :pseudo");

        query.setParameter("pseudo", session.getAttribute("username"));
        query.setParameter("password", newPassword);
        int result = query.executeUpdate();
        tx.commit();
        sessionHibernate.close();
    }
    
    public boolean pseudoExist(String username) {
        List<Users> users = null;    
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();

        Query queryTest = (Query) sessionHibernate.createQuery("FROM Users u WHERE u.pseudo = :pseudo");
        queryTest.setParameter("pseudo", username);       

        users = queryTest.list();

        
        System.out.println(users);
        tx.commit();
        sessionHibernate.close(); 
        
        return !users.isEmpty();
    }
}

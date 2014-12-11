package com.supmessaging.tools;

import com.supmessaging.persistence.HibernateUtil;
import com.supmessaging.persistence.UserFriendship;
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
    
    public List<Users> contactSearch (String username) {
        List<Users> users;
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();

        Query queryTest = (Query) sessionHibernate.createQuery("FROM Users WHERE pseudo LIKE :pseudo");
        queryTest.setParameter("pseudo", "%"+username+"%");       

        users = queryTest.list();

        tx.commit();
        sessionHibernate.close();
        return users;
    }
      
    public int getIdOfUser(String username) {
        List<Users> users;
        int idUser = -1;

        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();

        Query queryTest = (Query) sessionHibernate.createQuery("FROM Users WHERE pseudo LIKE :pseudo");
        queryTest.setParameter("pseudo", username);
        users = queryTest.list();

        for (Users user : users){
            idUser = user.getId();
        }

        tx.commit();
        sessionHibernate.close();

        return idUser;
    }
      
    public void createRelationship(String currentUsername, int idReceiver) {
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();

        UserFriendship newRelation = new UserFriendship();
        int idCurrentUser = getIdOfUser(currentUsername);

        if (idCurrentUser != -1) {
            newRelation.setIdUsers1(idCurrentUser);
            newRelation.setIdUsers2(idReceiver);

            Transaction tx = sessionHibernate.beginTransaction();
            sessionHibernate.saveOrUpdate(newRelation);

            tx.commit();
            sessionHibernate.flush();
            sessionHibernate.close();
        }
    }
}

package com.supmessaging.tools;

import com.supmessaging.persistence.HibernateUtil;
import com.supmessaging.persistence.Messages;
import com.supmessaging.persistence.UserFriendship;
import com.supmessaging.persistence.Users;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.hibernate.SessionFactory;

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
    
    public String getPseudoOfUser(int id) {
        List<Users> users;
        String pseudo = "";

        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();

        Query queryTest = (Query) sessionHibernate.createQuery("FROM Users WHERE pseudo LIKE :id");
        queryTest.setParameter("pseudo", id);
        users = queryTest.list();

        for (Users user : users){
            pseudo = user.getPseudo();
        }

        tx.commit();
        sessionHibernate.close();

        return pseudo;
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
   
    public List<UserFriendship> getFirstListRelation(String currentUsername, String friendUsername) {
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        int idCurrentUser = getIdOfUser(currentUsername);
        int idFriendUser = getIdOfUser(friendUsername);
        List<UserFriendship> MyFriends;
        Transaction tx = sessionHibernate.beginTransaction();
        
        Query checkRelation1 = (Query) sessionHibernate.createQuery("FROM UserFriendship WHERE idUsers1 = :id");
        checkRelation1.setParameter("id", idCurrentUser);
        
        MyFriends = checkRelation1.list();
        
        
        tx.commit();
        sessionHibernate.close();
        return MyFriends;        
    }
    
    public List<UserFriendship> getSecondListRelation(String currentUsername, String friendUsername) {
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        int idCurrentUser = getIdOfUser(currentUsername);
        int idFriendUser = getIdOfUser(friendUsername);
        List<UserFriendship> MyFriends2;
        Transaction tx = sessionHibernate.beginTransaction();
        
        Query checkRelation2 = (Query) sessionHibernate.createQuery("FROM UserFriendship WHERE idUsers2 = :id");
        checkRelation2.setParameter("id", idCurrentUser);
        
        MyFriends2 = checkRelation2.list();
             
        tx.commit();
        sessionHibernate.close();
        return MyFriends2;        
    }
    
    
   
    public boolean checkRelation(String currentUsername, String friendUsername) {
        boolean sameId = false;
        boolean relationExist1 = false;
        boolean relationExist2 = false;
        
        int idCurrentUser = getIdOfUser(currentUsername);
        int idFriendUser = getIdOfUser(friendUsername);
    
        System.out.println("current user : " + idCurrentUser);
        System.out.println("relation user : " + idFriendUser);
        
    
        //  TEST DE LA RELATION
        /*
        if (idCurrentUser == idFriendUser){
            sameId = true;
        }else {       
                for (UserFriendship relation : MyFriends){
                    //sinon on test si la valeur de la colonne user2 = id du nouvel ami
                    if (relation.getIdUsers2() == idFriendUser){
                        relationExist1 = true;
                    } 
                        for (UserFriendship relation2 : MyFriends2){
                            //on test si la valeur de la colonne user1 = id du nouvel ami
                            if (relation2.getIdUsers1() == idFriendUser){                       
                                relationExist2 = true;
                            } 
                            else if (relationExist1 == true){
                                    relationExist2 = true;
                                }
                                else {
                                    relationExist2 = false;
                                     }
                            }                   
                      }            
        }  
        
        */
        
                        
        System.out.println("la relation existe ? : " + relationExist2 +" ! Car même id ? : " + sameId );
        return relationExist2;
    }
    
    public int getStatsUser() {
        
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();
        int numberOfUsers= (int)((long)sessionHibernate.createQuery("select count(*) from Users").uniqueResult());
        
        tx.commit();
        sessionHibernate.close();
        
        return numberOfUsers;
        
        
    }
    public int getStatsMessages() {
        
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();
        int renumberOfMessages= (int)((long)sessionHibernate.createQuery("select count(*) from Messages").uniqueResult());
        
        tx.commit();
        sessionHibernate.close();
        
        return renumberOfMessages;
        
        
    }
    
    public void newMessage(String messageData, int idAuthor, int idReceiver) {
        
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();        
            Session sessionHibernate = sessionFactory.openSession();

            Messages newMessage = new Messages();

            newMessage.setDateMessage(currentDate);
            newMessage.setCorpus(messageData);
            newMessage.setIdUserAuthor(idAuthor);
            newMessage.setIdUserReceiver(idReceiver);     
            newMessage.setMail(idAuthor + "@supmessaging.com");
            newMessage.setReadMessage(0);
            

            Transaction tx = sessionHibernate.beginTransaction();
            sessionHibernate.saveOrUpdate(newMessage);

            tx.commit();
            sessionHibernate.flush();
            sessionHibernate.close();
    }
    
    public List<Messages> getMessages (int idUser1, int idUser2){
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();

        List<Messages> sendMessages;
        Transaction tx = sessionHibernate.beginTransaction();
        
        Query query = (Query) sessionHibernate.createQuery("FROM Messages WHERE idUserAuthor = :id AND idUserReceiver = :id2 OR idUserAuthor = :id2 AND idUserReceiver = :id ");
        query.setParameter("id", idUser1);
        query.setParameter("id2", idUser2);
        
        sendMessages = query.list();
             
        tx.commit();
        sessionHibernate.close();
        return sendMessages;
    }
    
}

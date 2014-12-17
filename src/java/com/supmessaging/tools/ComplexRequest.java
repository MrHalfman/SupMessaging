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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.SessionFactory;

public class ComplexRequest {
        
    public ComplexRequest() {
        
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
    
    // Permet la vérification de l'unicité d'un pseudo
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
    
    // Méthode de récupération des relations des utilisateurs
    // On va récupérer une liste d'utilisateurs en "écrémant" l'ID de l'utilisateur
    public List<Integer> getIDRelations(String username) {
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        int idCurrentUser = getIdOfUser(username);
        List<UserFriendship> myRelations;
        List<Integer> myFriends = new ArrayList<>();
        
        Transaction tx = sessionHibernate.beginTransaction();
        
        Query checkRelation = (Query) sessionHibernate.createQuery("FROM UserFriendship "
                + "WHERE idUsers1 = :id OR idUsers2 = :id");
        checkRelation.setParameter("id", idCurrentUser);
        
        myRelations = checkRelation.list();
        
        for (UserFriendship friendship : myRelations){
            if (friendship.getIdUsers1() == idCurrentUser) {
                myFriends.add(friendship.getIdUsers2());
            }
            else {
                myFriends.add(friendship.getIdUsers1());
            }
        }
        
        tx.commit();
        sessionHibernate.close();
        return myFriends;        
    }
    
    // Permet de retourner un objet utilisateur en fonction de l'ID
    public Users getUserById(int idCurrentUser) {
        List<Users> users;
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();

        Query queryTest = (Query) sessionHibernate.createQuery("FROM Users WHERE id LIKE :id");
        queryTest.setParameter("id", idCurrentUser);
        users = queryTest.list();

        tx.commit();
        sessionHibernate.close();

        return users.get(0);
    }
    
    public List<Users> getFriends(String username) {
        List<Integer> myFriendsID = getIDRelations(username);
        List<Users> myFriendsInformations = new ArrayList<>();
        
        for (Integer idUser : myFriendsID){
            myFriendsInformations.add(getUserById(idUser));
        }
        
        // Permet de trier les éléments en fonction du username
        if (myFriendsInformations.size() > 0) {
            Collections.sort(myFriendsInformations, new Comparator<Users>() {
                @Override
                public int compare(final Users object1, final Users object2) {
                    return object1.getPseudo().compareTo(object2.getPseudo());
                }
            });
        }
        
        return myFriendsInformations;
    }
    
    public List<Users> contactSearch (String friend, String username) {
        List<Users> allUsers;
        List<Users> usersOfInterest = new ArrayList<>();
        List<Integer> idFriends = getIDRelations(username);
        int idCurrentUser = getIdOfUser(username);
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();

        Query queryTest = (Query) sessionHibernate.createQuery("FROM Users WHERE pseudo LIKE :pseudo");
        queryTest.setParameter("pseudo", "%"+friend+"%");       

        allUsers = queryTest.list();
        
        for (Users user : allUsers){
            if (!idFriends.contains(user.getId()) && user.getId() != idCurrentUser) {
                usersOfInterest.add(user);
            }
        }
        
        tx.commit();
        sessionHibernate.close();
        return usersOfInterest;
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
        Messages newMessage = new Messages();
        String currentDate = dateFormat.format(date);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();        
        Session sessionHibernate = sessionFactory.openSession();

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
        
        Query query = (Query) sessionHibernate.createQuery("FROM Messages "
                + "WHERE idUserAuthor = :id AND idUserReceiver = :id2 "
                + "OR idUserAuthor = :id2 AND idUserReceiver = :id ");
        query.setParameter("id", idUser1);
        query.setParameter("id2", idUser2);
        
        sendMessages = query.list();
             
        tx.commit();
        sessionHibernate.close();
        return sendMessages;
    }
    
    public List<Integer> getCommunicateContact (String currentUsername) {
        ComplexRequest request = new ComplexRequest();
        int idCurrentUsername = request.getIdOfUser(currentUsername);
        List<Messages> idOfUserInConversation1;
        List<Messages> idOfUserInConversation2;
        
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();
        
        Query query = (Query) sessionHibernate.createQuery("SELECT idUserAuthor "
                + "FROM Messages WHERE idUserReceiver = :id");
        query.setParameter("id", idCurrentUsername);
        
        Query query2 = (Query) sessionHibernate.createQuery("SELECT idUserReceiver "
                + "FROM Messages WHERE idUserAuthor = :id");
        query2.setParameter("id", idCurrentUsername);
        
        idOfUserInConversation1 = query.list();
        idOfUserInConversation2 = query2.list();
        idOfUserInConversation1.addAll(idOfUserInConversation2);
        
        Set set = new HashSet() ;
        set.addAll(idOfUserInConversation1) ;
        ArrayList idOfUserInConversation = new ArrayList(set) ; 
        
        tx.commit();
        sessionHibernate.close();
        
        return idOfUserInConversation;        
    }
    
    public void sendAnonymMessage(String messageData, int idAnonym, int idAdmin, String emailData, int notRead) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        
        //on formate la date et l'heure pour l'envoyer en BDD
        String currentDate = dateFormat.format(date);
        Session sessionHibernate = sessionFactory.openSession();

        Messages contactAdmin = new Messages();

        contactAdmin.setDateMessage(currentDate);
        contactAdmin.setCorpus(messageData);
        contactAdmin.setIdUserAuthor(idAnonym);
        contactAdmin.setIdUserReceiver(idAdmin);        
        contactAdmin.setMail(emailData);
        contactAdmin.setReadMessage(notRead);   

        Transaction tx = sessionHibernate.beginTransaction();
        sessionHibernate.saveOrUpdate(contactAdmin);

        tx.commit();
        sessionHibernate.flush();
        sessionHibernate.close();
        
    }
    
    // Permet de retourner l'ID des utilisateurs tel que l'anonyme ou l'admin
    public int getSpecificUser(int userType) {
        int idSpecificUser = -1;
        List<Users> users = null;    
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();

        Query queryTest = (Query) sessionHibernate.createQuery("FROM Users u WHERE u.roleUser = :userType");
        queryTest.setParameter("userType", userType);       

        users = queryTest.list();
        
        for (Users user : users){
            idSpecificUser = user.getId();
        }
        
        tx.commit();
        sessionHibernate.close(); 
        
        return idSpecificUser;
    }
<<<<<<< HEAD
    
    public List<Messages> getTenLastMessages(int idCurrentUser) {
        
        List<Messages> mess = null;    
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();

        Query queryTest = (Query) sessionHibernate.createQuery("FROM Messages u WHERE idUserReceiver = :id ORDER BY id DESC LIMIT 0, 10");
        queryTest.setParameter("id", idCurrentUser);

        mess = queryTest.list();
        
        
        
        tx.commit();
        sessionHibernate.close(); 
        
        return mess;
=======

    public void registerAnUser(String firstname, String email, String lastname, String username, String encryptedPassword) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session sessionHibernate = sessionFactory.openSession();
        Users secretary = new Users();

        secretary.setFirstname(firstname);
        secretary.setMail(email);
        secretary.setName(lastname);
        secretary.setPseudo(username);
        secretary.setPassword(encryptedPassword);
        secretary.setRoleUser(1);

        Transaction tx = sessionHibernate.beginTransaction();
        sessionHibernate.saveOrUpdate(secretary);
        tx.commit();

        sessionHibernate.flush();
        sessionHibernate.close();
>>>>>>> FETCH_HEAD
    }
}

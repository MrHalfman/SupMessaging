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
    
    /*public List<Users> listFriends (String currentUsername) {
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        //int idCurrentUser = getIdOfUser(currentUsername);
        List<Users> MyFriends;
        //List<UserFriendship> MyRelations;
        //int idFriends = 0;
        //int idFriends2 = 0;
        Transaction tx = sessionHibernate.beginTransaction();
        
        //recuperer les relations
        //Query getFriendRelation = (Query) sessionHibernate.createQuery("SELECT id_users1, id_users2 FROM UserFriendship WHERE id_users2 OR id_users1 = :id");
        
        Query getFriendRelation = (Query) sessionHibernate.createQuery("FROM Users, UserFriendship WHERE Users.id = UserFriendship.id_users1");
        //getFriendRelation.setParameter("id", idCurrentUser);
        
        MyFriends = getFriendRelation.list();

        for (Users relation : MyFriends){
            
            System.out.println(relation.getPseudo()); 
        }
        
        
        
        
        //recuperer les infos graces aux ids
        Query getFriendRelation2 = (Query) sessionHibernate.createQuery("FROM Users WHERE id = :id OR :id2");
        getFriendRelation2.setParameter("id", idFriends);
        getFriendRelation2.setParameter("id2", idFriends2);
        
        
        MyFriends = getFriendRelation2.list();
        for (Users friends : MyFriends){
            friends.getPseudo();
            friends.getFirstname();
            friends.getName();
        }

        tx.commit();
        sessionHibernate.close();
        return MyFriends;
    }*/
    
    public boolean checkRelation(String currentUsername, String friendUsername) {
        boolean sameId = false;
        boolean relationExist1 = false;
        boolean relationExist2 = false;
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        int idCurrentUser = getIdOfUser(currentUsername);
        int idFriendUser = getIdOfUser(friendUsername);
        List<UserFriendship> MyFriends;
        List<UserFriendship> MyFriends2;
        Transaction tx = sessionHibernate.beginTransaction();
        
        Query checkRelation1 = (Query) sessionHibernate.createQuery("FROM UserFriendship WHERE idUsers1 = :id");
        checkRelation1.setParameter("id", idCurrentUser);
        Query checkRelation2 = (Query) sessionHibernate.createQuery("FROM UserFriendship WHERE idUsers2 = :id2");
        checkRelation2.setParameter("id2", idCurrentUser);
        
        MyFriends = checkRelation1.list();
        MyFriends2 = checkRelation2.list();
        
        System.out.println("current user: " + idCurrentUser);
        System.out.println("relation user: " + idFriendUser);
        
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
        
        
        
                        
        System.out.println("la relation existe ? : " + relationExist2 +" ! Car même id ? : " + sameId );
        tx.commit();
        sessionHibernate.close();
        return relationExist2;
    }
}

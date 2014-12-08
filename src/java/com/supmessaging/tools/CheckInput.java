package com.supmessaging.tools;

import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.supmessaging.persistence.Users;
import com.supmessaging.persistence.HibernateUtil;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CheckInput {
    
    private final HttpServletRequest request;
    private final Map<String, String> errors;
    private List<Users> users = null;
    private String pseudo;
    private String password;
    private String salt;
    
            
    public void queryUser(String username){
        
        Session sessionHibernate = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = sessionHibernate.beginTransaction();

        Query queryTest = (Query) sessionHibernate.createQuery("FROM Users u WHERE u.pseudo = :pseudo");
        queryTest.setParameter("pseudo", username);       

        users = queryTest.list();

        for (Users user : users){
            this.pseudo = user.getPseudo();
            this.password = user.getPassword();
            this.salt = user.getSalt();
        }
        System.out.println(this.pseudo);
        System.out.println(this.password);
        System.out.println(this.salt);
        tx.commit();
        sessionHibernate.close();
    }
    
            
    public CheckInput(HttpServletRequest request, Map<String, String> errors) {
        this.request = request;
        this.errors = errors;
    }
    
    public void configureError(String name, String error) {
        errors.put(name, error);
        request.removeAttribute(name);
    }
    
    public void validateUsername(String username, String name) {
        String error = "";
        if (username == null || username.trim().length() == 0) {
            error = "Please, enter a valid username";
            configureError(name, error);
        }
    }
    
    public void validatePassword(String password, String name) {
        String error = "";
        if (password == null || password.trim().length() == 0) {
            error = "Please, enter a valid password";
            configureError(name, error);
        }
    }
    
    public void validateMail(String email, String name, boolean isAnonymousMessage) {
        String error = "";
        if (email == null || email.trim().length() == 0) {
            if (!isAnonymousMessage) {
                error = "We need a email to validate the field";
            }
            else {
                error = "Please, if you want a response to your message, we need a email";
            }
            configureError(name, error);
        }
        else if (!email.matches( "([a-zA-Z0-9-_+\\/=]+\\.?[a-zA-Z0-9-_+\\/=])+@[a-zA-Z]+\\.[a-zA-Z]{0,4}" )) {
            error = "Please give us a valide email";
            configureError(name, error);
        }
    }
    
    public void nonEmpty(String text, String name, boolean isTextarea) {
        String error = "";
        if (text == null || text.trim().length() == 0) {
            
            if (isTextarea) {
                error = "Are you trying to communicate ?";
            }
            else {
                error = "Sorry, you have to fill the field";
            }
            configureError(name, error);
        }
    }
    
    public void equalizationField(String fieldOne, String fieldTwo, String name) {
        String error = "";
        boolean fieldMissing = false;
        boolean passwordStandard = true;
        String[] fields = {fieldOne, fieldTwo};
        
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] == null || fields[i].trim().length() == 0) {
                error = "Sorry, you have to fill the two fields";
                configureError(name, error);
                fieldMissing = true;
                break;
            }
        }
        
        if (!fieldMissing) {
            if (!fieldOne.equals(fieldTwo)) {
                error = "Sorry, the fields aren't equals";
                configureError(name, error);
            }

            else {
                if (fieldOne.trim().length() < 4) {
                    error += "Please enter a new one with 5 caracter or more. ";
                    passwordStandard = false;
                }
                if (fieldOne.equals(fieldOne.toLowerCase())) {
                    error += "It must contain both uppercase and lowercase characters";
                    passwordStandard = false;
                }

                if (!passwordStandard) {
                    configureError(name, error);
                }
            }
        }
    }
    
    public String formatName(String name) {
    name = name.toLowerCase(Locale.FRENCH);
    name = name.substring(0, 1).toUpperCase() + name.substring(1);
    
    return name;
    }
}

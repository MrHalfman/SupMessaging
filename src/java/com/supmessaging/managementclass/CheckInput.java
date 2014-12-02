/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supmessaging.managementclass;

/**
 *
 * @author Martin
 */
public class CheckInput {
    public String validateUsername(String username) {
        String error = "";
        if (username == null || username.trim().length() == 0) {
            error = "Please, enter a valid username";
        }
        return error;
    }
    
    public String validatePassword(String password) {
        String error = "";
        if (password == null || password.trim().length() == 0) {
            error = "Please, enter a valid password";
            return error;
        }
        if (password.trim().length() < 4) {
            error += "Please enter a new one with 5 caracter or more. ";
        }
        if (password.equals(password.toLowerCase())) {
            error += "It must contain both uppercase and lowercase characters";
        }
        return error;
    }
    
    public String validateMail(String email) {
        String error = "";
        if (email == null || email.trim().length() == 0) {
            error = "Please, if you want a response to your message, we need a email";
            return error;
        }
        if (!email.matches( "([a-zA-Z0-9-_+\\/=]+\\.?[a-zA-Z0-9-_+\\/=])+@[a-zA-Z]+\\.[a-zA-Z]{0,4}" )) {
            error = "Please give us a valide email";
        }
        
        return error;
    }
    
    public String nonEmpty(String text) {
        String error = "";
        if (text == null || text.trim().length() == 0) {
            error = "Are you trying to communicate ?";
        }
        return error;
    }
    
    public String equalizationField(String fieldOne, String fieldTwo) {
        String error = "";
        String[] fields = {fieldOne, fieldTwo};
        
        for (int i = 0; i < fields.length; i++) {
            if (fields[i] == null || fields[i].trim().length() == 0) {
                error = "Sorry, you have to fill the two fields";
                return error;
            }
        }
        
        if (!fieldOne.equals(fieldTwo)) {
            error = "Sorry, the fields aren't equals";
        }
        return error;
    }
}

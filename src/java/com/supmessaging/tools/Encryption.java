/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supmessaging.tools;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Martin
 */
public class Encryption {
    
    public String encryptionPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        byte[] passBytes = password.getBytes();
        md.reset();
        
        byte[] digested = md.digest(passBytes);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<digested.length;i++){
            sb.append(Integer.toHexString(0xff & digested[i]));
        }
        return sb.toString();
        
        /*KeySpec spec = new PBEKeySpec("password".toCharArray(), salt.getBytes(), 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        String encryptedPassword = new BigInteger(1, hash).toString(16);

        return encryptedPassword;*/
    }
    
    public boolean checkPasswordEqual(String password, String hash) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        
        String encryptedPassword = this.encryptionPassword(password);
        boolean isEqual = true;
        
        if(!hash.equals(encryptedPassword)) {
            isEqual = false;
        }
        
        return isEqual;
    }
}

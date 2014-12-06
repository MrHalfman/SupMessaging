/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.supmessaging.tools;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Martin
 */
public class Encryption {
    
    public byte[] generateSalt() {
        final Random random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        
        return salt;
    }
    
    public String encryptionPassword(byte[] salt, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec("password".toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        String encryptedPassword = new BigInteger(1, hash).toString(16);
        
        return encryptedPassword;
    }
    
    public boolean checkPasswordEqual(byte[] salt, String password, String hash) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String encryptedPassword = this.encryptionPassword(salt, password);
        boolean isEqual = true;
        
        if(!hash.equals(encryptedPassword)) {
            isEqual = false;
        }
        
        return isEqual;
    }
}

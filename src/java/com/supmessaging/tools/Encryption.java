package com.supmessaging.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    
    public String encryptionPassword(String password) throws NoSuchAlgorithmException {
        
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        byte[] passwordInBytes = password.getBytes();
        messageDigest.reset();
        
        byte[] digested = messageDigest.digest(passwordInBytes);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < digested.length; i++){
            stringBuilder.append(Integer.toHexString(0xff & digested[i]));
        }
        return stringBuilder.toString();
        
        /*KeySpec spec = new PBEKeySpec("password".toCharArray(), salt.getBytes(), 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();
        String encryptedPassword = new BigInteger(1, hash).toString(16);

        return encryptedPassword;*/
    }
    
    public boolean checkPasswordEqual(String password, String hash) throws NoSuchAlgorithmException{
        
        String encryptedPassword = this.encryptionPassword(password);
        boolean isEqual = true;
        
        if(!hash.equals(encryptedPassword)) {
            isEqual = false;
        }
        
        return isEqual;
    }
}

package com.supmessaging.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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

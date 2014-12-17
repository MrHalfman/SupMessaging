package com.supmessaging.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

    // Méthode de chiffrement des mots de passe
    public String encryptionPassword(String password) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        byte[] passwordInBytes = password.getBytes();
        messageDigest.reset();

        byte[] digested = messageDigest.digest(passwordInBytes);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < digested.length; i++) {
            stringBuilder.append(Integer.toHexString(0xff & digested[i]));
        }
        return stringBuilder.toString();
    }

    /* Au lieu de déchiffrer les mots de passe, la méthode va chiffrer la saisie de l'utilisateur
     et regarder s'ils sont égaux */
    public boolean checkPasswordEqual(String password, String hash) throws NoSuchAlgorithmException {

        String encryptedPassword = this.encryptionPassword(password);
        boolean isEqual = true;

        if (!hash.equals(encryptedPassword)) {
            isEqual = false;
        }

        return isEqual;
    }
}

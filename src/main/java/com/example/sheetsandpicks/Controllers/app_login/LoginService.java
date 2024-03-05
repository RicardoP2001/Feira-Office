package com.example.sheetsandpicks.Controllers.app_login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A classe LoginService fornece serviços relacionados à autenticação e segurança do login.
 */
public class LoginService {

    /**
     * Método para criar o hash SHA-256 da palavra-passe.
     *
     * @param password A senha a ser criptografada.
     * @return O hash SHA-256 da senha.
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

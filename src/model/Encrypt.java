/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.security.MessageDigest;

/**
 *
 * @author mcsmuscle
 */
public class Encrypt {
    public static String encryptPassword(String password) {
        String hex = "";
        try {
            // Create a MessageDigest object
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // Update the message digest with the password bytes
            md.update(password.getBytes());
            
            // Get the encrypted password as a byte array
            byte[] encryptedPassword = md.digest();
            
            // Convert the byte array to a hex string
            hex = javax.xml.bind.DatatypeConverter.printHexBinary(encryptedPassword);
        } catch (Exception e) {
        } // 8C6976E5B5410415BDE908BD4DEE15DFB167A9C873FC4BB8A81F6F2AB448A918
        return hex;
    }
    
    public static void main(String[] args) {
        System.out.println(Encrypt.encryptPassword("admin"));
    }
}

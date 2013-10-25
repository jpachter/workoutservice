package com.gymlife.workoutservice.db.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SecureRandomFactory {
    //static reference to itself
    private static SecureRandomFactory instance = new SecureRandomFactory(); 
    
    //private constructor
    private SecureRandomFactory() {}
 
    private SecureRandom createSecureRandom() {
    	SecureRandom random = null;
        try {
        	random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("ERROR: Unable to create SecureRandom.");
        }
        return random;
    }  
 
    public static SecureRandom getRandom() {
        return instance.createSecureRandom();
    }
}

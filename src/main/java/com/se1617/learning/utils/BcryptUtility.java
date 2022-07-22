/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author giaki
 */
public class BcryptUtility {
    
    public static String hashpw(String value) {
        String hash = BCrypt.hashpw(value, BCrypt.gensalt());
        return hash;
    }

    public static boolean verifyHash(String value, String hash) {
        return BCrypt.checkpw(value, hash);
    }
    
    private static BcryptUtility instance = new BcryptUtility();
    
    private BcryptUtility() {}
    
    public static BcryptUtility getInstance() {
        return instance;
    }
}

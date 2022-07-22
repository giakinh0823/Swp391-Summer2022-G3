/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.config.url.client;

/**
 *
 * @author giaki
 */
public class UrlAuthClient {

    public static final String URL_PATTERN = "^/auth(/|).*";
    public static final String AUTH_LOGIN = "^/auth/login(/|)$";
    public static final String AUTH_SIGNUP = "^/auth/signup(/|)$";
    public static final String AUTH_LOGOUT = "^/auth/logout(/|)$";
    public static final String AUTH_ENABLE = "^/auth/enable(/|)$";
    public static final String AUTH_ENABLE_SENDMAIL_DONE = "^/auth/enable/confirm(/|)$";
    public static final String AUTH_ENABLE_CONFIRM = "^/auth/enable/[A-Za-z0-9-_]*.[A-Za-z0-9-_]*.[A-Za-z0-9-_]*(/|)$";
    public static final String AUTH_RESETPASSWORD = "^/auth/reset(/|)$";
    public static final String AUTH_RESETPASSWORD_CONFIRM = "^/auth/very/[A-Za-z0-9-_]*.[A-Za-z0-9-_]*.[A-Za-z0-9-_]*(/|)$";
    
    public static final String API_AUTH_LOGIN = "^/auth/api/login(/|)$";
    public static final String API_AUTH_SIGNUP = "^/auth/api/signup(/|)$";

    private static UrlAuthClient instance = new UrlAuthClient();

    private UrlAuthClient() {
    }

    public static UrlAuthClient getInstance() {
        return instance;
    }

}

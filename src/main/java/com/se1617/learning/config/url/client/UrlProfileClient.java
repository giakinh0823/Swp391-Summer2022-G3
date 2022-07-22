/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.config.url.client;

import com.se1617.learning.config.security.SecurityConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giaki
 */
public class UrlProfileClient {

    public static final String URL_PATTERN = "^/profile(/|).*";
    public static final String CHANGE_PASSWORD = "^/profile/change-password(/|)$";
    public static final String PROFILE = "^/profile(/|)$";
    public static final String PROFILE_EDIT = "^/profile/edit(/|)$";

    private static UrlProfileClient instance = new UrlProfileClient();

    private UrlProfileClient() {
    }

    public static UrlProfileClient getInstance() {
        return instance;
    }

    public static void checkAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
        if (URI.matches(CHANGE_PASSWORD)) {
            System.out.println("CHANGE_PASSWORD");
            if (!SecurityConfig.isAccess(request, "CHANGE_PASSWORD")) {
               throw new Exception("code - CHANGE_PASSWORD");
            } 
        } else if (URI.matches(PROFILE)) {
            System.out.println("PROFILE_VIEW");
            if (!SecurityConfig.isAccess(request, "PROFILE_READ")) {
                throw new Exception("code - PROFILE_READ");
            } 
        } else if (URI.matches(PROFILE_EDIT)) {
            System.out.println("PROFILE_EDIT");
            if (!SecurityConfig.isAccess(request, "PROFILE_EDIT")) {
                throw new Exception("code - PROFILE_EDIT");
            } 
        }
    }
}

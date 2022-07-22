/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.config.security;

import com.se1617.learning.config.url.admin.UrlDashboardAdmin;
import com.se1617.learning.config.url.admin.UrlPostAdmin;
import com.se1617.learning.config.url.admin.UrlQuestionAdmin;
import com.se1617.learning.config.url.admin.UrlQuizzesAdmin;
import com.se1617.learning.config.url.admin.UrlRegistrationsAdmin;
import com.se1617.learning.config.url.admin.UrlRoleAdmin;
import com.se1617.learning.config.url.admin.UrlSettingAdmin;
import com.se1617.learning.config.url.admin.UrlSliderAdmin;
import com.se1617.learning.config.url.admin.UrlSubjectAdmin;
import com.se1617.learning.config.url.admin.UrlUserAdmin;
import com.se1617.learning.config.url.client.UrlBlogClient;
import com.se1617.learning.config.url.client.UrlCourseClient;
import com.se1617.learning.config.url.client.UrlProfileClient;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giaki
 */
public class AuthorizationConfig {
    
    private static AuthorizationConfig instance = new AuthorizationConfig();
    
    private AuthorizationConfig() {}
    
    public static AuthorizationConfig getInstance() {
        return instance;
    }

    public static void checkAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
        // -------------- Authorization admin --------------------//
        if (URI.matches(UrlSubjectAdmin.URL_PATTERN)) {
            UrlSubjectAdmin.checkAccess(request, response);
        }else if (URI.matches(UrlRoleAdmin.URL_PATTERN)) {
            UrlRoleAdmin.checkAccess(request, response);
        }else if  (URI.matches(UrlSliderAdmin.URL_PATTERN)) {
            UrlSliderAdmin.checkAccess(request, response);
        }else if  (URI.matches(UrlSettingAdmin.URL_PATTERN)) {
            UrlSettingAdmin.checkAccess(request, response);
        }else if  (URI.matches(UrlUserAdmin.URL_PATTERN)) {
            UrlUserAdmin.checkAccess(request, response);
        }else if  (URI.matches(UrlDashboardAdmin.URL_PATTERN)) {
            UrlDashboardAdmin.checkAccess(request, response);
        }else if (URI.matches(UrlRegistrationsAdmin.URL_PATTERN)) {
            UrlRegistrationsAdmin.checkAccess(request, response);
        }else if (URI.matches(UrlQuizzesAdmin.URL_PATTERN)) {
            UrlQuizzesAdmin.checkAccess(request, response);
        }else if (URI.matches(UrlQuestionAdmin.URL_PATTERN)) {
            UrlQuestionAdmin.checkAccess(request, response);
        }else if (URI.matches(UrlPostAdmin.URL_PATTERN)) {
            UrlPostAdmin.checkAccess(request, response);
        }
        
        // -------------- Authorization client --------------------//
        else if (URI.matches(UrlProfileClient.URL_PATTERN)) {
            UrlProfileClient.checkAccess(request, response);
        }else if(URI.matches(UrlCourseClient.URL_PATTERN)){
            UrlCourseClient.checkAccess(request, response);
        }
    }
}

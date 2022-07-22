/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.config.url.admin;

import com.se1617.learning.config.security.SecurityConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giaki
 */
public class UrlUserAdmin {

    public static final String URL_PATTERN = "^/admin/user(/|).*";
    public static final String USER_DELETE_SELECTED = "^/admin/user/delete-selected$";
    public static final String USER_EDIT = "^/admin/user/edit/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String USER_CREATE = "^/admin/user/create(/|)$";
    public static final String USER_DELETE = "^/admin/user/delete/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String USER_DETAIL = "^/admin/user/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String USER_LIST = "^/admin/user(/|)$";

    private static UrlUserAdmin instance = new UrlUserAdmin();

    private UrlUserAdmin() {
    }

    public static UrlUserAdmin getInstance() {
        return instance;
    }

    public static void checkAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
        if (URI.matches(USER_DELETE_SELECTED)) {
            if (!SecurityConfig.isAccess(request, "USER_DELETE")) {
                throw new Exception("code - USER_DELETE");
            }
        } else if (URI.matches(USER_EDIT)) {
            if (!SecurityConfig.isAccess(request, "USER_EDIT")) {
                throw new Exception("code - USER_EDIT");
            }
        } else if (URI.matches(USER_CREATE)) {
            if (!SecurityConfig.isAccess(request, "USER_CREATE")) {
                throw new Exception("code - USER_CREATE");
            }
        } else if (URI.matches(USER_DELETE)) {
            if (!SecurityConfig.isAccess(request, "USER_DELETE")) {
                throw new Exception("code - USER_DELETE");
            }
        } else if (URI.matches(USER_DETAIL)) {
            if (!SecurityConfig.isAccess(request, "USER_READ")) {
                throw new Exception("code - USER_READ");
            }
        } else if (URI.matches(USER_LIST)) {
            if (!SecurityConfig.isAccess(request, "USER_READ")) {
                throw new Exception("code - USER_READ");
            }
        }
    }
}

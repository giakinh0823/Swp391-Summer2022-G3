/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.config.url.admin;

import com.google.gson.Gson;
import com.se1617.learning.config.security.SecurityConfig;
import com.se1617.learning.model.general.Message;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giaki
 */
public class UrlRoleAdmin {

    public static final String URL_PATTERN = "^/admin/role(/|).*";
    public static final String ROLE_LIST = "^/admin/role(/|)$";
    public static final String ROLE_EDIT = "^/admin/role/edit/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String ROLE_FEATURE_DETAIL = "^/admin/role/feature/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String ROLE_FEATURE_CREATE = "^/admin/role/feature/create(/|)$";
    public static final String ROLE_FEATURE_EDIT = "^/admin/role/feature/edit/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String ROLE_FEATURE_DELETE = "^/admin/role/feature/delete/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String ROLE_FEATURE_DELETEALL = "^/admin/role/feature/delete-all$";
    
    private static UrlRoleAdmin instance = new UrlRoleAdmin();
    
    private UrlRoleAdmin() {}
    
    public static UrlRoleAdmin getInstance() {
        return instance;
    }

    public static void checkAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
        if (URI.matches(ROLE_FEATURE_DELETEALL)) {
            if (!SecurityConfig.isAccess(request, "ROLE_DELETE")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - ROLE_DELETE");
            }
        } else if (URI.matches(ROLE_FEATURE_DELETE)) {
            if (!SecurityConfig.isAccess(request, "ROLE_DELETE")) {
                throw new Exception("code - ROLE_DELETE");
            }
        } else if (URI.matches(ROLE_FEATURE_EDIT)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "FEATURE_EDIT")) {
                    throw new Exception("code - FEATURE_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "FEATURE_READ")) {
                    throw new Exception("code - FEATURE_READ");
                }
            }
        } else if (URI.matches(ROLE_FEATURE_CREATE)) {
            if (!SecurityConfig.isAccess(request, "FEATURE_CREATE")) {
                throw new Exception("code - FEATURE_CREATE");
            }
        } else if (URI.matches(ROLE_FEATURE_DETAIL)) {
            if (!SecurityConfig.isAccess(request, "FEATURE_READ")) {
                throw new Exception("code - FEATURE_READ");
            }
        } else if (URI.matches(ROLE_EDIT)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "ROLE_EDIT")) {
                    throw new Exception("code - ROLE_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "ROLE_READ")) {
                    throw new Exception("code - ROLE_READ");
                }
            }
        } else if (URI.matches(ROLE_LIST)) {
            if (!SecurityConfig.isAccess(request, "ROLE_READ")) {
                throw new Exception("code - ROLE_READ");
            }
        }
    }
}

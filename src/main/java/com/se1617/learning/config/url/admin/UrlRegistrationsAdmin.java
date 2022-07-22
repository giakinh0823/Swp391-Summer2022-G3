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
public class UrlRegistrationsAdmin {

    public static final String URL_PATTERN = "^/admin/registrations(/|).*";
    public static final String REGISTRATIONS_LIST = "^/admin/registrations(/|)$";
    public static final String REGISTRATIONS_CREATE = "^/admin/registrations/create(/|)$";
    public static final String REGISTRATIONS_DETAIL = "^/admin/registrations/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String REGISTRATIONS_CHANGE_STATUS = "^/admin/registrations/[a-zA-Z0-9-]{1,}/change-status(/|)$";
    public static final String REGISTRATIONS_EDIT = "^/admin/registrations/edit/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String REGISTRATIONS_DELETE = "^/admin/registrations/delete/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String REGISTRATIONS_DELETE_SELETED = "^/admin/registrations/delete-selected(/|)$";
    
    private static UrlRegistrationsAdmin instance = new UrlRegistrationsAdmin();

    private UrlRegistrationsAdmin() {
    }

    public static UrlRegistrationsAdmin getInstance() {
        return instance;
    }
    
    public static void checkAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
        if (URI.matches(REGISTRATIONS_DELETE_SELETED)) {
            if (!SecurityConfig.isAccess(request, "REGISTRATIONS_DELETE")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - REGISTRATIONS_DELETE");
            }
        } else if (URI.matches(REGISTRATIONS_CHANGE_STATUS)) {
            if (!SecurityConfig.isAccess(request, "REGISTRATIONS_EDIT")) {
                throw new Exception("code - REGISTRATIONS_EDIT");
            }
        }else if (URI.matches(REGISTRATIONS_CREATE)) {
            if (!SecurityConfig.isAccess(request, "REGISTRATIONS_CREATE")) {
                throw new Exception("code - REGISTRATIONS_CREATE");
            }
        } else if (URI.matches(REGISTRATIONS_EDIT)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "REGISTRATIONS_EDIT")) {
                    throw new Exception("code - REGISTRATIONS_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "QUIZZES_EDIT")) {
                    throw new Exception("code - QUIZZES_EDIT");
                }
            }
        } else if (URI.matches(REGISTRATIONS_DELETE)) {
            if (!SecurityConfig.isAccess(request, "REGISTRATIONS_DELETE")) {
                throw new Exception("code - REGISTRATIONS_DELETE");
            }
        } else if (URI.matches(REGISTRATIONS_DETAIL)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "REGISTRATIONS_EDIT")) {
                    throw new Exception("code - REGISTRATIONS_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "REGISTRATIONS_READ")) {
                    throw new Exception("code - REGISTRATIONS_READ");
                }
            }
        } else if (URI.matches(REGISTRATIONS_LIST)) {
            if (!SecurityConfig.isAccess(request, "REGISTRATIONS_READ")) {
                throw new Exception("code - REGISTRATIONS_READ");
            }
        }
    }
}

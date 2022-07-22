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
public class UrlPostAdmin {

    public static final String URL_PATTERN = "^/admin/post(/|).*";
    public static final String POST_LIST = "^/admin/post(/|)$";
    public static final String POST_CREATE = "^/admin/post/create(/|)$";
    public static final String POST_DETAIL = "^/admin/post/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String POST_EDIT = "^/admin/post/edit/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String POST_DELETE = "^/admin/post/delete/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String POST_DELETE_SELETED = "^/admin/post/delete-selected(/|)$";
    public static final String POST_MEDIA_UPLOAD = "^/admin/post/media/uploads(/|)$";
    public static final String POST_MEDIA_DELETE = "^/admin/post/media/delete(/|)$";

    private static UrlPostAdmin instance = new UrlPostAdmin();

    private UrlPostAdmin() {
    }

    public static UrlPostAdmin getInstance() {
        return instance;
    }

    public static void checkAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
        if (URI.matches(POST_MEDIA_UPLOAD)) {
            if (!SecurityConfig.isAccess(request, "POST_EDIT") && !SecurityConfig.isAccess(request, "POST_CREATE")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - POST_UPLOAD_MEDIA");
            }
        }else if (URI.matches(POST_MEDIA_DELETE)) {
            if (!SecurityConfig.isAccess(request, "POST_EDIT") && !SecurityConfig.isAccess(request, "POST_CREATE")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - POST_UPLOAD_MEDIA");
            }
        }else if (URI.matches(POST_DELETE_SELETED)) {
            if (!SecurityConfig.isAccess(request, "POST_DELETE")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - POST_DELETE");
            }
        } else if (URI.matches(POST_CREATE)) {
            if (!SecurityConfig.isAccess(request, "POST_CREATE")) {
                throw new Exception("code - POST_CREATE");
            }
        } else if (URI.matches(POST_EDIT)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "POST_EDIT")) {
                    throw new Exception("code - POST_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "POST_READ")) {
                    throw new Exception("code - POST_READ");
                }
            }
        } else if (URI.matches(POST_DELETE)) {
            if (!SecurityConfig.isAccess(request, "POST_DELETE")) {
                throw new Exception("code - POST_DELETE");
            }
        } else if (URI.matches(POST_DETAIL)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "POST_EDIT")) {
                    throw new Exception("code - POST_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "POST_READ")) {
                    throw new Exception("code - POST_READ");
                }
            }
        } else if (URI.matches(POST_LIST)) {
            if (!SecurityConfig.isAccess(request, "POST_READ")) {
                throw new Exception("code - POST_READ");
            }
        }
    }
}

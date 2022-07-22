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
public class UrlQuizzesAdmin {

    public static final String URL_PATTERN = "^/admin/quizzes(/|).*";
    public static final String QUIZZES_LIST = "^/admin/quizzes(/|)$";
    public static final String QUIZZES_CREATE = "^/admin/quizzes/create(/|)$";
    public static final String QUIZZES_DETAIL = "^/admin/quizzes/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String QUIZZES_EDIT = "^/admin/quizzes/edit/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String QUIZZES_DELETE = "^/admin/quizzes/delete/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String QUIZZES_DELETE_SELETED = "^/admin/quizzes/delete-selected(/|)$";

    private static UrlQuizzesAdmin instance = new UrlQuizzesAdmin();

    private UrlQuizzesAdmin() {
    }

    public static UrlQuizzesAdmin getInstance() {
        return instance;
    }

    public static void checkAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
        if (URI.matches(QUIZZES_DELETE_SELETED)) {
            if (!SecurityConfig.isAccess(request, "QUIZZES_DELETE")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - QUIZZES_DELETE");
            }
        } else if (URI.matches(QUIZZES_CREATE)) {
            if (!SecurityConfig.isAccess(request, "QUIZZES_CREATE")) {
                throw new Exception("code - QUIZZES_CREATE");
            }
        } else if (URI.matches(QUIZZES_EDIT)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "QUIZZES_EDIT")) {
                    throw new Exception("code - QUIZZES_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "QUIZZES_READ")) {
                    throw new Exception("code - QUIZZES_READ");
                }
            }
        } else if (URI.matches(QUIZZES_DELETE)) {
            if (!SecurityConfig.isAccess(request, "QUIZZES_DELETE")) {
                throw new Exception("code - QUIZZES_DELETE");
            }
        } else if (URI.matches(QUIZZES_DETAIL)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "QUIZZES_EDIT")) {
                    throw new Exception("code - QUIZZES_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "QUIZZES_READ")) {
                    throw new Exception("code - QUIZZES_READ");
                }
            }
        } else if (URI.matches(QUIZZES_LIST)) {
            if (!SecurityConfig.isAccess(request, "QUIZZES_READ")) {
                throw new Exception("code - SLIDER_READ");
            }
        }
    }
}

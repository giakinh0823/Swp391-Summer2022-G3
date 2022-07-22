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
public class UrlQuestionAdmin {

    public static final String URL_PATTERN = "^/admin/question(/|).*";
    public static final String QUESTION_LIST = "^/admin/question(/|)$";
    public static final String QUESTION_CREATE = "^/admin/question/create(/|)$";
    public static final String QUESTION_IMPORT = "^/admin/question/import(/|)$";
    public static final String QUESTION_DETAIL = "^/admin/question/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String QUESTION_EDIT = "^/admin/question/edit/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String QUESTION_DELETE = "^/admin/question/delete/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String QUESTION_DELETE_SELETED = "^/admin/question/delete-selected(/|)$";
    public static final String QUESTION_STATUS_CHANGE = "^/admin/question/status/change(/|)$";

    public static final String QUESTION_MEDIA_UPLOAD = "^/admin/question/media/uploads(/|)$";
    public static final String QUESTION_MEDIA_DELETE = "^/admin/question/media/delete(/|)$";

    public static final String QUESTION_ANSWER_SEARCH = "^/admin/question/answer/search(/|)$";
    public static final String QUESTION_ANSWER_GET = "^/admin/question/answer/get(/|)$";
    public static final String QUESTION_ANSWER_CREATE = "^/admin/question/answer/create(/|)$";
    public static final String QUESTION_ANSWER_DELETE = "^/admin/question/answer/delete(/|)$";
    public static final String QUESTION_ANSWER_EDIT = "^/admin/question/answer/edit(/|)$";

    private static UrlQuestionAdmin instance = new UrlQuestionAdmin();

    private UrlQuestionAdmin() {
    }

    public static UrlQuestionAdmin getInstance() {
        return instance;
    }

    public static void checkAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
        if (URI.matches(QUESTION_MEDIA_DELETE)) {
            if (!SecurityConfig.isAccess(request, "QUESTION_EDIT") && !SecurityConfig.isAccess(request, "QUESTION_CREATE")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - QUESTION_UPLOAD_MEDIA");
            }
        } else if (URI.matches(QUESTION_MEDIA_DELETE)) {
            if (!SecurityConfig.isAccess(request, "QUESTION_EDIT") && !SecurityConfig.isAccess(request, "QUESTION_CREATE")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - QUESTION_UPLOAD_MEDIA");
            }
        } else if (URI.matches(QUESTION_DELETE_SELETED)) {
            if (!SecurityConfig.isAccess(request, "QUESTION_DELETE")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - QUESTION_DELETE");
            }
        } else if (URI.matches(QUESTION_ANSWER_EDIT)) {
            if (!SecurityConfig.isAccess(request, "QUESTION_CREATE") && !SecurityConfig.isAccess(request, "QUESTION_EDIT")) {
                throw new Exception("code - ANSWER_EDIT");
            }
        } else if (URI.matches(QUESTION_ANSWER_DELETE)) {
            if (!SecurityConfig.isAccess(request, "QUESTION_READ") && !SecurityConfig.isAccess(request, "QUESTION_CREATE")) {
                throw new Exception("code - QUESTION_READ");
            }
        } else if (URI.matches(QUESTION_ANSWER_SEARCH)) {
            if (!SecurityConfig.isAccess(request, "QUESTION_READ") && !SecurityConfig.isAccess(request, "QUESTION_CREATE")) {
                throw new Exception("code - QUESTION_READ");
            }
        } else if (URI.matches(QUESTION_ANSWER_GET)) {
            if (!SecurityConfig.isAccess(request, "QUESTION_READ") && !SecurityConfig.isAccess(request, "QUESTION_CREATE")) {
                throw new Exception("code - QUESTION_READ");
            }
        } else if (URI.matches(QUESTION_ANSWER_CREATE)) {
            if (!SecurityConfig.isAccess(request, "QUESTION_CREATE")) {
                throw new Exception("code - QUESTION_CREATE");
            }
        } else if (URI.matches(QUESTION_STATUS_CHANGE)) {
            if (!SecurityConfig.isAccess(request, "QUESTION_EDIT") && !SecurityConfig.isAccess(request, "QUESTION_EDIT")) {
                throw new Exception("code - QUESTION_EDIT");
            }
        }else if (URI.matches(QUESTION_CREATE)) {
            if (!SecurityConfig.isAccess(request, "QUESTION_CREATE")) {
                throw new Exception("code - QUESTION_CREATE");
            }
        } else if (URI.matches(QUESTION_IMPORT)) {
            if (!SecurityConfig.isAccess(request, "QUESTION_CREATE")) {
                throw new Exception("code - QUESTION_CREATE");
            }
        } else if (URI.matches(QUESTION_EDIT)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "QUESTION_EDIT")) {
                    throw new Exception("code - QUESTION_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "QUESTION_READ")) {
                    throw new Exception("code - QUESTION_READ");
                }
            }
        } else if (URI.matches(QUESTION_DELETE)) {
            if (!SecurityConfig.isAccess(request, "QUESTION_DELETE")) {
                throw new Exception("code - QUESTION_DELETE");
            }
        } else if (URI.matches(QUESTION_DETAIL)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "QUESTION_EDIT")) {
                    throw new Exception("code - QUESTION_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "QUESTION_READ")) {
                    throw new Exception("code - QUESTION_READ");
                }
            }
        } else if (URI.matches(QUESTION_LIST)) {
            if (!SecurityConfig.isAccess(request, "QUESTION_READ")) {
                throw new Exception("code - QUESTION_READ");
            }
        }
    }
}

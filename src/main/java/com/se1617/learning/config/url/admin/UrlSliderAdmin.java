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
public class UrlSliderAdmin {

    public static final String URL_PATTERN = "^/admin/slider(/|).*";
    public static final String SLIDER_LIST = "^/admin/slider(/|)$";
    public static final String SLIDER_CREATE = "^/admin/slider/create(/|)$";
    public static final String SLIDER_DETAIL = "^/admin/slider/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SLIDER_EDIT = "^/admin/slider/edit/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SLIDER_DELETE = "^/admin/slider/delete/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SLIDER_STATUS_CHANGE = "^/admin/slider/status/change(/|)$";
    public static final String SLIDER_DELETEALL = "^/admin/slider/delete-selected(/|)$";
    
    private static UrlSliderAdmin instance = new UrlSliderAdmin();
    
    private UrlSliderAdmin() {}
    
    public static UrlSliderAdmin getInstance() {
        return instance;
    }

    public static void checkAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
        if (URI.matches(SLIDER_STATUS_CHANGE)) {
            if (!SecurityConfig.isAccess(request, "SLIDER_EDIT")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - SLIDER_EDIT");
            }
        } else if (URI.matches(SLIDER_CREATE)) {
            if (!SecurityConfig.isAccess(request, "SLIDER_CREATE")) {
                throw new Exception("code - SLIDER_CREATE");
            }
        } else if (URI.matches(SLIDER_EDIT)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "SLIDER_EDIT")) {
                    throw new Exception("code - SLIDER_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "SLIDER_READ")) {
                    throw new Exception("code - SLIDER_READ");
                }
            }
        } else if (URI.matches(SLIDER_DELETE)) {
            if (!SecurityConfig.isAccess(request, "SLIDER_DELETE")) {
                throw new Exception("code - SLIDER_DELETE");
            }
        } else if (URI.matches(SLIDER_DETAIL)) {
            if (!SecurityConfig.isAccess(request, "SLIDER_READ")) {
                throw new Exception("code - SLIDER_READ");
            }
        } else if (URI.matches(SLIDER_LIST)) {
            if (!SecurityConfig.isAccess(request, "SLIDER_READ")) {
                throw new Exception("code - SLIDER_READ");
            }
        }
    }
}

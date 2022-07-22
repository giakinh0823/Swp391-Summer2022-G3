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
public class UrlBlogClient {

    public static final String URL_PATTERN = "^/blog(/|).*";
    public static final String BLOG_LIST = "^/blog(/|)$";
    public static final String BLOG_DETAIL = "^/blog/[a-zA-Z0-9-]{1,}(/|)$";

    private static UrlBlogClient instance = new UrlBlogClient();

    private UrlBlogClient() {
    }

    public static UrlBlogClient getInstance() {
        return instance;
    }

    public static void checkAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
    }
}

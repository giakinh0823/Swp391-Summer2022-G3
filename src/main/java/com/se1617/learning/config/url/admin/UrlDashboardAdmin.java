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
public class UrlDashboardAdmin {

    public static final String URL_PATTERN = "^/admin/dashboard(/|).*";
    public static final String DASHBOARD = "^/admin/dashboard(/|)$";

    private static UrlDashboardAdmin instance = new UrlDashboardAdmin();

    private UrlDashboardAdmin() {
    }

    public static UrlDashboardAdmin getInstance() {
        return instance;
    }

    public static void checkAccess(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
        if (URI.matches(DASHBOARD)) {
            if (!SecurityConfig.isAccess(request, "DASHBOARD_READ")) {
                throw new Exception("code - DASHBOARD_READ");
            }
        }
    }
}

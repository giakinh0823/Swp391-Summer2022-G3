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
public class UrlSettingAdmin {

    public static final String URL_PATTERN = "^/admin/setting(/|).*";
    public static final String SETTING_LIST = "^/admin/setting(/|)$";
    public static final String SETTING_LIST_NO_PARENT = "^/admin/setting/no-parent(/|)$";
    public static final String SETTING_CREATE = "^/admin/setting/create(/|)$";
    public static final String SETTING_DETAIL = "^/admin/setting/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SETTING_EDIT = "^/admin/setting/edit/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SETTING_DELETE = "^/admin/setting/delete/[a-zA-Z0-9-]{1,}(/|)$";
    
    private static UrlSettingAdmin instance = new UrlSettingAdmin();
    
    private UrlSettingAdmin() {}
    
    public static UrlSettingAdmin getInstance() {
        return instance;
    }

    public static void checkAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
        if (URI.matches(UrlSettingAdmin.SETTING_EDIT)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "SETTING_EDIT")) {
                    throw new Exception("code - SETTING_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "SETTING_READ")) {
                    throw new Exception("code - SETTING_READ");
                }
            }
        } else if (URI.matches(UrlSettingAdmin.SETTING_DELETE)) {
            if (!SecurityConfig.isAccess(request, "SETTING_DELETE")) {
                throw new Exception("code - SETTING_DELETE");
            }
        } else if (URI.matches(UrlSettingAdmin.SETTING_CREATE)) {
            if (!SecurityConfig.isAccess(request, "SETTING_CREATE")) {
                throw new Exception("code - SETTING_CREATE");
            }
        } else if (URI.matches(UrlSettingAdmin.SETTING_DETAIL)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "SETTING_EDIT")) {
                    throw new Exception("code - SETTING_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "SETTING_READ")) {
                    throw new Exception("code - SETTING_READ");
                }
            }
        } else if (URI.matches(UrlSettingAdmin.SETTING_LIST)) {
            if (!SecurityConfig.isAccess(request, "SETTING_READ")) {
                throw new Exception("code - SETTING_READ");
            }
        }
    }
}

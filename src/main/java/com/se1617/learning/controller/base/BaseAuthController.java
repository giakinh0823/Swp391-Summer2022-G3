/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.controller.base;

import com.se1617.learning.model.user.Feature;
import com.se1617.learning.model.user.Group;
import com.se1617.learning.model.user.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giaki
 */
public abstract class BaseAuthController extends HttpServlet {

    private String URI = null;
    private List<String> paths = null;

    protected abstract boolean isPermission(HttpServletRequest request);

    private boolean isAuth(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return false;
        } else {
            return user.is_super() || isPermission(request);
        }
    }

    public List<String> getPaths() {
        return paths;
    }

    public String getURI() {
        return URI;
    }

    protected abstract void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    protected abstract void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (isAuth(request)) {
                String contextPath = request.getContextPath();
                URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
                String pathInfo = request.getPathInfo();
                if (pathInfo != null && !pathInfo.isEmpty()) {
                    this.paths = new ArrayList<>();
                    String[] paths = pathInfo.split("/");
                    for (String path : paths) {
                        if (!path.trim().isEmpty()) {
                            this.paths.add(path);
                        }
                    }
                }
                processGet(request, response);
            } else {
                request.getRequestDispatcher("/views/error/admin/access_denied.jsp").forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            request.getRequestDispatcher("/views/error/admin/500.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (isAuth(request)) {
                String contextPath = request.getContextPath();
                URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
                String pathInfo = request.getPathInfo();
                if (pathInfo != null && !pathInfo.isEmpty()) {
                    this.paths = new ArrayList<>();
                    String[] paths = pathInfo.split("/");
                    for (String path : paths) {
                        if (!path.trim().isEmpty()) {
                            this.paths.add(path);
                        }
                    }
                }
                processPost(request, response);
            } else {
                request.getRequestDispatcher("/views/error/admin/access_denied.jsp").forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            request.getRequestDispatcher("/views/error/admin/500.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

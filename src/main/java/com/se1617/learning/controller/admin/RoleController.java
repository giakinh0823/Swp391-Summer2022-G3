/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.admin;

import com.google.gson.Gson;
import com.se1617.learning.config.url.admin.UrlRoleAdmin;
import com.se1617.learning.controller.base.BaseAuthController;
import com.se1617.learning.dal.user.FeatureDBContext;
import com.se1617.learning.dal.user.GroupDBContext;
import com.se1617.learning.model.general.Message;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.user.Feature;
import com.se1617.learning.model.user.Group;
import com.se1617.learning.model.user.User;
import com.se1617.learning.utils.Validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giaki
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class RoleController extends BaseAuthController {

    private static final int pageSize = 12;

    @Override
    protected boolean isPermission(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user.is_staff();
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlRoleAdmin.ROLE_FEATURE_DELETEALL)) {
                featureDeleteAll(request, response);
            } else if (super.getURI().matches(UrlRoleAdmin.ROLE_FEATURE_DELETE)) {
                featureDelete(request, response);
            } else if (super.getURI().matches(UrlRoleAdmin.ROLE_FEATURE_EDIT)) {
                response.sendRedirect(request.getContextPath() + "/admin/role");
            } else if (super.getURI().matches(UrlRoleAdmin.ROLE_FEATURE_CREATE)) {
                featureCreateGet(request, response);
            } else if (super.getURI().matches(UrlRoleAdmin.ROLE_FEATURE_DETAIL)) {
                featureDetail(request, response);
            } else if (super.getURI().matches(UrlRoleAdmin.ROLE_EDIT)) {
                roleEditGet(request, response);
            } else if (super.getURI().matches(UrlRoleAdmin.ROLE_LIST)) {
                roleList(request, response);
            } else {
                request.getRequestDispatcher("/views/error/admin/404.jsp").forward(request, response);
            }
        } catch (IOException | ServletException e) {
            request.getRequestDispatcher("/views/error/admin/500.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("code", ex.getMessage());
            request.getRequestDispatcher("/views/error/admin/access_denied.jsp").forward(request, response);
        }
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlRoleAdmin.ROLE_FEATURE_DELETEALL)) {
                featureDeleteAll(request, response);
            } else if (super.getURI().matches(UrlRoleAdmin.ROLE_FEATURE_DELETE)) {
                featureDelete(request, response);
            } else if (super.getURI().matches(UrlRoleAdmin.ROLE_FEATURE_EDIT)) {
                featureEditPost(request, response);
            } else if (super.getURI().matches(UrlRoleAdmin.ROLE_FEATURE_CREATE)) {
                featureCreatePost(request, response);
            } else if (super.getURI().matches(UrlRoleAdmin.ROLE_FEATURE_DETAIL)) {
                featureDetail(request, response);
            } else if (super.getURI().matches(UrlRoleAdmin.ROLE_EDIT)) {
                roleEditPost(request, response);
            } else if (super.getURI().matches(UrlRoleAdmin.ROLE_LIST)) {
                roleList(request, response);
            } else {
                request.getRequestDispatcher("/views/error/admin/404.jsp").forward(request, response);
            }
        } catch (IOException | ServletException e) {
            request.getRequestDispatcher("/views/error/admin/500.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("code", ex.getMessage());
            request.getRequestDispatcher("/views/error/admin/access_denied.jsp").forward(request, response);
        }
    }

    //----------------- GET METHOD---------------//
    private void roleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String page = validate.getField(request, "page", false);
            if (page == null || page.trim().length() == 0) {
                page = "1";
            }
            int pageIndex = 1;
            try {
                pageIndex = validate.fieldInt(page, "Page size need a number!");
                if (pageIndex <= 0) {
                    pageIndex = 1;
                }
            } catch (Exception e) {
                pageIndex = 1;
            }

            String message = request.getParameter("message");
            String code = request.getParameter("code");
            String search = validate.getFieldAjax(request, "search", false);
            if (search == null) {
                search = "";
            }

            GroupDBContext groupDB = new GroupDBContext();
            ArrayList<Group> groups = groupDB.list();
            request.setAttribute("groups", groups);

            FeatureDBContext featureDB = new FeatureDBContext();
            ResultPageable<Feature> resultPageable = null;
            Map<String, String> orderMap = new HashMap<>();
            String[] sorts = validate.getFieldsAjax(request, "sort", false);
            if (sorts != null && sorts.length > 0) {
                int count = 0;
                for (String ordering : sorts) {
                    if (ordering != null && ordering.matches("^[a-zA-z]{1,}_(DESC|ASC)$")) {
                        String[] field = ordering.split("_", 2);
                        if (field[0].equalsIgnoreCase("id")
                                || field[0].equalsIgnoreCase("name")
                                || field[0].equalsIgnoreCase("feature")) {
                            orderMap.put(++count + "." + field[0], field[1]);
                        }
                    }
                }
            }
            Pageable pageable = new Pageable();
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(pageSize);
            pageable.setOrderings(orderMap);
            request.setAttribute("pageable", pageable);
            resultPageable = featureDB.searchByNameOrFeatureFilterMultiField(search, pageable);
            request.setAttribute("page", resultPageable.getPagination());
            request.setAttribute("features", resultPageable.getList());
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.getRequestDispatcher("/views/admin/role/role.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RoleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void roleEditGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(1);
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            int id = Integer.parseInt(paramId);
            GroupDBContext groupDB = new GroupDBContext();
            Group group = groupDB.get(id);
            if (group == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("group", group);

            FeatureDBContext featureDB = new FeatureDBContext();
            request.setAttribute("features", featureDB.list());
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.getRequestDispatcher("/views/admin/role/role_edit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Can't found role!");
        }
    }

    private void featureCreateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/views/admin/role/role_feature_create.jsp").forward(request, response);
    }

    private void featureDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(1);
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            int id = Integer.parseInt(paramId);
            FeatureDBContext featureDB = new FeatureDBContext();
            Feature feature = featureDB.get(id);
            if (feature == null) {
                throw new NumberFormatException();
            }

            request.setAttribute("feature", feature);
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.getRequestDispatcher("/views/admin/role/role_feature_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Can't found feature!");
        }
    }

    private void featureDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(2);
        try {
            int id = Integer.parseInt(paramId);
            FeatureDBContext featureDB = new FeatureDBContext();
            featureDB.delete(id);
            response.sendRedirect(request.getHeader("referer"));
        } catch (NumberFormatException e) {
            String message = "Delete feature failed!";
            String code = "error";
            response.sendRedirect(request.getContextPath() + "/admin/role?code=" + code + "&message=" + message);
        }
    }

    //----------------- POST METHOD---------------//
    private void roleEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(1);
        GroupDBContext groupDB = new GroupDBContext();
        List<Integer> listIds = new ArrayList<>();
        try {
            int idGroup = Integer.parseInt(paramId);
            Validate validate = new Validate();
            String[] ids = validate.getFieldsAjax(request, "features", false);
            if (ids != null) {
                for (String id : ids) {
                    try {
                        listIds.add(Integer.parseInt(id));
                    } catch (NumberFormatException e) {
                        String message = "List id must be number";
                        String code = "success";
                        response.sendRedirect(request.getContextPath() + "/admin/role/edit/" + paramId + "?code=" + code + "&message=" + message);
                        return;
                    }
                }
            }
            System.out.println(listIds.toString());

            Group group = groupDB.get(idGroup);
            if (group == null) {
                throw new NumberFormatException();
            }

            groupDB.updateFeatureGroup(group.getId(), listIds);
            String message = "Update role success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/role/edit/" + paramId + "?code=" + code + "&message=" + message);
        } catch (NumberFormatException e) {
            response.getWriter().println("Can't found role!");
        } catch (Exception ex) {
            String message = ex.getMessage();
            String code = "error";
            response.sendRedirect(request.getContextPath() + "/admin/role/edit/" + paramId + "?code=" + code + "&message=" + message);
        }

    }

    private void featureCreatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = null;
        String feature = null;
        try {
            Validate validate = new Validate();
            name = validate.getField(request, "name", true);
            feature = validate.getField(request, "feature", true);
            Feature model = new Feature();
            model.setName(name);
            model.setFeature(feature.toUpperCase());
            FeatureDBContext featureDB = new FeatureDBContext();
            featureDB.insert(model);
            String message = "Create feature sucess!";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/role?code=" + code + "&message=" + message);
        } catch (Exception ex) {
            request.setAttribute("name", new String(request.getParameter("name").getBytes("iso-8859-1"), "utf-8"));
            request.setAttribute("feature", new String(request.getParameter("feature").getBytes("iso-8859-1"), "utf-8"));
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/admin/role/role_feature_create.jsp").forward(request, response);
        }
    }

    private void featureEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(2);
        String name = null;
        String feature = null;
        try {
            Validate validate = new Validate();
            int id = Integer.parseInt(paramId);
            FeatureDBContext featureDB = new FeatureDBContext();
            Feature model = featureDB.get(id);
            request.setAttribute("feature", model);

            name = validate.getField(request, "name", true);
            model.setName(name);
            request.setAttribute("feature", model);
            feature = validate.getField(request, "feature", true);
            model.setFeature(feature);
            request.setAttribute("feature", model);
            featureDB.update(model);

            String message = "Update feature sucess!";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/role/feature/" + model.getId() + "?code=" + code + "&message=" + message);
        } catch (NumberFormatException e) {
            response.getWriter().println("Can't found feature!");
        } catch (Exception ex) {
            String message = ex.getMessage();
            String code = "error";
            response.sendRedirect(request.getContextPath() + "/admin/role/feature/" + paramId + "?code=" + code + "&message=" + message);
        }
    }

    private void featureDeleteAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String[] ids = validate.getFieldsAjax(request, "features[]", true);
            List<Integer> listIds = new ArrayList<>();
            FeatureDBContext featureDB = new FeatureDBContext();
            for (String id : ids) {
                try {
                    listIds.add(Integer.parseInt(id));
                } catch (NumberFormatException e) {
                    Message message = new Message();
                    message.setCode("error");
                    message.setMessage("List id must be number!");
                    String json = new Gson().toJson(message);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                    return;
                }
            }
            featureDB.deleteByIds(listIds);
            Message message = new Message();
            message.setCode("success");
            message.setMessage("Delete success");
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception ex) {
            Message message = new Message();
            message.setCode("error");
            message.setMessage(ex.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.admin;

import com.google.gson.Gson;
import com.se1617.learning.config.SettingTypeConfig;
import com.se1617.learning.config.url.admin.UrlSettingAdmin;
import com.se1617.learning.controller.base.BaseAuthController;
import com.se1617.learning.dal.setting.SettingDBContext;
import com.se1617.learning.model.general.Message;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.setting.Setting;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.se1617.learning.model.user.User;
import com.se1617.learning.utils.Validate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SettingController extends BaseAuthController {

    private static final int pageSize = 20;

    @Override
    protected boolean isPermission(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user.is_staff();
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlSettingAdmin.SETTING_DELETE)) {
                settingDelete(request, response);
            } else if (super.getURI().matches(UrlSettingAdmin.SETTING_EDIT)) {
                settingEditGet(request, response);
            } else if (super.getURI().matches(UrlSettingAdmin.SETTING_LIST_NO_PARENT)) {
                listNoParent(request, response);
            } else if (super.getURI().matches(UrlSettingAdmin.SETTING_CREATE)) {
                settingCreateGet(request, response);
            } else if (super.getURI().matches(UrlSettingAdmin.SETTING_DETAIL)) {
                settingDetail(request, response);
            } else if (super.getURI().matches(UrlSettingAdmin.SETTING_LIST)) {
                settingList(request, response);
            }
        } catch (IOException | ServletException e) {
            request.getRequestDispatcher("/views/error/admin/500.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("code", ex.getMessage());
            request.getRequestDispatcher("/views/error/admin/access_denied.jsp").forward(request, response);
        }
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlSettingAdmin.SETTING_DELETE)) {
                settingDelete(request, response);
            } else if (super.getURI().matches(UrlSettingAdmin.SETTING_EDIT)) {
                settingEditPost(request, response);
            } else if (super.getURI().matches(UrlSettingAdmin.SETTING_CREATE)) {
                settingCreatePost(request, response);
            } else if (super.getURI().matches(UrlSettingAdmin.SETTING_DETAIL)) {
                settingDetail(request, response);
            } else if (super.getURI().matches(UrlSettingAdmin.SETTING_LIST)) {
                settingList(request, response);
            }
        } catch (IOException | ServletException e) {
            request.getRequestDispatcher("/views/error/admin/500.jsp").forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("code", ex.getMessage());
            request.getRequestDispatcher("/views/error/admin/access_denied.jsp").forward(request, response);
        }
    }
    //--------------- GET METHOD---------------------//

    protected void settingList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("types", SettingTypeConfig.types);
        try {
            Validate validate = new Validate();
            String page = validate.getField(request, "page", false);
            if (page == null || page.trim().length() == 0) {
                page = "1";
            }
            int pageIndex = 1;
            try {
                pageIndex = validate.fieldInt(page, "Page size is a number!");
                if (pageIndex <= 0) {
                    pageIndex = 1;
                }
            } catch (Exception e) {
                pageIndex = 1;
            }

            String search = validate.getFieldAjax(request, "search", false);
            if (search == null) {
                search = "";
            }

            Pageable pageable = new Pageable();
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(pageSize);

            Map<String, String> orderMap = new HashMap<>();
            String[] sorts = validate.getFieldsAjax(request, "sort", false);
            if (sorts != null && sorts.length > 0) {
                int count = 0;
                for (String ordering : sorts) {
                    if (ordering != null && ordering.matches("^[a-zA-z]{1,}_(DESC|ASC)$")) {
                        String[] sort = ordering.split("_", 2);
                        if (sort[0].equalsIgnoreCase("id")
                                || sort[0].equalsIgnoreCase("type")
                                || sort[0].equalsIgnoreCase("value")
                                || sort[0].equalsIgnoreCase("order")
                                || sort[0].equalsIgnoreCase("status")
                                || sort[0].equalsIgnoreCase("parent")) {
                            orderMap.put(++count + "." + sort[0], sort[1]);
                        }
                    }
                }
            }
            pageable.setOrderings(orderMap);

            String[] status = validate.getFieldsAjax(request, "status", false);
            String[] types = validate.getFieldsAjax(request, "type", false);

            Map<String, ArrayList<String>> filters = new HashMap<>();
            if (status != null && status.length > 0) {
                ArrayList<String> listStatus = new ArrayList<>();
                for (String statu : status) {
                    if(statu.equalsIgnoreCase("active") || statu.equalsIgnoreCase("inactive")){
                        listStatus.add(statu);
                    }
                }
                if(!listStatus.isEmpty()){
                    filters.put("status", listStatus);
                }
            }
            if (types != null && types.length > 0) {
                ArrayList<String> listType = new ArrayList<>();
                Collections.addAll(listType, types);
                filters.put("type", listType);
            }
            
            pageable.setFilters(filters);

            SettingDBContext settingDB = new SettingDBContext();
            ResultPageable<Setting> resultPageable = null;

            resultPageable = settingDB.listPageable(search, pageable);

            request.setAttribute("settings", resultPageable.getList());
            request.setAttribute("page", resultPageable.getPagination());
            request.setAttribute("pageable", pageable);
            request.getRequestDispatcher("/views/admin/setting/setting.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listNoParent(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String code = validate.getField(request, "code", true);
            SettingDBContext settingDB = new SettingDBContext();
            ArrayList<Setting> settings = settingDB.listSettingNotPrarent(code);
            System.err.println(settings.toString());
            String json = new Gson().toJson(settings);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Message message = new Message();
            message.setCode("error");
            message.setMessage(ex.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            throw new Error(ex.getMessage());
        }
    }

    protected void settingCreateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("types", SettingTypeConfig.types);
        request.getRequestDispatcher("/views/admin/setting/setting_create.jsp").forward(request, response);
    }

    protected void settingDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String paramsId = super.getPaths().get(0);
            int id = Integer.parseInt(paramsId);

            SettingDBContext dbSetting = new SettingDBContext();
            Setting setting = dbSetting.get(id);

            request.setAttribute("parents", dbSetting.listSettingNotPrarent());
            request.setAttribute("setting", setting);
            request.setAttribute("types", SettingTypeConfig.types);
            request.getRequestDispatcher("/views/admin/setting/setting_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found setting!");
        }
    }

    protected void settingEditGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String paramsId = super.getPaths().get(1);
            int id = Integer.parseInt(paramsId);

            SettingDBContext dbSetting = new SettingDBContext();
            Setting setting = dbSetting.get(id);

            request.setAttribute("parents", dbSetting.listSettingNotPrarent());
            request.setAttribute("setting", setting);
            request.setAttribute("types", SettingTypeConfig.types);
            request.getRequestDispatcher("/views/admin/setting/setting_edit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found setting!");
        }
    }

    protected void settingDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramsId = super.getPaths().get(1);
        int id = Integer.parseInt(paramsId);
        SettingDBContext settingDBContext = new SettingDBContext();
        settingDBContext.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/setting");
    }

    //--------------- POST METHOD---------------------//
    protected void settingCreatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();

            String type = validate.getField(request, "type", true);
            String value = validate.getField(request, "value", true);
            String raw_order = validate.getField(request, "order", true);
            String raw_status = validate.getField(request, "status", true);
            String description = validate.getField(request, "description", false);
            String parent = validate.getField(request, "parent", false);

            int order = Integer.parseInt(raw_order);
            boolean status = raw_status.equals("status_true");

            // Construct Setting Object
            Setting setting = new Setting();
            setting.setType(type);
            setting.setValue(value);
            setting.setOrder(order);
            setting.setStatus(status);
            setting.setDescription(description);

            SettingDBContext dbSetting = new SettingDBContext();
            if (parent != null && !parent.equalsIgnoreCase("none") && !parent.trim().isEmpty()) {
                int id_parent = validate.fieldInt(parent, "Can't set field parent!");
                Setting parentSetting = dbSetting.get(id_parent);
                if (parentSetting == null) {
                    throw new Exception("Can't set field parent! Because not find parent!");
                }
                setting.setParent(id_parent);
                setting.setSetting(parentSetting);
            }
            dbSetting.insert(setting);

            response.sendRedirect(request.getContextPath() + "/admin/setting");
        } catch (Exception ex) {
            request.setAttribute("types", SettingTypeConfig.types);
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/admin/setting/setting_create.jsp").forward(request, response);
        }

    }

    protected void settingEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String paramsId = super.getPaths().get(1);
            int id = Integer.parseInt(paramsId);
            SettingDBContext dbSetting = new SettingDBContext();
            Setting setting = dbSetting.get(id);
            request.setAttribute("setting", setting);

            String type = validate.getField(request, "type", true);
            String value = validate.getField(request, "value", true);
            String raw_order = validate.getField(request, "order", true);
            String raw_status = validate.getField(request, "status", true);
            String description = validate.getField(request, "description", false);
            String parent = validate.getField(request, "parent", false);

            int order = Integer.parseInt(raw_order);
            boolean status = raw_status.equals("status_true");

            // Construct Setting Object
            setting.setId(id);
            setting.setType(type);
            setting.setValue(value);
            setting.setOrder(order);
            setting.setStatus(status);
            setting.setDescription(description);
            if (parent != null && !parent.equalsIgnoreCase("none") && !parent.trim().isEmpty()) {
                int id_parent = validate.fieldInt(parent, "Can't set field parent!");
                Setting parentSetting = dbSetting.get(id_parent);
                if (parentSetting == null) {
                    throw new Exception("Can't set field parent! Because not find parent!");
                }
                setting.setParent(id_parent);
                setting.setSetting(parentSetting);
            } else {
                setting.setSetting(null);
            }
            dbSetting.update(setting);

            response.sendRedirect(request.getContextPath() + "/admin/setting");
        } catch (Exception ex) {
            SettingDBContext dbSetting = new SettingDBContext();
            request.setAttribute("parents", dbSetting.listSettingNotPrarent("CATEGORY_POST"));
            request.setAttribute("types", SettingTypeConfig.types);
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/admin/setting/setting_edit.jsp").forward(request, response);
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

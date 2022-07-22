/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.admin;

import com.google.gson.Gson;
import com.se1617.learning.config.url.admin.UrlSubjectAdmin;
import com.se1617.learning.controller.base.BaseAuthController;
import com.se1617.learning.dal.setting.SettingDBContext;
import com.se1617.learning.dal.subject.DimensionDBContext;
import com.se1617.learning.dal.subject.PackagePriceDBContext;
import com.se1617.learning.dal.subject.SubjectDBContext;
import com.se1617.learning.model.general.Message;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.Res;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.subject.Dimension;
import com.se1617.learning.model.subject.PackagePrice;
import com.se1617.learning.model.subject.Subject;
import com.se1617.learning.model.user.User;
import com.se1617.learning.utils.FileUtility;
import com.se1617.learning.utils.Validate;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author giaki
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class SubjectController extends BaseAuthController {

    public static final int pageSize = 12;

    @Override
    protected boolean isPermission(HttpServletRequest request) {
        return ((User) request.getSession().getAttribute("user")).is_staff();
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_DELETE_SELETED)) {
                LessonController.lessonDeleteSelected(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_LIST_ALL)) {
                LessonController.searchLessonAll(request, response);
            }else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_SEARCH)) {
                LessonController.searchLesson(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_CREATE)) {
                LessonController.lessonCreateGet(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_EDIT)) {
                LessonController.lessonEditGet(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_DELETE)) {
                LessonController.lessonDelete(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_DETAIL)) {
                LessonController.lessonDetail(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_LIST)) {
                LessonController.lessonList(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DIMENSION_SEARCH)) {
                subjectDimensionSearch(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DIMENSION_DELETE)) {
                subjectDimensionDelete(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DIMENSION_EDIT)) {
                subjectDimensionEditGet(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DIMENSION_CREATE)) {
                subjectDimensionCreateGet(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DIMENSION_DETAIL)) {
                subjectDimensionDetail(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DIMENSION)) {
                subjectDimensionGet(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_PRICE_PACKAGE_STATUS_CHANGE)) {
                subjectPricePackageStatusChange(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_PRICE_PACKAGE_DELETE)) {
                subjectPricePackageDelete(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_PRICE_PACKAGE_CREATE)) {
                subjectPricePackageCreateGet(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_PRICE_PACKAGE_DETAIL)) {
                subjectPricePackageDetailGet(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_PRICE_PACKAGE)) {
                subjectPricePackageGet(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DELETE_SELECTED)) {
                subjectDeleteSelected(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_SEARCH)) {
                subjectSearch(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_EDIT)) {
                subjectEditGet(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_CREATE)) {
                subjectCreateGet(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DELETE)) {
                subjectDelete(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_STATUS_CHANGE)) {
                subjectStatusChange(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_FEATURE_CHANGE)) {
                subjectFeatureChange(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DETAIL)) {
                subjectDetailGet(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LIST)) {
                subjectList(request, response);
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
            if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_MEDIA_UPLOAD)) {
                LessonController.mediaUpload(request, response);
            }else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_MEDIA_DELETE)) {
                LessonController.mediaDelete(request, response);
            }else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_DELETE_SELETED)) {
                LessonController.lessonDeleteSelected(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_CREATE)) {
                LessonController.lessonCreatePost(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_EDIT)) {
                LessonController.lessonEditPost(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_DELETE)) {
                LessonController.lessonDelete(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_DETAIL)) {
                LessonController.lessonDetail(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LESSON_LIST)) {
                LessonController.lessonList(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DIMENSION_DELETE)) {
                subjectDimensionDelete(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DIMENSION_EDIT)) {
                subjectDimensionEditPost(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DIMENSION_CREATE)) {
                subjectDimensionCreatePost(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DIMENSION_DETAIL)) {
                subjectDimensionDetail(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DIMENSION)) {
                subjectDimensionPost(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_PRICE_PACKAGE_STATUS_CHANGE)) {
                subjectPricePackageStatusChange(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_PRICE_PACKAGE_DELETE)) {
                subjectPricePackageDelete(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_PRICE_PACKAGE_CREATE)) {
                subjectPricePackageCreatePost(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_PRICE_PACKAGE_DETAIL)) {
                subjectPricePackageDetailPost(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_PRICE_PACKAGE)) {
                subjectPricePackagePost(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DELETE_SELECTED)) {
                subjectDeleteSelected(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_EDIT)) {
                subjectEditPost(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_CREATE)) {
                subjectCreatePost(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DELETE)) {
                subjectDelete(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_STATUS_CHANGE)) {
                subjectStatusChange(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_FEATURE_CHANGE)) {
                subjectFeatureChange(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_DETAIL)) {
                subjectDetailPost(request, response);
            } else if (super.getURI().matches(UrlSubjectAdmin.SUBJECT_LIST)) {
                subjectList(request, response);
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

    //---------------------- GET METHOD ----------------------//
    private void subjectDeleteSelected(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void subjectCreateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_SUBJECT"));
            request.getRequestDispatcher("/views/admin/subject/subject_create.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().println("Can't load categories subject!");
        }

    }

    private void subjectEditGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(1);
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            int id = Integer.parseInt(paramId);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(id);
            if (subject == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("subject", subject);

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_SUBJECT"));

            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.setAttribute("backlink", request.getContextPath() + "/admin/subject");
            request.getRequestDispatcher("/views/admin/subject/subject_edit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Can't found subject!");
        }
    }

    private void subjectDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(1);
        try {
            int id = Integer.parseInt(paramId);
            SubjectDBContext subjectDB = new SubjectDBContext();
            subjectDB.delete(id);
            if (request.getHeader("referer").toLowerCase().contains("/admin/subject/" + paramId) && !request.getHeader("referer").toLowerCase().contains("/edit/")) {
                response.sendRedirect(request.getContextPath() + "/admin/subject");
            } else {
                response.sendRedirect(request.getHeader("referer"));
            }
        } catch (NumberFormatException e) {
            String message = "Not found post!";
            String code = "error";
            response.sendRedirect(request.getContextPath() + "/admin/subject?code=" + code + "&message=" + message);
        }
    }

    private void subjectSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String page = validate.getFieldAjax(request, "page", false);
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
            String search = validate.getFieldAjax(request, "search", false);
            if (search == null) {
                search = "";
            }

            User user = (User) request.getSession().getAttribute("user");

            SubjectDBContext subjectDB = new SubjectDBContext();
            ArrayList<Subject> subjects = new ArrayList<>();
            if (user.is_super() || user.checkFeature("SUBJECT_READ_ALL")) {
                subjects = subjectDB.searchSubject(search, pageIndex, 30);
            } else {
                subjects = subjectDB.searchSubject(user.getId(), search, pageIndex, 30);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            Res<Subject> res = new Res<Subject>(subjects, pageIndex, 30);
            String json = new Gson().toJson(res);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Message message = new Message();
            message.setCode("error");
            message.setMessage(ex.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }

    private void subjectList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            // get category
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_SUBJECT"));

            // pagination
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
            ResultPageable<Subject> resultPageable = null;
            SubjectDBContext subjectDBContext = new SubjectDBContext();
            // search 
            String search = validate.getFieldAjax(request, "search", false);
            if (search == null) {
                search = "";
            }
            // sort
            Pageable pageable = new Pageable();
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(pageSize);

            Map<String, String> orderMap = new HashMap<>();
            String[] sorts = validate.getFieldsAjax(request, "sort", false);
            if (sorts != null && sorts.length > 0) {
                int count = 0;
                for (String sort : sorts) {
                    if (sort != null && sort.matches("^[a-zA-z]{1,}_(DESC|ASC)$")) {
                        String[] ordering = sort.split("_", 2);
                        if (ordering[0].equalsIgnoreCase("id")
                                || ordering[0].equalsIgnoreCase("name")
                                || ordering[0].equalsIgnoreCase("category")
                                || ordering[0].equalsIgnoreCase("status")
                                || ordering[0].equalsIgnoreCase("description")
                                || ordering[0].equalsIgnoreCase("featured")
                                || ordering[0].equalsIgnoreCase("author")
                                || ordering[0].equalsIgnoreCase("created")
                                || ordering[0].equalsIgnoreCase("updated")) {
                            orderMap.put(++count + "." + ordering[0], ordering[1]);
                        }
                    }
                }
            }
            pageable.setOrderings(orderMap);
            // filter 
            String[] categories = validate.getFieldsAjax(request, "category", false);
            String[] status = validate.getFieldsAjax(request, "status", false);
            String[] features = validate.getFieldsAjax(request, "featured", false);
            Map<String, ArrayList<String>> filters = new HashMap<>();

            if (categories != null && categories.length > 0) {
                ArrayList<String> listCategories = new ArrayList<>();
                for (String category : categories) {
                    if (category.matches("^[0-9]+$")) {
                        listCategories.add(category);
                    }
                }
                if (!listCategories.isEmpty()) {
                    filters.put("category", listCategories);
                }
            }
            if (status != null && status.length > 0) {
                ArrayList<String> listStatus = new ArrayList<>();
                for (String stt : status) {
                    if (stt.equalsIgnoreCase("true") || stt.equalsIgnoreCase("false")) {
                        listStatus.add(stt);
                    }
                }
                if (!listStatus.isEmpty()) {
                    filters.put("status", listStatus);
                }
            }

            if (features != null && features.length > 0) {
                ArrayList<String> listFeatures = new ArrayList<>();
                for (String feature : features) {
                    if (feature.equalsIgnoreCase("true") || feature.equalsIgnoreCase("false")) {
                        listFeatures.add(feature);
                    }
                }
                if (!listFeatures.isEmpty()) {
                    filters.put("featured", listFeatures);
                }
            }
            pageable.setFilters(filters);

            // get session user
            User user = (User) request.getSession().getAttribute("user");

            if (user.is_super() || user.checkFeature("SUBJECT_READ_ALL")) {
                resultPageable = subjectDBContext.listBySearchBySortByFilter(search, pageable);
            } else {
                resultPageable = subjectDBContext.listByUseridPageable(user.getId(), search, pageable);
            }
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.setAttribute("subjects", resultPageable.getList());
            request.setAttribute("page", resultPageable.getPagination());
            request.setAttribute("pageable", pageable);
            request.getRequestDispatcher("/views/admin/subject/subject.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SubjectController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void subjectDetailGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(0);
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            int id = Integer.parseInt(paramId);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(id);
            if (subject == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("subject", subject);

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_SUBJECT"));

            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.setAttribute("backlink", request.getContextPath() + "/admin/subject");
            request.getRequestDispatcher("/views/admin/subject/subject_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Can't found subject!");
        }
    }

    private void subjectDimensionGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(0);
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            int id = Integer.parseInt(paramId);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(id);

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

            if (subject == null) {
                throw new NumberFormatException();
            }
            DimensionDBContext dimensionDB = new DimensionDBContext();
            Pageable pageable = new Pageable();
            pageable.setPageSize(12);
            pageable.setPageIndex(pageIndex);
            ResultPageable<Dimension> resultPageable = dimensionDB.findBySubjectIdPageable(subject.getId(), pageable);
            request.setAttribute("dimensions", resultPageable.getList());
            request.setAttribute("page", resultPageable.getPagination());
            request.setAttribute("subject", subject);
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.getRequestDispatcher("/views/admin/subject/dimension.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found subject!");
        } catch (Exception ex) {
            response.getWriter().println("Page not found!");
        }
    }

    private void subjectPricePackageGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(0);
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            int id = Integer.parseInt(paramId);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(id);

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

            if (subject == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("subject", subject);

            PackagePriceDBContext packageDB = new PackagePriceDBContext();
            Pageable pageable = new Pageable();
            pageable.setPageSize(12);
            pageable.setPageIndex(pageIndex);
            ResultPageable<PackagePrice> resultPageable = packageDB.findBySubjectIdPageable(subject.getId(), pageable);
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.setAttribute("packages", resultPageable.getList());
            request.setAttribute("page", resultPageable.getPagination());
            request.getRequestDispatcher("/views/admin/subject/price_package.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Can't found subject!");
        } catch (Exception ex) {
            response.getWriter().println("Not found page!");
        }
    }

    private void subjectDimensionCreateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(0);
        try {
            int id = Integer.parseInt(paramId);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(id);
            if (subject == null) {
                throw new NumberFormatException();
            }
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("types", settingDB.getByType("TYPE_DIMENSION"));
            request.setAttribute("subject", subject);
            request.getRequestDispatcher("/views/admin/subject/dimension_create.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Can't found subject!");
        }
    }

    private void subjectPricePackageCreateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(0);
        try {
            int id = Integer.parseInt(paramId);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(id);

            if (subject == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("subject", subject);
            request.getRequestDispatcher("/views/admin/subject/price_package_create.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found subject!");
        }
    }

    private void subjectPricePackageDetailGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramIdSubject = super.getPaths().get(0);
        String paramId = super.getPaths().get(2);
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            int idSubject = Integer.parseInt(paramIdSubject);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            request.setAttribute("subject", subject);
            
            int id = Integer.parseInt(paramId);
            PackagePriceDBContext packagePriceDB = new PackagePriceDBContext();
            PackagePrice packagePrice = packagePriceDB.get(id);
            if (packagePrice == null || subject == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("packagePrice", packagePrice);
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.getRequestDispatcher("/views/admin/subject/price_package_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found price package!");
        }
    }

    private void subjectDimensionDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramIdSubject = super.getPaths().get(0);
        String paramId = super.getPaths().get(2);
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            int idSubject = Integer.parseInt(paramIdSubject);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            request.setAttribute("subject", subject);

            int id = Integer.parseInt(paramId);
            DimensionDBContext dimensionDB = new DimensionDBContext();
            Dimension dimension = dimensionDB.get(id);
            if (dimension == null || subject == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("dimension", dimension);
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("types", settingDB.getByType("TYPE_DIMENSION"));
            request.getRequestDispatcher("/views/admin/subject/dimension_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found dimension!");
        }
    }

    private void subjectDimensionSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String subjectId = validate.getFieldAjax(request, "subjectId", true);
            String typeId = validate.getFieldAjax(request, "typeId", false);
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
            int id = Integer.parseInt(subjectId);

            DimensionDBContext dimensionDB = new DimensionDBContext();
            ArrayList<Dimension> dimensions = new ArrayList<>();
            if (typeId != null && typeId.matches("^[0-9]+$")) {
                int idType = Integer.parseInt(typeId);
                dimensions = dimensionDB.findBySubjectAndType(id, idType);
            } else {
                dimensions = dimensionDB.findBySubjectId(id);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            Res<Dimension> res = new Res(dimensions, pageIndex, 10);
            String json = new Gson().toJson(dimensions);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Message message = new Message();
            message.setCode("error");
            message.setMessage(ex.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }
    
    private void subjectDimensionEditGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramIdSubject = super.getPaths().get(0);
        String paramId = super.getPaths().get(3);
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            int idSubject = Integer.parseInt(paramIdSubject);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            request.setAttribute("subject", subject);

            int id = Integer.parseInt(paramId);
            DimensionDBContext dimensionDB = new DimensionDBContext();
            Dimension dimension = dimensionDB.get(id);
            if (dimension == null || subject == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("dimension", dimension);
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("types", settingDB.getByType("TYPE_DIMENSION"));
            request.getRequestDispatcher("/views/admin/subject/dimension_edit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found dimension!");
        }
    }

    //--------------------- POST METHOD -----------------------//
    private void subjectDetailPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(0);
        try {
            int id = Integer.parseInt(paramId);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(id);

            Validate validate = new Validate();
            String name = validate.getField(request, "name", true);
            String category = validate.getField(request, "category", true);
            String status = validate.getField(request, "status", false);
            String featured = validate.getField(request, "featured", false);
            String description = validate.getField(request, "description", false);

            boolean isStatus = status != null && status.equalsIgnoreCase("published");
            boolean isFeatured = featured != null && featured.equalsIgnoreCase("true");
            int idCategory = validate.fieldInt(category, "Set field category error!");

            User user = (User) request.getSession().getAttribute("user");

            SettingDBContext settingDB = new SettingDBContext();
            Setting setting = settingDB.get(idCategory);
            if (setting == null) {
                throw new Exception("Error set field category!");
            }

            subject.setName(name);
            subject.setCategoryId(setting.getId());
            subject.setCategory(setting);
            subject.setFeatured(isFeatured);
            subject.setDescription(description);
            subject.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            if (user.checkFeature("SUBJECT_CHANGE_STATUS")) {
                subject.setStatus(isStatus);
            }

            Part part = request.getPart("image");
            FileUtility fileUtility = new FileUtility();

            String folder = request.getServletContext().getRealPath("/images/subject");
            String filename = null;

            if (part.getSize() != 0) {
                if (subject.getImage() != null && !subject.getImage().isEmpty()) {
                    fileUtility.delete(subject.getImage(), folder);
                }
                filename = fileUtility.upLoad(part, folder);
            }

            if (filename != null) {
                subject.setImage(filename);
            }
            subjectDB.update(subject);
            String message = "Update subject success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/subject/" + subject.getId() + "?code=" + code + "&message=" + message);
        } catch (NumberFormatException ex) {
            response.getWriter().println("Can't found subject!");
        } catch (Exception e) {
            try {
                int id = Integer.parseInt(paramId);
                SubjectDBContext subjectDB = new SubjectDBContext();
                Subject subject = subjectDB.get(id);
                request.setAttribute("subject", subject);
                SettingDBContext settingDB = new SettingDBContext();
                request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_SUBJECT"));
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/views/admin/subject/subject_detail.jsp").forward(request, response);
            } catch (IOException | NumberFormatException | ServletException exx) {
                response.getWriter().println("Can't found subject!");
            }
        }
    }

    private void subjectCreatePost(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            User user = (User) request.getSession().getAttribute("user");
            Validate validate = new Validate();
            String name = validate.getField(request, "name", true);
            String category = validate.getField(request, "category", true);
            String featured = validate.getField(request, "featured", false);
            String status = validate.getField(request, "status", false);
            Part part = request.getPart("image");
            FileUtility fileUtility = new FileUtility();
            String folder = request.getServletContext().getRealPath("/images/subject");
            String filename = null;
            if (part.getSize() != 0) {
                filename = fileUtility.upLoad(part, folder);
            } else {
                throw new Exception("Thumbnail is required!");
            }
            String description = validate.getField(request, "description", false);

            boolean isStatus = status != null && status.equalsIgnoreCase("published");
            boolean isFeatured = featured != null && featured.equalsIgnoreCase("true");
            int idCategory = validate.fieldInt(category, "Set field category error!");

            SettingDBContext settingDB = new SettingDBContext();
            Setting setting = settingDB.get(idCategory);
            if (setting == null) {
                throw new Exception("Error set field category!");
            }
            Subject subject = new Subject();
            subject.setName(name);
            subject.setDescription(description);
            subject.setCategoryId(setting.getId());
            subject.setCategory(setting);
            subject.setFeatured(isFeatured);
            subject.setStatus(false);
            subject.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            subject.setCreated_at(new Timestamp(System.currentTimeMillis()));
            if (filename != null) {
                subject.setImage(filename);
            }

            subject.setUser(user);
            subject.setUserId(user.getId());
            SubjectDBContext subjectDB = new SubjectDBContext();
            subjectDB.insert(subject);
            String message = "Create subject success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/subject?code=" + code + "&message=" + message);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_SUBJECT"));
            request.getRequestDispatcher("/views/admin/subject/subject_create.jsp").forward(request, response);
        }

    }

    private void subjectDimensionPost(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void subjectPricePackagePost(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void subjectDimensionCreatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(0);
        try {
            int id = Integer.parseInt(paramId);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(id);
            request.setAttribute("subject", subject);

            if (subject == null) {
                throw new NumberFormatException();
            }

            Validate validate = new Validate();
            String name = validate.getField(request, "name", true);
            String type = validate.getField(request, "type", true);
            String description = validate.getField(request, "description", false);

            int typeId = Integer.parseInt(type);

            SettingDBContext settingDB = new SettingDBContext();
            if (settingDB.get(typeId) == null) {
                throw new NumberFormatException();
            }

            Dimension dimension = new Dimension();
            dimension.setName(name);
            dimension.setTypeId(typeId);
            dimension.setDescription(description);
            dimension.setSubject(subject);
            dimension.setSubjectId(subject.getId());

            DimensionDBContext dimensionDB = new DimensionDBContext();
            dimensionDB.insert(dimension);
            String message = "Create dimension success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/subject/" + subject.getId() + "/dimension?code=" + code + "&message=" + message);
        } catch (NumberFormatException ex) {
            response.getWriter().println("Can't found id!");
        } catch (Exception e) {
            String nameParam = request.getParameter("name");
            if (nameParam != null) {
                String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "utf-8");
                request.setAttribute("name", name);
            }
            request.setAttribute("status", request.getParameter("status"));
            request.setAttribute("type", request.getParameter("type"));
            if (request.getParameter("description") != null) {
                request.setAttribute("description", new String(request.getParameter("description").getBytes("iso-8859-1"), "UTF-8"));
            }
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("types", settingDB.getByType("TYPE_DIMENSION"));
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/admin/subject/dimension_create.jsp").forward(request, response);
        }
    }

    private void subjectPricePackageCreatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(0);
        try {
            int id = Integer.parseInt(paramId);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(id);

            if (subject == null) {
                throw new NumberFormatException();
            }

            Validate validate = new Validate();
            String name = validate.getField(request, "name", true);
            String durationParam = validate.getField(request, "duration", false);
            String listPriceParam = validate.getField(request, "list_price", true);
            String salePriceParam = validate.getField(request, "sale_price", false);
            String description = validate.getField(request, "description", false);
            PackagePrice packagePrice = new PackagePrice();
            packagePrice.setName(name);
            if (durationParam != null && !durationParam.trim().isEmpty()) {
                int duration = validate.fieldInt(durationParam, "Duration must be a number");
                if (duration < 0) {
                    throw new Exception("Duration must be greater than or equal to 0");
                }
                packagePrice.setDuration(duration);
            }
            double list_price = validate.fieldDouble(listPriceParam, "List price must be a number");
            if (list_price < 0) {
                throw new Exception("List price must be greater than or equal to 0");
            }
            packagePrice.setList_price(list_price);
            if (salePriceParam != null && !salePriceParam.trim().isEmpty()) {
                double sale_price = validate.fieldDouble(salePriceParam, "Sale price must be a number");
                if (sale_price < 0) {
                    throw new Exception("Sale price must be greater than or equal to 0");
                }
                if (sale_price > list_price) {
                    throw new Exception("Sale price must less than list price");
                }
                packagePrice.setSale_price(sale_price);
            }
            packagePrice.setDescription(description);
            packagePrice.setSubjectId(subject.getId());
            packagePrice.setSubject(subject);
            PackagePriceDBContext packagePriceDB = new PackagePriceDBContext();
            packagePriceDB.insert(packagePrice);
            String message = "Create price package success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/subject/" + subject.getId() + "/price-package?code=" + code + "&message=" + message);
        } catch (NumberFormatException ex) {
            response.getWriter().println("Can't found subject!");
        } catch (Exception e) {
            String nameParam = request.getParameter("name");
            if (nameParam != null) {
                String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "utf-8");
                request.setAttribute("name", name);
            }
            request.setAttribute("duration", request.getParameter("duration"));
            request.setAttribute("list_price", request.getParameter("list_price"));
            request.setAttribute("sale_price", request.getParameter("sale_price"));
            if (request.getParameter("description") != null) {
                request.setAttribute("description", new String(request.getParameter("description").getBytes("iso-8859-1"), "UTF-8"));
            }
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/admin/subject/price_package_create.jsp").forward(request, response);
        }
    }

    private void subjectPricePackageDetailPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramIdSubject = super.getPaths().get(0);
        String paramId = super.getPaths().get(2);
        try {
            int idSubject = Integer.parseInt(paramIdSubject);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);

            int id = Integer.parseInt(paramId);
            PackagePriceDBContext packagePriceDB = new PackagePriceDBContext();
            PackagePrice packagePrice = packagePriceDB.get(id);

            if (packagePrice == null || subject == null) {
                throw new NumberFormatException();
            }

            Validate validate = new Validate();
            String name = validate.getField(request, "name", true);
            String durationParam = validate.getField(request, "duration", false);
            String listPriceParam = validate.getField(request, "list_price", true);
            String salePriceParam = validate.getField(request, "sale_price", false);
            String description = validate.getField(request, "description", false);
            String status = validate.getField(request, "status", false);
            packagePrice.setName(name);
            Integer duration = null;
            if (durationParam != null && !durationParam.trim().isEmpty()) {
                duration = validate.fieldInt(durationParam, "Duration must be a number");
                if (duration < 0) {
                    throw new Exception("Duration must be greater than or equal to 0");
                }
                packagePrice.setDuration(duration);
            } else {
                packagePrice.setDuration(0);
            }
            double list_price = validate.fieldDouble(listPriceParam, "List price must be a number");
            if (list_price < 0) {
                throw new Exception("List price must be greater than or equal to 0");
            }
            packagePrice.setList_price(list_price);
            Double sale_price = null;
            if (salePriceParam != null && !salePriceParam.trim().isEmpty()) {
                sale_price = validate.fieldDouble(salePriceParam, "Sale price must be a number");
                if (sale_price < 0) {
                    throw new Exception("Sale price must be greater than or equal to 0");
                }
                if (sale_price > list_price) {
                    throw new Exception("Sale price must less than list price");
                }
                packagePrice.setSale_price(sale_price);
            } else {
                packagePrice.setSale_price(0);
            }
            packagePrice.setDescription(description);
            packagePrice.setSubjectId(subject.getId());
            packagePrice.setSubject(subject);
            packagePrice.setStatus(status != null && status.equalsIgnoreCase("active"));
            packagePriceDB.update(packagePrice);
            String message = "Update price package success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/subject/" + subject.getId() + "/price-package" + "?code=" + code + "&message=" + message);
        } catch (NumberFormatException ex) {
            response.getWriter().println("Can't found id!");
        } catch (Exception e) {
            int id = Integer.parseInt(paramId);
            PackagePriceDBContext packagePriceDB = new PackagePriceDBContext();
            PackagePrice packagePrice = packagePriceDB.get(id);
            request.setAttribute("packagePrice", packagePrice);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/admin/subject/price_package_detail.jsp").forward(request, response);
        }
    }

    private void subjectPricePackageDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramIdSubject = super.getPaths().get(0);
        String paramId = super.getPaths().get(3);
        try {
            int idSubject = Integer.parseInt(paramIdSubject);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);

            if (subject == null) {
                throw new NumberFormatException();
            }

            int id = Integer.parseInt(paramId);
            PackagePriceDBContext packagePriceDB = new PackagePriceDBContext();
            packagePriceDB.delete(id);
            String message = "Delete price package success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/subject/" + subject.getId() + "/price-package?code=" + code + "&message=" + message);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found id!");
        }
    }

    private void subjectDimensionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramIdSubject = super.getPaths().get(0);
        String paramId = super.getPaths().get(3);
        try {
            int idSubject = Integer.parseInt(paramIdSubject);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);

            if (subject == null) {
                throw new NumberFormatException();
            }

            int id = Integer.parseInt(paramId);
            DimensionDBContext dimensionDB = new DimensionDBContext();
            dimensionDB.delete(id);
            String message = "Delete dimension success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/subject/" + subject.getId() + "/dimension?code=" + code + "&message=" + message);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found id!");
        }
    }

    private void subjectPricePackageStatusChange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idSlider = validate.getFieldAjax(request, "id", true);
            String statusSlider = validate.getFieldAjax(request, "status", true);

            int id = validate.fieldInt(idSlider, "Id slider must be a number!");
            boolean status = validate.fieldBoolean(statusSlider, "Status slider must be a true or false!");

            PackagePriceDBContext packagePriceDB = new PackagePriceDBContext();
            PackagePrice packagePrice = packagePriceDB.get(id);
            packagePrice.setStatus(status);
            packagePriceDB.update(packagePrice);
            Message message = new Message();
            message.setCode("success");
            message.setMessage("Update status price package success!");
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            Message message = new Message();
            message.setCode("error");
            message.setMessage(e.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }

    private void subjectStatusChange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idParam = validate.getFieldAjax(request, "id", true);
            String statusParam = validate.getFieldAjax(request, "status", true);

            int id = validate.fieldInt(idParam, "Id subject must be a number!");
            boolean status = validate.fieldBoolean(statusParam, "Public subject must be a true or false!");

            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(id);

            PackagePriceDBContext priceDB = new PackagePriceDBContext();
            ArrayList<PackagePrice> packagePrices = priceDB.findBySubjectIdAndActive(subject.getId());
            if (status && (packagePrices == null || packagePrices.isEmpty())) {
                throw new Exception("Plese add price package and active for subject!");
            }
            subject.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            subject.setStatus(status);
            subjectDB.update(subject);
            Message message = new Message();
            message.setCode("success");
            message.setMessage("Update public subject success!");
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Message message = new Message();
            message.setCode("error");
            message.setMessage(e.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }

    private void subjectFeatureChange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idParam = validate.getFieldAjax(request, "id", true);
            String featureParam = validate.getFieldAjax(request, "feature", true);

            int id = validate.fieldInt(idParam, "Id subject must be a number!");
            boolean feature = validate.fieldBoolean(featureParam, "Feature subject must be a true or false!");

            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(id);
            subject.setFeatured(feature);
            subjectDB.update(subject);
            Message message = new Message();
            message.setCode("success");
            message.setMessage("Update feature subject success!");
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            Message message = new Message();
            message.setCode("error");
            message.setMessage(e.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }

    private void subjectEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(1);
        try {
            int id = Integer.parseInt(paramId);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(id);

            Validate validate = new Validate();
            String name = validate.getField(request, "name", true);
            String category = validate.getField(request, "category", true);
            String status = validate.getField(request, "status", false);
            String featured = validate.getField(request, "featured", false);
            String description = validate.getField(request, "description", false);

            boolean isStatus = status != null && status.equalsIgnoreCase("published");
            boolean isFeatured = featured != null && featured.equalsIgnoreCase("true");
            int idCategory = validate.fieldInt(category, "Set field category error!");

            User user = (User) request.getSession().getAttribute("user");

            SettingDBContext settingDB = new SettingDBContext();
            Setting setting = settingDB.get(idCategory);
            if (setting == null) {
                throw new Exception("Error set field category!");
            }

            subject.setName(name);
            subject.setCategoryId(setting.getId());
            subject.setCategory(setting);
            subject.setFeatured(isFeatured);
            subject.setDescription(description);
            subject.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            if (isStatus) {
                PackagePriceDBContext priceDB = new PackagePriceDBContext();
                ArrayList<PackagePrice> packagePrices = priceDB.findBySubjectIdAndActive(subject.getId());
                if (packagePrices == null || packagePrices.isEmpty()) {
                    throw new Exception("If subject public! Plese add price package and active for subject!");
                }
            }

            if (user.checkFeature("SUBJECT_CHANGE_STATUS")) {
                subject.setStatus(isStatus);
            }

            Part part = request.getPart("image");
            FileUtility fileUtility = new FileUtility();

            String folder = request.getServletContext().getRealPath("/images/subject");
            String filename = null;

            if (part.getSize() != 0) {
                if (subject.getImage() != null && !subject.getImage().isEmpty()) {
                    fileUtility.delete(subject.getImage(), folder);
                }
                filename = fileUtility.upLoad(part, folder);
            }

            if (filename != null) {
                subject.setImage(filename);
            }
            subjectDB.update(subject);
            String message = "Update subject success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/subject/" + subject.getId() + "?code=" + code + "&message=" + message);
        } catch (NumberFormatException ex) {
            response.getWriter().println("Can't found subject!");
        } catch (Exception e) {
            try {
                int id = Integer.parseInt(paramId);
                SubjectDBContext subjectDB = new SubjectDBContext();
                Subject subject = subjectDB.get(id);
                request.setAttribute("subject", subject);
                SettingDBContext settingDB = new SettingDBContext();
                request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_SUBJECT"));
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/views/admin/subject/subject_detail.jsp").forward(request, response);
            } catch (IOException | NumberFormatException | ServletException exx) {
                response.getWriter().println("Can't found subject!");
            }
        }
    }
    
    

    private void subjectDimensionEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramIdSubject = super.getPaths().get(0);
        String paramId = super.getPaths().get(3);
        try {
            int idSubject = Integer.parseInt(paramIdSubject);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            request.setAttribute("subject", subject);

            int id = Integer.parseInt(paramId);
            DimensionDBContext dimensionDB = new DimensionDBContext();
            Dimension dimension = dimensionDB.get(id);

            if (dimension == null || subject == null) {
                throw new NumberFormatException();
            }

            Validate validate = new Validate();
            String name = validate.getField(request, "name", true);
            String type = validate.getField(request, "type", true);
            String description = validate.getField(request, "description", false);

            int typeId = Integer.parseInt(type);

            SettingDBContext settingDB = new SettingDBContext();
            if (settingDB.get(typeId) == null) {
                throw new NumberFormatException();
            }

            dimension.setName(name);
            dimension.setTypeId(typeId);
            dimension.setDescription(description);
            dimension.setSubject(subject);
            dimension.setSubjectId(subject.getId());

            dimensionDB.update(dimension);
            String message = "Update dimension success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/subject/" + subject.getId() + "/dimension/" + dimension.getId() + "?code=" + code + "&message=" + message);
        } catch (NumberFormatException ex) {
            response.getWriter().println("Can't found id!");
        } catch (Exception e) {
            int id = Integer.parseInt(paramId);
            DimensionDBContext dimensionDB = new DimensionDBContext();
            Dimension dimension = dimensionDB.get(id);
            request.setAttribute("dimension", dimension);
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("types", settingDB.getByType("TYPE_DIMENSION"));
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/admin/subject/dimension_edit.jsp").forward(request, response);
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

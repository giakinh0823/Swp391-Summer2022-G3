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
public class UrlSubjectAdmin {

    public static final String URL_PATTERN = "^/admin/subject(/|).*";
    public static final String SUBJECT_DELETE_SELECTED = "^/admin/subject/delete-selected$";
    public static final String SUBJECT_SEARCH = "^/admin/subject/search(/|)$";
    public static final String SUBJECT_CREATE = "^/admin/subject/create(/|)$";
    public static final String SUBJECT_DELETE = "^/admin/subject/delete/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SUBJECT_STATUS_CHANGE = "^/admin/subject/status/change(/|)$";
    public static final String SUBJECT_FEATURE_CHANGE = "^/admin/subject/feature/change(/|)$";
    public static final String SUBJECT_EDIT = "^/admin/subject/edit/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SUBJECT_DETAIL = "^/admin/subject/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SUBJECT_LIST = "^/admin/subject(/|)$";

    public static final String SUBJECT_DIMENSION_DELETE = "^/admin/subject/[a-zA-Z0-9-]{1,}/dimension/delete/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SUBJECT_DIMENSION_DETAIL = "^/admin/subject/[a-zA-Z0-9-]{1,}/dimension/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SUBJECT_DIMENSION_EDIT = "^/admin/subject/[a-zA-Z0-9-]{1,}/dimension/edit/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SUBJECT_DIMENSION_CREATE = "^/admin/subject/[a-zA-Z0-9-]{1,}/dimension/create(/|)$";
    public static final String SUBJECT_DIMENSION = "^/admin/subject/[a-zA-Z0-9-]{1,}/dimension(/|)$";
    public static final String SUBJECT_DIMENSION_SEARCH = "^/admin/subject/dimension/search(/|)$";

    public static final String SUBJECT_PRICE_PACKAGE_STATUS_CHANGE = "^/admin/subject/[a-zA-Z0-9-]{1,}/price-package/status/change(/|)$";
    public static final String SUBJECT_PRICE_PACKAGE_DELETE = "^/admin/subject/[a-zA-Z0-9-]{1,}/price-package/delete/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SUBJECT_PRICE_PACKAGE_DETAIL = "^/admin/subject/[a-zA-Z0-9-]{1,}/price-package/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SUBJECT_PRICE_PACKAGE_CREATE = "^/admin/subject/[a-zA-Z0-9-]{1,}/price-package/create(/|)$";
    public static final String SUBJECT_PRICE_PACKAGE = "^/admin/subject/[a-zA-Z0-9-]{1,}/price-package(/|)$";

    public static final String SUBJECT_LESSON_DELETE_SELETED = "^/admin/subject/[a-zA-Z0-9-]{1,}/topic/[a-zA-Z0-9-]{1,}/lesson/delete-selected(/|)$";
    public static final String SUBJECT_LESSON_EDIT = "^/admin/subject/[a-zA-Z0-9-]{1,}/lesson/edit/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SUBJECT_LESSON_DELETE = "^/admin/subject/[a-zA-Z0-9-]{1,}/lesson/delete/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SUBJECT_LESSON_CREATE = "^/admin/subject/[a-zA-Z0-9-]{1,}/lesson/create(/|)$";
    public static final String SUBJECT_LESSON_DETAIL = "^/admin/subject/[a-zA-Z0-9-]{1,}/lesson/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String SUBJECT_LESSON_LIST_ALL = "^/admin/subject/lesson/all(/|)$";
    public static final String SUBJECT_LESSON_LIST = "^/admin/subject/[a-zA-Z0-9-]{1,}/lesson(/|)$";
    public static final String SUBJECT_LESSON_SEARCH = "^/admin/subject/lesson/search(/|)$";
    public static final String SUBJECT_LESSON_MEDIA_UPLOAD = "^/admin/subject/lesson/media/uploads(/|)$";
    public static final String SUBJECT_LESSON_MEDIA_DELETE = "^/admin/subject/lesson/media/delete(/|)$";

    private static UrlSubjectAdmin instance = new UrlSubjectAdmin();

    private UrlSubjectAdmin() {
    }

    public static UrlSubjectAdmin getInstance() {
        return instance;
    }

    public static void checkAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
        if (URI.matches(SUBJECT_LESSON_MEDIA_UPLOAD)) {
            if (!SecurityConfig.isAccess(request, "LESSON_CREATE") && !SecurityConfig.isAccess(request, "LESSON_EDIT")) {
                throw new Exception("code - LESSON_CREATE - LESSON_EDIT");
            }
        }if (URI.matches(SUBJECT_LESSON_MEDIA_DELETE)) {
            if (!SecurityConfig.isAccess(request, "LESSON_CREATE") && !SecurityConfig.isAccess(request, "LESSON_EDIT")) {
                throw new Exception("code - LESSON_CREATE - LESSON_EDIT");
            }
        }else if (URI.matches(SUBJECT_LESSON_DELETE_SELETED)) {
            if (!SecurityConfig.isAccess(request, "LESSON_DELETE")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - LESSON_DELETE");
            }
        } else if (URI.matches(SUBJECT_LESSON_LIST_ALL)) {
            if (!SecurityConfig.isAccess(request, "LESSON_READ")) {
                throw new Exception("code - LESSON_READ");
            }
        } if (URI.matches(SUBJECT_LESSON_SEARCH)) {
            if (!SecurityConfig.isAccess(request, "LESSON_READ")) {
                throw new Exception("code - LESSON_READ");
            }
        } else if (URI.matches(SUBJECT_LESSON_CREATE)) {
            if (!SecurityConfig.isAccess(request, "LESSON_CREATE")) {
                throw new Exception("code - LESSON_CREATE");
            }
        } else if (URI.matches(SUBJECT_LESSON_EDIT)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "LESSON_EDIT")) {
                    throw new Exception("code - LESSON_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "LESSON_READ")) {
                    throw new Exception("code - LESSON_READ");
                }
            }
        } else if (URI.matches(SUBJECT_LESSON_DELETE)) {
            if (!SecurityConfig.isAccess(request, "LESSON_DELETE")) {
                throw new Exception("code - LESSON_DELETE");
            }
        } else if (URI.matches(SUBJECT_LESSON_DETAIL)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "LESSON_EDIT")) {
                    throw new Exception("code - LESSON_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "LESSON_READ")) {
                    throw new Exception("code - LESSON_READ");
                }
            }
        } else if (URI.matches(SUBJECT_LESSON_LIST)) {
            if (!SecurityConfig.isAccess(request, "LESSON_READ")) {
                throw new Exception("code - LESSON_READ");
            }
        } else if (URI.matches(SUBJECT_DELETE_SELECTED)) {
            if (!SecurityConfig.isAccess(request, "SUBJECT_DELETE")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - SUBJECT_DELETE");
            }
        } else if (URI.matches(SUBJECT_DIMENSION_SEARCH)) {
            if (!SecurityConfig.isAccess(request, "DIMENSION_READ")) {
                throw new Exception("code - DIMENSION_READ");
            }
        } else if (URI.matches(SUBJECT_DIMENSION_DELETE)) {
            if (!SecurityConfig.isAccess(request, "DIMENSION_DELETE")) {
                throw new Exception("code - DIMENSION_DELETE");
            }
        } else if (URI.matches(SUBJECT_DIMENSION_EDIT)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "DIMENSION_EDIT")) {
                    throw new Exception("code - DIMENSION_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "DIMENSION_EDIT")) {
                    throw new Exception("code - DIMENSION_EDIT");
                }
            }
        } else if (URI.matches(SUBJECT_DIMENSION_CREATE)) {
            if (!SecurityConfig.isAccess(request, "DIMENSION_CREATE")) {
                throw new Exception("code - DIMENSION_CREATE");
            }
        } else if (URI.matches(SUBJECT_DIMENSION_DETAIL)) {
            if (!SecurityConfig.isAccess(request, "DIMENSION_READ")) {
                throw new Exception("code - DIMENSION_READ");
            }
        } else if (URI.matches(SUBJECT_DIMENSION)) {
            if (!SecurityConfig.isAccess(request, "DIMENSION_READ")) {
                throw new Exception("code - DIMENSION_READ");
            }
        } else if (URI.matches(SUBJECT_PRICE_PACKAGE_STATUS_CHANGE)) {
            if (!SecurityConfig.isAccess(request, "PRICEPACKAGE_EDIT")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - PRICEPACKAGE_EDIT");
            }
        } else if (URI.matches(SUBJECT_PRICE_PACKAGE_DELETE)) {
            if (!SecurityConfig.isAccess(request, "PRICEPACKAGE_DELETE")) {
                throw new Exception("code - PRICEPACKAGE_DELETE");
            }
        } else if (URI.matches(SUBJECT_PRICE_PACKAGE_CREATE)) {
            if (!SecurityConfig.isAccess(request, "PRICEPACKAGE_CREATE")) {
                throw new Exception("code - PRICEPACKAGE_CREATE");
            }
        } else if (URI.matches(SUBJECT_PRICE_PACKAGE_DETAIL)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "PRICEPACKAGE_EDIT")) {
                    throw new Exception("code - PRICEPACKAGE_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "PRICEPACKAGE_READ")) {
                    throw new Exception("code - PRICEPACKAGE_READ");
                }
            }
        } else if (URI.matches(SUBJECT_PRICE_PACKAGE)) {
            if (!SecurityConfig.isAccess(request, "PRICEPACKAGE_READ")) {
                throw new Exception("code - PRICEPACKAGE_READ");
            }
        } else if (URI.matches(SUBJECT_DELETE)) {
            if (!SecurityConfig.isAccess(request, "SUBJECT_DELETE")) {
                throw new Exception("code - SUBJECT_DELETE");
            }
        } else if (URI.matches(SUBJECT_SEARCH)) {
            if (!SecurityConfig.isAccess(request, "SUBJECT_READ")) {
                throw new Exception("code - SUBJECT_READ");
            }
        } else if (URI.matches(SUBJECT_EDIT)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "SUBJECT_EDIT")) {
                    throw new Exception("code - SUBJECT_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "SUBJECT_EDIT")) {
                    throw new Exception("code - SUBJECT_EDIT");
                }
            }
        } else if (URI.matches(SUBJECT_CREATE)) {
            if (!SecurityConfig.isAccess(request, "SUBJECT_CREATE")) {
                throw new Exception("code - SUBJECT_CREATE");
            }
        } else if (URI.matches(SUBJECT_STATUS_CHANGE)) {
            if (!SecurityConfig.isAccess(request, "SUBJECT_CHANGE_STATUS")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - SUBJECT_CHANGE_STATUS");
            }
        } else if (URI.matches(SUBJECT_FEATURE_CHANGE)) {
            if (!SecurityConfig.isAccess(request, "SUBJECT_EDIT")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Message message = new Message();
                message.setCode("error");
                message.setMessage("FORBIDDEN - role can't not access!");
                String json = new Gson().toJson(message);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
                throw new Error("code - SUBJECT_EDIT");
            }
        } else if (URI.matches(SUBJECT_DETAIL)) {
            if (request.getMethod().equalsIgnoreCase("POST")) {
                if (!SecurityConfig.isAccess(request, "SUBJECT_EDIT")) {
                    throw new Exception("code - SUBJECT_EDIT");
                }
            } else {
                if (!SecurityConfig.isAccess(request, "SUBJECT_READ")) {
                    throw new Exception("code - SUBJECT_READ");
                }
            }
        } else if (URI.matches(SUBJECT_LIST)) {
            if (!SecurityConfig.isAccess(request, "SUBJECT_READ")) {
                throw new Exception("code - SUBJECT_READ");
            }
        }
    }
}

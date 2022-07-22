/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.admin;

import com.se1617.learning.config.url.admin.UrlRegistrationsAdmin;
import com.se1617.learning.controller.base.BaseAuthController;
import com.se1617.learning.dal.registration.RegistrationDBContext;
import com.se1617.learning.model.general.Message;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.registration.Registration;
import com.se1617.learning.model.user.User;
import com.se1617.learning.utils.Validate;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giaki
 */
public class RegistrationController extends BaseAuthController {

    @Override
    protected boolean isPermission(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user.is_staff();
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_CHANGE_STATUS)) {
                registrationsChangeStatus(request, response);
            } else if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_CREATE)) {
                registrationsCreateGet(request, response);
            } else if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_EDIT)) {
                registrationsEditGet(request, response);
            } else if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_DELETE)) {
                registrationsDelete(request, response);
            } else if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_DETAIL)) {
                registrationsDetail(request, response);
            } else if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_LIST)) {
                registrationsList(request, response);
            } else if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_DELETE_SELETED)) {
                registrationsDeleteSelected(request, response);
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
            if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_CHANGE_STATUS)) {
                registrationsChangeStatus(request, response);
            }
            if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_CREATE)) {
                registrationsCreatePost(request, response);
            } else if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_EDIT)) {
                registrationsEditPost(request, response);
            } else if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_DELETE)) {
                registrationsDelete(request, response);
            } else if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_DETAIL)) {
                registrationsDetail(request, response);
            } else if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_LIST)) {
                registrationsList(request, response);
            } else if (super.getURI().matches(UrlRegistrationsAdmin.REGISTRATIONS_DELETE_SELETED)) {
                registrationsDeleteSelected(request, response);
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

    //-------------------- GET METHOD -------------------------//
    private void registrationsCreateGet(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void registrationsEditGet(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void registrationsDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramsId = super.getPaths().get(1);
        int id = Integer.parseInt(paramsId);
        RegistrationDBContext registrationDB = new RegistrationDBContext();
        registrationDB.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/registrations");
    }

    private void registrationsDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idParams = super.getPaths().get(0);
            int id = Integer.parseInt(idParams);
            RegistrationDBContext registrationDB = new RegistrationDBContext();
            Registration registration = registrationDB.get(id);
            request.setAttribute("register", registration);
            request.getRequestDispatcher("/views/admin/registers/registers_detail.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().println("Not found register!");
        }
    }

    private void registrationsList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String message = request.getParameter("message");
            String code = request.getParameter("code");
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

            Pageable pageable = new Pageable();
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(12);
            // search
            String search = validate.getFieldAjax(request, "search", false);
            if (search == null) {
                search = "";
            }
            RegistrationDBContext registrationDB = new RegistrationDBContext();
            ResultPageable<Registration> rsPageable = registrationDB.listWithPagination(search, pageable);
            request.setAttribute("registers", rsPageable.getList());
            request.setAttribute("page", rsPageable.getPagination());
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.getRequestDispatcher("/views/admin/registers/registers.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().write(e.getMessage());
        }
    }

    private void registrationsDeleteSelected(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void registrationsChangeStatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String idParams = super.getPaths().get(0);
            int id = Integer.parseInt(idParams);
            RegistrationDBContext registrationDB = new RegistrationDBContext();
            Registration registration = registrationDB.get(id);
            registration.setStatus(!registration.isStatus());
            registrationDB.updateStatus(registration);
            response.sendRedirect(request.getHeader("referer"));
        } catch (Exception e) {
            response.getWriter().println("Not found register!");
        }
    }

    //---------------------------POST METHOD -----------------------------//
    private void registrationsCreatePost(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void registrationsEditPost(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.admin;

import com.se1617.learning.config.url.admin.UrlDashboardAdmin;
import com.se1617.learning.controller.base.BaseAuthController;
import com.se1617.learning.dal.registration.RegistrationDBContext;
import com.se1617.learning.dal.subject.SubjectDBContext;
import com.se1617.learning.dal.user.UserDBContext;
import com.se1617.learning.model.user.User;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giaki
 */
public class DashboardController extends BaseAuthController {

    @Override
    protected boolean isPermission(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user.is_super() || user.is_staff();
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (super.getURI().matches(UrlDashboardAdmin.DASHBOARD)) {
            dashboardGet(request, response);
        } else {
            request.getRequestDispatcher("/views/error/admin/404.jsp").forward(request, response);
        }
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (super.getURI().matches(UrlDashboardAdmin.DASHBOARD)) {
            dashboardPost(request, response);
        } else {
            request.getRequestDispatcher("/views/error/admin/404.jsp").forward(request, response);
        }
    }

    //---------------- GET METHOD--------------------------//
    private void dashboardGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String dateStartString = request.getParameter("date-start");
            String dateEndString = request.getParameter("date-end");
            Date dateStart = null;
            Date dateEnd = null;
            if (dateStartString != null && dateEndString != null) {
                dateStart = new Date(dateStartString);
                dateEnd = new Date(dateEndString);
            }

            SubjectDBContext subjectDB = new SubjectDBContext();
            UserDBContext userDB = new UserDBContext();
            RegistrationDBContext registerDB = new RegistrationDBContext();
            Timestamp dateTimeStart = new Timestamp(dateStart != null ? dateStart.getTime() : new Date().getTime() - 604800000);
            Timestamp dateTimeEnd = new Timestamp(dateEnd != null ? dateEnd.getTime() : new Date().getTime());
            request.setAttribute("subjects_create", subjectDB.dataByDateMonthCreate(dateTimeStart, dateTimeEnd));
            request.setAttribute("subjects_update", subjectDB.dataByDateMonthUpdate(dateTimeStart, dateTimeEnd));
            request.setAttribute("registers_sucess", registerDB.dataByDateMonthSucesss(dateTimeStart, dateTimeEnd));
            request.setAttribute("registers_pending", registerDB.dataByDateMonthPending(dateTimeStart, dateTimeEnd));
             request.setAttribute("customers", userDB.dataByDateMonth(dateTimeStart, dateTimeEnd));
            request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/admin/dashboard.jsp").forward(request, response);
        }
    }

    // -----------------POST METHOD--------------------------//
    private void dashboardPost(HttpServletRequest request, HttpServletResponse response) {
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

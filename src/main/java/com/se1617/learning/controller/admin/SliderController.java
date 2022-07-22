/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.admin;

import com.google.gson.Gson;
import com.se1617.learning.config.url.admin.UrlSliderAdmin;
import com.se1617.learning.controller.base.BaseAuthController;
import com.se1617.learning.dal.slider.SliderDBContext;
import com.se1617.learning.model.general.Message;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.slider.Slider;
import com.se1617.learning.model.user.User;
import com.se1617.learning.utils.FileUtility;
import com.se1617.learning.utils.Validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author lanh0
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class SliderController extends BaseAuthController {

    private static final int pageSize = 12;

    @Override
    protected boolean isPermission(HttpServletRequest request) {
        return ((User) request.getSession().getAttribute("user")).is_staff();
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlSliderAdmin.SLIDER_STATUS_CHANGE)) {
                sliderStatusChange(request, response);
            } else if (super.getURI().matches(UrlSliderAdmin.SLIDER_CREATE)) {
                sliderCreateGet(request, response);
            } else if (super.getURI().matches(UrlSliderAdmin.SLIDER_EDIT)) {
                sliderEditGet(request, response);
            } else if (super.getURI().matches(UrlSliderAdmin.SLIDER_DELETE)) {
                sliderDelete(request, response);
            } else if (super.getURI().matches(UrlSliderAdmin.SLIDER_DETAIL)) {
                sliderDetail(request, response);
            } else if (super.getURI().matches(UrlSliderAdmin.SLIDER_LIST)) {
                sliderList(request, response);
            } else if (super.getURI().matches(UrlSliderAdmin.SLIDER_DELETEALL)) {
                sliderDeleteSelected(request, response);
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
            if (super.getURI().matches(UrlSliderAdmin.SLIDER_STATUS_CHANGE)) {
                sliderStatusChange(request, response);
            } else if (super.getURI().matches(UrlSliderAdmin.SLIDER_CREATE)) {
                sliderCreatePost(request, response);
            } else if (super.getURI().matches(UrlSliderAdmin.SLIDER_EDIT)) {
                sliderEditPost(request, response);
            } else if (super.getURI().matches(UrlSliderAdmin.SLIDER_DELETE)) {
                sliderDelete(request, response);
            } else if (super.getURI().matches(UrlSliderAdmin.SLIDER_DETAIL)) {
                sliderDetail(request, response);
            } else if (super.getURI().matches(UrlSliderAdmin.SLIDER_LIST)) {
                sliderList(request, response);
            } else if (super.getURI().matches(UrlSliderAdmin.SLIDER_DELETEALL)) {
                sliderDeleteSelected(request, response);
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

    //------ GET METHOD --------- //
    private void sliderList(HttpServletRequest request, HttpServletResponse response)
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

            String statusParam = validate.getField(request, "status", false);

            String search = validate.getFieldAjax(request, "search", false);
            if (search == null) {
                search = "";
            }

            Pageable pageable = new Pageable();
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(pageSize);
            pageable.setFieldSort("id");
            pageable.setOrder("DESC");

            SliderDBContext sliderDBContext = new SliderDBContext();
            ResultPageable<Slider> resultPageable = new ResultPageable<>();
            boolean status = statusParam != null && statusParam.equalsIgnoreCase("active");
            if (statusParam != null && !statusParam.isEmpty()) {
                request.setAttribute("status", status ? "active" : "inactive");
            }
            resultPageable = sliderDBContext.searchByTitleOrBackLinkAndFilterStatusPageable(search, statusParam, pageable);
            request.setAttribute("search", search);
            request.setAttribute("sliders", resultPageable.getList());
            request.setAttribute("page", resultPageable.getPagination());
            request.getRequestDispatcher("/views/admin/slider/slider.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SliderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sliderCreateGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/admin/slider/slider_add.jsp").forward(request, response);
    }

    private void sliderEditGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramsId = super.getPaths().get(1);
        int id = Integer.parseInt(paramsId);
        SliderDBContext sliderDBContext = new SliderDBContext();
        Slider slider = sliderDBContext.get(id);
        request.setAttribute("slider", slider);
        request.getRequestDispatcher("/views/admin/slider/slider_edit.jsp").forward(request, response);
    }

    private void sliderDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramsId = super.getPaths().get(1);
        int id = Integer.parseInt(paramsId);
        SliderDBContext sliderDBContext = new SliderDBContext();
        Slider slider = sliderDBContext.get(id);
        if (slider.getImage() != null) {
            FileUtility fileUtility = new FileUtility();
            String folder = request.getServletContext().getRealPath("/images/slider");
            fileUtility.delete(slider.getImage(), folder);
        }
        sliderDBContext.delete(slider.getId());
        response.sendRedirect(request.getContextPath() + "/admin/slider");
    }

    private void sliderDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String paramsId = super.getPaths().get(0);
            int id = Integer.parseInt(paramsId);
            SliderDBContext sliderDBContext = new SliderDBContext();
            Slider slider = sliderDBContext.get(id);
            request.setAttribute("slider", slider);
            request.getRequestDispatcher("/views/admin/slider/slider_detail.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/admin/slider/slider_detail.jsp").forward(request, response);
        }
    }

    //------ POST METHOD --------- //
    private void sliderCreatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String title = validate.getField(request, "title", true);
            String backlink = validate.getField(request, "backlink", true);
            String notes = validate.getField(request, "notes", true);
            // get image file upload to Servlet from form
            Part part = request.getPart("image");
            FileUtility fileUtility = new FileUtility();
            // get 
            String folder = request.getServletContext().getRealPath("/images/slider");
            String filename = null;

            if (part.getSize() != 0) {
                filename = fileUtility.upLoad(part, folder);
            }

            Slider slider = new Slider();
            slider.setTitle(title);
            if (filename != null) {
                slider.setImage(filename);
            }
            slider.setBacklink(backlink);
            slider.setStatus(false);
            slider.setNotes(notes);

            SliderDBContext sliderDBContext = new SliderDBContext();
            sliderDBContext.insert(slider);
            response.sendRedirect(request.getContextPath() + "/admin/slider");

        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/admin/slider/slider_add.jsp").forward(request, response);
        }

    }

    private void sliderEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String paramsId = super.getPaths().get(1);
            int id = Integer.parseInt(paramsId);
            SliderDBContext sliderDBContext = new SliderDBContext();
            Slider slider = sliderDBContext.get(id);

            Validate validate = new Validate();
            String title = validate.getField(request, "title", true);
            String backlink = validate.getField(request, "backlink", true);
            String notes = validate.getField(request, "notes", true);
            String status = validate.getField(request, "status", false);

            Part part = request.getPart("image");
            FileUtility fileUtility = new FileUtility();
            String folder = request.getServletContext().getRealPath("/images/slider");
            String filename = null;
            if (part.getSize() != 0) {
                if (slider.getImage() != null && !slider.getImage().isEmpty()) {
                    fileUtility.delete(slider.getImage(), folder);
                }
                filename = fileUtility.upLoad(part, folder);
            }
            slider.setTitle(title);
            if (filename != null) {
                slider.setImage(filename);
            }

            slider.setBacklink(backlink);
            slider.setStatus(status != null && status.equalsIgnoreCase("true"));
            slider.setNotes(notes);

            sliderDBContext.update(slider);
            response.sendRedirect(request.getContextPath() + "/admin/slider");
        } catch (NumberFormatException ex) {
            request.setAttribute("error", "Error update slider! please try again!");
            request.getRequestDispatcher(("/views/admin/slider/slider_edit.jsp")).forward(request, response);
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
            request.getRequestDispatcher("/views/admin/slider/slider_edit.jsp").forward(request, response);
        }
    }

    private void sliderStatusChange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idSlider = validate.getFieldAjax(request, "id", true);
            String statusSlider = validate.getFieldAjax(request, "status", true);

            int id = validate.fieldInt(idSlider, "Id slider must be a number!");
            boolean status = validate.fieldBoolean(statusSlider, "Status slider must be a true or false!");

            SliderDBContext sliderDB = new SliderDBContext();
            Slider slider = sliderDB.get(id);
            slider.setStatus(status);
            sliderDB.update(slider);
            Message message = new Message();
            message.setCode("success");
            message.setMessage("Update status slider success!");
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void sliderDeleteSelected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Validate validate = new Validate();
            String[] ids = validate.getFieldsAjax(request, "sliders[]", false);
            List<Integer> listIds = new ArrayList<>();
            SliderDBContext sliderDBContext = new SliderDBContext();
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
            sliderDBContext.deleteByIds(listIds);
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

}

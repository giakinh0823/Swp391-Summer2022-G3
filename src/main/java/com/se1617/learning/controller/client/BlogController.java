/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.client;

import com.se1617.learning.config.url.client.UrlBlogClient;
import com.se1617.learning.controller.base.BaseController;
import com.se1617.learning.dal.blog.PostDBContext;
import com.se1617.learning.dal.setting.SettingDBContext;
import com.se1617.learning.model.blog.Post;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.utils.Validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giaki
 */
public class BlogController extends BaseController {

    private static final int pageSize = 9;

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlBlogClient.BLOG_LIST)) {
                blogList(request, response);
            } else if (super.getURI().matches(UrlBlogClient.BLOG_DETAIL)) {
                blogDetail(request, response);
            } else {
                request.getRequestDispatcher("/views/error/client/404.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("code", e.getMessage());
            request.getRequestDispatcher("/views/error/client/access_denied.jsp").forward(request, response);
        }
    }

    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void blogList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            if(search!=null){
                request.setAttribute("search", search);
            }
            if (search == null) {
                search = "";
            }

            Pageable pageable = new Pageable();
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(pageSize);

            PostDBContext postDB = new PostDBContext();
            ResultPageable<Post> resultPageable = null;

            String[] categories = validate.getFieldsAjax(request, "category", false);
            Map<String, ArrayList<String>> filters = new HashMap<>();
            if (categories != null && categories.length > 0) {
                ArrayList<String> listCategory = new ArrayList<>();
                for (String category : categories) {
                    if (category.matches("[0-9]+")) {
                        listCategory.add(category);
                    }
                }
                if (!listCategory.isEmpty()) {
                    filters.put("category", listCategory);
                }
            }
            ArrayList<String> status = new ArrayList<>();
            status.add("show");
            filters.put("status", status);
            pageable.setFilters(filters);

            resultPageable = postDB.searchByTitleAndFilterMultiFieldAndMultiSort(search, pageable);

            ArrayList<Post> featureBlogs = postDB.getFeatureBlogs(3);

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("featureBlogs", featureBlogs);
            request.setAttribute("featureRandoms", postDB.getFeatureBlogsRandom(6));
            request.setAttribute("blogs", resultPageable.getList());
            request.setAttribute("page", resultPageable.getPagination());
            request.setAttribute("pageable", pageable);
            request.setAttribute("categoryBlogs", settingDB.listSettingNotPrarent("CATEGORY_POST"));

            List<Setting> categorieSubjects = settingDB.getByType("CATEGORY_SUBJECT");
            request.setAttribute("categories", categorieSubjects);
            request.getRequestDispatcher("/views/client/blog/blog.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(BlogController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void blogDetail(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try{
            String paramsId = super.getPaths().get(0);
            int id = Integer.parseInt(paramsId);
            
            PostDBContext postDB = new PostDBContext();
            Post post = postDB.get(id);
            
            request.setAttribute("post", post);
            ArrayList<Post> featureBlogs = postDB.getFeatureBlogsByCategory(post.getCategory().getId(), 7);
            request.setAttribute("featureBlogs", featureBlogs);
            request.getRequestDispatcher("/views/client/blog/blog_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found blog detail!");
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

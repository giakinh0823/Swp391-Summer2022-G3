/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.client;

import com.se1617.learning.dal.blog.PostDBContext;
import com.se1617.learning.dal.slider.SliderDBContext;
import com.se1617.learning.dal.subject.SubjectDBContext;
import com.se1617.learning.model.blog.Post;
import com.se1617.learning.model.subject.Subject;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giaki
 */
public class HomeController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SliderDBContext sliderDB = new SliderDBContext();
        request.setAttribute("sliders", sliderDB.getListActive());
        SubjectDBContext subjectDBContext = new SubjectDBContext();
        ArrayList<Subject> subjects = subjectDBContext.getFeatedtSubject();
        
        PostDBContext postDBContext = new PostDBContext();
        ArrayList<Post> listPostLatest = postDBContext.listPostLatest(20);
        ArrayList<Post> listPostFeatured = postDBContext.getFeatureBlogs(20);
        request.setAttribute("posts", listPostLatest);
        request.setAttribute("postsFeatured", listPostFeatured);
        request.setAttribute("subjects", subjects);
        request.getRequestDispatcher("/views/client/home.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

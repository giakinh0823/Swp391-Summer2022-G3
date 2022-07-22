/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.admin;

import com.se1617.learning.config.url.admin.UrlPostAdmin;
import com.se1617.learning.controller.base.BaseAuthController;
import com.se1617.learning.dal.blog.FilePostDBContext;
import com.se1617.learning.dal.blog.PostDBContext;
import com.se1617.learning.dal.setting.SettingDBContext;
import com.se1617.learning.model.blog.FilePost;
import com.se1617.learning.model.blog.Post;
import com.se1617.learning.model.general.Message;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.user.User;
import com.se1617.learning.utils.FileUtility;
import com.se1617.learning.utils.Validate;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
public class PostController extends BaseAuthController {

    private static final int pageSize = 12;

    @Override
    protected boolean isPermission(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user.is_staff();
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlPostAdmin.POST_DELETE_SELETED)) {
                postDeleteSelected(request, response);
            } else if (super.getURI().matches(UrlPostAdmin.POST_CREATE)) {
                postCreateGet(request, response);
            } else if (super.getURI().matches(UrlPostAdmin.POST_EDIT)) {
                postEditGet(request, response);
            } else if (super.getURI().matches(UrlPostAdmin.POST_DELETE)) {
                postDelete(request, response);
            } else if (super.getURI().matches(UrlPostAdmin.POST_DETAIL)) {
                postDetail(request, response);
            } else if (super.getURI().matches(UrlPostAdmin.POST_LIST)) {
                postList(request, response);
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
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlPostAdmin.POST_MEDIA_UPLOAD)) {
                mediaUpload(request, response);
            } else if (super.getURI().matches(UrlPostAdmin.POST_MEDIA_DELETE)) {
                mediaDelete(request, response);
            } else if (super.getURI().matches(UrlPostAdmin.POST_DELETE_SELETED)) {
                postDeleteSelected(request, response);
            } else if (super.getURI().matches(UrlPostAdmin.POST_EDIT)) {
                postEditPost(request, response);
            } else if (super.getURI().matches(UrlPostAdmin.POST_DELETE)) {
                postDelete(request, response);
            } else if (super.getURI().matches(UrlPostAdmin.POST_CREATE)) {
                postCreatePost(request, response);
            }  else if (super.getURI().matches(UrlPostAdmin.POST_DETAIL)) {
                postDetail(request, response);
            } else if (super.getURI().matches(UrlPostAdmin.POST_LIST)) {
                postList(request, response);
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

    //----------------------- GET METHOD ----------------------------------//
    private void postDeleteSelected(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void postCreateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SettingDBContext settingDB = new SettingDBContext();
        request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_POST"));
        request.setAttribute("backlink", request.getContextPath() + "/admin/post");
        request.getRequestDispatcher("/views/admin/post/post_create.jsp").forward(request, response);
    }

    private void postEditGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(1);
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            request.setAttribute("backlink",request.getContextPath() + "/admin/post");
            int id = Integer.parseInt(paramId);
            PostDBContext postDB = new PostDBContext();
            Post post = postDB.get(id);
            request.setAttribute("post", post);

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_POST"));

            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.getRequestDispatcher("/views/admin/post/post_edit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            message = "Not found post!";
            code = "error";
            response.sendRedirect(request.getContextPath() + "/admin/post?code=" + code + "&message=" + message);
        }
    }

    private void postDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(1);
        try {
            int id = Integer.parseInt(paramId);
            PostDBContext postDB = new PostDBContext();
            FilePostDBContext imageDB = new FilePostDBContext();
            ArrayList<FilePost> images = imageDB.findByPostid(id);
            if (images != null && !images.isEmpty()) {
                FileUtility fileUtility = new FileUtility();
                String folder = request.getServletContext().getRealPath("/images/post/media");
                for (FilePost image : images) {
                    try {
                        fileUtility.delete(image.getFile(), folder);
                    } catch (Exception e) {
                        System.out.println("Error delete file!");
                    }
                }
            }
            postDB.delete(id);
            if(request.getHeader("referer").toLowerCase().contains("/admin/post/"+id) && !request.getHeader("referer").toLowerCase().contains("/edit/")){
                response.sendRedirect(request.getContextPath()+"/admin/post");
            }else{
                response.sendRedirect(request.getHeader("referer"));
            }
        } catch (NumberFormatException e) {
            String message = "Not found post!";
            String code = "error";
            response.sendRedirect(request.getContextPath() + "/admin/post?code=" + code + "&message=" + message);
        }
    }

    private void postDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paramId = super.getPaths().get(0);
        try {
            int id = Integer.parseInt(paramId);
            PostDBContext postDB = new PostDBContext();
            Post post = postDB.get(id);
            request.setAttribute("post", post);

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_POST"));
            request.getRequestDispatcher("/views/admin/post/post_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            String message = "Not found post!";
            String code = "error";
            response.sendRedirect(request.getContextPath() + "/admin/post?code=" + code + "&message=" + message);
        }
    }

    private void postList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_POST"));
            request.setAttribute("groups", settingDB.getByType("USER_ROLE"));

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

            PostDBContext postDB = new PostDBContext();
            ResultPageable<Post> resultPageable = null;
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
                                || sort[0].equalsIgnoreCase("title")
                                || sort[0].equalsIgnoreCase("category")
                                || sort[0].equalsIgnoreCase("author")
                                || sort[0].equalsIgnoreCase("status")
                                || sort[0].equalsIgnoreCase("featured")
                                || sort[0].equalsIgnoreCase("created")
                                || sort[0].equalsIgnoreCase("updated")) {

                            orderMap.put(++count + "." + sort[0], sort[1]);
                        }
                    }
                }
            }
            pageable.setOrderings(orderMap);

            String search = validate.getField(request, "search", false);
            if (search == null) {
                search = "";
            }

            String[] status = validate.getFieldsAjax(request, "status", false);
            String[] categories = validate.getFieldsAjax(request, "category", false);
            String[] authors = validate.getFieldsAjax(request, "author", false);
            Map<String, ArrayList<String>> filters = new HashMap<>();
            if (status != null && status.length > 0) {
                ArrayList<String> listStatus = new ArrayList<>();
                for (String statu : status) {
                    if (statu.matches("show") || statu.matches("hidden")) {
                        listStatus.add(statu);
                    }
                }
                if (!listStatus.isEmpty()) {
                    filters.put("status", listStatus);
                }
            }
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
            if (authors != null && authors.length > 0) {
                ArrayList<String> listAuthor = new ArrayList<>();
                for (String author : authors) {
                    if (author.matches("[0-9]+")) {
                        listAuthor.add(author);
                    }
                }
                if (!listAuthor.isEmpty()) {
                    filters.put("author", listAuthor);
                }
            }
            pageable.setFilters(filters);
            resultPageable = postDB.searchByTitleAndFilterMultiFieldAndMultiSort(search, pageable);
            request.setAttribute("posts", resultPageable.getList());
            request.setAttribute("page", resultPageable.getPagination());

            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.setAttribute("pageable", pageable);
            request.getRequestDispatcher("/views/admin/post/post.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // --------------------- POST METHOD ----------------------------//
    private void postCreatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("backlink", request.getContextPath() + "/admin/post");
        try {
            PostDBContext postDB = new PostDBContext();
            Validate validate = new Validate();
            String title = validate.getField(request, "title", true);
            String categoryParam = validate.getField(request, "category", true);
            String status = validate.getField(request, "status", false);
            String flag = validate.getField(request, "flag", false);
            String featured = validate.getField(request, "featured", false);
            String content = validate.getField(request, "content", true);
            String description = validate.getField(request, "description", false);
            String[] dashs = validate.getFieldsAjax(request, "listDash", false);
            String[] images = validate.getFieldsAjax(request, "listImage", false);
            if (dashs != null && dashs.length > 0) {
                FileUtility fileUtility = new FileUtility();
                String folder = request.getServletContext().getRealPath("/images/post/media");
                List<String> listDashs = new ArrayList<>();
                FilePostDBContext imageDB = new FilePostDBContext();
                for (String image : dashs) {
                    if (image.contains("/images/post/media/")) {
                        String uri = request.getScheme()
                                + "://"
                                + request.getServerName()
                                + ":"
                                + request.getServerPort()
                                + request.getContextPath();
                        String img = image.replace(uri, "");
                        img = img.replace(request.getContextPath() + "/images/post/media/", "");
                        fileUtility.delete(img, folder);
                        listDashs.add(img);
                    }
                }
                if (listDashs != null && !listDashs.isEmpty()) {
                    imageDB.deleteByImages(listDashs);
                }
            }

            SettingDBContext settingDB = new SettingDBContext();
            Setting category = null;
            try {
                int categoryId = Integer.parseInt(categoryParam);
                category = settingDB.get(categoryId);
                if (category == null) {
                    throw new Exception("Error set field category!");
                }
            } catch (Exception e) {
                throw new Exception("Error set field category!");
            }

            Post post = new Post();
            post.setTitle(title);
            post.setCategory(category);
            post.setCategoryId(category.getId());
            post.setStatus(status != null && status.equalsIgnoreCase("show"));
            post.setFeatured(featured != null && featured.equalsIgnoreCase("true"));
            post.setFlag(flag != null && flag.equalsIgnoreCase("true"));
            post.setContent(content);
            post.setDescription(description);
            post.setCreated_at(new Timestamp(System.currentTimeMillis()));
            post.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            User user = (User) request.getSession().getAttribute("user");
            post.setUser(user);
            post.setUserId(user.getId());

            Part part = request.getPart("thumbnail");
            FileUtility fileUtility = new FileUtility();

            String folder = request.getServletContext().getRealPath("/images/post");
            String filename = null;

            if (part.getSize() != 0) {
                if (post.getThumbnail() != null && !post.getThumbnail().isEmpty()) {
                    fileUtility.delete(post.getThumbnail(), folder);
                }
                filename = fileUtility.upLoad(part, folder);
            }else{
                throw new Exception("Thumbnail is required!");
            }

            if (filename != null) {
                post.setThumbnail(filename);
            }

            post = postDB.insert(post);

            try {
                if (post != null && images != null && images.length > 0) {
                    System.out.println(Arrays.toString(images));
                    List<FilePost> listFiles = new ArrayList<>();
                    FilePostDBContext filePostDB = new FilePostDBContext();
                    for (String image : images) {
                        if (image.contains("/images/post/media/")) {
                            String uri = request.getScheme()
                                    + "://"
                                    + request.getServerName()
                                    + ":"
                                    + request.getServerPort()
                                    + request.getContextPath();
                            String img = image.replace(uri, "");
                            img = img.replace(request.getContextPath() + "/images/post/media/", "");
                            System.out.println(img);
                            FilePost filePost = filePostDB.findByUUID(img);
                            System.out.println("file-post: "+filePost.getFile());
                            if (filePost != null) {
                                System.out.println(filePost.getFile());
                                filePost.setPost(post);
                                filePost.setPostId(post.getId());
                                listFiles.add(filePost);
                            }
                        }
                    }
                    if (listFiles != null && !listFiles.isEmpty()) {
                        filePostDB.updateManyFile(listFiles);
                    }
                }
            } catch (Exception e) {
                String message = "Create post success! But error save content image!";
                String code = "success";
                response.sendRedirect(request.getContextPath() + "/admin/post?code=" + code + "&message=" + message);
                return;
            }
            String message = "Create post success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/post?code=" + code + "&message=" + message);
        } catch (Exception e) {

            if (request.getParameter("title") != null) {
                String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "utf-8");
                request.setAttribute("title", title);
            }
            if (request.getParameter("description") != null) {
                String description = new String(request.getParameter("description").getBytes("iso-8859-1"), "utf-8");
                request.setAttribute("description", description);
            }
            String categoryParam = request.getParameter("category");
            request.setAttribute("category", categoryParam);
            String status = request.getParameter("status");
            request.setAttribute("status", status);
            String flag = request.getParameter("flag");
            request.setAttribute("flag", flag);
            String featured = request.getParameter("featured");
            request.setAttribute("featured", featured);
            if (request.getParameter("title") != null) {
                String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "utf-8");
                request.setAttribute("content", content);
            }
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_POST"));
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/admin/post/post_create.jsp").forward(request, response);
        }
    }

    private void postEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Validate validate = new Validate();
        request.setAttribute("backlink", request.getContextPath() + "/admin/post");
        try {
            String paramId = super.getPaths().get(1);
            int id = Integer.parseInt(paramId);
            PostDBContext postDB = new PostDBContext();
            Post post = postDB.get(id);

            String title = validate.getField(request, "title", true);
            String categoryParam = validate.getField(request, "category", true);
            String status = validate.getField(request, "status", false);
            String flag = validate.getField(request, "flag", false);
            String featured = validate.getField(request, "featured", false);
            String content = validate.getField(request, "content", true);
            String description = validate.getField(request, "description", true);

            SettingDBContext settingDB = new SettingDBContext();
            Setting category = null;
            try {
                int categoryId = Integer.parseInt(categoryParam);
                category = settingDB.get(categoryId);
                if (category == null) {
                    throw new Exception("Error set field category!");
                }
            } catch (NumberFormatException e) {
                throw new Exception("Error set field category!");
            }
            post.setTitle(title);
            post.setCategory(category);
            post.setCategoryId(category.getId());
            post.setStatus(status != null && status.equalsIgnoreCase("show"));
            post.setFeatured(featured != null && featured.equalsIgnoreCase("true"));
            post.setFlag(flag != null && flag.equalsIgnoreCase("true"));
            post.setContent(content);
            post.setDescription(description);
            post.setUpdated_at(new Timestamp(System.currentTimeMillis()));

            Part part = request.getPart("thumbnail");
            FileUtility fileUtility = new FileUtility();

            String folder = request.getServletContext().getRealPath("/images/post");
            String filename = null;

            if (part.getSize() != 0) {
                if (post.getThumbnail() != null && !post.getThumbnail().isEmpty()) {
                    try {
                        fileUtility.delete(post.getThumbnail(), folder);
                    } catch (Exception e) {
                        System.out.println("Can't found file!");
                    }
                }
                filename = fileUtility.upLoad(part, folder);
            }

            if (filename != null) {
                post.setThumbnail(filename);
            }

            postDB.update(post);
            String message = "Update post success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/post/edit/" + id + "?code=" + code + "&message=" + message);
        } catch (NumberFormatException e) {
            String message = "Not found!";
            String code = "error";
            response.sendRedirect(request.getContextPath() + "/admin/post?code=" + code + "&message=" + message);
        } catch (Exception e) {
            String paramId;
            try {
                paramId =  super.getPaths().get(1);
                int id = Integer.parseInt(paramId);
                PostDBContext postDB = new PostDBContext();
                Post post = postDB.get(id);
                request.setAttribute("post", post);

                SettingDBContext settingDB = new SettingDBContext();
                request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_POST"));
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/views/admin/post/post_edit.jsp").forward(request, response);
            } catch (Exception ex) {
                String message = "Not found!";
                String code = "error";
                response.sendRedirect(request.getContextPath() + "/admin/post?code=" + code + "&message=" + message);
            }
        }
    }

    private void mediaUpload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idParam = validate.getFieldAjax(request, "id", false);
            Part part = request.getPart("file");
            FileUtility fileUtility = new FileUtility();

            String folder = request.getServletContext().getRealPath("/images/post/media");
            String filename = null;
            if (part.getSize() != 0) {
                filename = fileUtility.upLoad(part, folder);
                FilePost file = new FilePost();
                file.setFile(filename);
                if (idParam != null && !idParam.trim().isEmpty() && idParam.matches("^[0-9]+$")) {
                    int id = Integer.parseInt(idParam);
                    file.setPostId(id);
                }
                FilePostDBContext imageDB = new FilePostDBContext();
                file = imageDB.insert(file);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("READY:" + request.getContextPath() + "/images/post/media/" + file.getFile());
            } else {
                throw new Exception("Can't save file!");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("ERROR:" + e.getMessage());
        }
    }

    private void mediaDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String[] images = validate.getFieldsAjax(request, "images[]", false);
            System.out.println(Arrays.toString(images));
            if (images != null && images.length > 0) {
                FileUtility fileUtility = new FileUtility();
                String folder = request.getServletContext().getRealPath("/images/post/media");
                List<String> listImages = new ArrayList<>();
                FilePostDBContext imageDB = new FilePostDBContext();
                for (String image : images) {
                    if (image.contains("/images/post/media/")) {
                        String uri = request.getScheme()
                                + "://"
                                + request.getServerName()
                                + ":"
                                + request.getServerPort()
                                + request.getContextPath();
                        String img = image.replace(uri, "");
                        img = img.replace(request.getContextPath() + "/images/post/media/", "");
                        fileUtility.delete(img, folder);
                        listImages.add(img);
                    }
                }
                if (listImages != null && !listImages.isEmpty()) {
                    imageDB.deleteByImages(listImages);
                }
            }
            response.setStatus(HttpServletResponse.SC_OK);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Delete sucess!");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("ERROR:" + e.getMessage());
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

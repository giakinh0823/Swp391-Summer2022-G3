/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.controller.admin;

import com.google.gson.Gson;
import com.se1617.learning.dal.quizzes.QuizzesDBContext;
import com.se1617.learning.dal.setting.SettingDBContext;
import com.se1617.learning.dal.subject.FileLessonDBContext;
import com.se1617.learning.dal.subject.LessonDBContext;
import com.se1617.learning.dal.subject.PackagePriceDBContext;
import com.se1617.learning.dal.subject.SubjectDBContext;
import com.se1617.learning.model.general.Message;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.Res;
import com.se1617.learning.model.quizzes.Quizzes;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.subject.FileLesson;
import com.se1617.learning.model.subject.Lesson;
import com.se1617.learning.model.subject.PackagePrice;
import com.se1617.learning.model.subject.Subject;
import com.se1617.learning.utils.FileUtility;
import com.se1617.learning.utils.Validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author giaki
 */
public class LessonController {
    
    private static final int pageSize = 12;

    private static ArrayList<String> getPaths(HttpServletRequest request)
            throws ServletException, IOException {
        ArrayList<String> paths = new ArrayList<>();;
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && !pathInfo.isEmpty()) {
            String[] pathStrings = pathInfo.split("/");
            for (String path : pathStrings) {
                if (!path.trim().isEmpty()) {
                    paths.add(path);
                }
            }
        }
        return paths;
    }

    static void lessonList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<String> paths = getPaths(request);
        String idParam = paths.get(0);
        String message = request.getParameter("message");
        String code = request.getParameter("code");
        try {
            int idSubject = Integer.parseInt(idParam);
            LessonDBContext lessonDB = new LessonDBContext();
            SubjectDBContext subjectDB = new SubjectDBContext();
            request.setAttribute("subject", subjectDB.get(idSubject));
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.setAttribute("lessons", lessonDB.findTopicBySubjectId(idSubject));
            request.getRequestDispatcher("/views/admin/lesson/lesson_subject.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().println("Not found subject");
        }
    }

    static void lessonDeleteSelected(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    static void lessonCreateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<String> paths = getPaths(request);
        String idParam = paths.get(0);
        try {
            int idSubject = Integer.parseInt(idParam);
            SubjectDBContext subjectDB = new SubjectDBContext();
            request.setAttribute("subject", subjectDB.get(idSubject));
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("types", settingDB.getByType("TYPE_LESSON"));
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            request.setAttribute("quizzes", quizzesDB.list());
            PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
            request.setAttribute("prices", packagePriceDBContext.findBySubjectId(idSubject));
            LessonDBContext lessonDB = new LessonDBContext();
            request.setAttribute("topics", lessonDB.findBySubjectId(idSubject));
            request.getRequestDispatcher("/views/admin/lesson/lesson_create.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().println("Not found subject");
        }

    }

    static void lessonEditGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<String> paths = getPaths(request);
        String idSubjectParam = paths.get(0);
        String idParam = paths.get(3);
        try {
            int idSubject = Integer.parseInt(idSubjectParam);
            SubjectDBContext subjectDB = new SubjectDBContext();
            request.setAttribute("subject", subjectDB.get(idSubject));
            
            LessonDBContext lessonDB = new LessonDBContext();
            int id = Integer.parseInt(idParam);
            Lesson lesson = lessonDB.get(id);
            if (lesson == null) {
                throw new Exception();
            }

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("types", settingDB.getByType("TYPE_LESSON"));
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            request.setAttribute("quizzes", quizzesDB.list());
            PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
            request.setAttribute("prices", packagePriceDBContext.findBySubjectId(idSubject));

            request.setAttribute("topics", lessonDB.findBySubjectId(idSubject));
            request.setAttribute("lesson", lesson);
            request.getRequestDispatcher("/views/admin/lesson/lesson_edit.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().println("Not found by id");
        }
    }

    static void lessonDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<String> paths = getPaths(request);
        String idSubjectParam = paths.get(0);
        String idParam = paths.get(3);
        try {
            int idSubject = Integer.parseInt(idSubjectParam);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject =  subjectDB.get(idSubject);
            request.setAttribute("subject", subject);
            LessonDBContext lessonDB = new LessonDBContext();
            int id = Integer.parseInt(idParam);
            Lesson lesson = lessonDB.get(id);
            if (lesson == null) {
                throw new Exception();
            }
            lessonDB.delete(lesson.getId());
            String message = "Delete lesson success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/subject/" + subject.getId() + "/lesson?code=" + code + "&message=" + message);
        } catch (Exception e) {
            response.getWriter().println("Not found by id");
        }
    }

    static void lessonDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<String> paths = getPaths(request);
        String idSubjectParam = paths.get(0);
        String idParam = paths.get(2);
        try {
            int idSubject = Integer.parseInt(idSubjectParam);
            SubjectDBContext subjectDB = new SubjectDBContext();
            request.setAttribute("subject", subjectDB.get(idSubject));
            LessonDBContext lessonDB = new LessonDBContext();
            int id = Integer.parseInt(idParam);
            Lesson lesson = lessonDB.get(id);
            if (lesson == null) {
                throw new Exception();
            }

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("types", settingDB.getByType("TYPE_LESSON"));
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            request.setAttribute("quizzes", quizzesDB.list());
            PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
            request.setAttribute("prices", packagePriceDBContext.findBySubjectId(idSubject));

            request.setAttribute("topics", lessonDB.findBySubjectId(idSubject));
            request.setAttribute("lesson", lesson);
            request.getRequestDispatcher("/views/admin/lesson/lesson_detail.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().println("Not found by id");
        }
    }

    static void lessonCreatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<String> paths = getPaths(request);
        String idParam = paths.get(0);
        try {
            int idSubject = Integer.parseInt(idParam);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            if (subject == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("subject", subject);

            Validate validate = new Validate();
            String name = validate.getField(request, "name", true);
            String content = validate.getField(request, "content", false);
            String typeParam = validate.getField(request, "type", true);
            String youtube = validate.getField(request, "youtube", false);
            String orderParam = validate.getField(request, "order", true);
            String topicParam = validate.getField(request, "topic", false);
            String quizzesParam = validate.getField(request, "quizzes", false);
            String[] priceParams = validate.getFieldsAjax(request, "price-package", false);
            String[] dashs = validate.getFieldsAjax(request, "listDash", false);
            String[] images = validate.getFieldsAjax(request, "listImage", false);

            if (priceParams == null || priceParams.length <= 0) {
                throw new Exception("Price package is required!");
            }

            int typeId = validate.fieldInt(typeParam, "Error set field type");
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            SettingDBContext settingDB = new SettingDBContext();
            Setting type = settingDB.get(typeId);
            Lesson lesson = new Lesson();
            int order = validate.fieldInt(orderParam, "Error set field order!");
            lesson.setStatus(true);
            lesson.setName(name);
            lesson.setOrder(order);
            lesson.setSubject(subject);
            lesson.setSubjectId(idSubject);
            lesson.setType(type);
            lesson.setTypeid(typeId);
            LessonDBContext lessonDB = new LessonDBContext();
            ArrayList<PackagePrice> packagePrices = new ArrayList<>();
            for (String priceParam : priceParams) {
                int idPrice = validate.fieldInt(priceParam, "Error set field price");
                PackagePrice packagePrice = new PackagePrice();
                packagePrice.setId(idPrice);
                packagePrices.add(packagePrice);
            }
            lesson.setPackagePrices(packagePrices);
            if (type.getValue().toLowerCase().contains("topic")) {
                lesson.setTopic(null);
                lesson.setVideo(null);
                lesson.setContent(null);
                if (quizzesParam != null && !quizzesParam.trim().equalsIgnoreCase("none")) {
                    int quizzId = validate.fieldInt(quizzesParam, "Error set field quizzes");
                    Quizzes quizzes = quizzesDB.get(quizzId);
                    lesson.setQuizzesid(quizzId);
                    lesson.setQuizzes(quizzes);
                }
            } else if (type.getValue().toLowerCase().contains("lesson")) {
                lesson.setQuizzes(null);
                int idTopic = validate.fieldInt(topicParam, "Error set field topic!");
                Lesson topic = lessonDB.get(idTopic);
                lesson.setTopicId(idTopic);
                if (dashs != null && dashs.length > 0) {
                    FileUtility fileUtility = new FileUtility();
                    String folder = request.getServletContext().getRealPath("/images/lesson/media");
                    List<String> listDashs = new ArrayList<>();
                    FileLessonDBContext imageDB = new FileLessonDBContext();
                    for (String image : dashs) {
                        if (image.contains("/images/lesson/media/")) {
                            String uri = request.getScheme()
                                    + "://"
                                    + request.getServerName()
                                    + ":"
                                    + request.getServerPort()
                                    + request.getContextPath();
                            String img = image.replace(uri, "");
                            img = img.replace(request.getContextPath() + "/images/lesson/media/", "");
                            fileUtility.delete(img, folder);
                            listDashs.add(img);
                        }
                    }
                    if (listDashs != null && !listDashs.isEmpty()) {
                        imageDB.deleteByImages(listDashs);
                    }
                }
                lesson.setTopic(topic);
                if ((youtube == null || youtube.trim().isEmpty()) && (content == null || content.trim().isEmpty())) {
                    throw new Exception("Video or Content not empty");
                }
                if (youtube != null && !youtube.trim().isEmpty()) {
                    lesson.setVideo(youtube);
                }
                if (content != null && !content.trim().isEmpty()) {
                    lesson.setContent(content);
                }
            } else if (type.getValue().toLowerCase().contains("quiz")) {
                lesson.setVideo(null);
                lesson.setContent(null);
                int idTopic = validate.fieldInt(topicParam, "Error set field topic!");
                Lesson topic = lessonDB.get(idTopic);
                lesson.setTopicId(idTopic);
                lesson.setTopic(topic);
                int quizzId = validate.fieldInt(quizzesParam, "Error set field quizzes");
                Quizzes quizzes = quizzesDB.get(quizzId);
                lesson.setQuizzesid(quizzId);
                lesson.setQuizzes(quizzes);
            } else {
                throw new Exception("Some thing error!");
            }

            lesson = lessonDB.insert(lesson);
            if (type.getValue().toLowerCase().contains("lesson")) {
                try {
                    if (lesson != null && images != null && images.length > 0) {
                        System.out.println(Arrays.toString(images));
                        List<FileLesson> listFiles = new ArrayList<>();
                        FileLessonDBContext fileLesontDB = new FileLessonDBContext();
                        for (String image : images) {
                            if (image.contains("/images/lesson/media/")) {
                                String uri = request.getScheme()
                                        + "://"
                                        + request.getServerName()
                                        + ":"
                                        + request.getServerPort()
                                        + request.getContextPath();
                                String img = image.replace(uri, "");
                                img = img.replace(request.getContextPath() + "/images/lesson/media/", "");
                                System.out.println(img);
                                FileLesson fileLesson = fileLesontDB.findByUUID(img);
                                System.out.println("file-lesson: " + fileLesson.getFile());
                                if (fileLesson != null) {
                                    System.out.println(fileLesson.getFile());
                                    fileLesson.setLesson(lesson);
                                    fileLesson.setLessonId(lesson.getId());
                                    listFiles.add(fileLesson);
                                }
                            }
                        }
                        if (listFiles != null && !listFiles.isEmpty()) {
                            fileLesontDB.updateManyFile(listFiles);
                        }
                    }
                } catch (Exception e) {
                    String message = "Create post success! But error save content image!";
                    String code = "success";
                    response.sendRedirect(request.getContextPath() + "/admin/post?code=" + code + "&message=" + message);
                    return;
                }
            }
            String message = "Create lesson success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/subject/" + subject.getId() + "/lesson?code=" + code + "&message=" + message);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found subject");
        } catch (Exception e) {
            int idSubject = Integer.parseInt(idParam);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            if (subject == null) {
                throw new NumberFormatException();
            }
            if (request.getParameter("name") != null) {
                request.setAttribute("name", new String(request.getParameter("name").getBytes("iso-8859-1"), "utf-8"));
            }
            if (request.getParameter("content") != null) {
                request.setAttribute("content", new String(request.getParameter("content").getBytes("iso-8859-1"), "utf-8"));
            }
            request.setAttribute("type", request.getParameter("type"));
            request.setAttribute("youtube", request.getParameter("youtube"));
            request.setAttribute("quizze", request.getParameter("quizzes"));
            request.setAttribute("order", request.getParameter("order"));
            request.setAttribute("topic", request.getParameter("topic"));

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("types", settingDB.getByType("TYPE_LESSON"));
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            request.setAttribute("quizzes", quizzesDB.list());
            PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
            request.setAttribute("prices", packagePriceDBContext.findBySubjectId(idSubject));
            LessonDBContext lessonDB = new LessonDBContext();
            request.setAttribute("topics", lessonDB.findBySubjectId(idSubject));
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/admin/lesson/lesson_create.jsp").forward(request, response);
        }
    }

    static void lessonEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ArrayList<String> paths = getPaths(request);
        String idSubjectParam = paths.get(0);
        String idParam = paths.get(3);
        try {
            int idSubject = Integer.parseInt(idSubjectParam);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            if (subject == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("subject", subject);

            int id = Integer.parseInt(idParam);
            LessonDBContext lessonDB = new LessonDBContext();
            Lesson lesson = lessonDB.get(id);

            Validate validate = new Validate();
            String name = validate.getField(request, "name", true);
            String content = validate.getField(request, "content", false);
            String typeParam = validate.getField(request, "type", true);
            String youtube = validate.getField(request, "youtube", false);
            String orderParam = validate.getField(request, "order", true);
            String statusParam = validate.getField(request, "status", true);
            String topicParam = validate.getField(request, "topic", false);
            String quizzesParam = validate.getField(request, "quizzes", false);
            String[] priceParams = validate.getFieldsAjax(request, "price-package", false);

            if (priceParams == null || priceParams.length <= 0) {
                throw new Exception("Price package is required!");
            }

            int typeId = validate.fieldInt(typeParam, "Error set field type");
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            SettingDBContext settingDB = new SettingDBContext();
            Setting type = settingDB.get(typeId);
            int order = validate.fieldInt(orderParam, "Error set field order!");
            boolean status = statusParam.equals("status_true");
            lesson.setStatus(true);
            lesson.setName(name);
            lesson.setOrder(order);
            lesson.setStatus(status);
            lesson.setSubject(subject);
            lesson.setSubjectId(idSubject);
            lesson.setType(type);
            lesson.setTypeid(typeId);
            ArrayList<PackagePrice> packagePrices = new ArrayList<>();
            for (String priceParam : priceParams) {
                int idPrice = validate.fieldInt(priceParam, "Error set field price");
                PackagePrice packagePrice = new PackagePrice();
                packagePrice.setId(idPrice);
                packagePrices.add(packagePrice);
            }
            lesson.setPackagePrices(packagePrices);
            if (type.getValue().toLowerCase().contains("topic")) {
                lesson.setTopic(null);
                lesson.setVideo(null);
                lesson.setContent(null);
                if (quizzesParam != null && !quizzesParam.trim().equalsIgnoreCase("none")) {
                    int quizzId = validate.fieldInt(quizzesParam, "Error set field quizzes");
                    Quizzes quizzes = quizzesDB.get(quizzId);
                    lesson.setQuizzesid(quizzId);
                    lesson.setQuizzes(quizzes);
                }
            } else if (type.getValue().toLowerCase().contains("lesson")) {
                int idTopic = validate.fieldInt(topicParam, "Error set field topic!");
                Lesson topic = lessonDB.get(idTopic);
                lesson.setTopicId(idTopic);
                lesson.setQuizzes(null);

                lesson.setTopic(topic);
                if ((youtube == null || youtube.trim().isEmpty()) && (content == null || content.trim().isEmpty())) {
                    throw new Exception("Video or Content not empty");
                }
                if (youtube != null && !youtube.trim().isEmpty()) {
                    lesson.setVideo(youtube);
                }
                if (content != null && !content.trim().isEmpty()) {
                    lesson.setContent(content);
                }
            } else if (type.getValue().toLowerCase().contains("quiz")) {
                lesson.setVideo(null);
                lesson.setContent(null);
                int idTopic = validate.fieldInt(topicParam, "Error set field topic!");
                Lesson topic = lessonDB.get(idTopic);
                lesson.setTopicId(idTopic);
                lesson.setTopic(topic);
                int quizzId = validate.fieldInt(quizzesParam, "Error set field quizzes");
                Quizzes quizzes = quizzesDB.get(quizzId);
                lesson.setQuizzesid(quizzId);
                lesson.setQuizzes(quizzes);
            } else {
                throw new Exception("Some thing error!");
            }

            lessonDB.update(lesson);
            String message = "Update lesson success";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/admin/subject/" + subject.getId() + "/lesson?code=" + code + "&message=" + message);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found subject");
        } catch (Exception e) {
            int idSubject = Integer.parseInt(idParam);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            request.setAttribute("subject", subject);

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("types", settingDB.getByType("TYPE_LESSON"));
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            request.setAttribute("quizzes", quizzesDB.list());
            PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
            request.setAttribute("prices", packagePriceDBContext.findBySubjectId(idSubject));
            LessonDBContext lessonDB = new LessonDBContext();
            request.setAttribute("topics", lessonDB.findBySubjectId(idSubject));
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/views/admin/lesson/lesson_create.jsp").forward(request, response);
        }
    }

    static void searchLesson(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String subjectId = validate.getFieldAjax(request, "subjectId", true);
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
            int id = Integer.parseInt(subjectId);

            LessonDBContext lessonDB = new LessonDBContext();
            ArrayList<Lesson> lessons = lessonDB.searchSelected(id, search, pageIndex, 10);
            response.setStatus(HttpServletResponse.SC_OK);
            Res<Lesson> res = new Res(lessons, pageIndex, 10);
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

    static void searchLessonAll(HttpServletRequest request, HttpServletResponse response)
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
            if (search == null) {
                search = "";
            }
            
            Pageable pageable = new Pageable();
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(pageSize);
            
            
            
            LessonDBContext lessonDB = new LessonDBContext();
            
            ArrayList<Lesson> lessons = lessonDB.list();
            
            request.setAttribute("lessons", lessons);
            
            request.getRequestDispatcher("/views/admin/lesson/lesson.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LessonController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static void mediaUpload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idParam = validate.getFieldAjax(request, "id", false);
            Part part = request.getPart("file");
            FileUtility fileUtility = new FileUtility();

            String folder = request.getServletContext().getRealPath("/images/lesson/media");
            String filename = null;
            if (part.getSize() != 0) {
                filename = fileUtility.upLoad(part, folder);
                FileLesson file = new FileLesson();
                file.setFile(filename);
                if (idParam != null && !idParam.trim().isEmpty() && idParam.matches("^[0-9]+$")) {
                    int id = Integer.parseInt(idParam);
                    file.setLessonId(id);
                }
                FileLessonDBContext imageDB = new FileLessonDBContext();
                file = imageDB.insert(file);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("READY:" + request.getContextPath() + "/images/lesson/media/" + file.getFile());
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

    static void mediaDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String[] images = validate.getFieldsAjax(request, "images[]", false);
            System.out.println(Arrays.toString(images));
            if (images != null && images.length > 0) {
                FileUtility fileUtility = new FileUtility();
                String folder = request.getServletContext().getRealPath("/images/lesson/media");
                List<String> listImages = new ArrayList<>();
                FileLessonDBContext imageDB = new FileLessonDBContext();
                for (String image : images) {
                    if (image.contains("/images/lesson/media/")) {
                        String uri = request.getScheme()
                                + "://"
                                + request.getServerName()
                                + ":"
                                + request.getServerPort()
                                + request.getContextPath();
                        String img = image.replace(uri, "");
                        img = img.replace(request.getContextPath() + "/images/lesson/media/", "");
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
}

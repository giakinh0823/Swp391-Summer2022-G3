/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.admin;

import com.google.gson.Gson;
import com.se1617.learning.config.url.admin.UrlQuestionAdmin;
import com.se1617.learning.controller.base.BaseAuthController;
import com.se1617.learning.dal.question.AnswerDBContext;
import com.se1617.learning.dal.question.AnswerQuestionDBContext;
import com.se1617.learning.dal.question.FileQuestionDBContext;
import com.se1617.learning.dal.question.MediaDBContext;
import com.se1617.learning.dal.question.QuestionDBContext;
import com.se1617.learning.dal.setting.SettingDBContext;
import com.se1617.learning.dal.subject.DimensionDBContext;
import com.se1617.learning.dal.subject.LessonDBContext;
import com.se1617.learning.dal.subject.SubjectDBContext;
import com.se1617.learning.model.general.Message;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.Res;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.question.Answer;
import com.se1617.learning.model.question.AnswerQuestion;
import com.se1617.learning.model.question.FileQuestion;
import com.se1617.learning.model.question.Media;
import com.se1617.learning.model.question.Question;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.subject.Dimension;
import com.se1617.learning.model.subject.Lesson;
import com.se1617.learning.model.subject.Subject;
import com.se1617.learning.model.user.User;
import com.se1617.learning.utils.FileUtility;
import com.se1617.learning.utils.Validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author giaki
 */
@MultipartConfig
public class QuestionController extends BaseAuthController {

    private static final int pageSize = 15;

    @Override
    protected boolean isPermission(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user.is_staff();
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlQuestionAdmin.QUESTION_DELETE_SELETED)) {
                questionDeleteSelected(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_ANSWER_SEARCH)) {
                answerSearch(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_ANSWER_GET)) {
                answerGet(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_STATUS_CHANGE)) {
                questionStatusChange(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_CREATE)) {
                questionCreateGet(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_IMPORT)) {
                questionImportGet(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_EDIT)) {
                questionEditGet(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_DELETE)) {
                questionDelete(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_DETAIL)) {
                questionDetail(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_LIST)) {
                questionList(request, response);
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
            if (super.getURI().matches(UrlQuestionAdmin.QUESTION_MEDIA_UPLOAD)) {
                mediaUpload(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_MEDIA_DELETE)) {
                mediaDelete(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_DELETE_SELETED)) {
                questionDeleteSelected(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_ANSWER_EDIT)) {
                answerEdit(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_ANSWER_DELETE)) {
                answerDelete(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_ANSWER_CREATE)) {
                createAnswer(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_STATUS_CHANGE)) {
                questionStatusChange(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_CREATE)) {
                questionCreatePost(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_IMPORT)) {
                questionImportPost(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_EDIT)) {
                questionEditPost(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_DELETE)) {
                questionDelete(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_DETAIL)) {
                questionDetail(request, response);
            } else if (super.getURI().matches(UrlQuestionAdmin.QUESTION_LIST)) {
                questionList(request, response);
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

    // ------------------------GET METHOD -------------------------//
    private void questionDeleteSelected(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void questionEditGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("backlink", request.getContextPath() + "/admin/question");
        String idParams = super.getPaths().get(1);
        try {
            int id = Integer.parseInt(idParams);
            QuestionDBContext questionDB = new QuestionDBContext();
            Question question = questionDB.get(id);
            if (question == null) {
                throw new Exception();
            }
            request.setAttribute("question", question);
            SubjectDBContext subjectDB = new SubjectDBContext();
            request.setAttribute("subjects", subjectDB.searchSubject(question.getSubject().getName(), 1, 30));
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("levels", settingDB.getByType("LEVEL_QUESTION"));
            DimensionDBContext dimensionDB = new DimensionDBContext();
            request.setAttribute("dimensions", dimensionDB.findBySubjectId(question.getSubjectId()));

            LessonDBContext lessonDB = new LessonDBContext();
            request.setAttribute("lessons", lessonDB.searchSelected(question.getSubjectId(), question.getLesson().getName(), 1, 10));
            request.getRequestDispatcher("/views/admin/question/question_edit.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().print("Can't found question id");
        }
    }

    private void questionCreateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("backlink", request.getContextPath() + "/admin/question");
        SubjectDBContext subjectDB = new SubjectDBContext();
        request.setAttribute("subjects", subjectDB.searchSubject("", 1, 30));
        SettingDBContext settingDB = new SettingDBContext();
        request.setAttribute("levels", settingDB.getByType("LEVEL_QUESTION"));
        request.getRequestDispatcher("/views/admin/question/question_create.jsp").forward(request, response);
    }

    private void questionDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String paramsId = super.getPaths().get(1);
            int id = Integer.parseInt(paramsId);
            QuestionDBContext questionDB = new QuestionDBContext();
            questionDB.delete(id);
            if (request.getHeader("referer").toLowerCase().contains("/admin/question/" + id) && !request.getHeader("referer").toLowerCase().contains("/edit/")) {
                response.sendRedirect(request.getContextPath() + "/admin/question");
            } else {
                response.sendRedirect(request.getHeader("referer"));
            }
        } catch (Exception e) {
            response.getWriter().print("Not found question!");
        }
    }

    private void questionDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("backlink", request.getContextPath() + "/admin/question");
        String idParams = super.getPaths().get(0);
        try {
            int id = Integer.parseInt(idParams);
            QuestionDBContext questionDB = new QuestionDBContext();
            Question question = questionDB.get(id);
            if (question == null) {
                throw new Exception();
            }
            request.setAttribute("question", question);
            SubjectDBContext subjectDB = new SubjectDBContext();
            request.setAttribute("subjects", subjectDB.searchSubject(question.getSubject().getName(), 1, 30));
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("levels", settingDB.getByType("LEVEL_QUESTION"));
            DimensionDBContext dimensionDB = new DimensionDBContext();
            request.setAttribute("dimensions", dimensionDB.findBySubjectId(question.getSubjectId()));

            LessonDBContext lessonDB = new LessonDBContext();
            request.setAttribute("lessons", lessonDB.searchSelected(question.getSubjectId(), question.getLesson().getName(), 1, 10));
            request.getRequestDispatcher("/views/admin/question/question_detail.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().print("Can't found question id");
        }
    }

    private void questionList(HttpServletRequest request, HttpServletResponse response)
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

            Map<String, String> orderMap = new HashMap<>();
            String[] sorts = validate.getFieldsAjax(request, "sort", false);
            if (sorts != null && sorts.length > 0) {
                int count = 0;
                for (String ordering : sorts) {
                    if (ordering != null && ordering.matches("^[a-zA-z]{1,}_(DESC|ASC)$")) {
                        String[] sort = ordering.split("_", 2);
                        if (sort[0].equalsIgnoreCase("id")
                                || sort[0].equalsIgnoreCase("level")
                                || sort[0].equalsIgnoreCase("status")
                                || sort[0].equalsIgnoreCase("multi")
                                || sort[0].equalsIgnoreCase("dimension")
                                || sort[0].equalsIgnoreCase("lesson")
                                || sort[0].equalsIgnoreCase("subject")) {
                            orderMap.put(++count + "." + sort[0], sort[1]);
                        }
                    }
                }
            }

            String[] status = validate.getFieldsAjax(request, "status", false);
            String[] levels = validate.getFieldsAjax(request, "level", false);
            String[] multis = validate.getFieldsAjax(request, "multi", false);

            String[] subjects = validate.getFieldsAjax(request, "subject", false);
            String[] lessons = validate.getFieldsAjax(request, "lesson", false);
            String[] dimensions = validate.getFieldsAjax(request, "dimension", false);

            Map<String, ArrayList<String>> filters = new HashMap<>();

            if (subjects != null && subjects.length > 0) {
                ArrayList<String> list = new ArrayList<>();
                for (String item : subjects) {
                    if (item.matches("^[0-9]+$")) {
                        list.add(item);
                    }
                }
                if (!list.isEmpty()) {
                    filters.put("subject", list);
                }
            }

            if (lessons != null && lessons.length > 0) {
                ArrayList<String> list = new ArrayList<>();
                for (String item : lessons) {
                    if (item.matches("^[0-9]+$")) {
                        list.add(item);
                    }
                }
                if (!list.isEmpty()) {
                    filters.put("lesson", list);
                }
            }

            if (dimensions != null && dimensions.length > 0) {
                ArrayList<String> list = new ArrayList<>();
                for (String item : dimensions) {
                    if (item.matches("^[0-9]+$")) {
                        list.add(item);
                    }
                }
                if (!list.isEmpty()) {
                    filters.put("dimension", list);
                }
            }

            if (levels != null && levels.length > 0) {
                ArrayList<String> listLevels = new ArrayList<>();
                for (String level : levels) {
                    if (level.matches("^[0-9]+$")) {
                        listLevels.add(level);
                    }
                }
                if (!listLevels.isEmpty()) {
                    filters.put("level", listLevels);
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

            if (multis != null && multis.length > 0) {
                ArrayList<String> listMultis = new ArrayList<>();
                for (String multi : multis) {
                    if (multi.equalsIgnoreCase("true") || multi.equalsIgnoreCase("false")) {
                        listMultis.add(multi);
                    }
                }
                if (!listMultis.isEmpty()) {
                    filters.put("multi", listMultis);
                }
            }
            pageable.setFilters(filters);
            SettingDBContext settingDB = new SettingDBContext();
            pageable.setOrderings(orderMap);
            request.setAttribute("pageable", pageable);
            QuestionDBContext questionDB = new QuestionDBContext();
            ResultPageable resultPageable = questionDB.searchAndSortAndFilterMulti(search, pageable);
            request.setAttribute("questions", resultPageable.getList());
            request.setAttribute("page", resultPageable.getPagination());
            request.setAttribute("levels", settingDB.getByType("LEVEL_QUESTION"));
            SubjectDBContext subjectDB = new SubjectDBContext();
            request.setAttribute("subjects", subjectDB.searchSubject("", 1, 30));
            request.getRequestDispatcher("/views/admin/question/question.jsp").forward(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            Message message = new Message();
            message.setCode("error");
            message.setMessage(e.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            throw new Error(e.getMessage());
        }
    }

    private void answerSearch(HttpServletRequest request, HttpServletResponse response)
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

            AnswerDBContext answerDB = new AnswerDBContext();
            ArrayList<Answer> answers = answerDB.findBySubect(id, search, pageIndex, 10);
            response.setStatus(HttpServletResponse.SC_OK);
            Res<Answer> res = new Res(answers, pageIndex, 10);
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

    private void answerGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idParams = validate.getFieldAjax(request, "id", true);
            int id = Integer.parseInt(idParams);
            AnswerDBContext answerDB = new AnswerDBContext();
            Answer answer = answerDB.get(id);
            response.setStatus(HttpServletResponse.SC_OK);
            String json = new Gson().toJson(answer);
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

    private void questionImportGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("backlink", request.getContextPath() + "/admin/question");
        SubjectDBContext subjectDB = new SubjectDBContext();
        request.setAttribute("subjects", subjectDB.searchSubject("", 1, 30));
        SettingDBContext settingDB = new SettingDBContext();
        request.setAttribute("levels", settingDB.getByType("LEVEL_QUESTION"));
        request.getRequestDispatcher("/views/admin/question/question_import.jsp").forward(request, response);
    }

    //--------------------------POST METHOD--------------------------------//
    private void questionCreatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idSubjectParam = validate.getField(request, "subject", true);
            String idLessonParam = validate.getField(request, "lesson", true);
            String idDimensionParam = validate.getField(request, "dimension", true);
            String idLevelParam = validate.getField(request, "level", true);
            String content = validate.getField(request, "content", false);
            String status = validate.getField(request, "status", false);
            String explain = validate.getField(request, "explain", false);
            String contentNoHtml = validate.getField(request, "content-no-html", false);
            String[] answerParams = validate.getFieldsAjax(request, "answer", true);
            String[] answerResultParams = validate.getFieldsAjax(request, "answer-result", true);

            if (answerParams == null || answerParams.length <= 0) {
                throw new Exception("Please add answer!");
            }

            if (answerResultParams == null || answerResultParams.length <= 0) {
                throw new Exception("Please choose result for answer!");
            }

            Part part = request.getPart("media");
            FileUtility fileUtility = new FileUtility();

            if (part.getSize() <= 0 && (content == null || content.trim().isEmpty())) {
                throw new Exception("Please enter content or media!");
            }

            int idSubject = validate.fieldInt(idSubjectParam, "Error set field subject!");
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            if (subject == null) {
                throw new Exception("Error set field subject!");
            }

            int idLesson = validate.fieldInt(idLessonParam, "Error set field lesson!");
            LessonDBContext lessonDB = new LessonDBContext();
            Lesson lesson = lessonDB.get(idLesson);
            if (lesson == null) {
                throw new Exception("Error set field lesson!");
            }

            int idDimension = validate.fieldInt(idDimensionParam, "Error set field dimension!");
            DimensionDBContext dimensionDB = new DimensionDBContext();
            Dimension dimension = dimensionDB.get(idDimension);
            if (dimension == null) {
                throw new Exception("Error set field dimension!");
            }

            int idLevel = validate.fieldInt(idLevelParam, "Error set field level!");
            SettingDBContext settingDB = new SettingDBContext();
            Setting setting = settingDB.get(idLevel);
            if (setting == null) {
                throw new Exception("Error set field level!");
            }

            User user = (User) request.getSession().getAttribute("user");
            Question question = new Question();
            question.setSubjectId(idSubject);
            question.setSubject(subject);
            question.setDimensionId(idDimension);
            question.setDimension(dimension);
            question.setLessonId(idLesson);
            question.setLesson(lesson);
            question.setLevelId(idLevel);
            question.setLevel(setting);
            question.setContent(contentNoHtml.trim());
            question.setContentHtml(content.trim());
            question.setExplain(explain.trim());
            question.setUser(user);
            question.setUserId(user.getId());
            question.set_multi(answerResultParams != null && answerResultParams.length > 1);
            question.setStatus(status != null && !status.trim().isEmpty() && status.equalsIgnoreCase("true"));

            QuestionDBContext questionDB = new QuestionDBContext();
            question = questionDB.insert(question);

            if (part.getSize() != 0) {
                String folder = request.getServletContext().getRealPath("/images/question");
                String filename = fileUtility.upLoad(part, folder);
                MediaDBContext mediaDB = new MediaDBContext();
                Media media = new Media();
                media.setQuestionId(question.getId());
                media.setType(fileUtility.getType(part));
                media.setUrl(filename);
                media = mediaDB.insert(media);
                question.setMedia(media);
            }
            ArrayList<AnswerQuestion> answerQuestions = new ArrayList<>();
            for (String answer : answerParams) {
                int idAnswer = Integer.parseInt(answer);
                boolean is_correct = false;
                for (String answerResult : answerResultParams) {
                    int IdanswerResult = Integer.parseInt(answerResult);
                    if (idAnswer == IdanswerResult) {
                        is_correct = true;
                        break;
                    }
                }
                AnswerQuestion answerQuestion = new AnswerQuestion();
                answerQuestion.setQuestionId(question.getId());
                answerQuestion.setAnswerId(idAnswer);
                answerQuestion.set_correct(is_correct);
                answerQuestions.add(answerQuestion);
            }
            AnswerQuestionDBContext answerQuestionDB = new AnswerQuestionDBContext();
            answerQuestionDB.insertMulti(answerQuestions);
            question.setAnswers(answerQuestions);

            String[] dashs = validate.getFieldsAjax(request, "listDash", false);
            String[] images = validate.getFieldsAjax(request, "listImage", false);
            if (dashs != null && dashs.length > 0) {
                String folder = request.getServletContext().getRealPath("/images/question/media");
                List<String> listDashs = new ArrayList<>();
                FileQuestionDBContext imageDB = new FileQuestionDBContext();
                for (String image : dashs) {
                    if (image.contains("/images/question/media/")) {
                        String uri = request.getScheme()
                                + "://"
                                + request.getServerName()
                                + ":"
                                + request.getServerPort()
                                + request.getContextPath();
                        String img = image.replace(uri, "");
                        img = img.replace(request.getContextPath() + "/images/question/media/", "");
                        fileUtility.delete(img, folder);
                        listDashs.add(img);
                    }
                }
                if (listDashs != null && !listDashs.isEmpty()) {
                    imageDB.deleteByImages(listDashs);
                }
            }

            try {
                if (question != null && images != null && images.length > 0) {
                    System.out.println(Arrays.toString(images));
                    List<FileQuestion> listFiles = new ArrayList<>();
                    FileQuestionDBContext fileQuestionDB = new FileQuestionDBContext();
                    for (String image : images) {
                        if (image.contains("/images/question/media/")) {
                            String uri = request.getScheme()
                                    + "://"
                                    + request.getServerName()
                                    + ":"
                                    + request.getServerPort()
                                    + request.getContextPath();
                            String img = image.replace(uri, "");
                            img = img.replace(request.getContextPath() + "/images/question/media/", "");
                            System.out.println(img);
                            FileQuestion fileQuestion = fileQuestionDB.findByUUID(img);
                            if (fileQuestion != null) {
                                System.out.println(fileQuestion.getFile());
                                fileQuestion.setQuestion(question);
                                fileQuestion.setQuestionId(question.getId());
                                listFiles.add(fileQuestion);
                            }
                        }
                    }
                    if (listFiles != null && !listFiles.isEmpty()) {
                        fileQuestionDB.updateManyFile(listFiles);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error update file question!");
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Gson gson = new Gson();
            String json = gson.toJson(question);
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Message message = new Message();
            message.setCode("error");
            message.setMessage(e.getMessage());
            Gson gson = new Gson();
            String json = gson.toJson(message);
            response.getWriter().write(json);
        }
    }

    private void questionEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idParam = validate.getField(request, "id", true);
            String idSubjectParam = validate.getField(request, "subject", true);
            String idLessonParam = validate.getField(request, "lesson", true);
            String idDimensionParam = validate.getField(request, "dimension", true);
            String idLevelParam = validate.getField(request, "level", true);
            String content = validate.getField(request, "content", false);
            String status = validate.getField(request, "status", false);
            String explain = validate.getField(request, "explain", false);
            String contentNoHtml = validate.getField(request, "content-no-html", false);
            String[] answerParams = validate.getFieldsAjax(request, "answer", true);
            String[] answerResultParams = validate.getFieldsAjax(request, "answer-result", true);
            String removeMedia = validate.getField(request, "removeMedia", false);
            boolean is_removeMedia = removeMedia != null && !removeMedia.trim().isEmpty() && removeMedia.equalsIgnoreCase("true");

            int id = validate.fieldInt(idParam, "Can't update question!");

            if (answerParams == null || answerParams.length <= 0) {
                throw new Exception("Please add answer!");
            }

            if (answerResultParams == null || answerResultParams.length <= 0) {
                throw new Exception("Please choose result for answer!");
            }

            Part part = request.getPart("media");
            FileUtility fileUtility = new FileUtility();

            if (part.getSize() <= 0 && (content == null || content.trim().isEmpty())) {
                throw new Exception("Please enter content or media!");
            }

            int idSubject = validate.fieldInt(idSubjectParam, "Error set field subject!");
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            if (subject == null) {
                throw new Exception("Error set field subject!");
            }

            int idLesson = validate.fieldInt(idLessonParam, "Error set field lesson!");
            LessonDBContext lessonDB = new LessonDBContext();
            Lesson lesson = lessonDB.get(idLesson);
            if (lesson == null) {
                throw new Exception("Error set field lesson!");
            }

            int idDimension = validate.fieldInt(idDimensionParam, "Error set field dimension!");
            DimensionDBContext dimensionDB = new DimensionDBContext();
            Dimension dimension = dimensionDB.get(idDimension);
            if (dimension == null) {
                throw new Exception("Error set field dimension!");
            }

            int idLevel = validate.fieldInt(idLevelParam, "Error set field level!");
            SettingDBContext settingDB = new SettingDBContext();
            Setting setting = settingDB.get(idLevel);
            if (setting == null) {
                throw new Exception("Error set field level!");
            }

            QuestionDBContext questionDB = new QuestionDBContext();
            Question question = questionDB.get(id);
            question.setSubjectId(idSubject);
            question.setSubject(subject);
            question.setDimensionId(idDimension);
            question.setDimension(dimension);
            question.setLessonId(idLesson);
            question.setLesson(lesson);
            question.setLevelId(idLevel);
            question.setLevel(setting);
            question.setContent(contentNoHtml.trim());
            question.setContentHtml(content);
            question.setExplain(explain.trim());
            question.set_multi(answerResultParams != null && answerResultParams.length > 1);
            question.setStatus(status != null && !status.trim().isEmpty() && status.equalsIgnoreCase("true"));

            if (is_removeMedia) {
                String folder = request.getServletContext().getRealPath("/images/question");
                if (question.getMedia() != null && question.getMedia().getUrl() != null && !question.getMedia().getUrl().isEmpty()) {
                    fileUtility.delete(question.getMedia().getUrl(), folder);
                    MediaDBContext mediaDB = new MediaDBContext();
                    mediaDB.delete(question.getMedia().getId());
                    question.setMedia(null);
                }
            }

            if (part.getSize() != 0) {
                String folder = request.getServletContext().getRealPath("/images/question");
                if (question.getMedia() != null && question.getMedia().getUrl() != null && !question.getMedia().getUrl().isEmpty()) {
                    fileUtility.delete(question.getMedia().getUrl(), folder);
                }
                String filename = fileUtility.upLoad(part, folder);
                MediaDBContext mediaDB = new MediaDBContext();
                Media media = question.getMedia();
                if (media != null) {
                    media.setQuestionId(question.getId());
                    media.setType(fileUtility.getType(part));
                    media.setUrl(filename);
                    mediaDB.update(media);
                    question.setMedia(media);
                } else {
                    media = new Media();
                    media.setQuestionId(question.getId());
                    media.setType(fileUtility.getType(part));
                    media.setUrl(filename);
                    mediaDB.insert(media);
                    question.setMedia(media);
                }
            }
            ArrayList<AnswerQuestion> answerQuestions = new ArrayList<>();
            for (String answer : answerParams) {
                int idAnswer = Integer.parseInt(answer);
                boolean is_correct = false;
                for (String answerResult : answerResultParams) {
                    int IdanswerResult = Integer.parseInt(answerResult);
                    if (idAnswer == IdanswerResult) {
                        is_correct = true;
                        break;
                    }
                }
                AnswerQuestion answerQuestion = new AnswerQuestion();
                answerQuestion.setQuestionId(question.getId());
                answerQuestion.setAnswerId(idAnswer);
                answerQuestion.set_correct(is_correct);
                answerQuestions.add(answerQuestion);
            }
            AnswerQuestionDBContext answerQuestionDB = new AnswerQuestionDBContext();
            answerQuestionDB.deleteByQuestionId(question.getId());
            answerQuestionDB.insertMulti(answerQuestions);
            question.setAnswers(answerQuestions);
            questionDB.update(question);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Gson gson = new Gson();
            String json = gson.toJson(question);
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Message message = new Message();
            message.setCode("error");
            message.setMessage(e.getMessage());
            Gson gson = new Gson();
            String json = gson.toJson(message);
            response.getWriter().write(json);
        }
    }

    private void createAnswer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idSubjectParam = validate.getFieldAjax(request, "subject", true);
            String text = validate.getField(request, "text", false);

            Part part = request.getPart("media");

            if ((text == null || text.trim().isEmpty()) && part.getSize() <= 0) {
                throw new Exception("Error create answer!Please enter content");
            };
            FileUtility fileUtility = new FileUtility();
            int idSubject = validate.fieldInt(idSubjectParam, "Error create answer!");
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            if (subject == null) {
                throw new Exception("Error create answer!");
            }
            Answer answer = new Answer();
            String folder = request.getServletContext().getRealPath("/images/answer");
            String filename = null;
            if (part.getSize() != 0) {
                filename = fileUtility.upLoad(part, folder);
                answer.setMedia(filename);
            }
            answer.setText(text);
            answer.setSubject(subject);
            answer.setSubjectId(idSubject);
            AnswerDBContext answerDB = new AnswerDBContext();
            answer = answerDB.insert(answer);
            Gson gson = new Gson();
            String json = gson.toJson(answer);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Message message = new Message();
            message.setCode("error");
            message.setMessage(e.getMessage());
            Gson gson = new Gson();
            String json = gson.toJson(message);
            response.getWriter().write(json);
        }
    }

    private void answerEdit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idSubjectParam = validate.getFieldAjax(request, "subject", true);
            String text = validate.getField(request, "text", false);
            String idParam = validate.getField(request, "id", true);
            String idQuestionParam = validate.getField(request, "question", false);
            String removeImage = validate.getField(request, "removeImage", false);

            boolean isRemoveImage = removeImage != null && !removeImage.trim().isEmpty() && removeImage.equalsIgnoreCase("true");

            Part part = request.getPart("media");

            if ((text == null || text.trim().isEmpty()) && part.getSize() <= 0) {
                throw new Exception("Error create answer!Please enter content");
            };
            FileUtility fileUtility = new FileUtility();
            int idSubject = validate.fieldInt(idSubjectParam, "Error create answer!");
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            if (subject == null) {
                throw new Exception("Error create answer!");
            }
            int id = validate.fieldInt(idParam, "Can't update answer!");
            AnswerQuestionDBContext answerQuestionDB = new AnswerQuestionDBContext();
            ArrayList<AnswerQuestion> answers = answerQuestionDB.findAnswerId(id);
            if (answers != null && answers.size() >= 2) {
                throw new Exception("Answer have in another questions! can't edit answer!");
            } else if (answers != null && idQuestionParam != null && idQuestionParam.matches("^[0-9]+$") && answers.size() == 1) {
                int idQuestion = Integer.parseInt(idQuestionParam);
                if (answers.get(0).getQuestionId() != idQuestion) {
                    throw new Exception("Answer have in another questions! can't edit answer!");
                }
            } else if (answers.size() == 1 && idQuestionParam == null) {
                throw new Exception("Answer have in another questions! can't edit answer!");
            }
            AnswerDBContext answerDB = new AnswerDBContext();
            Answer answer = answerDB.get(id);
            if (answer == null) {
                throw new Exception("Can't update answer!");
            }

            String folder = request.getServletContext().getRealPath("/images/answer");
            String filename = null;
            if (isRemoveImage) {
                if (answer.getMedia() != null) {
                    fileUtility.delete(answer.getMedia(), folder);
                }
                answer.setMedia(null);
            }

            if (part.getSize() != 0) {
                if (answer.getMedia() != null) {
                    fileUtility.delete(answer.getMedia(), folder);
                }
                filename = fileUtility.upLoad(part, folder);
                answer.setMedia(filename);
            }

            answer.setText(text);
            answer.setSubject(subject);
            answer.setSubjectId(idSubject);

            answerDB.update(answer);
            Gson gson = new Gson();
            String json = gson.toJson(answer);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Message message = new Message();
            message.setCode("error");
            message.setMessage(e.getMessage());
            Gson gson = new Gson();
            String json = gson.toJson(message);
            response.getWriter().write(json);
        }
    }

    private void mediaUpload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idParam = validate.getFieldAjax(request, "id", false);
            Part part = request.getPart("file");
            FileUtility fileUtility = new FileUtility();

            String folder = request.getServletContext().getRealPath("/images/question/media");
            String filename = null;
            if (part.getSize() != 0) {
                filename = fileUtility.upLoad(part, folder);
                FileQuestion media = new FileQuestion();
                media.setFile(filename);
                if (idParam != null && !idParam.trim().isEmpty() && idParam.matches("^[0-9]+$")) {
                    int id = Integer.parseInt(idParam);
                    media.setQuestionId(id);
                }
                FileQuestionDBContext fileQuestionDB = new FileQuestionDBContext();
                media = fileQuestionDB.insert(media);
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("READY:" + request.getContextPath() + "/images/question/media/" + media.getFile());
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
                String folder = request.getServletContext().getRealPath("/images/question/media");
                List<String> listImages = new ArrayList<>();
                FileQuestionDBContext fileQuestionDB = new FileQuestionDBContext();
                for (String image : images) {
                    if (image.contains("/images/question/media/")) {
                        String uri = request.getScheme()
                                + "://"
                                + request.getServerName()
                                + ":"
                                + request.getServerPort()
                                + request.getContextPath();
                        String img = image.replace(uri, "");
                        img = img.replace(request.getContextPath() + "/images/question/media/", "");
                        fileUtility.delete(img, folder);
                        listImages.add(img);
                    }
                }
                if (listImages != null && !listImages.isEmpty()) {
                    fileQuestionDB.deleteByImages(listImages);
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

    private void answerDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idParams = validate.getFieldAjax(request, "id", true);
            int id = validate.fieldInt(idParams, "Can't delete answer!");
            AnswerDBContext answerDB = new AnswerDBContext();
            Answer answer = answerDB.findAnswerHaveInQuestion(id);
            if (answer != null) {
                throw new Exception("Answer have in another questions! can't delete answer!");
            }
            answer = answerDB.get(id);
            if (answer.getMedia() != null) {
                FileUtility fileUtility = new FileUtility();
                String folder = request.getServletContext().getRealPath("/images/answer");
                try {
                    fileUtility.delete(answer.getMedia(), folder);
                } catch (Exception e) {
                    System.out.println("Not found file!");
                }
            }
            answerDB.delete(id);
            Gson gson = new Gson();
            String json = gson.toJson("Delete success!");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Message message = new Message();
            message.setCode("error");
            message.setMessage(e.getMessage());
            Gson gson = new Gson();
            String json = gson.toJson(message);
            response.getWriter().write(json);
        }
    }

    private void questionStatusChange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idParam = validate.getFieldAjax(request, "id", true);
            String statusParam = validate.getFieldAjax(request, "status", true);

            int id = validate.fieldInt(idParam, "Id subject must be a number!");
            boolean status = validate.fieldBoolean(statusParam, "Public subject must be a true or false!");

            QuestionDBContext questionDB = new QuestionDBContext();
            Question question = questionDB.get(id);
            question.setStatus(status);
            questionDB.update(question);
            Message message = new Message();
            message.setCode("success");
            message.setMessage("Update status question success!");
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

    private void questionImportPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idSubjectParam = validate.getField(request, "subject", true);
            String idLessonParam = validate.getField(request, "lesson", true);
            String idDimensionParam = validate.getField(request, "dimension", true);
            String level = validate.getField(request, "level", true);
            String content = validate.getField(request, "question", true);
            String explain = validate.getField(request, "explain", false);
            String[] answerParams = validate.getFieldsAjax(request, "answer", true);
            String[] answerResultParams = validate.getFieldsAjax(request, "answer-result", true);

            if (answerParams == null || answerParams.length <= 0) {
                throw new Exception("Please add answer!");
            }

            if (answerResultParams == null || answerResultParams.length <= 0) {
                throw new Exception("Please choose result for answer!");
            }

            int idSubject = validate.fieldInt(idSubjectParam, "Error set field subject!");
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            if (subject == null) {
                throw new Exception("Error set field subject!");
            }

            int idLesson = validate.fieldInt(idLessonParam, "Error set field lesson!");
            LessonDBContext lessonDB = new LessonDBContext();
            Lesson lesson = lessonDB.get(idLesson);
            if (lesson == null) {
                throw new Exception("Error set field lesson!");
            }

            int idDimension = validate.fieldInt(idDimensionParam, "Error set field dimension!");
            DimensionDBContext dimensionDB = new DimensionDBContext();
            Dimension dimension = dimensionDB.get(idDimension);
            if (dimension == null) {
                throw new Exception("Error set field dimension!");
            }

            SettingDBContext settingDB = new SettingDBContext();
            Setting setting = settingDB.findOne("LEVEL_QUESTION" , level.toLowerCase());
            if (setting == null) {
                throw new Exception("Error set field level!");
            }

            User user = (User) request.getSession().getAttribute("user");
            Question question = new Question();
            question.setSubjectId(idSubject);
            question.setSubject(subject);
            question.setDimensionId(idDimension);
            question.setDimension(dimension);
            question.setLessonId(idLesson);
            question.setLesson(lesson);
            question.setLevelId(setting.getId());
            question.setLevel(setting);
            question.setStatus(true);
            question.setContent(content.trim());
            question.setContentHtml(content.trim());
            question.setExplain(explain.trim());
            question.setUser(user);
            question.setUserId(user.getId());
            question.set_multi(answerResultParams != null && answerResultParams.length > 1);
            
            QuestionDBContext questionDB = new QuestionDBContext();
            question = questionDB.insert(question);
            
            ArrayList<AnswerQuestion> answerQuestions = new ArrayList<>();
            for (int i = 0; i < answerParams.length; i++) {
                String answerString = answerParams[i];
                boolean is_correct = false;
                for (String answerResult : answerResultParams) {
                    int indexAnswerResult = Integer.parseInt(answerResult);
                    if (i + 1 == indexAnswerResult) {
                        is_correct = true;
                        break;
                    }
                }
                AnswerDBContext answerDB = new AnswerDBContext();
                Answer answer = new Answer();
                answer.setSubject(subject);
                answer.setSubjectId(idSubject);
                answer.setText(new String(answerString.getBytes("iso-8859-1"), "utf-8"));
                answer = answerDB.insert(answer);

                AnswerQuestion answerQuestion = new AnswerQuestion();
                answerQuestion.setAnswerId(answer.getId());
                answerQuestion.setText(answer.getText());
                answerQuestion.setSubject(answer.getSubject());
                answerQuestion.setSubjectId(answer.getSubjectId());
                answerQuestion.setMedia(answer.getMedia());
                answerQuestion.setQuestionId(question.getId());
                answerQuestion.setAnswerId(answer.getId());
                answerQuestion.set_correct(is_correct);
                answerQuestions.add(answerQuestion);
            }
            AnswerQuestionDBContext answerQuestionDB = new AnswerQuestionDBContext();
            answerQuestionDB.insertMulti(answerQuestions);
            question.setAnswers(answerQuestions);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Gson gson = new Gson();
            String json = gson.toJson(question);
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Message message = new Message();
            message.setCode("error");
            message.setMessage(e.getMessage());
            Gson gson = new Gson();
            String json = gson.toJson(message);
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

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.admin;

import com.google.gson.Gson;
import com.se1617.learning.config.url.admin.UrlQuizzesAdmin;
import com.se1617.learning.controller.base.BaseAuthController;
import com.se1617.learning.dal.question.QuestionDBContext;
import com.se1617.learning.dal.quizzes.QuizzesDBContext;
import com.se1617.learning.dal.quizzes.QuizzesDimensionDBContext;
import com.se1617.learning.dal.setting.SettingDBContext;
import com.se1617.learning.dal.subject.DimensionDBContext;
import com.se1617.learning.dal.subject.SubjectDBContext;
import com.se1617.learning.model.general.Message;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.quizzes.Quizzes;
import com.se1617.learning.model.quizzes.QuizzesDimension;
import com.se1617.learning.model.quizzes.SettingQuizzes;
import com.se1617.learning.model.setting.Setting;
import com.se1617.learning.model.subject.Dimension;
import com.se1617.learning.model.user.User;
import com.se1617.learning.utils.Validate;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giaki
 */
public class QuizzesController extends BaseAuthController {

    private static final int pageSize = 15;

    @Override
    protected boolean isPermission(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user.is_staff();
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlQuizzesAdmin.QUIZZES_DELETE_SELETED)) {
                quizzesDeleteSelected(request, response);
            } else if (super.getURI().matches(UrlQuizzesAdmin.QUIZZES_CREATE)) {
                quizzesCreateGet(request, response);
            } else if (super.getURI().matches(UrlQuizzesAdmin.QUIZZES_EDIT)) {
                quizzesEditGet(request, response);
            } else if (super.getURI().matches(UrlQuizzesAdmin.QUIZZES_DELETE)) {
                quizzesDelete(request, response);
            } else if (super.getURI().matches(UrlQuizzesAdmin.QUIZZES_DETAIL)) {
                quizzesDetail(request, response);
            } else if (super.getURI().matches(UrlQuizzesAdmin.QUIZZES_LIST)) {
                quizzesList(request, response);
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
            if (super.getURI().matches(UrlQuizzesAdmin.QUIZZES_DELETE_SELETED)) {
                quizzesDeleteSelected(request, response);
            } else if (super.getURI().matches(UrlQuizzesAdmin.QUIZZES_CREATE)) {
                quizzesCreatePost(request, response);
            } else if (super.getURI().matches(UrlQuizzesAdmin.QUIZZES_EDIT)) {
                quizzesEditPost(request, response);
            } else if (super.getURI().matches(UrlQuizzesAdmin.QUIZZES_DELETE)) {
                quizzesDelete(request, response);
            } else if (super.getURI().matches(UrlQuizzesAdmin.QUIZZES_DETAIL)) {
                quizzesDetail(request, response);
            } else if (super.getURI().matches(UrlQuizzesAdmin.QUIZZES_LIST)) {
                quizzesList(request, response);
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
    private void quizzesDeleteSelected(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void quizzesEditGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParams = super.getPaths().get(1);
        try {
            int id = Integer.parseInt(idParams);
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            Quizzes quizzes = quizzesDB.get(id);
            if (quizzes == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("quizzes", quizzes);
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("types", settingDB.getByTypeActive("TYPE_QUIZZES"));
            request.setAttribute("levels", settingDB.getByTypeActive("LEVEL_QUIZZES"));
            ArrayList<Setting> typeDimension = settingDB.getByTypeActive("TYPE_DIMENSION");
            if (typeDimension != null && !typeDimension.isEmpty()) {
                request.setAttribute("typeDimentions", typeDimension);
                DimensionDBContext dimensionDB = new DimensionDBContext();
                request.setAttribute("dimensions", dimensionDB.findBySubjectAndType(quizzes.getSubjectId(),typeDimension.get(0).getId()));
            }
            SubjectDBContext subjectDB = new SubjectDBContext();
            request.setAttribute("subjects", subjectDB.searchSubject("", 1, 30));
            request.getRequestDispatcher("/views/admin/quizzes/quizzes_edit.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            response.getWriter().println("Not found quizzes");
        } catch (Exception e) {
            response.getWriter().print(e.getMessage());
        }
    }

    private void quizzesDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String paramsId = super.getPaths().get(1);
            int id = Integer.parseInt(paramsId);
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            quizzesDB.delete(id);
            if (request.getHeader("referer").toLowerCase().contains("/admin/quizzes/" + id) && !request.getHeader("referer").toLowerCase().contains("/edit/")) {
                response.sendRedirect(request.getContextPath() + "/admin/quizzes");
            } else {
                response.sendRedirect(request.getHeader("referer"));
            }
        } catch (Exception e) {
            response.getWriter().print("Not found quizzes!");
        }
    }

    private void quizzesCreateGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SettingDBContext settingDB = new SettingDBContext();
        request.setAttribute("types", settingDB.getByTypeActive("TYPE_QUIZZES"));
        request.setAttribute("levels", settingDB.getByTypeActive("LEVEL_QUIZZES"));
        request.setAttribute("typeDimentions", settingDB.getByTypeActive("TYPE_DIMENSION"));
        SubjectDBContext subjectDB = new SubjectDBContext();
        request.setAttribute("subjects", subjectDB.searchSubject("", 1, 30));
        request.getRequestDispatcher("/views/admin/quizzes/quizzes_create.jsp").forward(request, response);
    }

    private void quizzesDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParams = super.getPaths().get(0);
        try {
            int id = Integer.parseInt(idParams);
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            Quizzes quizzes = quizzesDB.get(id);
            if (quizzes == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("quizzes", quizzes);
            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("types", settingDB.getByTypeActive("TYPE_QUIZZES"));
            request.setAttribute("levels", settingDB.getByTypeActive("LEVEL_QUIZZES"));
            request.setAttribute("typeDimentions", settingDB.getByTypeActive("TYPE_DIMENSION"));
            SubjectDBContext subjectDB = new SubjectDBContext();
            request.setAttribute("subjects", subjectDB.searchSubject("", 1, 30));
            request.getRequestDispatcher("/views/admin/quizzes/quizzes_detail.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            response.getWriter().println("Not found quizzes");
        } catch (Exception e) {
            response.getWriter().print(e.getMessage());
        }
    }

    private void quizzesList(HttpServletRequest request, HttpServletResponse response)
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
                                || sort[0].equalsIgnoreCase("name")
                                || sort[0].equalsIgnoreCase("level")
                                || sort[0].equalsIgnoreCase("type")
                                || sort[0].equalsIgnoreCase("duration")
                                || sort[0].equalsIgnoreCase("pass")
                                || sort[0].equalsIgnoreCase("description")
                                || sort[0].equalsIgnoreCase("total")) {
                            orderMap.put(++count + "." + sort[0], sort[1]);
                        }
                    }
                }
            }

            String[] subjects = validate.getFieldsAjax(request, "subject", false);
            String[] types = validate.getFieldsAjax(request, "type", false);
            String[] levels = validate.getFieldsAjax(request, "level", false);

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

            if (types != null && types.length > 0) {
                ArrayList<String> list = new ArrayList<>();
                for (String item : types) {
                    if (item.matches("^[0-9]+$")) {
                        list.add(item);
                    }
                }
                if (!list.isEmpty()) {
                    filters.put("type", list);
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

            pageable.setFilters(filters);
            pageable.setOrderings(orderMap);
            request.setAttribute("pageable", pageable);
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            ResultPageable resultPageable = quizzesDB.searchAndSortAndFilterMulti(search, pageable);
            request.setAttribute("quizzes", resultPageable.getList());
            request.setAttribute("page", resultPageable.getPagination());

            SettingDBContext settingDB = new SettingDBContext();
            request.setAttribute("levels", settingDB.getByType("LEVEL_QUIZZES"));
            request.setAttribute("types", settingDB.getByType("TYPE_QUIZZES"));
            SubjectDBContext subjectDB = new SubjectDBContext();
            request.setAttribute("subjects", subjectDB.searchSubject("", 1, 30));
            request.getRequestDispatcher("/views/admin/quizzes/quizzes.jsp").forward(request, response);
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

    //-------------------- POST METHOD -------------------------//
    private void quizzesCreatePost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String name = validate.getFieldAjax(request, "name", true);
            String levelParam = validate.getFieldAjax(request, "level", true);
            String typeParam = validate.getFieldAjax(request, "type", true);
            String subjectParam = validate.getFieldAjax(request, "subject", true);
            String durationParam = validate.getFieldAjax(request, "duration", true);
            String passRateParam = validate.getFieldAjax(request, "pass_rate", true);
            String description = validate.getFieldAjax(request, "description", false);
            String totalQuestionParam = validate.getFieldAjax(request, "total_question", true);
            String[] dimensions = validate.getFieldsAjax(request, "dimension", true);
            String[] numberQuestions = validate.getFieldsAjax(request, "number_question", true);

            int idLevel = validate.fieldInt(levelParam, "Error set field level!");
            int idSubject = validate.fieldInt(subjectParam, "Error set field subject!");
            int idType = validate.fieldInt(typeParam, "Error set field type!");
            int duration = validate.fieldInt(durationParam, "Error set field duration!");
            int totalQuestion = validate.fieldInt(totalQuestionParam, "Error set field total question!");
            int rate = validate.fieldInt(passRateParam, "Error set field rate!");

            if (dimensions == null || numberQuestions == null || dimensions.length <= 0 || numberQuestions.length <= 0) {
                throw new Exception("Please choose number question of dimension");
            }

            if (rate > 100) {
                throw new Exception("Pass rate must be less than or equals 100%");
            }

            if (dimensions.length != numberQuestions.length) {
                throw new Exception("Error set number question of dimension");
            }
            Map<Integer, Integer> listQuizzesDimension = new HashMap<>();
            int sum = 0;
            QuestionDBContext questionDB = new QuestionDBContext();
            for (int i = 0; i < dimensions.length; i++) {
                int dimensionId = Integer.parseInt(dimensions[i]);
                int number = Integer.parseInt(numberQuestions[i]);
                if (number > questionDB.countByDimension(dimensionId)) {
                    DimensionDBContext dimensionDB = new DimensionDBContext();
                    Dimension dimension = dimensionDB.get(dimensionId);
                    throw new Exception(" question is not enough for " + dimension.getName());
                }
                listQuizzesDimension.put(dimensionId, number);
                sum += number;
            }
            if (sum != totalQuestion) {
                throw new Exception("Please enter number of dimension same total question");
            }

            Quizzes quizzes = new Quizzes();
            quizzes.setName(name);
            quizzes.setDescription(description);
            quizzes.setLevelId(idLevel);
            quizzes.setSubjectId(idSubject);
            quizzes.setTypeId(idType);
            quizzes.setPass_rate(rate);
            quizzes.setDuration(duration);

            SettingQuizzes settingQuizzes = new SettingQuizzes();
            settingQuizzes.setTotalQuestion(totalQuestion);
            quizzes.setSettingQuizzes(settingQuizzes);
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            quizzes = quizzesDB.insert(quizzes);

            settingQuizzes.setQuizzesId(quizzes.getId());
            QuizzesDBContext quizzesDB2 = new QuizzesDBContext();
            settingQuizzes = quizzesDB2.insertSetting(settingQuizzes);
            quizzes.setSettingQuizzes(settingQuizzes);

            ArrayList<QuizzesDimension> quizzesDimensions = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : listQuizzesDimension.entrySet()) {
                Integer key = entry.getKey();
                Integer val = entry.getValue();
                QuizzesDimension quizzesDimension = new QuizzesDimension();
                quizzesDimension.setDimensionId(key);
                quizzesDimension.setSettingQuizzesId(settingQuizzes.getId());
                quizzesDimension.setNumberQuestion(val);
                quizzesDimensions.add(quizzesDimension);

            }

            QuizzesDimensionDBContext quizzesDimensionDB = new QuizzesDimensionDBContext();
            quizzesDimensionDB.insertMany(quizzesDimensions);
            quizzes.getSettingQuizzes().setQuizzesDimensions(quizzesDimensions);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Gson gson = new Gson();
            String json = gson.toJson(quizzes);
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

    private void quizzesEditPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParams = super.getPaths().get(1);
        try {
            int id = Integer.parseInt(idParams);
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            Quizzes quizzes = quizzesDB.get(id);
            if (quizzes == null) {
                throw new Exception("Not found quizzes");
            }

            Validate validate = new Validate();
            String name = validate.getFieldAjax(request, "name", true);
            String levelParam = validate.getFieldAjax(request, "level", true);
            String typeParam = validate.getFieldAjax(request, "type", true);
            String subjectParam = validate.getFieldAjax(request, "subject", true);
            String durationParam = validate.getFieldAjax(request, "duration", true);
            String passRateParam = validate.getFieldAjax(request, "pass_rate", true);
            String description = validate.getFieldAjax(request, "description", false);
            String totalQuestionParam = validate.getFieldAjax(request, "total_question", true);
            String[] dimensions = validate.getFieldsAjax(request, "dimension", true);
            String[] numberQuestions = validate.getFieldsAjax(request, "number_question", true);

            int idLevel = validate.fieldInt(levelParam, "Error set field level!");
            int idSubject = validate.fieldInt(subjectParam, "Error set field subject!");
            int idType = validate.fieldInt(typeParam, "Error set field type!");
            int duration = validate.fieldInt(durationParam, "Error set field duration!");
            int totalQuestion = validate.fieldInt(totalQuestionParam, "Error set field total question!");
            int rate = validate.fieldInt(passRateParam, "Error set field rate!");

            if (dimensions == null || numberQuestions == null || dimensions.length <= 0 || numberQuestions.length <= 0) {
                throw new Exception("Please choose number question of dimension");
            }

            if (rate > 100) {
                throw new Exception("Pass rate must be less than or equals 100%");
            }

            if (dimensions.length != numberQuestions.length) {
                throw new Exception("Error set number question of dimension");
            }
            Map<Integer, Integer> listQuizzesDimension = new HashMap<>();
            int sum = 0;
            QuestionDBContext questionDB = new QuestionDBContext();
            for (int i = 0; i < dimensions.length; i++) {
                int dimensionId = Integer.parseInt(dimensions[i]);
                int number = Integer.parseInt(numberQuestions[i]);
                if (number > questionDB.countByDimension(dimensionId)) {
                    DimensionDBContext dimensionDB = new DimensionDBContext();
                    Dimension dimension = dimensionDB.get(dimensionId);
                    throw new Exception(" question is not enough for " + dimension.getName());
                }
                listQuizzesDimension.put(dimensionId, number);
                sum += number;
            }
            if (sum != totalQuestion) {
                throw new Exception("Please enter number of dimension same total question");
            }

            quizzes.setName(name);
            quizzes.setDescription(description);
            quizzes.setLevelId(idLevel);
            quizzes.setSubjectId(idSubject);
            quizzes.setTypeId(idType);
            quizzes.setPass_rate(rate);
            quizzes.setDuration(duration);

            SettingQuizzes settingQuizzes = quizzes.getSettingQuizzes();
            settingQuizzes.setTotalQuestion(totalQuestion);
            quizzes.setSettingQuizzes(settingQuizzes);
            quizzesDB.update(quizzes);
            QuizzesDBContext quizzesDB2 = new QuizzesDBContext();
            quizzesDB2.updateSetting(settingQuizzes);

            quizzes.setSettingQuizzes(settingQuizzes);

            ArrayList<QuizzesDimension> quizzesDimensions = new ArrayList<>();
            for (Map.Entry<Integer, Integer> entry : listQuizzesDimension.entrySet()) {
                Integer key = entry.getKey();
                Integer val = entry.getValue();
                QuizzesDimension quizzesDimension = new QuizzesDimension();
                quizzesDimension.setDimensionId(key);
                quizzesDimension.setSettingQuizzesId(settingQuizzes.getId());
                quizzesDimension.setNumberQuestion(val);
                quizzesDimensions.add(quizzesDimension);

            }

            QuizzesDimensionDBContext quizzesDimensionDB = new QuizzesDimensionDBContext();
            quizzesDimensionDB.insertMany(quizzesDimensions);
            quizzes.getSettingQuizzes().setQuizzesDimensions(quizzesDimensions);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            Gson gson = new Gson();
            String json = gson.toJson(quizzes);
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

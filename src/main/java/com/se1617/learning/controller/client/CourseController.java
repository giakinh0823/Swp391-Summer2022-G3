/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.se1617.learning.controller.client;

import com.google.gson.Gson;
import com.se1617.learning.config.url.client.UrlCourseClient;
import com.se1617.learning.controller.base.BaseController;
import com.se1617.learning.dal.question.AnswerUserDBContext;
import com.se1617.learning.dal.question.QuestionDBContext;
import com.se1617.learning.dal.question.QuestionUserDBContext;
import com.se1617.learning.dal.question.UserChooseDBContext;
import com.se1617.learning.dal.quizzes.QuizzesDBContext;
import com.se1617.learning.dal.quizzes.QuizzesUserDBContext;
import com.se1617.learning.dal.registration.CustomerDBContext;
import com.se1617.learning.dal.registration.RegistrationDBContext;
import com.se1617.learning.dal.setting.SettingDBContext;
import com.se1617.learning.dal.subject.LessonDBContext;
import com.se1617.learning.dal.subject.PackagePriceDBContext;
import com.se1617.learning.dal.subject.SubjectDBContext;
import com.se1617.learning.dal.user.UserDBContext;
import com.se1617.learning.model.general.Message;
import com.se1617.learning.model.subject.PackagePrice;
import com.se1617.learning.model.general.Pageable;
import com.se1617.learning.model.general.ResultPageable;
import com.se1617.learning.model.question.AnswerQuestion;
import com.se1617.learning.model.question.AnswerUser;
import com.se1617.learning.model.question.Question;
import com.se1617.learning.model.question.QuestionUser;
import com.se1617.learning.model.question.UserChoose;
import com.se1617.learning.model.quizzes.Quizzes;
import com.se1617.learning.model.quizzes.QuizzesDimension;
import com.se1617.learning.model.quizzes.QuizzesUser;
import com.se1617.learning.model.quizzes.SettingQuizzes;
import com.se1617.learning.model.registration.Customer;
import com.se1617.learning.model.registration.Registration;
import com.se1617.learning.model.subject.Lesson;
import com.se1617.learning.model.subject.Subject;
import com.se1617.learning.model.user.User;
import com.se1617.learning.utils.Validate;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.time.DateUtils;

/**
 *
 * @author giaki
 */
public class CourseController extends BaseController {

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if (super.getURI().matches(UrlCourseClient.COURSE_CHECKOUT_SUCCESS)) {
                courseCheckoutSuccess(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_REGISTER_CANCEL)) {
                registerCancel(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_REGISTER_ROLLBACK)) {
                registerRollbackGet(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_CHECKOUT)) {
                courseCheckoutGet(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_LEARNING_LESSON_QUIZZES_CREATE)) {
                courseLearningLessonQuizzesCreate(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_LEARNING_LESSON_QUIZZES)) {
                courseLearningLessonQuizzesGet(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_LEARNING_LESSON)) {
                courseLearningLesson(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_LEARNING_DETAIL)) {
                courseLearningDetail(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_LEARNING)) {
                courseLearning(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_REGISTER)) {
                courseRegister(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_GET)) {
                courseGet(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_DETAIL)) {
                courseDetail(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_LIST)) {
                courseList(request, response);
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
        try {
            if (super.getURI().matches(UrlCourseClient.COURSE_LEARNING_LESSON_QUIZZES_CHOOSE)) {
                courseLearningLessonQuizzesChoose(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_LEARNING_LESSON_QUIZZES_SUBMIT)) {
                courseLearningLessonQuizzesSubmit(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_REGISTER_ROLLBACK)) {
                registerRollbackPost(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_CHECKOUT)) {
                courseCheckoutPost(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_CHECKOUT_API)) {
                courseCheckoutPost(request, response);
            } else if (super.getURI().matches(UrlCourseClient.COURSE_LEARNING_DONE)) {
                courseLearningDone(request, response);
            } else {
                request.getRequestDispatcher("/views/error/client/404.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("code", e.getMessage());
            request.getRequestDispatcher("/views/error/client/access_denied.jsp").forward(request, response);
        }
    }

    /*------------METHOD GET---------------*/
    private void courseList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                RegistrationDBContext registrationDB = new RegistrationDBContext();
                ArrayList<Registration> registers = registrationDB.findLearningByUser(user.getId());
                user.setRegisters(registers);
                request.setAttribute("me", user);
                request.getSession().setAttribute("user", user);
            }

            SettingDBContext settingDB = new SettingDBContext();

            SubjectDBContext subjectDBContext = new SubjectDBContext();
            ArrayList<User> allAuthorBySubject = subjectDBContext.getAllAuthorBySubject();
            request.setAttribute("authors", allAuthorBySubject);
            // pagination
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

            ResultPageable<Subject> resultPageable = null;
            Pageable pageable = new Pageable();
            pageable.setPageIndex(pageIndex);
            pageable.setPageSize(6);
            // search
            String search = validate.getFieldAjax(request, "search", false);
            if (search == null) {
                search = "";
            }

            Map<String, String> orderMap = new HashMap<>();
            String[] sorts = validate.getFieldsAjax(request, "sort", false);
            if (sorts != null && sorts.length > 0) {
                int count = 0;
                for (String sort : sorts) {
                    if (sort != null && sort.matches("^[a-zA-z]{1,}_(DESC|ASC)$")) {
                        String[] ordering = sort.split("_", 2);
                        if (ordering[0].equalsIgnoreCase("id")
                                || ordering[0].equalsIgnoreCase("name")
                                || ordering[0].equalsIgnoreCase("category")
                                || ordering[0].equalsIgnoreCase("featured")
                                || ordering[0].equalsIgnoreCase("updated")) {
                            orderMap.put(++count + "." + ordering[0], ordering[1]);
                        }
                    }
                }
            }
            pageable.setOrderings(orderMap);
            // filter 
            String[] categories = validate.getFieldsAjax(request, "category", false);
            String[] features = validate.getFieldsAjax(request, "featured", false);
            String[] authors = validate.getFieldsAjax(request, "author", false);

            Map<String, ArrayList<String>> filters = new HashMap<>();

            if (categories != null && categories.length > 0) {
                ArrayList<String> listCategories = new ArrayList<>();
                for (String category : categories) {
                    if (category.matches("^[0-9]+$")) {
                        listCategories.add(category);
                    }
                }
                if (!listCategories.isEmpty()) {
                    filters.put("category", listCategories);
                }
            }

            if (features != null && features.length > 0) {
                ArrayList<String> listFeatures = new ArrayList<>();
                for (String feature : features) {
                    if (feature.equalsIgnoreCase("true") || feature.equalsIgnoreCase("false")) {
                        listFeatures.add(feature);
                    }
                }
                if (!listFeatures.isEmpty()) {
                    filters.put("featured", listFeatures);
                }
            }

            if (authors != null && authors.length > 0) {
                ArrayList<String> listAuthors = new ArrayList<>();
                for (String author : authors) {
                    if (author.matches("^[0-9]+$")) {
                        listAuthors.add(author);
                    }
                }
                if (!listAuthors.isEmpty()) {
                    filters.put("author", listAuthors);
                }
            }
            pageable.setFilters(filters);
            request.setAttribute("pageable", pageable);
            resultPageable = subjectDBContext.listCourseActiveBySearchBySortByFilter(search, pageable);
            request.setAttribute("courses", resultPageable.getList());
            request.setAttribute("page", resultPageable.getPagination());
            request.setAttribute("categories", settingDB.listSettingNotPrarent("CATEGORY_SUBJECT"));
            request.getRequestDispatcher("/views/client/course/course.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void courseDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String paramsId = super.getPaths().get(0);
            int id = Integer.parseInt(paramsId);

            SubjectDBContext subjectDBContext = new SubjectDBContext();
            Subject subject = subjectDBContext.get(id);
            ArrayList<Subject> featureSubjects = subjectDBContext.getFeatureSubjectsByCategory(subject.getCategory().getId(), 3);

            PackagePriceDBContext packagePriceDBContext = new PackagePriceDBContext();
            ArrayList<PackagePrice> pricePackage = packagePriceDBContext.findBySubjectId(subject.getId());

            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                RegistrationDBContext registrationDB = new RegistrationDBContext();
                ArrayList<Registration> registers = registrationDB.findLearningByUser(user.getId());
                user.setRegisters(registers);
                request.getSession().setAttribute("user", user);
            }

            request.setAttribute("subject", subject);
            request.setAttribute("pricePackage", pricePackage);
            request.setAttribute("featureSubjects", featureSubjects);

            request.getRequestDispatcher("/views/client/course/course_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found subject detail!");
        }
    }

    private void courseCheckoutGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String paramsId = super.getPaths().get(0);
            int id = Integer.parseInt(paramsId);

            SubjectDBContext subjectDBContext = new SubjectDBContext();
            Subject subject = subjectDBContext.get(id);

            request.setAttribute("subject", subject);
            request.getRequestDispatcher("/views/client/course/course_checkout.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found subject!");
        }
    }

    private void courseCheckoutSuccess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void courseRegister(HttpServletRequest request, HttpServletResponse response)
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
            pageable.setPageSize(6);
            // search
            String search = validate.getFieldAjax(request, "search", false);
            if (search == null) {
                search = "";
            }

            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/auth/login");
            }
            RegistrationDBContext registrationDB = new RegistrationDBContext();
            ResultPageable<Registration> rsPageable = registrationDB.findByUser(user.getId(), search, pageable);
            request.setAttribute("registers", rsPageable.getList());
            request.setAttribute("page", rsPageable.getPagination());
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.getRequestDispatcher("/views/client/course/registers.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().write(e.getMessage());
        }
    }

    private void courseGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idParams = validate.getFieldAjax(request, "id", true);
            SubjectDBContext subjectDB = new SubjectDBContext();
            int id = Integer.parseInt(idParams);
            Subject subject = subjectDB.get(id);
            String json = new Gson().toJson(subject);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            Message message = new Message();
            message.setCode("error");
            message.setMessage(ex.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);

        }
    }

    private void registerRollbackGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        try {
            String paramsId = super.getPaths().get(2);
            int id = Integer.parseInt(paramsId);

            RegistrationDBContext registrationDB = new RegistrationDBContext();
            Registration registration = registrationDB.get(id);
            request.setAttribute("register", registration);
            request.getRequestDispatcher("/views/client/course/register_rollback.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found subject!");
        }
    }

    private void registerCancel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        String idParams = super.getPaths().get(2);
        try {
            Validate validate = new Validate();
            int id = validate.fieldInt(idParams, "Not found course");
            RegistrationDBContext registrationDB = new RegistrationDBContext();
            registrationDB.delete(id);
            String message = "Cancel success!";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/course/register?code=" + code + "&message=" + message);
        } catch (Exception e) {
            response.getWriter().print(e.getMessage());
        }
    }

    private void courseLearning(HttpServletRequest request, HttpServletResponse response)
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
            pageable.setPageSize(6);
            // search
            String search = validate.getFieldAjax(request, "search", false);
            if (search == null) {
                search = "";
            }

            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/auth/login");
                return;
            }
            RegistrationDBContext registrationDB = new RegistrationDBContext();
            ResultPageable<Registration> rsPageable = registrationDB.findLearningByUser(user.getId(), search, pageable);
            request.setAttribute("registers", rsPageable.getList());
            request.setAttribute("page", rsPageable.getPagination());
            if (message != null) {
                Message msg = new Message(code, message);
                request.setAttribute("message", msg);
            }
            request.getRequestDispatcher("/views/client/course/learning.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().write(e.getMessage());
        }
    }

    private void courseLearningDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        String idParams = super.getPaths().get(1);
        try {
            int idSubject = Integer.parseInt(idParams);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.getWithUser(idSubject, user.getId());
            if (subject == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("subject", subject);
            ArrayList<Lesson> lessons = subject.getLessons();
            request.setAttribute("lessons", lessons);
            request.getRequestDispatcher("/views/client/course/course_learning_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().print("Not found subject");
        } catch (Exception e) {
            request.setAttribute("code", e.getMessage());
            request.getRequestDispatcher("/views/error/client/access_denied.jsp").forward(request, response);
        }
    }

    private void courseLearningLesson(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idSubjectParams = super.getPaths().get(1);
        String idLessonParams = super.getPaths().get(3);
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/auth/login");
                return;
            }
            int idSubject = Integer.parseInt(idSubjectParams);
            SubjectDBContext subjectDB = new SubjectDBContext();
           Subject subject = subjectDB.getWithUser(idSubject, user.getId());
            if (subject == null) {
                throw new NumberFormatException();
            }
            int idLesson = Integer.parseInt(idLessonParams);
            request.setAttribute("subject", subject);
            LessonDBContext lessonDB = new LessonDBContext();
            Lesson lesson = lessonDB.get(idLesson);
            if (lesson == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("lesson", lesson);
            ArrayList<Lesson> lessons = subject.getLessons();
            for (int i = 0; i < lessons.size(); i++) {
                for (int j = 0; j < lessons.get(i).getLessons().size(); j++) {
                    if (lesson.getId() == lessons.get(i).getLessons().get(j).getId()) {
                        if (j + 1 < lessons.get(i).getLessons().size()) {
                            request.setAttribute("next", lessons.get(i).getLessons().get(j + 1));
                            break;
                        }
                    }
                }
            }
            request.setAttribute("lessons", lessons);
            QuizzesUserDBContext quizzesUserDB = new QuizzesUserDBContext();
            if (lesson.getQuizzes() != null) {
                QuizzesUser quizzesUser = quizzesUserDB.findByUserAndLessonAndQuiz(user.getId(), lesson.getId(), lesson.getQuizzes().getId());
                request.setAttribute("quizzes", quizzesUser);
                QuestionUserDBContext questionUserDB = new QuestionUserDBContext();
                if (quizzesUser != null) {
                    ArrayList<QuestionUser> questionUsers = questionUserDB.findByUserAndLessonAndQuizz(user.getId(), lesson.getId(), quizzesUser.getQuizzesId());
                    int count = 0;
                    if (questionUsers != null) {
                        for (QuestionUser questionUser : questionUsers) {
                            if (questionUser.isCorrect()) {
                                count++;
                            }
                        }
                        request.setAttribute("result", (count / (double) questionUsers.size()) * 100);
                        request.setAttribute("questions", questionUsers);
                    }
                    if (quizzesUser.getEnd_time().getTime() < System.currentTimeMillis()) {
                        request.setAttribute("done", true);
                        lessonDB.insertDone(lesson.getId(), user.getId());
                    } else {
                        request.setAttribute("done", false);
                    }
                }
            }
            request.getRequestDispatcher("/views/client/course/course_learning_detail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().print("Not found subject or lesson");
        } catch (Exception e) {
            request.setAttribute("code", e.getMessage());
            request.getRequestDispatcher("/views/error/client/access_denied.jsp").forward(request, response);
        }
    }

    private void courseLearningLessonQuizzesGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idSubjectParams = super.getPaths().get(1);
        String idLessonParams = super.getPaths().get(3);
        String idQuizzesParams = super.getPaths().get(5);
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/auth/login");
                return;
            }

            int idSubject = Integer.parseInt(idSubjectParams);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.getWithUser(idSubject, user.getId());
            if (subject == null) {
                throw new NumberFormatException();
            }
            int idLesson = Integer.parseInt(idLessonParams);
            request.setAttribute("subject", subject);
            LessonDBContext lessonDB = new LessonDBContext();
            Lesson lesson = lessonDB.get(idLesson);
            if (lesson == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("lesson", lesson);

            int idQuizzes = Integer.parseInt(idQuizzesParams);
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            Quizzes quizzes = quizzesDB.get(idQuizzes);

            QuestionUserDBContext questionUserDB = new QuestionUserDBContext();
            ArrayList<QuestionUser> questionUsers = questionUserDB.findByUserAndLessonAndQuizz(user.getId(), lesson.getId(), quizzes.getId());
            request.setAttribute("questions", questionUsers);
            QuizzesUserDBContext quizzesUserDB = new QuizzesUserDBContext();
            QuizzesUser quizzesUser = quizzesUserDB.findByUserAndLessonAndQuiz(user.getId(), lesson.getId(), quizzes.getId());
            if (quizzesUser.getEnd_time().getTime() < System.currentTimeMillis()) {
                request.setAttribute("done", true);
            } else {
                request.setAttribute("done", false);
            }
            request.setAttribute("quizzes", quizzesUser);
            request.getRequestDispatcher("/views/client/course/course_learning_quizzes.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.getWriter().print("Not found subject or lesson or quizzes");
        } catch (Exception e) {
            request.setAttribute("code", e.getMessage());
            request.getRequestDispatcher("/views/error/client/access_denied.jsp").forward(request, response);
        }
    }

    /*------------METHOD POST---------------*/
    private void courseCheckoutPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Validate validate = new Validate();
            String idParams = validate.getFieldAjax(request, "subjectid", true);

            SubjectDBContext subjectDB = new SubjectDBContext();
            int id = Integer.parseInt(idParams);
            Subject subject = subjectDB.get(id);

            if (subject == null) {
                throw new NumberFormatException();
            }

            String first_name = validate.getFieldAjax(request, "first_name", true);
            String last_name = validate.getFieldAjax(request, "last_name", true);
            String phone = validate.getField(request, "phone", true);
            String raw_gender = validate.getField(request, "gender", true);
            boolean gender = (raw_gender.equalsIgnoreCase("male"));

            String email = validate.getFieldAjax(request, "email", true);
            String priceParam = validate.getField(request, "price", true);

            int idPricePackage = validate.fieldInt(priceParam, "Error set field price package!");
            PackagePriceDBContext packagePriceDB = new PackagePriceDBContext();
            PackagePrice packagePrice = packagePriceDB.get(idPricePackage);

            if (packagePrice == null) {
                throw new Exception("Error set field price package!");
            }

            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                UserDBContext userDB = new UserDBContext();
                user = userDB.findOne("email", email);
            }

            RegistrationDBContext registrationDB = new RegistrationDBContext();
            ArrayList<Registration> registrations = registrationDB.findByUserAndSubject(email, subject.getId());
            if (registrations != null && !registrations.isEmpty()) {
                throw new Exception("User have register this course!");
            }
            Customer customer = null;

            if (user == null) {
                customer = new Customer();
                customer.setFirst_name(first_name);
                customer.setLast_name(last_name);
                customer.setFullname(first_name + " " + last_name);
                customer.setEmail(email);
                customer.setPhone(phone);
                customer.setGender(gender);

                CustomerDBContext customerDB = new CustomerDBContext();
                customer = customerDB.insert(customer);
            }

            Registration registration = new Registration();
            if (customer != null) {
                registration.setCustomerId(customer.getId());
                registration.setCustomer(customer);
            }
            registration.setValid_from(new Timestamp(System.currentTimeMillis()));
            Date date = DateUtils.addMonths(new Date(), packagePrice.getDuration());
            if(packagePrice.getDuration()>0){
                registration.setValid_to(new Timestamp(date.getTime()));
            }else{
                date = DateUtils.addMonths(new Date(),10000);
                registration.setValid_to(new Timestamp(date.getTime()));
            }
            registration.setPriceId(packagePrice.getId());
            registration.setPrice(packagePrice);
            registration.setTotal_cost(packagePrice.getSale_price());
            registration.setSubject(subject);
            registration.setSubjectId(subject.getId());
            registration.setStatus(false);
            if (user != null) {
                registration.setUser(user);
                registration.setUserId(user.getId());
            }
            registration.setUpdateBy(null);
            registration.setCreated_at(new Timestamp(System.currentTimeMillis()));
            registrationDB.insert(registration);
            Message message = new Message();
            message.setCode("success");
            message.setMessage("Checkout complete");
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            Message message = new Message();
            message.setCode("error");
            message.setMessage(e.getMessage());
            String json = new Gson().toJson("Not found subject");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            Message message = new Message();
            message.setCode("error");
            message.setMessage(ex.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);

        }
    }

    private void registerRollbackPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        try {
            String paramsId = super.getPaths().get(2);
            int id = Integer.parseInt(paramsId);

            Validate validate = new Validate();
            String priceParam = validate.getField(request, "price", true);

            int idPricePackage = validate.fieldInt(priceParam, "Error set field price package!");
            PackagePriceDBContext packagePriceDB = new PackagePriceDBContext();
            PackagePrice packagePrice = packagePriceDB.get(idPricePackage);

            RegistrationDBContext registrationDB = new RegistrationDBContext();
            Registration registration = registrationDB.get(id);

            registration.setPriceId(packagePrice.getId());
            registration.setPrice(packagePrice);
            registrationDB.updatePrice(registration);

            String message = "Update success!";
            String code = "success";
            response.sendRedirect(request.getContextPath() + "/course/register?code=" + code + "&message=" + message);
        } catch (NumberFormatException e) {
            response.getWriter().println("Not found subject!");
        } catch (Exception ex) {
            response.getWriter().print(ex.getMessage());
        }
    }

    private void courseLearningDone(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Validate validate = new Validate();
            String idParams = validate.getFieldAjax(request, "lessonId", true);
            int id = Integer.parseInt(idParams);
            LessonDBContext lessonDB = new LessonDBContext();
            Lesson lesson = lessonDB.get(id);
            if (lesson == null) {
                throw new Exception("Not found lesson!");
            }
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/auth/login");
                return;
            }
            lessonDB.insertDone(lesson.getId(), user.getId());
            String json = new Gson().toJson("Complete");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            Message message = new Message();
            message.setCode("error");
            message.setMessage(e.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }

    private void courseLearningLessonQuizzesCreate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idSubjectParams = super.getPaths().get(1);
        String idLessonParams = super.getPaths().get(3);
        String idQuizzesParams = super.getPaths().get(5);
        try {
            int idSubject = Integer.parseInt(idSubjectParams);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            if (subject == null) {
                throw new NumberFormatException();
            }
            int idLesson = Integer.parseInt(idLessonParams);
            request.setAttribute("subject", subject);
            LessonDBContext lessonDB = new LessonDBContext();
            Lesson lesson = lessonDB.get(idLesson);
            if (lesson == null) {
                throw new NumberFormatException();
            }
            request.setAttribute("lesson", lesson);

            int idQuizzes = Integer.parseInt(idQuizzesParams);
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            Quizzes quizzes = quizzesDB.get(idQuizzes);

            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/auth/login");
                return;
            }
            Timestamp time_start = new Timestamp(System.currentTimeMillis());
            Timestamp time_end = new Timestamp(System.currentTimeMillis() + quizzes.getDuration() * 60000);
            QuizzesUser quizzesUser = new QuizzesUser();
            quizzesUser.setUser(user);
            quizzesUser.setUserId(user.getId());
            quizzesUser.setLesson(lesson);
            quizzesUser.setLessonId(lesson.getId());
            quizzesUser.setQuizzes(quizzes);
            quizzesUser.setQuizzesId(quizzes.getId());
            quizzesUser.setDuration(quizzes.getDuration());
            quizzesUser.setPass_rate(quizzes.getPass_rate());
            quizzesUser.setDescription(quizzes.getDescription());
            quizzesUser.setName(quizzes.getName());
            quizzesUser.setLevel(quizzes.getLevel());
            quizzesUser.setLevelId(quizzes.getLevelId());
            quizzesUser.setType(quizzes.getType());
            quizzesUser.setTypeId(quizzes.getTypeId());
            quizzesUser.setStart_time(time_start);
            quizzesUser.setEnd_time(time_end);
            QuizzesUserDBContext quizzesUserDBContext = new QuizzesUserDBContext();
            quizzesUserDBContext.insert(quizzesUser);

            QuestionDBContext questionDB = new QuestionDBContext();
            ArrayList<QuestionUser> questionUsers = new ArrayList<>();
            QuestionUserDBContext questionUserDB = new QuestionUserDBContext();
            AnswerUserDBContext answerUserDB = new AnswerUserDBContext();

            for (QuizzesDimension item : quizzes.getSettingQuizzes().getQuizzesDimensions()) {
                ArrayList<Question> questions = questionDB.findBySubjectAndDimension(quizzes.getSubjectId(), item.getDimensionId(), item.getNumberQuestion());
                for (Question question : questions) {
                    QuestionUser questionUser = new QuestionUser();
                    questionUser.setQuestion(question);
                    questionUser.setQuestionId(question.getId());
                    questionUser.setContent(question.getContent());
                    questionUser.setContentHtml(question.getContentHtml());
                    questionUser.setDimension(question.getDimension());
                    questionUser.setDimensionId(question.getDimension().getId());
                    questionUser.setExplain(question.getExplain());
                    questionUser.setLevel(question.getLevel());
                    questionUser.setLevelId(question.getLevel().getId());
                    questionUser.setMedia(question.getMedia() != null ? question.getMedia().getUrl() : null);
                    questionUser.setSubject(question.getSubject());
                    questionUser.setSubjectId(question.getSubjectId());
                    questionUser.setLesson(lesson);
                    questionUser.setLessonId(lesson.getId());
                    questionUser.setQuizzes(quizzes);
                    questionUser.setQuizzesId(quizzes.getId());
                    questionUser.setUser(user);
                    questionUser.setUserId(user.getId());
                    questionUser.set_multi(question.is_multi());
                    ArrayList<AnswerUser> answerUsers = new ArrayList<>();
                    if (question.getAnswers() != null) {
                        for (AnswerQuestion answer : question.getAnswers()) {
                            AnswerUser answerUser = new AnswerUser();
                            answerUser.setUser(user);
                            answerUser.setUserId(user.getId());
                            answerUser.setLesson(lesson);
                            answerUser.setLessonId(lesson.getId());
                            answerUser.setQuizzes(quizzes);
                            answerUser.setQuizzesId(quizzes.getId());
                            answerUser.setQuestion(question);
                            answerUser.setQuestionId(question.getId());
                            answerUser.setAnswer(answer);
                            answerUser.setAnswerId(answer.getId());
                            answerUser.setText(answer.getText());
                            answerUser.setMedia(answer.getMedia());
                            answerUser.set_correct(answer.is_correct());
                            answerUsers.add(answerUser);
                        }
                    }
                    questionUser.setAnswers(answerUsers);
                    questionUsers.add(questionUser);
                }
            }
            questionUserDB.insertMany(questionUsers);
            answerUserDB.insertMany(questionUsers);
            lessonDB.deleteDone(lesson.getId(), user.getId());
            request.setAttribute("quizzes", quizzes);
            request.setAttribute("questionUsers", questionUsers);
            response.sendRedirect(request.getContextPath() + "/course/learning/" + subject.getId() + "/lesson/" + lesson.getId() + "/quizzes/" + quizzes.getId());
        } catch (NumberFormatException e) {
            response.getWriter().print("Not found subject or lesson or quizzes");
        } catch (Exception e) {
            request.setAttribute("code", e.getMessage());
            request.getRequestDispatcher("/views/error/client/access_denied.jsp").forward(request, response);
        }
    }

    private void courseLearningLessonQuizzesSubmit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idSubjectParams = super.getPaths().get(1);
        String idLessonParams = super.getPaths().get(3);
        String idQuizzesParams = super.getPaths().get(5);

        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                throw new Exception("Please login!");
            }
            int idSubject = Integer.parseInt(idSubjectParams);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            if (subject == null) {
                throw new NumberFormatException();
            }
            int idLesson = Integer.parseInt(idLessonParams);
            LessonDBContext lessonDB = new LessonDBContext();
            Lesson lesson = lessonDB.get(idLesson);
            if (lesson == null) {
                throw new NumberFormatException();
            }
            int idQuizzes = Integer.parseInt(idQuizzesParams);
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            Quizzes quizzes = quizzesDB.get(idQuizzes);

            QuizzesUserDBContext quizzesUserDB = new QuizzesUserDBContext();
            QuizzesUser quizzesUser = quizzesUserDB.findByUserAndLessonAndQuiz(user.getId(), lesson.getId(), quizzes.getId());
            if (System.currentTimeMillis() > quizzesUser.getEnd_time().getTime()) {
                throw new Exception("TIME EXPIRED");
            }
            quizzesUser.setEnd_time(new Timestamp(System.currentTimeMillis()));
            quizzesUserDB.update(quizzesUser);

            Validate validate = new Validate();
            String[] questions = validate.getFieldsAjax(request, "question[]", true);
            String[] answers = validate.getFieldsAjax(request, "answer[]", true);
            if (questions.length != answers.length) {
                throw new Exception("Something error! Please reload page");
            }
            UserChooseDBContext userChooseDB = new UserChooseDBContext();
            ArrayList<UserChoose> userChooses = new ArrayList<>();
            Timestamp created_at = new Timestamp(System.currentTimeMillis());
            for (int i = 0; i < questions.length; i++) {
                UserChoose userChoose = new UserChoose();
                int answerId = Integer.parseInt(answers[i]);
                int questionId = Integer.parseInt(questions[i]);
                userChoose.setAnswerId(answerId);
                userChoose.setQuestionId(questionId);
                userChoose.setUserId(user.getId());
                userChoose.setUser(user);
                userChoose.setQuizzesId(quizzes.getId());
                userChoose.setQuizzes(quizzes);
                userChoose.setLessonId(lesson.getId());
                userChoose.setLesson(lesson);
                userChoose.setCreated_at(created_at);
                userChooses.add(userChoose);
            }
            userChooseDB.insertMany(userChooses);
            lessonDB.insertDone(lesson.getId(), user.getId());
            String json = new Gson().toJson("Submit success");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (NumberFormatException e) {
            response.getWriter().print("Not found subject or lesson or quizzes");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
            Message message = new Message();
            message.setCode("error");
            message.setMessage(e.getMessage());
            String json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        }
    }

    private void courseLearningLessonQuizzesChoose(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idSubjectParams = super.getPaths().get(1);
        String idLessonParams = super.getPaths().get(3);
        String idQuizzesParams = super.getPaths().get(5);

        try {
            User user = (User) request.getSession().getAttribute("user");
            if (user == null) {
                throw new Exception("Please login!");
            }
            int idSubject = Integer.parseInt(idSubjectParams);
            SubjectDBContext subjectDB = new SubjectDBContext();
            Subject subject = subjectDB.get(idSubject);
            if (subject == null) {
                throw new NumberFormatException();
            }
            int idLesson = Integer.parseInt(idLessonParams);
            LessonDBContext lessonDB = new LessonDBContext();
            Lesson lesson = lessonDB.get(idLesson);
            if (lesson == null) {
                throw new NumberFormatException();
            }
            int idQuizzes = Integer.parseInt(idQuizzesParams);
            QuizzesDBContext quizzesDB = new QuizzesDBContext();
            Quizzes quizzes = quizzesDB.get(idQuizzes);

            QuizzesUserDBContext quizzesUserDB = new QuizzesUserDBContext();
            QuizzesUser quizzesUser = quizzesUserDB.findByUserAndLessonAndQuiz(user.getId(), lesson.getId(), quizzes.getId());
            if (System.currentTimeMillis() > quizzesUser.getEnd_time().getTime()) {
                throw new Exception("TIME EXPIRED");
            }

            Validate validate = new Validate();
            String[] questions = validate.getFieldsAjax(request, "question[]", true);
            String[] answers = validate.getFieldsAjax(request, "answer[]", true);
            if (questions.length != answers.length) {
                throw new Exception("Something error! Please reload page");
            }
            UserChooseDBContext userChooseDB = new UserChooseDBContext();
            ArrayList<UserChoose> userChooses = new ArrayList<>();
            Timestamp created_at = new Timestamp(System.currentTimeMillis());
            for (int i = 0; i < questions.length; i++) {
                UserChoose userChoose = new UserChoose();
                int answerId = Integer.parseInt(answers[i]);
                int questionId = Integer.parseInt(questions[i]);
                userChoose.setAnswerId(answerId);
                userChoose.setQuestionId(questionId);
                userChoose.setUserId(user.getId());
                userChoose.setUser(user);
                userChoose.setQuizzesId(quizzes.getId());
                userChoose.setQuizzes(quizzes);
                userChoose.setLessonId(lesson.getId());
                userChoose.setLesson(lesson);
                userChoose.setCreated_at(created_at);
                userChooses.add(userChoose);
            }
            userChooseDB.insertMany(userChooses);
            String json = new Gson().toJson("Submit success");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (NumberFormatException e) {
            response.getWriter().print("Not found subject or lesson or quizzes");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_GATEWAY);
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

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.se1617.learning.config.url.client;

import com.se1617.learning.config.security.SecurityConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author giaki
 */
public class UrlCourseClient {

    public static final String URL_PATTERN = "^/course(/|).*";
    public static final String COURSE_LIST = "^/course(/|)$";
    public static final String COURSE_GET = "^/course/get(/|)$";
    public static final String COURSE_DETAIL = "^/course/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String COURSE_REGISTER = "^/course/register(/|)$";
    public static final String COURSE_LEARNING= "^/course/learning(/|)$";
    public static final String COURSE_LEARNING_DONE= "^/course/learning/done(/|)$";
    public static final String COURSE_LEARNING_DETAIL= "^/course/learning/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String COURSE_LEARNING_LESSON= "^/course/learning/[a-zA-Z0-9-]{1,}/lesson/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String COURSE_LEARNING_LESSON_QUIZZES= "^/course/learning/[a-zA-Z0-9-]{1,}/lesson/[a-zA-Z0-9-]{1,}/quizzes/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String COURSE_LEARNING_LESSON_QUIZZES_CHOOSE= "^/course/learning/[a-zA-Z0-9-]{1,}/lesson/[a-zA-Z0-9-]{1,}/quizzes/[a-zA-Z0-9-]{1,}/choose(/|)$";
    public static final String COURSE_LEARNING_LESSON_QUIZZES_SUBMIT= "^/course/learning/[a-zA-Z0-9-]{1,}/lesson/[a-zA-Z0-9-]{1,}/quizzes/[a-zA-Z0-9-]{1,}/submit(/|)$";
    public static final String COURSE_LEARNING_LESSON_QUIZZES_CREATE= "^/course/learning/[a-zA-Z0-9-]{1,}/lesson/[a-zA-Z0-9-]{1,}/quizzes/[a-zA-Z0-9-]{1,}/create(/|)$";
    public static final String COURSE_REGISTER_CANCEL = "^/course/register/cancel/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String COURSE_REGISTER_ROLLBACK = "^/course/register/rollback/[a-zA-Z0-9-]{1,}(/|)$";
    public static final String COURSE_CHECKOUT_API = "^/course/checkout(/|)$";
    public static final String COURSE_CHECKOUT = "^/course/[a-zA-Z0-9-]{1,}/checkout(/|)$";
    public static final String COURSE_CHECKOUT_SUCCESS = "^/course/[a-zA-Z0-9-]{1,}/checkout/success(/|)$";

    private static UrlCourseClient instance = new UrlCourseClient();

    private UrlCourseClient() {
    }

    public static UrlCourseClient getInstance() {
        return instance;
    }

    public static void checkAccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String contextPath = request.getContextPath();
        String URI = request.getRequestURI().trim().replaceFirst(contextPath, "");
    }
}

<%-- 
    Document   : course_lesson_content
    Created on : Jul 6, 2022, 3:37:18 PM
    Author     : giaki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
    var videoSrc;
    var lessonId = ${lesson.id};
    var nextUrl = '${pageContext.request.contextPath}/course/learning/${subject.id}/lesson/${next!=null ? next.id : lesson.id}';
</script>
<div class="w-full  h-full">
    <c:if test="${lesson==null}">
        <div class="py-10 px-12">
            <h2 class="text-4xl">${subject.name}</h2>
            <p class="mt-5 text-lg">
                ${subject.description}
            </p>
        </div>
    </c:if>
    <c:if test="${lesson.video==null && lesson.content==null}">
        <div class="flex justify-center mt-20 items-center">
            <p class="text-4xl">
                ${lesson.name}
            </p>
        </div>
    </c:if>
    <c:if test="${lesson.video!=null}">
        <script>
            videoSrc = '${lesson.video}';
            nextUrl = '${pageContext.request.contextPath}/course/learning/${subject.id}/lesson/${next!=null ? next.id : lesson.id}';
        </script>
        <div id="lesson-video" style="height: 80vh"></div>
        <div class="mt-5 px-3">
            <p class="mt-2 text-2xl font-medium">${lesson.name}</p>
        </div>
    </c:if>
    <c:if test="${lesson.quizzes!=null}">
        <div class="flex justify-center mt-10">
            <div class="border">
                <div class="flex min-w-[450px] justify-between items-center border-b py-4 px-8">
                    <div>
                        <p>
                            ${lesson.quizzes.name}  
                        </p>
                        <p>
                            <c:if test="${quizzes!=null}">
                                Duo - ${quizzes.end_time}
                            </c:if>
                        </p>
                    </div>
                    <c:choose>
                        <c:when test="${quizzes!=null && done}">
                            <div class="flex items-center">
                                <a href="${pageContext.request.contextPath}/course/learning/${subject.id}/lesson/${lesson.id}/quizzes/${lesson.quizzes.id}" class="block py-2.5 px-5 bg-blue-700 text-white cursor-pointer">
                                    Review
                                </a>
<!--                                <button class="py-2.5 px-5 bg-blue-700 text-white cursor-pointer ml-2 text-center">
                                    Detail
                                </button>-->
                            </div>
                        </c:when>
                        <c:when test="${quizzes!=null && !done}">
                            <a href="${pageContext.request.contextPath}/course/learning/${subject.id}/lesson/${lesson.id}/quizzes/${lesson.quizzes.id}" class="py-2.5 px-5 bg-blue-700 text-white cursor-pointer rounded-lg mb-3 text-center">
                                Resume
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/course/learning/${subject.id}/lesson/${lesson.id}/quizzes/${lesson.quizzes.id}/create" class="py-2.5 px-5 bg-blue-700 text-white cursor-pointer rounded-lg mb-3 text-center">
                                Start quiz
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="flex min-w-[450px] justify-between items-center border-b py-4 px-8">
                    <div>
                        <p>
                            Receive grade
                        </p>
                        <p>
                            To Pass <span>${lesson.quizzes.pass_rate}% or higher</span>
                        </p>
                    </div>
                    <div class="border-l pl-8">
                        <p>
                            Your grade
                        </p>
                        <p class="${done!=null && done && result!=null && quizzes!=null ? result> quizzes.pass_rate ? "text-green-400" : "text-red-600" : ""} text-center">
                            <c:if test="${done!=null && done && result!=null && quizzes!=null}">
                                ${result}%
                            </c:if>
                            <c:if test="${(done==null || done==false) && quizzes==null}">
                                Not started
                            </c:if>
                            <c:if test="${(done==null || done==false) && quizzes!=null}">
                                Waiting
                            </c:if>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${lesson.content!=null}">
        <div class="py-5 px-10">
            <div class="w-full flex justify-end">
                <button onclick="doneVideo()" type="button" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2">Complete</button>
            </div>
            <div>
                ${lesson.content}
            </div>

        </div>
    </c:if>
</div>

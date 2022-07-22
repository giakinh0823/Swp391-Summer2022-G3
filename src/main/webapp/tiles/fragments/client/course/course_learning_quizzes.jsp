<%-- 
    Document   : course_learning_quizzes
    Created on : Jul 7, 2022, 1:47:47 AM
    Author     : giaki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
    const end_time = '${quizzes.end_time}';
    const subjectId = ${subject.id};
    const lessonId = ${lesson.id};
    const answers = new Map();
</script>
<div class="container mx-auto mt-5 px-5">
    <form action="${pageContext.request.contextPath}/course/learning/${subject.id}/lesson/${lesson.id}/quizzes/${lesson.quizzes.id}/submit" id="form" method="post">
        <div class="flex flex-1 w-full">
            <div class="flex-1  max-h-[calc(100vh-60px)] overflow-y-auto">
                <c:set value="0" var="count"/>
                <c:set value="0" var="numberCorrect"/>
                <c:forEach items="${questions}" var="question">
                    <c:set value="${count+1}" var="count"/>
                    <c:if test="${question.isCorrect()}">
                        <c:set value="${numberCorrect+1}" var="numberCorrect"/>
                    </c:if>
                    <div>
                        <div class="flex py-2 px-5 bg-gray-200 flex justify-between items-center">
                            <p id="question-index-${count}" class="font-medium">
                                Question: ${count}
                                <c:if test="${done && question.isCorrect()}">
                                    <span class="ml-5 text-green-600">+ 1 point</span>
                                </c:if>
                                <c:if test="${done && !question.isCorrect()}">
                                    <span class="ml-5 text-red-600">- 1 point</span>
                                </c:if>
                            </p>
                            <p class="font-medium">
                                #${question.questionId}
                            </p>
                        </div>
                        <div class="px-5 pb-3 bg-gray-200">
                            ${question.content}
                        </div>
                        <div class="px-10 py-5">
                            <c:forEach items="${question.answers}" var="answer">
                                <c:if test="${(done==null || done==false) && question.userChooses!=null}">
                                    <script>
                                        answers.set(${question.questionId}, new Set([
                                        <c:forEach items="${question.userChooses}" var="userChoose">
                                            ${userChoose.answerId},
                                        </c:forEach>
                                        ])
                                                );
                                    </script>
                                </c:if>
                                <div class="flex items-center mb-3">
                                    <c:choose>
                                        <c:when test="${question.is_multi()==true}">
                                            <input ${done ? "disabled": ""} id="question-${question.questionId}-answer-${answer.answerId}" ${question.checkUserChoose(answer.answerId)==true ? "checked": ""} onchange="addAnswer(${question.questionId},${answer.answerId})" name="answer-${question.questionId}" type="checkbox" value="${answer.answerId}" class="w-4 h-4 ${done!=null && done && question.checkUserChoose(answer.answerId)==true ? question.checkCorrect(answer.answerId)==true ? "text-green-600 focus:ring-green-500" :"text-red-600  focus:ring-red-500" : ""} bg-gray-100 rounded border-gray-300">
                                        </c:when>
                                        <c:otherwise>
                                            <input ${done ? "disabled": ""} id="question-${question.questionId}-answer-${answer.answerId}" ${question.checkUserChoose(answer.answerId)==true ? "checked": ""} onchange="addAnswer(${question.questionId},${answer.answerId})" type="radio" value="${answer.answerId}" name="answer-${question.questionId}" class="w-4 h-4 ${done!=null && done && question.checkUserChoose(answer.answerId)==true ? question.checkCorrect(answer.answerId)==true ? "text-green-600 focus:ring-green-500" :"text-red-600 focus:ring-red-500" : ""} bg-gray-100 border-gray-300 focus:ring-blue-500">
                                        </c:otherwise>
                                    </c:choose>
                                    <label for="question-${question.questionId}-answer-${answer.answerId}" class="ml-2 text-sm font-medium text-gray-900  ${done!=null && done && question.checkUserChoose(answer.answerId)==true ? question.checkCorrect(answer.answerId)==true ? "text-green-400" :"text-red-600" : ""}">${answer.text}</label>
                                </div>
                            </c:forEach>
                        </div>
                        <c:if test="${done && !question.isCorrect()}">
                            <div class="py-2 px-5 bg-blue-200 mb-5 rounded-md">
                                ${question.explain}
                            </div>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
            <div class="w-[280px] px-2 max-h-[calc(100vh-60px)] overflow-y-auto">
                <c:if test="${!done}">
                    <div class="flex justify-center mb-5">
                        <p class="text-2xl">Time: <span id="quizzes-count-down">EXPIRED</span></p>
                    </div>
                </c:if>
                <c:if test="${done}">
                    <div class="flex justify-center mb-5">
                        <p class="text-2xl">${numberCorrect} / ${count}</p>
                    </div>
                </c:if>
                <div class="w-full grid grid-cols-5 gap-1">
                    <c:set value="0" var="count"/>
                    <c:forEach items="${questions}" var="question">
                        <c:set value="${count+1}" var="count"/>
                        <a id="question-view-${question.questionId}" href="#question-index-${count}" class="block py-2 px-4 border hover:underline cursor-pointer text-center ${question.userChooses!=null && question.userChooses.size() >0 ? question.isCorrect() ? "bg-green-400" : done!=null && done  ? "bg-red-400" : "bg-green-400": done!=null && done  ? "bg-red-400" : ""}">${count}</a>
                    </c:forEach>
                </div>
                <div class="flex justify-center mt-5 flex-col items-center">
                    <c:if test="${done}">
                        <a href="${pageContext.request.contextPath}/course/learning/${subject.id}/lesson/${lesson.id}/quizzes/${lesson.quizzes.id}/create" class="w-full py-2.5 px-5 bg-blue-700 text-white cursor-pointer rounded-lg mb-3 text-center">
                            Try Again
                        </a>
                    </c:if>
                    <c:if test="${!done}">
                        <button id="button-submit" class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2">
                            Submit
                        </button>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/course/learning/${subject.id}/lesson/${lesson.id}" class="w-full py-2.5 px-5 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 text-center">
                        Cancel
                    </a>
                </div>
            </div>
        </div>
    </form>
</div>

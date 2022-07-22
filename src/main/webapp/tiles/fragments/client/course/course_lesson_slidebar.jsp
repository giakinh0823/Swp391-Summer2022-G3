<%-- 
    Document   : course_lesson_slidebar
    Created on : Jul 6, 2022, 3:27:58 PM
    Author     : giaki
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set value="0" var="count"/>
<c:forEach items="${lessons}" var="item">
    <div class="py-2 px-4 bg-gray-800">
        <a href="${pageContext.request.contextPath}/course/learning/${subject.id}/lesson/${item.id}" class="text-white" class="hover:underline">${item.name}</a>
    </div>
    <c:forEach items="${item.lessons}" var="child">
        <div class="py-2 px-5 border-b flex gap-2 items-center w-full">
            <p class="${(child.video!=null && child.video!='') ? "" : "hidden"}">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>                                        
            </p>
            <p class="${(child.content!=null && child.content!='') ? "" : "hidden"}">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path></svg>
            </p>
            <p class="${fn:contains(child.type.value, 'Quiz')==true ? "" : "hidden"}">
                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 17v-2m3 2v-4m3 4v-6m2 10H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path></svg>
            </p>
            <a " href="${pageContext.request.contextPath}/course/learning/${subject.id}/lesson/${child.id}" class="hover:underline flex-1" style="color:#333!important">${child.name}</a>
            <input disabled id="lesson-done-${child.id}" ${child.isLearning() ? "checked": ""} type="checkbox" class="w-4 h-4 text-green-600 bg-gray-100 rounded border-gray-300 focus:ring-green-400">
        </div>
    </c:forEach>
</c:forEach>

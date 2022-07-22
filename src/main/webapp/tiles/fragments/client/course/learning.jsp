<%-- 
    Document   : learning
    Created on : Jun 29, 2022, 1:37:47 AM
    Author     : giaki
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container mx-auto">
    <div class="mt-10 flex items-center justify-between pb-5 border-b">
        <h2 class="font-medium text-xl whitespace-nowrap">My courses</h2>
        <div class="flex justify-end w-full">
            <form class="flex items-center">   
                <label for="ssearch-feature" class="sr-only">Search</label>
                <div class="relative w-full">
                    <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                        <svg class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
                    </div>
                    <input type="text" id="search" name="search" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2" placeholder="Search">
                </div>
                <button type="submit" class="p-2 ml-2 text-sm font-medium text-white bg-blue-700 rounded-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300"><svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg></button>
            </form>
        </div>
    </div>
    <div class="grid grid-cols-5 gap-10 mt-10">
        <c:forEach items="${registers}" var="register">
            <a href="${pageContext.request.contextPath}/course/learning/${register.subject.id}" class="hover:cursor-pointer">
                <c:choose>
                    <c:when test="${fn:containsIgnoreCase(register.subject.image, 'dummyimage.com')}">
                        <img class="${register.subject.image!=null ? "":"hidden"} w-full h-[200px]" alt="${register.subject.name}" src="${register.subject.image}" id="image-preview"/>
                    </c:when>
                    <c:otherwise>
                        <img class="${register.subject.image!=null ? "":"hidden"} w-full h-[200px]" alt="${register.subject.name}" src="${pageContext.request.contextPath}/images/subject/${register.subject.image}" id="image-preview"/>
                    </c:otherwise>
                </c:choose>  
                <div>
                    <div style="max-width: full; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 1; -webkit-box-orient: vertical;">
                        <h2 class="font-medium text-xl">${register.subject.name}</h2></div>
                    <div class="mt-3"style="max-width: full; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;">
                        <p class="mb-1 text-gray-500"> ${register.subject.description}</p></div>
                </div>
            </a>
        </c:forEach>
    </div>
    <%@include file="/views/component/pagination.jsp" %>
</div>
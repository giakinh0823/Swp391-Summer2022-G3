<%-- 
    Document   : lesson_create
    Created on : Jun 6, 2022, 8:35:20 PM
    Author     : giaki
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/richtexteditor/rte_theme_default.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/richtexteditor/rte.js"></script>
<script>RTE_DefaultConfig.url_base = '${pageContext.request.contextPath}/assets/richtexteditor/richtexteditor'</script>
<script type="text/javascript" src='${pageContext.request.contextPath}/assets/richtexteditor/plugins/all_plugins.js'></script>
<div class="flex items-center justify-between">
    <div class="flex space-x-2 items-center mb-5">
        <h1 class="text-2xl font-medium ml-3">${subject.name}</h1>
    </div>
</div>
<%@include file="/tiles/fragments/admin/subject/tabbar.jsp" %>
<div class="container mx-auto">
    <form class="mx-auto mt-10" method="POST" id="form-post">

        <c:if test="${error!=null}">
            <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                <span class="font-medium">Error!</span> ${error}
            </div>
        </c:if>
        <div>
            <div>
                <div class="flex justify-end">
                    <button type="submit" class="ml-auto mt-5 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Create</button>
                </div>
                <div class="max-w-md xl:max-w-lg mx-auto">
                    <div class="mb-6">
                        <label for="name" class="block mb-2 text-sm font-medium text-gray-900">Name</label>
                        <input type="text" id="name" name="name" value="" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                    </div>
                    <div class="mb-6">
                        <label for="type" class="block mb-2 text-sm font-medium text-gray-900">Type</label>
                        <select id="type" name="type" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                            <c:forEach items="${types}" var="item">
                                <option value="${item.id}" ${type==item.id ? "selected": ""}>${item.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-6 hidden" id="topic-layout">
                        <label for="topic" class="block mb-2 text-sm font-medium text-gray-900">Topic</label>
                        <select id="topic" name="topic" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                            <c:forEach items="${topics}" var="item">
                                <option value="${item.id}" ${topic==item.id ? "selected": ""}>${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-6">
                        <label for="order" class="block mb-2 text-sm font-medium text-gray-900">Order</label>
                        <input type="number" min="1" id="order" name="order" value="" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                    </div>
                    <div class="mb-6">
                        <label for="price-package" " class="block mb-2 text-sm font-medium text-gray-900">Price package</label>
                        <select id="price-package" multiple name="price-package" " class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 max-2-[200px]">
                            <c:forEach items="${prices}" var="item">
                                <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-6 hidden" id="youtube-layout">
                        <label for="youtube" class="block mb-2 text-sm font-medium text-gray-900">Link youtube</label>
                        <input type="text" id="youtube" name="youtube" value="" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                    </div>
                    <div class="mb-6" id="quizzes-layout">
                        <label for="quizzes" " class="block mb-2 text-sm font-medium text-gray-900">Quizzes</label>
                        <select id="quizzes" name="quizzes" " class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                            <option value="none">None</option>
                            <c:forEach items="${quizzes}" var="item">
                                <option value="${item.id}" ${quizze==item.id ? "selected": ""}>${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="hidden" id="content-layout">
                <label for="content" class="block mb-2 text-sm font-medium text-gray-900">Content</label>
                <textarea  id="content" name="content" class="hidden bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                    ${content}
                </textarea>
            </div>
        </div>
    </form>
</div>

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
    <form method="POST" id="form-post">
        <div class="flex justify-end">
            <a href="${pageContext.request.contextPath}/admin/subject/${subject.id}/lesson/edit/${lesson.id}" class="font-medium text-blue-600">Edit</a>
            <span class="font-medium mx-2">/</span>
            <button type="button" onclick="$('#button-confirm-delete').attr('href', '${pageContext.request.contextPath}/admin/subject/${subject.id}/lesson/delete/${lesson.id}')" class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete">Delete</button>                       
        </div>
        <c:if test="${error!=null}">
            <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                <span class="font-medium">Error!</span> ${error}
            </div>
        </c:if>
        <div>
            <div>
                <div class="max-w-md xl:max-w-lg mx-auto">
                    <div class="mb-6">
                        <label for="name" class="block mb-2 text-sm font-medium text-gray-900">Name</label>
                        <input disabled type="text" id="name" name="name" value="${lesson.name}" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                    </div>
                    <div class="mb-6">
                        <label for="type" class="block mb-2 text-sm font-medium text-gray-900">Type</label>
                        <select disabled id="type" name="type" class="bg-gray-50 border border-gray-300 text-black text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                            <c:forEach items="${types}" var="item">
                                <option value="${item.id}" ${lesson.type.id==item.id ? "selected": ""}>${item.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-6 ${lesson.topic!=null ? "": "hidden"}" id="topic-layout">
                        <label for="topic" class="block mb-2 text-sm font-medium text-gray-900">Topic</label>
                        <select disabled id="topic" name="topic" class="bg-gray-50 border border-gray-300 text-black text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                            <c:forEach items="${topics}" var="item">
                                <option value="${item.id}" ${lesson.topic.id==item.id ? "selected": ""}>${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-6">
                        <label for="order" class="block mb-2 text-sm font-medium text-gray-900">Order</label>
                        <input disabled type="number" min="1" id="order" name="order" value="${lesson.order}" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                    </div>
                    <div class="mb-6">
                        <div>
                            <label for="status" class="block mb-2 text-sm font-medium text-gray-900">Status</label>
                        </div>
                        <div class="flex items-center">
                            <div class="flex items-center mr-4">
                                <input disabled id="status_true" type="radio" 
                                       ${lesson.status == true ? "checked":""}
                                       value="status_true" name="status" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500">
                                <label for="status_true" class="ml-2 text-sm font-medium text-gray-900">Activate</label>
                            </div>
                            <div class="flex items-center mr-4">
                                <input disabled id="status_false" type="radio" 
                                       ${lesson.status == false ? "checked":""}
                                       value="status_false" name="status" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500">
                                <label for="status_false" class="ml-2 text-sm font-medium text-gray-900">Deactivate</label>
                            </div>
                        </div>
                    </div>
                    <div class="mb-6">
                        <label for="price-package" " class="block mb-2 text-sm font-medium text-gray-900">Price package</label>
                        <select disabled id="price-package" multiple name="price-package" " class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 max-2-[200px]">
                            <c:forEach items="${prices}" var="item">
                                <option value="${item.id}" ${lesson.checkPackagePrice(item.id)==true ? "selected": ""}>${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-6 ${lesson.video!=null ? "": "hidden"}" id="youtube-layout">
                        <label for="youtube" class="block mb-2 text-sm font-medium text-gray-900">Link youtube</label>
                        <input disabled type="text" id="youtube" name="youtube" value="${lesson.video}" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                    </div>
                    <div class="mb-6 ${lesson.quizzes!=null ? "": "hidden"}" id="quizzes-layout">
                        <label for="quizzes" " class="block mb-2 text-sm font-medium text-gray-900">Quizzes</label>
                        <select disabled id="quizzes" name="quizzes" " class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                            <option value="none">None</option>
                            <c:forEach items="${quizzes}" var="item">
                                <option value="${item.id}" ${lesson.quizzes.id==item.id ? "selected": ""}>${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="${lesson.content!=null ? "": "hidden"}" id="content-layout">
                <label for="content" class="block mb-2 text-sm font-medium text-gray-900">Content</label>
                <div>
                    ${lesson.content}
                </div>
            </div>
        </div>
    </form>
</div>

<div id="popup-modal-delete" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-md h-full md:h-auto">
        <div class="relative bg-white rounded-lg shadow">
            <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center " data-modal-toggle="popup-modal-delete">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
            </button>
            <div class="p-6 text-center">
                <svg class="mx-auto mb-4 w-14 h-14 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this lesson?</h3>
                <a id="button-confirm-delete" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>

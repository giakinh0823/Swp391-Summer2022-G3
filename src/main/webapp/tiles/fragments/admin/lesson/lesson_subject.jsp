<%-- 
    Document   : lesson_subject
    Created on : Jun 24, 2022, 1:55:49 PM
    Author     : giaki
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="flex items-center justify-between">
    <div class="flex space-x-2 items-center mb-5">
        <h1 class="text-2xl font-medium ml-3">${subject.name}</h1>
    </div>
</div>
<%@include file="/tiles/fragments/admin/subject/tabbar.jsp" %>
<div id="alert-message" class="${message!=null ? "": "hidden"} p-4 mb-4 text-sm ${message.code == "success" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"} rounded-lg" role="alert">
    <span class="font-medium" id="alert-message-code">${message.code == "success" ? "Success" : "Error"}!</span> <span id="alert-message-span">${message.message}</span>
</div>
<div class="flex items-center mb-3">
    <div>
        <a href="${pageContext.request.contextPath}/admin/subject/${subject.id}" class="py-2 px-5 mr-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 :focus:ring-gray-700">Back</a>
    </div>
    <div class="ml-auto">
        <c:if test="${(sessionScope.user.checkFeature('LESSON_CREATE') || sessionScope.user.is_super())}">
            <a href="${pageContext.request.contextPath}/admin/subject/${subject.id}/lesson/create" class="py-2 px-5 mr-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 :focus:ring-gray-700">Add new</a>
        </c:if>
    </div>
</div>
<div class="w-full p-2 overflow-x-auto">
    <div class="relative overflow-x-auto shadow-md sm:rounded-lg mt-5">
        <table class="w-full text-sm text-left text-gray-500">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50">
                <tr>
                    <th scope="col" class="px-6 py-3">
                        <div class="flex space-x-2 items-center">
                            <span>Id</span>

                        </div>
                    </th>
                    <th>

                    </th>
                    <th scope="col" class="px-6 py-3">
                        <div class="flex space-x-2 items-center">
                            <span>Lesson</span>

                        </div>
                    </th>
                    <th scope="col" class="px-6 py-3">
                        <div class="flex space-x-2 items-center">
                            <span>Order</span>

                        </div>
                    </th>
                    <th scope="col" class="px-6 py-3">
                        <div class="flex space-x-2 items-center">
                            <span>Type</span>

                        </div>
                    </th>

                    <th scope="col" class="px-6 py-3">
                        <div class="flex space-x-2 items-center">
                            <span>Status</span>

                        </div>
                    </th>
                    <th scope="col" class="px-6 py-3">
                        Action
                    </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${lessons}" var="lesson">

                    <tr class="border-b">
                        <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                            ${lesson.id}
                        </th>
                        <td class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                            <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 19a2 2 0 01-2-2V7a2 2 0 012-2h4l2 2h4a2 2 0 012 2v1M5 19h14a2 2 0 002-2v-5a2 2 0 00-2-2H9a2 2 0 00-2 2v5a2 2 0 01-2 2z"></path></svg>
                        </td>
                        <td class="px-6 py-4 font-medium text-gray-900 uppercase">
                            ${lesson.name}
                        </td>
                        <td class="px-6 py-4 font-medium text-gray-900 ">
                            ${lesson.order}
                        </td>
                        <td class="px-6 py-4 font-medium text-gray-900 ">
                            ${lesson.type.value}
                        </td>
                        <td class="px-6 py-4 font-medium text-gray-900 ">
                            ${lesson.status ? "activate" : "deactivate"}
                        </td>
                        <td scope="col" class="px-6 py-3">
                            <span class="flex items-center">
                                <a href="${pageContext.request.contextPath}/admin/subject/${subject.id}/lesson/${lesson.id}" class="font-medium text-blue-600">View</a>
                                <span class="font-medium mx-2">/</span>
                                <a href="${pageContext.request.contextPath}/admin/subject/${subject.id}/lesson/edit/${lesson.id}" class="font-medium text-blue-600">Edit</a>
                                <span class="font-medium mx-2">/</span>
                                <button type="button" onclick="$('#button-confirm-delete').attr('href', '${pageContext.request.contextPath}/admin/subject/${lesson.subject.id}/lesson/delete/${lesson.id}')" class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete">Delete</button>                       
                            </span>
                        </td>
                    </tr>
                    <c:forEach items="${lesson.lessons}" var="child">
                        <tr class="border-b">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                ${child.id}
                            </th>
                            <td scope="row" class="px-6 py-4 font-medium text-gray-900">
                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7"></path></svg>
                            </td>

                            <td class="px-6 py-4 font-medium text-gray-900">
                                ${child.name}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900 ">
                                ${child.order}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900 ">
                                ${child .type.value}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900 ">
                                ${child.status ? "activate" : "deactivate"}
                            </td>
                            <td scope="col" class="px-6 py-3">
                                <span class="flex items-center">
                                    <a href="${pageContext.request.contextPath}/admin/subject/${subject.id}/lesson/${child.id}" class="font-medium text-blue-600">View</a>
                                    <span class="font-medium mx-2">/</span>
                                    <a href="${pageContext.request.contextPath}/admin/subject/${subject.id}/lesson/edit/${child.id}" class="font-medium text-blue-600">Edit</a>
                                    <span class="font-medium mx-2">/</span>
                                    <button type="button" onclick="$('#button-confirm-delete').attr('href', '${pageContext.request.contextPath}/admin/subject/${lesson.subject.id}/lesson/delete/${child.id}')" class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete">Delete</button>                       
                                </span>
                            </td>
                        </tr>
                    </c:forEach>

                </c:forEach>
            </tbody>
        </table>
    </div>    

</div>

<div id="popup-modal-delete" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-md h-full md:h-auto">
        <div class="relative bg-white rounded-lg shadow">
            <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center " data-modal-toggle="popup-modal-delete">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
            </button>
            <div class="p-6 text-center">
                <svg class="mx-auto mb-4 w-14 h-14 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this setting?</h3>
                <a id="button-confirm-delete" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div> 

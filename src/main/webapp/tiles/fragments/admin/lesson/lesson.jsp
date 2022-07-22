<%-- 
    Document   : lesson
    Created on : Jun 6, 2022, 8:35:04 PM
    Author     : giaki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="flex items-center justify-between">
    <div class="flex space-x-2 items-center mb-5">
        <a href="${backlink}"><svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M9.707 16.707a1 1 0 01-1.414 0l-6-6a1 1 0 010-1.414l6-6a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l4.293 4.293a1 1 0 010 1.414z" clip-rule="evenodd"></path></svg></a>
        <h1 class="text-2xl font-medium ml-3">${subject.name}</h1>
    </div>
</div>
<div class="flex items-center">
    <div>
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
    <div class="flex items-center ml-auto">
        <c:if test="${(sessionScope.user.checkFeature('QUIZZES_CREATE') || sessionScope.user.is_super())}">
            <div class="ml-2">
                <a href="${pageContext.request.contextPath}/admin/subject/lesson/create" class="py-2 px-5 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 block">Add new</a>
            </div>
        </c:if>
        <div id="button-open-filter" class="block md:hidden" onclick="showFilterbar(this)">
            <button type="button" class="text-white bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 hover:bg-gradient-to-br focus:ring-2 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-2 py-2 text-center">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M3 3a1 1 0 011-1h12a1 1 0 011 1v3a1 1 0 01-.293.707L12 11.414V15a1 1 0 01-.293.707l-2 2A1 1 0 018 17v-5.586L3.293 6.707A1 1 0 013 6V3z" clip-rule="evenodd"></path></svg>            
            </button>
        </div>
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
                <c:forEach items="${lessons}" var="lessons">

                    <tr class="border-b">
                        <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                            ${lessons.id}
                        </th>
                        <td class="px-6 py-4 font-medium text-gray-900">
                            ${lessons.name}
                        </td>
                        <td class="px-6 py-4 font-medium text-gray-900 ">
                            ${lessons.order}
                        </td>
                        <td class="px-6 py-4 font-medium text-gray-900 ">
                            ${lessons.type.value}
                        </td>
                        <td class="px-6 py-4 font-medium text-gray-900 ">
                            ${lessons.status ? "active" : "inactive"}
                        </td>
                        <td scope="col" class="px-6 py-3">
                            <span class="flex items-center">
                                <a href="#" class="font-medium text-blue-600">View</a>
                                <span class="font-medium mx-2">/</span>
                                <a href="#" class="font-medium text-blue-600">Edit</a>
                                <span class="font-medium mx-2">/</span>
                                <button type="button" onclick="$('#button-confirm-delete').attr('href', '${pageContext.request.contextPath}/admin/subject/${lessons.subject.id}/lesson/delete/${lessons.id}')" class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete">Delete</button>                       
                            </span>
                        </td>
                    </tr>
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



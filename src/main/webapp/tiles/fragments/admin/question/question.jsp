<%-- 
    Document   : question
    Created on : Jun 8, 2022, 10:09:44 PM
    Author     : giaki
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <c:if test="${(sessionScope.user.checkFeature('QUESTION_CREATE') || sessionScope.user.is_super())}">
            <div class="ml-2">
                <a href="${pageContext.request.contextPath}/admin/question/create" class="py-2 px-5 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 block">Add new</a>
            </div>
            <div class="ml-2">
                <button data-modal-toggle="import-question-modal" type="button" class="py-2 px-5 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 block">Import file</button>
            </div>
        </c:if>
        <div id="button-open-filter" class="block md:hidden" onclick="showFilterbar(this)">
            <button type="button" class="text-white bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 hover:bg-gradient-to-br focus:ring-2 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-2 py-2 text-center">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M3 3a1 1 0 011-1h12a1 1 0 011 1v3a1 1 0 01-.293.707L12 11.414V15a1 1 0 01-.293.707l-2 2A1 1 0 018 17v-5.586L3.293 6.707A1 1 0 013 6V3z" clip-rule="evenodd"></path></svg>            
            </button>
        </div>
    </div>
</div>
<div class="w-full flex">
    <div class="w-full p-2 overflow-x-auto">
        <div id="alert-message" class="${message!=null ? "": "hidden"} p-4 mb-4 text-sm ${message.code == "success" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"} rounded-lg" role="alert">
            <span class="font-medium" id="alert-message-code">${message.code == "success" ? "Success" : "Error"}!</span> <span id="alert-message-span">${message.message}</span>
        </div>
        <div class="relative overflow-x-auto shadow-md sm:rounded-lg mt-5">
            <table class="w-full text-sm text-left text-gray-500">
                <thead class="text-xs text-gray-700 uppercase bg-gray-50">
                    <tr>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Id</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${pageable.checkSort('id_ASC')?"":"hidden"}" 
                                         id="sort-up-id" 
                                         onclick="sortHandleColumn('id_DESC', this, 'sort-down-id')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${!pageable.checkField("id") ? "" : pageable.checkSort('id_DESC')? "": "hidden"}" 
                                         id="sort-down-id" 
                                         onclick="sortHandleColumn('id_ASC', this, 'sort-up-id')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Content</span>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3 text-center">
                            <span>Media</span>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Level</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("level") ? "" : pageable.checkSort('level_ASC')?"" :"hidden"}" 
                                         id="sort-up-level" 
                                         onclick="sortHandleColumn('level_DESC', this, 'sort-down-level')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('level_DESC')?"":"hidden"}" 
                                         id="sort-down-level" 
                                         onclick="sortHandleColumn('level_ASC', this, 'sort-up-level')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Status</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("status") ? "" : pageable.checkSort('status_ASC')?"" :"hidden"}" 
                                         id="sort-up-status" 
                                         onclick="sortHandleColumn('status_DESC', this, 'sort-down-status')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('status_DESC')?"":"hidden"}" 
                                         id="sort-down-status" 
                                         onclick="sortHandleColumn('status_ASC', this, 'sort-up-status')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Multi</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("multi") ? "" : pageable.checkSort('multi_ASC')?"" :"hidden"}" 
                                         id="sort-up-multi" 
                                         onclick="sortHandleColumn('multi_DESC', this, 'sort-down-multi')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('multi_DESC')?"":"hidden"}" 
                                         id="sort-down-multi" 
                                         onclick="sortHandleColumn('multi_ASC', this, 'sort-up-multi')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Dimension</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("dimension") ? "" : pageable.checkSort('dimension_ASC')?"" :"hidden"}" 
                                         id="sort-up-dimension" 
                                         onclick="sortHandleColumn('dimension_DESC', this, 'sort-down-dimension')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('dimension_DESC')?"":"hidden"}" 
                                         id="sort-down-dimension" 
                                         onclick="sortHandleColumn('dimension_ASC', this, 'sort-up-dimension')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center justify-center">
                                <span>Lesson</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("lesson") ? "" : pageable.checkSort('lesson_ASC')?"" :"hidden"}" 
                                         id="sort-up-lesson" 
                                         onclick="sortHandleColumn('lesson_DESC', this, 'sort-down-lesson')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('lesson_DESC')?"":"hidden"}" 
                                         id="sort-down-lesson" 
                                         onclick="sortHandleColumn('lesson_ASC', this, 'sort-up-lesson')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Subject</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("subject") ? "" : pageable.checkSort('subject_ASC')?"" :"hidden"}" 
                                         id="sort-up-subject" 
                                         onclick="sortHandleColumn('subject_DESC', this, 'sort-down-subject')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('subject_DESC')?"":"hidden"}" 
                                         id="sort-down-subject" 
                                         onclick="sortHandleColumn('subject_ASC', this, 'sort-up-subject')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Action
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${questions}" var="question">

                        <tr class="border-b">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                ${question.id}
                            </th>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                <p class="max-w-2xl" style="
                                   max-width: 400px;
                                   min-width: 200px;
                                   overflow: hidden;
                                   text-overflow: ellipsis;
                                   display: -webkit-box;
                                   -webkit-line-clamp: 2;
                                   -webkit-box-orient: vertical;">
                                    ${question.content}
                                </p>
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900 flex items-center justify-center">
                                <c:if test="${(question.media!=null && question.media.type=='image')}">
                                    <img width="200" height="200" class="min-w-[200px] rounded" src="${pageContext.request.contextPath}/images/question/${question.media.url}" alt="image"/>
                                </c:if>
                                <c:if test="${(question.media!=null && question.media.type=='audio')}">
                                    <div id="media-audio">
                                        <audio controls id="audio"
                                               src="${pageContext.request.contextPath}/images/question/${(question.media!=null && question.media.type=="audio") ? question.media.url :""}"></audio>
                                    </div>
                                </c:if>
                                <c:if test="${(question.media!=null && question.media.type=='video')}">
                                    <div id="media-video">
                                        <video class="min-w-[200px] rounded" width="200" height="200" id="video" controls
                                               src="${pageContext.request.contextPath}/images/question/${(question.media!=null && question.media.type=="video") ? question.media.url :""}"></video>
                                    </div>
                                </c:if>
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900 text-center">
                                ${question.level.value}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                ${question.status ? "Active" : "Inactive"}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap text-center">
                                ${question.is_multi() ? "Multi choice" : "Non"}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900 text-center">
                                ${question.dimension.name}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900 text-center">
                                <p class="max-w-2xl" style="max-width: 300px; min-width: 180px; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;">
                                    ${question.lesson.name}
                                </p>
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900 ">
                                <p class="max-w-2xl" style=" max-width: 400px; min-width: 200px; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;">
                                    ${question.subject.name}
                                </p>
                            </td>
                            <td scope="col" class="px-6 py-3">
                                <span class="flex items-center">
                                    <a href="${pageContext.request.contextPath}/admin/question/${question.id}" class="font-medium text-blue-600">View</a>
                                    <span class="font-medium mx-1">/</span>
                                    <a href="${pageContext.request.contextPath}/admin/question/edit/${question.id}" class="font-medium text-blue-600">Edit</a>
                                    <span class="font-medium mx-1">/</span>
                                    <button type="button" onclick="$('#button-confirm-delete').attr('href', '${pageContext.request.contextPath}/admin/question/delete/${question.id}')" class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete">Delete</button>                       
                                </span>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>    
        <c:if test="${page.size<=0}">
            <h2 class="mt-20 text-3xl text-center">Not found question!</h2>
        </c:if>
        <c:if test="${page.size>0}">
            <%@include file="/views/component/pagination.jsp" %>
        </c:if>
    </div>
    <div id="filter-drag-layout" class="hidden md:block md:min-w-[200px] md:max-w-[200px] md:xl:min-w-[256px] md:xl:max-w-[256px]">
        <div id="filter-drag-content" class="w-full mt-3 p-5 ui-widget-content max-h-[85vh] overflow-y-auto" style="box-shadow: rgba(0, 0, 0, 0.25) 0px 5px 15px; border:none">
            <div class="hidden flex items-center justify-end mb-3" id="button-close-filter">
                <button type="button" onclick="closeFilter()">
                    <svg class="w-4 h-4 text-gray-700" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>                
                </button>
            </div>
            <div class="border-b border-gray-200 flex justify-between">
                <h2 class="mb-3 font-medium uppercase">Filter</h2>
                <button type="button" onclick="window.location.search = ''">
                    <h2 class="mb-3 font-medium uppercase text-sm text-blue-600">Clear</h2>
                </button>
            </div>
            <form class="mt-5">
                <div class="mb-6">
                    <h2 class="mb-3 font-medium mb-3 hidden" id="label-subject"></h2>
                    <div class="w-full">
                        <div class="relative w-full">
                            <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                                <svg class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>                            
                            </div>
                            <input oninput="searchItemFilterSubject(this)" type="text" id="search-subject" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2 " placeholder="Search subject">
                        </div>
                    </div>
                    <div id="spin-search-subject" class="flex justify-center items-center mt-8 hidden">
                        <svg role="status" class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
                            <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
                        </svg>
                    </div>
                    <div class="min-h-[180px] max-h-[200px] overflow-y-auto px-2 pt-3 max-w-[200px]" id="list-subject">
                        <c:forEach items="${subjects}" var="item">
                            <div class="flex items-center mb-2">
                                <input type="radio" value="${item.id}" onchange="selectSubject(this)"  ${pageable.checkFilters('subject', item.id) ? "checked": ""} name="subject" id="subject-${item.id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                <label for="subject-${item.id}" id="label-subject-${item.id}" class="item-subject ml-2 text-sm font-medium text-gray-900">${item.name}</label>
                            </div>
                        </c:forEach>
                    </div>
                    <div id="spin-search-subject-loadmore" class="flex justify-center items-center hidden">
                        <svg role="status" class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
                            <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
                        </svg>
                    </div>
                </div>
                <div class="mb-6 hidden" id="lesson-content">
                    <h2 class="mb-3 font-medium mb-3 hidden" id="label-lesson"></h2>
                    <div class="w-full mb-2">
                        <div class="relative w-full">
                            <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                                <svg class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>                            
                            </div>
                            <input oninput="searchItemFilterLesson(this)" type="text" id="search-lesson" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2 " placeholder="Search lesson">
                        </div>
                    </div>
                    <div id="spin-search-lesson" class="flex justify-center items-center mt-8 hidden">
                        <svg role="status" class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
                            <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
                        </svg>
                    </div>
                    <div class="min-h-[150px] max-h-[180px] overflow-y-auto px-2 pt-2" id="list-lesson">
                        <div class="flex items-center mb-2">
                            <input type="radio" onchange="selectLesson(this)" name="lesson"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                            <label class="item-lesson ml-2 text-sm font-medium text-gray-900"></label>
                        </div>
                    </div>
                    <div class="hidden" id="not-found-lesson">
                        <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                            <span class="font-medium">Danger alert!</span> You need add lesson for current subject selected.
                        </div>
                    </div>
                    <div id="spin-search-lesson-loadmore" class="flex justify-center items-center hidden">
                        <svg role="status" class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
                            <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
                        </svg>
                    </div>
                </div>
                <div class="hidden" id="not-found-dimension">
                    <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                        <span class="font-medium">Danger alert!</span> Not found dimension
                    </div>
                </div>
                <div id="spin-dimension-select" class="flex justify-center items-center hidden">
                    <svg role="status" class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
                        <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
                    </svg>
                </div>
                <div class="mb-6 hidden" id="dimension-content">
                    <div>
                        <label for="dimension" class="block mb-2 text-sm font-medium text-gray-900">Dimension</label>
                    </div>
                    <div class="min-h-[130px] max-h-[150px] overflow-y-auto px-2 pt-2" id="dimension-list">

                    </div>
                </div>
                <div class="mb-3">
                    <h2 class="mb-3 font-medium">Level</h2>
                    <c:forEach items="${levels}" var="level">
                        <div class="flex items-center mb-2">
                            <input type="checkbox" value="${level.id}" ${pageable.checkFilters('level', level.id) ? "checked": ""} name="level"  id="level-${level.id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                                <label for="level-${level.id}" class="ml-2 text-sm font-medium text-gray-900">${level.value}</label>
                        </div>
                    </c:forEach>
                </div>
                <div class="mb-3">
                    <h2 class="mb-3 font-medium">Status</h2>
                    <div class="flex items-center mb-2">
                        <input type="checkbox" value="true" name="status" id="status-show" ${pageable.checkFilters('status', 'true') ? "checked": ""}  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="status-show" class="ml-2 text-sm font-medium text-gray-900">Active</label>
                    </div>
                    <div class="flex items-center mb-2">
                        <input type="checkbox" value="false" name="status"  id="status-hidden" ${pageable.checkFilters('status', 'false') ? "checked": ""}  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="status-hidden" class="ml-2 text-sm font-medium text-gray-900">Inactive</label>
                    </div>
                </div>
                <div class="mb-3">
                    <h2 class="mb-3 font-medium">Multi</h2>
                    <div class="flex items-center mb-2">
                        <input type="checkbox" value="true" name="multi" id="featured-show" ${pageable.checkFilters('multi', 'true') ? "checked": ""}   class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="featured-show" class="ml-2 text-sm font-medium text-gray-900">Multi</label>
                    </div>
                    <div class="flex items-center mb-2">
                        <input type="checkbox" value="false" name="multi" id="featured-hidden" ${pageable.checkFilters('multi', 'false') ? "checked": ""}  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="featured-hidden" class="ml-2 text-sm font-medium text-gray-900">Not Multi</label>
                    </div>
                </div>
                <div class="flex mt-2">
                    <button type="submit" class="w-full bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-3 py-2 mt-2 focus:outline-none text-white">Filter</button>
                </div>
            </form>
        </div>
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
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this question?</h3>
                <a id="button-confirm-delete" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>
                            
<!-- Extra Large Modal -->
<div id="import-question-modal" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 w-full md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-7xl h-full">
        <!-- Modal content -->
        <div class="relative bg-white rounded-lg shadow">
            <!-- Modal header -->
            <div class="flex justify-between items-center p-5 rounded-t border-b" >
                <h3 class="text-xl font-medium text-gray-900 ">
                    Question import
                </h3>
                <button type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center" data-modal-toggle="import-question-modal">
                    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
                </button>
            </div>
            <!-- Modal body -->
            <div class="p-6 space-y-6">
                <%@include file="/tiles/fragments/admin/question/question_import.jsp" %>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/js/admin/question_import.js"></script>

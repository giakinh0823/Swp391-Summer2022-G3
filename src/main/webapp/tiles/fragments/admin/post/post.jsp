<%-- 
    Document   : post
    Created on : May 30, 2022, 11:13:54 PM
    Author     : giaki
--%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="text-lg mb-3 font-medium">Manage post</h1>
<div class="flex justify-end mb-6 items-center">
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
    <div class="ml-auto flex items-center">
        <c:if test="${(sessionScope.user.checkFeature('POST_CREATE') || sessionScope.user.is_super())}">
            <a href="post/create" class="py-2 px-5 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 :focus:ring-gray-700">Add new</a>
        </c:if>
        <div id="button-open-filter" class="block md:hidden" onclick="showFilterbar(this)">
            <button type="button" class="text-white bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 hover:bg-gradient-to-br focus:ring-2 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-2 py-2 text-center">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M3 3a1 1 0 011-1h12a1 1 0 011 1v3a1 1 0 01-.293.707L12 11.414V15a1 1 0 01-.293.707l-2 2A1 1 0 018 17v-5.586L3.293 6.707A1 1 0 013 6V3z" clip-rule="evenodd"></path></svg>            </button>
        </div>
    </div>
</div>
<div class="w-full flex">
    <div class="w-full p-2 overflow-x-auto">
        <div id="alert-message" class="${message!=null ? "": "hidden"} p-4 mb-4 text-sm ${message.code == "success" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"} rounded-lg" role="alert">
            <span class="font-medium" id="alert-message-code">${message.code == "success" ? "Success" : "Error"}!</span> <span id="alert-message-span">${message.message}</span>
        </div>
        <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
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
                                <span>Title</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("title") ? "" : pageable.checkSort('title_ASC')?"" :"hidden"}" 
                                         id="sort-up-title" 
                                         onclick="sortHandleColumn('title_DESC', this, 'sort-down-title')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('title_DESC')?"":"hidden"}" 
                                         id="sort-down-title" 
                                         onclick="sortHandleColumn('title_ASC', this, 'sort-up-title')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Thumbnail
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Category</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("category") ? "" : pageable.checkSort('category_ASC')?"" :"hidden"}" 
                                         id="sort-up-category" 
                                         onclick="sortHandleColumn('category_DESC', this, 'sort-down-category')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('category_DESC')?"":"hidden"}" 
                                         id="sort-down-category" 
                                         onclick="sortHandleColumn('category_ASC', this, 'sort-up-category')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Author</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("author") ? "" : pageable.checkSort('author_ASC')?"" :"hidden"}" 
                                         id="sort-up-author" 
                                         onclick="sortHandleColumn('author_DESC', this, 'sort-down-author')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('author_DESC')?"":"hidden"}" 
                                         id="sort-down-author" 
                                         onclick="sortHandleColumn('author_ASC', this, 'sort-up-author')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Featured</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("featured") ? "" : pageable.checkSort('featured_ASC')?"" :"hidden"}" 
                                         id="sort-up-featured" 
                                         onclick="sortHandleColumn('featured_DESC', this, 'sort-down-featured')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('featured_DESC')?"":"hidden"}" 
                                         id="sort-down-featured" 
                                         onclick="sortHandleColumn('featured_ASC', this, 'sort-up-featured')" 
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
                                         id="sort-up-author" 
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
                                <span>Created</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("created") ? "" : pageable.checkSort('created_ASC')?"" :"hidden"}" 
                                         id="sort-up-created" 
                                         onclick="sortHandleColumn('created_DESC', this, 'sort-down-created')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('created_DESC')?"":"hidden"}" 
                                         id="sort-down-created" 
                                         onclick="sortHandleColumn('created_ASC', this, 'sort-up-created')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Updated</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("updated") ? "" : pageable.checkSort('updated_ASC')?"" :"hidden"}" 
                                         id="sort-up-updated" 
                                         onclick="sortHandleColumn('updated_DESC', this, 'sort-down-updated')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('updated_DESC')?"":"hidden"}" 
                                         id="sort-down-updated" 
                                         onclick="sortHandleColumn('updated_ASC', this, 'sort-up-updated')" 
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
                    <c:if test="${posts!=null && posts.size()>0}">
                        <c:forEach items="${posts}" var="post">
                            <tr class="bg-white border-b hover:bg-gray-50">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                    ${post.id}
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    <p style="
                                       max-width: 200px;
                                       min-width: 180px;
                                       overflow: hidden;
                                       text-overflow: ellipsis;
                                       display: -webkit-box;
                                       -webkit-line-clamp: 2;
                                       -webkit-box-orient: vertical;">${post.title}</p>
                                </td>
                                <td scope="col" class="px-6 py-3 font-medium text-gray-900">
                                    <c:choose>
                                        <c:when test="${fn:containsIgnoreCase(post.thumbnail, 'dummyimage.com')}">
                                            <img class="${post.thumbnail!=null ? "":"hidden"} max-w-[100px] max-h-[100px] overflow-hidden" alt="${post.title}" src="${post.thumbnail}" id="image-preview"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img class="${post.thumbnail!=null ? "":"hidden"} max-w-[100px] max-h-[100px] overflow-hidden" alt="${post.title}" src="${pageContext.request.contextPath}/images/post/${post.thumbnail}" id="image-preview"/>
                                        </c:otherwise>
                                    </c:choose>  
                                </td>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    ${post.category.value}
                                </td>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    ${post.user.username}
                                </td>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    <label for="featured-${post.id}" class="inline-flex relative items-center cursor-pointer">
                                        <input type="checkbox" disabled ${post.featured ? "checked" : ""} value="true" name="featured" id="featured-${post.id}" class="sr-only peer" ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"}>
                                            <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                                    </label>
                                </td>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    <label for="status-${post.id}" class="inline-flex relative items-center cursor-pointer">
                                        <input type="checkbox" disabled  ${post.status ? "checked" : ""} value="active" id="status-${post.id}" class="sr-only peer" ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"}>
                                            <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                                            <span class="ml-3 text-sm font-medium text-gray-900">Show</span>
                                    </label>
                                </td>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    ${post.created_at}
                                </td>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    ${post.updated_at}
                                </td>
                                <td class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                    <a href="${pageContext.request.contextPath}/admin/post/${post.id}" class="font-medium text-blue-600 hover:underline">View</a>
                                    <c:if test="${sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()}">
                                        <span class="font-medium mx-1">/</span>
                                        <a href="${pageContext.request.contextPath}/admin/post/edit/${post.id}" class="font-medium text-blue-600 hover:underline">Edit</a>
                                    </c:if>
                                    <c:if test="${sessionScope.user.checkFeature('POST_DELETE') || sessionScope.user.is_super()}">
                                        <span class="font-medium mx-1">/</span>
                                        <button type="button" onclick="$('#button-confirm-delete').attr('href', '${pageContext.request.contextPath}/admin/post/delete/${post.id}')"  class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete">Delete</button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
        </div>
        <c:if test="${posts==null || posts.size()<=0}">
            <h2 class="mt-20 text-3xl text-center">Not found post!</h2>
        </c:if>
        <c:if test="${posts!=null && posts.size()>0}">
            <%@include file="/views/component/pagination.jsp" %>
        </c:if>
    </div>
    <div id="filter-drag-layout" class="hidden md:block md:min-w-[200px] md:max-w-[200px] xl:min-w-[256px] xl:max-w-[256px]">
        <div id="filter-drag-content" class="w-full p-5 ui-widget-content max-h-[85vh] overflow-y-auto" style="box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px; border:none">
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
                <div class="mb-3">
                    <h2 class="mb-3 font-medium mb-3">Category</h2>
                    <div class="w-full mb-2">
                        <div class="relative w-full">
                            <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                                <svg class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>                            
                            </div>
                            <input oninput="searchItemFilter('category', this)" type="text" id="search-category" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2 " placeholder="Search category">
                        </div>
                    </div>
                    <div class="mb-5 flex flex-wrap max-w-full space-x-1 px-2">
                        <c:forEach items="${categories}" var="item">
                            <c:if test="${pageable.checkFilters('category', item.id)}">
                                <div class="flex items-center">
                                    <span class="text-sm font-medium">${item.value}</span>
                                    <button onclick="removeFilter('category',${item.id})" class="ml-1 text-gray-700 hover:text-gray-900" type="button">
                                        <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"></path></svg>                                    
                                    </button>
                                </div>
                            </c:if>
                            <c:forEach items="${item.childs}" var="child">
                                <c:if test="${pageable.checkFilters('category', child.id)}">
                                    <div class="flex items-center">
                                        <span class="text-sm font-medium">${child.value}</span>
                                        <button onclick="removeFilter('category',${child.id})" class="ml-1 text-gray-700 hover:text-gray-900" type="button">
                                            <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"></path></svg>                                        
                                        </button>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:forEach>
                    </div>
                    <div class="min-h-[180px] max-h-[200px] overflow-y-auto px-2">
                        <c:forEach items="${categories}" var="item">
                            <div class="flex items-center mb-2">
                                <input type="checkbox" value="${item.id}" ${pageable.checkFilters('category', item.id) ? "checked": ""} name="category" id="category-${item.id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                                    <label for="category-${item.id}" class="item-category ml-2 text-sm font-medium text-gray-900">${item.value}</label>
                            </div>
                            <c:forEach items="${item.childs}" var="child">
                                <div class="ml-3 flex items-center mb-2">
                                    <input parent="${item.id}" type="checkbox" value="${child.id}" ${pageable.checkFilters('category', child.id) ? "checked": ""} name="category" id="category-${child.id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                                        <label for="category-${child.id}" class="item-category ml-2 text-sm font-medium text-gray-900">${child.value}</label>
                                </div>
                            </c:forEach>
                        </c:forEach>
                    </div>
                </div>
                <div class="mb-3">
                    <h2 class="mb-3 font-medium">Author</h2>
                    <c:forEach items="${groups}" var="group">
                        <div class="flex items-center mb-2">
                            <input type="checkbox" value="${group.id}" ${pageable.checkFilters('author', group.id) ? "checked": ""} name="author" id="author-${group.id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                                <label for="author-${group.id}" class="ml-2 text-sm font-medium text-gray-900">${group.value}</label>
                        </div>
                    </c:forEach>
                </div>
                <div class="mb-3">
                    <h2 class="mb-3 font-medium">Status</h2>
                    <div class="flex items-center mb-2">
                        <input type="checkbox" value="show" name="status" ${pageable.checkFilters('status', 'show') ? "checked": ""} id="status-show"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="status-show" class="ml-2 text-sm font-medium text-gray-900">Show</label>
                    </div>
                    <div class="flex items-center mb-2">
                        <input type="checkbox" value="hidden" name="status" ${pageable.checkFilters('status', 'hidden') ? "checked": ""} id="status-hidden"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="status-hidden" class="ml-2 text-sm font-medium text-gray-900">Hidden</label>
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
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this post?</h3>
                <a id="button-confirm-delete" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>
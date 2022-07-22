<%-- 
    Document   : blog
    Created on : Jun 8, 2022, 9:45:07 PM
    Author     : congg
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="container justify-items-center content-center mx-auto mt-10">
    <c:if test="${featureBlogs.size() >= 3 && search==null}">
        <div class="grid grid-cols-12 grid-rows-2 gap-4">
            <div class="col-span-9 row-span-2">
                <div
                    class="h-full bg-white rounded-lg border border-gray-200">
                    <a href="${pageContext.request.contextPath}/blog/${featureBlogs.get(0).id}">
                        <c:choose>
                            <c:when test="${fn:containsIgnoreCase(featureBlogs.get(0).thumbnail, 'dummyimage.com')}">
                                <img class="w-full h-[440px]" alt="${featureBlogs.get(0).title}" src="${featureBlogs.get(0).thumbnail}" id="image-preview"/>
                            </c:when>
                            <c:otherwise>
                                <img class="w-full h-[440px]" alt="${featureBlogs.get(0).title}" src="${pageContext.request.contextPath}/images/post/${featureBlogs.get(0).thumbnail}" id="image-preview"/>
                            </c:otherwise>
                        </c:choose> 
                    </a>
                    <div class="p-2">
                        <a href="${pageContext.request.contextPath}/blog/${featureBlogs.get(0).id}">
                            <h5 class="mb-2 text-xl font-medium tracking-tight text-gray-900">${featureBlogs.get(0).title}</h5>
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-span-3 row-span-1">
                <div
                    class=" bg-white rounded-lg border border-gray-200 ">
                    <a href="${pageContext.request.contextPath}/blog/${featureBlogs.get(1).id}">
                        <c:choose>
                            <c:when test="${fn:containsIgnoreCase(featureBlogs.get(1).thumbnail, 'dummyimage.com')}">
                                <img class="w-full h-[190px]" alt="${featureBlogs.get(1).title}" src="${featureBlogs.get(1).thumbnail}" id="image-preview"/>
                            </c:when>
                            <c:otherwise>
                                <img class="w-full h-[190px]" alt="${featureBlogs.get(1).title}" src="${pageContext.request.contextPath}/images/post/${featureBlogs.get(1).thumbnail}" id="image-preview"/>
                            </c:otherwise>
                        </c:choose> 
                    </a>
                    <div class="p-2">
                        <a href="${pageContext.request.contextPath}/blog/${featureBlogs.get(1).id}">
                            <h5 class="mb-2 text-lg font-medium tracking-tight text-gray-900 "
                                style=" max-width: 400px;
                                min-width: 280px;
                                overflow: hidden;
                                text-overflow: ellipsis;
                                display: -webkit-box;
                                -webkit-line-clamp: 1;
                                -webkit-box-orient: vertical;">${featureBlogs.get(1).title}</h5>
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-start-10 col-end-13 row-span-2">
                <div
                    class=" bg-white rounded-lg border border-gray-200 ">
                    <a href="${pageContext.request.contextPath}/blog/${featureBlogs.get(2).id}">
                        <c:choose>
                            <c:when test="${fn:containsIgnoreCase(featureBlogs.get(2).thumbnail, 'dummyimage.com')}">
                                <img class="w-full h-[190px]" alt="${featureBlogs.get(2).title}" src="${featureBlogs.get(2).thumbnail}" id="image-preview"/>
                            </c:when>
                            <c:otherwise>
                                <img class="w-full h-[190px]" alt="${featureBlogs.get(2).title}" src="${pageContext.request.contextPath}/images/post/${featureBlogs.get(2).thumbnail}" id="image-preview"/>
                            </c:otherwise>
                        </c:choose> 
                    </a>
                    <div class="p-2">
                        <a href="${pageContext.request.contextPath}/blog/${featureBlogs.get(2).id}">
                            <h5 class="mb-2 text-lg font-medium tracking-tight text-gray-900 "
                                style=" max-width: 400px;
                                min-width: 280px;
                                overflow: hidden;
                                text-overflow: ellipsis;
                                display: -webkit-box;
                                -webkit-line-clamp: 1;
                                -webkit-box-orient: vertical;">${featureBlogs.get(2).title}</h5>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <div class="grid grid-cols-12 gap-4 mb-6 mt-5">
        <div class="col-span-9">
            <div class="mb-4 max-w-xl mx-auto">
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
            <div>
                <div class="border-b-2 pb-3 border-blue-700">
                    <h1 class=" font-bold">Last Post</h1>
                </div>
                <div
                    class="text-sm font-medium text-center text-gray-500 mb-6 overflow-x-auto">
                    <ul class="flex items-center scroll-pl-6 snap-x">
                        <li class="snap-start mr-2 cursor-pointer">
                            <a href="${pageContext.request.contextPath}/blog"
                               class="inline-block p-4 text-gray-800 rounded-t-lg border-b-2 border-transparent hover:text-gray-900 hover:border-gray-500 ${pageable.checkFilter('category') ? "" :"border-gray-500"} ">All</a>
                        </li>
                        <c:forEach items="${categoryBlogs}" var="category">
                            <li class="mr-2 cursor-pointer">
                                <a onclick="filterClickHandleColumn('category', ${category.id})"
                                   class="whitespace-nowrap inline-block p-4 text-gray-800 rounded-t-lg border-b-2 border-transparent hover:text-gray-900 hover:border-gray-500 ${pageable.checkFilters('category', category.id) ? "border-gray-500": ""} ">${category.value}</a>
                            </li>
                        </c:forEach>
                        <c:forEach items="${categoryBlogs}" var="item">
                            <c:forEach items="${item.childs}" var="category">
                                <li class="mr-2 cursor-pointer">
                                    <a onclick="filterClickHandleColumn('category', ${category.id})"
                                       class="whitespace-nowrap inline-block p-4 text-gray-800 rounded-t-lg border-b-2 border-transparent hover:text-gray-900 hover:border-gray-500 ${pageable.checkFilters('category', category.id) ? "border-gray-500": ""} ">${category.value}</a>
                                </li>
                            </c:forEach>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <div class="grid grid-cols-9 gap-4 mb-6">
                <c:forEach items="${blogs}" var="blog">
                    <div class="col-span-3">
                        <div class="h-full bg-white rounded-lg border border-gray-200 ">
                            <a href="${pageContext.request.contextPath}/blog/${blog.id}">
                                <c:choose>
                                    <c:when test="${fn:containsIgnoreCase(blog.thumbnail, 'dummyimage.com')}">
                                        <img class="w-full h-[180px]" alt="${blog.title}" src="${blog.thumbnail}" id="image-preview"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img class="w-full h-[180px]" alt="${blog.title}" src="${pageContext.request.contextPath}/images/post/${blog.thumbnail}" id="image-preview"/>
                                    </c:otherwise>
                                </c:choose>  
                            </a>
                            <div class="p-2">
                                <div>
                                    <a href="${pageContext.request.contextPath}/blog/${blog.id}">
                                        <h5 class="mb-1 text-md font-medium tracking-tight text-gray-900 "
                                            style=" max-width: 400px;
                                            min-width: 280px;
                                            overflow: hidden;
                                            text-overflow: ellipsis;
                                            display: -webkit-box;
                                            -webkit-line-clamp: 2;
                                            -webkit-box-orient: vertical;">${blog.title}</h5>
                                    </a>
                                </div>
                                <div class="flex items-center">
                                    <p class="text-sm font-small">${blog.user.last_name} ${blog.user.first_name}</p>
                                    <span class="mx-2">-</span>
                                    <p class="text-sm font-small"><fmt:formatDate pattern="MMM dd, yyyy" value="${blog.updated_at}"/></p>
                                </div>
                                <p class="mb-3 font-normal text-gray-700 "
                                   style=" max-width: 400px;
                                   min-width: 280px;
                                   overflow: hidden;
                                   text-overflow: ellipsis;
                                   display: -webkit-box;
                                   -webkit-line-clamp: 2;
                                   -webkit-box-orient: vertical;">${blog.description}</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>            
        </div>
        <div class="col-span-3">
            <div class="mb-3 mt-14">
                <h1 class=" font-bold mt-5">Blog featured</h1>
            </div>
            <c:forEach items="${featureRandoms}" var="blog">
                <div class="border-t-2 pt-4 border-blue-700 mb-3">
                    <a href="${pageContext.request.contextPath}/blog/${blog.id}">
                        <h5 class="mb-1 text-md font-medium tracking-tight text-gray-900 "
                            style=" max-width: 400px;
                            min-width: 280px;
                            overflow: hidden;
                            text-overflow: ellipsis;
                            display: -webkit-box;
                            -webkit-line-clamp: 3;
                            -webkit-box-orient: vertical;">${blog.title}</h5>
                    </a>
                    <p class="mb-3 font-normal text-gray-700 "
                       style=" max-width: 400px;
                       min-width: 280px;
                       overflow: hidden;
                       text-overflow: ellipsis;
                       display: -webkit-box;
                       -webkit-line-clamp: 5;
                       -webkit-box-orient: vertical;">
                        ${blog.description}
                    </p>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
<c:if test="${page.size<=0}">
    <h2 class="mt-10 mb-20 text-2xl font-small text-center">Not found blog!</h2>
</c:if>
<c:if test="${page.size>0}">
    <%@include file="/views/component/pagination.jsp" %>
</c:if>

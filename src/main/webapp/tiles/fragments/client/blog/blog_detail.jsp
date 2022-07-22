<%-- 
    Document   : blog_detail
    Created on : Jun 11, 2022, 9:30:01 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<head>
    <title>${post.title}</title>
</head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/richtexteditor/runtime/richtexteditor_preview.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/richtexteditor/rte_theme_default.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/richtexteditor/rte.js"></script>
<script>RTE_DefaultConfig.url_base = '${pageContext.request.contextPath}/assets/richtexteditor/richtexteditor'</script>
<script type="text/javascript" src='${pageContext.request.contextPath}/assets/richtexteditor/plugins/all_plugins.js'></script>
<div class="container justify-items-center content-center mx-auto mt-10">
    <div class="grid grid-cols-12 gap-4 mb-6">
        <div class="col-span-9">
            <div class="col-span-9">
                <nav class="px-3 py-10 bg-blue-100 text-center border border-gray-200">
                    <header>
                        <h1 class="text-2xl font-small">${post.title}</h1>
                        <p class="mt-2 font-medium" style="font-weight: 200!important; font-size: 18px!important">${post.user.first_name} ${post.user.last_name}</p>
                        <p class="mt-1">
                            <time class="text-md font-small"><fmt:formatDate pattern="MMM dd, yyyy" value="${post.updated_at}"/></time>
                        </p>
                    </header>
                </nav>
            </div>
            <div class="mt-5">
                <div class="p-2">
                    <div id="preview">
                        ${post.content}
                    </div>
                </div>
            </div>
        </div>
        <div class="col-span-3 ">
            <div class="mb-4">
                <form class="flex items-center" action="./">   
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
            <h1 class=" font-bold" style="font-size: 18px;">Featured</h1>
            <c:forEach items="${featureBlogs}" var="blog">
                <div class="border-t-2 pt-4 border-blue-700 mb-3">
                    <a href="${pageContext.request.contextPath}/blog/${blog.id}">
                        <h5 class="mb-1 font-medium tracking-tight text-gray-900 "
                            style="font-size: 18px ;max-width: 400px; min-width: 280px; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical;">
                            ${blog.title}
                        </h5>
                    </a>
                    <p class="mb-3 font-normal text-gray-700" style="max-width: 400px; min-width: 280px; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 5; -webkit-box-orient: vertical;">
                        ${blog.description}
                    </p>
                </div>
            </c:forEach>
        </div>
    </div>
</div>

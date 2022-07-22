<%-- 
    Document   : post_edit
    Created on : May 31, 2022, 11:06:38 AM
    Author     : giaki
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/richtexteditor/rte_theme_default.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/richtexteditor/rte.js"></script>
<script>RTE_DefaultConfig.url_base = '${pageContext.request.contextPath}/assets/richtexteditor/richtexteditor'</script>
<script type="text/javascript" src='${pageContext.request.contextPath}/assets/richtexteditor/plugins/all_plugins.js'></script>
<div class="mb-3 flex justify-between items-center">
    <h1 class="text-xl font-medium">Create post</h1>
</div>
<div class="container mx-auto p-5">
    <form class="mt-5" method="POST" enctype="multipart/form-data" id="form-post">
        <c:if test="${error!=null}">
            <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                <span class="font-medium">Error!</span> ${error}
            </div>
        </c:if>
        <div class="flex justify-between mb-5">
            <a href="${backlink}" class="text-white bg-gray-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Back</a>
            <c:if test="${(sessionScope.user.checkFeature('POST_CREATE') || sessionScope.user.is_super())}">
                <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Create</button>
            </c:if>
        </div>
        <div class="grid grid-cols-1 2xl:grid-cols-2 gap-10">
            <div>
                <div class="mb-6">
                    <label for="featured" class="inline-flex relative items-center cursor-pointer">
                        <input type="checkbox" name="featured" ${featured ? "checked" : ""} value="true" id="featured"  class="sr-only peer">
                        <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                        <span class="ml-3 text-sm font-medium text-gray-900">featured</span>
                    </label>
                </div>
                <div class="mb-6">
                    <label for="title" class="block mb-2 text-sm font-medium text-gray-900">Title</label>
                    <input type="text" id="title" name="title" value="${title}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"  >
                </div>
                <div class="mb-6">
                    <div>
                        <label for="type" class="block mb-2 text-sm font-medium text-gray-900">Category</label>
                    </div>
                    <select id="category" name="category"
                            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"  >
                        <c:forEach items="${categories}" var="item">
                            <option value="${item.id}" ${category==item.id ? "selected": ""}>${item.value}</option>
                            <c:forEach items="${item.childs}" var="child">
                                <option value="${child.id}" ${category==child.id ? "selected": ""}>
                                    + ${child.value}
                                </option>
                            </c:forEach>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-6">
                    <div class="flex justify-center items-center w-full">
                        <label for="thumbnail" class="flex flex-col justify-center items-center w-full h-64 bg-gray-50 rounded-lg border-2 border-gray-300 border-dashed cursor-pointer hover:bg-gray-100">
                            <div class="flex flex-col justify-center items-center pt-5 pb-6">
                                <div class="max-w-[400px] max-h-[240px]">
                                    <img id="image-preview" class="w-full h-full"/>
                                </div>
                                <div id="icon-upload" class="flex items-center flex-col justify-center">
                                    <svg class="mb-3 w-10 h-10 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path></svg>
                                    <p class="mb-2 text-sm text-gray-500 "><span class="font-semibold">Click to upload</span> or drag and drop</p>
                                </div>
                            </div>
                            <input id="thumbnail" name="thumbnail" type="file" accept="image/*" class="hidden" />
                        </label>
                    </div> 
                </div>
                <div class="flex items-center space-x-8">
                    <div class="mb-6">
                        <label for="status" class="inline-flex relative items-center cursor-pointer">
                            <input type="checkbox" name="status" ${status=='show' ? "checked" : ""} value="show" id="status"  class="sr-only peer">
                            <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                            <span class="ml-3 text-sm font-medium text-gray-900">Show</span>
                        </label>
                    </div>
                    <div class="mb-6">
                        <label for="flag" class="inline-flex relative items-center cursor-pointer">
                            <input type="checkbox" name="flag" ${flag ? "checked" : ""} value="true" id="flag"  class="sr-only peer">
                            <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                            <span class="ml-3 text-sm font-medium text-gray-900">Flag</span>
                        </label>
                    </div>
                </div>
            </div>
            <div>
                <div class="mb-6 mt-6">
                    <label for="description" class="block mb-2 text-sm font-medium text-gray-900">Description</label>
                    <textarea id="description" name="description" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Description...">${description}</textarea>
                </div>
                <div>
                    <label for="content" class="block mb-2 text-sm font-medium text-gray-900">Content</label>
                    <textarea  id="content" name="content" class="hidden bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                        ${content}
                    </textarea>
                </div>
            </div>
        </div>
    </form>
</div>
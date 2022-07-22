<%-- 
    Document   : post_edit
    Created on : May 31, 2022, 11:06:38 AM
    Author     : giaki
--%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/richtexteditor/runtime/richtexteditor_preview.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/richtexteditor/rte_theme_default.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/richtexteditor/rte.js"></script>
<script>RTE_DefaultConfig.url_base = '${pageContext.request.contextPath}/assets/richtexteditor/richtexteditor'</script>
<script type="text/javascript" src='${pageContext.request.contextPath}/assets/richtexteditor/plugins/all_plugins.js'></script>
<script>const postId = ${post.id}</script>
<div class="mb-3 flex justify-between items-center">
    <h1 class="text-xl font-medium">Edit post</h1>
</div>
<div class="container mx-auto p-5">
    <form class="mt-5" method="POST" enctype="multipart/form-data" id="form-post">
        <input type="hidden" name="id" value="${post.id}"/>
        <c:if test="${message!=null}">
            <div class="p-4 mb-4 text-sm ${message.code == "success" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"} rounded-lg" role="alert">
                <span class="font-medium">${message.code == "success" ? "Success" : "Error"}!</span> ${message.message}
            </div>
        </c:if>
        <c:if test="${error!=null}">
            <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                <span class="font-medium">Error!</span> ${error}
            </div>
        </c:if>
        <div class="flex justify-between mb-5">
            <a href="${backlink}" class="text-white bg-gray-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Back</a>
            <c:if test="${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super())}">
                <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Save</button>
            </c:if>
        </div>
        <div class="grid grid-cols-1 2xl:grid-cols-2 space-x-10">
            <div>
                <div class="mb-6">
                    <label for="featured" class="inline-flex relative items-center cursor-pointer">
                        <input type="checkbox" name="featured" ${post.featured ? "checked" : ""} value="true" id="featured" ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="sr-only peer">
                        <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                        <span class="ml-3 text-sm font-medium text-gray-900">featured</span>
                    </label>
                </div>
                <div class="mb-6">
                    <label for="title" class="block mb-2 text-sm font-medium text-gray-900">Title</label>
                    <input type="text" id="title" name="title" value="${post.title}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"  ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"}>
                </div>
                <div class="mb-6">
                    <div>
                        <label for="type" class="block mb-2 text-sm font-medium text-gray-900">Category</label>
                    </div>
                    <select id="category" name="category"
                            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"  ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"}>
                        <c:forEach items="${categories}" var="item">
                            <option value="${item.id}" ${post.category.id==item.id ? "selected": ""}>${item.value}</option>
                            <c:forEach items="${item.childs}" var="child">
                                <option value="${child.id}" ${post.category.id==child.id ? "selected": ""}>
                                    + ${child.value}
                                </option>
                            </c:forEach> 
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3 flex justify-center">
                    <c:choose>
                        <c:when test="${fn:containsIgnoreCase(post.thumbnail, 'dummyimage.com')}">
                            <img class="max-w-[100%] max-h-[500px]" src="${post.thumbnail}" id="image-preview"/>
                        </c:when>
                        <c:otherwise>
                            <img class="max-w-[100%] max-h-[500px]" src="${pageContext.request.contextPath}/images/post/${post.thumbnail}" id="image-preview"/>
                        </c:otherwise>
                    </c:choose>     
                </div>
                <div class="mb-6">
                    <label for="thumbnail" class="block mb-2 text-sm font-medium text-gray-900">Upload thumbnail</label>
                    <input name="thumbnail" class="block w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 cursor-pointer" id="thumbnail" type="file"  accept="image/*" ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"}>
                </div>
                <div class="flex items-center space-x-8">
                    <div class="mb-6">
                        <label for="status" class="inline-flex relative items-center cursor-pointer">
                            <input type="checkbox" name="status" ${post.status ? "checked" : ""} value="show" id="status" ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="sr-only peer">
                            <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                            <span class="ml-3 text-sm font-medium text-gray-900">Show</span>
                        </label>
                    </div>
                    <div class="mb-6">
                        <label for="flag" class="inline-flex relative items-center cursor-pointer">
                            <input type="checkbox" name="flag" ${post.flag ? "checked" : ""} value="true" id="flag" ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="sr-only peer">
                            <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                            <span class="ml-3 text-sm font-medium text-gray-900">Flag</span>
                        </label>
                    </div>
                </div>
            </div>
            <div>
                <div class="mb-6 mt-6">
                    <label for="description" class="block mb-2 text-sm font-medium text-gray-900">Description</label>
                    <textarea id="description" ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} name="description" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Description..."> ${post.description}</textarea>
                </div>
                <div>
                    <label for="content" class="block mb-2 text-sm font-medium text-gray-900">Content</label>
                    <c:choose>
                        <c:when test="${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super())}">
                            <textarea ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} id="content" name="content" class="hidden bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                ${post.content}
                            </textarea>
                        </c:when>
                        <c:otherwise>
                            <div id="preview">
                                ${post.content}
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </form>
</div>
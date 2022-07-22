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
    <h1 class="text-xl font-medium">${post.title}</h1>
</div>
<div class="container mx-auto p-5">
    <form class="mt-5" method="GET" enctype="multipart/form-data" id="form-post">
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
        <div class="flex justify-end mb-5">
            <div class="flex items-center">
                <c:if test="${sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()}">
                    <a href="${pageContext.request.contextPath}/admin/post/edit/${post.id}" class="font-medium text-blue-600 hover:underline">Edit</a>
                </c:if>
                <c:if test="${sessionScope.user.checkFeature('POST_DELETE') || sessionScope.user.is_super()}">
                    <span class="font-medium mx-1">/</span>
                    <button type="button" onclick="$('#button-confirm-delete').attr('href', '${pageContext.request.contextPath}/admin/post/delete/${post.id}')"  class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete">Delete</button>
                </c:if>
            </div>
        </div>
        <div class="grid grid-cols-1 2xl:grid-cols-2 space-x-10">
            <div>
                <div class="mb-6">
                    <label for="featured" class="inline-flex relative items-center cursor-pointer">
                        <input disabled type="checkbox" name="featured" ${post.featured ? "checked" : ""} value="true" id="featured" ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="sr-only peer">
                        <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                        <span class="ml-3 text-sm font-medium text-gray-900">featured</span>
                    </label>
                </div>
                <div class="mb-6">
                    <label for="title" class="block mb-2 text-sm font-medium text-gray-900">Title</label>
                    <input disabled type="text" id="title" name="title" value="${post.title}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"  ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"}>
                </div>
                <div class="mb-6">
                    <div>
                        <label for="type" class="block mb-2 text-sm font-medium text-gray-900">Category</label>
                    </div>
                    <select disabled id="category" name="category"
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
                <div class="flex items-center space-x-8">
                    <div class="mb-6">
                        <label for="status" class="inline-flex relative items-center cursor-pointer">
                            <input disabled type="checkbox" name="status" ${post.status ? "checked" : ""} value="show" id="status" ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="sr-only peer">
                            <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                            <span class="ml-3 text-sm font-medium text-gray-900">Show</span>
                        </label>
                    </div>
                    <div class="mb-6">
                        <label for="flag" class="inline-flex relative items-center cursor-pointer">
                            <input disabled type="checkbox" name="flag" ${post.flag ? "checked" : ""} value="true" id="flag" ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="sr-only peer">
                            <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                            <span class="ml-3 text-sm font-medium text-gray-900">Flag</span>
                        </label>
                    </div>
                </div>
            </div>
            <div>
                <div class="mb-6 mt-6">
                    <label for="description" class="block mb-2 text-sm font-medium text-gray-900">Description</label>
                    <textarea disabled id="description" ${(sessionScope.user.checkFeature('POST_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} name="description" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Description..."> ${post.description}</textarea>
                </div>
                <div>
                    <label for="content" class="block mb-2 text-sm font-medium text-gray-900">Content</label>
                    <div id="preview">
                        ${post.content}
                    </div>
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
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this post?</h3>
                <a id="button-confirm-delete" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>
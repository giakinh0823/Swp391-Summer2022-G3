<%-- 
    Document   : subject_detail
    Created on : May 28, 2022, 12:12:12 AM
    Author     : giaki
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="flex space-x-2 items-center mb-5">
    <a href="${backlink}"><svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M9.707 16.707a1 1 0 01-1.414 0l-6-6a1 1 0 010-1.414l6-6a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l4.293 4.293a1 1 0 010 1.414z" clip-rule="evenodd"></path></svg></a>
    <h1 class="text-2xl font-medium ml-3">${subject.name}</h1>
</div>
<%@include file="/tiles/fragments/admin/subject/tabbar.jsp" %>
<div class="container mx-auto">
    <form method="POST" enctype="multipart/form-data">
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
        <div class="grid grid-cols-1 lg:grid-cols-2 space-2">
            <div class="mb-6 p-5">
                <div class="mb-6">
                    <label for="name" class="block mb-2 text-sm font-medium text-gray-900">Subject name</label>
                    <input ${(sessionScope.user.checkFeature('SUBJECT_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} type="text" id="name" name="name" value="${subject.name}" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Python programing">
                </div> 
                <div class="mb-6">
                    <label for="category" class="block mb-2 text-sm font-medium text-gray-900">Category</label>
                    <select ${(sessionScope.user.checkFeature('SUBJECT_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} id="category" name="category" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}" ${subject.category.id==category.id ? "selected": ""} >${category.value}</option>
                            <c:forEach items="${category.childs}" var="child">
                                <option value="${child.id}" ${subject.category.id==child.id ? "selected": ""}> + ${child.value}</option>
                            </c:forEach>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-6 flex items-center justify-between">
                    <div>
                        <label for="featured" class="inline-flex relative items-center cursor-pointer">
                            <input ${(sessionScope.user.checkFeature('SUBJECT_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} type="checkbox" name="featured" ${subject.featured ? "checked" : ""}  value="true" id="featured" class="sr-only peer">
                                <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                                <span class="ml-3 text-sm font-medium text-gray-900">Featured</span>
                        </label>
                    </div>
                    <div>
                        <label for="status" class="block mb-2 text-sm font-medium text-gray-900">Status</label>
                        <select ${(sessionScope.user.checkFeature('SUBJECT_CHANGE_STATUS') || sessionScope.user.is_super()) ? "":"disabled"} id="status" name="status" class="w-[200px] bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                            <option value="published" ${subject.status ? "selected": ""}>Published</option>
                            <option value="unpublished" ${subject.status ? "": "selected"}>Unpublished</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="p-5">
                <div class="w-full flex justify-center">
                    <c:choose>
                        <c:when test="${fn:containsIgnoreCase(subject.image, 'dummyimage.com')}">
                            <img class="max-w-full max-w-[300px]" alt="${subject.name}" src="${subject.image}" id="image-preview"/>
                        </c:when>
                        <c:otherwise>
                            <img class="max-w-full max-w-[300px]" alt="${subject.name}" src="${pageContext.request.contextPath}/images/subject/${subject.image}" id="image-preview"/>
                        </c:otherwise>
                    </c:choose>     
                </div>
                <c:if test="${(sessionScope.user.checkFeature('SUBJECT_EDIT') || sessionScope.user.is_super())}">
                    <div>
                        <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300" for="image">Upload image</label>
                        <input  name="image" accept="image/*" class="block w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 cursor-pointer focus:outline-none" id="image" type="file">
                    </div>
                </c:if>
            </div>
        </div>
        <div class="mb-6">
            <label for="description" class="block mb-2 text-sm font-medium text-gray-900">Description</label>
            <textarea ${(sessionScope.user.checkFeature('SUBJECT_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} id="description" name="description" rows="6" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Your description...">${subject.description}</textarea>
        </div>
        <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 focus:outline-none ">Save</button>
    </form>
</div>
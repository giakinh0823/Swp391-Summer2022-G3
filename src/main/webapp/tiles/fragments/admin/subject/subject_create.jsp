<%-- 
    Document   : subject_create
    Created on : Jun 7, 2022, 1:02:22 PM
    Author     : lanh0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<h1 class="text-lg mb-3 font-medium">New subject</h1>
<div class="container mx-auto">
    <form class="mt-20" method="POST" enctype="multipart/form-data">
        <c:if test="${error!=null}">
            <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                <span class="font-medium">Error!</span> ${error}
            </div>
        </c:if>
        <div class="grid grid-cols-1 lg:grid-cols-2 space-2">
            <div class="mb-6 p-5">
                <div class="mb-6">
                    <label for="name" class="block mb-2 text-sm font-medium text-gray-900">Subject name</label>
                    <input ${(sessionScope.user.checkFeature('SUBJECT_CREATE') || sessionScope.user.is_super()) ? "":"disabled"} type="text" id="name" name="name" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="Python programing">
                </div>
                <div class="mb-6">
                    <label for="category" class="block mb-2 text-sm font-medium text-gray-900">Category</label>
                    <select ${(sessionScope.user.checkFeature('SUBJECT_CREATE') || sessionScope.user.is_super()) ? "":"disabled"} id="category" name="category" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}" >${category.value}</option>
                            <c:forEach items="${category.childs}" var="child">
                                <option value="${child.id}" > + ${child.value}</option>
                            </c:forEach>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-6 flex items-center justify-between"> 
                    <div>
                        <label for="featured" class="inline-flex relative items-center cursor-pointer">
                            <input ${(sessionScope.user.checkFeature('SUBJECT_CREATE') || sessionScope.user.is_super()) ? "":"disabled"} type="checkbox" name="featured" value="true" id="featured" class="sr-only peer">
                            <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                            <span class="ml-3 text-sm font-medium text-gray-900">Featured</span>
                        </label>
                    </div>
                </div>
            </div>
            <div class="p-5">
                <div class="flex justify-center items-center w-full">
                    <label for="image" class="flex flex-col justify-center items-center w-full h-64 bg-gray-50 rounded-lg border-2 border-gray-300 border-dashed cursor-pointer hover:bg-gray-100">
                        <div class="flex flex-col justify-center items-center pt-5 pb-6">
                            <div class="max-w-[400px] max-h-[240px]">
                                <img id="image-preview" class="w-full h-full"/>
                            </div>
                            <div id="icon-upload" class="flex items-center flex-col justify-center">
                                <svg class="mb-3 w-10 h-10 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path></svg>
                                <p class="mb-2 text-sm text-gray-500 "><span class="font-semibold">Click to upload</span> or drag and drop</p>
                            </div>
                        </div>
                        <input id="image" name="image" type="file" accept="image/*" class="hidden" />
                    </label>
                </div> 
            </div>
        </div>
        <div class="mb-6">
            <label for="description" class="block mb-2 text-sm font-medium text-gray-900">Description</label>
            <textarea ${(sessionScope.user.checkFeature('SUBJECT_CREATE') || sessionScope.user.is_super()) ? "":"disabled"} id="description" name="description" rows="6" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Your description..."></textarea>
        </div>
        <div class="flex justify-end">
            <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 focus:outline-none ">Create</button>
        </div>
    </form>
</div>
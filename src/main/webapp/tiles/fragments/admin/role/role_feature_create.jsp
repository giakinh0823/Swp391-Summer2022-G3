<%-- 
    Document   : role_feature_create
    Created on : May 24, 2022, 2:18:01 PM
    Author     : giaki
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="mb-3 flex justify-between items-center">
    <h1 class="text-xl font-medium">Create Feature</h1>
</div>
<form class="max-w-[400px] mx-auto mt-10" method="POST">
    <c:if test="${error!=null}">
        <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
            <span class="font-medium">Error!</span> ${error}
        </div>
    </c:if>
    <div class="mb-6">
        <label for="name" class="block mb-2 text-sm font-medium text-gray-900">Name</label>
        <input type="text" id="name" name="name" value="${name}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="mb-6">
        <label for="feature" class="block mb-2 text-sm font-medium text-gray-900">Feature</label>
        <input type="text" id="feature" name="feature" value="${feature}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Create</button>
</form>


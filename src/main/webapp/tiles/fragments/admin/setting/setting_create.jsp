<%-- 
    Document   : setting_create
    Created on : May 29, 2022, 10:30:51 PM
    Author     : Administrator
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="mb-3 flex justify-between items-center">
    <h1 class="text-xl font-medium">Create Setting</h1>
</div>
<form class="max-w-[400px] mx-auto mt-10" method="POST">
    <c:if test="${error!=null}">
        <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
            <span class="font-medium">Error!</span> ${error}
        </div>
    </c:if>
    <div class="mb-6">
        <div>
            <label for="type" class="block mb-2 text-sm font-medium text-gray-900">Type</label>
        </div>
        <select onchange="showParen(this, '${pageContext.request.contextPath}/admin/setting/no-parent')"  id="type" name="type"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
            <c:forEach items="${types}" var="type">
                <option value="${type.code}">${type.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="mb-6 hidden" id="parent-field">
        <div>
            <label for="parent" class="block mb-2 text-sm font-medium text-gray-900">Parent</label>
        </div>
        <select id="parent" name="parent"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
        </select>
    </div>
    <div class="mb-6">
        <label for="value" class="block mb-2 text-sm font-medium text-gray-900">Value</label>
        <input type="text" id="value" name="value" value="" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>

    <div class="mb-6">
        <label for="order" class="block mb-2 text-sm font-medium text-gray-900">Order</label>
        <input type="number" min="1" id="order" name="order" value="" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>

    <div class="mb-6">
        <div>
            <label for="status" class="block mb-2 text-sm font-medium text-gray-900">Status</label>
        </div>
        <div class="flex items-center">
            <div class="flex items-center mr-4">
                <input id="status_true" checked type="radio" value="status_true" name="status" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500">
                <label for="status_true" class="ml-2 text-sm font-medium text-gray-900">True</label>
            </div>
            <div class="flex items-center mr-4">
                <input id="status_false" type="radio" value="status_false" name="status" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500">
                <label for="status_false" class="ml-2 text-sm font-medium text-gray-900">False</label>
            </div>
        </div>
    </div>
    <div class="mb-6 mt-6">
        <label for="description" class="block mb-2 text-sm font-medium text-gray-900">Description</label>
        <textarea id="description" name="description" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Description..."></textarea>
    </div>

    <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Create</button>
</form>




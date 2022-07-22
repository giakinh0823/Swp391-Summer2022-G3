<%-- 
    Document   : role_edit.jsp
    Created on : May 25, 2022, 1:26:10 PM
    Author     : giaki
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="text-xl font-medium mb-3">Manage role feature</h1>
<form class="max-w-[450px] mx-auto mt-10" method="POST">
    <div id="alert-message" class="${message!=null ? "": "hidden"} p-4 mb-4 text-sm ${message.code == "success" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"} rounded-lg" role="alert">
        <span class="font-medium" id="alert-message-code">${message.code == "success" ? "Success" : "Error"}!</span> <span id="alert-message-span">${message.message}</span>
    </div>
    <div class="mb-6">
        <label class="block mb-2 text-sm font-medium text-gray-900">Group name</label>
        <input type="text" value="${group.value}" disabled class="disabled bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="grid grid-cols-3 space-2 mb-3">
        <div class="flex items-center mb-2">
            <input id="checked-checkbox-all" onchange="selectAllFeature(this)" type="checkbox" class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
            <label for="checked-checkbox-all" class="ml-2 text-sm font-medium text-gray-900">Select all</label>
        </div>
        <c:forEach items="${features}" var="feature">
            <div class="flex items-center mb-2">
                <input ${(sessionScope.user.checkFeature('ROLE_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} ${group.checkFeature(feature.id)?"checked":""} onchange="checkboxFeatureChange()" id="checked-checkbox-${feature.id}" type="checkbox" value="${feature.id}" name="features" class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                <label for="checked-checkbox-${feature.id}" class="ml-2 text-sm font-medium text-gray-900">${feature.name}</label>
            </div>
        </c:forEach>
    </div>
    <c:if test="${(sessionScope.user.checkFeature('ROLE_EDIT') || sessionScope.user.is_super())}">
        <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Save</button>
    </c:if>
</form>
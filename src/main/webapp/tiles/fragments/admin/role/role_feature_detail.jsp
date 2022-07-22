<%-- 
    Document   : role_feature_detail
    Created on : May 24, 2022, 6:59:52 PM
    Author     : giaki
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="text-xl font-medium mb-3">Feature Detail</h1>
<form class="max-w-[400px] mx-auto mt-10" method="POST" action="${pageContext.request.contextPath}/admin/role/feature/edit/${feature.id}">
    <c:if test="${message!=null}">
        <div class="p-4 mb-4 text-sm ${message.code == "success" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"} rounded-lg" role="alert">
            <span class="font-medium">${message.code == "success" ? "Success" : "Error"}!</span> ${message.message}
        </div>
    </c:if>
    <input type="hidden" name="id" value="${feature.id}"/>
    <div class="mb-6">
        <label for="name" class="block mb-2 text-sm font-medium text-gray-900">Name</label>
        <input ${(sessionScope.user.checkFeature('FEATURE_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} type="text" id="name" name="name" value="${feature.name}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="mb-6">
        <label for="feature" class="block mb-2 text-sm font-medium text-gray-900">Feature</label>
        <input ${(sessionScope.user.checkFeature('FEATURE_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} type="text" id="feature" name="feature" value="${feature.feature}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <c:if test="${(sessionScope.user.checkFeature('FEATURE_EDIT') || sessionScope.user.is_super())}">
        <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Save</button>
    </c:if>
</form>

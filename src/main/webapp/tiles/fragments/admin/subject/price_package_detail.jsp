<%-- 
    Document   : price_package_create
    Created on : May 28, 2022, 11:22:21 AM
    Author     : giaki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1 class="text-2xl mb-5 font-medium">${packagePrice.subject.name}</h1>
<%@include file="/tiles/fragments/admin/subject/tabbar.jsp" %>
<div>
    <a href="${pageContext.request.contextPath}/admin/subject/${packagePrice.subject.id}/price-package" class="py-2 px-5 mr-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 :focus:ring-gray-700">Back</a>
</div>
<div class="container mx-auto mt-20">
    <form class="max-w-[450px] mx-auto" method="POST">
        <c:if test="${error!=null}">
            <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                <span class="font-medium">Error!</span> ${error}
            </div>
        </c:if>
        <div id="alert-message" class="${message!=null ? "": "hidden"} p-4 mb-4 text-sm ${message.code == "success" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"} rounded-lg" role="alert">
            <span class="font-medium" id="alert-message-code">${message.code == "success" ? "Success" : "Error"}!</span> <span id="alert-message-span">${message.message}</span>
        </div>
        <div class="mb-6">
            <label for="name" class="block mb-2 text-sm font-medium text-gray-900">Name</label>
            <input type="text" ${(sessionScope.user.checkFeature('PRICEPACKAGE_EDIT') || sessionScope.user.is_super()) ? "":"disabled"}  id="name" name="name" value="${packagePrice.name}" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
        </div> 
        <div class="mb-6">
            <label for="duration" class="block mb-2 text-sm font-medium text-gray-900">Duration (Months)</label>
            <input type="number" ${(sessionScope.user.checkFeature('PRICEPACKAGE_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} id="duration" name="duration" value="${packagePrice.duration}" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
        </div> 
        <div class="mb-6">
            <label for="list_price" class="block mb-2 text-sm font-medium text-gray-900">List price</label>
            <input type="number" ${(sessionScope.user.checkFeature('PRICEPACKAGE_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} id="list_price" value="${packagePrice.list_price}" step="0.1" name="list_price" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
        </div> 
        <div class="mb-6">
            <label for="sale_price" class="block mb-2 text-sm font-medium text-gray-900">Sale price</label>
            <input type="number" ${(sessionScope.user.checkFeature('PRICEPACKAGE_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} id="sale_price" value="${packagePrice.sale_price}" step="0.1" name="sale_price" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
        </div> 
        <div class="mb-6">
            <label for="status" class="inline-flex relative items-center cursor-pointer">
                <input type="checkbox" ${(sessionScope.user.checkFeature('PRICEPACKAGE_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} ${packagePrice.status ? "checked" : ""} value="active" name="status" id="status" class="sr-only peer">
                    <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                    <span class="ml-3 text-sm font-medium text-gray-900">Active</span>
            </label>
        </div>
        <div class="mb-6">
            <label for="description" class="block mb-2 text-sm font-medium text-gray-900">Description</label>
            <textarea id="description" name="description" rows="6" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Your description..." ${(sessionScope.user.checkFeature('PRICEPACKAGE_EDIT') || sessionScope.user.is_super()) ? "":"disabled"}>${packagePrice.description}</textarea>
        </div>
        <c:if test="${(sessionScope.user.checkFeature('PRICEPACKAGE_EDIT') || sessionScope.user.is_super())}">
            <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 focus:outline-none ">Save</button>
        </c:if>
    </form>
</div>

<%-- 
    Document   : dimension_detail
    Created on : Jun 22, 2022, 5:29:34 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="text-2xl mb-5 font-medium">${subject.name}</h1>
<%@include file="/tiles/fragments/admin/subject/tabbar.jsp" %>
<div>
    <a href="${pageContext.request.contextPath}/admin/subject/${subject.id}/dimension" class="py-2 px-5 mr-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 :focus:ring-gray-700">Back</a>
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
            <label for="id" class="block mb-2 text-sm font-medium text-gray-900">Id</label>
            <input disabled type="text" id="id" name="id" value="${dimension.id}" ${sessionScope.user.checkFeature('DIMENSION_EDIT') || sessionScope.user.is_super() ? "": "disabled"} class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
        </div>
        <div class="mb-6">
            <label for="name" class="block mb-2 text-sm font-medium text-gray-900">Name</label>
            <input disabled type="text" id="name" name="name" value="${dimension.name}" ${sessionScope.user.checkFeature('DIMENSION_EDIT') || sessionScope.user.is_super() ? "": "disabled"} class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
        </div>
        <div class="mb-6">
            <label for="type" class="block mb-2 text-sm font-medium text-gray-900">Type</label>
            <select disabled id="type" name="type" ${sessionScope.user.checkFeature('DIMENSION_EDIT') || sessionScope.user.is_super() ? "": "disabled"} class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                <c:forEach items="${types}" var="item">
                    <option value="${item.id}" ${dimension.type.id==item.id ? "selected": ""}>${item.value}</option>
                </c:forEach>
            </select>
        </div>
        <div class="mb-6">
            <label for="description" class="block mb-2 text-sm font-medium text-gray-900">Description</label>
            <textarea disabled id="description" name="description" rows="6" ${sessionScope.user.checkFeature('DIMENSION_EDIT') || sessionScope.user.is_super() ? "": "disabled"} class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Your description...">${dimension.description}</textarea>
        </div>
    </form>
</div>




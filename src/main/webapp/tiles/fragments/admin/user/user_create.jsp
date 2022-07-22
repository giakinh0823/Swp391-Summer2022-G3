<%-- 
    Document   : user_edit
    Created on : May 30, 2022, 3:15:54 AM
    Author     : Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="text-xl font-medium mb-3">Create user</h1>
<form class="max-w-[400px] mx-auto mb-5 py-10" method="POST" enctype="multipart/form-data">
    <c:if test="${error!=null}">
        <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
            <span class="font-medium">Error!</span> ${error}
        </div>
    </c:if>
    <div class="flex justify-end">
        <div class="mb-2">
            <label for="status" class="inline-flex relative items-center cursor-pointer">
                <input type="checkbox"  ${is_active=='true' ? "checked" : ""} ${(sessionScope.user.checkFeature('USER_CHANGE_STATUS') || sessionScope.user.is_super()) ? "":"disabled"} name="status" value="active" id="status" class="sr-only peer">
                <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                <span class="ml-3 text-sm font-medium text-gray-900">Active</span>
            </label>
        </div>
    </div>
    <div class="mb-6">
        <label for="username" class="block mb-2 text-sm font-medium text-gray-900">Username</label>
        <input type="text" id="username" name="username" value="${username}"class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="mb-6">
        <label for="username" class="block mb-2 text-sm font-medium text-gray-900">Email</label>
        <input type="email" id="email" name="email" value="${email}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="mb-6">
        <label for="first_name" class="block mb-2 text-sm font-medium text-gray-900">First name</label>
        <input  type="text" id="first_name" name="first_name" value="${first_name}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="mb-6">
        <label for="last_name" class="block mb-2 text-sm font-medium text-gray-900">Last name</label>
        <input  type="text" id="last_name" name="last_name" value="${last_name}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="mb-6 flex items-center">
        <div class="flex items-center">
            <input ${gender ? "checked" : "" }  id="male" name="gender" type="radio" value="male" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 focus:ring-2">
            <label class="ml-2 text-sm font-medium text-gray-900">Male</label>
        </div>
        <div class="flex items-center ml-3">
            <input ${gender ? "" : "checked" }  id="female" name="gender" type="radio" value="female" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 focus:ring-2">
            <label  for="female" class="ml-2 text-sm font-medium text-gray-900">Female</label>
        </div>
    </div>
    <div class="mb-6">
        <label for="phone" class="block mb-2 text-sm font-medium text-gray-900">Phone</label>
        <input  type="text" id="phone" name="phone" value="${phone}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="mb-3">
        <img class="max-w-[100%] max-h-[400px]" id="avatar-preview"/> 
    </div>
    <div class="mb-6">
        <label for="avatar" class="block mb-2 text-sm font-medium text-gray-900">Upload image</label>
        <input name="avatar" class="block w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 cursor-pointer" id="avatar" type="file" accept="image/*">
    </div>
    <div class="mb-6 flex items-center">
        <c:if test="${sessionScope.user.is_super()}">
            <div class="flex items-center mr-3">
                <input  id="super" name="super" type="checkbox" value="super"  class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 focus:ring-2">
                <label for="super" class="ml-2 text-sm font-medium text-gray-900">Is super</label>
            </div>
        </c:if>
        <div class="flex items-center ">
            <input   id="staff" name="staff" type="checkbox" value="staff" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 focus:ring-2">
            <label  for="staff" class="ml-2 text-sm font-medium text-gray-900">Is staff</label>
        </div>
    </div>
    <div class="mb-6">
        <label for="roles" class="block mb-2 text-sm font-medium text-gray-900">Roles</label>
        <select multiple id="roles" name="roles" ${(sessionScope.user.checkFeature('USER_EDIT_ROLE') || sessionScope.user.is_super()) ? "":"disabled"} class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
            <c:forEach items="${roles}" var="item"> 
                <option value="${item.id}">${item.value}</option>
            </c:forEach>
        </select>
    </div>
    <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Create</button>

</form>


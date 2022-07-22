<%-- 
    Document   : new_password
    Created on : May 31, 2022, 12:04:04 AM
    Author     : lanh0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container mx-auto flex justify-center items-center">
    <form class="min-w-[400px] mt-20" method="POST">
        <div class="mb-10">
            <h1 class="text-center text-2xl mb-2">Reset password</h1>
        </div>
        <c:if test="${error!=null}">
            <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                <span class="font-medium">Error!</span> ${error}
            </div>
        </c:if>
        <div class="mb-6">
            <label for="password" class="block mb-2 text-sm font-medium text-gray-900">Password</label>
            <input type="password" id="password" name="password"
                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                   placeholder="" required>
        </div>
        <div class="mb-6">
            <label for="confirm-password" class="block mb-2 text-sm font-medium text-gray-900">Confirm Password</label>
            <input type="password" id="confirm-password" name="confirm-password"
                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                   placeholder="" required>
        </div>
        <button type="submit"
                class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full px-5 py-2.5 text-center">Change password</button>
    </form>
</div>
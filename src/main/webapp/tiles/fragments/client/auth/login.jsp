<%-- 
    Document   : login
    Created on : May 23, 2022, 11:14:56 PM
    Author     : giaki
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container mx-auto flex justify-center items-center p-5 mt-10">
    <form class="min-w-[400px]" method="POST">
        <div class="mb-6">
            <h1 class="text-center text-2xl">Hello Again!</h1>
            <h1 class="text-center">Wellcome back you've been missed!</h1>
        </div>
        <c:if test="${error!=null}">
            <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                <span class="font-medium">Error!</span> ${error}
            </div>
        </c:if>
        <div class="mb-6">
            <label for="username" class="block mb-2 text-sm font-medium text-gray-900">Username or email</label>
            <input type="text" id="username"
                   name="username"
                   value="${username}"
                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                   placeholder="email">
        </div>
        <div class="mb-6">
            <label for="password" class="block mb-2 text-sm font-medium text-gray-900">Your password</label>
            <input type="password" id="password"
                   name="password"
                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                   placeholder="password">
        </div>

        <button type="submit"
                class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full px-5 py-2.5 text-center">Sign In</button>
        <div class="container flex mt-3">
            <span class="mx-auto flex justify-between">
                <p>Not a member? <a href="../auth/signup" class="text-blue-500">Sign up</a></p>
            </span>

            <span class="mx-auto flex justify-between">
                <a href="../auth/reset" class="text-blue-500">Forgot password</a>
            </span>
        </div>
    </form>
</div>


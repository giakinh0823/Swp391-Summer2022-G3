<%-- 
    Document   : register
    Created on : May 31, 2022, 9:58:55 PM
    Author     : Administrator
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container flex justify-center items-center mt-20 mx-auto">
    <form class="min-w-[450px] mx-auto mb-5" method="post">
        <div class="mb-6">
            <h1 class="text-left text-2xl font-medium">Sign up</h1>
            <h1 class="text-left mt-2">Create account to start using coursera</h1>
        </div>
        <c:if test="${error!=null}">
            <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                <span class="font-medium">Error!</span> ${error}
            </div>
        </c:if>
        <div class="grid gap-6 mb-6 lg:grid-cols-2">
            <div>
                <label for="first_name"
                       class="block mb-2 text-sm font-medium text-gray-900">First name</label>
                <input type="text" name="first_name"
                       value="${first_name}"
                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                       placeholder="John">
            </div>
            <div>
                <label for="last_name" class="block mb-2 text-sm font-medium text-gray-900">Last
                    name</label>
                <input type="text" name="last_name"
                       value="${last_name}"
                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                       placeholder="Doe">
            </div>
        </div>

        <div class="mb-6">
            <h1 class="block mb-2 text-sm font-medium text-gray-900">Gender</h1> 
            <div class="flex items-center">
                <div class="flex items-center">
                    <input id="male" ${gender=='male' ?"checked": ""} ${gender==null ? "checked": ""} type="radio" value="male" name="gender" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500">
                    <label for="male" class="ml-2 text-sm font-medium text-gray-900">Male</label>
                </div>
                <div class="flex items-center ml-3">
                    <input id="female" ${gender=='female' ?"checked": ""}  type="radio" value="female" name="gender" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500">
                    <label for="female" class="ml-2 text-sm font-medium text-gray-900">Female</label>
                </div>
            </div>
        </div>
        <div class="mb-6">
            <label for="phone" class="block mb-2 text-sm font-medium text-gray-900 ">Phone number</label>
            <input type="text" id="phone" name="phone"
                   value="${phone}"
                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                   placeholder="Your phone number">
        </div>

        <div class="mb-6">
            <label for="username" class="block mb-2 text-sm font-medium text-gray-900 ">Username</label>
            <input type="text" id="username" name="username" value="${username}"
                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                   placeholder="" required>
        </div>

        <div class="mb-6">
            <label for="email" class="block mb-2 text-sm font-medium text-gray-900 ">Email</label>
            <input type="email" id="email" name="email"
                   value="${email}"
                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                   placeholder="coursera@gmail.com" >
        </div>
        <div class="mb-6">
            <label for="password" class="block mb-2 text-sm font-medium text-gray-900">Password</label>
            <input type="password" id="password" name="password"
                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                   placeholder="password" >
        </div>
        <div class="mb-6">
            <label for="re-password" class="block mb-2 text-sm font-medium text-gray-900">Re-enter password</label>
            <input type="password" id="re-password" name="re-password"
                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                   placeholder="re-password" >
        </div>
        <button type="submit"
                class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center w-full">Sign up</button>
        <div class="flex justify-center mt-5">
            <label for="terms" class="text-center mx-auto font-medium text-gray-900">Already have an account? 
                <a href="${pageContext.request.contextPath}/auth/login" class="text-blue-600 hover:underline">Login</a>
            </label>
        </div>    
    </form>
</div>
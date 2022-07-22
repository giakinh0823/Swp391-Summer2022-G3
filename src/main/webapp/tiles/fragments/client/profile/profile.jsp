<%-- 
    Document   : profile
    Created on : May 26, 2022, 9:05:33 PM
    Author     : congg
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<div class="container flex mx-auto justify-center ">
    <form class="w-[400px] mt-6">
        <div class="text-2xl mb-6 text-center">Profile & Settings</div>
        <div class="flex justify-center mb-4">
            <c:choose>
                <c:when test="${fn:contains(user.avatar, 'dummyimage.com')}">
                    <img class=" w-24 rounded-full "
                         src="${user.avatar}"
                         alt="Rounded avatar">
                </c:when>
                <c:when test="${user.avatar!=null}">
                    <img class=" w-24 rounded-full "
                         src="${pageContext.request.contextPath}/images/user/${user.avatar}"
                         alt="Rounded avatar">
                </c:when>
                <c:otherwise>
                    <img class=" w-24 rounded-full "
                         src="https://flowbite.com/docs/images/people/profile-picture-3.jpg"
                         alt="Rounded avatar">
                </c:otherwise>
            </c:choose>
        </div>

        <div id="alert-message" class="${message!=null ? "": "hidden"} p-4 mb-4 text-sm ${message.code == "success" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"} rounded-lg" role="alert">
            <span class="font-medium" id="alert-message-code">${message.code == "success" ? "Success" : "Error"}!</span> <span id="alert-message-span">${message.message}</span>
        </div>

        <div class="mb-6">
            <label for="username"
                   class="block mb-2 text-sm font-medium text-gray-900 ">Username</label>
            <input type="text" id="username" disabled name="username"
                   value="${user.username} "
                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                   placeholder="username">
        </div>

        <div class="grid gap-6 mb-6 lg:grid-cols-2">
            <div>
                <label for="first_name"
                       class="block mb-2 text-sm font-medium text-gray-900">First name</label>
                <input type="text" id="first_name" disabled name="firstname"
                       value="${user.first_name}"
                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                       placeholder="John" >
            </div>
            <div>
                <label for="last_name" class="block mb-2 text-sm font-medium text-gray-900 ">Last
                    name</label>
                <input type="text" id="last_name" disabled name ="lastname"
                       value="${user.last_name}"
                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                       placeholder="Doe" >
            </div>
            <div> 
                <label for="phone" class="block mb-2 text-sm font-medium text-gray-900 ">Phone
                    number</label>
                <input type="tel" id="phone" disabled name="phone"
                       value="${user.phone}"
                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                       placeholder="123-45-678" >
            </div>
            <div>
                <label for="gender" class="block mb-2 text-sm font-medium text-gray-900 mb-3">Gender</label>
                <div class="flex items-center">
                    <div class="flex items-center mr-4">
                        <input id="male" type="radio" disabled value="male" ${user.gender ? "checked" : ""} name="gender"
                               class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 ">
                        <label for="male" class="ml-2 text-sm font-medium text-gray-900 ">Male</label>
                    </div>
                    <div class="flex items-center mr-4">
                        <input id="female" type="radio"  disabled value="female" ${user.gender ? "" : "checked"} name="gender"
                               class="disabled w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 ">
                        <label for="female" class="ml-2 text-sm font-medium text-gray-900 ">Female</label>
                    </div>
                </div>
            </div>
        </div>
        <div class="mb-6">
            <label for="email" class="block mb-2 text-sm font-medium text-gray-900">Email</label>
            <input type="email" id="email" name="email" disabled value="${user.email}"
                   class="disabled bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                   placeholder="Email">
        </div>
        <div class="flex"><a href="${pageContext.request.contextPath}/profile/edit"
                             class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full px-5 py-2.5 text-center">Edit profile</a></div>
        <div class="flex justify-center">
            <a href="${pageContext.request.contextPath}/profile/change-password" class="mx-auto text-blue-700 mt-3 hover:cursor-pointer underline underline-offset-1">Change password</a>
        </div>
    </form>
</div>

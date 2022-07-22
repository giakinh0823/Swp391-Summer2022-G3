<%-- 
    Document   : profile
    Created on : May 26, 2022, 9:05:33 PM
    Author     : congg
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<div class="container flex mx-auto justify-center ">
    <form class="w-[400px] mt-6"  method="POST" enctype="multipart/form-data">
        <div class="text-2xl mb-6 text-center">Profile & Settings</div>
        <c:if test="${error!=null}">
            <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                <span class="font-medium">Error!</span> ${error}
            </div>
        </c:if>
        <c:choose>
            <c:when test="${fn:contains(user.avatar, 'dummyimage.com')}">
                <div class="flex justify-center mb-4">
                    <img id="avatar-img" class=" w-24 rounded-full "
                         src="${user.avatar}"
                         alt="Rounded avatar">
                </c:when>
                <c:when test="${user.avatar!=null}">
                    <div class="flex justify-center mb-4">
                        <img id="avatar-img" class=" w-24 rounded-full "
                             src="${pageContext.request.contextPath}/images/user/${user.avatar}"
                             alt="Rounded avatar">
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="flex justify-center mb-4">
                        <img id="avatar-img" class=" w-24 rounded-full "
                             src="https://flowbite.com/docs/images/people/profile-picture-3.jpg"
                             alt="Rounded avatar">
                    </div>
                </c:otherwise>
            </c:choose>
        <div>
            <label class="block mb-2 text-sm font-medium text-gray-900 " for="avatar">Avatar</label>
            <input name="avatar" class="block mb-5 w-full text-xs text-gray-900 bg-gray-50 rounded-lg border border-gray-300 cursor-pointer " id="avatar" type="file" accept="image/*">
        </div>

        <div class="mb-6">
            <label for="username"
                   class="block mb-2 text-sm font-medium text-gray-900 ">Username</label>
            <input type="text" id="username" name="username"
                   value="${user.username}"
                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                   placeholder="username">
        </div>

        <div class="grid gap-6 mb-6 lg:grid-cols-2">
            <div>
                <label for="first_name"
                       class="block mb-2 text-sm font-medium text-gray-900">First name</label>
                <input type="text" id="first_name" name="first_name"
                       value="${user.first_name}"
                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                       placeholder="firstname" >
            </div>
            <div>
                <label for="last_name" class="block mb-2 text-sm font-medium text-gray-900 ">Last
                    name</label>
                <input type="text" id="last_name" name="last_name"
                       value="${user.last_name}"
                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                       placeholder="lastname" >
            </div>
            <div> 
                <label for="phone" class="block mb-2 text-sm font-medium text-gray-900 ">Phone
                    number</label>
                <input type="tel" id="phone" name="phone"
                       value="${user.phone}"
                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                       placeholder="0123456789" >
            </div>
            <div>
                <label for="gender" class="block mb-2 text-sm font-medium text-gray-900 mb-3">Gender</label>
                <div class="flex items-center">
                    <div class="flex items-center mr-4">
                        <input id="male" type="radio" value="male" ${user.gender ? "checked" : ""} name="gender"
                               class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 ">
                        <label for="male" class="ml-2 text-sm font-medium text-gray-900 ">Male</label>
                    </div>
                    <div class="flex items-center mr-4">
                        <input id="female" type="radio" value="female" ${user.gender ? "" : "checked"} name="gender"
                               class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 ">
                        <label for="female" class="ml-2 text-sm font-medium text-gray-900 ">Female</label>
                    </div>
                </div>
            </div>
        </div>
        <div class="mb-6">
            <label for="email" class="block mb-2 text-sm font-medium text-gray-900">Email</label>
            <input type="email" id="email" name="email"
                   value="${user.email}"
                   class="disabled bg-gray-100 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                   placeholder="Email" disabled>
        </div>

        <button type="submit"
                class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full px-5 py-2.5 text-center">Update profile</button>      
    </form>
</div>

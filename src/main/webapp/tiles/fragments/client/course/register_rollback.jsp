<%-- 
    Document   : course_detail
    Created on : Jun 24, 2022, 9:23:29 PM
    Author     : congg
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container flex mx-auto justify-center items-center">
    <form class="mt-6" method="POST" id="form-checkout">
        <input type="hidden" name="registerid" value="${register.id}"/>
        <div class="mb-10 mt-10">
            <h1 class="text-center text-4xl">Checkout</h1>
        </div>
        <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg hidden" role="alert" id="error-checkout">
            <span class="font-medium">Error!</span> <span id="error-message-checkout"></span>
        </div>
        <div class="grid gap-6 mb-6 lg:grid-cols-2 h-full ">
            <div class="col-span-1">
                <div class="grid gap-6 lg:grid-cols-2">
                    <div class="${user!=null ? "hidden": ""}">
                        <label for="first_name" class="block mb-2 text-sm font-medium text-gray-900">First
                            name</label>
                        <input type="text" id="first_name" name="first_name" value="${user.first_name}"
                               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                               placeholder="firstname">
                    </div>
                    <div class="${user!=null ? "hidden": ""}">
                        <label for="last_name" class="block mb-2 text-sm font-medium text-gray-900 ">Last
                            name</label>
                        <input type="text" id="last_name" name="last_name" value="${user.last_name}"
                               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                               placeholder="lastname">
                    </div>
                    <div class="${(user!=null && user.phone!=null) ? "hidden": ""}">
                        <label for="phone" class="block mb-2 text-sm font-medium text-gray-900 ">Phone
                            number</label>
                        <input type="tel" id="phone" name="phone" value="${user.phone}"
                               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                               placeholder="phone">
                    </div>
                    <div class="${(user!=null && user.gender!=null) ? "hidden": ""}">
                        <label for="gender" class="block mb-2 text-sm font-medium text-gray-900 mb-3">Gender</label>
                        <div class="flex items-center">
                            <div class="flex items-center mr-4">
                                <input id="male" type="radio" value="male" ${user.gender ? "checked" : ""} name="gender"
                                       class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 ">
                                <label for="male" class="ml-2 text-sm font-medium text-gray-900 ">Male</label>
                            </div>
                            <div class="flex items-center mr-4">
                                <input id="female" type="radio" value="female" ${user.gender ? "" : "checked"} name="gender"
                                       class=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 ">
                                <label for="female" class="ml-2 text-sm font-medium text-gray-900 ">Female</label>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="mb-6 ${(user!=null && user.email!=null) ? "hidden": ""}">
                    <label for="email" class="block mb-2 text-sm font-medium text-gray-900">Email</label>
                    <input type="email" id="email" name="email" value="${user.email}"
                           class=" bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                           placeholder="email">
                </div>

                <div class=" bg-white rounded-lg border border-gray-200 p-5">
                    <a href="#">
                        <img class="w-full h-[200px]" src="${pageContext.request.contextPath}/images/subject/${register.subject.image}" alt=""/>
                    </a>
                    <div class="p-2">
                        <a href="${pageContext.request.contextPath}/course/${register.subject.id}">
                            <h5 class="mb-2 text-xl font-medium tracking-tight text-gray-900 ">${register.subject.name}</h5>
                        </a>
                    </div>
                    <div style="max-width: full; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical;">${subject.description}</div>
                </div>
            </div>
            <div class="col-span-1 h-500px ">
                <div class="px-6 py-5 mb-5 border border-gray-200">
                    <table class="w-full text-sm text-center text-gray-900">
                        <tbody>
                            <c:forEach items="${register.subject.packegePrices}" var="price">
                                <tr class="bg-white">
                                    <td class="py-2 font-medium">
                                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4M7.835 4.697a3.42 3.42 0 001.946-.806 3.42 3.42 0 014.438 0 3.42 3.42 0 001.946.806 3.42 3.42 0 013.138 3.138 3.42 3.42 0 00.806 1.946 3.42 3.42 0 010 4.438 3.42 3.42 0 00-.806 1.946 3.42 3.42 0 01-3.138 3.138 3.42 3.42 0 00-1.946.806 3.42 3.42 0 01-4.438 0 3.42 3.42 0 00-1.946-.806 3.42 3.42 0 01-3.138-3.138 3.42 3.42 0 00-.806-1.946 3.42 3.42 0 010-4.438 3.42 3.42 0 00.806-1.946 3.42 3.42 0 013.138-3.138z"></path></svg>
                                    </td>
                                    <td class="py-2 font-medium text-left">
                                        ${price.getName()}
                                    </td>
                                    <td class="px-2 py-2 font-medium line-through">
                                        ${price.getList_price()} $
                                    </td>
                                    <td class="px-2 py-2 font-medium">
                                        ${price.getSale_price()} $
                                    </td>
                                    <td class="px-2 py-2 font-medium">
                                        <div class="flex items-center">
                                            <input type="radio" value="${price.id}" name="price" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500">
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <button type="submit" class="mt-10 text-center px-5 py-2.5 bg-yellow-400 block font-medium cursor-pointer w-full">
                        Save
                    </button>
                </div>
            </div>
        </div>
    </form>
</div>


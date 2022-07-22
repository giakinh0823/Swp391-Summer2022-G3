<%-- 
    Document   : user_edit
    Created on : May 30, 2022, 3:15:54 AM
    Author     : Computer
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="flex items-center justify-between">
    <h1 class="text-xl font-medium mb-3">${user.username}</h1>
    <div  class="flex items-center">
        <c:if test="${sessionScope.user.checkFeature('USER_EDIT') || sessionScope.user.is_super()}">
            <a href="${pageContext.request.contextPath}/admin/user/edit/${user.id}" class="font-medium text-blue-600 hover:underline">Edit</a>
        </c:if>
        <c:if test="${sessionScope.user.checkFeature('USER_DELETE') || sessionScope.user.is_super()}">
            <span class="font-medium mx-1">/</span>
            <button type="button" onclick="$('#button-confirm-delete').attr('href', '${pageContext.request.contextPath}/admin/user/delete/${user.id}')"  class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete">Delete</button>
        </c:if>    
    </div>
</div>
<form class="max-w-[400px] mx-auto mb-5 py-10" method="GET" enctype="multipart/form-data">
    <input disabled type="hidden" name="id" value="${user.id}"/>
    <div class="flex justify-end">
        <div class="mb-2">
            <label for="status" class="inline-flex relative items-center cursor-pointer">
                <input disabled type="checkbox"  ${user.is_active() ? "checked" : ""} name="status" value="active" id="status" ${(sessionScope.user.checkFeature('USER_CHANGE_STATUS') || sessionScope.user.is_super()) ? "":"disabled"} class="sr-only peer">
                <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                <span class="ml-3 text-sm font-medium text-gray-900">Active</span>
            </label>
        </div>
    </div>
    <div class="mb-6">
        <label for="username" class="block mb-2 text-sm font-medium text-gray-900">Username</label>
        <input disabled type="text" id="username" name="username" value="${user.username}"  ${(sessionScope.user.checkFeature('USER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"}class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="mb-6">
        <label for="username" class="block mb-2 text-sm font-medium text-gray-900">Email</label>
        <input disabled type="email" id="email" name="email" value="${user.email}" ${(sessionScope.user.checkFeature('USER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="mb-6">
        <label for="first_name" class="block mb-2 text-sm font-medium text-gray-900">First name</label>
        <input disabled type="text" id="first_name" name="first_name" value="${user.first_name}"  ${(sessionScope.user.checkFeature('USER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="mb-6">
        <label for="last_name" class="block mb-2 text-sm font-medium text-gray-900">Last name</label>
        <input  disabled type="text" id="last_name" name="last_name" value="${user.last_name}"  ${(sessionScope.user.checkFeature('USER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="mb-6 flex items-center">
        <div class="flex items-center">
            <input disabled ${user.gender ? "checked" : "" }  id="male" name="gender" type="radio" value="male"   ${(sessionScope.user.checkFeature('USER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 focus:ring-2">
            <label class="ml-2 text-sm font-medium text-gray-900">Male</label>
        </div>
        <div class="flex items-center ml-3">
            <input disabled ${user.gender ? "" : "checked" }  id="female" name="gender" type="radio" value="female"  ${(sessionScope.user.checkFeature('USER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 focus:ring-2">
            <label  for="female" class="ml-2 text-sm font-medium text-gray-900">Female</label>
        </div>
    </div>
    <div class="mb-6">
        <label for="phone" class="block mb-2 text-sm font-medium text-gray-900">Phone</label>
        <input disabled  type="text" id="phone" name="phone" value="${user.phone}"  ${(sessionScope.user.checkFeature('USER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="mb-3">
        <c:choose>
            <c:when test="${fn:containsIgnoreCase(user.avatar, 'dummyimage.com')}">
                <img class="max-w-[100%] max-h-[400px]" src="${user.avatar}" id="avatar-preview"/>
            </c:when>
            <c:when test="${user.avatar!=null}">
                <img class="max-w-[100%] max-h-[400px]" src="${pageContext.request.contextPath}/images/user/${user.avatar}" id="avatar-preview"/>
            </c:when>
            <c:otherwise>
                <img class="max-w-[100%] max-h-[400px]" src="${pageContext.request.contextPath}/assets/images/default/avatar.jpg" id="avatar-preview"/>
            </c:otherwise>
        </c:choose>  
    </div>
    <div class="mb-6 flex items-center">
        <c:if test="${sessionScope.user.is_super()}">
            <div class="flex items-center mr-3">
                <input disabled ${user.is_super() ? "checked" : "" }  id="super" name="super" type="checkbox" value="super"   ${(sessionScope.user.checkFeature('USER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 focus:ring-2">
                <label for="super" class="ml-2 text-sm font-medium text-gray-900">Is super</label>
            </div>
        </c:if>
        <div class="flex items-center ">
            <input disabled ${user.is_staff() ? "checked" : "" }  id="staff" name="staff" type="checkbox" value="staff"  ${(sessionScope.user.checkFeature('USER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 focus:ring-2">
            <label  for="staff" class="ml-2 text-sm font-medium text-gray-900">Is staff</label>
        </div>
    </div>
    <div class="mb-6">
        <label for="roles" class="block mb-2 text-sm font-medium text-gray-900">Roles</label>
        <select disabled multiple id="roles" name="roles" ${(sessionScope.user.checkFeature('USER_EDIT_ROLE') || sessionScope.user.is_super()) ? "":"disabled"} class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
            <c:forEach items="${roles}" var="role"> 
                <option value="${role.id}" ${user.checkGroup(role.value) ? "selected": ""}>${role.value}</option>
            </c:forEach>
        </select>
    </div>
</form>


<div id="popup-modal-delete" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-md h-full md:h-auto">
        <div class="relative bg-white rounded-lg shadow">
            <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center " data-modal-toggle="popup-modal-delete">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
            </button>
            <div class="p-6 text-center">
                <svg class="mx-auto mb-4 w-14 h-14 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this user?</h3>
                <a id="button-confirm-delete" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>
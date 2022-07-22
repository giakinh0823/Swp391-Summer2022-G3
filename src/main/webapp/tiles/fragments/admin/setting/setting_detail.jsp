<%-- 
    Document   : setting_edit
    Created on : May 30, 2022, 12:31:02 AM
    Author     : Administrator
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="mb-3 flex justify-between items-center">
    <h1 class="text-xl font-medium uppercase">${setting.value}</h1>
    <div class="flex items-center">
        <a href="${pageContext.request.contextPath}/admin/setting/edit/${setting.id}" class="font-medium text-blue-600">Edit</a>
        <span class="font-medium mx-2">/</span>
        <button type="button" onclick="$('#button-confirm-delete').attr('href', 'setting/delete/${setting.id}')" class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete">Delete</button> 
    </div>
</div>
<form class="max-w-[400px] mx-auto mt-10" method="GET">
    <div class="mb-6">
        <label for="id" class="block mb-2 text-sm font-medium text-gray-900">Id</label>
        <input disabled type="text" id="id" name="id" value="${setting.id}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>
    <div class="mb-6">
        <div>
            <label for="type" class="block mb-2 text-sm font-medium text-gray-900">Type</label>
        </div>
        <select disabled id="type" name="type" onchange="showParen(this, '${pageContext.request.contextPath}/admin/setting/no-parent')" 
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
            <c:forEach items="${types}" var="type">
                <option 
                    ${setting.type == type.code ? "selected":""}
                    value="${type.code}">${type.name}</option>
            </c:forEach>
        </select>
    </div>
    <div class="mb-6 ${setting.type=="CATEGORY_POST" || setting.type=="CATEGORY_SUBJECT"?"":"hidden"}" id="parent-field">
        <div>
            <label for="parent" class="block mb-2 text-sm font-medium text-gray-900">Parent</label>
        </div>
        <select disabled id="parent" name="parent"
                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
            <option value="none">None</option>
            <c:forEach items="${parents}" var="parent">
                <c:if test="${parent.type == setting.type}">
                    <option value="${parent.id}" ${(setting.setting!=null && setting.setting.id == parent.id) ? "selected": ""}>${parent.value}</option>
                </c:if>
            </c:forEach>
        </select>
    </div>
    <div class="mb-6">
        <label for="value" class="block mb-2 text-sm font-medium text-gray-900">Value</label>
        <input type="text" disabled id="value" name="value" value="${setting.value}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>

    <div class="mb-6">
        <label for="order" class="block mb-2 text-sm font-medium text-gray-900">Order</label>
        <input type="number" disabled id="order" min="1" name="order" value="${setting.order}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
    </div>

    <div class="mb-6">
        <div>
            <label for="status" class="block mb-2 text-sm font-medium text-gray-900">Status</label>
        </div>
        <div class="flex items-center">
            <div class="flex items-center mr-4">
                <input disabled id="status_true" type="radio" 
                       ${setting.status == true ? "checked":""}
                       value="status_true" name="status" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500">
                <label  for="status_true" class="ml-2 text-sm font-medium text-gray-900">True</label>
            </div>
            <div class="flex items-center mr-4">
                <input disabled id="status_false" type="radio" 
                       ${setting.status == false ? "checked":""}
                       value="status_false" name="status" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500">
                <label for="status_false" class="ml-2 text-sm font-medium text-gray-900">False</label>
            </div>
        </div>
        <div class="mb-6 mt-6">
            <label for="description" class="block mb-2 text-sm font-medium text-gray-900">Description</label>
            <textarea disabled id="description" name="description" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Description...">${setting.description}</textarea>
        </div>
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
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this setting?</h3>
                <a id="button-confirm-delete" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>

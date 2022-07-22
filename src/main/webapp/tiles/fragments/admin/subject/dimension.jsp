<%-- 
    Document   : dimension
    Created on : May 28, 2022, 1:59:08 AM
    Author     : giaki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<h1 class="text-2xl mb-5 font-medium">${subject.name}</h1>
<%@include file="/tiles/fragments/admin/subject/tabbar.jsp" %>
<div id="alert-message" class="${message!=null ? "": "hidden"} p-4 mb-4 text-sm ${message.code == "success" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"} rounded-lg" role="alert">
    <span class="font-medium" id="alert-message-code">${message.code == "success" ? "Success" : "Error"}!</span> <span id="alert-message-span">${message.message}</span>
</div>
<div class="flex items-center mb-3">
    <div>
        <a href="${pageContext.request.contextPath}/admin/subject/${subject.id}" class="py-2 px-5 mr-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 :focus:ring-gray-700">Back</a>
    </div>
    <div class="ml-auto">
        <c:if test="${(sessionScope.user.checkFeature('DIMENSION_CREATE') || sessionScope.user.is_super())}">
            <a href="dimension/create" class="py-2 px-5 mr-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 :focus:ring-gray-700">Add new</a>
        </c:if>
    </div>
</div>
<div class="relative overflow-x-auto shadow-md sm:rounded-lg">
    <table class="w-full text-sm text-left text-gray-500">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50">
            <tr>
                <th scope="col" class="px-6 py-3">
                    Id
                </th>
                <th scope="col" class="px-6 py-3">
                    name
                </th>
                <th scope="col" class="px-6 py-3">
                    type
                </th>
                <th scope="col" class="px-6 py-3">
                    description
                </th>
                <th scope="col" class="px-6 py-3">
                    Action
                </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${dimensions}" var="item">
                <tr class="bg-white border-b hover:bg-gray-50 text-gray-900" id="package-item-${item.id}">
                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                        ${item.id}
                    </th>
                    <td class="px-6 py-4 font-medium text-gray-900">
                        ${item.name}
                    </td>
                    <td class="px-6 py-4 font-medium text-gray-900">
                        ${item.type.value}
                    </td>
                    <td class="px-6 py-4 font-medium text-gray-900">
                        ${item.description}
                    </td>
                    <td class="px-6 py-4">
                        <a href="dimension/${item.id}" class="font-medium text-blue-600 hover:underline">View</a>
                        <span class="font-medium mx-2">/</span>
                        <a href="dimension/edit/${item.id}" class="font-medium text-blue-600 hover:underline">Edit</a>
                        <span class="font-medium mx-2">/</span>
                        <button type="button" onclick="$('#button-confirm-delete').attr('href', 'dimension/delete/${item.id}')"  class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete">Delete</button>

                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<c:if test="${page.size<=0}">
    <h2 class="mt-20 text-3xl text-center">Not found dimension!</h2>
</c:if>
<c:if test="${page.size>0}">
    <%@include file="/views/component/pagination.jsp" %>
</c:if>


<div id="popup-modal-delete" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-md h-full md:h-auto">
        <div class="relative bg-white rounded-lg shadow">
            <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center " data-modal-toggle="popup-modal-delete">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
            </button>
            <div class="p-6 text-center">
                <svg class="mx-auto mb-4 w-14 h-14 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this dimension?</h3>
                <a id="button-confirm-delete" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>

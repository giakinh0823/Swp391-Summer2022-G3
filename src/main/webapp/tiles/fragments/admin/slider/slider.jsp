<%-- 
    Document   : slider
    Created on : May 23, 2022, 11:49:25 PM
    Author     : lanh0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="relative overflow-x-auto shadow-md sm:rounded-lg">
    <div class="flex items-center">
        <form method="GET">
            <div class="p-4 flex items-center justify-between">
                <label for="table-search" class="sr-only">Search</label>
                <div class="relative mt-1">
                    <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                        <svg class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
                    </div>
                    <input type="text" id="search" name="search" value="${search}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-80 pl-10 p-2.5" placeholder="Search for tile or backlink">
                </div>
            </div>
        </form>
        <div class="ml-3">
            <select id="status" onchange="filterStatus(this)" class="w-[200px] bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                <option value="">Status</option>
                <option value="active" ${status=="active"?"selected":""}>Active</option>
                <option value="inactive" ${status=="inactive"?"selected":""}>Inactive</option>
            </select>
        </div>
        <div class="ml-auto">
            <a href="slider/create" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2">Add slider</a>
            <button type="button" id="button-delete-slider-seleted" data-modal-toggle="popup-modal-delete-slider-selected"  class="hidden focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm py-2 px-5 mr-2 mb-2">Delete selected</button>
        </div>
    </div>
    <div id="alert-message" class="${message!=null ? "": "hidden"} p-4 mb-4 text-sm ${message.code == "success" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"} rounded-lg" role="alert">
        <span class="font-medium" id="alert-message-code">${message.code == "success" ? "Success" : "Error"}!</span> <span id="alert-message-span">${message.message}</span>
    </div>
    <table class="w-full text-sm text-left text-gray-500">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50">
            <tr>
                <th scope="col" class="px-6 py-3">
                    <div class="flex items-center">
                        <input id="checkbox-all-slider" onchange="changeCheckboxAll()" type="checkbox" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500">
                            <label for="checkbox-all-search" class="sr-only">checkbox</label>
                    </div>
                </th>
                <th scope="col" class="px-6 py-3">
                    ID
                </th>
                <th scope="col" class="px-6 py-3">
                    Title
                </th>
                <th scope="col" class="px-6 py-3">
                    Image
                </th>
                <th scope="col" class="px-6 py-3">
                    backlink
                </th>
                <th scope="col" class="px-6 py-3">
                    status
                </th>
                <th scope="col" class="px-6 py-3">
                    notes
                </th>
                <th scope="col" class="px-6 py-3">
                    Action
                </th>
            </tr>
        </thead>

        <tbody>
            <c:forEach items="${sliders}" var="slider">
                <tr class="bg-white border-b" id="slider-item-${slider.id}">
                    <td class="px-6 py-3 font-medium text-gray-900">
                        <div class="flex items-center">
                            <input id="checkbox-table-slider" name="slider[]" onchange="changeCheckbox()" value="${slider.id}"type="checkbox" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500 ">
                                <label for="checkbox-table-search-1" class="sr-only">checkbox</label>
                        </div>
                    </td>
                    <td scope="col" class="px-6 py-3 font-medium text-gray-900">
                        ${slider.id}
                    </td>
                    <td scope="col" class="px-6 py-3 font-medium text-gray-900">
                        <p class="min-w-[150px]" style="overflow: hidden;  text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;"> 
                            ${slider.title}
                        </p>
                    </td>
                    <td scope="col" class="px-6 py-3 font-medium text-gray-900">
                        <img class="${slider.image!=null ? "":"hidden"} max-w-[100px] max-h-[100px]" src="${pageContext.request.contextPath}/images/slider/${slider.image}"/>
                    </td>
                    <td scope="col" class="px-6 py-3 font-medium text-gray-900">
                        <p class="max-w-[200px]" style="overflow: hidden;  text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;"> 
                            ${slider.backlink}
                        </p>
                    </td>
                    <td scope="col" class="px-6 py-3 font-medium text-gray-900">
                        <label for="toggle-slider-${slider.id}" class="inline-flex relative items-center cursor-pointer">
                            <input type="checkbox" ${slider.status ? "checked" : ""} onchange="changeActiveSlider(this)" value="${slider.id}" id="toggle-slider-${slider.id}" ${(sessionScope.user.checkFeature('SLIDER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="sr-only peer">
                                <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
                        </label>
                    </td>

                    <td scope="col" class="px-6 py-3 max-w-[500px] font-medium text-gray-900" >
                        <p style="overflow: hidden;  text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;"> 
                            ${slider.notes}
                        </p>
                    </td>
                    <td scope="col" class="px-6 py-3 font-medium text-gray-900">
                        <span class="flex items-center">
                            <c:choose>
                                <c:when test="${sessionScope.user.checkFeature('SLIDER_EDIT') || sessionScope.user.is_super()}">
                                    <a href="${pageContext.request.contextPath}/admin/slider/${slider.id}" class="font-medium text-blue-600">View</a>
                                    <span class="font-medium mx-2">/</span>
                                    <a href="${pageContext.request.contextPath}/admin/slider/edit/${slider.id}" class="font-medium text-blue-600">Edit</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/admin/slider/edit/${slider.id}" class="font-medium text-blue-600">View</a>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${sessionScope.user.checkFeature('SLIDER_DELETE') || sessionScope.user.is_super()}">
                                <span class="font-medium mx-2">/</span>
                                <button type="button" onclick="$('#button-confirm-delete-slider').attr('href', 'slider/delete/${slider.id}')"  class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete-slider">Delete</button>                       
                            </c:if>      
                        </span>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<c:if test="${page.size<=0}">
    <h2 class="mt-20 text-3xl text-center">Not found slider!</h2>
</c:if>
<c:if test="${page.size>0}">
    <%@include file="/views/component/pagination.jsp" %>
</c:if>

<div id="popup-modal-delete-slider" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-md h-full md:h-auto">
        <div class="relative bg-white rounded-lg shadow">
            <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center " data-modal-toggle="popup-modal-delete-slider">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
            </button>
            <div class="p-6 text-center">
                <svg class="mx-auto mb-4 w-14 h-14 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this slider?</h3>
                <a id="button-confirm-delete-slider" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete-slider" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>



<div id="popup-modal-delete-slider-selected" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-md h-full md:h-auto">
        <div class="relative bg-white rounded-lg shadow">
            <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center " data-modal-toggle="popup-modal-delete-slider-selected">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
            </button>
            <div class="p-6 text-center">
                <svg class="mx-auto mb-4 w-14 h-14 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this feature?</h3>
                <button type="button" onclick="confirmDeleteSliderSelected('${pageContext.request.contextPath}/admin/slider/delete-selected')" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </button>
                <button id="button-delete-slider-selected-cancel" data-modal-toggle="popup-modal-delete-slider-selected" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>

<div id="popup-modal-change-status" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-md h-full md:h-auto">
        <div class="relative bg-white rounded-lg shadow">
            <button type="button" id="button-close-change-status" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center " data-modal-toggle="popup-modal-change-status">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
            </button>
            <div class="p-6 text-center">
                <svg class="mx-auto mb-4 w-14 h-14 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to change this slider?</h3>
                <button type="button" id="button-confirm-change-status" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </button>
                <button data-modal-toggle="popup-modal-change-status" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>

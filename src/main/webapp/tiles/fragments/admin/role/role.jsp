<%-- 
    Document   : role
    Created on : May 24, 2022, 1:29:22 PM
    Author     : giaki
--%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1 class="text-lg mb-3 font-medium">Manage role</h1>
<div class="relative overflow-x-auto shadow-md sm:rounded-lg">
    <table class="w-full text-sm text-left text-gray-500">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50">
            <tr>
                <th scope="col" class="px-6 py-3">
                    Id
                </th>
                <th scope="col" class="px-6 py-3">
                    Name
                </th>
                <th scope="col" class="px-6 py-3">
                    Order
                </th>
                <th scope="col" class="px-6 py-3">
                    Status
                </th>
                <th scope="col" class="px-6 py-3">
                    Action
                </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${groups}" var="group">
                <tr class="bg-white border-b hover:bg-gray-50">
                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                        ${group.id}
                    </th>
                    <td class="px-6 py-4 font-medium text-gray-900">
                        ${group.value}
                    </td>
                    <td class="px-6 py-4 font-medium text-gray-900">
                        ${group.order}
                    </td>
                    <td class="px-6 py-4 font-medium text-gray-900">
                        ${group.status ? "active" :"inactive"}
                    </td>
                    <td class="px-6 py-4">
                        <c:choose>
                            <c:when test="${sessionScope.user.checkFeature('ROLE_EDIT') || sessionScope.user.is_super()}">
                                <a href="role/edit/${group.id}" class="font-medium text-blue-600 hover:underline">Edit Role</a>
                            </c:when>
                            <c:otherwise>
                                <a href="role/edit/${group.id}" class="font-medium text-blue-600 hover:underline">View</a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<h1 class="text-lg mb-3 font-medium mt-5">Manage feature</h1>
<div class="flex items-center mb-3">
    <div>
        <form class="flex items-center">   
            <label for="ssearch-feature" class="sr-only">Search</label>
            <div class="relative w-full">
                <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                    <svg class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
                </div>
                <input type="text" id="search-feature" name="search" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2" placeholder="Search">
            </div>
            <button type="submit" class="p-2 ml-2 text-sm font-medium text-white bg-blue-700 rounded-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300"><svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg></button>
        </form>
    </div>
    <div class="ml-auto">
        <button type="button" data-modal-toggle="popup-modal-explain-feature" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2 mr-2 mb-2 focus:outline-none">Feature</button>
        <c:if test="${(sessionScope.user.checkFeature('FEATURE_CREATE') || sessionScope.user.is_super())}">
            <a href="role/feature/create" class="py-2 px-5 mr-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 :focus:ring-gray-700">Add new</a>
        </c:if>
        <c:if test="${(sessionScope.user.checkFeature('FEATURE_DELETE') || sessionScope.user.is_super())}">
            <button type="button" id="button-delete-feature-seleted" data-modal-toggle="popup-modal-delete-feature-selected"  class="hidden focus:outline-none text-white bg-red-700 hover:bg-red-800 focus:ring-4 focus:ring-red-300 font-medium rounded-lg text-sm py-2 px-5 mr-2 mb-2">Delete selected</button>
        </c:if>
    </div>
</div>
<div id="alert-message" class="${message!=null ? "": "hidden"} p-4 mb-4 text-sm ${message.code == "success" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"} rounded-lg" role="alert">
    <span class="font-medium" id="alert-message-code">${message.code == "success" ? "Success" : "Error"}!</span> <span id="alert-message-span">${message.message}</span>
</div>
<div class="relative overflow-x-auto shadow-md sm:rounded-lg">
    <table class="w-full text-sm text-left text-gray-500">
        <thead class="text-xs text-gray-700 uppercase bg-gray-50">
            <tr>
                <c:if test="${(sessionScope.user.checkFeature('FEATURE_DELETE') || sessionScope.user.is_super())}">
                    <th scope="col" class="p-4">
                        <div class="flex items-center">
                            <input id="checkbox-all-feature" onchange="changeCheckboxAll()" type="checkbox" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500  focus:ring-2">
                            <label for="checkbox-all" class="sr-only">checkbox</label>
                        </div>
                    </th>
                </c:if>
                <th scope="col" class="px-6 py-3">
                    <div class="flex space-x-2 items-center">
                        <span>Id</span>
                        <div class="cursor-pointer">
                            <svg class="w-5 h-5 ${pageable.checkSort('id_ASC')?"":"hidden"}" 
                                 id="sort-up-id" 
                                 onclick="sortHandleColumn('id_DESC', this, 'sort-down-id')"  
                                 fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                            <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                            </path>
                            </svg>
                            <svg class="w-5 h-5 ${!pageable.checkField("id") ? "" : pageable.checkSort('id_DESC')? "": "hidden"}" 
                                 id="sort-down-id" 
                                 onclick="sortHandleColumn('id_ASC', this, 'sort-up-id')" 
                                 fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                            <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                            </path>
                            </svg>
                        </div>
                    </div>
                </th>
                <th scope="col" class="px-6 py-3">
                    <div class="flex space-x-2 items-center">
                        <span>Name</span>
                        <div class="cursor-pointer">
                            <svg class="w-5 h-5 ${!pageable.checkField("name") ? "" : pageable.checkSort('name_ASC')? "": "hidden"}" 
                                 id="sort-up-name" 
                                 onclick="sortHandleColumn('name_DESC', this, 'sort-down-name')"  
                                 fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                            <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                            </path>
                            </svg>
                            <svg class="w-5 h-5 ${pageable.checkSort('name_DESC')?"":"hidden"}" 
                                 id="sort-down-name" 
                                 onclick="sortHandleColumn('name_ASC', this, 'sort-up-name')" 
                                 fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                            <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                            </path>
                            </svg>
                        </div>
                    </div>
                </th>
                <th scope="col" class="px-6 py-3">
                    <div class="flex space-x-2 items-center">
                        <span>Feature</span>
                        <div class="cursor-pointer">
                            <svg class="w-5 h-5 ${!pageable.checkField("feature") ? "" : pageable.checkSort('feature_ASC')? "": "hidden"}" 
                                 id="sort-up-feature    " 
                                 onclick="sortHandleColumn('feature_DESC', this, 'sort-down-feature')"  
                                 fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                            <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                            </path>
                            </svg>
                            <svg class="w-5 h-5 ${pageable.checkSort('feature_DESC')?"":"hidden"}" 
                                 id="sort-down-feature" 
                                 onclick="sortHandleColumn('feature_ASC', this, 'sort-up-feature')" 
                                 fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                            <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                            </path>
                            </svg>
                        </div>
                    </div>
                </th>
                <th scope="col" class="px-6 py-3">
                    Action
                </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${features}" var="feature">
                <tr class="bg-white border-b hover:bg-gray-50" id="feature-item-${feature.id}">
                    <c:if test="${(sessionScope.user.checkFeature('FEATURE_DELETE') || sessionScope.user.is_super())}">
                        <td class="w-4 p-4">
                            <div class="flex items-center">
                                <input id="checkbox-table-feature-${feature.id}" name="feature[]" onchange="changeCheckbox()" value="${feature.id}" type="checkbox" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500  focus:ring-2">
                            </div>
                        </td>
                    </c:if>
                    <th scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                        ${feature.id}
                    </th>
                    <td class="px-6 py-4 font-medium text-gray-900">
                        ${feature.name}
                    </td>
                    <td class="px-6 py-4 font-medium text-gray-900">
                        ${feature.feature}
                    </td>
                    <td class="px-6 py-4 ">
                        <c:choose>
                            <c:when test="${sessionScope.user.checkFeature('FEATURE_EDIT') || sessionScope.user.is_super()}">
                                <a href="role/feature/${feature.id}" class="font-medium text-blue-600 hover:underline">Edit</a>
                            </c:when>
                            <c:otherwise>
                                <a href="role/feature/${feature.id}" class="font-medium text-blue-600 hover:underline">View</a>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${(sessionScope.user.checkFeature('FEATURE_DELETE') || sessionScope.user.is_super())}">
                            <span>/</span>
                            <button type="button" onclick="$('#button-confirm-delete-feature').attr('href', 'role/feature/delete/${feature.id}')"  class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete-feature">Delete</button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<c:if test="${page.size<=0}">
    <div class="flex justify-center items-center">
        <h2 class="text-3xl mt-20">Not found feature!</h2>
    </div>
</c:if>

<c:if test="${page.size>0}">
    <%@include file="/views/component/pagination.jsp" %>
</c:if>

<div id="popup-modal-delete-feature" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-md h-full md:h-auto">
        <div class="relative bg-white rounded-lg shadow">
            <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center " data-modal-toggle="popup-modal-delete-feature">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
            </button>
            <div class="p-6 text-center">
                <svg class="mx-auto mb-4 w-14 h-14 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this feature?</h3>
                <a id="button-confirm-delete-feature" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete-feature" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>



<div id="popup-modal-delete-feature-selected" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-md h-full md:h-auto">
        <div class="relative bg-white rounded-lg shadow">
            <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center " data-modal-toggle="popup-modal-delete-feature-selected">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
            </button>
            <div class="p-6 text-center">
                <svg class="mx-auto mb-4 w-14 h-14 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this feature?</h3>
                <button type="button" onclick="confirmDeleteFeatureSelected('${pageContext.request.contextPath}/admin/role/feature/delete-all')" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </button>
                <button id="button-delete-feature-selected-cancel" data-modal-toggle="popup-modal-delete-feature-selected" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>

<div id="popup-modal-explain-feature" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-10 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-[90vh] max-h-full md:h-auto">
        <div class="relative bg-white rounded-lg shadow">
            <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center" data-modal-toggle="popup-modal-explain-feature">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
            </button>
            <h1 class="p-3 pl-4 text-2xl">Current feature</h1>
            <div class="p-6 text-center">
                <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                    <table class="w-full text-sm text-left text-gray-500">
                        <thead class="text-xs text-gray-700 uppercase bg-gray-50">
                            <tr>
                                <th scope="col" class="px-6 py-3">
                                    Name
                                </th>
                                <th scope="col" class="px-6 py-3">
                                    Feature
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Setting
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [SETTING_READ, SETTING_CREATE, SETTING_EDIT, SETTING_DELETE]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    User
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [USER_READ, USER_CREATE, USER_EDIT, USER_DELETE, USER_CHANGE_STATUS, USER_EDIT_ROLE]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Role
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [ROLE_READ, ROLE_CREATE, ROLE_EDIT, ROLE_DELETE]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Feature
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [FEATURE_READ, FEATURE_CREATE, FEATURE_EDIT, FEATURE_DELETE]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Subject
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [SUBJECT_READ, SUBJECT_CREATE, SUBJECT_EDIT, SUBJECT_DELETE, SUBJECT_CHANGE_STATUS, SUBJECT_READ_ALL, SUBJECT_PUBLIC]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Dimension
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [DIMENSION_READ, DIMENSION_CREATE, DIMENSION_EDIT, DIMENSION_DELETE]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Lesson
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [LESSON_READ, LESSON_CREATE, LESSON_EDIT, LESSON_DELETE]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Price Package
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [PRICEPACKAGE_READ, PRICEPACKAGE_CREATE, PRICEPACKAGE_EDIT, PRICEPACKAGE_DELETE]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Slider
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [SLIDER_READ, SLIDER_CREATE, SLIDER_EDIT, SLIDER_DELETE, SLIDER_PUBLIC]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Question
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [QUESTION_READ, QUESTION_CREATE, QUESTION_EDIT, QUESTION_DELETE, QUESTION_PUBLIC]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Quizzes
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [QUIZZES_READ, QUIZZES_CREATE, QUIZZES_EDIT, QUIZZES_DELETE, QUIZZES_PUBLIC]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Registration 
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [REGISTRATIONS_READ, REGISTRATIONS_CREATE, REGISTRATIONS_EDIT, REGISTRATIONS_DELETE]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Post
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [POST_READ, POST_CREATE, POST_EDIT, POST_DELETE, POST_PUBLIC]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Profile 
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [PROFILE_READ, PROFILE_CREATE, PROFILE_EDIT, PROFILE_DELETE]
                                </td>
                            </tr>
                            <tr class="bg-white border-b">
                                <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    Change password 
                                </th>
                                <td class="px-6 py-4 font-medium text-gray-900">
                                    [CHANGE_PASSWORD]
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

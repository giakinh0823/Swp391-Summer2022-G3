<%-- 
    Document   : setting
    Created on : May 23, 2022, 10:54:41 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="flex items-center">
    <div>
        <form class="flex items-center">   
            <label for="ssearch-feature" class="sr-only">Search</label>
            <div class="relative w-full">
                <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                    <svg class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
                </div>
                <input type="text" id="search" name="search" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2" placeholder="Search">
            </div>
            <button type="submit" class="p-2 ml-2 text-sm font-medium text-white bg-blue-700 rounded-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300"><svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path></svg></button>
        </form>
    </div> 
    <div class="flex items-center ml-auto">
        <c:if test="${(sessionScope.user.checkFeature('SETTING_CREATE') || sessionScope.user.is_super())}">
            <div class="ml-2">
                <a href="setting/create" class="py-2 px-5 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 block">Add new</a>
            </div>
        </c:if>
        <div id="button-open-filter" class="block md:hidden" onclick="showFilterbar(this)">
            <button type="button" class="text-white bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 hover:bg-gradient-to-br focus:ring-2 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-2 py-2 text-center">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M3 3a1 1 0 011-1h12a1 1 0 011 1v3a1 1 0 01-.293.707L12 11.414V15a1 1 0 01-.293.707l-2 2A1 1 0 018 17v-5.586L3.293 6.707A1 1 0 013 6V3z" clip-rule="evenodd"></path></svg>            
            </button>
        </div>
    </div>
</div>

<div class="w-full flex">
    <div class="w-full p-2 overflow-x-auto">
        <div class="relative overflow-x-auto shadow-md sm:rounded-lg mt-5">
            <table class="w-full text-sm text-left text-gray-500">
                <thead class="text-xs text-gray-700 uppercase bg-gray-50">
                    <tr>
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
                                <span>Type</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("type") ? "" : pageable.checkSort('type_ASC')?"" :"hidden"}" 
                                         id="sort-up-type" 
                                         onclick="sortHandleColumn('type_DESC', this, 'sort-down-type')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('type_DESC')?"":"hidden"}" 
                                         id="sort-down-type" 
                                         onclick="sortHandleColumn('type_ASC', this, 'sort-up-type')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Value</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("value") ? "" : pageable.checkSort('value_ASC')?"" :"hidden"}" 
                                         id="sort-up-value" 
                                         onclick="sortHandleColumn('value_DESC', this, 'sort-down-value')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('value_DESC')?"":"hidden"}" 
                                         id="sort-down-value" 
                                         onclick="sortHandleColumn('value_ASC', this, 'sort-up-value')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Parent</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("parent") ? "" : pageable.checkSort('parent_ASC')?"" :"hidden"}" 
                                         id="sort-up-parent" 
                                         onclick="sortHandleColumn('parent_DESC', this, 'sort-down-parent')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('parent_DESC')?"":"hidden"}" 
                                         id="sort-down-parent" 
                                         onclick="sortHandleColumn('parent_ASC', this, 'sort-up-parent')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Order</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("order") ? "" : pageable.checkSort('order_ASC')?"" :"hidden"}" 
                                         id="sort-up-order" 
                                         onclick="sortHandleColumn('order_DESC', this, 'sort-down-order')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('order_DESC')?"":"hidden"}" 
                                         id="sort-down-order" 
                                         onclick="sortHandleColumn('order_ASC', this, 'sort-up-order')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Status</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("status") ? "" : pageable.checkSort('status_ASC')?"" :"hidden"}" 
                                         id="sort-up-status" 
                                         onclick="sortHandleColumn('status_DESC', this, 'sort-down-status')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('status_DESC')?"":"hidden"}" 
                                         id="sort-down-status" 
                                         onclick="sortHandleColumn('status_ASC', this, 'sort-up-status')" 
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
                    <c:forEach items="${settings}" var="setting">

                        <tr class="border-b">
                            <th scope="row" class="px-6 py-4 font-medium text-gray-900">
                                ${setting.id}
                            </th>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                ${setting.type}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900 ">
                                ${setting.value}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900 ">
                                ${setting.setting.value}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900 ">
                                ${setting.order}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                ${setting.status ? "activate" : "deactivate"}
                            </td>
                            <td scope="col" class="px-6 py-3">
                                <span class="flex items-center">
                                    <a href="${pageContext.request.contextPath}/admin/setting/${setting.id}" class="font-medium text-blue-600">View</a>
                                    <span class="font-medium mx-2">/</span>
                                    <a href="${pageContext.request.contextPath}/admin/setting/edit/${setting.id}" class="font-medium text-blue-600">Edit</a>
                                    <span class="font-medium mx-2">/</span>
                                    <button type="button" onclick="$('#button-confirm-delete').attr('href', 'setting/delete/${setting.id}')" class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete">Delete</button>                       
                                </span>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>    
        <c:if test="${page.size<=0}">
            <h2 class="mt-20 text-3xl text-center">Not found setting!</h2>
        </c:if>
        <c:if test="${page.size>0}">
            <%@include file="/views/component/pagination.jsp" %>
        </c:if>
    </div>
    <div id="filter-drag-layout" class="hidden md:block min-w-[200px] md:min-w-[200px] md:max-w-[200px] xl:min-w-[220px] xl:max-w-[220px]">
        <div id="filter-drag-content" class="w-full mt-5 p-5 ui-widget-content max-h-[85vh] overflow-y-auto" style="box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px; border:none">
            <div class="hidden flex items-center justify-end mb-3" id="button-close-filter">
                <button type="button" onclick="closeFilter()">
                    <svg class="w-4 h-4 text-gray-700" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>                
                </button>
            </div>
            <div class="border-b border-gray-200 flex justify-between">
                <h2 class="mb-3 font-medium uppercase">Filter</h2>
                <button type="button" onclick="window.location.search = ''">
                    <h2 class="mb-3 font-medium uppercase text-sm text-blue-600">Clear</h2>
                </button>
            </div>
            <form class="mt-5">
                <div class="mb-3">
                    <h2 class="mb-3 font-medium">Types</h2>
                    <c:forEach items="${types}" var="type">
                        <div class="flex items-center mb-2">
                            <input type="checkbox" value="${type.code}" ${pageable.checkFilters('type', type.code) ? "checked": ""} name="type" id="type-${type.id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                                <label for="type-${type.code}" class="ml-2 text-sm font-medium text-gray-900">${type.name}</label>
                        </div>
                    </c:forEach>
                </div>
                <div class="mb-3">
                    <h2 class="mb-3 font-medium">Status</h2>
                    <div class="flex items-center mb-2">
                        <input type="checkbox" value="active" name="status" ${pageable.checkFilters('status', 'activate') ? "checked": ""} id="status-show"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="status-show" class="ml-2 text-sm font-medium text-gray-900">Activate</label>
                    </div>
                    <div class="flex items-center mb-2">
                        <input type="checkbox" value="inactive" name="status" ${pageable.checkFilters('status', 'deactivate') ? "checked": ""} id="status-hidden"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="status-hidden" class="ml-2 text-sm font-medium text-gray-900">Deactivate</label>
                    </div>
                </div>
                <div class="flex mt-2">
                    <button type="submit" class="w-full bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-3 py-2 mt-2 focus:outline-none text-white">Filter</button>
                </div>
            </form>
        </div>
    </div>
</div>


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

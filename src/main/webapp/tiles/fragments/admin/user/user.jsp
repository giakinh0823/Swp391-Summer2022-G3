<%-- 
    Document   : user
    Created on : May 26, 2022, 9:12:36 PM
    Author     : Computer
--%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <div class="ml-auto flex items-center">
        <c:if test="${sessionScope.user.checkFeature('USER_CREATE') || sessionScope.user.is_super()}">
            <a href="user/create" class="py-2 px-5 mr-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200 :focus:ring-gray-700">Add new</a>
        </c:if>
        <div id="button-open-filter" class="block md:hidden" onclick="showFilterbar(this)">
            <button type="button" class="text-white bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 hover:bg-gradient-to-br focus:ring-2 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-2 py-2 text-center">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M3 3a1 1 0 011-1h12a1 1 0 011 1v3a1 1 0 01-.293.707L12 11.414V15a1 1 0 01-.293.707l-2 2A1 1 0 018 17v-5.586L3.293 6.707A1 1 0 013 6V3z" clip-rule="evenodd"></path></svg>            </button>
        </div>
    </div>
</div>
<div class="w-full flex">
    <div class="w-full p-2 overflow-x-auto">
        <div id="alert-message" class="${message!=null ? "": "hidden"} p-4 mb-4 text-sm ${message.code == "success" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"} rounded-lg" role="alert">
            <span class="font-medium" id="alert-message-code">${message.code == "success" ? "Success" : "Error"}!</span> <span id="alert-message-span">${message.message}</span>
        </div>
        <div class="overflow-x-auto shadow-md sm:rounded-lg max-w-full">
            <table class="w-full text-sm text-left text-gray-500">
                <thead class="text-xs text-gray-700 uppercase bg-gray-50">
                    <tr>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Id</span>
                                <div class="cursor-pointer">
                                    <svg   class="w-5 h-5 ${pageable.checkSort('id_ASC')?"":"hidden"}" 
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
                            Avatar
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span class="min-w-max">First name</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("firstName") ? "" : pageable.checkSort('firstName_ASC')? "": "hidden"}" 
                                         id="sort-up-firstName" 
                                         onclick="sortHandleColumn('firstName_DESC', this, 'sort-down-firstName')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('firstName_DESC')?"":"hidden"}" 
                                         id="sort-down-firstName" 
                                         onclick="sortHandleColumn('firstName_ASC', this, 'sort-up-firstName')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span class="min-w-max">Last name</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("lastName") ? "" : pageable.checkSort('lastName_ASC')? "": "hidden"}" 
                                         id="sort-up-lastName" 
                                         onclick="sortHandleColumn('lastName_DESC', this, 'sort-down-lastName')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('lastName_DESC')?"":"hidden"}" 
                                         id="sort-down-lastName" 
                                         onclick="sortHandleColumn('lastName_ASC', this, 'sort-up-lastName')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Username</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("username") ? "" : pageable.checkSort('username_ASC')? "": "hidden"}" 
                                         id="sort-up-username" 
                                         onclick="sortHandleColumn('username_DESC', this, 'sort-down-username')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('username_DESC')?"":"hidden"}" 
                                         id="sort-down-username" 
                                         onclick="sortHandleColumn('username_ASC', this, 'sort-up-username')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Email</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("email") ? "" : pageable.checkSort('email_ASC')? "": "hidden"}" 
                                         id="sort-up-email" 
                                         onclick="sortHandleColumn('email_DESC', this, 'sort-down-email')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('email_DESC')?"":"hidden"}" 
                                         id="sort-down-email" 
                                         onclick="sortHandleColumn('email_ASC', this, 'sort-up-email')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Phone</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("phone") ? "" : pageable.checkSort('phone_ASC')? "": "hidden"}" 
                                         id="sort-up-phone" 
                                         onclick="sortHandleColumn('phone_DESC', this, 'sort-down-phone')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('phone_DESC')?"":"hidden"}" 
                                         id="sort-down-phone" 
                                         onclick="sortHandleColumn('phone_ASC', this, 'sort-up-phone')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Gender</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("gender") ? "" : pageable.checkSort('gender_ASC')? "": "hidden"}" 
                                         id="sort-up-gender" 
                                         onclick="sortHandleColumn('gender_DESC', this, 'sort-down-gender')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('gender_DESC')?"":"hidden"}" 
                                         id="sort-down-gender" 
                                         onclick="sortHandleColumn('gender_ASC', this, 'sort-up-gender')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            Roles
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Super</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("super") ? "" : pageable.checkSort('super_ASC')? "": "hidden"}" 
                                         id="sort-up-super" 
                                         onclick="sortHandleColumn('super_DESC', this, 'sort-down-super')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('super_DESC')?"":"hidden"}" 
                                         id="sort-down-super" 
                                         onclick="sortHandleColumn('super_ASC', this, 'sort-up-super')" 
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h7a1 1 0 100-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM15 8a1 1 0 10-2 0v5.586l-1.293-1.293a1 1 0 00-1.414 1.414l3 3a1 1 0 001.414 0l3-3a1 1 0 00-1.414-1.414L15 13.586V8z">
                                        </path>
                                    </svg>
                                </div>
                            </div>
                        </th>
                        <th scope="col" class="px-6 py-3">
                            <div class="flex space-x-2 items-center">
                                <span>Staff</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("staff") ? "" : pageable.checkSort('staff_ASC')? "": "hidden"}" 
                                         id="sort-up-staff" 
                                         onclick="sortHandleColumn('staff_DESC', this, 'sort-down-staff')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('staff_DESC')?"":"hidden"}" 
                                         id="sort-down-staff" 
                                         onclick="sortHandleColumn('staff_ASC', this, 'sort-up-staff')" 
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
                                    <svg class="w-5 h-5 ${!pageable.checkField("status") ? "" : pageable.checkSort('status_ASC')? "": "hidden"}" 
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
                            <div class="flex space-x-2 items-center">
                                <span>Enable</span>
                                <div class="cursor-pointer">
                                    <svg class="w-5 h-5 ${!pageable.checkField("enable") ? "" : pageable.checkSort('enable_ASC')? "": "hidden"}" 
                                         id="sort-up-enable" 
                                         onclick="sortHandleColumn('enable_DESC', this, 'sort-down-enable')"  
                                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M3 3a1 1 0 000 2h11a1 1 0 100-2H3zM3 7a1 1 0 000 2h5a1 1 0 000-2H3zM3 11a1 1 0 100 2h4a1 1 0 100-2H3zM13 16a1 1 0 102 0v-5.586l1.293 1.293a1 1 0 001.414-1.414l-3-3a1 1 0 00-1.414 0l-3 3a1 1 0 101.414 1.414L13 10.414V16z">
                                        </path>
                                    </svg>
                                    <svg class="w-5 h-5 ${pageable.checkSort('enable_DESC')?"":"hidden"}" 
                                         id="sort-down-enable" 
                                         onclick="sortHandleColumn('enable_ASC', this, 'sort-up-enable')" 
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
                    <c:forEach items="${users}" var="item">
                        <tr class="bg-white border-b hover:bg-gray-50">
                            <td scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                ${item.id}
                            </td>
                            <td class="px-6 py-4">
                                <div class="relative">
                                    <c:choose>
                                        <c:when test="${fn:containsIgnoreCase(item.avatar, 'dummyimage.com')}">
                                            <img class="w-10 h-10 rounded-full" src="${item.avatar}" alt="${item.username}"/>
                                        </c:when>
                                        <c:when test="${item.avatar!=null}">
                                            <img class="w-10 h-10 rounded-full" src="${pageContext.request.contextPath}/images/user/${item.avatar}" alt="${item.username}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img class="w-10 h-10 rounded-full" src="${pageContext.request.contextPath}/assets/images/default/avatar.jpg" alt="${item.username}"/>
                                        </c:otherwise>
                                    </c:choose>  
                                </div>
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                ${item.first_name}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                ${item.last_name}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                ${item.username}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                ${item.email}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                ${item.phone}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                ${item.gender ? "Male" :"Female"}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900 flex flex-wrap">
                                <c:forEach items="${item.groups}" var="group">
                                    <span class="ml-1">${group.value},</span>
                                </c:forEach>
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                <input ${item.is_super() ? "checked": ""} disabled type="checkbox" class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500">
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                <input ${item.is_staff() ? "checked": ""} disabled type="checkbox" class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500">
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                ${item.is_active() ? "active" : "inactive"}
                            </td>
                            <td class="px-6 py-4 font-medium text-gray-900">
                                ${item.enable ? "enabled" : "disabled"}
                            </td>
                            <td class="px-6 py-4">
                                <div class="flex items-center">
                                    <a href="${pageContext.request.contextPath}/admin/user/${item.id}" class="font-medium text-blue-600 hover:underline">View</a>
                                    <c:if test="${sessionScope.user.checkFeature('USER_EDIT') || sessionScope.user.is_super()}">
                                        <span class="font-medium mx-1">/</span>
                                        <a href="${pageContext.request.contextPath}/admin/user/edit/${item.id}" class="font-medium text-blue-600 hover:underline">Edit</a>
                                    </c:if>
                                    <c:if test="${sessionScope.user.checkFeature('USER_DELETE') || sessionScope.user.is_super()}">
                                         <span class="font-medium mx-1">/</span>
                                        <button type="button" onclick="$('#button-confirm-delete').attr('href', '${pageContext.request.contextPath}/admin/user/delete/${item.id}')"  class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete">Delete</button>
                                    </c:if>                            
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <c:if test="${page.size<=0}">
            <h2 class="mt-20 text-3xl text-center">Not found user!</h2>
        </c:if>
        <c:if test="${page.size>0}">
            <%@include file="/views/component/pagination.jsp" %>
        </c:if>
    </div>
    <div id="filter-drag-layout" class="hidden md:block min-w-[200px] md:min-w-[200px] md:max-w-[200px] xl:min-w-[200px] xl:max-w-[256px]">
        <div id="filter-drag-content" class="w-full p-5 ui-widget-content max-h-[85vh] overflow-y-auto" style="box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px; border:none">
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
                    <h2 class="mb-3 font-medium">Role</h2>
                    <c:forEach items="${roles}" var="item">
                        <div class="flex items-center mb-2">
                            <input type="checkbox" value="${item.id}" ${pageable.checkFilters('role', item.id) ? "checked": ""} name="role" id="role-${item.id}"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                                <label for="role-${item.id}" class="ml-2 text-sm font-medium text-gray-900">${item.value}</label>
                        </div>
                    </c:forEach>
                </div>
                <div class="mb-3">
                    <h2 class="mb-3 font-medium">Status</h2>
                    <div class="flex items-center mb-2">
                        <input type="checkbox" value="active" name="status" ${pageable.checkFilters('status', 'active') ? "checked": ""} id="status-active"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="status-active" class="ml-2 text-sm font-medium text-gray-900">active</label>
                    </div>
                    <div class="flex items-center mb-2">
                        <input type="checkbox" value="inactive" name="status" ${pageable.checkFilters('status', 'inactive') ? "checked": ""} id="status-inactive"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="status-inactive" class="ml-2 text-sm font-medium text-gray-900">inactive</label>
                    </div>
                </div>
                <div class="mb-3">
                    <h2 class="mb-3 font-medium">Gender</h2>
                    <div class="flex items-center mb-2">
                        <input type="checkbox" value="male" name="gender" ${pageable.checkFilters('gender', 'male') ? "checked": ""} id="gender-male"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="status-active" class="ml-2 text-sm font-medium text-gray-900">male</label>
                    </div>
                    <div class="flex items-center mb-2">
                        <input type="checkbox" value="female" name="gender" ${pageable.checkFilters('gender', 'female') ? "checked": ""} id="gender-female"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="status-inactive" class="ml-2 text-sm font-medium text-gray-900">female</label>
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
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this user?</h3>
                <a id="button-confirm-delete" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>
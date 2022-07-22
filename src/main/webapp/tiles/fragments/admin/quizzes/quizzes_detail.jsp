<%-- 
    Document   : quizzes_create
    Created on : Jun 12, 2022, 1:13:06 PM
    Author     : giaki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1 class="text-lg mb-3 font-medium">${quizzes.name}</h1>
<div class="container mx-auto">
    <form method="post" id="form-quizzes">
        <div class="w-full text-gray-900">
            <div class="border-b border-gray-200 w-full">
                <ul class="flex flex-wrap text-sm font-medium text-center text-gray-900 opacity-100" id="myTab" data-tabs-toggle="#myTabContent" role="tablist">
                    <li class="mr-2 text-gray-900 opacity-100"  role="presentation">
                        <button class="inline-block p-4 active font-medium opacity-100"  aria-current="page" id="profile-tab" data-tabs-target="#profile" type="button" role="tab" aria-controls="profile" aria-selected="true">
                            Overview
                        </button>
                    </li>
                    <li class="mr-2 text-gray-900 opacity-100"  role="presentation">
                        <button  class="inline-flex p-4 rounded-t-lg font-medium opacity-100" id="dashboard-tab" data-tabs-target="#dashboard" type="button" role="tab" aria-controls="dashboard" aria-selected="false">
                            Settings
                        </button>
                    </li>
                    <li class="ml-auto">
                        <p class="flex items-center">
                            <c:if test="${sessionScope.user.checkFeature('QUIZZES_EDIT') || sessionScope.user.is_super()}">
                                <a href="${pageContext.request.contextPath}/admin/quizzes/edit/${quizzes.id}" class="font-medium text-blue-600 hover:underline">Edit</a>
                            </c:if>
                            <c:if test="${sessionScope.user.checkFeature('QUIZZES_DELETE') || sessionScope.user.is_super()}">
                                <span class="font-medium text-gray-900 m-1">/</span>
                                <button type="button" onclick="$('#button-confirm-delete').attr('href', '${pageContext.request.contextPath}/admin/quizzes/delete/${quizzes.id}')"  class="font-medium text-red-600 hover:underline"  data-modal-toggle="popup-modal-delete">Delete</button>
                            </c:if>
                        </p>
                    </li>
                </ul>
            </div>
            <div id="myTabContent">
                <div class="hidden p-4 rounded-lg" id="profile" role="tabpanel" aria-labelledby="profile-tab">
                    <div class="max-w-[450px] mx-auto mt-10">
                        <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg hidden" role="alert" id="error-overview">
                            <span class="font-medium">Error!</span> <span id="message-error-overview"></span>
                        </div>
                        <div class="mb-6">
                            <label for="name" class="block mb-2 text-sm font-medium text-gray-900">Name</label>
                            <input type="text" disabled id="name" name="name" value="${quizzes.name}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                        </div>

                        <div class="mb-6">
                            <div>
                                <label for="level" class="block mb-2 text-sm font-medium text-gray-900">Level</label>
                            </div>
                            <select disabled id="level" name="level"
                                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                <c:forEach items="${levels}" var="level">
                                    <option value="${level.id}" ${quizzes.levelId == level.id ? "selected": ""}>${level.value}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-6">
                            <h2 class="mb-3 font-medium mb-3" id="label-subject">Subject</h2>
                            <div class="min-h-[20px] max-h-[30px] overflow-y-auto px-2 pt-3" id="list-subject">
                                <div class="flex items-center mb-2">
                                    <input disabled type="radio" value="${quizzes.subject.id}" checked name="subject" id="subject-${quizzes.subject.id}" onclick="selectSubject(this)"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                    <label for="subject-${quizzes.subject.id}" id="label-subject-${quizzes.subject.id}" class="item-subject ml-2 text-sm font-medium text-gray-900">${quizzes.subject.name}</label>
                                </div>
                            </div>
                        </div>
                        <div class="mb-6">
                            <div>
                                <label for="type" class="block mb-2 text-sm font-medium text-gray-900">Type</label>
                            </div>
                            <select disabled id="type" name="type"
                                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                <c:forEach items="${types}" var="type">
                                    <option value="${type.id}" ${quizzes.type.id == type.id ? "selected": ""}>${type.value}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-6">
                            <label for="duration" class="block mb-2 text-sm font-medium text-gray-900">Duration(minus)</label>
                            <input type="number" disabled min="1" id="duration" name="duration" value="${quizzes.duration}" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                        </div>

                        <div class="mb-6">
                            <label for="pass_rate" class="block mb-2 text-sm font-medium text-gray-900">Pass rate(%)</label>
                            <input disabled type="number" min="1" id="pass_rate" name="pass_rate" value="${quizzes.pass_rate}"  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                        </div>


                        <div class="mb-6 mt-6">
                            <label for="description" class="block mb-2 text-sm font-medium text-gray-900">Description</label>
                            <textarea id="description" name="description" rows="4" disabled class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Description...">${quizzes.description}</textarea>
                        </div>
                    </div>
                </div>
                <div class="hidden p-4 rounded-lg" id="dashboard" role="tabpanel" aria-labelledby="dashboard-tab">
                    <div class="max-w-[450px] mx-auto mt-10">
                        <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg hidden" role="alert" id="error-setting">
                            <span class="font-medium">Error!</span> <span id="message-error-setting"></span>
                        </div>
                        <div class="mb-6">
                            <label for="total_question" class="block mb-2 text-sm font-medium text-gray-900">Total question</label>
                            <input disabled type="number" min="1" value="${quizzes.settingQuizzes.totalQuestion}" id="total_question" name="total_question" value="" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                        </div>
                        <div class="mb-6" id="selected-dimension">
                            <c:forEach items="${quizzes.settingQuizzes.quizzesDimensions}" var="settingDimension">
                                <div class="flex mb-3">
                                    <select disabled id="dimension-selected"
                                            class="dimension bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                        <option value="${settingDimension.dimension.id}" selected>${settingDimension.dimension.name}</option>
                                    </select>
                                    <input disabled type="number" min="1" id="new_dimenion" value="${settingDimension.numberQuestion}" class="ml-2 bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5">
                                </div>
                            </c:forEach>
                        </div>
                        <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg hidden" role="alert" id="error-setting-dimension">
                            <span class="font-medium">Error!</span> <span>Not found dimension!</span>
                        </div>
                        <div id="spin-select-type-dimension" class="flex justify-center items-center hidden">
                            <svg role="status" class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
                            <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
                            </svg>
                        </div>
                    </div>
                </div
            </div>
        </div>
    </form>
</div>


<div id="popup-modal-delete" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-md h-full md:h-auto">
        <div class="relative bg-white rounded-lg shadow">
            <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center " data-modal-toggle="popup-modal-delete">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
            </button>
            <div class="p-6 text-center">
                <svg class="mx-auto mb-4 w-14 h-14 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this quizzes?</h3>
                <a id="button-confirm-delete" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>


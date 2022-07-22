



<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<div class="container mx-auto mt-10">
    <div class="flex justify-end w-full">
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
    <div id="alert-message" class="${message!=null ? "": "hidden"} p-4 mb-4 text-sm ${message.code == "success" ? "bg-green-100 text-green-700" : "bg-red-100 text-red-700"} rounded-lg" role="alert">
        <span class="font-medium" id="alert-message-code">${message.code == "success" ? "Success" : "Error"}!</span> <span id="alert-message-span">${message.message}</span>
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
                                </div>
                            </th>
                            <th scope="col" class="px-6 py-3">
                                <div class="flex space-x-2 items-center">
                                    <span>Image</span>
                                </div>
                            </th>
                            <th scope="col" class="px-6 py-3">
                                <div class="flex space-x-2 items-center">
                                    <span>Subject</span>
                                </div>
                            </th>
                            <th scope="col" class="px-6 py-3">
                                <div class="flex space-x-2 items-center">
                                    <span>Created</span>
                                </div>
                            </th>
                            <th scope="col" class="px-6 py-3">
                                <div class="flex space-x-2 items-center">
                                    <span>From</span>
                                </div>
                            </th>
                            <th scope="col" class="px-6 py-3">
                                <div class="flex space-x-2 items-center">
                                    <span>To</span>
                                </div>
                            </th>
                            <th scope="col" class="px-6 py-3">
                                <div class="flex space-x-2 items-center">
                                    <span>Cost</span>
                                </div>
                            </th>
                            <th scope="col" class="px-6 py-3">
                                <div class="flex space-x-2 items-center">
                                    <span>Status</span>
                                </div>
                            </th>
                            <th scope="col" class="px-6 py-3">
                                Action
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${registers}" var="register">
                            <tr class="border-b">
                                <td scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    ${register.id}
                                </td>
                                <td scope="col" class="px-6 py-3 font-medium text-gray-900">
                                    <c:choose>
                                        <c:when test="${fn:containsIgnoreCase(register.subject.image, 'dummyimage.com')}">
                                            <img class="${register.subject.image!=null ? "":"hidden"} max-w-[100px] max-h-[100px]" alt="${register.subject.name}" src="${register.subject.image}" id="image-preview"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img class="${register.subject.image!=null ? "":"hidden"} max-w-[100px] max-h-[100px]" alt="${register.subject.name}" src="${pageContext.request.contextPath}/images/subject/${register.subject.image}" id="image-preview"/>
                                        </c:otherwise>
                                    </c:choose>  
                                </td>
                                <td scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    <a href="${pageContext.request.contextPath}/course/${register.subject.id}" class="hover:underline hover:text-blue-500" style="max-width: 200px; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2;-webkit-box-orient: vertical;">
                                        ${register.subject.name}
                                    </a>
                                </td>
                                <td scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                    <fmt:formatDate value="${register.created_at}" pattern="dd-MM-yyyy hh:mm:ss"/>
                                </td>
                                <td scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                    <fmt:formatDate value="${register.valid_from}" pattern="dd-MM-yyyy"/>
                                </td>
                                <td scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                    <fmt:formatDate value="${register.valid_to}" pattern="dd-MM-yyyy"/>
                                </td>
                                <td scope="row" class="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                    $${register.total_cost} 
                                </td>
                                <td scope="row" class="px-6 py-4 font-medium text-gray-900 ${register.status ? "text-green-500" : "text-blue-500"}">
                                    ${register.status ? "success" : "submit"}
                                </td>
                                <td scope="row" class="px-6 py-4 font-medium text-gray-900">
                                    <div class="flex items-center">
                                        <c:if test="${register.status==false}">
                                            <button  onclick="$('#button-confirm-delete').attr('href', '${pageContext.request.contextPath}/course/register/cancel/${register.id}')"  data-modal-toggle="popup-modal-delete" class="text-pink-500 hover:underline cursor-pointer text-medium">Cancel</button>
                                            <span>/</span>
                                            <a href="${pageContext.request.contextPath}/course/register/rollback/${register.id}" class="text-blue-500 hover:underline cursor-pointer">Rollback</a>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>    
            <%@include file="/views/component/pagination.jsp" %>
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
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to cancel course?</h3>
                <a id="button-confirm-delete" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete" type="button" class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No, cancel</button>
            </div>
        </div>
    </div>
</div>

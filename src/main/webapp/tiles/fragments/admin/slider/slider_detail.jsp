<%-- 
    Document   : slider_detail
    Created on : Jun 22, 2022, 8:52:43 PM
    Author     : congg
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="w-[500px] mx-auto mt-10" method="post" enctype="multipart/form-data">
    <c:if test="${error!=null}">
        <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
            <span class="font-medium">Error!</span> ${error}
        </div>
    </c:if>
    
    <div class="mb-6">
        <label for="title" class="block disabled mb-2 text-sm font-medium text-gray-900 ">Title</label>
        <input type="text" name="title" disabled id="title" value="${slider.title}" ${(sessionScope.user.checkFeature('SLIDER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" >
    </div>
    <div class="mb-3 flex justify-center">
        <img class="max-w-[100%] max-h-[500px]" src="${pageContext.request.contextPath}/images/slider/${slider.image}" id="image-preview"/>
    </div>
    
    <div class="mb-6">
        <label for="default-toggle" class="inline-flex relative items-center cursor-pointer">
            <input type="checkbox" name="status" disabled ${slider.status ? "checked" : ""} value="true" id="default-toggle" ${(sessionScope.user.checkFeature('SLIDER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="sr-only peer">
            <div class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
            <span class="ml-3 text-sm font-medium text-gray-900">Active</span>
        </label>
    </div>
    <div class="mb-6">
        <label for="backlink" class="block  mb-2 text-sm font-medium text-gray-900 ">Backlink</label>
        <input name="backlink" disabled type="text" id="backlink" value="${slider.backlink}" ${(sessionScope.user.checkFeature('SLIDER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" >
    </div>
    <div class="mb-6">
        <label for="notes" class="block disabled mb-2 text-sm font-medium text-gray-900 dark:text-gray-400">Notes</label>
        <textarea name="notes" disabled id="notes" rows="4"  ${(sessionScope.user.checkFeature('SLIDER_EDIT') || sessionScope.user.is_super()) ? "":"disabled"} class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Your message...">${slider.notes}</textarea>
    </div>
</form>

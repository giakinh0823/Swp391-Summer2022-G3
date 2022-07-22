<%-- 
    Document   : slider_add
    Created on : May 24, 2022, 1:26:28 AM
    Author     : lanh0
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
        <label for="title" class="block mb-2 text-sm font-medium text-gray-900 ">Title</label>
        <input type="title" name="title" id="title" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" >
    </div>
    <div class="mb-6">
        <label for="slider_image" class="block mb-2 text-sm font-medium text-gray-900">Upload image</label>
        <input name="image" class="block w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 cursor-pointer" id="slider_image" type="file" accept="image/*">
        <p class="mt-1 text-sm text-gray-500" id="file_input_help">SVG, PNG, JPG or GIF (MAX. 800x400px).</p>
    </div>
    <div class="mb-6">
        <label for="backlink" class="block mb-2 text-sm font-medium text-gray-900 ">Backlink</label>
        <input name="backlink" type="text" id="backlink" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" >
    </div>
    <div class="mb-6">
        <label for="notes" class="block mb-2 text-sm font-medium text-gray-900">Notes</label>
        <textarea name="notes" id="notes" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Your message..."></textarea>
    </div>
    <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Add</button>
</form>

<%-- 
    Document   : confirm_register
    Created on : Jun 1, 2022, 1:41:35 AM
    Author     : giaki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form class="w-[500px] mx-auto mt-10" method="POST" action="${pageContext.request.contextPath}/auth/enable">
    <div class="mb-6">
        <h1 class="text-2xl">Send email</h1>
        <h1 class="mt-3">Please enable account! Please click on the button. You will receive a new order link via email</h1>
    </div>
    <c:if test="${error!=null}">
        <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
            <span class="font-medium">Error!</span> ${error}
        </div>
    </c:if>
    <button type="submit" class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Send email</button>
</form>

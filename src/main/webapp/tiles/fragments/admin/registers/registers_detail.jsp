<%-- 
    Document   : registers_detail
    Created on : Jul 19, 2022, 4:11:04 AM
    Author     : giaki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="text-2xl font-medium flex items-center">
    <a href="${pageContext.request.contextPath}/admin/registrations"><svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M9.707 16.707a1 1 0 01-1.414 0l-6-6a1 1 0 010-1.414l6-6a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l4.293 4.293a1 1 0 010 1.414z" clip-rule="evenodd"></path></svg></a>
    Register
</h1>
<div class="relative overflow-x-auto shadow-md sm:rounded-lg mt-5">
    <table class="w-full text-sm text-left text-gray-500">
        <tbody>
            <tr class="border-b">
                <td class="px-6 py-4 font-medium text-gray-900">
                    Full name
                </td>
                <td class="px-6 py-4 font-medium text-gray-900">
                    ${register.user.first_name} ${register.user.last_name}
                </td>
            </tr>
            <tr class="border-b">
                <td class="px-6 py-4 font-medium text-gray-900">
                    Email
                </td>
                <td class="px-6 py-4 font-medium text-gray-900">
                    ${register.user.email}
                </td>
            </tr>
            <tr class="border-b">
                <td class="px-6 py-4 font-medium text-gray-900">
                    Phone
                </td>
                <td class="px-6 py-4 font-medium text-gray-900">
                    ${register.user.phone} 
                </td>
            </tr>
            <tr class="border-b">
                <td class="px-6 py-4 font-medium text-gray-900">
                    Course
                </td>
                <td class="px-6 py-4 font-medium text-gray-900">
                    ${register.subject.name} 
                </td>
            </tr>
            <tr class="border-b">
                <td class="px-6 py-4 font-medium text-gray-900">
                    From
                </td>
                <td class="px-6 py-4 font-medium text-gray-900">
                    ${register.valid_from} 
                </td>
            </tr>
            <tr class="border-b">
                <td class="px-6 py-4 font-medium text-gray-900">
                    To
                </td>
                <td class="px-6 py-4 font-medium text-gray-900">
                    ${register.valid_to} 
                </td>
            </tr>
            <tr class="border-b">
                <td class="px-6 py-4 font-medium text-gray-900">
                    Total cost
                </td>
                <td class="px-6 py-4 font-medium text-gray-900">
                    $${register.total_cost} 
                </td>
            </tr>
        </tbody>
    </table>
</div>    
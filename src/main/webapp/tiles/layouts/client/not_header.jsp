<%-- 
    Document   : footer
    Created on : May 20, 2022, 10:15:00 PM
    Author     : giaki
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="header shadow-sm bg-white">
    <nav class="bg-white border-gray-200 px-2 sm:px-4 py-2.5 rounded">
        <div class="flex flex-wrap justify-between items-center mx-auto">
            <div class="flex items-center">
                <a href="${pageContext.request.contextPath}/" class="flex items-center" style="color:#333">
                    <img src="https://flowbite.com/docs/images/logo.svg" class="mr-3 h-6 sm:h-9"
                         alt="Flowbite Logo" />
                    <span class="self-center text-xl font-semibold whitespace-nowrap">
                        <c:choose>
                            <c:when test="${subject!=null}">
                                ${subject.name}
                            </c:when>
                            <c:otherwise>
                                Coursera
                            </c:otherwise>
                        </c:choose>
                    </span>
                </a>
            </div>
            <div class="flex items-center md:order-2">
                <!-- avatar or button login signup  -->
                <c:choose>
                    <c:when test="${sessionScope.user!=null}">
                        <button type="button"
                                class="flex mr-3 text-sm bg-gray-800 rounded-full md:mr-0 focus:ring-4 focus:ring-gray-300"
                                id="user-menu-button" aria-expanded="false" type="button" data-dropdown-toggle="dropdown">
                            <span class="sr-only">Open user menu</span>
                            <c:choose>
                                <c:when test="${fn:contains(sessionScope.user.avatar, 'dummyimage.com')}">
                                    <img class="w-8 h-8 rounded-full" src="${sessionScope.user.avatar}"
                                         alt="user photo"/>
                                </c:when>
                                <c:when test="${sessionScope.user.avatar!=null}">
                                    <img class="w-8 h-8 rounded-full" src="${pageContext.request.contextPath}/images/user/${sessionScope.user.avatar}"
                                         alt="user photo"/>
                                </c:when>
                                <c:otherwise>
                                    <img class="w-8 h-8 rounded-full" src="https://flowbite.com/docs/images/people/profile-picture-3.jpg"
                                         alt="user photo"/>
                                </c:otherwise>
                            </c:choose>
                        </button> 

                        <!-- Dropdown menu -->
                        <div class="hidden z-50 my-4 text-base list-none bg-white rounded divide-y divide-gray-100 shadow "
                             id="dropdown">
                            <div class="py-3 px-4">
                                <span class="block text-sm text-gray-900">${sessionScope.user.last_name} ${sessionScope.user.first_name}</span>
                                <span class="block text-sm font-medium text-gray-500 truncate">${sessionScope.user.email}</span>
                            </div>
                            <ul class="py-1" aria-labelledby="dropdown">
                                <c:if test="${sessionScope.user!=null && (sessionScope.user.is_super() || sessionScope.user.is_staff())}">
                                    <li>
                                        <c:choose>
                                            <c:when test="${sessionScope.user.is_super()}">
                                                <a href="${pageContext.request.contextPath}/admin/dashboard"
                                                   class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Manage</a>
                                            </c:when>
                                            <c:when  test="${sessionScope.user.checkGroup('marketing')}">
                                                <a href="${pageContext.request.contextPath}/admin/dashboard"
                                                   class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Manage</a>
                                            </c:when>
                                            <c:when  test="${sessionScope.user.checkGroup('sale')}">
                                                <a href="${pageContext.request.contextPath}/admin/registrations"
                                                   class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Manage</a>
                                            </c:when>
                                            <c:when  test="${sessionScope.user.checkGroup('expert')}">
                                                <a href="${pageContext.request.contextPath}/admin/subject"
                                                   class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Manage</a>
                                            </c:when>
                                        </c:choose>
                                    </li>
                                </c:if>
                                <li>
                                    <a href="${pageContext.request.contextPath}/profile" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Profile</a>
                                </li>
                                <li>
                                    <a href="${pageContext.request.contextPath}/course/learning" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">My courses</a>
                                </li>
                                <li>
                                    <a href="${pageContext.request.contextPath}/course/register" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">My registrations</a>
                                </li>
                                <li>
                                    <a href="${pageContext.request.contextPath}/auth/logout" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Sign
                                        out</a>
                                </li>
                            </ul>
                        </div>
                    </c:when>
                </c:choose>

                <button data-collapse-toggle="mobile-menu-2" type="button"
                        class="inline-flex items-center p-2 ml-1 text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200"
                        aria-controls="mobile-menu-2" aria-expanded="false">
                    <span class="sr-only">Open main menu</span>
                    <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd"
                          d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z"
                          clip-rule="evenodd"></path>
                    </svg>
                    <svg class="hidden w-6 h-6" fill="currentColor" viewBox="0 0 20 20"
                         xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd"
                          d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                          clip-rule="evenodd"></path>
                    </svg>
                </button>
            </div>
        </div>
    </nav>
</div>
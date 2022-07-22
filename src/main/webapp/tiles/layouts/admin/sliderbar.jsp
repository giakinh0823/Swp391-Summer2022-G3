<%-- 
   Document   : sliderbar
   Created on : May 23, 2022, 10:48:57 AM
   Author     : giaki
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<aside class="hidden md:block min-w-[200px] xl:min-w-[256px] fixed top-0 left-0 z-[10]" aria-label="Sidebar" id="sliderar-admin">
    <div class="overflow-y-auto py-4 px-3 bg-gray-50 rounded h-screen">
        <ul class="space-y-2">
            <c:if test="${sessionScope.user.is_super() || sessionScope.user.checkFeature('DASHBOARD_READ')}">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/dashboard" class="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg hover:bg-gray-100">
                        <svg class="w-6 h-6 text-gray-500 transition duration-75 group-hover:text-gray-900"
                             fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                            <path d="M2 10a8 8 0 018-8v8h8a8 8 0 11-16 0z"></path>
                            <path d="M12 2.252A8.014 8.014 0 0117.748 8H12V2.252z"></path>
                        </svg>
                        <span class="ml-3">Dashboard</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${sessionScope.user.is_super() || sessionScope.user.checkFeature('REGISTRATIONS_READ') || sessionScope.user.checkFeature('REGISTRATIONS_CREATE')}">
                <li>
                    <button type="button"
                            class="flex items-center p-2 w-full text-base font-normal text-gray-900 rounded-lg transition duration-75 group hover:bg-gray-100"
                            aria-controls="dropdown-example" data-collapse-toggle="dropdown-sale">
                        <svg class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 group-hover:text-gray-900" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M4 4a2 2 0 00-2 2v1h16V6a2 2 0 00-2-2H4z"></path><path fill-rule="evenodd" d="M18 9H2v5a2 2 0 002 2h12a2 2 0 002-2V9zM4 13a1 1 0 011-1h1a1 1 0 110 2H5a1 1 0 01-1-1zm5-1a1 1 0 100 2h1a1 1 0 100-2H9z" clip-rule="evenodd"></path></svg>
                        <span class="flex-1 ml-3 text-left whitespace-nowrap"
                              sidebar-toggle-item>Sale</span>
                        <svg sidebar-toggle-item class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20"
                             xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                  d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                                  clip-rule="evenodd"></path>
                        </svg>
                    </button>
                    <ul id="dropdown-sale" class="hidden py-2 space-y-2">
                        <c:if test="${sessionScope.user.is_super() || sessionScope.user.checkFeature('REGISTRATIONS_READ')}">
                            <li>
                                <a href="${pageContext.request.contextPath}/admin/registrations"
                                   class="flex items-center p-2 pl-11 w-full text-base font-normal text-gray-900 rounded-lg transition duration-75 group hover:bg-gray-100">User registrations</a>
                            </li>
                        </c:if>
                    </ul>
                </li>
            </c:if>
            <c:if test="${sessionScope.user.is_super() || sessionScope.user.checkFeature('SETTING_READ')}">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/setting" class="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg hover:bg-gray-100">
                        <svg class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 group-hover:text-gray-900" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M11.49 3.17c-.38-1.56-2.6-1.56-2.98 0a1.532 1.532 0 01-2.286.948c-1.372-.836-2.942.734-2.106 2.106.54.886.061 2.042-.947 2.287-1.561.379-1.561 2.6 0 2.978a1.532 1.532 0 01.947 2.287c-.836 1.372.734 2.942 2.106 2.106a1.532 1.532 0 012.287.947c.379 1.561 2.6 1.561 2.978 0a1.533 1.533 0 012.287-.947c1.372.836 2.942-.734 2.106-2.106a1.533 1.533 0 01.947-2.287c1.561-.379 1.561-2.6 0-2.978a1.532 1.532 0 01-.947-2.287c.836-1.372-.734-2.942-2.106-2.106a1.532 1.532 0 01-2.287-.947zM10 13a3 3 0 100-6 3 3 0 000 6z" clip-rule="evenodd"></path></svg>
                        <span class="flex-1 ml-3 whitespace-nowrap">Setting</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${sessionScope.user.is_super() || sessionScope.user.checkFeature('USER_READ')}">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/user"
                       class="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg hover:bg-gray-100">
                        <svg class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 group-hover:text-gray-900"
                             fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd" d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z"
                                  clip-rule="evenodd"></path>
                        </svg>
                        <span class="flex-1 ml-3 whitespace-nowrap">User</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${sessionScope.user.is_super() || sessionScope.user.checkFeature('ROLE_READ')}">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/role"
                       class="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg hover:bg-gray-100">
                        <svg class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 group-hover:text-gray-900" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M13 6a3 3 0 11-6 0 3 3 0 016 0zM18 8a2 2 0 11-4 0 2 2 0 014 0zM14 15a4 4 0 00-8 0v3h8v-3zM6 8a2 2 0 11-4 0 2 2 0 014 0zM16 18v-3a5.972 5.972 0 00-.75-2.906A3.005 3.005 0 0119 15v3h-3zM4.75 12.094A5.973 5.973 0 004 15v3H1v-3a3 3 0 013.75-2.906z"></path></svg>                    
                        <span class="flex-1 ml-3 whitespace-nowrap">Role</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${sessionScope.user.is_super() 
                          || sessionScope.user.checkFeature('SUBJECT_READ') 
                          || sessionScope.user.checkFeature('LESSON_READ')}">
                  <li>
                      <button type="button"
                              class="flex items-center p-2 w-full text-base font-normal text-gray-900 rounded-lg transition duration-75 group hover:bg-gray-100"
                              aria-controls="dropdown-subject" data-collapse-toggle="dropdown-subject">
                          <svg class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 group-hover:text-gray-900" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M2 6a2 2 0 012-2h6a2 2 0 012 2v8a2 2 0 01-2 2H4a2 2 0 01-2-2V6zM14.553 7.106A1 1 0 0014 8v4a1 1 0 00.553.894l2 1A1 1 0 0018 13V7a1 1 0 00-1.447-.894l-2 1z"></path></svg>
                          <span class="flex-1 ml-3 text-left whitespace-nowrap"
                                sidebar-toggle-item>Subject</span>
                          <svg sidebar-toggle-item class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20"
                               xmlns="http://www.w3.org/2000/svg">
                              <path fill-rule="evenodd"
                                    d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
                                    clip-rule="evenodd"></path>
                          </svg>
                      </button>
                      <ul id="dropdown-subject" class="hidden py-2 space-y-2">
                          <c:if test="${sessionScope.user.is_super() || sessionScope.user.checkFeature('SUBJECT_READ')}">
                              <li>
                                  <a href="${pageContext.request.contextPath}/admin/subject"
                                     class="flex items-center p-2 pl-11 w-full text-base font-normal text-gray-900 rounded-lg transition duration-75 group hover:bg-gray-100">Subject list</a>
                              </li>
                          </c:if>
                          <c:if test="${sessionScope.user.is_super() || sessionScope.user.checkFeature('SUBJECT_CREATE')}">
                              <li>
                                  <a href="${pageContext.request.contextPath}/admin/subject/create"
                                     class="flex items-center p-2 pl-11 w-full text-base font-normal text-gray-900 rounded-lg transition duration-75 group hover:bg-gray-100">New subject</a>
                              </li>
                          </c:if>
                      </ul>
                  </li>
            </c:if>
            <c:if test="${sessionScope.user.is_super() || sessionScope.user.checkFeature('SLIDER_READ')}">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/slider"
                       class="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg hover:bg-gray-100">
                        <svg class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 group-hover:text-gray-900" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M5 4a1 1 0 00-2 0v7.268a2 2 0 000 3.464V16a1 1 0 102 0v-1.268a2 2 0 000-3.464V4zM11 4a1 1 0 10-2 0v1.268a2 2 0 000 3.464V16a1 1 0 102 0V8.732a2 2 0 000-3.464V4zM16 3a1 1 0 011 1v7.268a2 2 0 010 3.464V16a1 1 0 11-2 0v-1.268a2 2 0 010-3.464V4a1 1 0 011-1z"></path></svg>                    
                        <span class="flex-1 ml-3 whitespace-nowrap">Slider</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${sessionScope.user.is_super() || sessionScope.user.checkFeature('QUIZZES_READ')}">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/quizzes" class="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg hover:bg-gray-100">
                        <svg class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 group-hover:text-gray-900" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M9 2a2 2 0 00-2 2v8a2 2 0 002 2h6a2 2 0 002-2V6.414A2 2 0 0016.414 5L14 2.586A2 2 0 0012.586 2H9z"></path><path d="M3 8a2 2 0 012-2v10h8a2 2 0 01-2 2H5a2 2 0 01-2-2V8z"></path></svg> 
                        <span class="flex-1 ml-3 whitespace-nowrap">Quizzes</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${sessionScope.user.is_super() || sessionScope.user.checkFeature('QUESTION_READ')}">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/question" class="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg hover:bg-gray-100">
                        <svg class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 group-hover:text-gray-900" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M9 2a1 1 0 000 2h2a1 1 0 100-2H9z"></path><path fill-rule="evenodd" d="M4 5a2 2 0 012-2 3 3 0 003 3h2a3 3 0 003-3 2 2 0 012 2v11a2 2 0 01-2 2H6a2 2 0 01-2-2V5zm3 4a1 1 0 000 2h.01a1 1 0 100-2H7zm3 0a1 1 0 000 2h3a1 1 0 100-2h-3zm-3 4a1 1 0 100 2h.01a1 1 0 100-2H7zm3 0a1 1 0 100 2h3a1 1 0 100-2h-3z" clip-rule="evenodd"></path></svg>                        <span class="flex-1 ml-3 whitespace-nowrap">Question</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${sessionScope.user.is_super() || sessionScope.user.checkFeature('POST_READ')}">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/post" class="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg hover:bg-gray-100">
                        <svg class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75 group-hover:text-gray-900" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M5 3a1 1 0 000 2c5.523 0 10 4.477 10 10a1 1 0 102 0C17 8.373 11.627 3 5 3z"></path><path d="M4 9a1 1 0 011-1 7 7 0 017 7 1 1 0 11-2 0 5 5 0 00-5-5 1 1 0 01-1-1zM3 15a2 2 0 114 0 2 2 0 01-4 0z"></path></svg>                    
                        <span class="flex-1 ml-3 whitespace-nowrap">Post</span>
                    </a>
                </li>
            </c:if>
            <li>
                <a href="${pageContext.request.contextPath}" class="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg hover:bg-gray-100">
                    <svg class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"></path></svg>
                    <span class="flex-1 ml-3 whitespace-nowrap">Home</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/auth/logout" class="flex items-center p-2 text-base font-normal text-gray-900 rounded-lg hover:bg-gray-100">
                    <svg class="flex-shrink-0 w-6 h-6 text-gray-500 transition duration-75"
                         fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd"
                              d="M3 3a1 1 0 00-1 1v12a1 1 0 102 0V4a1 1 0 00-1-1zm10.293 9.293a1 1 0 001.414 1.414l3-3a1 1 0 000-1.414l-3-3a1 1 0 10-1.414 1.414L14.586 9H7a1 1 0 100 2h7.586l-1.293 1.293z"
                              clip-rule="evenodd"></path>
                    </svg>
                    <span class="flex-1 ml-3 whitespace-nowrap">Logout</span>
                </a>
            </li>
        </ul>
    </div>
</aside>
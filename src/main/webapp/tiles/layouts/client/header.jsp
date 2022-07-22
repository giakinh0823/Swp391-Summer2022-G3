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
                <a href="${pageContext.request.contextPath}/" class="flex items-center">
                    <img src="https://flowbite.com/docs/images/logo.svg" class="mr-3 h-6 sm:h-9"
                         alt="Flowbite Logo" />
                    <span class="self-center text-xl font-semibold whitespace-nowrap">Coursera</span>
                </a>
                <div class="ml-3 relative hidden" class="category-container">
                    <button type="button" class="category-button" id="category-button">Categories</button>
                    <div class="hidden max-h-[50vh] overflow-y-auto absolute top-[130%] left-[-40%] category-content shadow-lg bg-white z-[1000]" id="category-menu">
                        <ul class="category-list w-[250px] py-3">
                            <c:forEach items="${categories}" var="category">
                                <li class="category-item hover:bg-gray-100">
                                    <a href="${pageContext.request.contextPath}/course/category/${category.id}"
                                       class="category-link block py-3 px-8 w-full">
                                        ${category.value}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <a class="ml-4 block text-md" href="${pageContext.request.contextPath}/course">
                    Course
                </a>
                <a class="ml-4 block text-md" href="${pageContext.request.contextPath}/blog">
                    Blog
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
                                    <a href="${pageContext.request.contextPath}/course/register" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">My registration</a>
                                </li>
                                <li>
                                    <a href="${pageContext.request.contextPath}/auth/logout" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Sign
                                        out</a>
                                </li>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <buton type="button" data-modal-toggle="signup-modal"
                               class="py-2.5 px-5 mr-2 mb-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200">Signup</buton>
                        <buton type="button" data-modal-toggle="authentication-modal"
                               class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-2 mb-2 focus:outline-none">Login</buton>
                        </c:otherwise>
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
            <div class="hidden justify-between items-center w-full md:flex md:w-auto md:order-1" id="mobile-menu-2"
                 style="flex: 1">
                <!-- input sreach -->
                <form class="flex items-center px-5 max-w-lg" action="${pageContext.request.contextPath}/course" style="flex: 1">
                    <label for="simple-search" class="sr-only">Search</label>
                    <div class="relative w-full">
                        <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                            <svg class="w-5 h-5 text-gray-500 " fill="currentColor" viewBox="0 0 20 20"
                                 xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                  d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                                  clip-rule="evenodd"></path>
                            </svg>
                        </div>
                        <input type="text" id="search" name="search"
                               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5 "
                               placeholder="Search" required>
                    </div>
                    <button type="submit"
                            class="p-2.5 ml-2 text-sm font-medium text-white bg-blue-700 rounded-lg border border-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300"><svg
                            class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                            xmlns="http://www.w3.org/2000/svg">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                        </svg></button>
                </form>
            </div>
        </div>
    </nav>
</div>



<div id="authentication-modal" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 w-full md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-md h-full md:h-auto">
        <!-- Modal content -->
        <div class="relative bg-white rounded-lg shadow">
            <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center " data-modal-toggle="authentication-modal">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
            </button>
            <div class="py-6 px-6 lg:px-8">
                <form class="w-full" method="POST" id="pop-up-login" action="${pageContext.request.contextPath}/auth/api/login">
                    <div class="mb-6">
                        <h1 class="text-center text-2xl">Hello Again!</h1>
                        <h1 class="text-center">Wellcome back you've been missed!</h1>
                    </div>
                    <div id="pop-up-login-alert" class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg hidden" role="alert">
                        <span class="font-medium">Error!</span> <span class="ml-1" id="pop-up-login-message"></span>
                    </div>
                    <div class="hidden flex justify-center mb-3" id="spinner-login">
                        <svg role="status" class="w-8 h-8 mr-2 text-gray-200 animate-spin  fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
                        <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
                        </svg>
                    </div>
                    <div class="mb-6">
                        <label for="username" class="block mb-2 text-sm font-medium text-gray-900">Username or email</label>
                        <input type="text" id="username"
                               name="username"
                               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                               placeholder="email">
                    </div>
                    <div class="mb-6">
                        <label for="password" class="block mb-2 text-sm font-medium text-gray-900">Your password</label>
                        <input type="password" id="password"
                               name="password"
                               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                               placeholder="password">
                    </div>

                    <button type="submit"
                            class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full px-5 py-2.5 text-center">Sign In</button>
                    <div class="container flex mt-3">
                        <span class="mx-auto flex justify-between">
                            <p>Not a member? <a href="${pageContext.request.contextPath}/auth/signup" class="text-blue-500">Sign up</a></p>
                        </span>

                        <span class="mx-auto flex justify-between">
                            <a href="${pageContext.request.contextPath}/auth/reset" class="text-blue-500">Forgot password</a>
                        </span>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div> 


<div id="signup-modal" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 w-full md:inset-0 h-modal min-h-full">
    <div class="relative p-4 w-full max-w-lg h-full 2xl:h-auto">
        <!-- Modal content -->
        <div class="pb-10 pt-10">
            <div class="relative bg-white rounded-lg shadow">
                <button type="button" class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center " data-modal-toggle="signup-modal">
                    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
                </button>
                <div class="py-6 px-6 lg:px-8">
                    <form class="min-w-full" method="post" action="${pageContext.request.contextPath}/auth/api/signup" id="pop-up-signup" >
                        <div class="mb-6">
                            <h1 class="text-left text-2xl font-medium">Sign up</h1>
                            <h1 class="text-left mt-2">Create account to start using coursera</h1>
                        </div>
                        <div id="pop-up-signup-alert" class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg hidden" role="alert">
                            <span class="font-medium">Error!</span> <span class="ml-1" id="pop-up-signup-message"></span>
                        </div>
                        <div class="hidden flex justify-center mb-3" id="spinner-signup">
                            <svg role="status" class="w-8 h-8 mr-2 text-gray-200 animate-spin  fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
                            <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
                            </svg>
                        </div>
                        <div class="grid gap-6 mb-6 lg:grid-cols-2">
                            <div>
                                <label for="first_name"
                                       class="block mb-2 text-sm font-medium text-gray-900">First name</label>
                                <input type="text" name="first_name"
                                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                                       placeholder="John">
                            </div>
                            <div>
                                <label for="last_name" class="block mb-2 text-sm font-medium text-gray-900">Last
                                    name</label>
                                <input type="text" name="last_name"
                                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                                       placeholder="Doe">
                            </div>
                        </div>

                        <div class="mb-6">
                            <h1 class="block mb-2 text-sm font-medium text-gray-900">Gender</h1> 
                            <div class="flex items-center">
                                <div class="flex items-center">
                                    <input id="male" checked type="radio" value="male" name="gender" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500">
                                    <label for="male" class="ml-2 text-sm font-medium text-gray-900">Male</label>
                                </div>
                                <div class="flex items-center ml-3">
                                    <input id="female"  type="radio" value="female" name="gender" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500">
                                    <label for="female" class="ml-2 text-sm font-medium text-gray-900">Female</label>
                                </div>
                            </div>
                        </div>
                        <div class="mb-6">
                            <label for="phone" class="block mb-2 text-sm font-medium text-gray-900 ">Phone number</label>
                            <input type="text" id="phone" name="phone"
                                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                                   placeholder="Your phone number">
                        </div>

                        <div class="mb-6">
                            <label for="username-register" class="block mb-2 text-sm font-medium text-gray-900 ">Username</label>
                            <input type="text" id="username-register" name="username"
                                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                                   placeholder="" required>
                        </div>

                        <div class="mb-6">
                            <label for="email-register" class="block mb-2 text-sm font-medium text-gray-900 ">Email</label>
                            <input type="email" id="email-register" name="email"
                                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                                   placeholder="coursera@gmail.com" >
                        </div>
                        <div class="mb-6">
                            <label for="password-register" class="block mb-2 text-sm font-medium text-gray-900">Password</label>
                            <input type="password" id="password-register" name="password"
                                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                                   placeholder="password" >
                        </div>
                        <div class="mb-6">
                            <label for="re-password" class="block mb-2 text-sm font-medium text-gray-900">Re-enter password</label>
                            <input type="password" id="re-password" name="re-password"
                                   class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                                   placeholder="re-password" >
                        </div>
                        <button type="submit"
                                class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center w-full">Sign up</button>
                        <div class="flex justify-center mt-5">
                            <label for="terms" class="text-center mx-auto font-medium text-gray-900">Already have an account? 
                                <a href="${pageContext.request.contextPath}/auth/login" class="text-blue-600 hover:underline">Login</a>
                            </label>
                        </div>    
                    </form>
                </div>
            </div>
        </div>
    </div>
</div> 
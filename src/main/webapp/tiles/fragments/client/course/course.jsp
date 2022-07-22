<%-- 
    Document   : course
    Created on : Jun 22, 2022, 8:42:49 PM
    Author     : lanh0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<div class="container justify-items-center content-center mx-auto mt-10">
    <div class="flex items-center mt-6 border-b pb-6">
        <div class=" items-center pr-5">
            <button onclick="dropdownfilter()"type="button" class="flex items-center py-2.5 px-5 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10">
                <svg class="font-normal w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.414a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.293A1 1 0 013 6.586V4z"></path></svg>
                Filter
            </button>
        </div>
        <div class=" justify-start items-center pr-5">
            <button id="dropdownDefault" data-dropdown-toggle="dropdownSort" class="flex items-center py-2.5 px-5 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10" type="button"> Sort updated <svg class="w-4 h-4 ml-2" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7"></path></svg></button>
            <div id="dropdownSort" class="z-10 hidden bg-white divide-y divide-gray-100 rounded shadow w-44 " data-popper-placement="bottom" style="position: absolute; inset: 0px auto auto 0px; margin: 0px; transform: translate(903px, 735px);">
                <ul class="py-1 text-sm text-gray-700 " aria-labelledby="dropdownDefault">
                    <li>
                        <p onclick="sortHandleColumn('updated_ASC')" class="cursor-pointer block px-4 py-2 hover:bg-gray-100 ">Latest Date</p>
                    </li>
                    <li>
                        <p onclick="sortHandleColumn('updated_DESC')"  class="cursor-pointer block px-4 py-2 hover:bg-gray-100 ">Oldest Date</p>
                    </li>
                </ul>
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/course" class="flex items-center py-2.5 px-5 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10">
            Clear
        </a>

        <div class="ml-auto items-center pr-5">
            <span class="text-blue-600">${page.pageIndex}</span> / <span >${page.count}</span>
        </div>

        <div class="items-center">
            <a data="${page.prev}" class="page-link mr-3 mb-3 md:mb-0 text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2.5 text-center inline-flex items-center " type="button">
                <svg class="w-4 h-4 mr-2" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z" clip-rule="evenodd"></path></svg></a>
            <a data="${page.next}" class="page-link mb-3 md:mb-0 text-white bg-blue-700 hover:bg-blue-800 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2.5 text-center inline-flex items-center " type="button">
                <svg class="w-4 h-4 ml-2" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd"></path></svg></a>
        </div>
    </div>

    <div class="grid grid-cols-12 gap-10 mt-10">
        <div id="box-filter" class="col-span-3 px-3">
            <form method="GET" class="w-full">
                <div class="border-b pb-5">
                    <div class="mb-3">
                        <h2 class="mb-3 font-bold mb-3">Category</h2>
                        <div class="w-full mb-2">
                            <div class="relative w-full">
                                <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                                    <svg class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>                            
                                </div>
                                <input type="text" oninput="searchItemFilter('category', this)" id="search-category" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2 " placeholder="Search category">
                            </div>
                        </div>
                        <div class="mb-5 flex flex-wrap max-w-full space-x-1 px-2">
                            <c:forEach items="${categories}" var="item">
                                <c:if test="${pageable.checkFilters('category', item.id)}">
                                    <div class="flex items-center">
                                        <span class="text-sm font-medium text-gray-900">${item.value}</span>
                                        <button onclick="removeFilter('category',${item.id})" class="ml-1 text-gray-700 hover:text-gray-900" type="button">
                                            <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"></path></svg>                                    
                                        </button>
                                    </div>
                                </c:if>
                                <c:forEach items="${item.childs}" var="child">
                                    <c:if test="${pageable.checkFilters('category', child.id)}">
                                        <div class="flex items-center">
                                            <span class="text-sm font-medium text-gray-900">${child.value}</span>
                                            <button onclick="removeFilter('category',${child.id})" class="ml-1 text-gray-700 hover:text-gray-900" type="button">
                                                <svg class="w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clip-rule="evenodd"></path></svg>                                        
                                            </button>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </div>
                        <div class="min-h-[180px] max-h-[250px] overflow-y-auto px-2">
                            <c:forEach items="${categories}" var="item">
                                <div class="flex items-center mb-2">
                                    <input type="checkbox" value="${item.id}" name="category" id="category-${item.id}"  ${pageable.checkFilters('category', item.id) ? "checked": ""} class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                                        <label for="category-${item.id}" class="item-category ml-2 text-sm font-medium text-gray-900">${item.value}</label>
                                </div>
                                <c:forEach items="${item.childs}" var="child">
                                    <div class="ml-3 flex items-center mb-2">
                                        <input parent="${item.id}" type="checkbox" value="${child.id}" name="category" id="category-${child.id}"  ${pageable.checkFilters('category', child.id) ? "checked": ""} class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                                            <label for="category-${child.id}" class="item-category ml-2 text-sm font-medium text-gray-900">${child.value}</label>
                                    </div>
                                </c:forEach>
                            </c:forEach>
                        </div>
                    </div>
                </div>

                <div class="border-b pb-5">
                    <h2 class="mb-3 font-bold mb-3">Featured</h2>
                    <div class="flex items-center mb-2">
                        <input  ${pageable.checkFilters('featured', 'true') ? "checked": ""} type="checkbox" value="true" name="featured" id="featured-show"  class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="featured-show" class="ml-2 text-sm font-medium text-gray-900">Featured</label>
                    </div>
                    <div class="flex items-center mb-2">
                        <input ${pageable.checkFilters('featured', 'false') ? "checked": ""} type="checkbox" value="false" name="featured" id="featured-hidden" class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2">
                            <label for="featured-hidden" class="ml-2 text-sm font-medium text-gray-900">Not Featured</label>
                    </div>
                </div>

                <div class="border-b pb-5">
                    <div class="mb-3">
                        <h2 class="mb-3 font-bold mb-3">Author</h2>
                        <div class="w-full mb-2">
                            <div class="relative w-full">
                                <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                                    <svg class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>                            
                                </div>
                                <input type="text" oninput="searchItemFilter('author', this)" id="search-author" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2 " placeholder="Search author">
                            </div>
                        </div>
                        <div class="min-h-[20px] max-h-[200px] overflow-y-auto px-2">
                            <c:forEach items="${authors}" var="item">
                                <div class="flex items-center mb-2">
                                    <input type="checkbox" value="${item.id}" name="author" id="author-${item.id}"  ${pageable.checkFilters('author', item.id) ? "checked": ""} class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2"/>
                                    <label for="author-${item.id}" class="item-author ml-2 text-sm font-medium text-gray-900">${item.first_name} ${item.last_name}</label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <button type="submit" class="w-full bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-3 py-2 mt-2 focus:outline-none text-white">Filter</button>
            </form>
        </div>
        <div id="box-course-list" class="col-span-9">
            <c:forEach items="${courses}" var="item">
                <div class="relative box-price-hover">
                    <a class="flex cursor-pointer gap-3 border-b pb-5  mb-10" href="${pageContext.request.contextPath}/course/${item.id}" onmouseout="$('#price-package-${item.id}').addClass('hidden')" onmousemove="$('#price-package-${item.id}').removeClass('hidden')">
                        <div class="w-[250px] h-[150px] flex justify-center">
                            <c:choose>
                                <c:when test="${fn:containsIgnoreCase(item.image, 'dummyimage.com')}">
                                    <img class="${item.image!=null ? "":"hidden"} w-full" alt="${item.name}" src="${item.image}" id="image-preview"/>
                                </c:when>
                                <c:otherwise>
                                    <img class="${item.image!=null ? "":"hidden"} w-full" alt="${item.name}" src="${pageContext.request.contextPath}/images/subject/${item.image}" id="image-preview"/>
                                </c:otherwise>
                            </c:choose>  
                        </div>
                        <div class="flex flex-1">
                            <div class="flex flex-1 flex-col px-3">
                                <div class="mb-1 flex items-center justify-between"> 
                                    <h2 class="font-bold text-xl" style="max-width: full; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 1; -webkit-box-orient: vertical;"> ${item.name} </h2>
                                </div>
                                <div class="mb-1 text-gray-800" style="max-width: full; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;"> 
                                    ${item.description}
                                </div>
                                <div class="font-normal text-sm text-gray-600 mb-1">${item.user.getFirst_name()} ${item.user.getLast_name()}</div>
                                <div class="font-normal text-sm text-gray-600 mb-1"><span>${item.getTotalLesson()} lesson</span> </div>
                                <div class="${item.featured ?"" : "hidden"} bg-yellow-400 max-w-fit px-2 font-medium mt-auto mb-1 text-sm rounded-sm">Featured</div>
                            </div>
                            <div class="${item.getLowPrice()!=null ? "" : "hidden"}">
                                <p class="text-md text-right font-medium">$ ${item.getLowPrice()!=null ?item.getLowPrice().sale_price: ""}</p>
                                <p class="text-sm text-right line-through">$ ${item.getLowPrice()!=null ?item.getLowPrice().list_price: ""}</p>
                            </div>
                        </div>
                    </a>
                    <div id="price-package-${item.id}" onmouseout="$('#price-package-${item.id}').addClass('hidden')" onmousemove="$('#price-package-${item.id}').removeClass('hidden')" class="hidden item-price-package absolute bottom-[100%] left-[50%] translate-x-[-50%] rounded-lg shadow-xl px-6 py-4 bg-white">
                        <table class="w-full text-sm text-center text-gray-900">
                            <tbody>
                                <c:forEach items="${item.packegePrices}" var="price">
                                    <tr class="bg-white">
                                        <td class="py-2 font-medium">
                                            <svg class="w-4 h-4 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4M7.835 4.697a3.42 3.42 0 001.946-.806 3.42 3.42 0 014.438 0 3.42 3.42 0 001.946.806 3.42 3.42 0 013.138 3.138 3.42 3.42 0 00.806 1.946 3.42 3.42 0 010 4.438 3.42 3.42 0 00-.806 1.946 3.42 3.42 0 01-3.138 3.138 3.42 3.42 0 00-1.946.806 3.42 3.42 0 01-4.438 0 3.42 3.42 0 00-1.946-.806 3.42 3.42 0 01-3.138-3.138 3.42 3.42 0 00-.806-1.946 3.42 3.42 0 010-4.438 3.42 3.42 0 00.806-1.946 3.42 3.42 0 013.138-3.138z"></path></svg>
                                        </td>
                                        <td class="py-2 font-medium text-left">
                                            ${price.getName()}
                                        </td>
                                        <td class="px-2 py-2 text-red-600 font-medium line-through">
                                            ${price.getList_price()} $
                                        </td>
                                        <td class="px-2 py-2 text-green-600 font-medium">
                                            ${price.getSale_price()} $
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <c:if test="${user!=null && !user.checkRegistrationPending(item.id)}">
                            <div class="flex justify-center w-full mt-2">
                                <button onclick="openModalCheckout(${item.id})" data-modal-toggle="modal-checkout-course" class="block text-center text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 focus:outline-none w-full">Register</button>
                            </div>
                        </c:if>
                        <c:if test="${user==null}">
                            <div class="flex justify-center w-full mt-2">
                                <button onclick="openModalCheckout(${item.id})" data-modal-toggle="modal-checkout-course" class="block text-center text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 focus:outline-none w-full">Register</button>
                            </div>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${courses!=null && courses.size()>0}">
                <%@include file="/views/component/pagination.jsp" %>
            </c:if>
            <c:if test="${courses==null || courses.size()<=0}">
                <p class="text-center text-2xl mt-10">Not found course</p>
            </c:if>
        </div>
    </div>
</div>


<!-- Extra Large Modal -->
<div id="modal-checkout-course" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 w-full md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-7xl h-full">
        <!-- Modal content -->
        <div class="relative bg-white rounded-lg shadow">
            <!-- Modal header -->
            <div class="flex justify-between items-center rounded-t">
                <button type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center" data-modal-toggle="modal-checkout-course">
                    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
                </button>
            </div>
            <!-- Modal body -->
            <div class="p-6 space-y-6">
                <form class="mt-6" method="POST" id="form-checkout" action="${pageContext.request.contextPath}/course/checkout">
                    <input type="hidden" name="subjectid"  id="checkout-subject"/>
                    <div class="mb-10 mt-10">
                        <h1 class="text-center text-4xl">Checkout</h1>
                    </div>
                    <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg hidden" role="alert" id="error-checkout">
                        <span class="font-medium">Error!</span> <span id="error-message-checkout"></span>
                    </div>
                    <div class="grid gap-6 mb-6 lg:grid-cols-2 h-full ">
                        <div class="col-span-1">
                            <div class="grid gap-6 lg:grid-cols-2">
                                <div class="${user!=null ? "hidden": ""}">
                                    <label for="first_name" class="block mb-2 text-sm font-medium text-gray-900">First
                                        name</label>
                                    <input type="text" id="first_name" name="first_name" value="${user.first_name}"
                                           class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                                           placeholder="firstname">
                                </div>
                                <div class="${user!=null ? "hidden": ""}">
                                    <label for="last_name" class="block mb-2 text-sm font-medium text-gray-900 ">Last
                                        name</label>
                                    <input type="text" id="last_name" name="last_name" value="${user.last_name}"
                                           class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                                           placeholder="lastname">
                                </div>
                                <div class="${(user!=null && user.phone!=null) ? "hidden": ""}">
                                    <label for="phone" class="block mb-2 text-sm font-medium text-gray-900 ">Phone
                                        number</label>
                                    <input type="tel" id="phone" name="phone" value="${user.phone}"
                                           class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 "
                                           placeholder="phone">
                                </div>
                                <div class="${(user!=null && user.gender!=null) ? "hidden": ""}">
                                    <label for="gender" class="block mb-2 text-sm font-medium text-gray-900 mb-3">Gender</label>
                                    <div class="flex items-center">
                                        <div class="flex items-center mr-4">
                                            <input id="male" type="radio" value="male" ${user.gender ? "checked" : ""} name="gender"
                                                   class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 ">
                                                <label for="male" class="ml-2 text-sm font-medium text-gray-900 ">Male</label>
                                        </div>
                                        <div class="flex items-center mr-4">
                                            <input id="female" type="radio" value="female" ${user.gender ? "" : "checked"} name="gender"
                                                   class=" w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 ">
                                                <label for="female" class="ml-2 text-sm font-medium text-gray-900 ">Female</label>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="mb-6 ${(user!=null && user.email!=null) ? "hidden": ""}">
                                <label for="email" class="block mb-2 text-sm font-medium text-gray-900">Email</label>
                                <input type="email" id="email" name="email" value="${user.email}"
                                       class=" bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
                                       placeholder="email">
                            </div>

                            <div class=" bg-white rounded-lg border border-gray-200 p-5">
                                <a id="checkout-subject-image-href">
                                    <img class="w-full h-[200px] object-cover" alt="" id="checkout-subject-image"/>
                                </a>
                                <div class="p-2">
                                    <a id="checkout-subject-title-href">
                                        <h5 class="mb-2 text-xl font-medium tracking-tight text-gray-900 " id="checkout-subject-title"></h5>
                                    </a>
                                </div>
                                <div id="checkout-subject-description" style="max-width: full; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 3; -webkit-box-orient: vertical;">${subject.description}</div>
                            </div>
                        </div>
                        <div class="col-span-1 h-500px ">
                            <div class="px-6 py-5 mb-5 border border-gray-200">
                                <table class="w-full text-sm text-center text-gray-900">
                                    <tbody id="checkout-body-price">

                                    </tbody>
                                </table>
                                <button type="submit" class="mt-10 text-center px-5 py-2.5 bg-yellow-400 block font-medium cursor-pointer w-full">
                                    Register now 
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
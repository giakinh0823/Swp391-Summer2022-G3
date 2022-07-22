<%-- 
    Document   : course_detail
    Created on : Jun 23, 2022, 8:49:35 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<div class="bg-[#1c1d1f] px-6 py-10">
    <div class="container mx-auto">
        <div class="flex flex-col items-start text-white flex-1"> 
            <h2 class="text-4xl font-medium">
                ${subject.name}
            </h2>
            <a href="${pageContext.request.contextPath}/course?category=${subject.category.id}" class="mt-2 text-pink-200 underline cursor-pointer">
                ${subject.category.value}
            </a>
            <p class="mt-2">${subject.description}</p>
            <div class="mt-4 flex items-center gap-3">
                <img class="w-12 h-12 p-1 rounded-full ring-2 ring-gray-300 dark:ring-gray-500" src="${pageContext.request.contextPath}/images/user/${subject.user.avatar}" alt="avatar user">
                    <p class="">${subject.user.first_name} ${subject.user.last_name}</p>
            </div>
            <div class="flex items-center mt-10 ">
                <c:if test="${user==null || !user.checkRegistration(subject.id)}">
                    <a href="${pageContext.request.contextPath}/course/${subject.id}/checkout" class="px-5 py-3 bg-yellow-400 block font-medium cursor-pointer">
                        Register
                    </a>
                    <p class="text-lg ml-3">Include your learning program</p>
                </c:if>
                <c:if test="${user!=null && user.checkRegistration(subject.id)}">
                    <a href="${pageContext.request.contextPath}/course/learning/${subject.id}" class="px-5 py-3 bg-yellow-400 block font-medium cursor-pointer">
                        Go to course
                    </a>
                </c:if>
            </div>
            <div class="mt-6 flex items-center gap-3">
                <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M6.267 3.455a3.066 3.066 0 001.745-.723 3.066 3.066 0 013.976 0 3.066 3.066 0 001.745.723 3.066 3.066 0 012.812 2.812c.051.643.304 1.254.723 1.745a3.066 3.066 0 010 3.976 3.066 3.066 0 00-.723 1.745 3.066 3.066 0 01-2.812 2.812 3.066 3.066 0 00-1.745.723 3.066 3.066 0 01-3.976 0 3.066 3.066 0 00-1.745-.723 3.066 3.066 0 01-2.812-2.812 3.066 3.066 0 00-.723-1.745 3.066 3.066 0 010-3.976 3.066 3.066 0 00.723-1.745 3.066 3.066 0 012.812-2.812zm7.44 5.252a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path></svg>
                <p>Last updated <fmt:formatDate pattern="dd-MM-yyyy" value="${subject.updated_at}"/></p>
                <p class="flex">
                    <span>
                        <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M10.394 2.08a1 1 0 00-.788 0l-7 3a1 1 0 000 1.84L5.25 8.051a.999.999 0 01.356-.257l4-1.714a1 1 0 11.788 1.838L7.667 9.088l1.94.831a1 1 0 00.787 0l7-3a1 1 0 000-1.838l-7-3zM3.31 9.397L5 10.12v4.102a8.969 8.969 0 00-1.05-.174 1 1 0 01-.89-.89 11.115 11.115 0 01.25-3.762zM9.3 16.573A9.026 9.026 0 007 14.935v-3.957l1.818.78a3 3 0 002.364 0l5.508-2.361a11.026 11.026 0 01.25 3.762 1 1 0 01-.89.89 8.968 8.968 0 00-5.35 2.524 1 1 0 01-1.4 0zM6 18a1 1 0 001-1v-2.065a8.935 8.935 0 00-2-.712V17a1 1 0 001 1z"></path></svg>
                    </span>
                    <span class="ml-2">${subject.getTotalLesson()} Lectures</span>
                </p>
            </div>
        </div>
    </div>
</div>
<div class="container mx-auto mt-10">
    <div class="grid grid-cols-12 gap-10">
        <div class="col-span-9">
            <div class="px-6 py-5 mb-5 border border-gray-200">
                <table class="w-full text-sm text-center text-gray-900">
                    <tbody>
                        <c:forEach items="${subject.packegePrices}" var="price">
                            <tr class="bg-white">
                                <td class="py-2 font-medium">
                                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4M7.835 4.697a3.42 3.42 0 001.946-.806 3.42 3.42 0 014.438 0 3.42 3.42 0 001.946.806 3.42 3.42 0 013.138 3.138 3.42 3.42 0 00.806 1.946 3.42 3.42 0 010 4.438 3.42 3.42 0 00-.806 1.946 3.42 3.42 0 01-3.138 3.138 3.42 3.42 0 00-1.946.806 3.42 3.42 0 01-4.438 0 3.42 3.42 0 00-1.946-.806 3.42 3.42 0 01-3.138-3.138 3.42 3.42 0 00-.806-1.946 3.42 3.42 0 010-4.438 3.42 3.42 0 00.806-1.946 3.42 3.42 0 013.138-3.138z"></path></svg>
                                </td>
                                <td class="py-2 font-medium text-left">
                                    ${price.getName()}
                                </td>
                                <td class="px-2 py-2 font-medium line-through">
                                    ${price.getList_price()} $
                                </td>
                                <td class="px-2 py-2 font-medium">
                                    ${price.getSale_price()} $
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="">
                <c:forEach items="${subject.lessons}" var="lesson">
                    <div class="border border-gray-200">
                        <div class="px-5 py-3 flex items-center justify-between border-b border-gray-200 bg-gray-100">
                            <p class="font-medium">${lesson.name}</p>
                            <button type="button" onclick="showLesson(this, 'item-content-lesson-${lesson.id}')">
                                <svg class="w-5 h-5 text-gray-600" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 13l-3 3m0 0l-3-3m3 3V8m0 13a9 9 0 110-18 9 9 0 010 18z"></path></svg>
                            </button>
                        </div>
                        <div id="item-content-lesson-${lesson.id}" >
                            <c:forEach items="${lesson.lessons}" var="child">
                                <div class="flex items-center justify-between py-2 px-6">
                                    <a class="text-sm cursor-pointer flex items-center">
                                        <p class="${(child.video!=null && child.video!='') ? "" : "hidden"}">
                                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14.752 11.168l-3.197-2.132A1 1 0 0010 9.87v4.263a1 1 0 001.555.832l3.197-2.132a1 1 0 000-1.664z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>                                        
                                        </p>
                                        <p class="${(child.content!=null && child.content!='') ? "" : "hidden"}">
                                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path></svg>
                                        </p>
                                        <p class="${fn:contains(child.type.value, 'Quiz')==true ? "" : "hidden"}">
                                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 17v-2m3 2v-4m3 4v-6m2 10H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path></svg>
                                        </p>
                                        <p class="ml-2 hover:underline">${child.name}</p>
                                    </a>
                                    <div>
                                        <p class="${(child.video!=null && child.video!='') ? "" : "hidden"}">
                                            Video
                                        </p>
                                        <p class="${(child.content!=null && child.content!='') ? "" : "hidden"}">
                                            Document
                                        </p>
                                        <p class="${fn:contains(child.type.value, 'Quiz')==true ? "" : "hidden"}">
                                            Quiz
                                        </p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="px-10 mt-10 py-5 border">
                <h2 class="text-2xl mb-5 font-medium">Course featured</h2>
                <c:forEach items="${featureSubjects}" var="item">
                    <div class="relative box-price-hover">
                        <a class="flex cursor-pointer gap-3 border-b pb-5 mb-5" href="${pageContext.request.contextPath}/course/${item.id}">
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
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="col-span-3">
            <div class="flex flex-col items-start">
                <div class="flex items-center justify-start">
                    <div class="border rounded-full p-5">
                        <svg aria-hidden="true" class="_ufjrdd" style="fill:#4285f4;height:20px;width:20px" viewBox="0 0 48 48" role="img" aria-labelledby="ShareableCertificatec8779b78-8462-4ed6-d60a-e6d372ad0e9c ShareableCertificatec8779b78-8462-4ed6-d60a-e6d372ad0e9cDesc" xmlns="http://www.w3.org/2000/svg"><title id="ShareableCertificatec8779b78-8462-4ed6-d60a-e6d372ad0e9c">Shareable Certificate</title><path fill-rule="evenodd" transform="translate(0, 7)" d="M0,33.4883721 L48,33.4883721 L48,0 L0,0 L0,33.4883721 Z M2.23255814,31.2835216 L45.7685581,31.2835216 L45.7685581,2.20595294 L2.23255814,2.20595294 L2.23255814,31.2835216 Z M37.9534884,18.5546345 L37.9534884,24.788241 L34.354436,23.3584786 L30.5362598,24.7838735 L30.5362598,18.8590034 C28.305764,17.6498837 26.7906977,15.3076504 26.7906977,12.622016 C26.7906977,8.7009892 30.0194673,5.5121263 33.9894439,5.5121263 C37.9598282,5.5121263 41.1886812,8.7009069 41.1886812,12.622016 C41.1886812,15.0409886 39.9485788,17.251856 37.9534884,18.5546345 Z M33.9894734,7.4680421 C31.1125966,7.4680421 28.7711928,9.7805068 28.7711928,12.6219868 C28.7711928,15.4630998 31.1126571,17.7755049 33.9894734,17.7755049 C34.9006939,17.7755049 35.7772388,17.544058 36.5515083,17.1107151 C38.1883943,16.1858852 39.208186,14.4893549 39.208186,12.6219868 C39.208186,9.7804471 36.8667218,7.4680421 33.9894734,7.4680421 Z M32.2012663,22.1366425 L34.3414624,21.3376682 L36.2702836,22.1039133 L36.2702836,19.3659681 C35.5424465,19.6066591 34.7746492,19.7314207 33.9894734,19.7314207 C33.3801179,19.7314207 32.7805792,19.6561943 32.2012663,19.5094807 L32.2012663,22.1366425 Z" role="presentation"></path></svg>
                    </div>
                    <div class="ml-2">
                        <p class="text-xl font-medium">
                            Shareable Certificate
                        </p>
                        <p>
                            Earn a Certificate upon completion
                        </p>
                    </div>
                </div>
                <div class="flex items-center justify-start mt-5">
                    <div class="border rounded-full p-5">
                        <svg aria-hidden="true" class="_ufjrdd" style="fill:#4285f4;height:20px;width:20px" viewBox="0 0 48 48" role="img" aria-labelledby="100%onlinecourses5e5f581b-1905-4da9-e647-6e0e68cc41ff 100%onlinecourses5e5f581b-1905-4da9-e647-6e0e68cc41ffDesc" xmlns="http://www.w3.org/2000/svg"><title id="">100% online courses</title><path d="M29.144,2.6074 C30.868,4.5654 32.31,7.5324 33.322,11.1824 C36.407,10.4934 38.17,9.4914 39.698,8.6014 C36.855,5.7034 33.218,3.5864 29.144,2.6074 L29.144,2.6074 Z M8.239,8.6654 C9.691,9.5474 11.656,10.5824 15.059,11.2674 C16.093,7.5044 17.581,4.4624 19.364,2.4914 C15.054,3.4194 11.208,5.6144 8.239,8.6654 L8.239,8.6654 Z M17.026,11.5984 C18.939,11.8624 21.228,12.0234 24,12.0234 C27.009,12.0234 29.408,11.8414 31.364,11.5454 C29.704,5.6564 26.962,2.1054 24.321,2.0024 C24.263,2.0014 24.206,2.0014 24.148,2.0004 C21.478,2.0484 18.698,5.6304 17.026,11.5984 L17.026,11.5984 Z M2.022,23.0004 L13.482,23.0004 C13.543,19.4734 13.93,16.1644 14.579,13.2154 C10.664,12.4204 8.499,11.1834 6.891,10.1844 C4.03,13.7214 2.24,18.1604 2.022,23.0004 L2.022,23.0004 Z M15.482,23.0004 L32.923,23.0004 C32.861,19.4734 32.472,16.2744 31.855,13.4984 C29.767,13.8234 27.209,14.0234 24,14.0234 C21.035,14.0234 18.585,13.8444 16.538,13.5524 C15.928,16.3154 15.543,19.4964 15.482,23.0004 L15.482,23.0004 Z M34.923,23.0004 L45.977,23.0004 C45.758,18.1314 43.95,13.6704 41.06,10.1234 C40.978,10.1704 40.896,10.2184 40.813,10.2664 C39.129,11.2484 37.204,12.3704 33.808,13.1304 C34.467,16.1004 34.861,19.4394 34.923,23.0004 L34.923,23.0004 Z M23.915,33.4534 C27.147,33.4534 29.78,33.6774 31.956,34.0364 C32.515,31.3704 32.864,28.3324 32.923,25.0004 L15.482,25.0004 C15.54,28.2944 15.883,31.3024 16.431,33.9474 C18.471,33.6414 20.922,33.4534 23.915,33.4534 L23.915,33.4534 Z M2.022,25.0004 C2.233,29.6894 3.92,34.0034 6.627,37.4834 C6.703,37.4394 6.78,37.3944 6.858,37.3494 C8.586,36.3414 10.707,35.1064 14.475,34.3004 C13.889,31.4754 13.54,28.3354 13.482,25.0004 L2.022,25.0004 Z M33.905,34.4184 C37.597,35.2674 39.727,36.5214 41.338,37.5274 C44.065,34.0394 45.766,29.7094 45.977,25.0004 L34.923,25.0004 C34.864,28.3824 34.506,31.5634 33.905,34.4184 L33.905,34.4184 Z M33.443,36.3704 C32.426,40.2264 30.938,43.3554 29.144,45.3924 C33.373,44.3754 37.132,42.1334 40.02,39.0634 C38.571,38.1704 36.649,37.1104 33.443,36.3704 L33.443,36.3704 Z M7.947,39.0284 C10.955,42.2394 14.911,44.5494 19.364,45.5084 C17.504,43.4524 15.965,40.2294 14.927,36.2404 C11.531,36.9614 9.609,38.0614 7.947,39.0284 L7.947,39.0284 Z M16.888,35.8944 C18.541,42.1664 21.404,45.9494 24.148,45.9994 C24.206,45.9994 24.263,45.9984 24.321,45.9974 C27.031,45.8924 29.847,42.1574 31.492,35.9904 C29.446,35.6594 26.966,35.4534 23.915,35.4534 C21.091,35.4534 18.793,35.6194 16.888,35.8944 L16.888,35.8944 Z M24.203,48.0004 L24,48.0004 C10.767,48.0004 0,37.2334 0,24.0004 C0,10.7664 10.767,0.0004 24,0.0004 L24.203,0.0004 C24.26,0.0004 24.317,0.0014 24.375,0.0034 C37.436,0.2044 48,10.8914 48,24.0004 C48,37.1084 37.436,47.7954 24.375,47.9974 C24.317,47.9994 24.26,48.0004 24.203,48.0004 L24.203,48.0004 Z" role="presentation"></path></svg>
                    </div>
                    <div class="ml-2">
                        <p class="text-xl font-medium">
                            100% online courses
                        </p>
                        <p>
                            Start instantly and learn at your own schedule.
                        </p>
                    </div>
                </div>
                <div class="flex items-center justify-start mt-5">
                    <div class="border rounded-full p-5">
                        <svg aria-hidden="true" class="_ufjrdd" style="fill:#4285f4;height:20px;width:20px" viewBox="0 0 48 48" role="img" aria-labelledby="FlexibleSchedule7f358885-9ce6-46e3-b7a2-b2a1cfe2949c FlexibleSchedule7f358885-9ce6-46e3-b7a2-b2a1cfe2949cDesc" xmlns="http://www.w3.org/2000/svg"><title id="FlexibleSchedule7f358885-9ce6-46e3-b7a2-b2a1cfe2949c">Flexible Schedule</title><g stroke="none" stroke-width="1" fill-rule="evenodd" role="presentation"><g fill-rule="nonzero"><g><path d="M0,0 L48,0 L48,37 C48,43.0751322 43.0751322,48 37,48 L11,48 C4.92486775,48 0,43.0751322 0,37 L0,0 Z M46,37 L46,2 L2,2 L2,37 C2,41.9705627 6.02943725,46 11,46 L37,46 C41.9705627,46 46,41.9705627 46,37 Z M10,8 L38,8 L38,10 L10,10 L10,8 Z M34,17 L38,17 L38,21 L34,21 L34,17 Z M26,17 L30,17 L30,21 L26,21 L26,17 Z M18,17 L22,17 L22,21 L18,21 L18,17 Z M34,25 L38,25 L38,29 L34,29 L34,25 Z M26,25 L30,25 L30,29 L26,29 L26,25 Z M18,25 L22,25 L22,29 L18,29 L18,25 Z M10,25 L14,25 L14,29 L10,29 L10,25 Z M26,33 L30,33 L30,37 L26,37 L26,33 Z M18,33 L22,33 L22,37 L18,37 L18,33 Z M10,33 L14,33 L14,37 L10,37 L10,33 Z"></path></g></g></g></svg>
                    </div>
                    <div class="ml-2">
                        <p class="text-xl font-medium">
                            Flexible Schedule
                        </p>
                        <p>
                            Set and maintain flexible deadlines.
                        </p>
                    </div>
                </div>
                <div class="flex items-center justify-start mt-5">
                    <div class="border rounded-full p-5">
                        <svg aria-hidden="true" class="_ufjrdd" style="fill:#4285f4;height:20px;width:20px" viewBox="0 0 48 48" role="img" aria-labelledby="BeginnerLevel509fc6d3-0111-4009-b7e5-588d7891e865 BeginnerLevel509fc6d3-0111-4009-b7e5-588d7891e865Desc" xmlns="http://www.w3.org/2000/svg"><title id="BeginnerLevel509fc6d3-0111-4009-b7e5-588d7891e865">Beginner Level</title><g fill-rule="nonzero" role="presentation"><path d="M6 30h9v14H6z"></path><path fill="#D8D8D8" d="M20 20h9v24h-9zM35 6h9v38h-9z"></path></g></svg>
                    </div>
                    <div class="ml-2">
                        <p class="text-xl font-medium">
                            Beginner Level
                        </p>
                        <p>
                            No prior experience required.
                        </p>
                    </div>
                </div>
                <div class="flex items-center justify-start mt-5">
                    <div class="border rounded-full p-5">
                        <svg aria-hidden="true" class="_ufjrdd" style="fill:#4285f4;height:20px;width:20px" viewBox="0 0 48 48" role="img" aria-labelledby="Hourstocomplete934df715-fb35-442b-a147-c6c0ed6efaa1 Hourstocomplete934df715-fb35-442b-a147-c6c0ed6efaa1Desc" xmlns="http://www.w3.org/2000/svg"><title id="Hourstocomplete934df715-fb35-442b-a147-c6c0ed6efaa1">Hours to complete</title><path d="M24 47C11.318375 47 1 36.681625 1 24S11.318375 1 24 1s23 10.318375 23 23-10.318375 23-23 23zM2.91666667 24c0 11.6255417 9.45779163 21.0833333 21.08333333 21.0833333S45.0833333 35.6255417 45.0833333 24 35.6255417 2.91666667 24 2.91666667 2.91666667 12.3744583 2.91666667 24zm19.12500003 1.9166667V6.70833333h2.9166666V23H35.5v2.9166667H22.0416667z" role="presentation"></path></svg>
                    </div>
                    <div class="ml-2">
                        <p class="text-xl font-medium">
                            Approximately months to complete
                        </p>
                        <p>
                            Suggested pace of every hours/week
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
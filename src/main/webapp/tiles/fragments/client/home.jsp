<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : home.jsp
    Created on : May 21, 2022, 12:59:32 PM
    Author     : giaki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!-- banner -->
<div class="container justify-items-center content-center mx-auto mt-10 ">

    <div class="container banner mb-10 mx-auto relative">
        <div class="swiper banner-swiper">
            <div class="swiper-wrapper h-[450px]">
                <c:forEach items="${sliders}" var="item">
                    <a href="${item.backlink}" class="swiper-slide block  cursor-pointer w-full">
                        <img class="banner-image w-full h-[450px] object-cover	" src="${pageContext.request.contextPath}/images/slider/${item.image}" alt="banner">
                    </a>
                </c:forEach>
            </div>
            <div class="swiper-button-next"></div>
            <div class="swiper-button-prev"></div>
            <div class="swiper-pagination"></div>
        </div>
    </div>
    <div  class="mt-10">
        <h1 class="text-gray-900 text-xl mb-5 font-medium">A broad selection of courses</h1> 
        <div class="swiper mySwiper2">
            <div class="swiper-wrapper h-[360px]">
                <c:forEach items="${subjects}" var="subject"> 
                    <div class="swiper-slide cursor-pointer">
                        <div class="swiper-slide w-full flex items-stretch">
                            <a href="${pageContext.request.contextPath}/course/${subject.id}" class="w-full"> 
                                <div>
                                    <c:choose>
                                        <c:when test="${fn:containsIgnoreCase(subject.image, 'dummyimage.com')}">
                                            <img class="${subject.image!=null ? "":"hidden"} w-full h-[220px] object-cover	" alt="${subject.name}" src="${subject.image}" id="image-preview"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img class="${subject.image!=null ? "":"hidden"} w-full h-[220px] object-cover	" alt="${subject.name}" src="${pageContext.request.contextPath}/images/subject/${subject.image}" id="image-preview"/>
                                        </c:otherwise>
                                    </c:choose>  
                                </div>
                                <div style="max-width: full; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 1; -webkit-box-orient: vertical;">
                                    <h2 class="font-medium text-xl">${subject.name}</h2></div>
                                <div class="mt-3"style="max-width: full; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;">
                                    <p class="mb-1 text-gray-500"> ${subject.description}</p></div>
                            </a>
                        </div>
                    </div>
                </c:forEach> 
            </div>

            <div class="swiper-button-next rounded-full p-5 bg-gray-900"></div>
            <div class="swiper-button-prev rounded-full p-5 bg-gray-900"></div>
            <div class="swiper-pagination"></div>
        </div>
    </div>
    <div  class="mt-10">
        <h1 class="text-gray-900 text-xl mb-5 font-medium">A broad selection of Blog</h1> 
        <div class="swiper mySwiper3">
            <div class="swiper-wrapper h-[360px]">
                <c:forEach items="${posts}" var="post"> 
                    <div class="swiper-slide cursor-pointer">
                        <div class="w-full"> 
                            <a href="${pageContext.request.contextPath}/blog/${post.id}"  class="w-full">
                                <c:choose>
                                    <c:when test="${fn:containsIgnoreCase(post.thumbnail, 'dummyimage.com')}">
                                        <img class="${post.thumbnail!=null ? "":"hidden"} w-full h-[220px] object-cover" alt="${post.title}" src="${post.thumbnail}" id="image-preview"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img class="${post.thumbnail!=null ? "":"hidden"} w-full h-[220px] object-cover" alt="${post.title}" src="${pageContext.request.contextPath}/images/post/${post.thumbnail}" id="image-preview"/>
                                    </c:otherwise>
                                </c:choose> 
                                <div style="max-width: full; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 1; -webkit-box-orient: vertical;">
                                    <h2 class="font-medium text-xl">${post.title}</h2></div>
                                <div class="mt-3"style="max-width: full; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;">
                                    <p class="mb-1 text-gray-500"> ${post.description}</p></div>
                            </a>
                        </div>
                    </div>
                </c:forEach> 
            </div>


            <div class="swiper-button-next rounded-full p-5 bg-gray-900"></div>
            <div class="swiper-button-prev rounded-full p-5 bg-gray-900"></div>
            <div class="swiper-pagination"></div>
        </div>
    </div>
    <div class="mt10 font-medium">
        <h1 class="text-gray-900 text-xl mb-5">A broad selection of blog</h1> 
        <div class="swiper mySwiper4">
            <div class="swiper-wrapper h-[360px]">
                <c:forEach items="${postsFeatured}" var="post"> 
                    <div class="swiper-slide cursor-pointer">
                        <a href="${pageContext.request.contextPath}/blog/${post.id}"  class="w-full">
                            <c:choose>
                                <c:when test="${fn:containsIgnoreCase(post.thumbnail, 'dummyimage.com')}">
                                    <img class="${post.thumbnail!=null ? "":"hidden"} w-full h-[220px] object-cover	" alt="${post.title}" src="${post.thumbnail}" id="image-preview"/>
                                </c:when>
                                <c:otherwise>
                                    <img class="${post.thumbnail!=null ? "":"hidden"} w-full h-[220px] object-cover	" alt="${post.title}" src="${pageContext.request.contextPath}/images/post/${post.thumbnail}" id="image-preview"/>
                                </c:otherwise>
                            </c:choose> 
                            <div style="max-width: full; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 1; -webkit-box-orient: vertical;">
                                <h2 class="font-medium text-xl">${post.title}</h2></div>
                            <div class="mt-3"style="max-width: full; overflow: hidden; text-overflow: ellipsis; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;">
                                <p class="mb-1 text-gray-500"> ${post.description}</p></div>
                        </a>
                    </div>
                </c:forEach> 
            </div>


            <div class="swiper-button-next rounded-full p-5 bg-gray-900"></div>
            <div class="swiper-button-prev rounded-full p-5 bg-gray-900"></div>
            <div class="swiper-pagination"></div>
        </div>
    </div>
</div>

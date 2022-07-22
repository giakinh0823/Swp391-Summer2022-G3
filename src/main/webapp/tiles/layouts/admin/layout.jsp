<%-- 
    Document   : layout
    Created on : May 23, 2022, 10:47:49 AM
    Author     : giaki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="https://d3njjcbhbojbot.cloudfront.net/web/images/favicons/favicon-v2-194x194.png" sizes="194x194">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/lib/jquery-ui.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/lib/swiper.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/lib/flowbite.min.css" />
        <script src="${pageContext.request.contextPath}/assets/lib/tailwindcss.js"></script>
        <title><tiles:getAsString name="title" ignore="true"/></title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}<tiles:getAsString name="style" />">
        <script>const contextPath = `${pageContext.request.contextPath}`</script>
        <script src="${pageContext.request.contextPath}/assets/lib/swiper.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/flowbite.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/datepicker.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/jquery-ui.js"></script>
    </head>
    <body>
        <div class="w-full flex">
            <div class="hidden md:block md:min-w-[200px] xl:min-w-[256px]" id="sliderar-admin-layout">
                <tiles:insertAttribute name="sliderbar" />
            </div>
            <div class="w-full p-5 overflow-x-auto">
                <tiles:insertAttribute name="body" />
            </div>
        </div>
        <div class="block md:hidden fixed top-[50%] left-0" onclick="showSliderbar(this)">
            <button type="button" class="text-white bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 hover:bg-gradient-to-br focus:ring-2 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-2 py-2 text-center">
                <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M11.49 3.17c-.38-1.56-2.6-1.56-2.98 0a1.532 1.532 0 01-2.286.948c-1.372-.836-2.942.734-2.106 2.106.54.886.061 2.042-.947 2.287-1.561.379-1.561 2.6 0 2.978a1.532 1.532 0 01.947 2.287c-.836 1.372.734 2.942 2.106 2.106a1.532 1.532 0 012.287.947c.379 1.561 2.6 1.561 2.978 0a1.533 1.533 0 012.287-.947c1.372.836 2.942-.734 2.106-2.106a1.533 1.533 0 01.947-2.287c1.561-.379 1.561-2.6 0-2.978a1.532 1.532 0 01-.947-2.287c.836-1.372-.734-2.942-2.106-2.106a1.532 1.532 0 01-2.287-.947zM10 13a3 3 0 100-6 3 3 0 000 6z" clip-rule="evenodd"></path></svg>
            </button>
        </div>
        <script src="${pageContext.request.contextPath}/assets/js/admin/admin.js"></script>
        <script><tiles:insertAttribute name="script" /></script>
    </body>
</html>
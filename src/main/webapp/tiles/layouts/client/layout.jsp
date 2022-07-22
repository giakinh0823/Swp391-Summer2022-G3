<%-- 
    Document   : header
    Created on : May 20, 2022, 10:14:49 PM
    Author     : giaki
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="icon" type="image/png" href="https://d3njjcbhbojbot.cloudfront.net/web/images/favicons/favicon-v2-194x194.png" sizes="194x194">
        <meta property="og:title" content="Coursera | Degrees, Certificates, &amp; Free Online Courses" data-react-helmet="true">
        <meta property="og:description" content="Learn new job skills in online courses from industry leaders like Google, IBM, &amp; Meta. Advance your career with top degrees from Michigan, Penn, Imperial &amp; more." data-react-helmet="true">
        <meta property="twitter:title" content="Coursera | Degrees, Certificates, &amp; Free Online Courses" data-react-helmet="true">
        <meta property="twitter:description" content="Learn new job skills in online courses from industry leaders like Google, IBM, &amp; Meta. Advance your career with top degrees from Michigan, Penn, Imperial &amp; more." data-react-helmet="true">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/lib/swiper.min.css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/lib/flowbite.min.css" />
        <script src="${pageContext.request.contextPath}/assets/lib/tailwindcss.js"></script>
        <c:set var="title"><tiles:getAsString name="title" ignore="true"/></c:set>
        <c:if test="${title!=null && title!=''}">
             <title>${title}</title>
        </c:if>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/global.css" />
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}<tiles:getAsString name="style" />">
        <script>const contextPath = `${pageContext.request.contextPath}`</script>
    </head>
    <body>
        <tiles:insertAttribute name="header" />
        <div class="min-h-[70vh]">
            <tiles:insertAttribute name="body" />
        </div>
        <tiles:insertAttribute name="footer" />
        <script src="${pageContext.request.contextPath}/assets/lib/swiper.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/flowbite.js"></script>
        <script src="${pageContext.request.contextPath}/assets/lib/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/client/client.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/client/header.js"></script>
        <script><tiles:insertAttribute name="script" /></script>
    </body>
</html>


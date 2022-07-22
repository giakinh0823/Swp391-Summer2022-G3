<%-- 
    Document   : course_learning_detail
    Created on : Jul 6, 2022, 2:50:24 PM
    Author     : giaki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/richtexteditor/runtime/richtexteditor_preview.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/richtexteditor/rte_theme_default.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/richtexteditor/rte.js"></script>
<script>RTE_DefaultConfig.url_base = '${pageContext.request.contextPath}/assets/richtexteditor/richtexteditor'</script>
<script type="text/javascript" src='${pageContext.request.contextPath}/assets/richtexteditor/plugins/all_plugins.js'></script>
<div class="flex flex-1">
    <div class="flex flex-1  max-h-[calc(100vh-60px)] overflow-y-auto">
        <div class="w-full">
            <%@include file="/tiles/fragments/client/course/course_lesson_content.jsp" %>
        </div>
    </div>
    <div class="w-[280px] max-h-[calc(100vh-60px)] overflow-y-auto border">
         <%@include file="/tiles/fragments/client/course/course_lesson_slidebar.jsp" %>
    </div>
</div>

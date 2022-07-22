<%-- Document : question_create Created on : Jun 8, 2022, 10:09:50 PM Author : giaki --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/assets/richtexteditor/rte_theme_default.css" />
<script type="text/javascript"
src="${pageContext.request.contextPath}/assets/richtexteditor/rte.js"></script>
<script>RTE_DefaultConfig.url_base = '${pageContext.request.contextPath}/assets/richtexteditor/richtexteditor'</script>
<script type="text/javascript"
src='${pageContext.request.contextPath}/assets/richtexteditor/plugins/all_plugins.js'></script>
<script>
    var questionId = ${ question.id }; 
    var subjectid = ${question.subject.id};
    var listAnswers = [];
    <c:forEach items="${question.answers}" var="item">
        listAnswers.push({
            id: ${item.id},
            text: "${item.text}",
            media: "${item.media!=null ? item.media : null}",
            subjectid: ${item.subjectId}
        })
    </c:forEach>
</script>
<div class="container mx-auto p-5">
    <form class="mt-5" method="POST" enctype="multipart/form-data" id="form-question">
        <input type="hidden" name="id" value="${question.id}"/>
        <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg hidden" role="alert"
             id="create-question-error">
            <span class="font-medium">Error!</span> <span id="message-create-question-error"></span>
        </div>
        <div class="p-4 mb-4 text-sm text-green-700 bg-green-100 rounded-lg hidden" role="alert"  id="create-question-success">
            <span class="font-medium">Success!</span> <span id="message-create-question-success"></span>
        </div>
        <div class="flex justify-between mb-5">
            <a href="${backlink}"
               class="text-white bg-gray-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Back</a>
            <c:if
                test="${(sessionScope.user.checkFeature('QUESTION_EDIT') || sessionScope.user.is_super())}">
                <button type="submit"
                        class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Save</button>
            </c:if>
        </div>
        <div class="grid grid-cols-1 2xl:grid-cols-2 space-x-10 mt-10">
            <div>
                <div class="max-w-xl mx-auto 2xl:max-w-full">
                    <div class="mb-6">
                        <div class="mb-6">
                            <label for="status"
                                   class="inline-flex relative items-center cursor-pointer">
                                <input type="checkbox" name="status" ${question.status==true ? "checked" : "" } value="true" id="status" class="sr-only peer">
                                <div
                                    class="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300  rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600">
                                </div>
                                <span class="ml-3 text-sm font-medium text-gray-900">Status</span>
                            </label>
                        </div>
                    </div>
                    <div class="mb-6">
                        <h2 class="mb-3 font-medium mb-3" id="label-subject">${question.subject.name}
                        </h2>
                        <div class="w-full">
                            <div class="relative w-full">
                                <div
                                    class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                                    <svg class="w-5 h-5 text-gray-500" fill="currentColor"
                                         viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd"
                                          d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                                          clip-rule="evenodd"></path>
                                    </svg>
                                </div>
                                <input oninput="searchItemFilterSubject(this)" type="text"
                                       id="search-subject"
                                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2 "
                                       placeholder="Search subject">
                            </div>
                        </div>
                        <div id="spin-search-subject"
                             class="flex justify-center items-center mt-8 hidden">
                            <svg role="status"
                                 class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600"
                                 viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                                fill="currentColor" />
                            <path
                                d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                                fill="currentFill" />
                            </svg>
                        </div>
                        <div class="min-h-[180px] max-h-[200px] overflow-y-auto px-2 pt-3"
                             id="list-subject">
                            <c:forEach items="${subjects}" var="item">
                                <div class="flex items-center mb-2">
                                    <input type="radio" ${question.subject.id==item.id ?"checked": "" }
                                           value="${item.id}" onchange="selectSubject(this)" name="subject"
                                           id="subject-${item.id}"
                                           class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2" />
                                    <label for="subject-${item.id}" id="label-subject-${item.id}"
                                           class="item-subject ml-2 text-sm font-medium text-gray-900">${item.name}</label>
                                </div>
                            </c:forEach>
                        </div>
                        <div id="spin-search-subject-loadmore"
                             class="flex justify-center items-center hidden">
                            <svg role="status"
                                 class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600"
                                 viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                                fill="currentColor" />
                            <path
                                d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                                fill="currentFill" />
                            </svg>
                        </div>
                    </div>
                    <div class="mb-6" id="lesson-content">
                        <h2 class="mb-3 font-medium mb-3" id="label-lesson">${question.lesson.name}</h2>
                        <div class="w-full mb-2">
                            <div class="relative w-full">
                                <div
                                    class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                                    <svg class="w-5 h-5 text-gray-500" fill="currentColor"
                                         viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd"
                                          d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                                          clip-rule="evenodd"></path>
                                    </svg>
                                </div>
                                <input oninput="searchItemFilterLesson(this)" type="text"
                                       id="search-lesson"
                                       class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2 "
                                       placeholder="Search lesson">
                            </div>
                        </div>
                        <div id="spin-search-lesson"
                             class="flex justify-center items-center mt-8 hidden">
                            <svg role="status"
                                 class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600"
                                 viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                                fill="currentColor" />
                            <path
                                d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                                fill="currentFill" />
                            </svg>
                        </div>
                        <div class="min-h-[180px] max-h-[200px] overflow-y-auto px-2 pt-2"
                             id="list-lesson">
                            <c:forEach items="${lessons}" var="item">
                                <div class="flex items-center mb-2">
                                    <input type="radio" ${question.lesson.id==item.id ?"checked": "" }
                                           onchange="selectLesson(this)" name="lesson"
                                           value="${item.id}"
                                           class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2" />
                                    <label
                                        class="item-lesson ml-2 text-sm font-medium text-gray-900">${item.name}</label>
                                </div>
                            </c:forEach>
                        </div>
                        <div class="hidden" id="not-found-lesson">
                            <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg"
                                 role="alert">
                                <span class="font-medium">Danger alert!</span> You need add lesson for
                                current subject selected.
                            </div>
                        </div>
                        <div id="spin-search-lesson-loadmore"
                             class="flex justify-center items-center hidden">
                            <svg role="status"
                                 class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600"
                                 viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                                fill="currentColor" />
                            <path
                                d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                                fill="currentFill" />
                            </svg>
                        </div>
                    </div>
                    <div class="hidden" id="not-found-dimension">
                        <div class="p-4 mb-4 text-sm text-red-700 bg-red-100 rounded-lg" role="alert">
                            <span class="font-medium">Danger alert!</span> You need add dimension for
                            current subject selected.
                        </div>
                    </div>
                    <div id="spin-dimension-select" class="flex justify-center items-center hidden">
                        <svg role="status" class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600"
                             viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path
                            d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                            fill="currentColor" />
                        <path
                            d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                            fill="currentFill" />
                        </svg>
                    </div>
                    <div class="mb-6" id="dimension-content">
                        <div>
                            <label for="dimension"
                                   class="block mb-2 text-sm font-medium text-gray-900">Dimension</label>
                        </div>
                        <select id="dimension" name="dimension"
                                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                            <c:forEach items="${dimensions}" var="item">
                                <option value="${item.id}" ${question.dimension.id==item.id ? "selected" : "" }>${item.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-6">
                        <div>
                            <label for="level"
                                   class="block mb-2 text-sm font-medium text-gray-900">Level</label>
                        </div>
                        <select id="level" name="level"
                                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                            <c:forEach items="${levels}" var="item">
                                <option value="${item.id}" ${question.level.id==item.id ? "selected" : "" }>${item.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-6">
                        <div class="mb-6">
                            <div class="flex justify-center items-center w-full">
                                <label for="media"
                                       class="relative flex flex-col justify-center items-center w-full h-64 bg-gray-50 rounded-lg border-2 border-gray-300 border-dashed cursor-pointer hover:bg-gray-100">
                                    <button type="button" onclick="removeMediaQuestion()" class="absolute top-2 right-2">
                                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path></svg>
                                    </button>
                                    <input type="checkbox" value="true" name="removeMedia" id="removeMedia" class="hidden"/>
                                    <div class="flex flex-col justify-center items-center pt-5 pb-6">
                                        <c:choose>
                                            <c:when test="${(question.media!=null && question.media.type=='image')}">
                                                <div id="image"
                                                     class="max-w-[400px] max-h-[240px] ${(question.media!=null && question.media.type=="image") ?"" :"hidden"}">
                                                    <img id="image-preview" class="w-full h-full"
                                                         src="${pageContext.request.contextPath}/images/question/${(question.media!=null && question.media.type=="image") ? question.media.url :""}" />
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div id="image" class="max-w-[400px] max-h-[240px] hidden">
                                                    <img id="image-preview" class="w-full h-full"/>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${(question.media!=null && question.media.type=='audio')}">
                                                <div id="media-audio"
                                                     class="${(question.media!=null && question.media.type=="audio") ?"" :"hidden"}">
                                                    <audio controls id="audio"
                                                           src="${pageContext.request.contextPath}/images/question/${(question.media!=null && question.media.type=="audio") ? question.media.url :""}"></audio>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div id="media-audio" class="hidden">
                                                    <audio controls id="audio"></audio>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${(question.media!=null && question.media.type=='video')}">
                                                <div id="media-video"
                                                     class="${(question.media!=null && question.media.type=="video") ?"" :"hidden"}">
                                                    <video width="400" height="400" id="video" controls
                                                           src="${pageContext.request.contextPath}/images/question/${(question.media!=null && question.media.type=="video") ? question.media.url :""}"></video>
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div id="media-video" class="hidden">
                                                    <video width="400" height="400"  id="video" controls></video>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                        <div id="icon-upload"
                                             class="flex items-center flex-col justify-center ${(question.media!=null) ?"hidden" :""}">
                                            <svg class="mb-3 w-10 h-10 text-gray-400" fill="none"
                                                 stroke="currentColor" viewBox="0 0 24 24"
                                                 xmlns="http://www.w3.org/2000/svg">
                                            <path stroke-linecap="round" stroke-linejoin="round"
                                                  stroke-width="2"
                                                  d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12">
                                            </path>
                                            </svg>
                                            <p class="mb-2 text-sm text-gray-500 "><span
                                                    class="font-semibold">Click to upload media!</span>
                                        </div>
                                    </div>
                                    <input id="media" name="media" type="file" class="hidden"
                                           accept="image/*, audio/*, video/*" />
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <div class="mb-6">
                    <label for="content"
                           class="block mb-2 text-sm font-medium text-gray-900">Content</label>
                    <textarea id="content" name="content" style="min-height:400px;max-height:800px">
                        ${question.contentHtml}
                    </textarea>
                    <textarea class="hidden" id="content-no-html" name="content-no-html">
                        ${question.content}
                    </textarea>
                </div>
                <div class="mb-6" id="search-answer-content">
                    <label for="search-answer"
                           class="block mb-2 text-sm font-medium text-gray-900">Answer</label>
                    <div class="w-full mb-4">
                        <div class="relative w-full">
                            <div
                                class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                                <svg class="w-5 h-5 text-gray-500" fill="currentColor"
                                     viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd"
                                      d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                                      clip-rule="evenodd"></path>
                                </svg>
                            </div>
                            <input type="text"
                                   onclick="$('#button-search-answer-modal').click(); openSearchAnswer();"
                                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2 "
                                   placeholder="Search answer in current subject">
                        </div>
                    </div>
                    <div class="mb-5">
                        <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                            <table class="w-full text-sm text-left text-gray-500">
                                <tbody id="list-answer">
                                    <c:forEach items="${question.answers}" var="item">
                                        <tr class="bg-white border-b" id="item-answer-${item.id}">
                                            <td class="w-4 p-4">
                                                <div class="flex items-center">
                                                    <input id="answer-${item.id}" type="checkbox"
                                                           value="${item.id}" ${item.is_correct()==true
                                                                    ? "checked" :""} name="answer-result"
                                                           class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500 focus:ring-2" />
                                                    <input type="hidden" value="${item.id}"
                                                           name="answer" />
                                                </div>
                                            </td>
                                            <td class="px-6 py-4 font-medium text-gray-900">
                                                ${item.text}
                                            </td>
                                            <td class="px-6 py-4">
                                                <c:if test="${item.media!=null}">
                                                    <img class="ml-4 w-10 h-10 rounded"
                                                         src="${pageContext.request.contextPath}/images/answer/${item.media}"
                                                         alt="answer-image" />
                                                </c:if>
                                            </td>
                                            <td class="px-6 py-4 text-right">
                                                <button type="button" onclick="editAnswer(${item.id})" 
                                                        class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                                    <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path></svg>
                                                </button>
                                                <button type="button" onclick="deleteAnswer(${item.id})"
                                                        class="py-1.5 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-2 focus:ring-gray-200">
                                                    <svg class="w-4 h-4" fill="currentColor"
                                                         viewBox="0 0 20 20"
                                                         xmlns="http://www.w3.org/2000/svg">
                                                    <path fill-rule="evenodd"
                                                          d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                                                          clip-rule="evenodd"></path>
                                                    </svg>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="flex items-center justify-center mb-3">
                        <button type="button" id="button-open-create-answer"
                                data-modal-toggle="new-answer-modal"
                                class="flex items-center justify-center py-2 px-3 mr-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200">
                            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"
                                 xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd"
                                  d="M10 18a8 8 0 100-16 8 8 0 000 16zm1-11a1 1 0 10-2 0v2H7a1 1 0 100 2h2v2a1 1 0 102 0v-2h2a1 1 0 100-2h-2V7z"
                                  clip-rule="evenodd"></path>
                            </svg>
                            <span class="ml-1">new answer</span>
                        </button>
                    </div>
                </div>
                <div class="mb-6">
                    <label for="explain"
                           class="block mb-2 text-sm font-medium text-gray-900">Explain</label>
                    <textarea id="explain" name="explain">
                        ${question.explain}
                    </textarea>
                </div>
            </div>
        </div>
    </form>
</div>

<button id="button-search-answer-modal"
        class="hidden w-full md:w-auto text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
        type="button" data-modal-toggle="search-answer-modal"></button>
<div id="search-answer-modal" tabindex="-1"
     class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-[1000] w-full md:inset-0 h-modal md:h-full">
    <div class="relative w-full h-full  max-w-2xl">
        <div class="bg-white rounded-lg p-4 mt-5">
            <div class="flex justify-end mb-5">
                <button class="hover:bg-gray-100 rounded-full" type="button"
                        data-modal-toggle="search-answer-modal">
                    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"
                         xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd"
                          d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                          clip-rule="evenodd"></path>
                    </svg>
                </button>
            </div>
            <!-- Modal content -->
            <div class="relative bg-white">
                <div class="space-y-6">
                    <div class="w-full mb-4">
                        <div class="relative w-full">
                            <div
                                class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                                <svg class="w-5 h-5 text-gray-500" fill="currentColor"
                                     viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd"
                                      d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
                                      clip-rule="evenodd"></path>
                                </svg>
                            </div>
                            <input type="text" id="search-answer" oninput="searchItemFilterAnswer(this)"
                                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2 "
                                   placeholder="Search answer in current answer">
                        </div>
                    </div>
                    <div class="p-4 mb-2 text-sm text-green-700 bg-green-100 rounded-lg hidden"
                         role="alert" id="alert-answer-success">
                        <span class="font-medium">Success!</span> <span
                            id="message-alert-answer-success"></span>
                    </div>
                    <div class="p-4 mb-2 text-sm text-red-700 bg-red-100 rounded-lg  hidden"
                         role="alert" id="alert-answer-error">
                        <span class="font-medium">Error!</span> <span
                            id="message-alert-answer-error"></span>
                    </div>
                    <div class="min-h-[180px] max-h-[500px] overflow-y-auto px-2 pt-3"
                         id="table-answer-search">
                        <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
                            <table class="w-full text-sm text-left text-gray-500">
                                <thead class="text-xs text-gray-700 uppercase bg-gray-50">
                                    <tr>
                                        <th scope="col" class="px-6 py-3 text-center">
                                            id
                                        </th>
                                        <th scope="col" class="px-6 py-3 text-center">
                                            text
                                        </th>
                                        <th scope="col" class="px-6 py-3 text-center">
                                            media
                                        </th>
                                        <th scope="col" class="px-6 py-3 text-center">
                                            Add
                                        </th>
                                    </tr>
                                </thead>
                                <tbody id="list-answer-search">

                                </tbody>
                            </table>
                        </div>
                        <div id="spin-search-answer"
                             class="flex justify-center items-center hidden mt-10">
                            <svg role="status"
                                 class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600"
                                 viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                                fill="currentColor" />
                            <path
                                d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                                fill="currentFill" />
                            </svg>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



<div id="new-answer-modal" tabindex="-1"
     class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-[1000] w-full md:inset-0 h-modal md:h-full">
    <div class="relative w-full h-full  max-w-2xl">
        <div class="bg-white rounded-lg p-4 mt-5">
            <div class="flex justify-end mb-5">
                <button class="hover:bg-gray-100 rounded-full" type="button"
                        data-modal-toggle="new-answer-modal">
                    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"
                         xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd"
                          d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                          clip-rule="evenodd"></path>
                    </svg>
                </button>
            </div>
            <!-- Modal content -->
            <div class="relative bg-white">
                <div class="space-y-6">
                    <form class="pb-5 px-5"
                          action="${pageContext.request.contextPath}/admin/question/answer/create"
                          method="POST" id="form-create-answer">
                        <div class="p-4 mb-2 text-sm text-green-700 bg-green-100 rounded-lg hidden"
                             role="alert" id="create-answer-success">
                            <span class="font-medium">Success!</span> <span
                                id="message-create-answer-success"></span>
                        </div>
                        <div class="p-4 mb-2 text-sm text-red-700 bg-red-100 rounded-lg  hidden"
                             role="alert" id="create-answer-error">
                            <span class="font-medium">Error!</span> <span
                                id="message-create-answer-error"></span>
                        </div>
                        <input type="hidden" name="subject" id="create-answer-subject" value="${question.subject.id}" />
                        <div class="mb-6">
                            <label for="text"
                                   class="block mb-2 text-sm font-medium text-gray-900">Text</label>
                            <textarea name="text" id="text" rows="4"
                                      class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500"
                                      placeholder="Text question..."></textarea>
                        </div>
                        <div class="mb-6">
                            <label for="media-answer"
                                   class="relative flex flex-col justify-center items-center w-full h-64 bg-gray-50 rounded-lg border-2 border-gray-300 border-dashed cursor-pointer hover:bg-gray-100">
                                <button type="button" onclick="removeImageCreateAnswer()" class="absolute top-2 right-2">
                                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path></svg>
                                </button>
                                <div class="flex flex-col justify-center items-center pt-5 pb-6">
                                    <div class="max-w-[400px] max-h-[240px]">
                                        <img id="media-answer-preview" class="w-full h-full" />
                                    </div>
                                    <div id="icon-upload-answer"
                                         class="flex items-center flex-col justify-center">
                                        <svg class="mb-3 w-10 h-10 text-gray-400" fill="none"
                                             stroke="currentColor" viewBox="0 0 24 24"
                                             xmlns="http://www.w3.org/2000/svg">
                                        <path stroke-linecap="round" stroke-linejoin="round"
                                              stroke-width="2"
                                              d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12">
                                        </path>
                                        </svg>
                                        <p class="mb-2 text-sm text-gray-500 "><span
                                                class="font-semibold">Click to upload</span> or drag and
                                            drop</p>
                                    </div>
                                </div>
                                <input id="media-answer" name="media" type="file" accept="image/*"
                                       class="hidden" />
                            </label>
                        </div>

                        <div class="flex justify-end">
                            <button type="submit"
                                    class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2 text-center">Add</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>



<div id="popup-modal-delete" tabindex="-1"
     class="hidden z-[10000] overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-md h-full md:h-auto">
        <div class="relative bg-white rounded-lg shadow-lg">
            <button type="button"
                    class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center "
                    id="button-model-answer-delete" data-modal-toggle="popup-modal-delete">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"
                     xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd"
                      d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                      clip-rule="evenodd"></path>
                </svg>
            </button>
            <div class="p-6 text-center">
                <svg class="mx-auto mb-4 w-14 h-14 text-gray-400" fill="none" stroke="currentColor"
                     viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                      d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                </svg>
                <h3 class="mb-5 text-lg font-normal text-gray-500">Are you sure you want to delete this
                    answer?</h3>
                <a id="button-confirm-delete"
                   class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300  font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center mr-2">
                    Yes, I'm sure
                </a>
                <button data-modal-toggle="popup-modal-delete" type="button"
                        class="text-gray-500 bg-white hover:bg-gray-100 focus:ring-4 focus:outline-none focus:ring-gray-200 rounded-lg border border-gray-200 text-sm font-medium px-5 py-2.5 hover:text-gray-900 focus:z-10">No,
                    cancel</button>
            </div>
        </div>
    </div>
</div>


<button type="button" id="button-question-model-spinner"
        data-modal-toggle="create-question-model-spinner"></button>
<div id="create-question-model-spinner" tabindex="-1" aria-hidden="true"
     class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 w-full md:inset-0 h-modal md:h-full">
    <div class="relative p-4 w-full max-w-2xl h-full md:h-auto">
        <!-- Modal content -->
        <div class="relative rounded-lg shadow">
            <div class="flex justify-center">
                <svg role="status" class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600"
                     viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path
                    d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                    fill="currentColor" />
                <path
                    d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                    fill="currentFill" />
                </svg>
            </div>
        </div>
    </div>
</div>
                          
                          
<div id="edit-answer-modal" tabindex="-1"
     class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-[1000] w-full md:inset-0 h-modal md:h-full">
    <div class="relative w-full h-full  max-w-2xl">
        <div class="bg-white rounded-lg p-4 mt-5">
            <div class="flex justify-end mb-5">
                <button class="hover:bg-gray-100 rounded-full" type="button"
                        data-modal-toggle="edit-answer-modal" id="button-edit-answer-modal">
                    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20"
                         xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd"
                          d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                          clip-rule="evenodd"></path>
                    </svg>
                </button>
            </div>
            <!-- Modal content -->
            <div class="relative bg-white">
                <div class="space-y-6">
                    <div id="spin-edit-answer"
                             class="flex justify-center items-center hidden mt-5 mb-5">
                            <svg role="status"
                                 class="w-8 h-8 mr-2 text-gray-200 animate-spin fill-blue-600"
                                 viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path
                                d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                                fill="currentColor" />
                            <path
                                d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                                fill="currentFill" />
                            </svg>
                    </div>
                    <form class="pb-5 px-5 hidden"
                          action="${pageContext.request.contextPath}/admin/question/answer/create"
                          method="POST" id="form-edit-answer">
                        <div class="p-4 mb-2 text-sm text-green-700 bg-green-100 rounded-lg hidden"
                             role="alert" id="edit-answer-success">
                            <span class="font-medium">Success!</span> <span
                                id="message-edit-answer-success"></span>
                        </div>
                        <div class="p-4 mb-2 text-sm text-red-700 bg-red-100 rounded-lg  hidden"
                             role="alert" id="edit-answer-error">
                            <span class="font-medium">Error!</span> <span
                                id="message-edit-answer-error"></span>
                        </div>
                        <input type="hidden" name="id" id="edit-amswer-id"/>
                        <input type="hidden" name="subject" id="edit-answer-subject" />
                        <input type="hidden" name="question" value="${question.id}"/>
                        <div class="mb-6">
                            <label for="text"
                                   class="block mb-2 text-sm font-medium text-gray-900">Text</label>
                            <textarea name="text" id="edit-text" rows="4"
                                      class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500"
                                      placeholder="Text question..."></textarea>
                        </div>
                        <div class="mb-6">
                            <label for="edit-media-answer"
                                   class="relative flex flex-col justify-center items-center w-full h-64 bg-gray-50 rounded-lg border-2 border-gray-300 border-dashed cursor-pointer hover:bg-gray-100">
                                <button type="button" onclick="removeImageEditAnswer()" class="absolute top-2 right-2">
                                    <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path></svg>
                                </button>
                                <input type="checkbox" value="true" name="removeImage" id="remove-image-edit-answer" class="hidden"/>
                                <div class="flex flex-col justify-center items-center pt-5 pb-6">
                                    <div class="max-w-[400px] max-h-[240px]">
                                        <img id="edit-media-answer-preview" class="w-full h-full" />
                                    </div>
                                    <div id="edit-icon-upload-answer"
                                         class="flex items-center flex-col justify-center">
                                        <svg class="mb-3 w-10 h-10 text-gray-400" fill="none"
                                             stroke="currentColor" viewBox="0 0 24 24"
                                             xmlns="http://www.w3.org/2000/svg">
                                        <path stroke-linecap="round" stroke-linejoin="round"
                                              stroke-width="2"
                                              d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12">
                                        </path>
                                        </svg>
                                        <p class="mb-2 text-sm text-gray-500 "><span
                                                class="font-semibold">Click to upload</span> or drag and
                                            drop</p>
                                    </div>
                                </div>
                                <input id="edit-media-answer" name="media" type="file" accept="image/*" class="hidden" />
                            </label>
                        </div>

                        <div class="flex justify-end">
                            <button type="submit"
                                    class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2 text-center">Save</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
                 
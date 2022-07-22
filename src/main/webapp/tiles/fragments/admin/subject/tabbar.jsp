<% 
    String uri = request.getRequestURI();
    request.setAttribute("uri", uri);
%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<div class="border-b border-gray-200 mb-6">
    <ul class="flex flex-wrap -mb-px text-sm font-medium text-center text-gray-500">
        <li class="mr-2">
            <a href="${pageContext.request.contextPath}/admin/subject/${subject.id}" class="${(!fn:contains(uri, 'dimension') && !fn:contains(uri, 'package') && !fn:contains(uri, 'lesson')) ? 'inline-flex p-4 text-blue-600 rounded-t-lg border-b-2 border-blue-600 active': 'inline-flex p-4 rounded-t-lg border-b-2 border-transparent hover:text-gray-600 hover:border-gray-300 group'}">
                <svg class="${(!fn:contains(uri, 'dimension') && !fn:contains(uri, 'package') && !fn:contains(uri, 'lesson')) ? 'mr-2 w-5 h-5 text-blue-600': 'mr-2 w-5 h-5 text-gray-400 group-hover:text-gray-500'}" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-6-3a2 2 0 11-4 0 2 2 0 014 0zm-2 4a5 5 0 00-4.546 2.916A5.986 5.986 0 0010 16a5.986 5.986 0 004.546-2.084A5 5 0 0010 11z" clip-rule="evenodd"></path></svg>
                Overview
            </a>
        </li>
        <li class="mr-2">
            <a href="${pageContext.request.contextPath}/admin/subject/${subject.id}/dimension" class="${fn:contains(uri, 'dimension') ? 'inline-flex p-4 text-blue-600 rounded-t-lg border-b-2 border-blue-600 active': 'inline-flex p-4 rounded-t-lg border-b-2 border-transparent hover:text-gray-600 hover:border-gray-300 group'}" aria-current="page">
                <svg class="${fn:contains(uri, 'dimension') ? 'mr-2 w-5 h-5 text-blue-600': 'mr-2 w-5 h-5 text-gray-400 group-hover:text-gray-500'}" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M5 3a2 2 0 00-2 2v2a2 2 0 002 2h2a2 2 0 002-2V5a2 2 0 00-2-2H5zM5 11a2 2 0 00-2 2v2a2 2 0 002 2h2a2 2 0 002-2v-2a2 2 0 00-2-2H5zM11 5a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V5zM11 13a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z"></path></svg>
                Dimension
            </a>
        </li>
        <li class="mr-2">
            <a href="${pageContext.request.contextPath}/admin/subject/${subject.id}/price-package" class="${fn:contains(uri, 'package') ? 'inline-flex p-4 text-blue-600 rounded-t-lg border-b-2 border-blue-600 active': 'inline-flex p-4 rounded-t-lg border-b-2 border-transparent hover:text-gray-600 hover:border-gray-300 group'}">
                <svg class="${fn:contains(uri, 'package') ? 'mr-2 w-5 h-5 text-blue-600': 'mr-2 w-5 h-5 text-gray-400 group-hover:text-gray-500'}" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M5 4a1 1 0 00-2 0v7.268a2 2 0 000 3.464V16a1 1 0 102 0v-1.268a2 2 0 000-3.464V4zM11 4a1 1 0 10-2 0v1.268a2 2 0 000 3.464V16a1 1 0 102 0V8.732a2 2 0 000-3.464V4zM16 3a1 1 0 011 1v7.268a2 2 0 010 3.464V16a1 1 0 11-2 0v-1.268a2 2 0 010-3.464V4a1 1 0 011-1z"></path></svg>
                Price package
            </a>
        </li>
        <li class="mr-2">
            <a href="${pageContext.request.contextPath}/admin/subject/${subject.id}/lesson" class="${fn:contains(uri, 'lesson') ? 'inline-flex p-4 text-blue-600 rounded-t-lg border-b-2 border-blue-600 active': 'inline-flex p-4 rounded-t-lg border-b-2 border-transparent hover:text-gray-600 hover:border-gray-300 group'}">
                <svg class="${fn:contains(uri, 'lesson') ? 'mr-2 w-5 h-5 text-blue-600': 'mr-2 w-5 h-5 text-gray-400 group-hover:text-gray-500'}" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path d="M12 14l9-5-9-5-9 5 9 5z"></path><path d="M12 14l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 14l9-5-9-5-9 5 9 5zm0 0l6.16-3.422a12.083 12.083 0 01.665 6.479A11.952 11.952 0 0012 20.055a11.952 11.952 0 00-6.824-2.998 12.078 12.078 0 01.665-6.479L12 14zm-4 6v-7.5l4-2.222"></path></svg>
                Lesson
            </a>
        </li>  
    </ul>
</div>

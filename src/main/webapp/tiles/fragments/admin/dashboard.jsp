<%-- 
    Document   : dashboard
    Created on : May 27, 2022, 3:37:11 PM
    Author     : giaki
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script>
    var data_subjects_create = new Map();
    var data_subjects_update = new Map();
    var data_registers_success = new Map();
    var data_registers_pending = new Map();
    var data_customers = new Map();
</script>
<c:forEach items="${subjects_create}" var="item">
    <script>
        data_subjects_create.set('${item.key}', ${item.value.size()});
        data_subjects_update.set('${item.key}', 0);
    </script>
</c:forEach>
<c:forEach items="${subjects_update}" var="item">
    <script>
        data_subjects_update.set('${item.key}', ${item.value.size()});
        if (!data_subjects_create.get('${item.key}')) {
            data_subjects_create.set('${item.key}', 0);
        }
    </script>
</c:forEach>
<c:forEach items="${registers_sucess}" var="item">
    <script>
        data_registers_success.set('${item.key}', ${item.value.size()});
    </script>
</c:forEach>
<c:forEach items="${registers_pending}" var="item">
    <script>
        data_registers_pending.set('${item.key}', ${item.value.size()});
         if (!data_registers_success.get('${item.key}')) {
            data_registers_success.set('${item.key}', 0);
        }
    </script>
</c:forEach>
<c:forEach items="${customers}" var="item">
    <script>
        data_customers.set('${item.key}', ${item.value.size()});
    </script>
</c:forEach>
<div class="w-full">
    <form method="GET" class="mb-5">
        <div class="flex justify-end">
            <div date-rangepicker class="flex items-center">
                <div class="relative">
                    <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                        <svg aria-hidden="true" class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M6 2a1 1 0 00-1 1v1H4a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V6a2 2 0 00-2-2h-1V3a1 1 0 10-2 0v1H7V3a1 1 0 00-1-1zm0 5a1 1 0 000 2h8a1 1 0 100-2H6z" clip-rule="evenodd"></path></svg>
                    </div>
                    <input name="date-start" type="text" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5 " placeholder="Select date start">
                </div>
                <span class="mx-4 text-gray-500">to</span>
                <div class="relative">
                    <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                        <svg aria-hidden="true" class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M6 2a1 1 0 00-1 1v1H4a2 2 0 00-2 2v10a2 2 0 002 2h12a2 2 0 002-2V6a2 2 0 00-2-2h-1V3a1 1 0 10-2 0v1H7V3a1 1 0 00-1-1zm0 5a1 1 0 000 2h8a1 1 0 100-2H6z" clip-rule="evenodd"></path></svg>
                    </div>
                    <input name="date-end" type="text" class="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full pl-10 p-2.5" placeholder="Select date end">
                </div>
            </div>
            <button type="submit" class="py-2.5 px-5 ml-2 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-200">Search</button>
        </div>
    </form>

    <div>
        <div>
            <div>
                <div>
                    <div class="grid grid-cols-3 gap-2">
                        <div>
                            <div class="box box1 shadow-sm">
                                <div id="spark1"></div>
                            </div>
                        </div>
                        <div>
                            <div class="box box2 shadow-sm">
                                <div id="spark2"></div>
                            </div>
                        </div>
                        <div>
                            <div class="box box3 shadow-sm">
                                <div id="spark3"></div>
                            </div>
                        </div>
                    </div>
                    <div class="box mt-10">
                        <div id="bar"></div>
                    </div>
                    <div class="box mt-10">
                        <div id="bar2"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div id="chart-subject" class="mt-10">
    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/apexcharts/apexcharts.min.js"></script>
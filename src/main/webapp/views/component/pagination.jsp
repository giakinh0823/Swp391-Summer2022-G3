<div class="mt-10 w-full flex justify-center">
    <nav aria-label="Page navigation example">
        <ul class="inline-flex -space-x-px">
            <li>
                <a data="${page.prev}" class="page-link py-2 px-3 ml-0 leading-tight text-gray-500 bg-white rounded-l-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700">Previous</a>
            </li>
            <c:if test="${page.pageIndex>2}">
                <li>
                    <a data="${page.pageIndex - 2}" class="page-link py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 ">${page.pageIndex - 2}</a>
                </li>
            </c:if>
            <c:if test="${page.pageIndex>1}">
                <li>
                    <a data="${page.pageIndex-1}" class="page-link py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700">${page.pageIndex-1}</a>
                </li>
            </c:if>
            <li>
                <a data="${page.pageIndex}" aria-current="page" class="page-link py-2 px-3 text-blue-600 bg-blue-50 border border-gray-300 hover:bg-blue-100 hover:text-blue-700">${page.pageIndex}</a>
            </li>
            <c:if test="${page.pageIndex<page.count}">
                <li>
                    <a data="${page.pageIndex+1}" class="page-link py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700">${page.pageIndex+1}</a>
                </li>
            </c:if>
            <c:if test="${page.pageIndex+1<page.count}">
                <li>
                    <a data="${page.pageIndex+2}" class="page-link py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700">${page.pageIndex+2}</a>
                </li>
            </c:if>
            <li>
                <a data="${page.next}" class="page-link py-2 px-3 leading-tight text-gray-500 bg-white rounded-r-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700">Next</a>
            </li>
        </ul>
    </nav>
</div>
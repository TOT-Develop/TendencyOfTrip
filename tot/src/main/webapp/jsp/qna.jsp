<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="initial-scale=1, width=device-width" />
    <title>QnA</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/qna.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/qna.js"></script>
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
    <div class="wrapper">
        <div class="container">
            <div class="row1">
                <h2 class="row1Text">Q & A</h2>
            </div>
            <div class="row2">
                <form id="searchForm" action="${pageContext.request.contextPath}/qna/${boardId}/1" method="get">
                    <div class="qnaCategories">
                        <!-- 문의글 검색 -->
                        <!-- 문의 유형 검색 -->
                        <select name="searchType" class="qnaCategory">
                            <option value="ALL" ${page.searchType=='ALL' ? 'selected' : '' }>전체</option>
                            <option value="Q01" ${page.searchType=='Q01' ? 'selected' : '' }>계정관리</option>
                            <option value="Q02" ${page.searchType=='Q02' ? 'selected' : '' }>기술지원</option>
                            <option value="Q03" ${page.searchType=='Q03' ? 'selected' : '' }>불만요청</option>
                            <option value="Q04" ${page.searchType=='Q04' ? 'selected' : '' }>기타요청</option>
                        </select>
                        <!-- 문의 제목, 내용 검색 -->
                        <input name="search" class="searchInput" value="${page.search}" type="text"
                            placeholder="검색어를 입력하세요.">
                        <button type="submit" class="initButton2">검색</button>
						<!-- 문의글 검색 끝 -->
						
                        <input type="button" value="+ 글쓰기" class="toWrite" />
                        <input type="button" value="전체 글 보기" id="totalQnaBtn" />
                        <input type="button" value="내 글 보기" id="myQnaBtn" />
                    </div>
                </form>

                <div class="questionList">
                    <div class="tableContainer">
                        <table class="table">
                            <thead id="qnaTableHead">
                                <tr>
                                    <th>카테고리</th>
                                    <th>제목</th>
                                    <th>작성자</th>
                                    <th>작성일</th>
                                </tr>
                            </thead>
                            <tbody id="qnaTableBody">
                                <c:choose>
				                    <c:when test="${empty pagination.postList}">
				                        <tr>
				                            <td colspan="4" style="text-align: center;">작성된 문의글이 없습니다.</td>
				                        </tr>
				                    </c:when>
				                    <c:otherwise>
				                        <c:forEach var="qna" items="${pagination.postList}">
				                            <tr onclick="location.href='${pageContext.request.contextPath}/qna/${boardId}/detail/${qna.qnaId}'">
				                                <td>
				                                	<c:choose>
												        <c:when test="${qna.qna_001 == 'Q01'}">계정관리</c:when>
												        <c:when test="${qna.qna_001 == 'Q02'}">기술지원</c:when>
												        <c:when test="${qna.qna_001 == 'Q03'}">불만요청</c:when>
												        <c:when test="${qna.qna_001 == 'Q04'}">기타요청</c:when>
												        <c:otherwise>알 수 없는 카테고리</c:otherwise>
												    </c:choose>
				                                </td>
				                                <td>${qna.qnaTitle}</td>
				                                <td>${qna.memNick}</td>
				                                <td>
				                                	<fmt:formatDate value="${qna.qnaRegdate}" pattern="yyyy-MM-dd HH:mm" />
				                                </td>
				                            </tr>
				                        </c:forEach>
				                    </c:otherwise>
				                </c:choose>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- 페이징 버튼 -->
            <nav class="paginationNav">
                <ul class="paginationList">
                    <!-- 처음 페이지로 이동하는 버튼 -->
                    <li class="paginationItem">
                        <a href="${pageContext.request.contextPath}/qna/${boardId}/${pagination.startBlockPage}"
                            class="paginationLink">&laquo;</a>
                    </li>

                    <!-- 이전 페이지 버튼 -->
                    <c:if test="${pagination.isPrev}">
                        <li class="paginationItem">
                            <a href="${pageContext.request.contextPath}/qna/${boardId}/${pagination.currentPage - 1}"
                                class="paginationLink">이전</a>
                        </li>
                    </c:if>

                    <!-- 페이지 번호 출력 -->
                    <c:forEach begin="${pagination.startBlockPage}" end="${pagination.endBlockPage}" var="pageNum">
                        <c:choose>
                            <c:when test="${pagination.currentPage == pageNum}">
                                <li class="paginationItem active">
                                    <span class="paginationLink">${pageNum}</span>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="paginationItem">
                                    <a href="${pageContext.request.contextPath}/qna/${boardId}/${pageNum}"
                                        class="paginationLink">${pageNum}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <!-- 다음 페이지 버튼 -->
                    <c:if test="${pagination.isNext}">
                        <li class="paginationItem">
                            <a href="${pageContext.request.contextPath}/qna/${boardId}/${pagination.currentPage + 1}"
                                class="paginationLink">다음</a>
                        </li>
                    </c:if>

                    <!-- 맨 끝으로 버튼 -->
                    <li class="paginationItem">
                        <a href="${pageContext.request.contextPath}/qna/${boardId}/${pagination.endBlockPage}"
                            class="paginationLink">&raquo;</a>
                    </li>
                </ul>
            </nav>
            <!-- 페이징 버튼 끝 -->
        </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
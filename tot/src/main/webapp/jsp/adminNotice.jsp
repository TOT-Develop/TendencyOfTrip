<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="initial-scale=1, width=device-width" />
    <title>Admin 공지사항 메인</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/notice.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>   
    <script src="${pageContext.request.contextPath}/static/js/adminNotice.js"></script>
</head>
<body>
    <div class="wrapper">
        <div class="container">
            <div class="row1">
                <h2 class="row1Text">NOTICE</h2>
            </div>        
            <div class="row2">
                <div class="qnaCategories">
                    <!-- 검색을 서버에서 처리 -->
				 <form id="searchForm" action="${pageContext.request.contextPath}/notices/search" method="get">
				    <div class="searchDiv">
				        <!-- 검색 조건 선택 -->
				        <select name="searchType" aria-label="searchType select" class="select">
				            <option value="ALL" ${pageReqDTO.searchType == 'ALL' ? 'selected' : ''}>전체</option>
				            <option value="TITLE" ${pageReqDTO.searchType == 'TITLE' ? 'selected' : ''}>제목</option>
				            <option value="CONTENT" ${pageReqDTO.searchType == 'CONTENT' ? 'selected' : ''}>내용</option>
				        </select>
				
				        <!-- 검색어 입력 -->
				        <input name="keyword" class="searchInput" value="${pageReqDTO.search}" type="text" placeholder="검색어를 입력하세요." />
				
				        <!-- 검색 버튼 -->
				        <button type="submit" class="initButton2">검색</button>
				    </div>
				</form>
                </div>

                <div class="questionList">
                    <div class="tableContainer">
                        <table class="table">
                            <thead id="qnaTableHead">
                                <tr>
                                    <th>작성자</th>
                                    <th>제목</th>
                                    <th>작성일</th>
                                    <th>삭제</th>
                                    <th>수정</th>
                                </tr>                                
                            </thead>
                            <tbody id="qnaTableBody">
								<c:forEach var="notice" items="${pagination.postList}">
								    <tr>
								        <td>${notice.noid}</td>
								        <td><a href="${pageContext.request.contextPath}/admin/api/notices/detail/${notice.noid}">${notice.notitle}</a></td>
								        <td><fmt:formatDate value="${notice.noregdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								        <td><a href="#" class="deleteLink" onclick="deleteNotice(${notice.noid})">삭제</a></td>
								        <td><a href="${pageContext.request.contextPath}/api/notices/update/${notice.noid}">수정</a></td>
								    </tr>
								</c:forEach>                                                            
                            </tbody>
                            	<tr>
									<th colspan="5" class="regbtn">
                            			<button onclick="location.href='${pageContext.request.contextPath}/jsp/noticeRegist.jsp'">글쓰기</button>
                            		</th>
                            	</tr>
                        </table>
                    </div>                    
                    <!-- 페이징 버튼 추가 -->
                    <nav class="paginationNav">
                        <ul class="paginationList">
                            <!-- 처음 페이지로 이동하는 버튼 -->
                            <li class="paginationItem">
                                <a href="${pageContext.request.contextPath}/admin/api/notices?page=${pagination.startBlockPage}" class="paginationLink">&laquo;</a>
                            </li>

                            <!-- 이전 페이지 버튼 -->
                            <c:if test="${pagination.isPrev}">
                                <li class="paginationItem">
                                    <a href="${pageContext.request.contextPath}/admin/api/notices?page=${pagination.currentPage - 1}" class="paginationLink">이전</a>
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
                                            <a href="${pageContext.request.contextPath}/admin/api/notices?page=${pageNum}" class="paginationLink">${pageNum}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>

                            <!-- 다음 페이지 버튼 -->
                            <c:if test="${pagination.isNext}">
                                <li class="paginationItem">
                                    <a href="${pageContext.request.contextPath}/admin/api/notices?page=${pagination.currentPage + 1}" class="paginationLink">다음</a>
                                </li>
                            </c:if>

                            <!-- 맨 끝으로 버튼 -->
                            <li class="paginationItem">
                                <a href="${pageContext.request.contextPath}/admin/api/notices?page=${pagination.endBlockPage}" class="paginationLink">&raquo;</a>
                            </li>
                        </ul>
                    </nav>
                    <!-- 페이징 버튼 끝 -->
                </div>
            </div> 
        </div>
    </div>

</body>
</html>

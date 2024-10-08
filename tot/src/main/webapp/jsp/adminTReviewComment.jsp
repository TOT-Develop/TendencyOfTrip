<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/review.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/adminReview.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/adminTReviewComment.js"></script>
    <title>댓글 관리</title>
</head>
    <%@ include file="adminMenu.jsp" %>
<body>
    <div class="mainDiv">
        <div class="reviewTitleDiv">
            <div class="bigTitle">댓글 관리</div>
        </div>
        <div class="reviewIntroDiv">
        	<div>[댓글 관리 설명]</div><hr />
        	<div>이 페이지는 작성된 여행 후기 댓글을 관리하는 화면입니다. 작성된 댓글을 조회하고, 상태를 변경할 수 있습니다.</div>
            <div>상단의 검색 옵션을 사용해 내용, 게시물, 사용자 및 작성 날짜별로 쉽게 후기를 확인할 수 있습니다.</div>
			<div>상태는 '정상', '삭제', '신고', '제재', '수정' 상태로 나뉩니다. 각 상태에 따라 댓글을 관리하고, 부적절한 내용이 포함된 댓글은 비활성화할 수 있습니다.</div>
        </div>
        
        <div class="searchAndWriteDiv">
        	<!-- 댓글 검색 및 정렬 폼 -->
        	<form id="searchForm" action="${pageContext.request.contextPath}/admin/comment/${boardId}/1" method="get">
	            <div class="searchDiv">
	            	<!-- 댓글 정렬  -->
	            	<select name="sortType" aria-label="sortType select" class="select">
			            <option value="LATEST" ${page.sortType == 'LATEST' ? 'selected' : ''}>최신순</option>
			            <option value="OLDEST" ${page.sortType == 'OLDEST' ? 'selected' : ''}>오래된순</option>
			        </select>
			        <!-- 댓글 정렬 끝 -->
	            	
	            	<!-- 댓글 검색 -->
	                <select name="searchType" aria-label="searchType select" class="select">
	                    <option value="ALL" ${page.searchType == 'ALL' ? 'selected' : ''}>전체</option>
	                    <option value="CONTENT" ${page.searchType == 'CONTENT' ? 'selected' : ''}>내용</option>
	                    <option value="POST" ${page.searchType == 'POST' ? 'selected' : ''}>게시물</option>
			            <option value="MEMBER" ${page.searchType == 'MEMBER' ? 'selected' : ''}>사용자</option>
                	</select>
	                <input name="search" class="searchInput" value="${page.search}" type="text" placeholder="검색어를 입력하세요.">
	                <!-- 댓글 검색 끝 -->
	                <button type="submit" class="initButton2">검색</button>
	            </div>
	        </form>
	        <!-- 댓글 검색 및 정렬 폼 끝 -->
        </div>
        
        <!-- 활성화, 비활성화 목록 버튼 -->
        <div class="activeButtonDiv">
        	<div class="initButton active" id="activeBtn">활성화</div>
            <div class="initButton" id="deactiveBtn">비활성화</div>
        </div>

        <!-- 댓글 테이블 시작 -->
        <div class="table-responsive">
            <table class="adminTable">
                <thead>
                    <tr>
                    	<th><input type="checkbox" id="selectAll"></th>
                        <th>번호</th>
                		<th>게시글</th>
                        <th>작성자</th>
                        <th>내용</th>
                        <th>작성일</th>
                        <th>수정일</th>
                        <th>댓글 유형</th>
                        <th>상태</th>
                        <th>관리</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="comment" items="${pagination.postList}" varStatus="status">
					    <tr>
					    	<td><input type="checkbox" name="commentSelect" value="${comment.commentId}"></td>
					        <td>${status.index+1}</td>
					        <td>
						        <a href="${pageContext.request.contextPath}/admin/review/${boardId}/detail/${comment.postId}/1">
						        	${comment.postId}
						        </a>
					        </td>
					        <td>${comment.memNick}</td>
					        <td>
						        <div class="comment-content" data-full-text="${comment.content}">
	                            	${comment.content}
	                            </div>
					        </td>
					        <td>${comment.regDate}</td>
					        <td>${comment.update}</td>
					        <td>
					        	<c:choose>
                                    <c:when test="${comment.depth == 0}">댓글</c:when>
                                    <c:when test="${comment.depth == 1}">대댓글</c:when>
                                    <c:when test="${comment.depth == 2}">대대댓글</c:when>
                                    <c:when test="${comment.depth == 3}">대대대댓글</c:when>
                                    <c:otherwise>깊은 댓글 (depth: ${comment.depth})</c:otherwise>
                                </c:choose>
					        </td>
					        <td>
                                <c:choose>
                                    <c:when test="${comment.status == 'CMT001'}">정상</c:when>
                                    <c:when test="${comment.status == 'CMT002'}">삭제</c:when>
                                    <c:when test="${comment.status == 'CMT003'}">신고</c:when>
                                    <c:when test="${comment.status == 'CMT004'}">제재</c:when>
                                    <c:when test="${comment.status == 'CMT005'}">수정</c:when>
                                    <c:otherwise>조정중</c:otherwise>
                                </c:choose>
                            </td>
					        <td>
					        	<c:choose>
							        <c:when test="${comment.status == 'CMT001' || comment.status == 'CMT003' || comment.status == 'CMT005'}">
						                <a href="${pageContext.request.contextPath}/admin/comment/${boardId}/deactive" class="activeButton">비활성화</a>
						            </c:when>
						            <c:when test="${comment.status == 'CMT002' || comment.status == 'CMT004'}">
						                <a href="${pageContext.request.contextPath}/admin/comment/${boardId}/active" class="activeButton">활성화</a>
						            </c:when>
						    	</c:choose>
					        </td>
					    </tr>
					</c:forEach>
                </tbody>
            </table>
        </div>
        <!-- 댓글 테이블 끝 -->

        <!-- 페이징 버튼 -->
		<nav class="paginationNav">
		    <ul class="paginationList">
		        <!-- 처음 페이지로 이동하는 버튼 -->
	            <li class="paginationItem">
	                <a href="${pageContext.request.contextPath}/admin/comment/${boardId}/${pagination.startBlockPage}" class="paginationLink">&laquo;</a>
	            </li>
		
		        <!-- 이전 페이지 버튼 -->
		        <c:if test="${pagination.isPrev}">
		            <li class="paginationItem">
		                <a href="${pageContext.request.contextPath}/admin/comment/${boardId}/${pagination.currentPage - 1}" class="paginationLink">이전</a>
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
		                        <a href="${pageContext.request.contextPath}/admin/comment/${boardId}/${pageNum}" class="paginationLink">${pageNum}</a>
		                    </li>
		                </c:otherwise>
		            </c:choose>
		        </c:forEach>
		
		        <!-- 다음 페이지 버튼 -->
		        <c:if test="${pagination.isNext}">
		            <li class="paginationItem">
		                <a href="${pageContext.request.contextPath}/admin/comment/${boardId}/${pagination.currentPage + 1}" class="paginationLink">다음</a>
		            </li>
		        </c:if>
		
		        <!-- 맨 끝으로 버튼 -->
	            <li class="paginationItem">
	                <a href="${pageContext.request.contextPath}/admin/comment/${boardId}/${pagination.endBlockPage}" class="paginationLink">&raquo;</a>
	            </li>
		    </ul>
		</nav>
		<!-- 페이징 버튼 끝 -->
    </div>
    
    <!-- 댓글 전체 내용 모달 -->
    <div id="commentModal" class="modal">
	    <div class="modal-content">
	        <span class="close2">&times;</span>
	        <h2>댓글 내용</h2>
	        <div id="modalCommentContent"></div>
	    </div>
	</div>
</body>
</html>

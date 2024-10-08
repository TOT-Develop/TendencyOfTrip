<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/review.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/adminReview.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/adminTReview.js"></script>
    <title>게시물 관리</title>
</head>
<%@ include file="adminMenu.jsp" %>
<body>
    <div class="mainDiv">
        <div class="reviewTitleDiv">
            <div class="bigTitle">게시물 관리</div>
        </div>
        <div class="reviewIntroDiv">
        	<div>[게시물 관리 설명]</div><hr />
            <div>이 페이지는 작성된 여행 후기를 관리하는 곳입니다. 작성된 후기를 조회하고, 상태를 변경할 수 있습니다.</div>
            <div>상단의 검색 옵션을 사용해 제목, 내용 및 작성 날짜별로 쉽게 후기를 확인할 수 있습니다.</div>
			<div>후기는 '정상', '삭제', '신고', '제재', '수정' 상태로 나뉩니다. 각 상태에 따라 후기를 관리하고, 부적절한 내용이 포함된 후기는 비활성화할 수 있습니다.</div>
			<div>정기적으로 후기를 검토하여 커뮤니티의 품질을 유지하세요.</div>
        </div>
        
        <div class="searchAndWriteDiv">
        	<!-- 여행 후기 검색 및 정렬 폼 -->
        	<form id="searchForm" action="${pageContext.request.contextPath}/admin/review/${boardId}/1" method="get">
	            <div class="searchDiv">
	            	<!-- 여행 후기 정렬  -->
	            	<select name="sortType" aria-label="sortType select" class="select">
			            <option value="LATEST" ${page.sortType == 'LATEST' ? 'selected' : ''}>최신순</option>
			            <option value="OLDEST" ${page.sortType == 'OLDEST' ? 'selected' : ''}>오래된순</option>
			            <option value="VIEWS" ${page.sortType == 'VIEWS' ? 'selected' : ''}>조회순</option>
			            <option value="RATING" ${page.sortType == 'RATING' ? 'selected' : ''}>평점순</option>
			        </select>
	            	<!-- 여행 후기 정렬 끝 -->
	            	<!-- 여행 후기 검색 -->
	                <select name="searchType" aria-label="searchType select" class="select">
	                    <option value="ALL" ${page.searchType == 'ALL' ? 'selected' : ''}>전체</option>
	                    <option value="TITLE" ${page.searchType == 'TITLE' ? 'selected' : ''}>제목</option>
	                    <option value="CONTENT" ${page.searchType == 'CONTENT' ? 'selected' : ''}>내용</option>
                	</select>
	                <input name="search" class="searchInput" value="${page.search}" type="text" placeholder="검색어를 입력하세요.">
	                <!-- 여행 후기 검색 끝 -->
	                <button type="submit" class="initButton2">검색</button>
	            </div>
	        </form>
	        <!-- 여행 후기 검색 및 정렬 폼 끝 -->
        </div>
        
        <!-- 활성화, 비활성화 목록 버튼 -->
        <div class="activeButtonDiv">
        	<div class="initButton active" id="activeBtn">활성화</div>
            <div class="initButton" id="deactiveBtn">비활성화</div>
        </div>

        <!-- 게시물 테이블 시작 -->
        <div class="table-responsive">
            <table class="adminTable">
                <thead>
                    <tr>
                    	<th><input type="checkbox" id="selectAll"></th>
                        <th>번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>수정일</th>
                        <th>상태</th>
                        <th>조회수</th>
                        <th>평점</th>
                        <th>관리</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="review" items="${pagination.postList}" varStatus="status">
					    <tr onclick="location.href='${pageContext.request.contextPath}/admin/review/${boardId}/detail/${review.trevId}/1'">
					    	<td><input type="checkbox" name="reviewSelect" value="${review.trevId}" onclick="event.stopPropagation();"></td>
					        <td>${status.index + 1}</td>
					        <td>
							    <c:choose>
							        <c:when test="${fn:length(review.trevTitle) > 10}">
							            ${fn:substring(review.trevTitle, 0, 10)}...
							        </c:when>
							        <c:otherwise>
							            ${review.trevTitle}
							        </c:otherwise>
							    </c:choose>
							</td>
					        <td>${review.memNick}</td>
					        <td>${review.trevRegdate}</td>
					        <td>${review.trevUpdate}</td>
					        <td>
                                <c:choose>
                                    <c:when test="${review.trevStatus == 'CMT001'}">정상</c:when>
                                    <c:when test="${review.trevStatus == 'CMT002'}">삭제</c:when>
                                    <c:when test="${review.trevStatus == 'CMT003'}">신고</c:when>
                                    <c:when test="${review.trevStatus == 'CMT004'}">제재</c:when>
                                    <c:when test="${review.trevStatus == 'CMT005'}">수정</c:when>
                                    <c:otherwise>알 수 없음</c:otherwise>
                                </c:choose>
                            </td>
					        <td>${review.trevCount}</td>
					        <td>
					            <c:forEach var="i" begin="1" end="5">
					                <c:choose>
					                    <c:when test="${i <= review.trevRating}">
					                        <span class="star filled">⭐</span>
					                    </c:when>
					                    <c:otherwise>
					                        <span class="star">☆</span>
					                    </c:otherwise>
					                </c:choose>
					            </c:forEach>
					        </td>
					        <td>
					            <c:choose>
							        <c:when test="${boardId == 1}">
							            <a href="${pageContext.request.contextPath}/admin/review/${boardId}/deactive" class="activeButton">비활성화</a>
							        </c:when>
							        <c:when test="${boardId == 2}">
							            <a href="${pageContext.request.contextPath}/admin/review/${boardId}/active" class="activeButton">활성화</a>
							        </c:when>
						    	</c:choose>
					        </td>
					    </tr>
					</c:forEach>
                </tbody>
            </table>
        </div>
        <!-- 게시물 테이블 끝 -->

        <!-- 페이징 버튼 -->
		<nav class="paginationNav">
		    <ul class="paginationList">
		        <!-- 처음 페이지로 이동하는 버튼 -->
	            <li class="paginationItem">
	                <a href="${pageContext.request.contextPath}/admin/review/${boardId}/${pagination.startBlockPage}" class="paginationLink">&laquo;</a>
	            </li>
		
		        <!-- 이전 페이지 버튼 -->
		        <c:if test="${pagination.isPrev}">
		            <li class="paginationItem">
		                <a href="${pageContext.request.contextPath}/admin/review/${boardId}/${pagination.currentPage - 1}" class="paginationLink">이전</a>
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
		                        <a href="${pageContext.request.contextPath}/admin/review/${boardId}/${pageNum}" class="paginationLink">${pageNum}</a>
		                    </li>
		                </c:otherwise>
		            </c:choose>
		        </c:forEach>
		
		        <!-- 다음 페이지 버튼 -->
		        <c:if test="${pagination.isNext}">
		            <li class="paginationItem">
		                <a href="${pageContext.request.contextPath}/admin/review/${boardId}/${pagination.currentPage + 1}" class="paginationLink">다음</a>
		            </li>
		        </c:if>
		
		        <!-- 맨 끝으로 버튼 -->
	            <li class="paginationItem">
	                <a href="${pageContext.request.contextPath}/admin/review/${boardId}/${pagination.endBlockPage}" class="paginationLink">&raquo;</a>
	            </li>
		    </ul>
		</nav>
		<!-- 페이징 버튼 끝 -->
    </div>
    
    <!-- 로딩창 HTML -->
	<div id="loading" style="display:none;">
	    <p>메일을 전송 중입니다...</p>
	    <div id="progressContainer">
	        <div id="progressBar"></div>
	    </div>
	</div>
</body>
</html>

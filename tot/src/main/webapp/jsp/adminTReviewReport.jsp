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
    <script src="${pageContext.request.contextPath}/static/js/adminTReviewReport.js"></script>
    <title>신고 내역 관리</title>
</head>
<body>
    <div class="mainDiv">
        <div class="reviewTitleDiv">
            <div class="bigTitle">신고 내역 관리</div>
        </div>
        <div class="reviewIntroDiv">
        	<div>[신고 내역 관리 설명]</div><hr />
        	<div>이 페이지는 사용자들이 신고한 게시물이나 댓글에 대한 정보를 관리자가 확인하고 처리할 수 있도록 돕습니다.</div>
		    <div>검색 및 정렬 기능을 통해 원하는 신고를 빠르게 찾을 수 있으며, 각 신고에 대한 처리 상태를 변경하여 관리할 수 있습니다.</div>
		    <div>관리자는 신고된 게시물 또는 댓글의 세부 정보를 확인하고, 적절한 조치를 취할 수 있습니다.</div>
		    <div>신고 항목은 접수, 처리중, 완료, 기각 상태로 구분되며, 선택적으로 상태를 변경할 수 있습니다.</div>
        </div>
        
        <div class="searchAndWriteDiv">
        	<!-- 신고 검색 및 정렬 폼 -->
        	<form id="searchForm" action="${pageContext.request.contextPath}/admin/report/${boardId}/1" method="get">
	            <div class="searchDiv">
	            	<!-- 신고 정렬  -->
	            	<select name="sortType" aria-label="sortType select" class="select">
			            <option value="LATEST" ${page.sortType == 'LATEST' ? 'selected' : ''}>최신순</option>
			            <option value="OLDEST" ${page.sortType == 'OLDEST' ? 'selected' : ''}>오래된순</option>
			        </select>
			        <!-- 신고 정렬 끝 -->
	            	
	            	<!-- 신고 검색 -->
	                <select name="searchType" aria-label="searchType select" class="select">
	                    <option value="ALL" ${page.searchType == 'ALL' ? 'selected' : ''}>전체</option>
	                    <option value="POST" ${page.searchType == 'POST' ? 'selected' : ''}>게시물</option>
			            <option value="MEMBER" ${page.searchType == 'MEMBER' ? 'selected' : ''}>사용자</option>
			            <option value="COMMENT" ${page.searchType == 'COMMENT' ? 'selected' : ''}>댓글</option>
                	</select>
	                <input name="search" class="searchInput" value="${page.search}" type="text" placeholder="검색어를 입력하세요.">
	                <!-- 신고 검색 끝 -->
	                <button type="submit" class="initButton2">검색</button>
	            </div>
	        </form>
	        <!-- 신고 검색 및 정렬 폼 끝 -->
        </div>
        
        <!-- 활성화, 비활성화 목록 버튼 -->
        <div class="activeButtonDiv">
        	<div class="initButton active" id="activeBtn">미완료</div>
            <div class="initButton" id="deactiveBtn">완료</div>
        </div>

        <!-- 신고 테이블 시작 -->
        <div class="table-responsive">
            <table class="adminTable">
                <thead>
                    <tr>
                    	<th><input type="checkbox" id="selectAll"></th>
                        <th>번호</th>
                		<th>게시글/댓글</th>
                        <th>신고자</th>
                        <th>콘텐츠 유형</th>
                        <th>신고 사유</th>
                        <th>신고 날짜</th>
                        <th>신고 처리 날짜</th>
                        <th>처리 상태</th>
                        <th>관리</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="report" items="${pagination.postList}" varStatus="status">
					    <tr>
					    	<td><input type="checkbox" name="reportSelect" value="${report.reportId}"></td>
					        <td>${status.index+1}</td>
					        <td>
					        	<c:choose>
						        	<c:when test="${report.reportedContentType == 'Treview'}">
							        	<a href="${pageContext.request.contextPath}/admin/review/${boardId}/detail/${report.reportedTrevId}">
								        	${report.postTitle}
								        </a>
						        	</c:when>
	                                <c:when test="${report.reportedContentType == 'Treview Comment'}">
	                                	<a href="${pageContext.request.contextPath}/admin/comment/${boardId}/1">
							        		${report.commentContent}
							        	</a>
	                                </c:when>
                                </c:choose>
					        </td>
					        <td>${report.reporterNickName}</td>
					        <td>
					        	<c:choose>
                                    <c:when test="${report.reportedContentType == 'Treview'}">게시물</c:when>
                                    <c:when test="${report.reportedContentType == 'Treview Comment'}">댓글</c:when>
                                    <c:otherwise>기타</c:otherwise>
                                </c:choose>
					        </td>
					        <td>${report.reportReason}</td>
					        <td>${report.reportDate}</td>
					        <td>${report.processDate}</td>
					        <td>
                                <c:choose>
                                    <c:when test="${report.reportStatus == 'RECEIVED'}">접수</c:when>
                                    <c:when test="${report.reportStatus == 'INPROGRESS'}">처리중</c:when>
                                    <c:when test="${report.reportStatus == 'COMPLETED'}">완료</c:when>
                                    <c:when test="${report.reportStatus == 'REJECTED'}">기각</c:when>
                                    <c:otherwise>조정중</c:otherwise>
                                </c:choose>
                            </td>
					        <td>
					        	<a href="${pageContext.request.contextPath}/admin/report/${boardId}/{status}" class="activeButton">처리</a>
					        </td>
					    </tr>
					</c:forEach>
                </tbody>
            </table>
        </div>
        <!-- 신고 테이블 끝 -->

        <!-- 페이징 버튼 -->
		<nav class="paginationNav">
		    <ul class="paginationList">
		        <!-- 처음 페이지로 이동하는 버튼 -->
	            <li class="paginationItem">
	                <a href="${pageContext.request.contextPath}/admin/report/${boardId}/${pagination.startBlockPage}" class="paginationLink">&laquo;</a>
	            </li>
		
		        <!-- 이전 페이지 버튼 -->
		        <c:if test="${pagination.isPrev}">
		            <li class="paginationItem">
		                <a href="${pageContext.request.contextPath}/admin/report/${boardId}/${pagination.currentPage - 1}" class="paginationLink">이전</a>
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
		                        <a href="${pageContext.request.contextPath}/admin/report/${boardId}/${pageNum}" class="paginationLink">${pageNum}</a>
		                    </li>
		                </c:otherwise>
		            </c:choose>
		        </c:forEach>
		
		        <!-- 다음 페이지 버튼 -->
		        <c:if test="${pagination.isNext}">
		            <li class="paginationItem">
		                <a href="${pageContext.request.contextPath}/admin/report/${boardId}/${pagination.currentPage + 1}" class="paginationLink">다음</a>
		            </li>
		        </c:if>
		
		        <!-- 맨 끝으로 버튼 -->
	            <li class="paginationItem">
	                <a href="${pageContext.request.contextPath}/admin/report/${boardId}/${pagination.endBlockPage}" class="paginationLink">&raquo;</a>
	            </li>
		    </ul>
		</nav>
		<!-- 페이징 버튼 끝 -->
    </div>
    
    <!-- 신고 처리 모달 -->
    <div id="statusModal" class="modal">
    <div class="modal-content">
        <span class="close2">&times;</span>
        <h2>신고 처리 상태 변경</h2>
        <p>선택된 신고의 상태를 변경합니다.</p>
        <label for="reportStatus">상태 선택:</label>
        <select id="reportStatus">
            <option value="RECEIVED">접수</option>
            <option value="INPROGRESS">처리중</option>
            <option value="COMPLETED">완료</option>
            <option value="REJECTED">기각</option>
        </select>
        <button id="confirmUpdateBtn" class="initButton active">확인</button>
    </div>
</div>
</body>
</html>

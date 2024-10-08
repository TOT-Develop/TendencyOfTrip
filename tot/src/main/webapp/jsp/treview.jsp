<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="tot.domain.MemberVO"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/review.css" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/treview.js"></script>
	<title>여행 후기</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="mainDiv">
		<div class="reviewTitleDiv">
			<div class="bigTitle">여행 후기</div>
		</div>
		<div class="reviewIntroDiv">
			<div>여행 후기는 여행지에서의 경험을 기록하고 공유하는 중요한 방법입니다.</div>
			<div>아래 목록에서 다양한 여행 후기를 확인하고, 자신이 작성한 후기 목록을 선택할 수 있습니다.</div>
		</div>
		<div class="menu">
			<div class="initButton active" id="TotalReviewsBtn">전체 여행 후기</div>
			<div class="initButton" id="myReviewsBtn">나의 여행 후기</div>
		</div>

		<div class="searchAndWriteDiv">
			<!-- 여행 후기 검색 및 정렬 폼 -->
			<form id="searchForm" action="${pageContext.request.contextPath}/review/${boardId}/1" method="get">
				<div class="searchDiv">
					<!-- 여행 후기 정렬  -->
					<select name="sortType" aria-label="sortType select" class="select">
						<option value="LATEST" ${page.sortType=='LATEST' ? 'selected' : '' }>최신순</option>
						<option value="OLDEST" ${page.sortType=='OLDEST' ? 'selected' : '' }>오래된순</option>
						<option value="VIEWS" ${page.sortType=='VIEWS' ? 'selected' : '' }>조회순</option>
						<option value="RATING" ${page.sortType=='RATING' ? 'selected' : '' }>평점순</option>
					</select>
					<!-- 여행 후기 정렬 끝 -->
					<!-- 여행 후기 검색 -->
					<select name="searchType" aria-label="searchType select" class="select">
						<option value="ALL" ${page.searchType=='ALL' ? 'selected' : '' }>전체</option>
						<option value="TITLE" ${page.searchType=='TITLE' ? 'selected' : '' }>제목</option>
						<option value="CONTENT" ${page.searchType=='CONTENT' ? 'selected' : '' }>내용</option>
					</select>
					<input name="search" class="searchInput" value="${page.search}" type="text"
						placeholder="검색어를 입력하세요.">
					<button type="submit" class="initButton2">검색</button>
					<!-- 여행 후기 검색 끝 -->
				</div>
			</form>
			<!-- 여행 후기 검색 및 정렬 폼 끝 -->

			<!-- 여행 후기 글쓰기 컨테이너 -->
			<div class="writeReviewDiv">
				<button id="writeReviewBtn" class="initButton2">글쓰기</button>
			</div>
			<!-- 여행 후기 글쓰기 컨테이너 끝 -->
		</div>
		<hr />

		<!-- 여행 후기 목록 리스트 -->
		<div class="reviewList">
			<c:forEach var="review" items="${pagination.postList}">
				<a href="${pageContext.request.contextPath}/review/${boardId}/detail/${review.trevId}"
					class="reviewItem">
					<c:choose>
						<c:when test="${not empty review.trevImages and fn:length(review.trevImages) > 0}">
							<c:choose>
								<c:when test="${not empty review.trevImages[0].trevImgpath}">
									<img src="${pageContext.request.contextPath}${review.trevImages[0].trevImgpath}"
										alt="Review Title Image">
								</c:when>
								<c:otherwise>
									<img src="${pageContext.request.contextPath}/static/image/treviewDefaultImg.png"
										alt="Review Default Image">
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<img src="${pageContext.request.contextPath}/static/image/treviewDefaultImg.png"
								alt="Review Default Image">
						</c:otherwise>
					</c:choose>
					<div class="info">
						<div class="title">${review.trevTitle}</div>
						<div class="author">${review.memNick}</div>
						<div class="rating">
							평점:
							<c:if test="${not empty review.trevRating}">
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
							</c:if>
						</div>
						<div class="count">조회수: ${review.trevCount}</div>
					</div>
				</a>
			</c:forEach>
		</div>
		<!-- 여행 후기 목록 리스트 END-->
		<!-- 페이징 버튼 -->
		<nav class="paginationNav">
			<ul class="paginationList">
				<!-- 처음 페이지로 이동하는 버튼 -->
				<li class="paginationItem">
					<a href="${pageContext.request.contextPath}/review/${boardId}/${pagination.startBlockPage}"
						class="paginationLink">&laquo;</a>
				</li>

				<!-- 이전 페이지 버튼 -->
				<c:if test="${pagination.isPrev}">
					<li class="paginationItem">
						<a href="${pageContext.request.contextPath}/review/${boardId}/${pagination.currentPage - 1}"
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
								<a href="${pageContext.request.contextPath}/review/${boardId}/${pageNum}"
									class="paginationLink">${pageNum}</a>
							</li>
						</c:otherwise>
					</c:choose>
				</c:forEach>

				<!-- 다음 페이지 버튼 -->
				<c:if test="${pagination.isNext}">
					<li class="paginationItem">
						<a href="${pageContext.request.contextPath}/review/${boardId}/${pagination.currentPage + 1}"
							class="paginationLink">다음</a>
					</li>
				</c:if>

				<!-- 맨 끝으로 버튼 -->
				<li class="paginationItem">
					<a href="${pageContext.request.contextPath}/review/${boardId}/${pagination.endBlockPage}"
						class="paginationLink">&raquo;</a>
				</li>
			</ul>
		</nav>
		<!-- 페이징 버튼 끝 -->
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
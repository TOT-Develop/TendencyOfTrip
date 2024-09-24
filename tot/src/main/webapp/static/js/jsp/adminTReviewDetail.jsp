<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/adminReview.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/adminTReview.js"></script>
    <title>게시물 상세 관리</title>
</head>
<body>
    <div class="mainDiv">
        <div class="reviewTitleDiv">
        	<div class="backBtn">
        		<button aria-label="뒤로가기">&#128281;</button>
       		</div>
            <div class="bigTitle">게시물 상세 관리</div>
	        <div class="activeButtonDiv" data-trevid="${review.trevId}">
	        	<c:choose>
		            <c:when test="${review.trevStatus == 'CMT001' || review.trevStatus == 'CMT003' || review.trevStatus == 'CMT005'}">
		                <button class="initButton active" data-url="${pageContext.request.contextPath}/admin/review/${boardId}/deactive">활성화</button>
		            </c:when>
		            <c:when test="${review.trevStatus == 'CMT002' || review.trevStatus == 'CMT004'}">
		                <button class="initButton" data-url="${pageContext.request.contextPath}/admin/review/${boardId}/active">비활성화</button>
		            </c:when>
		        </c:choose>
	        </div>
        </div>
        
        <!-- 1. 상세 정보 내역 -->
        <div class="section">
            <h2>상세 정보</h2>
            <table>
                <tr>
                    <th>제목</th>
                    <td id="review-title">${review.trevTitle}</td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td id="review-content">${review.trevTitle}</td>
                </tr>
                <tr>
                    <th>평점</th>
                    <td id="review-rating">${review.trevRating}</td>
                </tr>
                <tr>
                    <th>작성일</th>
                    <td id="review-date">${review.trevRegdate}</td>
                </tr>
                <tr>
                    <th>수정일</th>
                    <td id="review-update">${review.trevUpdate}</td>
                </tr>
                <tr>
                    <th>조회수</th>
                    <td id="review-count">${review.trevCount}</td>
                </tr>
                <tr>
                    <th>상태</th>
                    <td>
                        <c:choose>
                            <c:when test="${review.trevStatus == 'CMT001'}">
                                정상
                            </c:when>
                            <c:when test="${review.trevStatus == 'CMT002'}">
                                삭제
                            </c:when>
                            <c:when test="${review.trevStatus == 'CMT003'}">
                                신고
                            </c:when>
                            <c:when test="${review.trevStatus == 'CMT004'}">
                                제재
                            </c:when>
                            <c:when test="${review.trevStatus == 'CMT005'}">
                                수정
                            </c:when>
                            <c:otherwise>
                                조정중
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </table>
        </div>
        <!-- 상세 정보 내역 끝 -->

        <!-- 2. 여행 코스 정보 -->
        <div class="section">
            <h2>여행 코스 정보</h2>
            <div class="reviewCourseDiv">
                <!-- 여행 코스 이미지 -->
                <div class="reviewCourseImg">
                    <img src="https://via.placeholder.com/300x200" alt="Review Course Image">
                    <div class="initButton2">경로 상세보기</div>
                </div>

                <!-- 여행 코스 테이블 -->
                <div class="reviewCourseDayDiv">
                    <table class="course-table">
                        <thead>
                            <tr>
                                <th>Day</th>
                                <th>코스 정보</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="course" items="${courses}" varStatus="courseStatus">
                                <tr>
                                    <!-- Day 번호 -->
                                    <td>${courseStatus.index + 1}일차</td>
                                    <td>
                                        <div class="course">
                                            <!-- 코스 정보 -->
                                            <c:forEach var="detail" items="${course.courseDetail}"
                                                varStatus="detailStatus">
                                                <c:choose>
                                                    <c:when test="${not empty detail.dname}">
                                                        <c:out value="${detail.dname}" />
                                                        <c:choose>
                                                            <c:when test="${not empty detail.daddress}">
                                                                <c:if
                                                                    test="${detailStatus.index < course.courseDetail.size() - 1}">
                                                                    &rsaquo;
                                                                </c:if>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:when>
                                                    <c:when test="${not empty detail.daddress}">
                                                        <c:out value="${detail.daddress}" />
                                                        <c:if
                                                            test="${detailStatus.index < course.courseDetail.size() - 1}">
                                                            &rsaquo;
                                                        </c:if>
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!-- 여행 코스 정보 끝 -->

        <!-- 3. 이미지 갤러리 -->
        <div class="section">
            <h3>이미지 갤러리</h3>
		    <div class="image-gallery" id="image-gallery">
		        <c:choose>
		            <c:when test="${not empty review.trevImages && review.trevImages[0].trevImgpath != null}">
		                <c:forEach var="image" items="${review.trevImages}">
		                    <img src="${pageContext.request.contextPath}${image.trevImgpath}" 
		                         alt="여행 후기 이미지 ${image.trevImgId}" 
		                         class="uploadTRevImg">
		                </c:forEach>
		            </c:when>
		            <c:otherwise>
		                <p>이미지가 없습니다.</p>
		            </c:otherwise>
		        </c:choose>
		    </div>
        </div>
        <!-- 이미지 갤러리 끝 -->

       	<!-- 4. 댓글 관리 내역 -->
		<div class="section">
		    <h3>댓글 관리</h3>
		    <table class="comment-table">
		        <thead>
		            <tr>
		            	<th>번호</th>
		                <th>작성자</th>
		                <th>내용</th>
		                <th>작성일자</th>
		                <th>수정일자</th>
		                <th>댓글유형</th>
		                <th>상태</th>
		                <th>관리</th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:choose>
		                <c:when test="${not empty comments}">
		                    <c:forEach var="comment" items="${comments}" varStatus="status">
		                        <tr style="margin-left: ${comment.depth * 20}px;">
		                            <td>${status.index + 1}</td>
		                            <td>
		                                <strong>${comment.memNick}</strong>
		                            </td>
		                            <td>
		                                <div style="margin-left: ${comment.depth * 20}px;">
		                                    ${comment.content}
		                                </div>
		                            </td>
		                            <td>${comment.regDate}</td>
		                            <td>
		                                <c:choose>
		                                    <c:when test="${not empty comment.update}">
		                                        ${comment.update}
		                                    </c:when>
		                                    <c:otherwise>
		                                        수정되지 않음
		                                    </c:otherwise>
		                                </c:choose>
		                            </td>
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
		                                </c:choose>
		                            </td>
		                            <td>
		                            	<div class="activeButtonDiv" data-trevid="${comment.commentId}">
								        	<c:choose>
									            <c:when test="${comment.status == 'CMT001' || comment.status == 'CMT003' || comment.status == 'CMT005'}">
									                <button class="initButton active" data-url="${pageContext.request.contextPath}/admin/comment/${boardId}/deactive">활성화</button>
									            </c:when>
									            <c:when test="${comment.status == 'CMT002' || comment.status == 'CMT004'}">
									                <button class="initButton" data-url="${pageContext.request.contextPath}/admin/comment/${boardId}/active">비활성화</button>
									            </c:when>
									        </c:choose>
								        </div>
		                            </td>
		                        </tr>
		                    </c:forEach>
		                </c:when>
		                <c:otherwise>
		                    <tr>
		                        <td colspan="8" style="text-align:center;">댓글이 없습니다.</td>
		                    </tr>
		                </c:otherwise>
		            </c:choose>
		        </tbody>
		    </table>
		</div>
		<!-- 댓글 관리 내역 끝 -->
		
		<!-- 5. 게시물 이력 내역 -->
		<div class="section">
		    <h3>이력 내역</h3>
		    <table class="history-table">
		        <thead>
		            <tr>
		                <th>번호</th>
		                <th>작성자</th>
		                <th>내용</th>
		                <th>일시</th>
		                <th>활동</th>
		            </tr>
		        </thead>
		        <tbody>
		            <c:forEach var="history" items="${historys}" varStatus="status">
		                <tr>
		                    <td>${historyCount - status.index}</td>
		                    <td>${history.memNick}</td>
		                    <td>${history.content}</td>
		                    <td>${history.actionDate}</td>
		                    <td>${history.action}</td>
		                </tr>
		            </c:forEach>
		        </tbody>
		    </table>
		</div>
		<!-- 게시물 이력 내역 끝 -->
    </div>
    
    <!-- 이미지 모달 구조 -->
	<div id="imageModal" class="modal" onclick="closeModal()">
	    <span class="close" onclick="closeModal()">&times;</span>
	    <img class="modal-content" id="modalImage">
	    <div id="caption"></div>
	</div>
</body>
</html>
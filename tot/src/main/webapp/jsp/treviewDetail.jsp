<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/review.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/treview.js"></script>
    <title>여행 후기 상세</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
    <div class="mainDiv">
        <div class="reviewTitleDiv">
            <div class="bigTitle">${review.trevTitle}</div>
        </div>
        <div class="reviewIntroDiv">
            <div>
                <fmt:formatDate value="${review.trevRegdate}" pattern="yyyy년 MM월 dd일" />
            </div>
        </div>
        <div class="reviewFuncDiv">
	        <c:choose>
		        <c:when test="${review.memId == member.memId}">
		            <a href="${pageContext.request.contextPath}/review/${boardId}/edit/${review.trevId}" class="initButton">수정</a>
		            <button id="delTReviewBtn" class="initButton" data-title="${review.trevTitle}" data-delete-url="${pageContext.request.contextPath}/review/${boardId}/delete/${review.trevId}">삭제</button>
		        </c:when>
		        <c:otherwise>
		            <button id="reportTReviewBtn" class="initButton" data-title="${review.trevTitle}" data-report-url="${pageContext.request.contextPath}/review/${boardId}/report/${review.trevId}">신고</button>
		        </c:otherwise>
	    	</c:choose>
    	</div>
        <!-- 여행 코스 내용 -->
        <div class="reviewCourseDiv">
            <!-- 여행 코스 이미지 -->
            <div class="reviewCourseImg">
                <img src="${courses[0].regionImageUrl}" style="width: 300px; height: 200px; object-fit: cover;" alt="ReviewCourseImage">
                <div class="initButton2">경로 상세보기 ></div>
            </div>
            <!-- 여행 코스 날짜별 내역 리스트 -->
            <div class="reviewCourseDayDiv">
                <c:forEach var="course" items="${courses}" varStatus="courseStatus">
                    <div class="courseDay">
                        <!-- DAY 번호 출력 -->
                        <div class="day">Day ${courseStatus.index + 1}</div>
                        <div class="course">
                            <c:forEach var="detail" items="${course.courseDetail}" varStatus="detailStatus">
                                <c:choose>
                                    <c:when test="${not empty detail.dname}">
                                        <c:out value="${detail.dname}" />
                                        <c:choose>
                                            <c:when test="${not empty detail.daddress}">
                                                <c:if test="${detailStatus.index < course.courseDetail.size() - 1}">
                                                    &rsaquo;
                                                </c:if>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                    <c:when test="${not empty detail.daddress}">
                                        <c:out value="${detail.daddress}" />
                                        <c:if test="${detailStatus.index < course.courseDetail.size() - 1}">
                                            &rsaquo;
                                        </c:if>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- 여행 코스 날짜별 상세 내역 리스트 -->
        <div class="reviewCourseDetailDiv">
            <c:set var="imageIndex" value="0" />
            <c:forEach var="content" items="${fn:split(review.trevContent, ',')}" varStatus="status">
                <div class="reviewCourseDetailItem">
                    <c:choose>
                        <c:when test="${content == 'image'}">
                            <c:if test="${imageIndex < review.trevImages.size()}">
                                <img src="${pageContext.request.contextPath}${review.trevImages[imageIndex].trevImgpath}"
                                    alt="Review Detail Image">
                                <c:set var="imageIndex" value="${imageIndex + 1}" />
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <div class="detailContent">${content}</div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:forEach>
            <button id="reviewListBtn" class="initButton2">목록으로</button>
        </div>
        <!-- 여행 코스 날짜별 상세 내역 리스트 끝 -->

        <!-- 댓글 작성 및 목록 -->
        <div class="commentsSection">
            <h2>댓글(${fn:length(comments)})</h2>

            <!-- 댓글 입력 폼 -->
            <div class="commentInputDiv">
                <div class="profileImg">
                    <img src="https://via.placeholder.com/50" alt="Profile Image 1">
                </div>
                <form id="commentForm"
                    action="${pageContext.request.contextPath}/${boardId}/${review.trevId}/comment/add" method="post">
                    <div id="commentMember" class="commentMember" name="member">${member.memNick}</div>
                    <input id="commentContent" name="content" type="text" placeholder="댓글을 작성해주세요." required />
                    <button id="commentRegBtn" type="submit" class="initButton active">댓글 작성</button>
                    <button id="commentCancelBtn" type="button" class="initButton">취소</button>
                </form>
            </div>
            <!-- 댓글 입력 폼 끝 -->

			<!-- 댓글 목록 -->
            <c:forEach var="comment" items="${comments}">
			    <c:if test="${comment.depth == 0}">
			        <!-- 최상위 댓글 -->
			        <div class="commentItem">
			            <div class="commentDetailItem" style="margin-left: ${comment.depth * 20}px;">
			                <div class="profileImg">
			                    <img src="https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg?t=st=1728268060~exp=1728271660~hmac=6f8134298a9985ceaedf1eaa32a7382277a3b36ffddbf59715632e283ad220df&w=740" alt="Profile Image">
			                </div>
			                <div class="commentMemberDiv">
			                    <div id="commentMember" class="commentMember" name="member">${comment.memNick}</div>
			                    <div class="commentText">${comment.content}</div>
			                </div>
			                <div class="commentDate">
			                    <c:choose>
			                        <c:when test="${not empty comment.update}">
			                            <fmt:formatDate value="${comment.update}" pattern="yyyy-MM-dd HH:mm" />
			                        </c:when>
			                        <c:otherwise>
			                            <fmt:formatDate value="${comment.regDate}" pattern="yyyy-MM-dd HH:mm" />
			                        </c:otherwise>
			                    </c:choose>
			                </div>
			                <div id="commentSetting" class="commentSetting">⋮</div>
			                <!-- 댓글 옵션 메뉴 -->
			                <c:if test="${member.memId == comment.memId}">
			                    <div class="commentOptionsMenu" style="display: none;">
			                        <a href="${pageContext.request.contextPath}/${boardId}/${review.trevId}/comment/edit/${comment.commentId}"
			                            class="editComment">수정</a>
			                        <a href="${pageContext.request.contextPath}/${boardId}/${review.trevId}/comment/delete/${comment.commentId}"
			                            class="deleteComment">삭제</a>
			                        <a href="${pageContext.request.contextPath}/${boardId}/${review.trevId}/comment/report/${comment.commentId}"
			                        	class="reportComment">신고</a>
			                    </div>
			                </c:if>
			                <c:if test="${member.memId != comment.memId}">
							    <div class="commentOptionsMenu" style="display: none;">
							        <a href="${pageContext.request.contextPath}/${boardId}/${review.trevId}/comment/report/${comment.commentId}"
							        	class="reportComment">신고</a>
							    </div>
							</c:if>
			            </div>

			            <div class="commentReply" data-comment-id="${comment.commentId}">댓글 작성</div>
			            <!-- 대댓글 입력 폼 -->
			            <div class="commentReplyForm" style="display: none;">
			                <form class="replyForm"
			                    action="${pageContext.request.contextPath}/${boardId}/${review.trevId}/comment/add" method="post">
			                    <input type="hidden" name="parentId" value="${comment.commentId}" />
			                    <input type="text" name="content" placeholder="댓글을 작성해주세요." required />
			                    <button type="submit" class="initButton active">작성</button>
			                    <button type="button" class="initButton cancelReply">취소</button>
			                </form>
			            </div>
			            <!-- 대댓글 입력 폼 끝 -->
			            
			            <!-- 댓글이 있는지 확인 및 댓글 보이기 -->
			            <c:set var="hasReplies" value="false" />
			            <c:forEach var="reply" items="${comments}">
			                <c:if test="${reply.parentId == comment.commentId && reply.depth > comment.depth}">
			                    <c:set var="hasReplies" value="true" />
			                </c:if>
			            </c:forEach>
			            <c:if test="${hasReplies}">
			                <div class="showRepliesButton" data-comment-id="${comment.commentId}">▶ 댓글 보기</div>
			            </c:if>
			        </div>
			        
			        <!-- 대댓글 표시 영역 -->
			        <div class="replyComments" data-comment-id="${comment.commentId}" style="display: none;">
			            <c:forEach var="reply" items="${comments}">
			                <c:if test="${reply.topParentId == comment.commentId && reply.depth > 0}">
			                    <div class="commentItem" style="margin-left: 20px;">
			                        <div class="commentDetailItem">
			                            <div class="profileImg">
			                                <img src="https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg?t=st=1728268060~exp=1728271660~hmac=6f8134298a9985ceaedf1eaa32a7382277a3b36ffddbf59715632e283ad220df&w=740" alt="Profile Image">
			                            </div>
			                            <div class="commentMemberDiv">
			                                <div id="commentMember" class="commentMember">${reply.memNick}</div>
			                                <div class="commentText">
				                                <c:if test="${not empty reply.parentNickname}">
										            <span class="nickname-highlight">@${reply.parentNickname}</span>
										        </c:if>
				                                ${reply.content}
			                                </div>
			                            </div>
			                            <div class="commentDate">
			                                <c:choose>
			                                    <c:when test="${not empty reply.update}">
			                                        <fmt:formatDate value="${reply.update}" pattern="yyyy-MM-dd HH:mm" />
			                                    </c:when>
			                                    <c:otherwise>
			                                        <fmt:formatDate value="${reply.regDate}" pattern="yyyy-MM-dd HH:mm" />
			                                    </c:otherwise>
			                                </c:choose>
			                            </div>
			                            <div class="commentSetting">⋮</div>
			                            <!-- 댓글 옵션 메뉴 -->
			                            <c:if test="${member.memId == reply.memId}">
			                                <div class="commentOptionsMenu" style="display: none;">
			                                    <a href="${pageContext.request.contextPath}/${boardId}/${review.trevId}/comment/edit/${reply.commentId}"
			                                        class="editComment">수정</a>
			                                    <a href="${pageContext.request.contextPath}/${boardId}/${review.trevId}/comment/delete/${reply.commentId}"
			                                        class="deleteComment">삭제</a>
			                                    <a href="${pageContext.request.contextPath}/${boardId}/${review.trevId}/comment/report/${reply.commentId}"
			                                    	class="reportComment">신고</a>
			                                </div>
			                            </c:if>
			                            <c:if test="${member.memId != reply.memId}">
										    <div class="commentOptionsMenu" style="display: none;">
										        <a href="${pageContext.request.contextPath}/${boardId}/${review.trevId}/comment/report/${reply.commentId}"
										        	class="reportComment">신고</a>
										    </div>
										</c:if>
			                        </div>
			                       			                        
			                        <div class="commentReply">댓글 작성</div>
						            <!-- 대댓글 입력 폼 -->
						            <div class="commentReplyForm" style="display: none;">
						            	<div class="parentNicknameDisplay"></div>
						                <form class="replyForm"
						                    action="${pageContext.request.contextPath}/${boardId}/${review.trevId}/comment/add" method="post">
						                    <input type="hidden" name="parentId" value="${reply.commentId}" />
						                    <input type="hidden" name="parentNickname" class="parentNicknameInput" value="" />
						                    <input type="text" class="replyInput" name="content" placeholder="댓글을 작성해주세요." required />
						                    <button type="submit" class="initButton active">작성</button>
						                    <button type="button" class="initButton cancelReply">취소</button>
						                </form>
						            </div>
						            <!-- 대댓글 입력 폼 끝 -->
						            
						            <!-- 대댓글은 해당 댓글에 대해 언급해서 표현 -->
			                    </div>
			                </c:if>
			            </c:forEach>
			        </div>
			        <!-- 대댓글 표시 영역 끝-->
			    </c:if>
			</c:forEach>
			<!-- 댓글 목록 끝  -->
        </div>
        <!-- 댓글 작성 및 목록 끝 -->
    </div>
    <!-- 신고 모달 폼 -->
    <div id="reportModal" class="modal">
	    <div class="modal-content">
	        <span class="close-button">&times;</span>
	        <h2>📢 신고</h2>
	        <p id="reportCommentText"></p>
	        <form id="reportForm" method="post">
	        	<input type="hidden" name="reportedContentType" value="">
	            <label for="reportReason">신고 사유:</label>
	            <textarea id="reportReason" name="reportReason" required></textarea>
	            <div class="reportButtonDiv">
	            	<button class="initButton active" type="submit">신고하기</button>
	        	</div>
	        </form>
	    </div>
	</div>
	<!-- 신고 모달 폼 끝 -->
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
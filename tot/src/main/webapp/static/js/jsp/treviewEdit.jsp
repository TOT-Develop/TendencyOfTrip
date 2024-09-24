<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/review.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/treview.js"></script>
    <title>여행 후기 수정</title>
</head>
<body>
    <div class="mainDiv">
        <div class="reviewTitleDiv">
            <div class="bigTitle">여행 후기 수정</div>
        </div>
        <div class="reviewIntroDiv">
            <div>나의 여행 후기글을 수정해보세요!</div>
        </div>
        <!-- 여행 후기 작성 폼 -->
        <div class="reviewForm">
            <form id="reviewForm" action="${pageContext.request.contextPath}/review/all/edit/${review.trevId}" method="post"
                enctype="multipart/form-data">
                
                <!-- 1. 여행 후기 제목 입력 -->
                <div class="formGroup">
                    <label for="reviewTitle">제목</label>
                    <input id="reviewTitle" type="text" class="titleInput" name="trevTitle" value="${review.trevTitle}">
                </div>

                <!-- 2.1 여행 항목 선택 -->
                <div class="formGroup">
                	<label for="trip">여행 항목</label>
                	<select id="travelTrip" class="treviewSelect" name="tripId">
                		<option value="" disabled selected>여행을 선택하세요</option>
                		<c:forEach var="trip" items="${trips}">
				            <option value="${trip.tripId}" <c:if test="${trip.tripId == review.tripId}">selected</c:if>>${trip.tripName}</option>
				        </c:forEach>
                	</select>
                </div>
                
                <!-- 2.2 해당 여행의 코스 정보 출력 -->
                <div class="formGroup">
				    <label for="travelCourse">여행 코스</label>
				    <div id="travelCourse" class="courseDetails">
				        <c:forEach var="course" items="${courses}" varStatus="courseStatus">
				            [Day ${courseStatus.index + 1}]
				            <c:forEach var="detail" items="${course.courseDetail}" varStatus="detailStatus">
				                <c:if test="${not empty detail.dname}">
				                    <span>${detail.dname}</span>
				                    <c:if test="${detailStatus.index < (fn:length(course.courseDetail) - 1)}">
				                        <span>&rarr;</span>
				                    </c:if>
				                </c:if>
				            </c:forEach>
				            <hr />
				        </c:forEach>
				    </div>
				</div>
				
				<!-- 3. 평점 입력 -->
				<div class="formGroup">
				    <div id="reviewRating" class="ratingDiv">
				    	<label>평점</label>
				    	<div class="ratingOptions">
					        <input type="radio" id="rating1" name="trevRating" value="1" <c:if test="${review.trevRating == 1}">checked</c:if> />
					        <label for="rating1">⭐</label>
					        <input type="radio" id="rating2" name="trevRating" value="2" <c:if test="${review.trevRating == 2}">checked</c:if> />
					        <label for="rating2">⭐⭐</label>
					        <input type="radio" id="rating3" name="trevRating" value="3" <c:if test="${review.trevRating == 3}">checked</c:if> />
					        <label for="rating3">⭐⭐⭐</label>
					        <input type="radio" id="rating4" name="trevRating" value="4" <c:if test="${review.trevRating == 4}">checked</c:if> />
					        <label for="rating4">⭐⭐⭐⭐</label>
					        <input type="radio" id="rating5" name="trevRating" value="5" <c:if test="${review.trevRating == 5}">checked</c:if> />
					        <label for="rating5">⭐⭐⭐⭐⭐</label>
				        </div>
				    </div>
				</div>
                
                <!-- 4. 여행 후기 내용 입력 -->
				<div class="reviewContentDiv">
				    <label for="reviewContent">후기 내용</label>
				    <div id="reviewContentAndImgDiv" class="reviewContentAndImgDiv">
				        <c:set var="contentParts" value="${fn:split(review.trevContent, ',')}" />
				        <c:choose>
				            <c:when test="${not empty review.trevImages}">
				                <c:set var="imageIndex" value="0" />
				            </c:when>
				        </c:choose>
				
				        <c:forEach var="part" items="${contentParts}">
				            <c:choose>
				                <c:when test="${fn:contains(part, 'image')}">
				                    <c:if test="${imageIndex lt review.trevImages.size()}">
				                        <c:set var="image" value="${review.trevImages[imageIndex]}" />
				                        <div class="img-wrapper">
				                            <img 
				                                src="${pageContext.request.contextPath}${image.trevImgpath}" 
				                                alt="Trip Review Image ${imageIndex + 1}" 
				                                name="reviewImage" 
				                                class="reviewImage" />
				                            <input type="hidden" name="trevContent" value="image" />
				                        </div>
				                        <c:set var="imageIndex" value="${imageIndex + 1}" />
				                    </c:if>
				                </c:when>
				                <c:otherwise>
				                    <input class="reviewContent" name="trevContent" value="${part}" />
				                </c:otherwise>
				            </c:choose>
				        </c:forEach>
				    </div>
				</div>
				
				<!-- 5. 이미지 파일 업로드 -->
				<div class="formGroup">
				    <label for="reviewImage">이미지 업로드</label>
				    <div class="reviewImageDiv">
				        <input id="reviewImage" type="file" name="image" multiple />
    				    <c:forEach var="image" items="${review.trevImages}">
				            <div class="file-input-wrapper">
				                <input type="text" name="reviewImage" value="${fn:substringAfter(image.trevImgpath, '/upload/')}" multiple="multiple" />
				                <button id="delImgBtn" type="button" class="initButton">삭제</button>
				            </div>
				        </c:forEach>
				    </div>
				</div>
                
                <!-- 6. 이용 약관 동의 -->
                <div class="formGroup">
                    <fieldset class="termsFieldset">
                        <legend>개인정보 수집 및 이용 동의</legend>
                        <label>
                            <input type="radio" name="trevAgree" value="agree" class="termsRadio" id="agreeRadio">
                            동의함
                        </label>
                        <label>
                            <input type="radio" name="trevAgree" value="disagree" class="termsRadio" id="disagreeRadio">
                            동의하지 않음
                        </label>
                    </fieldset>
                </div>
                
                <!-- 버튼 -->
                <div class="formGroup">
                    <div class="submitAndCancleBtnDiv">
                        <button id="editButton" type="button" class="initButton active">글 수정하기</button>
                        <button id="cancleButton" type="button" class="initButton">취소하기</button>
                    </div>
                </div>
            </form>
        </div>
        <!-- 여행 후기 작섬 폼 END -->
    </div>
</body>
</html>
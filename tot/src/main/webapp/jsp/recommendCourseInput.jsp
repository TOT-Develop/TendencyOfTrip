<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>recommendCourseInput</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/recommendCourseInput.css">
    <script src="${pageContext.request.contextPath}/static/js/recommendCourseInput.js"></script>
</head>
<body>
   	<jsp:include page="header.jsp"></jsp:include>
	<div id="wrapper">
       <div class="title-group">
               <div class="title-container">
                  <h2 class="strong-title">COURSE</h2>
                  <h3 class="strong-title">여행코스 추천을 위한 사전 질문입니다.</h3>
               </div>
               <div class="title-line"></div>
        </div>
    	<div class="form-container">
        <form method="post" action="${pageContext.request.contextPath}/planner">
			<div class="form-group">
			    <label for="mbti">MBTI</label>
			    <select id="mbti" name="mbti">
			        <option value="">MBTI</option>
			        <option value="ISTJ">ISTJ</option>
			        <option value="ISFJ">ISFJ</option>
			        <option value="INFJ">INFJ</option>
			        <option value="INTJ">INTJ</option>
			        <option value="ISTP">ISTP</option>
			        <option value="ISFP">ISFP</option>
			        <option value="INFP">INFP</option>
			        <option value="INTP">INTP</option>
			        <option value="ESTP">ESTP</option>
			        <option value="ESFP">ESFP</option>
			        <option value="ENFP">ENFP</option>
			        <option value="ENTP">ENTP</option>
			        <option value="ESTJ">ESTJ</option>
			        <option value="ESFJ">ESFJ</option>
			        <option value="ENFJ">ENFJ</option>
			        <option value="ENTJ">ENTJ</option>
			        <option value="ENTJ">없음</option>
			    </select>
			</div>
			<div class="line"></div>
			<div class="line"></div>
				<div class="form-group">
				    <label for="tramt">예산</label>
				        <input type="text" id="tramt" name="tramt" placeholder="예산">
				        <span class="currency">원</span>
				</div>
			<div class="line"></div>
			<div class="line"></div>
			<div class="form-group">
			    <label for="trpeople">인원수</label>
			    <select id="trpeople" name="trpeople">
			        <option value="">인원수 선택</option>
			        <option value="1">1명</option>
			        <option value="2">2명</option>
			        <option value="3">3명</option>
			        <option value="4">4명</option>
			    </select>
			</div>
			<div class="line"></div>
			<div class="line"></div>
            <div class="date-group">
	                <label for="trstadate">출발일</label>
	                <input type="date" id="trstadate" name="trstadate">
	                <span></span>
	                <label for="trenddate">도착일</label>
	                <input type="date" id="trenddate" name="trenddate">
            </div>
			<div class="line"></div>
			<div class="line"></div>
			<div class="form-group">
                <label for="trperiod">일정</label>
                <input type="text" id="trperiod" name="trperiod" placeholder="출발, 도착일이 없을때 입력 (예: 2박3일)">
            </div>
			<div class="line"></div>
			<div class="line"></div>
             <div class="form-group">
                <label>지역</label>
                <div class="button-group" id="area-group">
				    <input type="button" id="1" class="region" value="서울">
				    <input type="button" id="2" class="region" value="인천">
				    <input type="button" id="3" class="region" value="대전">
				    <input type="button" id="4" class="region" value="대구">
				    <input type="button" id="5" class="region" value="광주">
				    <input type="button" id="6" class="region" value="부산">
				    <input type="button" id="7" class="region" value="울산">
				    <input type="button" id="8" class="region" value="세종특별자치시">
				    <input type="button" id="31" class="region" value="경기도">
				    <input type="button" id="32" class="region" value="강원특별자치도">
				    <input type="button" id="33" class="region" value="충청북도">
				    <input type="button" id="34" class="region" value="충청남도">
				    <input type="button" id="35" class="region" value="경상북도">
				    <input type="button" id="36" class="region" value="경상남도">
				    <input type="button" id="37" class="region" value="전북특별자치도">
				    <input type="button" id="38" class="region" value="전라남도">
				    <input type="button" id="39" class="region" value="제주도">
				    <input type="button" id="40" class="region" value="전국">
				</div>
            </div>
			<div class="line"></div>
			<div class="line"></div>
            <div class="form-group">
                <label>음식</label>
                <div class="button-group" id="food-group">
                    <input type="button" id="k" class="food" value="한식">
                    <input type="button" id="c" class="food" value="중식">
                    <input type="button" id="w" class="food" value="양식">
                    <input type="button" id="j" class="food" value="일식">
                    <input type="button" class="food" value="기타">
                </div>
            </div>

            <div class="final-button-group">
                <button type="button" class="back-button">뒤로가기</button>
                <button type="submit" class="submit-button">확인</button>
            </div>
        </form>
    </div>
   </div> 
      	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>

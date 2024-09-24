<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width='device-width', initial-scale=1.0">
   	<script type="text/javascript"
        src="//dapi.kakao.com/v2/maps/sdk.js?appkey=자바스크립트 인증키"></script>
    <script defer src="${pageContext.request.contextPath}/static/js/trip.js"></script>
    <title>여행코스경로</title>
</head>
<body>
    <div class="container">
        <h1>여행 경로 표시 Map</h1>
        <div class="courseDiv" style="display: flex; justify-content: space-evenly;">
	        <div class="courseBtnDiv">
	        	<!-- 코스 개수만큼 코스 버튼이 생성되도록 수정 -->
	        	<button data-course="course001">코스 1</button>
	            <button data-course="course002">코스 2</button>
	            <button data-course="course003">코스 3</button>
	        </div>
	        <div class="courseMapDiv">
		        <div id="map" style="width:800px;height:600px;"></div>
		        <div id="clickLatlng"></div>
	        </div>
        </div>
    </div>
</body>
</html>
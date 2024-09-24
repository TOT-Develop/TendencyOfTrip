<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>여행 성향 분석 결과</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tendencyTestResult.css">
    <script src="${pageContext.request.contextPath}/static/js/tendencyTestResult.js" defer></script>
</head>
<script>
        const basePath = '${pageContext.request.contextPath}';
</script>
<body>
	<jsp:include page="/jsp/header.jsp"></jsp:include>
    <div class="result-container">
        <h1 id="testResultTitle">당신은 <span id="resultType"></span> 입니다.</h1>
        <img id="resultImage" src="" alt="Result Image" class="result-image">
        <p id="resultDescription" class="result-description">
            <!-- 결과 설명이 여기 표시됩니다. -->
        </p>
        <div class="recommendation-button">
        	<button id="saveResultBtn" class="save-button">결과 저장하기</button>
            내 성향에 맞는 여행지 추천 받기<button class="go-button" onclick="getRecommendation()">GO!</button>
        </div>
    </div>
</body>
</html>

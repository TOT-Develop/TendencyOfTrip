<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>planner</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/planner.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/planner.css">

</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
    <h1 class="plannerHead">여행 계획</h1>
    <!-- 세션에서 불러온 데이터 표시 -->
    <div id="h2s">
        <h2 id="h2tramt">코스 이름 :</h2>
        <p id="recommendationName"></p>
        <h2 id="h2mbti">MBTI :</h2>
        <p id="mbti"></p>
        <h2 id="h2tramt">총 예산 :</h2>
        <p id="tramt"></p>
    </div>

    
    <div class="container">
        <div class="pheader">
            <h1>제주</h1>
            <div>
                <div class="pdate"></div>
            </div>
        </div>

    </div>
    
    
    <div class="butdiv">
        <button class="backbtn">메인으로</button>
        <button class="okbtn">저장하기</button>
    </div>
    
    

</body>
</html>
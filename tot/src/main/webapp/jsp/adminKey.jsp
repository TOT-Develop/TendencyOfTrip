<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>key</title>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="${pageContext.request.contextPath}/static/js/adminKey.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/adminKey.css" />
</head>
<%@ include file="adminMenu.jsp" %>
<body>
			<div class="keyHeader">
				<h1 class="keyHeader">api key</h1>
			</div>
    <table>
	    <thead>
	        <tr>
	            <th>키 이름</th>
	            <th>키 상태</th>
	        </tr>
	    </thead>
	    <tbody>
	        <tr>
	            <td>Kakao 지도 API</td>
	            <td><span id="map-status" class="status-circle"></span></td>
	        </tr>
	        <tr>
	            <td>Kakao 이미지 검색 API</td>
	            <td><span id="image-status" class="status-circle"></span></td>
	        </tr>
	        <tr>
	         <td>OpenAI Chat API</td>
                <td>
                    <span class="status-circle 
                        ${apiStatus.equals('OpenAI 키 상태: 정상') ? 'status-ok' : 'status-error'}">
                    </span>
                </td>
             </tr>   
             <tr>
                <td>Google OAuth</td>
                <td><span id="google-status" class="status-circle"></span></td>
            </tr>
            <tr>
                <td>Naver OAuth</td>
                <td><span id="naver-status" class="status-circle"></span></td>
            </tr>
            <tr>
                <td>Kakao OAuth</td>
                <td><span id="kakao-status" class="status-circle"></span></td>
            </tr>   
	    </tbody>
	</table>
</body>
</html>
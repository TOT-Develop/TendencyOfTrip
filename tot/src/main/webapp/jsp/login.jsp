<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trick or Trip</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/login.css"/>
    
    <!-- 닉네임 입력 모달창 스크립트 -->
	<script type="text/javascript">
	    function showModal() {
	        document.getElementById('nicknameModal').style.display = 'block';
	    }
	
	    function closeModal() {
	        document.getElementById('nicknameModal').style.display = 'none';
	    }
	
	    window.onload = function() {
	        
	        // 서버에서 전달된 loginSuccess 값이 제대로 전달됐는지 콘솔에 출력
            var loginSuccess = '<%= request.getAttribute("loginSuccess") %>';

            // loginSuccess가 true이면 모달창을 띄움
            if (loginSuccess === 'true') {
                showModal();
            }
        };
	</script>
</head>
<body>
    <main>
        <div id="loginWrapper">
            <div id="loginCenter">
                <div id="loginText">
                    <h3>LOGIN</h3>
                    <p>간편 로그인으로 시작해보세요!</p>
                </div>
                <div id="loginImg">
                    <ul>
                        <!-- 구글 로그인 버튼 -->
                        <li><a href="${google_url}"><img src="${pageContext.request.contextPath}/static/image/google.png" alt="google"/></a></li>
                        <!-- 네이버 로그인 버튼 -->
                        <li><a href="${naver_url}"><img src="${pageContext.request.contextPath}/static/image/naver.png" alt="naver"/></a></li>
                        <!-- 카카오 로그인 버튼 -->
                        <li><a href="${kakao_url}"><img src="${pageContext.request.contextPath}/static/image/kakao.png" alt="kakao"/></a></li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- 닉네임 입력 모달창 -->
        <div id="nicknameModal" class="modal">
            <div class="modal-content">
                <h3>닉네임을 입력하세요</h3>
                
                                <!-- 에러 메시지 출력 부분 -->
                <c:if test="${not empty errorMessage}">
                    <p style="color:red;">${errorMessage}</p>
                </c:if>
                
                <form action="${pageContext.request.contextPath}/saveNickname" method="post">
                    <label for="nickname">닉네임:</label>
                    <input type="text" id="nickname" name="nickname" required>
                    <button type="submit">저장</button>
                    <button type="button" onclick="closeModal()">닫기</button>
                </form>
            </div>
        </div>
    </main>
</body>
</html>

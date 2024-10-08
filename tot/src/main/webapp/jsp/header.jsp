<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <title>Trick or Trip</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/header.css"/>
    <script src="${pageContext.request.contextPath}/static/js/header.js"></script>
</head>

<body>
    <header id="header">
        <div id="headerWrapper">
            <div id="headerLogo">
                <h1 id="h1"><a href="/tot/"><img src="${pageContext.request.contextPath}/static/image/header_logo.png" alt="headerLogo"/></a></h1>
            </div>
            <div id="headerRight">
                <div id="headerNav">
                    <nav id="nav">
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/trip/list">PLANNER</a></li>
                            <li><a href="${pageContext.request.contextPath}/qna/1/1">QNA</a></li>
                            <li><a href="${pageContext.request.contextPath}/review/1/1">REVIEW</a></li>
                            <li><a href="${pageContext.request.contextPath}/notice/1">NOTICE</a></li>
                        </ul>
                    </nav>
                </div>
                <div id="headerLogin">
					<c:choose>
					    <c:when test="${not empty sessionScope.member}">
					        <p>
						        ${sessionScope.member.memNick}님 환영합니다!
						         <button onclick="showNicknameModal()">닉네임 변경</button>
						        <a href="${pageContext.request.contextPath}/logout">LOGOUT</a>
					        </p>
					    </c:when>
					    <c:otherwise>
					        <p><a href="${pageContext.request.contextPath}/login">LOGIN</a></p>
					    </c:otherwise>
					</c:choose>                  	
                </div>
            </div>
        </div>
        <!-- 닉네임 변경 모달 -->
		<div id="nicknameModal" style="display:none;">
		    <div class="modal-content">
		        <span class="close" onclick="closeNicknameModal()">&times;</span>
		        <h2>닉네임 변경</h2>
		        <form id="nicknameForm">
		            <input type="text" id="newNickname" placeholder="새로운 닉네임을 입력하세요" required />
		            <button type="submit">변경하기</button>
		        </form>
		    </div>
		</div>
    </header>
</body>
</html>
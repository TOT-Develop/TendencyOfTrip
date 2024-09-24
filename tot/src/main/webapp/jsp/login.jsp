<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="tot.domain.MemberVO"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trick or Trip</title>
    <link rel="stylesheet" href="../static/css/global.css"/>
    <link rel="stylesheet" href="../static/css/login.css"/>
</head>
<%
	MemberVO member = new MemberVO("user123", "홍길동", "hong@naver.com", "M01", "MB02", "TT02", null, null, null, null, null);

	session.setAttribute("member", member);

	out.print("회원 정보 저장완료");
%>
<body>
	<jsp:include page="header.jsp"></jsp:include>
    <main>
        <div id="loginWrapper">
            <div id="loginCenter">
                <div id="loginText">
                    <h3>LOGIN</h3>
                    <p>간편 로그인으로 시작해보세요!</p>
                </div>
                <div id="loginImg">
                    <ul>
                        <li><a href="#"><img src="../static/image/google.png" alt="google"/></a></li>
                        <li><a href="#"><img src="../static/image/naver.png" alt="naver"/></a></li>
                        <li><a href="#"><img src="../static/image/kakao.png" alt="kakao"/></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </main>
    	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
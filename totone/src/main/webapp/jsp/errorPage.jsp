<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/errorPage.css" />
<title>에러 발생 페이지</title>
</head>
<body>
	<h1>서버 오류</h1>
	<p>${message}</p>
	<a href="/tot">홈으로 돌아가기</a>
</body>
</html>
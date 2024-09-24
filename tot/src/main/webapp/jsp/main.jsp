<%@ page language="java" contentType="text/html; charset=EUC-KR"
pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trick or Trip</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css"/>
</head>
<body>

	<jsp:include page="/jsp/header.jsp"></jsp:include>
    <main id="main">
        <div id="mainWrapper">
            <div id="mainFirst">
                <div id="mainFirstText">
                    <h2>나의 성향에 따라<br />떠나는 여행가이드</h2>
                    <p>MBTI별로 추천해주는 여행코스를 즐겨보세요</p>
                </div>
                <div id="mainFirstBtn">
                    <a href="${pageContext.request.contextPath}/tendency">
				        <input type="button" value="TEST START" />
				    </a>
                </div>
            </div>
            <div id="mainSecond">

            </div>
        </div>
    </main>
<jsp:include page="/jsp/footer.jsp"></jsp:include>
</body>
</html>
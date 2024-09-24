<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script defer src="${pageContext.request.contextPath}/static/js/tripList.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tripList.css">
    <title>내 여행 목록</title>
    <script>
        // 세션에 저장된 memid를 JavaScript 변수로 전달
        const memId = '<%= session.getAttribute("memId") %>';
        console.log(memId);
    </script>
</head>
<body>
    <div class="container">
        <h1>저장된 여행 목록</h1>
        <div id="trip-list-container">
            <!-- 여행 리스트가 동적으로 추가될 공간 -->
        </div>
        <div class="butdiv">
            <button class="backbtn">메인으로</button>
        </div>
    </div>
</body>
</html>

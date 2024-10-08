<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trick or Trip</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/main.js"></script>
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
    <main id="main">
        <div id="main1">
            <div id="mainFirst">
                <div id="mainFirstText">
                    <h2>나의 성향에 따라<br />떠나는 여행가이드</h2>
                    <p>MBTI별로 추천해주는 여행코스를 즐겨보세요</p>
                </div>
                <div id="mainFirstBtn">
                    <input type="button" value="TEST START" onclick="checkTestStatus()" />
                </div>
            </div>
            <!-- 모달창 -->
            <div id="testModal" class="modal">
                <div class="modal-content">
                <div class="images">
                  <img src="${pageContext.request.contextPath}/static/image/header_logo.png" alt="headerLogo"/>
                </div>
                    <span class="close" onclick="closeModal()">&times;</span>
                    <h2>이미 테스트 결과가 있습니다.</h2>
                    <h2>다시 테스트를 받으시겠습니까?</h2>
                    <button id="retestBtn">테스트 다시 받기</button>
                    <button id="courseBtn">여행 코스 추천 받기</button>
                </div>
            </div>

    </div>

    <div id="main2">
        <div id="mainSecond">
           <div id="mainSecond2">
               <p>성향 테스트 부터 나에게 맞는 추천코스 까지</p>
               <p>Let's go on a trip together !</p>
           </div>
        </div>
    </div>

    <div id="main3">
        <h2>How To Use</h2>
            <div class="images">        
                <video id="video" controls autoplay muted loop>
                     <source src="${pageContext.request.contextPath}/static/video/howtouse.mp4" type="video/mp4" />
              </video>
           </div>
    </div>
    </main>
    <script type="text/javascript">
        // JSP에서 서버로부터 받은 memberTT 값을 JavaScript 변수에 할당
        const memberTT = "${member != null ? member.member_tt : ''}";
        console.log('memberTT:', memberTT);
    </script>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
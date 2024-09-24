<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trick or Trip</title>
    <link rel="stylesheet" href="../static/css/global.css"/>
    <link rel="stylesheet" href="../static/css/main.css"/>
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
                    <input type="button" value="TEST START"/>
                </div>
            </div>
        </div>
        
        <div id="main2">
            <div id="mainSecond">
            	<p>성향 테스트 부터 나에게 맞는 추천코스 까지</p>
            	<p>Let's go on a trip together</p>
            </div>
        </div>
        
        <div id="main3">
        	<h2>How To Use</h2>
        	<div id="step1" class="step">
        		<div class="texts">
        			<b>STEP 1</b>
        			<p>테스트 하는 영상 -></p>
        		</div>
        		<div class="images"><img src="../static/image/browser.png" /></div>
        	</div>
        </div>
        
        <div id="main4">
        	<h2></h2>
        </div>
        
    </main>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tendencyTest.css" />
    <title>성향 분석</title>
</head>
<body>
	<jsp:include page="/jsp/header.jsp"></jsp:include>
    <h1 id="testTitle">성향 분석 설문</h1>
    <div id="questions">
        <div class="question active" id="question1">
            <p>일년에 한번밖에 없는 여름 휴가! 떠나고 싶은 곳은?</p>
            <img alt="" src="${pageContext.request.contextPath}/static/image/testImg1.png">
            <div class="button-group">
                <button onclick="selectAnswer('귀차니즘:4', 1)">쉬는게 최고! 숙소에서 즐기는 휴가</button>
                <button onclick="selectAnswer('낭만주의자:3,실속주의자:1,자유로운영혼:2', 1)">트렌드를 놓칠 수 없지 무조건 인스타 핫플!</button>
                <button onclick="selectAnswer('탐험가:4,자유로운영혼:4', 1)">목적지가 있는 것보다, 발길 닿는 곳이 더 매력적!</button>
                <button onclick="selectAnswer('힐링여행자:5,낭만주의자:1', 1)">힐링여행자이 필요해! 도심을 벗어나 자연을 즐기고 싶어</button>
            </div>
        </div>

        <div class="question" id="question2">
            <p>여행갈 때 짐을 챙기는 스타일과 가장 가까운 것은?</p>
            <img alt="" src="${pageContext.request.contextPath}/static/image/testImg2.png">        
            <div class="button-group">
                <button onclick="selectAnswer('완벽주의자:4,낭만주의자:1', 2)">모든 상황을 대비해서.. 무조건 풀 캐리어!</button>
                <button onclick="selectAnswer('탐험가:3,힐링여행자:1', 2)">짐은 최대한 가볍게, 배낭 하나면 OK!</button>
                <button onclick="selectAnswer('자유로운영혼:3,귀차니즘:1', 2)">지갑, 핸드폰만 있으면 GO!</button>
            </div>
        </div>

        <div class="question" id="question3">
            <p>여행갈 때 돈 + 1가지 아이템만 챙길 수 있다면?</p>
            <img alt="" src="${pageContext.request.contextPath}/static/image/testImg3.png">
            <div class="button-group">
                <button onclick="selectAnswer('낭만주의자:4,힐링여행자:3', 3)">사진기</button>
                <button onclick="selectAnswer('탐험가:4', 3)">지도</button>
                <button onclick="selectAnswer('완벽주의자:4,실속주의자:2', 3)">여행계획표</button>
                <button onclick="selectAnswer('귀차니즘:4,자유로운영혼:2', 3)">아무것도 필요없어!</button>
            </div>
        </div>

        <div class="question" id="question4">
            <p>여행지에서 주로 이용하는 이동수단은?</p>
            <img alt="" src="${pageContext.request.contextPath}/static/image/testImg4.png">
            <div class="button-group">
                <button onclick="selectAnswer('귀차니즘:4,힐링여행자:1', 4)">걷는 건 힘들어 ㅜㅜ 무조건 자동차로!</button>
                <button onclick="selectAnswer('낭만주의자:4,탐험가:1', 4)">낭만주의자이 최고지.. 천천히 둘러볼 수 있는 도보/자전거</button>
                <button onclick="selectAnswer('실속주의자:3,완벽주의자:1', 4)">난 운전은 못하니까 대중교통으로!</button>
            </div>
        </div>

        <div class="question" id="question5">
            <p>마트에서 장을 볼 때 일회용품은?</p>
            <img alt="" src="${pageContext.request.contextPath}/static/image/testImg5.png">
            <div class="button-group">
                <button onclick="selectAnswer('귀차니즘:4,자유로운영혼:4', 5)">설거지가 세상에서 가장 귀찮으니까.. 전부 일회용품으로 산다.</button>
                <button onclick="selectAnswer('실속주의자:3,낭만주의자:1', 5)">플라스틱 괜히 찝찝하니까.. 그냥 숙소에 있는 식기를 사용한다.</button>
                <button onclick="selectAnswer('완벽주의자:4,실속주의자:2,힐링여행자:1', 5)">재사용 가능한 제품은 이미 집에서 챙겨와서 구매할 필요가 없다.</button>
            </div>
        </div>

        <div class="question" id="question6">
            <p>여행지에서 소비 패턴은?</p>
            <img alt="" src="${pageContext.request.contextPath}/static/image/testImg6.png">
            <div class="button-group">
                <button onclick="selectAnswer('자유로운영혼:4,귀차니즘:2', 6)">"여행은 FLEX.." 어차피 놀러온 거니까, 펑펑 쓴다</button>
                <button onclick="selectAnswer('완벽주의자:3,힐링여행자:1', 6)">"영수증 주세요" 아끼진 않지만, 정해진 예산에 맞춰서 쓴다</button>
                <button onclick="selectAnswer('실속주의자:3,낭만주의자:1', 6)">"와 이 가격이면.." 최대한 아껴가며, 최적의 가성비를 찾는다.</button>
            </div>
        </div>

        <div class="question" id="question7">
            <p>여행지에 딱 1가지 고민거리를 덜어내고 돌아올 수 있다면?</p>
            <img alt="" src="${pageContext.request.contextPath}/static/image/testImg7.png">
            <div class="button-group">
                <button onclick="selectAnswer('힐링여행자:4,완벽주의자:1', 7)">건강</button>
                <button onclick="selectAnswer('탐험가:3,힐링여행자:1,자유로운영혼:1', 7)">인간관계</button>
                <button onclick="selectAnswer('완벽주의자:3,자유로운 영혼:1', 7)">진로</button>
                <button onclick="selectAnswer('실속주의자:3,귀차니즘:1', 7)">금전</button>
                <button onclick="selectAnswer('낭만주의자:3,완벽주의자:1', 7)">연애</button>
                <button onclick="selectAnswer('자유로운영혼:3,낭만주의자:1', 7)">고민같은건 없어!</button>
            </div>
        </div>
    </div>

    <div id="result"></div>
    <jsp:include page="/jsp/footer.jsp"></jsp:include>
    <script src="${pageContext.request.contextPath}/static/js/tendencyTest.js"></script>
</body>
</html>

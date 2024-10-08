<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script
        src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=380ae83d33ff0006270a1f7b2d257ebf&libraries=services"></script>
    <script defer src="${pageContext.request.contextPath}/static/js/trip.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/trip.css">
    <title>여행코스경로</title>
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
    <div class="container">
        <h1 id="tripH1">여행 경로 Map
            <button id="toggle-course-btn" class="toggle-course-btn">코스만 보기</button>
        </h1>
        <div class="pheader">
            <div>
                <div class="pdate"></div>
            </div>
        </div>
        <div class="main-content">
            <div id="courses-container"></div>

            <div class="courseDiv">
                <div class="courseBtnDiv" id="course-buttons"></div>
                <div class="courseMapDiv">
                    <div id="map" style="width:900px;height:900px;position:absolute;"></div>
                    <div id="clickLatlng"></div>
                </div>
            </div>

            <!-- 축제 정보를 표시할 컨테이너 추가 -->
            <div id="festivals-container"></div>
            <!-- Date Selection Modal -->
            <div id="date-selection-modal" class="modal hidden">
                <div class="modal-content">
                    <span class="close-btn">&times;</span>
                    <h2>날짜 선택</h2>
                    <input type="date" id="selected-date">
                    <button id="confirm-date-btn">확인</button>
                </div>
            </div>
        </div>

        <!-- Modal HTML 구조 -->
        <div id="locationModal" class="locationModal" style="display: none;">
            <div class="locationModal-content">
                <span class="close" id="closeModal">&times;</span>
                <h5 class="modal-header">장소 선택</h5>
                <select id="locationTypeSelect">
                    <option value="">전체</option>
                    <option value="호텔">호텔</option>
                    <option value="식당">식당</option>
                    <option value="관광지">관광지</option>
                </select>
                <input type="text" id="locationSearchInput" placeholder="검색어를 입력하세요">
                <button id="searchBtn">검색</button>
                <button id="resetBtn">초기화</button>
                <div id="locationList"></div>
                <div id="pagination"></div>
            </div>
        </div>

        <div class="butdiv">
            <button id="cancel-button" class="backbtn"
                onclick="window.location.href='${pageContext.request.contextPath}/trip/list'">목록으로</button>
            <button id="save-button" class="okbtn">저장하기</button>
        </div>
    </div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>

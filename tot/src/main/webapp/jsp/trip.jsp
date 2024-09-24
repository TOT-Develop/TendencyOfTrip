<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   	<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=380ae83d33ff0006270a1f7b2d257ebf&libraries=services"></script>
    <script defer src="${pageContext.request.contextPath}/static/js/trip.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/trip.css">
    <title>여행코스경로</title>
</head>
<body>
    <div class="container">
        <h1>여행 경로 표시 Map</h1>
        <div class="pheader">
            <h1></h1>
            <div>
                <div class="pdate"></div>
            </div>
            <button id="toggle-course-btn" class="toggle-course-btn">코스만 보기</button>
        </div>
        <div class="main-content">
            <div id="courses-container" ></div>
            
            <div class="courseDiv">
                <div class="courseBtnDiv" id="course-buttons"></div>
                <div class="courseMapDiv">
                    <div id="map" style="width:800px;height:600px;position:absolute;"></div>
                    <div id="clickLatlng"></div>
                </div>
            </div>

            <!-- 축제 정보를 표시할 컨테이너 추가 -->
            <div id="festivals-container">
            	<div class="timeline-container"></div>
            </div>
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
        
        <div class="butdiv">
            <button class="backbtn" onclick="window.location.href='${pageContext.request.contextPath}/trip/list'">목록으로</button>
            <button id="save-btn" class="okbtn">저장하기</button>
        </div>
    </div>
    <script>
        $(document).ready(function() {
                $('.timeline-container').sortable({
                    connectWith: '.timeline-container',
                    items: '.timeline-item',
                    placeholder: 'ui-state-highlight',
                    cursor: 'move',
                    start: function(event, ui) {
                        ui.placeholder.height(ui.item.outerHeight());
                    },
                    update: function(event, ui) {
                        console.log("타임라인 아이템 변경됨:", ui.item.data('number'));
                    },
                    receive: function(event, ui) {
                        console.log("타임라인 아이템을 받음:", ui.item.data('number'));
                    }
                }).disableSelection();
        });
    </script>
</body>
</html>

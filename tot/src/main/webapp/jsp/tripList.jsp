<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script defer src="${pageContext.request.contextPath}/static/js/tripList.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/tripList.css">
    <title>내 여행 목록</title>
</head>
<body>
   <jsp:include page="header.jsp"></jsp:include>
    <div class="container">
        <h1>저장된 여행 목록</h1>
        <!-- 여행 목록 리스트 -->
        <c:choose>
            <c:when test="${not empty trips}">
                <div id="trip-list-container">
                    <!-- trips 리스트의 각 항목을 출력 -->
                    <c:forEach var="trip" items="${trips}" varStatus="status">
                        <div class="trip-item" data-trip-id="${trip.tripId}" 
                             style="background-image: url('${trip.regionImageUrl != null ? trip.regionImageUrl : 'default-image.jpg'}');">
                            <div class="trip-info">
                                <h2>${status.index + 1 + (currentPage - 1) * pageSize}  ${trip.regionName}</h2> <!-- 페이지에 맞는 인덱스 계산 -->
                                <p>예상 비용: ${trip.formattedTrAmt != null ? trip.formattedTrAmt : trip.trAmt}</p>
                                <p>여행 기간: ${trip.displayPeriod != null ? trip.displayPeriod : trip.trPeriod}</p>
                                <p>인원 수: ${trip.trPeople}</p>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                
               <!-- 페이지네이션 UI -->
            <div class="pagination">
                <!-- 이전 페이지 버튼 -->
                <c:if test="${currentPage > 1}">
                    <button onclick="location.href='?page=${currentPage - 1}&pageSize=${pageSize}'" class="prev-page">이전</button>
                </c:if>
                
                <!-- 페이지 번호들 표시 -->
                <c:forEach var="i" begin="1" end="${totalPages}">
                    <button 
                        onclick="location.href='?page=${i}&pageSize=${pageSize}'" 
                        class="${i == currentPage ? 'active' : ''}">
                        ${i}
                    </button>
                </c:forEach>
                
                <!-- 다음 페이지 버튼 -->
                <c:if test="${currentPage < totalPages}">
                    <button onclick="location.href='?page=${currentPage + 1}&pageSize=${pageSize}'" class="next-page">다음</button>
                </c:if>
            </div>
                
            </c:when>
            <c:otherwise>
                <p>저장된 여행 목록이 없습니다.</p>
            </c:otherwise>
        </c:choose>
        <div class="butdiv">
             <button class="backbtn">메인으로</button>
        </div>
    </div>

    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>

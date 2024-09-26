<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Ban User</title>
  <link rel="stylesheet" href="../static/css/global.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
   <script defer src="${pageContext.request.contextPath}/static/js/banUser.js"></script>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/banUser.css">
</head>
<jsp:include page="/jsp/adminMenu.jsp"></jsp:include>
<body>
    <div id="banUserContainer">
        <h1>회원 제재</h1>
        <div>
            <p>회원 ID: <span id="memId">${member.memId}</span></p>
            <p>회원 이메일: <span id="memEmail">${member.memEmail}</span></p>
            <label for="banReason">제재 사유:</label>
            <select id="banReason">
                <option value="" disabled selected>사유를 선택하세요</option>
                <option value="부적절한 행동">부적절한 행동</option>
                <option value="서비스 이용 약관 위반">서비스 이용 약관 위반</option>
                <option value="기타">기타</option>
            </select>
            <div id="otherReasonContainer" style="display: none;">
                <label for="otherReason">기타 사유:</label>
                <input type="text" id="otherReason" placeholder="기타 사유를 입력하세요" />
            </div>
            <button id="banButton">제재</button>
        </div>
        <!-- 제재 해제 섹션 추가 -->
        <c:if test="${member.member_status == 'M02'}">
        <div>
            <h2>제재 해제</h2>
            <label for="liftReason">제재 해제 사유:</label>
            <select id="liftReason">
                <option value="" disabled selected>사유를 선택하세요</option>
                <option value="정상적인 행동">정상적인 행동</option>
                <option value="오해 해소">오해 해소</option>
                <option value="기타">기타</option>
            </select>
            <div id="otherLiftReasonContainer" style="display: none;">
                <label for="otherLiftReason">기타 사유:</label>
                <input type="text" id="otherLiftReason" placeholder="기타 사유를 입력하세요" />
            </div>
        <button id="liftButton">제재 해제</button>
        </div>
        </c:if>
        <div>
            <h2>제재 내역</h2>
            <table border="1" width="100%" id="banHistoryTable">
                <thead>
                    <tr>
                        <th>제재 시작일</th>
                        <th>제재 종료일</th>
                        <th>제재 사유</th>
                        <th>해제 사유</th>
                        <th>해제 일시</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="history" items="${banHistoryList}">
                        <tr>
                            <td>${history.banStart}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${history.banEnd != null}">
                                    ${history.banEnd}
                                    </c:when>
                                    <c:otherwise>
                                    제재 중
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${history.banReason}</td>
                            <td>${history.liftReason}</td>
                            <td>${history.banLifted}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
     </div>
  </div>
</body>
</html>


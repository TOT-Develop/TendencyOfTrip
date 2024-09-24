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
</head>
<body>
  <h1>회원 제재</h1>
  <div>
    <p>회원 ID: <span id="memId">${member.memId}</span></p>
    <p>회원 이메일: <span id="memEmail">${member.email}</span></p> <!-- 이메일 표시 -->
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
  <div>
	<h2>제재 내역</h2>
    <table border="1" width="100%">
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
   <script>
    // 제재 버튼 클릭 이벤트 처리
    $('#banButton').click(function() {
      const memId = $('#memId').text();
      const memEmail = $('#memEmail').text();
      const banReason = $('#banReason').val();
      let reasonToSend = banReason;

      if (banReason === '기타') {
        reasonToSend = $('#otherReason').val();
      }

      if (reasonToSend) {
        $.ajax({
          url: '/tot/admin/banUserProc',
          method: 'POST',
          data: { id: memId, email: memEmail, reason: reasonToSend },
          success: function(response) {
            alert('제재가 완료되었습니다.');
            window.location.href = '/tot/admin/adminUser'; // 이 페이지에서 제재 내역을 보여주도록 리다이렉트
          },
          error: function() {
            alert('제재 중 오류가 발생했습니다.');
          }
        });
      } else {
        alert('제재 사유를 선택해야 합니다.');
      }
    });
  </script>


</body>
</html>

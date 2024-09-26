<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>닉네임 입력</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h3>닉네임을 입력하세요</h3>
    <form id="nicknameForm">
        <input type="text" id="nickname" placeholder="닉네임을 입력하세요" required>
        <button type="button" onclick="submitNickname()">확인</button>
    </form>

    <script type="text/javascript">
        function submitNickname() {
            var nickname = document.getElementById('nickname').value;
            if (nickname) {
                // Ajax로 서버에 닉네임 전송
                $.ajax({
                    url: '${pageContext.request.contextPath}/saveNickname',  // 닉네임 저장 요청
                    type: 'POST',
                    data: { nickname: nickname },
                    success: function(response) {
                        if (response === 'success') {
                            alert('닉네임이 저장되었습니다.');
                            window.opener.location.href = '${pageContext.request.contextPath}/main.jsp';  // 부모창을 메인 페이지로 이동
                            window.close();  // 팝업창 닫기
                        } else {
                            alert('닉네임 저장 중 오류가 발생했습니다.');
                        }
                    },
                    error: function() {
                        alert('닉네임 저장 중 오류가 발생했습니다.');
                    }
                });
            } else {
                alert('닉네임을 입력하세요.');
            }
        }
    </script>
</body>
</html>

$(document).ready(function() {
    // 닉네임 변경 모달 열기
    window.showNicknameModal = function() {
        $("#nicknameModal").show();  // 모달 창을 표시
    };

    // 닉네임 변경 모달 닫기
    window.closeNicknameModal = function() {
        $("#nicknameModal").hide();  // 모달 창을 숨기기
    };

    // 닉네임 변경 폼 제출 처리
    $("#nicknameForm").submit(function(e) {
        e.preventDefault();  // 폼 기본 제출 동작 막기

        const newNickname = $("#newNickname").val();  // 입력된 닉네임 가져오기
		console.log(newNickname);
        if (!newNickname) {
            alert("닉네임을 입력해주세요.");
            return;
        }

        // AJAX 요청으로 닉네임 변경 처리
        $.ajax({
            url: '/tot/member/changeNickname',  // 닉네임 변경 요청 URL
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ newNickname: newNickname }),  // 입력된 닉네임을 서버로 전송
            success: function(response) {
                alert("닉네임이 변경되었습니다.");
                location.reload();  // 성공 시 페이지 새로고침
            },
            error: function(xhr) {
                if (xhr.status === 400) {
                    alert("중복된 닉네임입니다. 다른 닉네임을 입력해주세요.");
                } else {
                    alert("닉네임 변경에 실패했습니다.");
                }
            }
        });
    });
    
});

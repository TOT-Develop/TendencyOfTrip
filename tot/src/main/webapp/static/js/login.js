// 정규식 정의

window.showModal = function() {
    $("#nicknameModal").show();
    $("#nicknameModalOverlay").show();  // 어두운 배경 표시
};

window.closeModal = function() {
    $("#nicknameModal").hide();
    $("#nicknameModalOverlay").hide();  // 어두운 배경 숨기기
};

const regex = {
    nickname: /^[가-힣a-zA-Z0-9_]{2,10}$/, // 닉네임: 2~10자의 한글, 영문, 숫자, _
};

// 유효성 검사 함수
const validateNickname = () => {
    const nicknameInput = $('#nickname');
    const errorMessage = $('#error-message');
    let isValid = true;

    // 닉네임 입력 시마다 검사
    nicknameInput.on('input', function() {
        const nickname = $(this).val();

        // 닉네임이 정규식에 맞지 않으면 에러 메시지 표시 및 유효성 검사 실패 처리
        if (!regex.nickname.test(nickname)) {
            errorMessage.text('닉네임은 2~10자의 한글, 영문, 숫자만 사용할 수 있습니다.');
            errorMessage.show();
            isValid = false;
        } else {
            // 유효한 닉네임이면 에러 메시지 숨김 및 유효성 검사 성공 처리
            errorMessage.hide();
            isValid = true;
        }
    });

    return isValid;
};

// 폼 전송 시 유효성 검사
const submitForm = (event) => {
    if (!validateNickname()) {
        // 유효성 검사가 실패하면 폼 전송 막음
        event.preventDefault();
    }
};



// 페이지 로드 시 유효성 검사 활성화 및 폼 전송 차단 설정
$(document).ready(function() {
    $('form').on('submit', submitForm);
    validateNickname();
});

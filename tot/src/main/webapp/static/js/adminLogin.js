$(document).ready(() => {
  $('#adminLogin').on('click', (event) => {
    event.preventDefault(); // 기본 폼 제출 방지

    const id = $('#id').val();
    const password = $('#pass').val();

    // 간단한 유효성 검사
    if (!id || !password) {
      alert("ID와 비밀번호를 입력하세요.");
      return;
    }

    // 서버로 데이터 전송
    $.ajax({
      url: `${window.location.origin}/tot/admin/adminLoginProc`,
      type: 'POST',
      data: {
        id: id,
        pass: password
      },
      success: (data, textStatus, jqXHR) => {
        // 302 상태 코드 확인 후 Location 헤더에서 리다이렉트 처리
        if (jqXHR.status === 200 || jqXHR.status === 302) {
          const redirectUrl = jqXHR.getResponseHeader('Location');
          if (redirectUrl) {
            console.log(`Redirecting to: ${redirectUrl}`);
            window.location.href = redirectUrl; // 수동으로 리다이렉트
          } else {
            alert("로그인 실패: 잘못된 ID 또는 비밀번호입니다.");
          }
        }
      },
      error: (jqXHR, textStatus, errorThrown) => {
        console.error('Error:', errorThrown);
        alert("서버 오류가 발생했습니다. 다시 시도해 주세요.");
      }
    });
  });
});

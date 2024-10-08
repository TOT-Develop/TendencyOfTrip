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
			url: `/tot/admin/adminLoginProc`,
			type: 'POST',
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8', // 추가
			data: {
				id: id,
				pass: password
			},
			success: (data, textStatus, jqXHR) => {
				window.location.href = "/tot/admin/adminUser"; // 로그인 성공 후 리다이렉트
			},
			error: (jqXHR, textStatus, errorThrown) => {
				alert(jqXHR.responseText); // 에러 메시지 출력
			}
		});

	});
});

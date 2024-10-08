window.onload = function () {
	$('#logOut').on('click', () => {
		// 로그아웃 요청을 서버에 보냄
		$.ajax({
			url: `${window.location.origin}/tot/admin/adminLogout`,
			type: 'GET',
			success: (data, textStatus, jqXHR) => {
				// 성공적으로 로그아웃되면 로그인 페이지로 리다이렉트
				window.location.href = `${window.location.origin}/tot/admin/adminLogin`;
			},
			error: (jqXHR, textStatus, errorThrown) => {
				console.error('Error during logout:', errorThrown);
				alert("로그아웃에 실패했습니다. 다시 시도해 주세요.");
			}
		});
	});
	$("#user").on('click', function () {
		window.location.href = "/tot/admin/adminUser";
	})
	$("#qna").on('click', function () {
		window.location.href = "/tot/admin/qna/1/1";
	})
	$("#notice").on('click', function () {
		window.location.href = "/tot/admin/notice";
	})
	$("#tripreview").on('click', function () {
		window.location.href = "/tot/admin/review/1/1";
	})
	$("#reviewcomment").on('click', function () {
		window.location.href = "/tot/admin/comment/1/1";
	})
	$("#report").on('click', function () {
		window.location.href = "/tot/admin/report/1/1";
	})
	$("#key").on('click', function () {
		window.location.href = "/tot/admin/key";
	})
}

$(document).ready(function() {
    // 글쓰기 폼 제출 시 처리
	    $('#submitBtn').click(function(e) {
	    e.preventDefault();
	
	    var notitle = $('#notitle').val();
	    var notext = editor.getData();  // CKEditor의 데이터를 가져옴
	
	    if (!notitle || !notext) {  // 제목과 본문이 비어있는지 확인
	        alert("제목과 본문을 입력하세요.");
	        return;
	    }
	
	    // HTML 태그 제거
	    var plainText = notext.replace(/<\/?[^>]+(>|$)/g, "");
	
	    var newNotice = {
	        notitle: notitle,
	        notext: plainText
	    };
	
	    $.ajax({
	        url: '/tot/api/notices',  // RESTful API 경로로 글 등록
	        method: 'POST',
	        contentType: 'application/json',
	        data: JSON.stringify(newNotice),
	        success: function() {
	            alert('공지사항이 성공적으로 등록되었습니다.');
	            window.location.href = '/tot/admin/api/notices';  // 등록 후 컨트롤러 경로로 이동
	        },
	        error: function(error) {
	            console.error('공지사항 등록에 실패했습니다:', error);
	            alert('공지사항 등록에 실패했습니다.');
	        }
	    });
	});

    // 취소 버튼 클릭 시 목록으로 이동
    $('#cancelBtn').click(function() {
        window.location.href = '/tot/admin/api/notices';
    });

    // 목록으로 버튼 클릭 시 목록으로 이동
    $('#backToListBtn').click(function() {
        window.location.href = '/tot/admin/api/notices';
    });

});

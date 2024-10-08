window.onload = function () {

    // 댓글마다 이메일 보내기 버튼 클릭 이벤트 추가
    $('.sendCommentEmailButton').click(function () {
        const commentText = $(this).data('comment');
	    // 이메일 전송
	    $.ajax({
	        url: '/tot/admin/qna/1/sendQnaEmail',
	        method: 'POST',
	        data: {
	        	memId: qnaDetail.memId,
	        	qnaTitle: qnaDetail.qnaTitle,
	        	qnaText: qnaDetail.qnaText,
	        	comment: commentText
	        },
	        success: function (response) {
	            console.log('이메일 전송 성공:', response);
	            alert('이메일이 성공적으로 전송되었습니다.');
	        }
	    });
            
    });

    // 댓글 작성 폼 제출 이벤트 처리
    $('#commentForm').submit(function (event) {
        event.preventDefault(); // 폼 제출 기본 동작 막기

        const qnaId = $('input[name="qnaId"]').val();
        const commentText = $('#commentText').val();

        $.ajax({
            url: '/tot/admin/qna/1/addComment',
            method: 'POST',
            data: {
                qnaId: qnaId,
                commentText: commentText
            },
            success: function (response) {
                alert(response); // 성공 메시지 출력
                location.reload(); // 페이지 리로드
            },
            error: function (xhr, status, error) {
                alert(xhr.responseText); // 에러 메시지 출력
                location.reload(); // 페이지 리로드
            }
        });
    });
};


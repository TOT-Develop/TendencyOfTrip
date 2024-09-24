$(document).ready(function() {
    const noid = $('#noid').val(); // 숨겨진 필드에서 noid 값을 가져옴
    if(noid != null) {
    console.log(noid);  // 디버깅을 위해 noid 출력
    } else {
    console.log('못가져옴')
    }
    

    // 공지사항 데이터를 가져와서 폼에 채워넣음
    $.ajax({
        url: `/tot/api/notices/${noid}`,  // 공지사항 조회 API
        method: 'GET',
        success: function(notice) {
        	console.log('불러온 데이터:', notice); // 기존 데이터
            $('#notitle').val(notice.notitle);  // 제목을 입력 필드에 설정
            editor.setData(notice.notext ? notice.notext : '');  // CKEditor에 내용 삽입
        },
        error: function(error) {
            console.error('공지사항 데이터를 가져오는 데 실패했습니다:', error);
        }
    });

    // "글 수정하기" 버튼 클릭 시 수정된 데이터를 서버로 전송
    $('#submitBtn').click(function(event) {
        event.preventDefault();  // 기본 폼 제출 동작을 막음

        // CKEditor에서 데이터를 가져오고 HTML 태그를 제거
        let content = editor.getData();
        content = content.replace(/<\/?[^>]+(>|$)/g, '');  // 모든 HTML 태그 제거

        const updatedNotice = {
            notitle: $('#notitle').val(),   // 수정된 제목 가져오기
            notext: content                 // 수정된 내용 가져오기
        };

		$.ajax({
		    url: `/tot/api/notices/${noid}`,  // PUT 요청으로 수정 데이터 전송
		    method: 'PUT',  // PUT 메서드 사용
		    contentType: 'application/json',
		    data: JSON.stringify(updatedNotice),  // JSON 형식으로 데이터 전송
		    success: function() {
		        alert('공지사항이 성공적으로 수정되었습니다.');
		        window.location.href = '/tot/api/notices'; // 수정 후 목록 페이지로 이동
		    },
		    error: function(xhr) {
		        console.error('공지사항 수정에 실패했습니다:', xhr);
		        alert('공지사항 수정에 실패했습니다.');
		    }
		});
 	});

    // 취소 버튼 클릭 시 목록으로 이동
    $('#cancelBtn').click(function() {
        window.location.href = '/tot/api/notices';
    });

    // 목록으로 버튼 클릭 시 목록으로 이동
    $('#backToListBtn').click(function() {
        window.location.href = '/tot/api/notices';
    });
});

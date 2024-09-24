$(document).ready(function() {

    // 검색 기능 AJAX 처리
    $('#searchForm').submit(function(e) {
        e.preventDefault();
        var formData = $(this).serialize();  // 검색 조건
        loadNoticeList(formData);
    });

    // 공지사항 목록 로드 함수 
    function loadNoticeList(searchParams) {
    $.ajax({
        url: '/tot/api/notices/search',  // RESTful API 경로
        type: 'GET',
        data: searchParams,  // 검색 조건이 있으면 전송
        success: function(data) {
            console.log('공지사항 목록 로드 성공:', data);
            
            // 전체 새로고침을 없애고, 검색 결과를 페이지 일부에 반영
            $('#qnaTableBody').empty();  // 기존 테이블 내용 삭제
            
                // 공지사항 목록이 있을 경우 리스트를 추가
		    // 검색 결과가 있을 경우 테이블에 데이터를 추가
            if (data && data.length > 0) {
                data.forEach(function(notice) {
                    $('#qnaTableBody').append(
                        `<tr>
                            <td>${notice.noid}</td>
                            <td><a href="/tot/api/notices/detail/${notice.noid}">${notice.notitle}</a></td>
                            <td>${new Date(notice.noregdate).toLocaleString()}</td>
                            <td><a href="#" class="deleteLink" onclick="deleteNotice(${notice.noid})">삭제</a></td>
                            <td><a href="/tot/api/notices/update/${notice.noid}">수정</a></td>
                        </tr>`
                    );
                });
            } else {
                $('#qnaTableBody').append('<tr><td colspan="5">검색 결과가 없습니다.</td></tr>');
            }
        },
        error: function(xhr) {
            console.log('공지사항 목록 로드 오류:', xhr.responseText);
            alert('공지사항 목록을 불러오는 데 실패했습니다.');
        }
    });
}



    // 공지사항 상세보기로 이동
    window.viewNotice = function(noid) {
        location.href = `/tot/jsp/noticeDetail.jsp?noid=${noid}`;  
    }        

    // 공지사항 삭제
    window.deleteNotice = function(noid) {
        if (confirm('정말 삭제하시겠습니까?')) {
            $.ajax({
                url: `/tot/api/notices/${noid}`,
                type: 'DELETE',
                success: function() {
                    alert('삭제되었습니다.');
                    console.log('삭제 성공');  
                    location.reload();  // 삭제 후 목록 새로고침 (전체 새로고침)
                },
                error: function(xhr) {
                    console.log('삭제 오류:', xhr.responseText); 
                    alert('삭제에 실패했습니다.');
                }
            });
        }
    }

    // 공지사항 수정으로 이동
    window.updateNotice = function(noid) {
    location.href = `/tot/api/notices/update/${noid}`;
    }
});

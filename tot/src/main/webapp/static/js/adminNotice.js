$(document).ready(function () {
    // 제목 셀 클릭 이벤트 설정
    $('.noticeTitle').on('click', function () {
        const noticeId = $(this).data('id');  // data-id 속성에서 noid 값을 가져옴
        window.location.href = "/tot/admin/notice/detail/" + noticeId;
    });

    // 수정 셀 클릭 이벤트 설정
    $('.updateLink').on('click', function () {
        const noticeId = $(this).data('id');  // data-id 속성에서 noid 값을 가져옴
        window.location.href = "/tot/admin/notice/update/" + noticeId;
    });


    // 검색 기능 AJAX 처리
    $('#searchForm').submit(function (e) {
        e.preventDefault();
        var formData = $(this).serialize();  // 검색 조건
        loadNoticeList(formData);
    });

    // 공지사항 목록 로드 함수 
    function loadNoticeList(searchParams) {
        $.ajax({
            url: '/tot/admin/notice/search',  // RESTful API 경로
            type: 'GET',
            data: searchParams,  // 검색 조건이 있으면 전송
            success: function (data) {
                console.log('공지사항 목록 로드 성공:', data);

                // 전체 새로고침을 없애고, 검색 결과를 페이지 일부에 반영
                $('#qnaTableBody').empty();  // 기존 테이블 내용 삭제

                // 공지사항 목록이 있을 경우 리스트를 추가
                // 검색 결과가 있을 경우 테이블에 데이터를 추가
                if (data && data.length > 0) {
                    data.forEach(function (notice, index) {
                        $('#qnaTableBody').append(
                            `<tr>
                            <td>${index + 1}</td>
                            <td class="noticeTitle" data-id="${notice.noId}">${notice.noTitle}</a></td>
                            <td>${notice.formattedNoregdate}</td>
                            <td class="deleteLink" onclick="deleteNotice(${notice.noId})">삭제</td>
                            <td><a href="/tot/notice/update/${notice.noId}">수정</a></td>
                        </tr>`
                        );
                    });
                } else {
                    $('#qnaTableBody').append('<tr><td colspan="5">검색 결과가 없습니다.</td></tr>');
                }
            },
            error: function (xhr) {
                console.log('공지사항 목록 로드 오류:', xhr.responseText);
                alert('공지사항 목록을 불러오는 데 실패했습니다.');
            }
        });
    }


    // 공지사항 삭제
    window.deleteNotice = function (noId) {
        if (confirm('정말 삭제하시겠습니까?')) {
            $.ajax({
                url: `/tot/admin/notice/${noId}`,
                type: 'DELETE',
                success: function () {
                    alert('삭제되었습니다.');
                    console.log('삭제 성공');
                    location.reload();  // 삭제 후 목록 새로고침 (전체 새로고침)
                },
                error: function (xhr) {
                    console.log('삭제 오류:', xhr.responseText);
                    alert('삭제에 실패했습니다.');
                }
            });
        }
    }

    // 공지사항 수정으로 이동
    window.updateNotice = function (noId) {
        location.href = `/tot/notice/update/${noId}`;
    }
});

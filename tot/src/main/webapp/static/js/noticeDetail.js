// 공지사항 상세보기
$(document).ready(function() {
    const urlParams = new URLSearchParams(window.location.search);
    const noid = urlParams.get('noid');

    $.ajax({
        url: `/tot/admin/api/notices/${noid}`,
        type: 'GET',
        success: function(notice) {
            $('#noticeTitle').text(notice.notitle);
            $('#noticeContent').text(notice.notext);
            $('#noticeDate').text(new Date(notice.noregdate).toLocaleString());
        },
        error: function(xhr) {
            alert('공지사항 불러오기 실패했습니다.');
            console.error(xhr);
        }
    });
});
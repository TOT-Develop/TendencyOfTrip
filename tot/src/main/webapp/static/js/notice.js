$(document).ready(function() {
    // notice 상세보기 페이지로 이동
    $('#noticeTableBody').on('click', 'tr', function(){
        window.location.href = "noticeDetail.jsp";
    });
});
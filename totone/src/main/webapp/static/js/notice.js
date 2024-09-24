window.onload = function (){
	// notice 상세보기 페이지로 이동
    $('#noticeTableBody tr').click(function(){
    	window.location.href="noticeDetail.jsp"
    })

	// notice 목록 페이지로 이동
    $('.toListBtn').click(function(){
    	window.location.href="notice.jsp"
    })


}
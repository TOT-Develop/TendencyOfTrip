$(document).ready(function () {
    // 여행 항목 클릭 시 해당 여행의 코스 추천 화면으로 이동
    $(document).on('click', '.trip-item', function () {
        const tripId = $(this).data('trip-id');
        
        if (tripId) {
            window.location.href = `/tot/trip?tripId=${tripId}`;
        } else {
            console.error('해당 여행 상세 정보를 찾을 수 없습니다.');
        }
    });

	$('.backbtn').on('click', function() {
	    window.location.href = '/tot/';
	});
	
});
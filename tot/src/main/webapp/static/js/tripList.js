$(document).ready(function() {
    // 서버에서 memid를 가져오는 AJAX 요청
    $.getJSON("/tot/session/id", function(response) {
        let memId = response.memId;
        console.log(memId);
        
        if (memId) {
            // AJAX 요청으로 여행 목록을 가져오는 코드
            $.getJSON("/tot/triplist/trips", function(data) {
                let tripListContainer = $('#trip-list-container');
                tripListContainer.empty(); // 기존 내용 제거
				console.log(data);
                data.forEach(function(trip, index) {
                	let count = index + 1;
                    // 데이터를 HTML 요소로 변환하여 추가
                    tripListContainer.append(`
                        <div class="trip-item" data-trip-id="${trip.tripId}" style="background-image: url('${trip.regionImageUrl || 'default-image.jpg'}');">
                            <div class="trip-info">
                                <h2>${count}</h2>
                                <p>회원 아이디: ${trip.memId}</p>
                                <p>지역: ${trip.regionName}</p>
                                <p>예상 비용: ${trip.formattedTrAmt || trip.trAmt}</p>
                                <p>여행 기간: ${trip.displayPeriod || trip.trPeriod}</p>
                                <p>인원 수: ${trip.trPeople}</p>
                            </div>
                        </div>
                    `);
                });

                // 여행 항목 클릭 시 해당 여행의 코스 추천 화면으로 이동
                $(document).on('click', '.trip-item', function() {
                    const tripId = $(this).data('trip-id');
                    console.log('Clicked trip ID:', tripId); // 디버깅용
                    if (tripId) {
                        window.location.href = `/tot/trip?tripId=${tripId}`;
                    } else {
                        console.error('Trip ID is not defined.');
                    }
                });
            });
        } else {
            alert("로그인 정보가 없습니다.");
            window.location.href = "/login"; // 로그인 페이지로 리다이렉트
        }
    });
});

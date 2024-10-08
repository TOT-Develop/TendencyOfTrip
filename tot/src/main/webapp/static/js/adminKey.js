$(document).ready(function(){

	checkKakaoMapApiKey();
	checkKakaoImageSearchApiKey();
	checkGoogleApiKey();
    checkNaverApiKey();
    checkKakaoApiKey();

});

	function checkKakaoMapApiKey() {
	    const kakaoApiKey = 'KAKAOAPIKEY'; // Kakao API 키
	    const lat = 37.0789561558879; // 위도
	    const lon = 127.423084873712; // 경도
	
	    const url = `https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${lon}&y=${lat}&input_coord=WGS84`;
	
	    fetch(url, {
	        method: 'GET',
	        headers: {
	            'Authorization': `KakaoAK ${kakaoApiKey}` // Kakao API 키를 Authorization 헤더에 포함
	        }
	    })
	    .then(response => {
	        const mapStatus = document.getElementById('map-status');
	        if (response.ok) {
	            mapStatus.classList.add('status-ok'); // 정상 상태 - 초록색
	        } else {
	            mapStatus.classList.add('status-error'); // 오류 상태 - 빨간색
	            console.error(`Kakao 지도 API 키 상태: 오류 ${response.status}`);
	        }
	    })
	    .catch(error => {
	        document.getElementById('map-status').classList.add('status-error'); // 오류 상태 - 빨간색
	        console.error(`Kakao 지도 API 호출 오류: ${error}`);
	    });
	}

	
	function checkKakaoImageSearchApiKey() {
	    const kakaoImageSearchApiKey = 'kakaoImageSearchApiKey'; // 이미지 검색 API 키
	    fetch('https://dapi.kakao.com/v2/search/image?query=apple', {
	        method: 'GET',
	        headers: {
	            'Authorization': `KakaoAK ${kakaoImageSearchApiKey}`,
	            'Content-Type': 'application/json'
	        }
	    })
	    .then(response => {
	        const imageStatus = document.getElementById('image-status');
	        if (response.ok) {
	            imageStatus.classList.add('status-ok'); // 정상 상태 - 초록색
	        } else {
	            imageStatus.classList.add('status-error'); // 오류 상태 - 빨간색
	            console.error(`Kakao 이미지 검색 API 키 상태: 오류 ${response.status}`);
	        }
	    })
	    .catch(error => {
	        document.getElementById('image-status').classList.add('status-error'); // 오류 상태 - 빨간색
	        console.error(`Kakao 이미지 검색 API 호출 오류: ${error}`);
	    });
	}
	
	   // Google API 키 상태 확인
        function checkGoogleApiKey() {
            $.ajax({
                url: '/tot/admin/key/status/google',
                method: 'GET',
                success: function(response) {
                    const statusEl = $('#google-status');
                    if (response.status === '정상') {
                        statusEl.addClass('status-ok');
                    } else {
                        statusEl.addClass('status-error');
                    }
                    console.log('Google OAuth API 키 상태:', response.status);
                },
                error: function(xhr, status, error) {
                    $('#google-status').addClass('status-error');
                    console.error('Google OAuth API 키 상태 오류:', error);
                }
            });
        }

        // Naver API 키 상태 확인
        function checkNaverApiKey() {
            $.ajax({
                url: '/tot/admin/key/status/naver',
                method: 'GET',
                success: function(response) {
                    const statusEl = $('#naver-status');
                    if (response.status === '정상') {
                        statusEl.addClass('status-ok');
                    } else {
                        statusEl.addClass('status-error');
                    }
                    console.log('Naver OAuth API 키 상태:', response.status);
                },
                error: function(xhr, status, error) {
                    $('#naver-status').addClass('status-error');
                    console.error('Naver OAuth API 키 상태 오류:', error);
                }
            });
        }

        // Kakao API 키 상태 확인
        function checkKakaoApiKey() {
            $.ajax({
                url: '/tot/admin/key/status/kakao',
                method: 'GET',
                success: function(response) {
                    const statusEl = $('#kakao-status');
                    if (response.status === '정상') {
                        statusEl.addClass('status-ok');
                    } else {
                        statusEl.addClass('status-error');
                    }
                    console.log('Kakao OAuth API 키 상태:', response.status);
                },
                error: function(xhr, status, error) {
                    $('#kakao-status').addClass('status-error');
                    console.error('Kakao OAuth API 키 상태 오류:', error);
                }
            });
        }


   
$(document).ready(function() {
    // 날짜 필드 초기화
	const $startDate = $('#trstadate');
	const $endDate = $('#trenddate');
	
	const today = new Date();
	today.setHours(0, 0, 0, 0); // 시간 부분을 0으로 설정하여 오늘 날짜의 시작 부분만 비교
	
	const tomorrow = new Date(today);
	tomorrow.setDate(today.getDate() + 1);
	
	const formattedTomorrow = tomorrow.toISOString().split('T')[0]; // YYYY-MM-DD 형식으로 변환
	$startDate.attr('min', formattedTomorrow); // 시작일의 min 속성 설정
	
	$endDate.on('change', function() {
	    const endDateValue = $(this).val();
	    const startDateValue = $startDate.val();
	
	    if (endDateValue && startDateValue) {
	        // 여행 기간 계산
	        const start = new Date(startDateValue);
	        const end = new Date(endDateValue);
	        const diffTime = Math.abs(end - start);
	        const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) + 1; // +1로 종료일 포함
	
	        if (diffDays < 1) { // 종료일이 시작일보다 빠를 경우
	            alert('여행 기간은 1일 이상이어야 합니다.');
	            $(this).val(''); // 잘못된 날짜를 선택하면 입력값을 지웁니다.
	        } else {
	            // 여행 기간 필드에 값 설정
	            const nights = diffDays - 1; // 숙박 수 = 여행일 - 1
	            const totalDays = diffDays; // 총 여행일 수
	            let periodText = '';
	
	            if (nights > 0) {
	                periodText = `${nights}박 ${totalDays}일`;
	            } else {
	                periodText = `${totalDays}일`; // 1박일 경우
	            }
	
	            $('#trperiod').val(periodText); // 여행 기간에 형식화된 값 설정
	            $('#trperiod-error').remove();
	        }
	    }
	
	    // 종료일이 시작일보다 빠를 경우 처리
	    if (endDateValue && startDateValue) {
	        const startDate = new Date(startDateValue);
	        const endDate = new Date(endDateValue);
	        if (endDate < startDate) {
	            alert('종료일은 시작일보다 이전으로 선택할 수 없습니다.');
	            $(this).val(''); // 잘못된 날짜를 선택하면 입력값을 지웁니다.
	            $('#trperiod').val(''); // 여행 기간 필드 초기화
	        }
	    }
	});
	
	// startDate의 변화에 따라 endDate의 최대 날짜를 설정
	$startDate.on('change', function() {
	    const startDateValue = $(this).val();
	    if (startDateValue) {
	        const startDate = new Date(startDateValue);
	        const maxEndDate = new Date(startDate);
	        maxEndDate.setDate(maxEndDate.getDate() + 2); // 시작일로부터 2일 후 
	        const maxEndDateString = maxEndDate.toISOString().split('T')[0]; // YYYY-MM-DD 형식으로 변환
	        
	        // endDate의 max 속성을 설정
	        $endDate.attr('max', maxEndDateString);
	        
	        // endDate의 현재 값이 maxEndDate보다 클 경우 값 초기화
	        if (new Date($endDate.val()) > maxEndDate) {
	            alert('종료일은 시작일로부터 2일 이내로 선택해야 합니다.'); 
	            $endDate.val(''); // 잘못된 날짜를 선택하면 입력값을 지웁니다.
	        }
	    }
	});

    // 지역 버튼 클릭 시 선택 상태 변경
    $('.region').on('click', function() {
        $('.region').removeClass('selected'); // 모든 지역 버튼 선택 해제
        $(this).addClass('selected'); // 현재 버튼 선택
    });

    // 음식 버튼 클릭 시 선택 상태 변경
    $('.food').on('click', function() {
        $('.food').removeClass('selected'); // 모든 음식 버튼 선택 해제
        $(this).addClass('selected'); // 현재 버튼 선택
    });

    // MBTI 선택 시 오류 메시지 제거
    $('#mbti').on('change', function() {
        $('#mbti-error').remove(); // 선택 시 오류 메시지 제거
    });

    $('#trpeople').on('change', function() {
        $('#trpeople-error').remove(); // 선택 시 오류 메시지 제거
    });

    $('.region').on('click', function() {
        $('.region').removeClass('selected'); // 모든 지역 버튼 선택 해제
        $(this).addClass('selected'); // 현재 버튼 선택
        $('#areacode-error').remove(); // 지역 선택 시 오류 메시지 제거
    });

    // 음식 버튼 클릭 시 선택 상태 변경 및 오류 메시지 제거
    $('.food').on('click', function() {
        $('.food').removeClass('selected'); // 모든 음식 버튼 선택 해제
        $(this).addClass('selected'); // 현재 버튼 선택
        $('#restaurant_001-error').remove(); // 음식 선택 시 오류 메시지 제거
    });


	$('#tramt').on('input', function() {
        $('#tramt-error').remove(); // 값이 입력될 때 오류 메시지 제거
    });

    $('#trperiod').on('input', function() {
        $('#trperiod-error').remove();
    });

	
	$(".back-button").on("click", function () {
        const returnUrl = sessionStorage.getItem('returnUrl') || '/'; // returnUrl이 없으면 홈으로 이동
        window.location.href = returnUrl;
    });

    $('.submit-button').on('click', function(event) {
        event.preventDefault();

        let hasError = false;

        const mbti = $('#mbti').val();
		const tramt = $('#tramt').val();
		const trpeople = $('#trpeople').val();
		const trstadate = $('#trstadate').val();
        const trenddate = $('#trenddate').val();
        const trperiod = $('#trperiod').val();
        const areacode = $('.region.selected').attr('id'); // 선택된 지역 버튼의 값
        const restaurant_001 = $('.food.selected').attr('id'); // 선택된 음식 버튼의 값
        const resultType = sessionStorage.getItem('resultType'); // 세션에서 resultType 가져오기


        if (mbti === "" || mbti === "MBTI") {
            $('#mbti-error').remove(); // 기존 오류 메시지 제거
            $('#mbti').after('<p id="mbti-error" style="color: red;">MBTI를 선택해 주세요.</p>');
            hasError = true;
        }

        
        if (tramt === "" || tramt === "예산") { 
            $('#tramt-error').remove(); // 기존 오류 메시지 제거
            $('.currency').after('<p id="tramt-error" style="color: red;">예산을 입력해 주세요.</p>');
            hasError = true;
        }

		
		if (trpeople === "" || trpeople === "인원수 선택") { 
            $('#trpeople-error').remove(); // 기존 오류 메시지 제거
            $('#trpeople').after('<p id="trpeople-error" style="color: red;">인원수을 선택해 주세요.</p>');
            hasError = true;
        }

        if (!areacode) {
			$('#areacode-error').remove(); // 기존 오류 메시지 제거
            $('#area-group').after('<p id="areacode-error" style="color: red;">지역을 선택해 주세요.</p>');
            hasError = true;
        } 

    	if (!restaurant_001) {
			$('#restaurant_001-error').remove(); // 기존 오류 메시지 제거
            $('#food-group').after('<p id="restaurant_001-error" style="color: red;">음식 유형을 선택해 주세요.</p>');
            hasError = true;
        } 

        if (!trstadate || !trenddate) {
            if (!trperiod) {
                $('#trperiod-error').remove(); // 기존 오류 메시지 제거
                $('#trperiod').after('<p id="trperiod-error" style="color: red;">여행 기간을 입력해 주세요.</p>');
                hasError = true;
            }
        } 

        if (hasError) return; // 에러가 있을 경우 중단

        


        // 세션에 데이터 저장
        sessionStorage.setItem('mbti', mbti);
        sessionStorage.setItem('tramt', tramt);
        sessionStorage.setItem('trpeople', trpeople);
        sessionStorage.setItem('trstadate', trstadate);
        sessionStorage.setItem('trenddate', trenddate);
        sessionStorage.setItem('trperiod', trperiod);
        sessionStorage.setItem('areacode', areacode);
        sessionStorage.setItem('restaurant_001', restaurant_001);
        sessionStorage.setItem('resultType', resultType);

        // JSON 데이터 구조화
        const requestData = {
            mbti: mbti,
            tramt: tramt,
            trpeople: trpeople,
            trstadate: trstadate,
            trenddate: trenddate,
            trperiod: trperiod,
            areacode: areacode,
            restaurant_001: restaurant_001,
            resultType: resultType // resultType 추가
        };
    
        // AJAX 요청
        fetch('/tot/recommendCourse', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        })
        .then(response => response.json())
        .then(data => {
            console.log("변환된 데이터:", data);
            window.location.href = "/tot/planner";
        })
        .catch(error => console.error('Error:', error));
    });

    // 예산 입력 필드 숫자만 허용
    $('#tramt').on('input', function() {
        let value = $(this).val().replace(/[^0-9]/g, ''); // 숫자만 추출
        $(this).val(formatNumber(value)); // 숫자 포맷팅
    });

    $('#tramt').on('focusout', function() {
        let value = $(this).val().replace(/[^0-9]/g, '');
        $(this).val(formatNumber(value)); // 숫자 포맷팅
    });

    $('#tramt').on('focus', function() {
        $(this).val($(this).val().replace(/[^0-9]/g, ''));
    });

    // 숫자 천 단위로 포맷팅
    function formatNumber(value) {
        return value.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }

    // 페이지 로드 시 resultType 출력
    const resultType = sessionStorage.getItem('resultType');
    if (resultType) {
        console.log('세션에서 가져온 resultType:', resultType);
    } else {
        console.log('세션에 resultType이 없습니다.');
    }
});

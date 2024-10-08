$(document).ready(function() { 
    const requestData = JSON.parse(sessionStorage.getItem('requestData')); 

	const mbti = sessionStorage.getItem('mbti');
    const tramtre = sessionStorage.getItem('tramt');
    const trpeople = sessionStorage.getItem('trpeople');
    const trstadateStr  = sessionStorage.getItem('trstadate');
    const trenddateStr = sessionStorage.getItem('trenddate');
    const trperiod = sessionStorage.getItem('trperiod');
    const areacode = sessionStorage.getItem('areacode');
    const restaurant_001 = sessionStorage.getItem('restaurant_001');
    const resultType = sessionStorage.getItem('resultType');

    // 가져온 값 출력 (디버깅용)
    console.log('MBTI:', mbti);
    console.log('예산:', tramtre);
    console.log('인원 수:', trpeople);
    console.log('출발일:', trstadateStr);
    console.log('도착일:', trenddateStr);
    console.log('여행 기간:', trperiod);
    console.log('지역 코드:', areacode);
    console.log('음식 종류:', restaurant_001);
    console.log('결과 타입:', resultType);

    let tramt = tramtre.replace(/,/g, ''); 

	fetch('/tot/session/id')
	    .then(response => {
	        if (!response.ok) {
	            throw new Error('네트워크 응답이 좋지 않습니다: ' + response.statusText);
	        }
	        return response.json();
	    })
	    .then(data => {
	        const memId = data.memId || 'guest';  // memId가 없으면 'user123'으로 기본값 설정
	        sessionStorage.setItem('memId', memId); // 세션에 memId 저장
	    })
	    .catch(error => {
	        console.error('에러:', error);
	    });

    let dateElements = [];
	let dayCounter = 0;
        console.log("세션에서 불러온 데이터:", requestData); 
        $('#mbti').text(mbti); 
        $('#tramt').text(tramt); 
		$('.pdate').text(trstadateStr + "-" + trenddateStr);
		const trstadate = new Date(trstadateStr);
		const trenddate = new Date(trenddateStr);
		
		let currentDate = trstadate;
		
		const daysWeek = ['일', '월', '화', '수', '목', '금', '토'];

        while(currentDate <= trenddate){
            const formattedDate = currentDate.toISOString().split('T')[0];
            const dayOfWeek = daysWeek[currentDate.getDay()];
            const dateWithDay = `${formattedDate} (${dayOfWeek})`;
            dateElements.push(dateWithDay); // 배열에 추가
            currentDate.setDate(currentDate.getDate() + 1);
        }

		

    let dataLoaded = { 
        chatdata: false 
    }; 

    let ids = {}; // IDs를 저장할 객체
    let recommendationName = " "; 
	console.log('현재 memId:', sessionStorage.getItem('memId'));
	  function checkAllDataLoaded() { 
	    if (dataLoaded.chatdata) { 
	        $('.okbtn').on('click', function() { 
	            let courses = []; 
	            for (const [day, idList] of Object.entries(ids)) { 
	                const description = idList.join(','); 
	                const courseDTO = createCourseDTO(day, description); 
	                courses.push(courseDTO); 
	            } 
	            const trstadate = trstadateStr;
	            const trendDate = trenddateStr;
	
	            const tripData = { 
	                memId: sessionStorage.getItem('memId'), 
	                tripName: recommendationName,  // Use extracted recommendation name
	                mbti: mbti, 
	                trAmt: parseInt(tramt), 
	                trstaDate: trstadate, 
	                trendDate: trendDate, 
	                trPeople: parseInt(trpeople), 
	                areaCode: areacode, 
	                courses: courses // CourseDTO 객체 포함 
	            }; 
	
	            console.log(tripData); 
	
		         $.ajax({ 
				    url: '/tot/recommendCourse/create', 
				    type: 'POST', 
				    contentType: 'application/json', 
				    dataType: 'json', // 응답 데이터 형식 설정
				    data: JSON.stringify(tripData), 
				    success: function(response) { 
				        alert("저장 성공"); // JSON 응답 처리
						window.location.href = '/tot/';
				    }, 
				    error: function(xhr, status, error) { 
				        console.error("에러 발생:", status, error);
				        console.log("응답 내용:", xhr.responseText); // 서버에서 반환한 에러 메시지 출력
				        alert("저장 실패: " + xhr.responseText); // 사용자에게 알림
				    } 
				});
	        }); 
	    } 
	}

    // 채팅 데이터 가져오기 
	   fetch('/tot/planner/chatdata')
	    .then(response => response.json())
	    .then(data => {
	        console.log("받은 채팅 데이터:", data);
	
	        if (data && data.content && data.content.choices) {
	            const content = data.content.choices[0].message.content;
	
	            let parsedContent;
	            try {
	                parsedContent = JSON.parse(content);
	            } catch (e) {
	                console.error("Content는 유효한 JSON이 아닙니다:", e);
	                parsedContent = null;
	            }
	
	            if (parsedContent) {
	                console.log("파싱된 Content:", parsedContent);
	                // 추천 이름 추출
	                if (parsedContent.추천이름) {
	                    recommendationName = parsedContent.추천이름;  // Store the recommendation name
	                    $('#recommendationName').text(recommendationName); // HTML 요소에 표시
	                }
	
	                ids = extractIds(parsedContent); // IDs를 추출하여 할당
	                console.log("추출된 IDs:", ids);
	
	                const dailyContainer = $('.container');
	                let number = 1;
	                let dayCounter = 0;  // 날짜 인덱스 증가용
	
	                for (const [key, value] of Object.entries(parsedContent)) {
	                    if (key === '추천이름') continue;
	                    const day = key; // '1일차 추천 코스', '2일차 추천 코스', '3일차 추천 코스'
	                    const activities = value;
	                    const dayContainer = $('<div class="day-container">');
	                    const datanav1 = $('<div>').addClass('datanav1');
	                    const dayElement = $('<div>').addClass('date1').text(`${day}`);
	                    const date = $('<div>').text(dateElements[dayCounter++]);
	                    datanav1.append(dayElement);
	                    datanav1.append(date);
	                    dayContainer.append(datanav1);
	
	                    const timelineContainer = $('<div>').addClass('timeline-container');
	
	                    for (const [meal, details] of Object.entries(activities)) {
	                        const mealElement = $('<div>').addClass('pcolor').append(`${meal}`);
	                        const timelineItem = $('<div class="timeline-item">');
	                        const numberElement = $('<div class="number1">').text(number++);
	
	                        const timeContent = $('<div class="time-content">');
	                        const detailsList = $('<div class="datanav3">');
	
	                        detailsList.append(mealElement);
	
	                        for (const [type, info] of Object.entries(details)) {
	                            const infoElement = $('<div class="time-title">').html(`${info.이름}<br>(${type})`); // 타입을 괄호 안에 추가
	                            detailsList.append(infoElement);
	                        }
	
	                        timeContent.append(detailsList);
	                        timelineItem.append(numberElement);
	                        timelineItem.append(timeContent);
	
	                        timelineContainer.append(timelineItem);
	                    }
	                    timelineContainer.append($('<div class="endnumber">'));
	
	                    dayContainer.append(timelineContainer);
	                    dailyContainer.append(dayContainer);
	                }
	
	                dataLoaded.chatdata = true;
	                checkAllDataLoaded();
	            } else {
	                console.error("파싱된 content가 없습니다.");
	            }
	        } else {
	            console.error("잘못된 데이터 구조:", data);
	            if (data && data.content) {
	                console.log("Content 존재:", data.content);
	            }
	        }
	    })
	    .catch(error => {
	        console.error("채팅 데이터 가져오기 에러:", error);
	    });

    function extractIds(data) { 
        if (typeof data !== 'object' || data === null) { 
            console.error("잘못된 데이터 형식:", data); 
            return {}; 
        } 

        function extractIdsFromObject(obj) { 
            let idsByDay = {}; 
             
            for (const day in obj) { 
                if (obj.hasOwnProperty(day)) { 
                    if (typeof obj[day] === 'object' && obj[day] !== null) { 
                        idsByDay[day] = []; 
                        for (const meal in obj[day]) { 
                            if (obj[day].hasOwnProperty(meal)) { 
                                if (typeof obj[day][meal] === 'object' && obj[day][meal] !== null) { 
                                    for (const type in obj[day][meal]) { 
                                        if (obj[day][meal].hasOwnProperty(type)) { 
                                            if (typeof obj[day][meal][type] === 'object' && obj[day][meal][type] !== null) { 
                                                for (const key in obj[day][meal][type]) { 
                                                    if (obj[day][meal][type].hasOwnProperty(key)) { 
                                                        if (key.includes('ID') && obj[day][meal][type][key] !== 'N/A') { 
                                                            let formattedId = ''; 
                                                            if (key.startsWith('관광지')) { 
                                                                formattedId = `TOID:${obj[day][meal][type][key]}`; 
                                                            } else if (key.startsWith('호텔')) { 
                                                                formattedId = `LODID:${obj[day][meal][type][key]}`; 
                                                            } else if (key.startsWith('식당')) { 
                                                                formattedId = `RESTID:${obj[day][meal][type][key]}`; 
                                                            } 
                                                            if (formattedId) { 
                                                                idsByDay[day].push(formattedId); 
                                                            } 
                                                        } 
                                                    } 
                                                } 
                                            } 
                                        } 
                                    } 
                                } 
                            } 
                        } 
                    } 
                } 
            } 
            return idsByDay; 
        } 

        return extractIdsFromObject(data); 
    } 

	$(".backbtn").on("click", function () {
        window.location.href = "/tot/";
    });

    function createCourseDTO(day, description) { 
        return { 
            tripId: null, // tripid는 서버에서 설정할 값 
            areaCode: areacode, // 지역코드 
            dCourse: description, // 하루 코스 설명 
            courRegdate: null, // 서버에서 SYSDATE로 설정 
            courUpdate: null  // 서버에서 SYSDATE로 설정 
        }; 
    } 
});

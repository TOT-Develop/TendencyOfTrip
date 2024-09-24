$(document).ready(function() {
    // 비동기 함수 정의
    async function init() {
        // 이벤트 리스너 및 초기화 코드 설정
        $('#toggle-course-btn').click(toggleCourseView);
        $('#course-buttons').on('click', '.day-button', handleDayButtonClick);

        $(document).on('click', '.add-to-course-btn', function() {
            const festivalId = $(this).data('id');
            const festival = state.allFestivals.find(f => f.contentid == festivalId);

            if (festival) {
                showDateSelectionModal(festival);
            } else {
                console.error('Festival not found');
            }
        });

        $('#save-btn').on('click', async function() {
            try {
                const tripId = getQueryParam('tripId');
                const courseData = getCurrentCourseData();
                const festivals = getSelectedFestivals();

                const response = await fetch(`/tot/triplist/locations/${tripId}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        courseData: courseData,
                        festivals: festivals
                    })
                });

                if (!response.ok) {
                    throw new Error('Failed to save itinerary');
                }

                alert('Itinerary saved successfully!');
            } catch (error) {
                console.error('Error saving itinerary:', error);
                alert('There was an error saving the itinerary. Please try again.');
            }
        });

        $('#date-options').on('click', '.date-option', function() {
            $('#date-options .date-option').removeClass('selected');
            $(this).addClass('selected');
        });

        $('#confirm-addition').click(function() {
            const selectedDay = $('#date-options .selected').data('day');
            const festivalId = $(this).data('festival-id');

            if (selectedDay) {
                addFestivalToCourse(festivalId, selectedDay);
                $('#date-selection-modal').remove();
            } else {
                alert('날짜를 선택해주세요.');
            }
        });

        $('#courses-container').sortable({
            items: '.day-container',
            handle: '.datanav1',
            cursor: 'move',
            placeholder: 'ui-state-highlight',
            start: function(event, ui) {
                ui.placeholder.height(ui.item.outerHeight());
            },
            update: function(event, ui) {
                console.log("일차 변경됨:", ui.item.data('day'));
            }
        });

        // initializeMap 함수 실행
        initializeMap();

    }

    // 비동기 초기화 함수 호출
    init();
});


    const state = {
        allDailyCourses: null,
        mapVisible: true,
        map: null,
        dailyPaths: {},
        dailyMarkers: {},
        currentPolylines: []
    };

    const config = {
        kakaoApiKey: 'b46016897768df03faee0171297891f2',
        mapInitialCenter: [33.450701, 126.570667],
        mapInitialLevel: 10
    };


// 일정 순서가 변경될 때 날짜 번호를 업데이트합니다.
function updateDayNumbers() {
    $('.day-container').each(function(index) {
        $(this).find('.date1').text(`${index + 1}일차`);
        $(this).attr('data-day', index + 1);
    });
}

// 타임라인 내 항목의 순서를 변경할 때 번호를 업데이트합니다.
function updateTimelineNumbers(container) {
    container.find('.timeline-item').each(function(index) {
        $(this).find('.number1').text(index + 1);
    });
}

    function getQueryParam(param, defaultValue = null) {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param) || defaultValue;
    }

    async function initializeMap() {
        const tripId = getQueryParam('tripId');
        if (!tripId) {
            console.error('tripId not found in the URL');
            return;
        }

        state.map = new kakao.maps.Map(document.getElementById('map'), {
            center: new kakao.maps.LatLng(...config.mapInitialCenter),
            level: config.mapInitialLevel   
        });

        try {
            const memId = await fetchSessionId();
            const trip = await fetchTripData(tripId);
            if (trip) {
                const { trStadate, trEnddate, trPeriod, areacode } = trip;
                const tripMonth = getTripMonth(trPeriod, trStadate);
                
                state.allDailyCourses = await fetchLocations(tripId);
                await createAllMarkers(state.allDailyCourses);
                displayCourses(state.allDailyCourses);
                updateMapDisplay();
                fetchFestivals(trStadate, trEnddate, areacode);
            } else {
                throw new Error('Trip not found');
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }

    async function fetchSessionId() {
        const response = await fetch('/tot/session/id');
        const data = await response.json();
        console.log('Session memId:', data.memId);
        return data.memId;
    }

    async function fetchTripData(tripId) {
        const response = await fetch(`/tot/triplist/trip/${tripId}`);
        const trip = await response.json();
        console.log('Trip data:', trip);
        return trip;
    }

    async function fetchLocations(tripId) {
        const response = await fetch(`/tot/triplist/locations/${tripId}`);
        return await response.json();
    }

    function getTripMonth(trPeriod, trStadate) {
        if (trPeriod) {
            const currentDate = new Date();
            return `${currentDate.getFullYear()}-${('0' + (currentDate.getMonth() + 1)).slice(-2)}`;
        }
        return trStadate ? trStadate.substring(0, 7) : '';
    }

    async function fetchImage(name, address) {
        const searchQueries = [name, name.split(' ')[0], address];
        for (const query of searchQueries) {
            const url = await searchImage(query);
            if (url) return url;
        }
        return 'default-image.jpg';
    }

    async function searchImage(query) {
        try {
            const response = await fetch(`https://dapi.kakao.com/v2/search/image?query=${encodeURIComponent(query)}&size=1`, {
                headers: { 'Authorization': `KakaoAK ${config.kakaoApiKey}` }
            });
            const data = await response.json();
            if (data.documents && data.documents.length > 0) {
                console.log(`Image found for query: ${query}`);
                return data.documents[0].thumbnail_url;
            }
            console.log(`No image found for query: ${query}`);
            return null;
        } catch (error) {
            console.error(`Error searching image for query ${query}:`, error);
            return null;
        }
    }

    function setupFestivalClickListeners() {
        $('#festivals-container').on('click', '.festival-item', function() {
            $(this).find('.festival-details').toggle();
        });
    }

    async function displayCourses(dailyCourses) {
        const coursesContainer = $('#courses-container');
        const courseButtons = $('#course-buttons');
        const festivalsContainer = $('#festivals-container');
        
        coursesContainer.empty();
        courseButtons.empty();
        festivalsContainer.empty();

        for (const [dayNumber, dailyList] of Object.entries(dailyCourses)) {
            let timelineHtml = createDayContainer(dayNumber);
            
            for (let index = 0; index < dailyList.length; index++) {
                const item = dailyList[index];
                timelineHtml += await createTimelineItem(item, index);
            }
            
            timelineHtml += '</div></div>';
            coursesContainer.append(timelineHtml);
            courseButtons.append(`<button class="day-button" data-day="${dayNumber}">Day ${dayNumber}</button>`);
        }
    }

    function createDayContainer(dayNumber) {
        return `
            <div class="day-container active" data-day="${dayNumber}">
                <div class="datanav1">
                    <div class="date1">${dayNumber}일차</div>
                </div>
                <div class="timeline-container">
        `;
    }

    async function createTimelineItem(item, index) {
        let imageUrl = 'default-image.jpg';
        let itemHtml = '';

        if (item.toId) {
            imageUrl = item.toImgPath || await fetchImage(item.toName, item.toAddress);
            itemHtml = createAttractionItem(item, index, imageUrl);
        } else if (item.restId) {
            imageUrl = item.restImgPath || await fetchImage(item.restName, item.restAddress);
            itemHtml = createRestaurantItem(item, index, imageUrl);
        } else if (item.lodId) {
            itemHtml = createAccommodationItem(item, index);
        }

        return itemHtml;
    }

    function createAttractionItem(item, index, imageUrl) {
        return `
            <div class="timeline-item">
                <div class="number1">${index + 1}</div>
                <div class="time-content">
                    <div class="time-date">12:07-12:07</div>
                    <div class="asd123">
                        <div class="datanav3">
                            <div class="pcolor">명소</div>
                            <div class="time-title">${item.toName}</div>
                        </div>
                        <div><img class="imgdiv1" src="${imageUrl}" alt=""></div>
                        <div>${item.toAddress}</div>
                        <div>${item.toHomePage}</div>
                    </div>
                </div>
            </div>
        `;
    }

    function createRestaurantItem(item, index, imageUrl) {
        return `
            <div class="timeline-item">
                <div class="number1">${index + 1}</div>
                <div class="time-content">
                    <div class="time-date">12:07-12:07</div>
                    <div class="asd123">
                        <div class="datanav3">
                            <div class="pcolor">식당</div>
                            <div class="time-title">${item.restName}</div>
                        </div>
                        <div><img class="imgdiv1" src="${imageUrl}" alt=""></div>
                        <div>${item.restAddress}</div>
                    </div>
                </div>
            </div>
        `;
    }

    function createAccommodationItem(item, index) {
        return `
            <div class="timeline-item">
                <div class="number1">${index + 1}</div>
                <div class="time-content">
                    <div class="time-date">12:07-12:07</div>
                    <div class="asd123">
                        <div class="datanav3">
                            <div class="pcolor">숙소</div>
                            <div class="time-title">${item.lodName}</div>
                        </div>
                        <div><img class="imgdiv1" src="${item.lodImgPath || 'default-image.jpg'}" alt=""></div>
                        <div>${item.lodAddress}</div>
                        <div><a href="${item.lodUrl}" target="_blank">예약하기</a></div>
                        <div>가격: ${item.lodPrice}</div>
                    </div>
                </div>
            </div>
        `;
    }

    async function fetchFestivals(trStartDate, trEndDate, areaCode) {
	    const festivalsUrl = createFestivalsUrl(trStartDate, trEndDate, areaCode);
	
	    try {
	        const response = await fetch(festivalsUrl);
	        if (!response.ok) {
	            throw new Error('Network response was not ok');
	        }
	        state.allFestivals = await response.json(); // 축제 데이터를 상태에 저장
	        displayFestivals(state.allFestivals);
	    } catch (error) {
	        console.error('Error fetching festivals:', error);
	    }
	}

	
	function createFestivalsUrl(trStartDate, trEndDate, areaCode) {
	    if (trStartDate && trEndDate) {
	        // 여행 날짜가 주어졌을 때
	        return `festivals?areacode=${areaCode}&tripStartDate=${trStartDate}&tripEndDate=${trEndDate}`;
	    } else {
	        // 여행 날짜가 주어지지 않았을 때
	        const today = new Date();
	        const currentMonth = (today.getMonth() + 1).toString().padStart(2, '0');
	        return `festivals?areacode=${areaCode}&month=${currentMonth}`;
	    }
	}


    function displayFestivals(festivals) {
        const festivalsContainer = $('#festivals-container');
        festivalsContainer.empty();

        if (festivals.length > 0) {
            let festivalsHtml = '<h2>추천 축제</h2><ul>';
            festivals.forEach(festival => {
                festivalsHtml += createFestivalItem(festival);
            });
            festivalsHtml += '</ul>';
            festivalsContainer.append(festivalsHtml);
        } else {
            festivalsContainer.append('<p>현재 축제 정보가 없습니다.</p>');
        }
    }

    function createFestivalItem(festival) {
        return `
            <li class="festival-item">
			    <h3 class="festival-title">${festival.title}</h3>
			    <img class="festImg" src="${festival.firstimage || 'default-image.jpg'}" alt="${festival.title}">
			    <p class="festival-description">${festival.addr1} ${festival.addr2 || ''}</p>
			    <p class="festival-description">${festival.eventstartdate} ~ ${festival.eventenddate}</p>
			    <p class="festival-description">${festival.tel || ''}</p>
			    <p class="festival-description">${festival.overviewYN || '정보 없음'}</p>
			    <div class="festival-details">
			        <p>상세 정보: ${festival.details || '상세 정보가 없습니다.'}</p>
			    </div>
			    <button class="add-to-course-btn" data-id="${festival.contentid}">코스에 추가</button> <!-- 추가 버튼 -->
			</li>
        `;
    }

    function createAllMarkers(dailyCourses) {
        const geocoder = new kakao.maps.services.Geocoder();
        const geocodingPromises = [];

        Object.entries(dailyCourses).forEach(([day, courseList]) => {
            state.dailyPaths[day] = [];
            state.dailyMarkers[day] = [];

            courseList.forEach((item, index) => {
                const promise = createMarkerPromise(geocoder, item, index, day);
                geocodingPromises.push(promise);
            });
        });

        return Promise.all(geocodingPromises);
    }

    function createMarkerPromise(geocoder, item, index, day) {
        return new Promise((resolve) => {
            if (item.toX && item.toY) {
                const position = new kakao.maps.LatLng(item.toY, item.toX);
                createMarker(position, item, index, day);
                state.dailyPaths[day][index] = position;
                resolve();
            } else {
                const address = item.restAddress || item.lodAddress || item.toAddress;
                if (address) {
                    geocoder.addressSearch(address, function(result, status) {
                        if (status === kakao.maps.services.Status.OK) {
                            const position = new kakao.maps.LatLng(result[0].y, result[0].x);
                            createMarker(position, item, index, day);
                            state.dailyPaths[day][index] = position;
                        }
                        resolve();
                    });
                } else {
                    resolve();
                }
            }
        });
    }

    function createMarker(position, item, index, day) {
        const markerImageUrl = `static/image/mapMarker${index + 1}.png`;
        const markerImage = new kakao.maps.MarkerImage(markerImageUrl, new kakao.maps.Size(36, 36));
        const marker = new kakao.maps.Marker({
            position,
            title: item.toName || item.restName || item.lodName,
            image: markerImage
        });
        
        const infowindow = createInfoWindow(item, index);
        
        kakao.maps.event.addListener(marker, 'mouseover', () => infowindow.open(state.map, marker));
        kakao.maps.event.addListener(marker, 'mouseout', () => infowindow.close());

        state.dailyMarkers[day][index] = marker;
    }

    function createInfoWindow(item, index) {
        const content = `
            <div style="padding: 5px; width: 200px;">
                <strong>${index + 1}. ${item.toName || item.restName || item.lodName}</strong><br>
                ${item.toAddress || item.restAddress || item.lodAddress}<br>
                ${item.lodPrice ? `가격: ${item.lodPrice}` : ''}
            </div>
        `;
        
        return new kakao.maps.InfoWindow({
            content: content,
            zIndex: 1
        });
    }

    function updateMapDisplay(selectedDay = null) {
        clearMap();
        let bounds = new kakao.maps.LatLngBounds();

        Object.entries(state.dailyPaths).forEach(([day, path]) => {
            if (!selectedDay || selectedDay === parseInt(day)) {
                displayDayPath(day, path, bounds);
            }
        });

        state.map.setBounds(bounds);
    }

    function clearMap() {
        Object.values(state.dailyMarkers).flat().forEach(marker => marker.setMap(null));
        state.currentPolylines.forEach(polyline => polyline.setMap(null));
        state.currentPolylines = [];
    }

    function displayDayPath(day, path, bounds) {
        const filteredPath = path.filter(Boolean);
        Object.values(state.dailyMarkers[day]).forEach(marker => {
            marker.setMap(state.map);
            bounds.extend(marker.getPosition());
        });
        
        if (filteredPath.length > 1) {
            const polyline = new kakao.maps.Polyline({
                path: filteredPath,
                strokeWeight: 5,
                strokeColor: getColor(parseInt(day) - 1),
                strokeOpacity: 0.7,
                strokeStyle: 'solid'
            });
            polyline.setMap(state.map);
            state.currentPolylines.push(polyline);
        }
    }

    function getColor(index) {
        const colors = ['#FF0000', '#00FF00', '#0000FF', '#FFFF00', '#FF00FF', '#00FFFF'];
        return colors[index % colors.length];
    }

    function toggleCourseView() {
        if (state.mapVisible) {
            $('#map').addClass('map-hidden');
            $('.day-button').addClass('map-hidden');
            $('#courses-container').css('width', '100%');
            $('#toggle-course-btn').text('지도 보기');
        } else {
            $('#map').removeClass('map-hidden');
            $('.day-button').removeClass('map-hidden');
            $('#courses-container').css('width', 'calc(100% - 800px)');
            $('#toggle-course-btn').text('코스만 보기');
        }
        state.mapVisible = !state.mapVisible;
    }

    function handleDayButtonClick() {
        const selectedDay = $(this).data('day');
        $('.day-button').removeClass('active');
        $(this).addClass('active');
        updateMapDisplay(selectedDay);
    }
    
    // 날짜 선택 모달을 표시하는 함수
function showDateSelectionModal(festival) {
    const modalHtml = `
        <div id="date-selection-modal" class="modal">
            <div class="modal-content">
                <span class="close-button">&times;</span>
                <h2>${festival.title} 추가</h2>
                <p>축제를 추가할 날짜를 선택하세요:</p>
                <div id="date-options">
                    ${Object.keys(state.allDailyCourses).map(dayNumber => 
                        `<button class="date-option" data-day="${dayNumber}">Day ${dayNumber}</button>`
                    ).join('')}
                </div>
                <button id="confirm-addition" data-festival-id="${festival.contentid}">확인</button>
            </div>
        </div>
    `;
    
    $('body').append(modalHtml);
    $('.modal').show();
    
    // 모달 닫기 버튼 클릭 시 모달 숨기기
    $('.close-button').click(() => {
        $('#date-selection-modal').remove();
    });

    // 날짜 버튼 클릭 시 활성화 처리
    $('#date-options').on('click', '.date-option', function() {
        $('.date-option').removeClass('active');
        $(this).addClass('active');
    });

    // 확인 버튼 클릭 시 축제 추가
    $('#confirm-addition').click(function() {
        const selectedDay = $('.date-option.active').data('day');
        if (selectedDay) {
            addFestivalToCourse(festival, selectedDay);
            $('#date-selection-modal').remove();
        } else {
            alert('날짜를 선택하세요.');
        }
    });
}
// 일정에 축제를 추가하는 함수
function addFestivalToCourse(festival, day) {
    // 해당 날짜의 타임라인 항목 개수를 계산하여 번호를 지정
    const courseContainer = $(`.day-container[data-day="${day}"] .timeline-container`);
    const itemCount = courseContainer.children('.timeline-item').length + 1;

    const festivalHtml = `
        <div class="timeline-item">
            <div class="number1">${itemCount}</div> <!-- 번호를 itemCount로 변경 -->
            <div class="time-content">
                <div class="asd123">
                    <div class="datanav3">
                        <div class="pcolor">축제</div>
                        <div class="time-title">${festival.title}</div>
                    </div>
                    <div><img class="imgdiv1" src="${festival.firstimage || 'default-image.jpg'}" alt="${festival.title}"></div>
                    <div>${festival.addr1} ${festival.addr2 || ''}</div>
                    <div>${festival.eventstartdate} ~ ${festival.eventenddate}</div>
                    <div>${festival.tel || ''}</div>
                </div>
            </div>
        </div>
    `;
    
    courseContainer.append(festivalHtml);
}

	
	// 현재 코스 데이터를 가져오는 함수 예시
function getCurrentCourseData() {
    // 실제로 DOM에서 코스 데이터를 가져오는 로직을 구현해야 합니다.
    return {
        dcourse: 'TOID:1887368,RESTID:697258,LODID:26' // 예시 데이터
    };
}

// 선택된 축제 정보를 가져오는 함수 예시
function getSelectedFestivals() {
    // 실제로 DOM에서 선택된 축제 정보를 가져오는 로직을 구현해야 합니다.
    return [
        { id: 1, name: '축제1' }, // 예시 데이터
        { id: 2, name: '축제2' }
    ];
}

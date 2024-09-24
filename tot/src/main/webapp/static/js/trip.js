$(document).ready(function() {
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

   let deletedItems = [];  // 임시 삭제된 아이템들을 저장할 배열
   let locationData; // 전역 변수로 데이터 정의
   
   $(document).on('click', '.pbtn', function() {
       const areacode = sessionStorage.getItem('areacode');
       const dayNumber = $(this).data('cour');
       const index = $(this).data('index');
       console.log('pbtn index:', index);
       console.log('areacode:', areacode);
       console.log('dayNumber:', dayNumber);

       $('#locationModal').data('index', index);
       $('#locationModal').data('dayNumber', dayNumber);
   
       // 명소 데이터를 가져오고 모달에 표시
       fetchLocationsByAreaCode(areacode, dayNumber).then(data => {
           // 데이터 렌더링
           renderLocationList(data);
           // 모달 표시
           $('#locationModal').css('display', 'block');
       });
   });
   
   $('#closeModal, #cancelModal').on('click', function() {
       $('#locationModal').css('display', 'none');
   });

   async function fetchLocationsByAreaCode(areacode, dayNumber) {
       try {
           const response = await fetch(`/tot/triplist/locations?areacode=${areacode}`);
           if (response.ok) {
               const data = await response.json();
               console.log('Fetched locations:', data); // 데이터 확인
               // 데이터의 구조를 확인
               if (!data.hotels || !data.restaurants || !data.tours) {
                   console.error('Invalid data structure:', data);
               }
               return data;
           } else {
               console.error('Failed to fetch locations:', response.statusText);
           }
       } catch (error) {
           console.error('Error fetching locations:', error);
       }
       return { hotels: [], restaurants: [], tours: [] }; // 기본값 반환
   }

   function renderLocationList(data) {
       locationData = data; // 함수가 호출될 때 데이터를 저장
   
       const locationList = $('#locationList');
       locationList.empty();
   
       // 데이터 유효성 검사
       if (!data || !data.hotels || !data.restaurants || !data.tours) {
           console.error('유효하지 않은 데이터 구조:', data);
           return; // 유효하지 않으면 함수 종료
       }
   
       // 모든 장소를 하나의 배열로 통합
       const allLocations = [
           ...data.hotels.map(hotel => ({
               type: '호텔',
               id: hotel.lodId,
               idName: 'LODID:',
               name: hotel.lodName,
               address: hotel.lodAddress,
               image: hotel.lodImgPath
           })),
           ...data.restaurants.map(restaurant => ({
               type: '식당',
               id: restaurant.restId,
               idName: 'RESTID:',
               name: restaurant.restName,
               address: restaurant.restAddress,
               image: restaurant.restImgPath
           })),
           ...data.tours.map(tour => ({
               type: '관광지',
               id: tour.toId,
               idName: 'TOID:',
               name: tour.toName,
               address: tour.toAddress,
               image: tour.toImgPath
           }))
       ];
   
       // 타입 필터링
       const selectedType = $('#locationTypeSelect').val();
       const filteredLocations = selectedType ? allLocations.filter(location => location.type === selectedType) : allLocations;
   
      filteredLocations.forEach(location => {
           const locationDiv = $(`
               <ul class="location-item">
                   <input type="radio" name="location-radio" value="${location.idName + location.id}">
                   <li class="location-info">
                       <div class="location-type">${location.type}</div>
                       <img src="${location.image || 'default-image.jpg'}" alt="${location.name}" class="location-image">
                       <div class="location-details">
                           <div class="location-name">${location.name}</div>
                           <div class="location-address">${location.address}</div>
                       </div>
                   </li>
               </ul>
           `);
           locationList.append(locationDiv);
       });
   }
   
   // 드롭다운 변경 시 목록 갱신
   $('#locationTypeSelect').change(() => {
       renderLocationList(locationData); // 전역 변수 사용
   });

   $('#addSelectedLocation').on('click', function() {
       console.log("등록 버튼 클릭");
       
       // 모달에서 저장된 인덱스와 dayNumber 값을 가져오기
       const index = $('#locationModal').data('index');
       const dayNumber = $('#locationModal').data('dayNumber');
       
       console.log("index:", index);
       console.log("dayNumber:", dayNumber);
       
       // 선택된 라디오 버튼의 값 가져오기
       const selectedLocation = $('input[name="location-radio"]:checked').val();
       
       // 선택된 장소가 없으면 경고
       if (!selectedLocation) {
           alert("선택된 장소가 없습니다.");
           return;
       }
       
       // location 값을 분리하여 ID 및 타입 얻기
       const [idType, id] = selectedLocation.split(':');
       let name, type, address, imageUrl, price, lodUrl, toHomePage;
   
       // locationData에서 선택한 ID와 매칭되는 항목을 찾아서 정보 설정
       if (idType === 'LODID') {
           const hotel = locationData.hotels.find(h => h.lodId == id);
           name = hotel.lodName;
           type = '호텔';
           address = hotel.lodAddress;
           imageUrl = hotel.lodImgPath || 'default-image.jpg';
           price = hotel.lodPrice || '가격 정보 없음';
           lodUrl = hotel.lodUrl || 'URL 없음';
       } else if (idType === 'RESTID') {
           const restaurant = locationData.restaurants.find(r => r.restId == id);
           name = restaurant.restName;
           type = '식당';
           address = restaurant.restAddress;
           imageUrl = restaurant.restImgPath || 'default-image.jpg';
       } else if (idType === 'TOID') {
           const tour = locationData.tours.find(t => t.toId == id);
           name = tour.toName;
           type = '관광지';
           address = tour.toAddress;
           imageUrl = tour.toImgPath || 'default-image.jpg';
           toHomePage = tour.toHomePage || '홈페이지 정보 없음';
       }
   
       // 새로운 타임라인 아이템 생성
       let newTimelineItem = `
           <div class="timeline-item" data-cour="${dayNumber}">
               <div class="number1">${index + 1}</div>
               <div class="time-content">
                   <div class="time-date">시간정보</div>
                   <div class="asd123">
                       <div class="datanav3">
                           <div class="pcolor">${type}</div>
                           <div class="time-title">${name}</div>
                           <button class="mbtn" data-name="${address}" data-id="${id}" data-type="${idType}" data-cour="${dayNumber}" data-index="${index}">
                               <img src="./static/image/mbtn.png" alt="">
                           </button>
                           <button class="pbtn" data-cour="${dayNumber}">
                               <img src="./static/image/pbtn.png" alt="">
                           </button>
                       </div>
                       <div><img class="imgdiv1" src="${imageUrl}" alt=""></div>
                       <div>${address}</div>
       `;
       
       // 호텔일 경우 가격과 URL 추가
       if (idType === 'LODID') {
           newTimelineItem += `
               <div>가격: ${price}</div>
               <div><a href="${lodUrl}" target="_blank">예약하기</a></div>
           `;
       }
       
       // 관광지일 경우 홈페이지 정보 추가
       if (idType === 'TOID') {
           newTimelineItem += `
               <div>홈페이지: <a href="${toHomePage}" target="_blank">${toHomePage}</a></div>
           `;
       }
       
       // 타임라인 아이템 닫기
       newTimelineItem += `
                   </div>
               </div>
           </div>
       `;
       
      // 타임라인에 새 항목 추가
       const targetContainer = $(`.day-container[data-day="${dayNumber}"] .timeline-container`);
   
       // 원하는 위치에 삽입 (index에 따라)
       const existingItems = targetContainer.children('.timeline-item');
       if (index < existingItems.length) {
           // 특정 인덱스 앞에 추가
           $(existingItems[index]).before(newTimelineItem);
       } else {
           // 마지막에 추가
           targetContainer.append(newTimelineItem);
       }
       
      updateIndexes(dayNumber);
      addLocationToMap({  idType, id, name, address, imageUrl, dayNumber, index, price });

       // 모달 닫기
       $('#locationModal').css('display', 'none');
   });
   
   function addLocationToMap(location) {
       const { idType, id, name, address, imageUrl, dayNumber, index, price } = location;
   
       const geocoder = new kakao.maps.services.Geocoder();
   
       // 주소로 위치 찾기
       geocoder.addressSearch(address, function(result, status) {
           if (status === kakao.maps.services.Status.OK) {
               const position = new kakao.maps.LatLng(result[0].y, result[0].x);
               const item = {
                   toName: name || '', // 이름
                   toAddress: address || '', // 주소
                   lodPrice: price || '', // 가격 (추가된 속성)
                   restName: '', // 필요한 경우 나중에 설정
                   restAddress: '', // 필요한 경우 나중에 설정
                   lodName: '', // 필요한 경우 나중에 설정
               };
               createMarker(position, item, index, dayNumber);
            if (!state.dailyPaths[dayNumber]) {
                   state.dailyPaths[dayNumber] = [];
               }
               state.dailyPaths[dayNumber].push(position);
               updatePolyline(dayNumber);
           } else {
               console.error('주소를 찾을 수 없습니다:', address);
           }
       });
   }

   function updatePolyline(dayNumber) {
       const path = state.dailyPaths[dayNumber].filter(Boolean);
       if (path.length > 1) {
           const polyline = new kakao.maps.Polyline({
               path,
               strokeWeight: 4,
               strokeColor: '#FF0000',
               strokeOpacity: 0.8,
               strokeStyle: 'solid'
           });
   
           polyline.setMap(state.map); 
           state.currentPolylines.push(polyline);
       }
   }
   
   // 삭제 버튼 클릭 시 호출
   $(document).on('click', '.mbtn', function() {
       const tripId = getQueryParam('tripId');
       const address = $(this).data('name');
       const dataId = $(this).data('id');
       const index = $(this).data('index'); // 인덱스 찾기
       const itemType = $(this).data('type'); // itemType을 data 속성으로 추가해야 함
       const coId = $(this).data('cour'); 
       
       console.log('mbtn index:', index);
   
       let identifier = '';
       if (itemType === 'TOID') {
           identifier = `TOID:${dataId}`;
       } else if (itemType === 'RESTID') {
           identifier = `RESTID:${dataId}`;
       } else if (itemType === 'LODID') {
           identifier = `LODID:${dataId}`;
       }
       console.log(identifier);
   
       // 임시로 삭제 처리
       deleteItemTemporarily(identifier, index,coId);

   });
   
   function deleteItemTemporarily(identifier, index, coId) {
       // UI에서 해당 아이템을 숨김
       const itemElements = $(`.timeline-item[data-cour="${coId}"]`);
       const itemElement = itemElements.eq(index);
       itemElement.hide();  
       deletedItems.push({ identifier, index, coId });
   
       // 지도에서 마커 제거
       const markers = state.dailyMarkers[coId];
       if (markers && markers.length > index) { // 마커가 존재하는지 확인
           const markerToRemove = markers[index];
           if (markerToRemove) {
               markerToRemove.setMap(null); // 마커 제거
           }
           
           // 마커 배열에서 해당 마커 삭제
           markers.splice(index, 1); // 해당 인덱스의 마커 삭제
   
           // 경로에서 해당 위치 null로 설정
           state.dailyPaths[coId][index] = null; 
   
           // 경로 업데이트: null을 제외한 필터링
           const filteredPath = state.dailyPaths[coId].filter(Boolean);
           state.dailyPaths[coId] = filteredPath; 
   
           updateMapDisplay(coId); // 선택한 day에 대한 업데이트
       }
   
       updateIndexes(coId); // 인덱스 업데이트
   }
   
   // 삭제 취소
   function cancelChanges() {
       deletedItems.forEach(({ identifier, index, coId }) => {
           const itemElements = $(`.timeline-item[data-cour="${coId}"]`); // coId에 맞는 요소 선택
           const itemElement = itemElements.eq(index); // 인덱스에 해당하는 요소 선택
           itemElement.show();  // UI에서 복구
       });
       deletedItems = [];  // 삭제된 항목 초기화
   }
   
   function updateIndexes(dayNumber) {
       const targetContainer = $(`.day-container[data-day="${dayNumber}"] .timeline-container`);
       targetContainer.children('.timeline-item:visible').each((index, element) => {
           $(element).find('.number1').text(index + 1); // 표시된 인덱스를 업데이트
       });
   }
   
   // 코스 업데이트 함수
   async function updateCourse(coId, tripId, targetId, index) {
       try {
           const response = await fetch(`/tot/triplist/update?courId=${coId}&tripId=${tripId}&targetId=${encodeURIComponent(targetId)}&index=${index}`, {
               method: 'POST',
               headers: {
                   'Content-Type': 'application/json'
               }
           });
   
           if (response.ok) {
               console.log('Course updated successfully');
               deletedItems = [];  // 저장 후 삭제 배열 초기화
               refreshCourseList(tripId); // 코스 리스트 새로고침
           } else {
               console.error('Failed to update course:', response.statusText);
           }
       } catch (error) {
           console.error('Error updating course:', error);
       }
   }
   
   // 저장 버튼 클릭 시 최종 삭제 저장 호출
   document.getElementById('save-button').addEventListener('click', () => {
       deletedItems.forEach(({ identifier, index,coId }) => {
           // identifier 전체를 updateCourse에 전달
           updateCourse(coId, getQueryParam('tripId'), identifier, index); // 각 삭제된 항목에 대해 업데이트
       });
   });
   
   // 취소 버튼 클릭 시 삭제 취소 호출
   document.getElementById('cancel-button').addEventListener('click', cancelChanges);
   
   // 코스 리스트 새로고침 함수
   async function refreshCourseList(tripId) {
       const allDailyCourses = await fetchLocations(tripId); // tripId는 초기화 시 가져온 값
       displayCourses(allDailyCourses); // 코스 UI 업데이트

       updateMapDisplay(); // 맵 업데이트
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

   function createMarker(position, item, index, dayNumber) {
       const markerImageUrl = `static/image/mapMarker${index + 1}.png`; // 마커 이미지 경로
       const markerImage = new kakao.maps.MarkerImage(markerImageUrl, new kakao.maps.Size(36, 36));
   
       const marker = new kakao.maps.Marker({
           position: position,
           map: state.map, 
           image: markerImage
       });
   
       const infowindow = createInfoWindow(item, index);
       console.log("maker index :" + index);
   
       kakao.maps.event.addListener(marker, 'mouseover', () => infowindow.open(state.map, marker));
       kakao.maps.event.addListener(marker, 'mouseout', () => infowindow.close());
   
       // 마커가 추가될 배열을 초기화
       if (!state.dailyMarkers[dayNumber]) {
           state.dailyMarkers[dayNumber] = []; 
       }
   
       // 기존 마커 인덱스 업데이트
       if (index < state.dailyMarkers[dayNumber].length) {
           // index 위치에 마커를 추가할 경우, 기존 마커들 업데이트
           for (let i = index; i < state.dailyMarkers[dayNumber].length; i++) {
               const currentMarker = state.dailyMarkers[dayNumber][i];
               if (currentMarker) {
                   // 기존 마커의 이미지 경로와 인덱스 업데이트
                   currentMarker.setMap(null); // 기존 마커 제거
                   const newImageUrl = `static/image/mapMarker${i + 1}.png`; // 새 인덱스에 맞는 이미지 경로
                   currentMarker.image = new kakao.maps.MarkerImage(newImageUrl, new kakao.maps.Size(36, 36)); // 새 이미지로 업데이트
                   currentMarker.setMap(state.map); // 다시 지도에 추가
               }
           }
       }
   
       // 새로운 마커를 지정된 인덱스에 추가
       state.dailyMarkers[dayNumber].splice(index, 0, marker); // 해당 인덱스에 마커 추가
   
       updateIndexes(dayNumber); // 인덱스 업데이트
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
       state.selectedDay = selectedDay; // 선택된 일차를 저장
   
       // 선택된 일차만 업데이트
       if (selectedDay) {
           displayDayPath(selectedDay, state.dailyPaths[selectedDay], bounds);
       } else {
           // 선택되지 않았을 때는 모든 일차 표시
           Object.entries(state.dailyPaths).forEach(([day, path]) => {
               displayDayPath(day, path, bounds);
           });
       }
   
   }
   
   function clearMap() {
       // 모든 마커와 Polyline을 지도에서 제거
       Object.values(state.dailyMarkers).flat().forEach(marker => marker.setMap(null));
       state.currentPolylines.forEach(polyline => polyline.setMap(null));
       state.currentPolylines = [];
   }
   
   function displayDayPath(day, path, bounds) {
       const filteredPath = path.filter(Boolean);
   
       // 선택된 일차의 마커만 표시
       Object.values(state.dailyMarkers[day]).forEach(marker => {
           marker.setMap(state.map);
           bounds.extend(marker.getPosition());
       });
   
       // 선택된 일차의 Polyline만 표시
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
            $('#map').addClass('hidden');
            $('.day-button').addClass('hidden');
            $('#courses-container').css('width', '100%');
            $('#toggle-course-btn').text('지도 보기');
        } else {
            $('#map').removeClass('hidden');
            $('.day-button').removeClass('hidden');
            $('#courses-container').css('width', 'calc(100% - 800px)');
            $('#toggle-course-btn').text('코스만 보기');
        }
        state.mapVisible = !state.mapVisible;
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
       if (trip && trip.areacode) {
           sessionStorage.setItem('areacode', trip.areacode);
       }
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

    function handleDayButtonClick() {
        const selectedDay = $(this).data('day');
        $('.day-button').removeClass('active');
        $(this).addClass('active');
        updateMapDisplay(selectedDay);
    }

    // Event listeners
    $('#toggle-course-btn').click(toggleCourseView);
    $('#course-buttons').on('click', '.day-button', handleDayButtonClick);

   $(document).on('click', '.add-to-course-btn', function() {
       const festivalId = $(this).data('id');
       const festival = state.allFestivals.find(f => f.contentid == festivalId);
   
       if (festival) {
           // 날짜를 선택할 수 있는 모달을 표시
           showDateSelectionModal(festival);
       } else {
           console.error('Festival not found');
       }
   });
   
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
   
       const sortedDays = Object.keys(dailyCourses).map(Number).sort((a, b) => a - b);
       const minDayNumber = sortedDays[0]; 
   
       for (const dayNumber of sortedDays) {
           console.log(dayNumber); // courId
           const adjustedDayNumber = dayNumber - minDayNumber + 1;
   
           let timelineHtml = createDayContainer(adjustedDayNumber, dayNumber);  
   
           const dailyList = dailyCourses[dayNumber]; 
           for (let index = 0; index < dailyList.length; index++) {
               const item = dailyList[index];
               timelineHtml += await createTimelineItem(item, index, dayNumber); 
           }
   
           timelineHtml += '</div></div>';
           coursesContainer.append(timelineHtml);
           courseButtons.append(`<button class="day-button" data-day="${dayNumber}">Day ${adjustedDayNumber}</button>`);
       }
   }

    function createDayContainer(adjustedDayNumber, originalDayNumber) {
       return `
           <div class="day-container active" data-day="${originalDayNumber}">
               <div class="datanav1">
                   <div class="date1">${adjustedDayNumber}일차</div>
               </div>
               <div class="timeline-container">
       `;
   }

    async function createTimelineItem(item, index,dayNumber) {
        let imageUrl = 'default-image.jpg';
        let itemHtml = '';

        if (item.toId) {
            imageUrl = item.toImgPath || await fetchImage(item.toName, item.toAddress);
            itemHtml = createAttractionItem(item, index, imageUrl,dayNumber);
        } else if (item.restId) {
            imageUrl = item.restImgPath || await fetchImage(item.restName, item.restAddress);
            itemHtml = createRestaurantItem(item, index, imageUrl,dayNumber);
        } else if (item.lodId) {
            itemHtml = createAccommodationItem(item, index,dayNumber);
        }

        return itemHtml;
    }

    function createAttractionItem(item, index, imageUrl,dayNumber) {
          const latitude = item.toX ? item.toX : '';
          const longitude = item.toY ? item.toY : '';
        return `
            <div class="timeline-item" data-cour="${dayNumber}"              
          ${latitude ? `data-latitude="${item.toX}"` : ''} 
             ${longitude ? `data-longitude="${item.toY}"` : ''}>
                <div class="number1">${index + 1}</div>
                <div class="time-content">
                    <div class="time-date">12:07-12:07</div>
                    <div class="asd123">
                        <div class="datanav3">
                            <div class="pcolor">명소</div>
                            <div class="time-title">${item.toName}</div>
                            <button class="mbtn" data-name="${item.toAddress}" data-id="${item.toId}" data-type="TOID" data-cour="${dayNumber}" data-index="${index}"><img src="./static/image/mbtn.png" alt=""></button>
                            <button class="pbtn" data-cour="${dayNumber}" data-index="${index + 1}"><img src="./static/image/pbtn.png" alt=""></button>
                        </div>
                        <div><img class="imgdiv1" src="${imageUrl}" alt=""></div>
                        <div>${item.toAddress}</div>
                        <div>${item.toHomePage}</div>
                    </div>
                </div>
            </div>
        `;
    }

    function createRestaurantItem(item, index, imageUrl,dayNumber) {
        return `
            <div class="timeline-item" data-cour="${dayNumber}">
                <div class="number1">${index + 1}</div>
                <div class="time-content">
                    <div class="time-date">12:07-12:07</div>
                    <div class="asd123">
                        <div class="datanav3">
                            <div class="pcolor">식당</div>
                            <div class="time-title">${item.restName}</div>
                            <button class="mbtn" data-name="${item.restAddress}" data-id="${item.restId}" data-type="RESTID" data-cour="${dayNumber}" data-index="${index}"><img src="./static/image/mbtn.png" alt=""></button>
                            <button class="pbtn" data-cour="${dayNumber}" data-index="${index + 1}"><img src="./static/image/pbtn.png" alt=""></button>
                        </div>
                        <div><img class="imgdiv1" src="${imageUrl}" alt=""></div>
                        <div>${item.restAddress}</div>
                    </div>
                </div>
            </div>
        `;
    }

    function createAccommodationItem(item, index,dayNumber) {
        return `
            <div class="timeline-item" data-cour="${dayNumber}">
                <div class="number1">${index + 1}</div>
                <div class="time-content">
                    <div class="time-date">12:07-12:07</div>
                    <div class="asd123">
                        <div class="datanav3">
                            <div class="pcolor">숙소</div>
                            <div class="time-title">${item.lodName}</div>
                            <button class="mbtn" data-name="${item.lodAddress}" data-id="${item.lodId}" data-type="LODID" data-cour="${dayNumber}" data-index="${index}"><img src="./static/image/mbtn.png" alt=""></button>
                            <button class="pbtn" data-cour="${dayNumber}" data-index="${index + 1}"><img src="./static/image/pbtn.png" alt=""></button>
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


   }

    // Initialize the map
    initializeMap();
});

   


   
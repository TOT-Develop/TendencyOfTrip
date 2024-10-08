// URL 및 에러 메시지 선언
const BASE_TREVIEW_URL = '/tot/admin/review';
const ALL_ADMIN_ACTIVE_TREVIEW_URL = `${BASE_TREVIEW_URL}/1/1`;
const ALL_ADMIN_DEACTIVE_TREVIEW_URL = `${BASE_TREVIEW_URL}/2/1`;

const ERROR_MESSAGES = {
    NOT_SELECT_TREVIEW: '하나 이상의 후기 게시물을 선택해야 합니다.',
    FAIL_UPDATE_STATUS: '상태 변경 중 오류가 발생했습니다'
};

$(document).ready(() => {
    setupPage();
    setupButtonClickHandlers();
});

// 페이지 초기화 함수
const setupPage = () => {
    let path = window.location.pathname;
    const isActivePath = new RegExp(`${BASE_TREVIEW_URL}/1/\\d+`).test(path);
    const isDeactivePath = new RegExp(`${BASE_TREVIEW_URL}/2/\\d+`).test(path);

    $('#activeBtn').toggleClass('active', isActivePath);
    $('#deactiveBtn').toggleClass('active', isDeactivePath);
};

// 버튼 클릭 핸들러 설정 함수
const setupButtonClickHandlers = () => {
	
    // 모든 후기 활성화 버튼 클릭 시 처리
    $('#activeBtn').on('click', (e) => {
        e.preventDefault(); // 기본 동작 방지
        window.location.href = ALL_ADMIN_ACTIVE_TREVIEW_URL;
    });
    
     // 모든 후기 비활성화 버튼 클릭 시 처리
    $('#deactiveBtn').on('click', (e) => {
        e.preventDefault(); // 기본 동작 방지
        window.location.href = ALL_ADMIN_DEACTIVE_TREVIEW_URL;
    });

    $("#selectAll").change(function () {
        $("input[name='reviewSelect']").prop("checked", this.checked);
    });

    // 배치 활성화/비활성화 버튼 클릭 핸들러
    $('.activeButton').on('click', (e) => {
        e.preventDefault(); // 링크 이동 방지
        handleBatchUpdate(e);
    });
    
    $('.backBtn').on('click', () => window.history.back());
    $('.activeButtonDiv button').on('click', handleSingleUpdate);
    $('.uploadTRevImg').on('click', toggleImageModal);
};

// 배치 활성화/비활성화 처리 함수
const handleBatchUpdate = (e) => {
    e.preventDefault();
    e.stopPropagation();

    const trevIds = getCheckedReviewIds();
    if (trevIds.length === 0) {
        alert(ERROR_MESSAGES.NOT_SELECT_TREVIEW);
        return;
    }
    
	const action = $('#deactiveBtn').hasClass('active') ? '비활성화' : '활성화';
    const dataToSend = {
        trevIds: [trevIds],
        reason: 'INAPPROPRIATE'
    };
    
    const url = $(e.currentTarget).attr('href');
    handleActiveStatus(url, JSON.stringify(dataToSend), action === '활성화');
};

// 개별 게시물 활성화/비활성화 처리 함수
const handleSingleUpdate = function () {
    const button = $(this);
    const action = button.text() === '활성화' ? '비활성화' : '활성화';
    const trevIds = button.closest('.activeButtonDiv').data('trevid');

    if (confirm(`${action} 하시겠습니까?`)) {
        const url = button.data('url');
        const dataToSend = {
            trevIds: [trevIds],
            reason: 'INAPPROPRIATE'
        };
       // 비활성화하는 경우에는 true를 전달
       handleActiveStatus(url, JSON.stringify(dataToSend), action === '비활성화');
    }
};

// 체크된 후기 ID를 반환하는 함수
const getCheckedReviewIds = () => {
    return $("input[name='reviewSelect']:checked").map(function () {
        return $(this).val();
    }).get();
};

// 활성화, 비활성화 처리 함수
const handleActiveStatus = (url, data, isDeactivating = false) => {
    if (isDeactivating) {
        showLoadingScreen();
        initializeProgressBar();
    }

    $.ajax({
        type: 'POST',
        url: url,
        contentType: 'application/json',
        data: data,
        success: function (response) {
            alert(response.message);
            location.reload();
        },
        error: function (xhr) {
            const errorResponse = xhr.responseJSON;
            alert(errorResponse?.message || ERROR_MESSAGES.FAIL_UPDATE_STATUS);
        },
        complete: function() {
            completeProgressBar();
        }
    });
};

// 로딩 화면과 프로그래스 바 표시
const showLoadingScreen = () => {
    $('#loading').show();
};

// 프로그래스 바 초기화
const initializeProgressBar = () => {
    $('#progressBar').css('width', '0%');
    let progress = 0;

    const progressInterval = setInterval(() => {
        if (progress < 90) {
            progress += 10;
            updateProgressBar(progress);
        }
    }, 100);

    return progressInterval;
};

// 프로그래스 바 업데이트
const updateProgressBar = (progress) => {
    $('#progressBar').css('width', progress + '%');
};

// 프로그래스 바 완료 처리
const completeProgressBar = () => {
    updateProgressBar(100);
    setTimeout(() => {
        $('#loading').hide();
    }, 500);
};

// 이미지 클릭 시 모달 창 토글
const toggleImageModal = function () {
    const modal = $('#imageModal');
    const modalImg = $('#modalImage');
    const captionText = $('#caption');

    modal.show();
    modalImg.attr('src', $(this).attr('src'));
    captionText.text($(this).attr('alt'));
};

// 이미지 모달 창 닫기
const closeModal = () => {
    $('#imageModal').hide();
};

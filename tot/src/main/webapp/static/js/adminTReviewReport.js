//  URL 선언
const BASE_TREVIEW_URL = '/tot/admin/report'; // 여행 후기 기본 URL
const ALL_ADMIN_ACTIVE_TREVIEW_REPORT_URL = `${BASE_TREVIEW_URL}/1/1`; // 활성화 게시물 조회 URL
const ALL_ADMIN_DEACTIVE_TREVIEW_REPORT_URL = `${BASE_TREVIEW_URL}/2/1`; // 비활성화 게시물 조회 URL

// 에러 메시지 선언
const ERROR_MESSAGES = {
    NOT_SELECT_REPORT: '하나 이상의 후기 신고 내역을 선택해야 합니다.',
    FAIL_UPDATE_STATUS: '상태 변경 중 오류가 발생했습니다'
};

$(document).ready(() => {
    // 현재 경로에 따라 버튼 활성화
    let path = window.location.pathname;

    // 활성화 경로 패턴 (BASE_TREVIEW_URL/1/** 형태)
    const activePathPattern = new RegExp(`${BASE_TREVIEW_URL}/1/\\d+`);
    
    // 비활성화 경로 패턴 (BASE_TREVIEW_URL/2/** 형태)
    const deactivePathPattern = new RegExp(`${BASE_TREVIEW_URL}/2/\\d+`);

    // 경로에 따라 버튼 활성화/비활성화 로직
    if (activePathPattern.test(path)) {
        $('#activeBtn').addClass('active');
        $('#deactiveBtn').removeClass('active');
    } else if (deactivePathPattern.test(path)) {
        $('#deactiveBtn').addClass('active');
        $('#activeBtn').removeClass('active');
    }

    // 활성화 버튼 클릭 시 활성화 댓글 목록 처리
    $('#activeBtn').on('click', () => {
        window.location.href = ALL_ADMIN_ACTIVE_TREVIEW_REPORT_URL;
    });

    // 비활성화 버튼 클릭 시 비활성화 댓글 목록 처리
    $('#deactiveBtn').on('click', () => {
        window.location.href = ALL_ADMIN_DEACTIVE_TREVIEW_REPORT_URL;
    });

    // 신고 내역 관리 전체 선택 및 해제
    $("#selectAll").change(function () {
        $("input[name='reportSelect']").prop("checked", this.checked);
    });

    // 행 전체 클릭 시 체크박스 선택
    $('tbody tr').on('click', function() {
        const checkbox = $(this).find('input[name="reportSelect"]');
        checkbox.prop('checked', !checkbox.prop('checked'));
    });
    
    // 체크박스를 클릭시 체크박스 선택
    $('input[name="reportSelect"]').on('click', function(event) {
        const checkbox = $(this);
        checkbox.prop('checked', !checkbox.prop('checked'));
    });

    // 상태 선택 변화에 따른 제재 이유 활성화/비활성화
    $('#reportStatus').on('change', function() {
        const selectedStatus = $(this).val();
        if (selectedStatus === "COMPLETED") {
            $('#banReason').prop('disabled', false); // "완료"인 경우 활성화
        } else {
        	// 다른 상태인 경우 비활성화 및 첫 번째 옵션으로 초기화
            $('#banReason').prop('disabled', true).val($('#banReason option:first').val());
        }
    });
    
    // 신고 내역 목록에서 체크한 댓글에 대한 활성화, 비활성화 처리
    $('.activeButton').on('click', function (e) {
        e.preventDefault();
        e.stopPropagation();

        const selectedReports = $("input[name='reportSelect']:checked").map(function () {
            return $(this).val();
        }).get();

        if (selectedReports.length === 0) {
            alert(ERROR_MESSAGES.NOT_SELECT_REPORT);
            return;
        }
        
        // 모달창 띄우기
		$('#statusModal').show();
		
		// 제재 사유 초기화
        $('#banReason').val($('#banReason option:first').val()); // 첫 번째 옵션으로 초기화
        $('#banReason').prop('disabled', true); // 기본 비활성화
		
		// 확인 버튼 클릭 시 신고 처리 요청
		$('#confirmUpdateBtn').off('click').on('click', function() {
	        const status = $('#reportStatus').val();
	        const reason = $('#banReason').val();
	        const url = $(e.currentTarget).attr('href');
	
	        // 단순 ID 배열을 JSON.stringify로 변환하여 전송
	        const dataToSend = {
                reportIds: selectedReports.map(id => parseInt(id)),
                status: status,
                reason: reason
            };
	        
	        handleActiveStatus(url.replace("{status}", status), JSON.stringify(dataToSend));
	        $('#statusModal').hide();
    	});
    });
     
    // 모달 닫기 버튼 클릭 시 모달 닫기
    $('.close2').on('click', function() {
        $('#statusModal').hide();
    });

	// 모달 외부 클릭 시 모달 닫기
    $(window).on('click', function(event) {
        if ($(event.target).is('#statusModal')) {
            $('#statusModal').hide();
        }
    });
    
    // 댓글 사유 최대 길이 조정
    handleReportMaxLength();
    
    // 신고사유를 클릭했을 때 모달 열기 
    $('.report-reason').on('click', function() {
        const reportText = $(this).data('full-reason');
        const trimmedReportText = reportText.trim();
        $('#modalReportContent').text(reportText);
        $('#reportModal').show();
    });

    // 모달 닫기 버튼 클릭 시 모달 닫기
    $('.close2').on('click', function() {
        $('#reportModal').hide(); // 모달 닫기
    });

    // 모달 외부 클릭 시 모달 닫기
    $(window).on('click', function(event) {
        if ($(event.target).is('#reportModal')) {
            $('#reportModal').hide();
        }
    });
    
});

// 활성화, 비활성화 처리 함수
const handleActiveStatus = (url, data) => {
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
            // 서버에서 전달한 오류 메시지를 확인
            const errorResponse = xhr.responseJSON;
            if (errorResponse && errorResponse.message) {
                alert(errorResponse.message);
            } else {
                alert(ERROR_MESSAGES.FAIL_UPDATE_STATUS);
            }
        }
    });
}

// 게시물, 댓글 내용 및 신고사유 최대 길이 조정 함수
const handleReportMaxLength = () => {
	const maxLength = 15; // 최대 글자 수

    $('.report-content').each(function() {
        const $link = $(this).find('a'); // report-content 안의 a 태그
        const content = $link.text().trim();

        if (content.length > maxLength) {
            $link.text(content.slice(0, maxLength) + '...');
        } else {
            $link.text(content);
        }
    });
    
    $('.report-reason').each(function() {
        const content = $(this).text().trim();

        if (content.length > maxLength) {
            $(this).text(content.slice(0, maxLength) + '...');
        } else {
            $(this).text(content);
        }
    });
}


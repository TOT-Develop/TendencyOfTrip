//  URL 선언
const BASE_TREVIEW_URL = '/tot/admin/comment'; // 여행 후기 기본 URL
const ALL_ADMIN_ACTIVE_TREVIEW_COMMENT_URL = `${BASE_TREVIEW_URL}/1/1`; // 활성화 게시물 조회 URL
const ALL_ADMIN_DEACTIVE_TREVIEW_COMMENT_URL = `${BASE_TREVIEW_URL}/2/1`; // 비활성화 게시물 조회 URL

// 에러 메시지 선언
const ERROR_MESSAGES = {
    NOT_SELECT_TREVIEW: '하나 이상의 후기 댓글을 선택해야 합니다.',
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

    if (path.includes(ALL_ADMIN_ACTIVE_TREVIEW_COMMENT_URL)) {
        $('#activeBtn').addClass('active');
        $('#deactiveBtn').removeClass('active');
    } else if (path.includes(ALL_ADMIN_DEACTIVE_TREVIEW_COMMENT_URL)) {
        $('#deactiveBtn').addClass('active');
        $('#activeBtn').removeClass('active');
    }

    // 활성화 버튼 클릭 시 활성화 댓글 목록 처리
    $('#activeBtn').on('click', () => {
        window.location.href = ALL_ADMIN_ACTIVE_TREVIEW_COMMENT_URL;
    });

    // 비활성화 버튼 클릭 시 비활성화 댓글 목록 처리
    $('#deactiveBtn').on('click', () => {
        window.location.href = ALL_ADMIN_DEACTIVE_TREVIEW_COMMENT_URL;
    });

    // 댓글 관리 전체 선택 및 해제
    $("#selectAll").change(function () {
        $("input[name='commentSelect']").prop("checked", this.checked);
    });

    // 댓글 목록에서 체크한 댓글에 대한 활성화, 비활성화 처리
    $('.activeButton').on('click', function (e) {
        e.preventDefault();
        e.stopPropagation();

        const selectedReviews = $("input[name='commentSelect']:checked").map(function () {
            return $(this).val();
        }).get();

        if (selectedReviews.length === 0) {
            alert(ERROR_MESSAGES.NOT_SELECT_TREVIEW);
            return;
        }

        handleActiveStatus($(this).attr('href'), JSON.stringify(selectedReviews));
    });
    
    // 행 전체 클릭 시 체크박스 선택
    $('tbody tr').on('click', function() {
        const checkbox = $(this).find('input[name="commentSelect"]');
        checkbox.prop('checked', !checkbox.prop('checked'));
    });
    
    // 체크박스를 클릭시 체크박스 선택
    $('input[name="commentSelect"]').on('click', function(event) {
        const checkbox = $(this);
        checkbox.prop('checked', !checkbox.prop('checked'));
    });

    // 댓글 상세 관리에서 활성화, 비활성화 처리
    $('.activeButtonDiv button').on('click', function () {
        const button = $(this);
        const action = button.text() === '활성화' ? '비활성화' : '활성화';
        const trevId = button.closest('.activeButtonDiv').data('trevid');

        if (confirm(`${action} 하시겠습니까?`)) {
            const url = button.data('url');
            const data = JSON.stringify([trevId]);

            handleActiveStatus(url, data);
        }
    });

	// 댓글 내용 최대 길이 조정
    handleCommentMaxLength();
    
    // 댓글 내용을 클릭했을 때 모달 열기 
    $('.comment-content').on('click', function() {
        const commentText = $(this).data('full-text');
        $('#modalCommentContent').text(commentText);
        $('#commentModal').show();
    });

    // 모달 닫기 버튼 클릭 시 모달 닫기
    $('.close2').on('click', function() {
        $('#commentModal').hide(); // 모달 닫기
    });

    // 모달 외부 클릭 시 모달 닫기
    $(window).on('click', function(event) {
        if ($(event.target).is('#commentModal')) {
            $('#commentModal').hide();
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

// 댓글 내용 최대 길이 조정 함수
const handleCommentMaxLength = () => {
	const maxLength = 20; // 최대 글자 수

    $('.comment-content').each(function() {
        const content = $(this).text().trim();

        if (content.length > maxLength) {
            $(this).text(content.slice(0, maxLength) + '...');
        } else {
            $(this).text(content);
        }
    });
}


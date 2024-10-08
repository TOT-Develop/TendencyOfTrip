//  URL 선언
const IS_MEMBER_LOGIN_URL = '/tot/member/checkLogin'; // 사용자 로그인 여부 확인 URL
const LOGIN_URL = '/tot/login'; // 로그인 URL
const BASE_TREVIEW_URL = '/tot/review'; // 여행 후기 기본 URL
const ALL_TREVIEW_URL = `${BASE_TREVIEW_URL}/1/1`; // 전체 후기 조회 URL
const MY_TREVIEW_URL = `${BASE_TREVIEW_URL}/2/1`; // 나의 후기 조회 URL
const WRITE_TREVIEW_URL = `${BASE_TREVIEW_URL}/1/add`; // 후기 작성 URL

// 에러 메시지 선언
const ERROR_MESSAGES = {
    TITLE_REQUIRED: '제목을 입력해주세요.',
    TRIP_REQUIRED: '작성할 여행을 선택해주세요.',
    RATING_REQUIRED: '후기 평점을 선택해주세요.',
    CONTENT_REQUIRED: '후기 내용을 입력해주세요.',
    LOGIN_REQUIRED: '회원 정보가 없습니다. 로그인 화면으로 이동합니다.',
    FAIL_LOGIN_CONFIRM: '로그인 상태 확인 중 오류 발생',
    AGREE_REQUIRED: '개인정보 수집 및 이용에 동의하셔야 글을 작성할 수 있습니다.',
    FAIL_TREVIEW_DELETE: '여행 후기글 삭제를 실패했습니다.',
    FAIL_COMMON_ERROR: '예상치 못한 오류가 발생했습니다. 관리자에게 문의하세요. ',
    FAIL_GET_COURSE: '코스를 가져오는 데 실패했습니다.',
    FAIL_EDIT_COMMENT: '댓글 수정에 실패했습니다.',
    FAIL_DELETE_COMMENT: '댓글 삭제를 실패했습니다.',
    FAIL_REPORT_TREVIEW: '여행 후기 신고 접수를 실패했습니다.',
    FAIL_TOTAL: '신고 중 오류가 발생했습니다. 관리자에게 문의하세요.',
    MAXIMUM_FILE_SIZE: '업로드할 수 있는 총 파일 크기를 초과했습니다. (100MB 이하로 제한)'
};

// 파일 크기 선언
const MAX_TOTAL_SIZE = 104857600; // 총 최대 용량: 100MB
const MAX_FILE_SIZE = 10485760; // 개별 최대 용량: 10MB

let fileList = [];  // 업로드 파일 리스트 초기화

$(document).ready(() => {
    // 업로드 이미지 파일 초기화
    initFileList();

    // 현재 경로에 따라 버튼 활성화
    let path = window.location.pathname;

    if (path.includes(ALL_TREVIEW_URL)) {
        $('#TotalReviewsBtn').addClass('active');
        $('#myReviewsBtn').removeClass('active');
    } else if (path.includes(MY_TREVIEW_URL)) {
        $('#myReviewsBtn').addClass('active');
        $('#TotalReviewsBtn').removeClass('active');
    }

    // 여행 선택 시 코스 로드
    $('#travelTrip').on('change', function () {
        var tripId = $(this).val();

        if (tripId) {
            handleCourseSelect(tripId);
        }
    });

    // 글쓰기 버튼 클릭 시 이동
    $('#writeReviewBtn').on('click', function () {
        window.location.href = WRITE_TREVIEW_URL;
    });

    // 이미지 업로드시 미리보기 생성
    $('#reviewImage').on('change', handleFileSelect);

    // 입력 필드에서 엔터키 입력 시 새로운 입력 필드 생성 없으면 삭제
    $('#reviewContentAndImgDiv').on('keydown', '.reviewContent', function (event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            createNewInputField();
        } else if (event.key === 'Backspace') {
            handleBackspace(event);
        }
    });

    // 이미지 미리보기 삭제 버튼 클릭시 삭제 처리
    $(document).on('click', '#delImgBtn', function () {
        deletePreviewImg($(this));
    });

    // 여행 후기 등록 버튼 클릭 시 유효성 검사 후 등록
    $('#submitButton').on('click', function (event) {
        event.preventDefault();
        const validationResult = validate();

        if (validationResult.isValid) {
            submitReview();
        } else {
            alert(validationResult.errorMessage);
        }
    });

    // 여행 후기 수정 버튼 클릭 시 유효성 검사 후 수정
    $('#editButton').on('click', function (event) {
        event.preventDefault();

        const validationResult = validate();
        if (validationResult.isValid) {
            editReview();
        } else {
            alert(validationResult.errorMessage);
        }
    })

    // 여행 후기 삭제 버튼 클릭 시 삭제 확인 및 처리
    $('#delTReviewBtn').on('click', function (event) {
        event.preventDefault();

        const reviewTitle = $(this).data('title');
        const deleteUrl = $(this).data('delete-url');

        if (confirm(`${reviewTitle} 여행 후기를 삭제하시겠습니까?`)) {
            $.get({
                url: deleteUrl,
                success: function (response) {
                    alert(response.message);
                    window.location.href = MY_TREVIEW_URL;
                },
                error: function (error) {
                    alert(ERROR_MESSAGES.FAIL_TREVIEW_DELETE);
                    console.error(error);
                }
            });
        }
    });

    // 여행 후기 글 신고 버튼 클릭 시 신고 모달 창 띄우기
    $('#reportTReviewBtn').on('click', async function (event) {
        event.preventDefault();

	    const isLoggedIn = await checkLoginStatus();
	    if (!isLoggedIn) {
	        return; // 로그인하지 않은 경우 바로 리턴
	    }
	
	    // 로그인 된 경우만 실행
	    openReportModal($(this));
    });

    // 전체 여행 후기 버튼 클릭 시 전체 여행 후기 페이지 이동
    $('#TotalReviewsBtn').on('click', () => {
        window.location.href = ALL_TREVIEW_URL;
    });

    // 나의 여행 후기 버튼 클릭 시 나의 여행 후기 페이지 이동
    $('#myReviewsBtn').on('click', () => {
        window.location.href = MY_TREVIEW_URL;
    });

    // 취소하기 버튼 클릭 시 목록 페이지로 이동
    $('#cancleButton').on('click', () => {
        window.history.back();
    });

    // 나의 여행 상세 후기 목록 버튼 클릭 시 뒤로가기
    $('#reviewListBtn').on('click', () => {
        window.location.href = ALL_TREVIEW_URL;
    });

    // 여행 상세 후기 댓글 취소 버튼 클릭 시 내용 삭제
    $('#commentCancelBtn').on('click', () => {
        $('#commentContent').val('');
    });

    // 댓글 취소 버튼 클릭 시 댓글 내용 비우기
    $('#commentCancelBtn').on('click', () => {
        $('#commentContent').val('');
    });

    // 대댓글 작성 버튼 클릭 시 대댓글 입력 폼 열기
    $('.commentReply').on('click', function () {
        let parentNickname = $(this).closest('.commentItem').find('.commentMember').text().trim();
        let replyForm = $(this).next('.commentReplyForm');
        let parentNicknameDisplay = replyForm.find('.parentNicknameDisplay');
        let parentNicknameInput = replyForm.find('.parentNicknameInput');

        if (replyForm.find('input[name="content"]').val() === '') {
            parentNicknameDisplay.html('<span class="nickname-highlight">' + '@' + parentNickname + '</span> ');
            parentNicknameInput.val(parentNickname);
        }
        replyForm.toggle();
    });

    // 대댓글 취소 버튼 클릭 시 댓글 내용 비우기
    $('.commentReplyForm .cancelReply').on('click', function () {
        let replyForm = $(this).closest('.commentReplyForm');
        replyForm.find('input[name="content"]').val('');
    });

    // 댓글 설정 버튼 클릭 시 수정, 삭제, 신고 목록 열기
    $(document).on('click', '.commentSetting', function () {
        $('.commentOptionsMenu').not($(this).next('.commentOptionsMenu')).hide();
        $(this).next('.commentOptionsMenu').toggle();
    });

    // 댓글 설정의 수정 버튼 클릭 시 input으로 변경
    $('.commentOptionsMenu a.editComment').click(function (e) {
        e.preventDefault();

        let commentItem = $(this).closest('.commentDetailItem');
        let commentText = commentItem.find('.commentText');
        let editUrl = $(this).attr('href');

        // 댓글 내용이 이미 input으로 변경된 상태인지 확인
        if (commentText.html().includes('<input')) {
            return;
        }

        // 댓글의 현재 내용을 가져와서 data 속성에 저장
        let originalText = commentText.text().trim().replace(/\s+/g, ' ');
        commentItem.data('original-text', originalText);

        // 기존의 저장 및 취소 버튼이 이미 있는 경우 제거
        commentText.find('.editCommentInput, .saveCommentEditBtn, .cancelCommentEditBtn').remove();

        // 기존의 댓글을 input으로 변경
        commentText.html('<input type="text" class="editCommentInput" value="' + originalText + '" />');
        commentText.append('<button class="saveCommentEditBtn initButton active" data-edit-url="' + editUrl + '">저장</button>');
        commentText.append('<button class="cancelCommentEditBtn initButton">취소</button>');
    });

    // 댓글 수정 저장 버튼 클릭 시 수정된 댓글을 서버로 전송
    $(document).on('click', '.saveCommentEditBtn', function () {
        let commentItem = $(this).closest('.commentDetailItem');
        let newContent = commentItem.find('.editCommentInput').val();
        let editUrl = $(this).data('edit-url');

        $.ajax({
            type: "POST",
            url: editUrl,
            data: {
                content: newContent
            },
            dataType: "json",
            success: function (response) {
                // 성공 시, 댓글 내용을 다시 텍스트로 변경
                commentItem.find('.commentText').html(newContent);
                commentItem.find('.saveCommentEditBtn').remove();
                commentItem.find('.cancelCommentEditBtn').remove();
                commentItem.find('.commentDate').html(response.updatedDate);
                alert(response.message);
            },
            error: function (xhr, status, error) {
                console.log(error);
                alert(ERROR_MESSAGES.FAIL_EDIT_COMMENT + " " + xhr.responseText);
            }
        });
    });

    // 댓글 수정 취소 버튼 클릭 시 수정 취소
    $(document).on('click', '.cancelCommentEditBtn', function () {
        let commentItem = $(this).closest('.commentDetailItem');

        // data 속성에서 원래 내용 가져오기
        let originalText = commentItem.data('original-text');

        // 댓글 내용을 원래대로 복원
        commentItem.find('.commentText').html(originalText);

        commentItem.find('.saveCommentEditBtn').remove();
        commentItem.find('.cancelCommentEditBtn').remove();
    });

    // 댓글 삭제 버튼 클릭 시 삭제 여부 확인 및 삭제 기능 구현
    $(document).on('click', '.deleteComment', function (e) {
        e.preventDefault();
        let commentItem = $(this).closest('.commentItem');
        let commentText = commentItem.find('.commentText').text().trim();
        let deleteUrl = $(this).attr('href');

        // 사용자에게 삭제 확인
        if (confirm(`"${commentText}"댓글과 모든 하위댓글을 삭제하시겠습니까?`)) {
            $.ajax({
                url: deleteUrl,
                type: 'GET',
                dataType: "json",
                success: function (response) {
                    // 성공 시, 성공 응답 메시지 출력
                    alert(response.message);
                    window.location.reload();
                },
                error: function (error) {
                    alert(ERROR_MESSAGES.FAIL_DELETE_COMMENT);
                    console.error(error);
                }
            });
        }
    });

    // 댓글 신고 버튼 클릭 시 신고 모달 창 띄우기
    $(document).on('click', '.reportComment', async function (e) {
        e.preventDefault();
        const isLoggedIn = await checkLoginStatus();

		if (isLoggedIn) {
	        let commentText = $(this).closest('.commentItem').find('.commentText').text().trim();
	        let reportUrl = $(this).attr('href');
	        let reportedContentType = 'Treview Comment';
	
	        $('#reportCommentText').text(`댓글 내용: "${commentText}"`);
	        $('#reportForm').attr('action', reportUrl);
	        $('#reportForm').find('input[name="reportedContentType"]').val(reportedContentType);
	
	        $('#reportModal').show();
        } else {
        	$('#reportModal').hide();
        }
    });

    // 신고 댓글 모달 폼 닫기
    $(document).on('click', '.close-button', function () {
        $('#reportModal').hide();
    });

    // 신고 제출 시 신고 처리
    $('#reportForm').on('submit', function (e) {
        e.preventDefault();

        let reportReason = $('#reportReason').val();
        let formAction = $(this).attr('action');
        let reportType = $('#reportForm').find('input[name="reportedContentType"]').val();

        $.ajax({
            url: formAction,
            method: 'POST',
            dataType: 'json',
            data: {
                reportedContentType: reportType,
                reportReason: reportReason,
            },
            success: function (response) {
                alert(response.message);
                $('#reportModal').hide();
                $('#reportReason').val(''); // 신고 사유 텍스트 영역 비우기
                $('#reportCommentText').text(''); // 신고 내용 텍스트 비우기
            },
            error: function (xhr, jqXHR, textStatus, errorThrown) {
                console.log(xhr.status);
                console.log('에러 상태:', textStatus);
                console.log('에러 내용:', errorThrown);
                console.log('서버 응답:', xhr.responseText);
                if (xhr.status === 403) {
                    errorMessage = ERROR_MESSAGES.LOGIN_REQUIRED;
                } else if (xhr.status === 302) {
                    let redirectUrl = xhr.getResponseHeader('Location');
                    if (redirectUrl) {
                        window.location.href = redirectUrl;
                    }
                } else {
                    errorMessage = ERROR_MESSAGES.FAIL_TOTAL;
                }
                alert(errorMessage);
            }
        });
    });

    // 댓글 보기 버튼 클릭 시 댓글 목록 토글
    $(document).on('click', '.showRepliesButton', function () {
        const commentId = $(this).data('comment-id');
        const replyComments = $(`.replyComments[data-comment-id="${commentId}"]`);

        // 댓글 목록의 표시 상태를 토글
        replyComments.toggle();

        // 버튼 텍스트를 변경
        if (replyComments.is(':visible')) {
            $(this).text('▼ 댓글 닫기');
        } else {
            $(this).text('▶ 댓글 보기');
        }
    });

});

// 여행 선택 시 이벤트 처리
const handleCourseSelect = tripId => {
    $.ajax({
        url: `${WRITE_TREVIEW_URL}/${tripId}`,
        type: 'GET',
        data: { tripId: tripId },
        success: function (courses) {
            let courseHtml = '';
            courses.forEach((course, index) => {
                let day = `[Day${index + 1}]`;
                let courseDetails = course.courseDetail.map(detail => detail.dname).join(' &rarr; ');
                courseHtml += `<div class="courseDetailItem">${day} ${courseDetails}</div><hr/>`;
            });
            $('#travelCourse').html(courseHtml);
        },
        error: function () {
            alert(ERROR_MESSAGES.FAIL_GET_COURSE);
        }
    });
}

// 업로드 파일 정보와 함께 글쓰기 등록 처리
const submitReview = () => {
    const formData = new FormData($('#reviewForm')[0]);
    fileList.forEach(file => {
        formData.append('reviewImage', file);
    });

    $.post({
        url: `${WRITE_TREVIEW_URL}`,
        data: formData,
        contentType: false,
        processData: false,
        dataType: "json",
        success: function (response) {
            alert(response.message);
            window.location.href = ALL_TREVIEW_URL;
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log('Error status:', jqXHR.status);
            console.log('Response:', jqXHR.responseText);
            console.log('Text status:', textStatus);
            console.log('Error thrown:', errorThrown);

            try {
                const errorResponse = JSON.parse(jqXHR.responseText);
                alert(errorResponse.message);
            } catch (e) {
                console.log(e);
                alert(ERROR_MESSAGES.FAIL_COMMON_ERROR);
            }
        }
    });
}

// 글 수정하기 처리
const editReview = () => {
    const formUrl = $('#reviewForm').attr('action');
    const formData = new FormData($('#reviewForm')[0]);

    fileList.forEach(file => {
        if (file instanceof File) {
            // File 객체인 경우
            formData.append('reviewImage', file);
        } else if (typeof file === 'object' && file.url) {
            // URL 객체인 경우
            formData.append('existingImages', file.url);
        }
    });

    $.post({
        url: formUrl,
        data: formData,
        contentType: false,
        processData: false,
        dataType: "json",
        success: function (response) {
            alert(response.message);
            window.location.href = ALL_TREVIEW_URL;
        },
        error: function (jqXHR) {
            try {
                const errorResponse = JSON.parse(jqXHR.responseText);
                alert(errorResponse.message);
            } catch (e) {
                alert(ERROR_MESSAGES.FAIL_COMMON_ERROR);
            }
        }
    });
}

// 이미지 파일 초기화
const initFileList = () => {
    const existingImages = $('.file-input-wrapper input[name="reviewImage"]').map((_, input) => $(input).val()).get();
    // 기존 이미지 URL을 파일 객체처럼 다룰 수 있도록 객체로 변환
    const imageObjects = existingImages.map((imgSrc) => ({
        name: imgSrc.split('/').pop(),
        url: imgSrc
    }));

    fileList = [...fileList, ...imageObjects];
}

// 이미지 파일 업로드 처리
const handleFileSelect = event => {
    const files = Array.from(event.target.files);

    if (!checkFileSize(files)) {
        $('#reviewImage').val('');
        return;
    }

    fileList = [...fileList, ...files];

    if (files) {
        $.each(files, (index, file) => {
            let reader = new FileReader();

            reader.onload = function (e) {
                addNewPreviewImg(index, e.target.result);
                addNewFileInput(file);
            };
            reader.readAsDataURL(file);
        });
        $('#reviewImage').val('');
    }
}

// 여행 후기 내역에 이미지 미리보기 생성
const addNewPreviewImg = (index, path) => {
    const img = $('<img>', {
        src: path,
        alt: `Trip Review Image ${index + 1}`,
        name: 'reviewImage',
        class: 'reviewImage'
    });
    const imgWrapper = $('<div>', { class: 'img-wrapper' });

    let hiddenImageInput = $('<input>', {
        type: 'hidden',
        name: 'trevContent',
        value: 'image'
    });
    imgWrapper.append(img).append(hiddenImageInput);
    $('#reviewContentAndImgDiv').append(imgWrapper);
}

// 이미지 파일 업로드 내역 생성
const addNewFileInput = file => {
    const inputWrapper = $('<div>', {
        class: 'file-input-wrapper'
    });

    const fileNameInput = $('<input>', {
        type: 'text',
        name: 'reviewImage',
        value: `${file.name}`,
        multiple: 'multiple'
    });

    const deleteButton = $('<button>', {
        type: 'button',
        id: 'delImgBtn',
        text: '삭제',
        class: 'initButton'
    });

    inputWrapper.append(fileNameInput).append(deleteButton);
    $('.reviewImageDiv').append(inputWrapper);
}

// 이미지 파일 업로드 내역 및 미리보기 삭제
const deletePreviewImg = button => {
    const inputWrapper = button.closest('.file-input-wrapper');
    const fileName = inputWrapper.find('input[name="reviewImage"]').val();
    const index = $('.file-input-wrapper').index(inputWrapper);
    const imgWrapper = $($('.img-wrapper').get(index));

    imgWrapper.remove();
    inputWrapper.remove();

    fileList = fileList.filter(file => file.name !== fileName);
};

const createNewInputField = () => {
    let newInput = $('<input>', {
        class: 'reviewContent',
        name: 'trevContent'
    });

    $('#reviewContentAndImgDiv').append(newInput);
    newInput.focus();
}

const handleBackspace = event => {
    const currentInput = $(event.target);
    if (currentInput.val().length === 0) {
        const inputs = $('#reviewContentAndImgDiv').find('.reviewContent');

        if (inputs.length > 1) {
            event.preventDefault();
            currentInput.remove();

            const lastInput = $('#reviewContentAndImgDiv').find('.reviewContent').last();
            lastInput.focus();
        }
    }
}

// 로그인 여부 유효성 검사
const checkLoginStatus = async () => {
    try {
        const response = await $.ajax({
            url: IS_MEMBER_LOGIN_URL,
            type: 'GET',
            dataType: 'json'
        });

        if (response.loggedIn) {
            return true; // 로그인된 경우
        } else {
            alert(ERROR_MESSAGES.LOGIN_REQUIRED);
            window.location.href = LOGIN_URL;
            return false; // 로그인되지 않은 경우
        }
    } catch (error) {
        console.log(error);
        alert(ERROR_MESSAGES.FAIL_LOGIN_CONFIRM);
        window.location.href = LOGIN_URL;
        return false;
    }
};

// 여행 후기글 신고 모달 열기
const openReportModal = (button) => {
    const reviewTitle = button.data('title');
    const reportUrl = button.data('report-url');
    const reportedContentType = 'Treview';

    $('#reportCommentText').text(`여행 후기 제목 : "${reviewTitle}"`);
    $('#reportForm').attr('action', reportUrl);
    $('#reportForm').find('input[name="reportedContentType"]').val(reportedContentType);

    $('#reportModal').show();
};

// 공백 여부 유효성 검사
const checkField = (selector, errorMessage) => {
    const value = $(selector)[0].value;
    return value === '' ? errorMessage : '';
};

// 후기 평점 유효성 검사
const checkRating = (errorMessage) => {
    return $('input[name="trevRating"]:checked').length === 0 ? errorMessage : '';
};

// 업로드 파일 크기 검사
const checkFileSize = files => {
    let totalSize = fileList.reduce((acc, file) => acc + file.size, 0); // 기존 파일 크기 합계

    for (const file of files) {
        totalSize += file.size;

        // 개별 파일 크기 검사
        if (file.size > MAX_FILE_SIZE) {
            alert(`파일 "${file.name}"의 크기가 10MB를 초과합니다.`);
            return false;
        }
    }

    // 총 파일 크기 검사
    if (totalSize > MAX_TOTAL_SIZE) {
        alert(ERROR_MESSAGES.MAXIMUM_FILE_SIZE);
        return false;
    }

    return true;
}

const validate = () => {
    const validations = [
        { selector: '#reviewTitle', errorMessage: ERROR_MESSAGES.TITLE_REQUIRED },
        { selector: '#travelTrip', errorMessage: ERROR_MESSAGES.TRIP_REQUIRED },
        { selector: '#reviewContentAndImgDiv .reviewContent', errorMessage: ERROR_MESSAGES.CONTENT_REQUIRED }
    ];

    // 나머지 필드 유효성 검사
    for (const { selector, errorMessage } of validations) {
        const message = checkField(selector, errorMessage);
        if (message) {
            return { isValid: false, errorMessage: message };
        }
    }

    // 평점 유효성 검사
    const ratingMessage = checkRating(ERROR_MESSAGES.RATING_REQUIRED);
    if (ratingMessage) {
        return { isValid: false, errorMessage: ratingMessage };
    }

    // 체크박스 유효성 검사
    if (!$('#agreeRadio').is(':checked')) {
        return { isValid: false, errorMessage: ERROR_MESSAGES.AGREE_REQUIRED };
    }

    return { isValid: true, errorMessage: '' };
}
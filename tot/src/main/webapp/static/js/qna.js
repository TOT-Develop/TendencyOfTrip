//  URL 선언
const URL = {
    LOGIN_URL: '/tot/login',
    BASE_QNA_URL: '/tot/qna/1/1',
    MY_QNA_URL: '/tot/qna/2/1',
    WRITE_QNA_URL: '/tot/qna/1/add'
};

$(document).ready(function () {

    // 글쓰기 페이지로 이동
    $('.toWrite').click(function () {
        window.location.href = URL.WRITE_QNA_URL;
    });

    // 문의글 작성 취소한 경우
    $('.cancelButton').click(function () {
        window.location.href = URL.BASE_QNA_URL;
    });

    // 문의글 등록하기 클릭한 경우
    $('.registButton').on('click', function (e) {
        e.preventDefault();

        $.ajax({
            url: URL.WRITE_QNA_URL,
            type: 'POST',
            data: $('form').serialize(),
            success: function (response) {
                alert(response.message);
                window.location.href = URL.BASE_QNA_URL;
            },
            error: function (xhr) {
                let errorResponse = JSON.parse(xhr.responseText);
                alert(errorResponse.message);
            }
        });
    });

	// '전체 글 보기' 버튼 클릭 시
    $('#totalQnaBtn').click(function () {
        window.location.href = URL.BASE_QNA_URL;
    });
    
    // '내 글 보기' 버튼 클릭 시
    $('#myQnaBtn').click(function () {
        window.location.href = URL.MY_QNA_URL;
    });
    
    // '목록으로 이동' 버튼 클릭 시
    $(".toListBtn").click(function() {
        window.location.href = URL.BASE_QNA_URL;
    });

});

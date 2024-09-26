$(document).ready(function () {
    console.log('Document ready and JavaScript running...');
    
    let isMyQnaView = false;  // 전체 보기인지, 내 글 보기를 하는지 상태를 저장하는 변수
    let currentPage = 1;      // 현재 페이지 번호

    const qnaPage = 5;
    const categoryMap = {
        "Q01": "계정관리",
        "Q02": "기술지원",
        "Q03": "불만요청",
        "Q04": "기타요청"
    };

    // 검색 버튼 클릭 시 검색 기능 실행
    $('#searchForm').submit(function(e) {
        e.preventDefault(); // 페이지 이동 막기
        isMyQnaView = false; // 검색 시에는 전체 글 보기로 전환

        const selectedCategory = $('.qnaCategory').val();  // 선택한 카테고리 값
        const searchKeyword = $('.searchBox').val();       // 입력한 검색어 값

        // 첫 번째 페이지로 검색 결과를 로드
        loadQnaList(selectedCategory, 1, searchKeyword);
    });

    // 글쓰기 페이지로 이동
    $('.toWrite').click(function() {
        window.location.href = "qnaRegist.jsp";
    });

    // QnA 목록 로드 함수 (카테고리, 페이지번호에 따라 로드)
    function loadQnaList(category = 'ALL', page = 1, search = '') {
        const params = new URLSearchParams({
            searchType: category,
            page: page,
            search: search // 검색어 추가
        });

        fetch(`/tot/qna/api?${params.toString()}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok.');
                }
                return response.json();
            })
            .then(data => {
                console.log("Fetched data:", data);  // 데이터를 출력하여 확인

                $('#qnaTableBody').html('');  // 테이블 내용을 비웁니다.

                if (data.length === 0) {
                    $('#qnaTableBody').append('<tr><td colspan="4">검색 결과가 없습니다.</td></tr>');
                    return;
                }

                // 데이터를 테이블에 표시
                data.forEach(qna => {
                    const formattedDate = new Date(qna.qnaregdate).toLocaleString();
                    const category = categoryMap[qna.qna_001] || '알 수 없음';

                    let QnaLi = $(`
                        <tr id="qnaContent" data-id="${qna.qnaid}">
                            <input type="hidden" name="qnaContent1" value="${qna.memid}"/>
                            <td>${category}</td>
                            <td>${qna.qnatitle}</td>
                            <td>${qna.memNick}</td>
                            <td>${formattedDate}</td>
                        </tr>
                    `);
                    $('#qnaTableBody').append(QnaLi);
                });

                // 페이지 번호 갱신
                $('.currentBtn').text(page);  // 현재 페이지 번호 갱신

                // 클릭 시 QnA 상세보기로 이동
                $('#qnaTableBody').on('click', '#qnaContent', function () {
                    const qnaid = $(this).data('id');
                    sessionStorage.setItem('qnaid', qnaid);
                    window.location.href = `qnaDetail.jsp?QNAID=${encodeURIComponent(qnaid)}`;
                });
            })
            .catch(error => console.error('Error fetching QnA list:', error));
    }

    // '내 글 보기' 버튼 클릭 시
    $('#myQnaBtn').click(function () {
        isMyQnaView = true;  // 내 글 보기 상태로 설정
        currentPage = 1;     // 첫 번째 페이지부터 로드
        loadMyQnaList(currentPage);
    });

    // 내 글 목록 로드 함수
    function loadMyQnaList(page = 1) {
        fetch(`/tot/qna/myQna?page=${page}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok.');
                }
                return response.json();
            })
            .then(data => {
                $('#qnaTableBody').html('');  // 기존 테이블 내용을 비웁니다.

                if (data.length === 0) {
                    $('#qnaTableBody').append('<tr><td colspan="4">작성한 글이 없습니다.</td></tr>');
                    return;
                }

                // 데이터를 테이블에 표시
                data.forEach(qna => {
                    const formattedDate = new Date(qna.qnaregdate).toLocaleString();
                    const category = categoryMap[qna.qna_001] || '알 수 없음';

                    let QnaLi = $(`
                        <tr id="qnaContent" data-id="${qna.qnaid}">
                            <input type="hidden" name="qnaContent1" value="${qna.memid}"/>
                            <td>${category}</td>
                            <td>${qna.qnatitle}</td>
                            <td>${qna.memNick}</td>
                            <td>${formattedDate}</td>
                        </tr>
                    `);
                    $('#qnaTableBody').append(QnaLi);
                });

                // 페이지 번호 갱신
                $('.currentBtn').text(page);  // 현재 페이지 번호 갱신
            })
            .catch(error => console.error('Error:', error));
    }

    // 페이징 처리 (이전 페이지 버튼)
    $('.prevBtn').click(function () {
	    console.log('Previous button clicked');
	    const currentPage = parseInt($('.currentBtn').text(), 10);
	    if (currentPage > 1) {
	        if (isMyQnaView) {
	            loadMyQnaList(currentPage - 1);
	        } else {
	            loadQnaList($('.qnaCategory').val(), currentPage - 1);
	        }
	    }
	});
	
	$('.nextBtn').click(function () {
	    console.log('Next button clicked');
	    const currentPage = parseInt($('.currentBtn').text(), 10);
	    if (isMyQnaView) {
	        loadMyQnaList(currentPage + 1);
	    } else {
	        loadQnaList($('.qnaCategory').val(), currentPage + 1);
	    }
	});


    // 페이지 로드 시 기본으로 전체 카테고리와 첫 번째 페이지 로드
    loadQnaList();  // 초기 데이터 로드
});

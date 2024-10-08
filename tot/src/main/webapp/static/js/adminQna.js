$(document).ready(function () {
    console.log('Document ready and JavaScript running...');

    let isMyQnaView = false;
    let currentPage = 1;

    const qnaPage = 5;
    const categoryMap = {
        "Q01": "계정관리",
        "Q02": "기술지원",
        "Q03": "불만요청",
        "Q04": "기타요청"
    };
    let currentStatus = '';

    // 카테고리 변경 시 이벤트 처리
    $('.qnaCategory').change(function () {
        const selectedCategory = $(this).val();
        console.log('카테고리 변경:', selectedCategory);
        loadQnaList(selectedCategory, 1, '', currentStatus); // 선택된 카테고리와 첫 번째 페이지로 리스트 로드
    });

    // 접수전체 버튼 클릭 이벤트 처리
    $('#allFilterBtn').click(function () {
        console.log('접수전체 버튼 클릭됨');
        currentStatus = ''
        loadQnaList($('.qnaCategory').val(), 1, '', ''); // 상태값을 ''로 설정하여 목록 로드
    });

    // 접수대기 버튼 클릭 이벤트 처리
    $('#pendingFilterBtn').click(function () {
        console.log('접수대기 버튼 클릭됨');
        currentStatus = 'RECEIVED'
        loadQnaList($('.qnaCategory').val(), 1, '', 'RECEIVED'); // 상태값을 'RECEIVED'로 설정하여 목록 로드
    });

    // 접수완료 버튼 클릭 이벤트 처리
    $('#completedFilterBtn').click(function () {
        console.log('접수완료 버튼 클릭됨');
        currentStatus = 'COMPLETED'
        loadQnaList($('.qnaCategory').val(), 1, '', 'COMPLETED'); // 상태값을 'COMPLETED'로 설정하여 목록 로드
    });

    // 검색 버튼 클릭 시 이벤트 처리
    $('#searchForm').submit(function (e) {
        e.preventDefault();
        isMyQnaView = false;
        const selectedCategory = $('.qnaCategory').val();
        const searchKeyword = $('.searchBox').val();
        console.log('검색어:', searchKeyword, '카테고리:', selectedCategory, '상태:', currentStatus);

        loadQnaList(selectedCategory, 1, searchKeyword, currentStatus);

        $('.searchBox').val('');
    });

    // 제목 긴거 자르기
    function truncateText(text, maxLength) {
        if (text.length > maxLength) {
            return text.substring(0, maxLength) + '...';
        }
        return text;
    }

    function loadQnaList(category = 'ALL', page = 1, search = '', status = '', boardId = 1) {
        const params = new URLSearchParams({
            category: category, // category 값을 서버로 전달
            page: page,          // 페이지 번호 전달
            search: search,      // 검색어 전달
            status: status		 // 상태(RECEIVED or COMPLETED)
        });


        fetch(`/tot/admin/qna/${boardId}/${page}?${params.toString()}`, {
            headers: {
                'X-Requested-With': 'XMLHttpRequest'  // This line indicates it's an AJAX request
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(pagination => {
                $('#qnaTableBody').html('');

                if (!pagination || !pagination.postList || pagination.postList.length === 0) {
                    $('#qnaTableBody').append('<tr><td colspan="6">검색 결과가 없습니다.</td></tr>');
                    return;
                }

                pagination.postList.forEach(qna => {

                    console.log('qna 전체 값 : ', qna);

                    const formattedDate = qna.qnaRegdate ? new Date(qna.qnaRegdate).toLocaleString() : '날짜 없음';
                    const categoryName = categoryMap[qna.qna_001] || '알 수 없음';
                    const truncatedTitle = truncateText(qna.qnaTitle, 25);

                    // 상태에 따른 텍스트 및 클래스 설정
                    let statusText;
                    let statusClass = '';

                    switch (qna.qna_002) {
                        case 'RECEIVED':
                            statusText = '접수대기';
                            statusClass = 'receivedStatus'
                            break;
                        case 'COMPLETED':
                            statusText = '접수완료';
                            statusClass = 'completedStatus'
                            break;
                        default:
                            statusText = '알 수 없음';
                            statusClass = 'defaultStatus'
                    }

                    let changeStatusButton = `
                    <button class="changeStatusBtn ${statusClass}" 
                            data-id="${qna.qnaId}" 
                            data-status="${qna.qna_002}">
                        ${statusText}
                    </button>`;

                    let qnaRow = `
                    <tr id="qnaContent" data-id="${qna.qnaId}">
                        <td>${categoryName}</td>
                        <td title="${qna.qnaTitle}">${truncatedTitle}</td>
                        <td>${qna.memNick}</td>
                        <td>${formattedDate}</td>
                        <td>${statusText}</td>
                        <td>${changeStatusButton}</td>
                    </tr>`;
                    $('#qnaTableBody').append(qnaRow);
                });

                $('.currentBtn').text(page);

                $('#qnaTableBody').off('click').on('click', '#qnaContent', function () {
                    const qnaId = $(this).data('id');
                    sessionStorage.setItem('qnaId', qnaId);
                    window.location.href = `detail/${qnaId}`;
                });

                $('.changeStatusBtn').click(function (event) {
                    event.stopPropagation();
                    const qnaId = parseInt($(this).data('id')); // 숫자로 변환
                    let currentStatus = $(this).data('status');
                    let newStatus = currentStatus === 'RECEIVED' ? 'COMPLETED' : 'RECEIVED'; // 상태 변경 로직 수정
                    const btn = $(this);

                    fetch(`/tot/admin/qna/${boardId}/changeStatus`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            qnaId: qnaId,
                            qna_002: newStatus
                        })
                    })
                        .then(response => {
                            if (!response.ok) {
                                throw new Error(`HTTP error! status: ${response.status}`);
                            }
                            return response.json();
                        })
                        .then(data => {
                            if (data.success) {
                                alert('처리상태가 변경되었습니다.');
                                btn.data('status', newStatus);
                                btn.text(newStatus === 'RECEIVED' ? '접수' : '완료');
                                btn.toggleClass('pending');

                                const statusCell = btn.closest('tr').find('td').eq(4);  // 해당 행의 상태 텍스트 셀 찾기
                                statusCell.text(newStatus === 'RECEIVED' ? '접수' : '완료');  // 상태 텍스트 변경 
                                loadQnaList();
                            } else {
                                alert('처리상태 변경에 실패했습니다.');
                            }
                        })
                        .catch(error => console.error('Error:', error));
                });

            })
            .catch(error => console.error('Error:', error));
    }

    $('.prevBtn').click(function () {
        const currentPage = parseInt($('.currentBtn').text(), 10);
        if (currentPage > 1) {
            loadQnaList($('.qnaCategory').val(), currentPage - 1, '', currentStatus);
        }
    });

    $('.nextBtn').click(function () {
        const currentPage = parseInt($('.currentBtn').text(), 10);
        loadQnaList($('.qnaCategory').val(), currentPage + 1, '', currentStatus);
    });

    loadQnaList();
});

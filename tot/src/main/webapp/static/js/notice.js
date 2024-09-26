$(document).ready(function() {

    // 검색 기능 AJAX 처리
    $('#searchForm').submit(function(e) {
        e.preventDefault();
        var formData = $(this).serializeArray();  // serialize를 객체 배열로 변환
        var searchParams = {};  // 빈 객체 선언

        // formData 배열을 객체로 변환
        formData.forEach(function(item) {
            searchParams[item.name] = item.value;
        });

        loadNoticeList(searchParams);  // 객체로 넘김
    });

    // 공지사항 목록 로드 함수 
    function loadNoticeList(searchParams) {
    $.ajax({
        url: '/tot/api/notices/search',  
        type: 'GET',
        data: searchParams,  
        success: function(data) {
            console.log('공지사항 목록 로드 성공:', data);
            
            // 전체 새로고침을 없애고, 검색 결과를 페이지 일부에 반영
            $('#qnaTableBody').empty();  // 기존 테이블 내용 삭제
            $('#paginationList').empty(); // 추가
            
             // 공지사항 목록이 있을 경우 리스트를 추가
            if (data && data.length > 0) {
                data.forEach(function(notice) {
                    $('#qnaTableBody').append(
                        `<tr>
                            <td>${notice.noid}</td>
                            <td><a href="/tot/api/notices/detail/${notice.noid}">${notice.notitle}</a></td>
                            <td>${new Date(notice.noregdate).toLocaleString()}</td>
                        </tr>`
                    );
                });
                
				// 검색 결과에 맞게 페이징 버튼 추가
				if (data.totalPages) {  // 서버에서 검색된 데이터에 대한 페이지 정보 제공
				    for (let i = data.startBlockPage; i <= data.endBlockPage; i++) {
				        $('#paginationList').append(
				            `<li class="paginationItem">
				                <a href="#" class="paginationLink" data-page="${i}">${i}</a>
				            </li>`
				        );
				    }
               
				    // 페이징 버튼 클릭 이벤트 (검색 조건 포함)
				    $('.paginationLink').click(function(e) {
				        e.preventDefault();
				        var page = $(this).data('page');
				        searchParams['page'] = page;  // 페이지 값 업데이트
				        loadNoticeList(searchParams);  // 해당 페이지로 재검색
				    });
				} else {
				    $('#paginationList').append('<li>페이지 정보가 없습니다.</li>');
				}
				}
        },
        error: function(xhr) {
            console.log('공지사항 목록 로드 오류:', xhr.responseText);
            alert('공지사항 목록을 불러오는 데 실패했습니다.');
        }
    });
}



    // 공지사항 상세보기로 이동
    window.viewNotice = function(noid) {
        location.href = `/tot/jsp/noticeDetail.jsp?noid=${noid}`;  
    }        

    // 공지사항 삭제
    window.deleteNotice = function(noid) {
        if (confirm('정말 삭제하시겠습니까?')) {
            $.ajax({
                url: `/tot/api/notices/${noid}`,
                type: 'DELETE',
                success: function() {
                    alert('삭제되었습니다.');
                    console.log('삭제 성공');  
                    location.reload();  // 삭제 후 목록 새로고침 (전체 새로고침)
                },
                error: function(xhr) {
                    console.log('삭제 오류:', xhr.responseText); 
                    alert('삭제에 실패했습니다.');
                }
            });
        }
    }

    // 공지사항 수정으로 이동
    window.updateNotice = function(noid) {
    location.href = `/tot/api/notices/update/${noid}`;
    }
});

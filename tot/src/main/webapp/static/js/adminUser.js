$(document).ready(function() {
  // 각 상태 버튼에 대한 색상 설정
  $('.statusButton').each(function() {
    const status = $(this).closest('tr').find('.statusValue').text().trim();
    console.log(status);
    const memId = $(this).closest('tr').find('td:nth-child(2)').text().trim(); // 회원 ID 가져오기

    if (status == '정상회원') {
      $(this).css('background-color', 'green').css('color', 'white');
    } else if (status == '제재회원') {
      $(this).css('background-color', 'yellow').css('color', 'black');
    } else if (status == '정지회원') {
      $(this).css('background-color', 'red').css('color', 'white');
    }

    // 제재확인 버튼 클릭 이벤트
    $(this).click(function() {
      window.location.href = '/tot/admin/banUser?id=' + memId; // 이메일 없이 아이디만 넘김
    });
  });

  // 검색 버튼 클릭 이벤트
  $('#searchButton').click(function() {
    const searchQuery = $('#searchInput').val();
    window.location.href = '?search=' + encodeURIComponent(searchQuery) + '&page=1';
  });
});

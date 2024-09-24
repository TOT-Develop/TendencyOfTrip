 // 제재 버튼 클릭 이벤트 처리
    $('#banButton').click(function() {
      const memId = $('#memId').text();
      const memEmail = $('#memEmail').text();
      const banReason = $('#banReason').val();
      let reasonToSend = banReason;

      if (banReason === '기타') {
        reasonToSend = $('#otherReason').val();
      }

      if (reasonToSend) {
        $.ajax({
          url: '/tot/admin/banUserProc',
          method: 'POST',
          data: { id: memId, email: memEmail, reason: reasonToSend },
          success: function(response) {
            alert('제재가 완료되었습니다.');
            window.location.href = '/tot/admin/adminUser';
          },
          error: function() {
            alert('제재 중 오류가 발생했습니다.');
          }
        });
      } else {
        alert('제재 사유를 선택해야 합니다.');
      }
    });
    
    // 제재 해제 버튼 클릭 이벤트 처리
    $('#liftButton').click(function() {
      const memId = $('#memId').text();
      const liftReason = $('#liftReason').val();
      let reasonToSend = liftReason;

      if (liftReason === '기타') {
        reasonToSend = $('#otherLiftReason').val();
      }

      if (reasonToSend) {
        $.ajax({
          url: '/tot/admin/liftUserProc',
          method: 'POST',
          data: { id: memId, reason: reasonToSend },
          success: function(response) {
            alert('제재 해제가 완료되었습니다.');
            window.location.href = '/tot/admin/adminUser';
          },
          error: function() {
            alert('제재 해제 중 오류가 발생했습니다.');
          }
        });
      } else {
        alert('제재 해제 사유를 선택해야 합니다.');
      }
      
      // 제재 사유 선택 시 "기타"를 선택했을 때 입력 필드 보여주기
$('#banReason').change(function() {
    if ($(this).val() === '기타') {
        $('#otherReasonContainer').show();
    } else {
        $('#otherReasonContainer').hide();
    }
});

// 제재 해제 사유 선택 시 "기타"를 선택했을 때 입력 필드 보여주기
$('#liftReason').change(function() {
    if ($(this).val() === '기타') {
        $('#otherLiftReasonContainer').show();
    } else {
        $('#otherLiftReasonContainer').hide();
    }
});
      
    });
window.onload = function() {
    $(".toListBtn").click(function() {
        window.location.href = "adminQna.jsp";
    });

    const qnaid = sessionStorage.getItem('qnaid');
    fetch(`/tot/admin/qna/QnaDetail?QNAID=${qnaid}`)
        .then(response => response.json())
        .then(data => {
            $('.row2').html(`
                <div class="qnaTitle">
                    <b>제목</b>
                    <p>${data.qnatitle}</p>
                </div>
                <div class="qnaTitleWriter">
                    <b>작성자</b>
                    <p>${data.memNick}</p>
                </div>
                <div class="qnaTitleContent">
                    <b>내용</b>
                    <p>${data.qnatext}</p>
                </div>
            `);

            // 이메일 보내기 버튼 추가
            let emailButton = $(`<button id="sendEmailButton">이메일 보내기</button>`);
            $('.row2').append(emailButton);

            // 이메일 보내기 버튼 클릭 이벤트 추가
            $('#sendEmailButton').click(function() {
                // 작성자 이메일을 가져오는 fetch 호출 (여기서는 MEMID를 이용해서)
                fetch(`/tot/admin/qna/getEmail?MEMID=${data.memId}`)
                    .then(response => response.json())
                    .then(emailData => {
						console.log(memId);
						console.log("Fetched email data:", emailData);
                        // 이메일 전송 함수 호출
                        sendEmailToUser(emailData.email, data.qnatitle, data.qnatext);
                    })
                    .catch(error => console.error('Error fetching email:', error));
            });

            // 댓글 목록을 가져오는 fetch 호출
            fetch(`/tot/admin/qna/comments?QNAID=${qnaid}`)
                .then(response => response.json())
                .then(comments => {
                    let commentList = $('.commentList');
                    commentList.html('');  // 댓글 리스트 초기화
                    
                    if (comments.length === 0) {
                        commentList.append('<p>댓글이 없습니다.</p>');
                    } else {
                        comments.forEach(comment => {
                            let commentItem = $(`
                                <div class="commentItem">
                                    <div class="commentText">
                                        <p>${comment.qnactext}</p>
                                    </div>
                                    <div class="commentDate">
                                        <small>${new Date(comment.qnacregdate).toLocaleString()}</small>
                                    </div>
                                </div>
                            `);
                            commentList.append(commentItem);
                        });
                    }
                })
                .catch(error => console.error('Error fetching comments:', error));
        });

    // 이메일 보내기 함수
    function sendEmailToUser(email, title, body) {
        console.log("Sending email to:", email);
        $.ajax({
            url: '/tot/admin/qna/sendQnaEmail',
            method: 'POST',
            data: {
                email: email,
                subject: '문의에 대한 답변입니다: ' + title,  // 이메일 제목
                body: '회원님, 안녕하세요. TendencyOfTrip입니다.\n\n문의하신 글에 답변을 남겼습니다.\n' + body  // 이메일 본문
            },
            success: function(response) {
                console.log('이메일 전송 성공');
                alert('이메일이 성공적으로 전송되었습니다.');
            },
            error: function(xhr, status, error) {
                console.error('이메일 전송 오류: ' + error);
                alert('이메일 전송 중 오류가 발생했습니다.');
            }
        });
    }
};

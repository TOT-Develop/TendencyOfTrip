window.onload = function() {
	$(".toListBtn").click(function(){
		window.location.href="qna.jsp";
	})
	
	
 const qnaid = sessionStorage.getItem('qnaid');
    fetch(`/tot/qna/qnaDetail?QNAID=${qnaid}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok.');
            }
            return response.json();
        })
        .then(data => {
            console.log(data);
            $('.row2').html('');
            let QnaDetail = $(`
                <div class="qnaTitle">
                    <b>제목</b>
                    <p>${data.qnatitle}</p>
                </div>
                <div class="qnaTitleWriter">
                    <b>작성자</b>
                    <p>${data.memid}</p>
                </div>
                <div class="qnaTitleContent">
                    <b>내용</b>
                    <p>${data.qnatext}</p>
                </div>
            `);
            $('.row2').append(QnaDetail);
        })
        .catch(error => console.error('Error:', error));
};

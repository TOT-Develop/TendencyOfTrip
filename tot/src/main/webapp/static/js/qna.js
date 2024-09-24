window.onload = function () {
    const currentPage = 1;
    const currentCategory = 'category';
    const qna = [];
    const qnaPage = 5;
    
    // 글쓰기 페이지로 이동
    $('.toWrite').click(function(){
        window.location.href="qnaRegist.jsp";
		
		//var memid = '<%= sessionScope.memid %>'; 
        //window.location.href = '/insertQnaForm;

		
		fetch('/insertQnaForm', {
			method : 'POST'
		})
		.then(response => {
			if(!response.ok){
				throw new Error('Network response was not ok.');
			}
			return response.json();
		})
		.then(data => {
			console.log(data)
		})
		.catch(error => console.error('Error', error))
    });

    fetch('/tot/qna/api')
    .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }
        return response.json(); // Parse the JSON data from the server
    })
    .then(data => {
        $('#qnaTableBody').html('');

        data.forEach(qna => {
            let QnaLi = $(`
                <tr id="qnaContent" data-id="${qna.qnaid}">
                    <input type="hidden" name="qnaContent1" value="${qna.memid}"/>
                    <td>${qna.qna_001}</td>
                    <td>${qna.qnatitle}</td>
                    <td>${qna.memid}</td>
                    <td>${qna.qnaregdate}</td>
                </tr>
            `);
            $('#qnaTableBody').append(QnaLi); 
        });

        $('#qnaTableBody').on('click', '#qnaContent', function() {
            const qnaid = $(this).data('id');
            sessionStorage.setItem('qnaid', qnaid);
            window.location.href = `qnaDetail.jsp?QNAID=${encodeURIComponent(qnaid)}`;
        });
    })
    .catch(error => console.error('Error:', error)); 

    if (window.location.pathname.endsWith('qnaDetail.jsp')) {
        load();
    } else {
    }
};
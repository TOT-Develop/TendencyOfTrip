window.onload = function () {
    // 글쓰기 취소
    $('.cancelButton').click(function(){
        window.location.href="qna.jsp";
    });


    document.querySelector('.qnaCategory').addEventListener('change', getCateValue);

	
	// 카테고리 가져오기
    function getCateValue() {
        var selectElement = document.querySelector('.qnaCategory');
		var Elementttt = selectElement.value;
		 console.log(Elementttt)
        // Do something with the selected value, e.g., send it to the server


    }
       // var selectElement = document.querySelector('.qnaCategory');
      //  selectElement.addEventListener('change', getSelectedValue);
	
	// 등록하기
	


	// 등록하기
	$('.registButton').on('click', function(){
		const qnaid = sessionStorage.getItem('qnaid');
		const requestData = {
			qnaid:qnaid,
	        memid: 'M01',
	        qna_001: document.querySelector('.qnaCategory').value,
	        qna_002: 'Q05',
	        commentstatus: 'CMT001',
	        qnatitle: document.querySelector('.qnaTitleText').value,
	        qnatext: document.querySelector('.qnaTextarea').value
		}
	console.log(requestData);

	    fetch(`/tot/qna/insertQna`,
		{
			method : 'POST',
		    headers: {
       		'Content-Type': 'application/json'  // 요청 본문이 JSON 형식임을 서버에 알림
    },
    body: JSON.stringify(requestData)
		})
	    .then(response => {
	            if (!response.ok) {
	                throw new Error('Network response was not ok.');
	            }
	            return response.json();
	        })
	        .then(data => {
				if(data.success){
				}else{
					alert("failed @@@@@@")
				}
	            console.log(data);
	       
	        })
	        .catch(error => console.error('Error:', error));
			
			window.location.href='qna.jsp';
	})
	
	
	

	
	




};
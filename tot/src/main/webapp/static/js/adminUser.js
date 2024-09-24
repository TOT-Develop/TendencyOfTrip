$(document).ready(function() {

	
	const btn = document.querySelectorAll('input[type="button"]');
	
	btn.forEach(button => {
		button.addEventListener('click', function(){
			const userList = this.closest('li');
			const listP = userList.children;
			const thirdP = listP[2];
			const value = this.value;
			
            const confirmed = confirm(`상태를 "${value}"로 변경하시겠습니까?`);
			
			if(confirmed){
			thirdP.textContent = value;
				
			}
			console.log(value)
		})
	})

		
	
	
	
})
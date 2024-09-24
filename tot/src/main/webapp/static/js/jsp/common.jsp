<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script type="text/javascript">
	$(() => {
		const message = "${message}";
		
		if (message) {
			alert(message);
			
			window.history.back();
		}
	});
</script>
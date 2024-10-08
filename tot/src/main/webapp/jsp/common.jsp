<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<script type="text/javascript">
	$(() => {
		const message = "${message}";
		const redirectPage = "${redirectPage}";
		
		if (message) {
			alert(message);
			
			// redirectPage가 존재하면 해당 페이지로 이동
            if (redirectPage) {
                window.location.href = "/tot/" + redirectPage;
            } else {
                window.history.back();
            }
		}
	});
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>admin login</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <link rel="stylesheet" href="../static/css/global.css">
  <link rel="stylesheet" href="../static/css/adminLogin.css">
  <script src="${pageContext.request.contextPath}/static/js/adminLogin.js"></script>
</head>
<body>
  <div id="loginWrapper">
    <div id="loginContainer">
	    <div id=loginContent>
	      <h2>LOGIN</h2>
	      <div id="content">
	        <form action="${pageContext.request.contextPath}/admin/adminLoginProc" method="POST">
				<p>ID<input id="id" name="id" type="text" placeholder="id" /></p>
				<p>PASSWORD<input id="pass" name="pass" type="password" placeholder="password" /></p>
				<input type="submit" value="login" />
			</form>
	    </div>
    </div>
  </div>
</body>
</html>
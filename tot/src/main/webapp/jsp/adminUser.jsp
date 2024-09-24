<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>admin user</title>
  <link rel="stylesheet" href="../static/css/global.css">
  <link rel="stylesheet" href="../static/css/adminUser.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="../static/js/adminUser.js"></script>
</head>
<body>
<%@ include file="adminMenu.jsp" %>
  <div id="userWrapper">
    <div id="userContainer">
	  <div id="userContent">
	        <ul id="userListUl">
	          <li id="userListLiHeader">
	            <p>USER</p>
	            <p>ID</p>
	            <p>VALUE</p>
	          </li>
	
	          <li id="userListLi">
	            <p><img src="../static/image/send.png"></p>
	            <p>user1</p>
	            <p></p>
	            <p class="btn">
	              <input type="button" value="정상"/>
	              <input type="button" value="제재"/>
	              <input type="button" value="탈퇴"/>
	            </p>
	          </li>
              <li id="userListLi">
	            <p><img src="../static/image/send.png"></p>
	            <p>user1</p>
	            <p></p>
	            <p>
	              <input type="button" value="정상"/>
	              <input type="button" value="제재"/>
	              <input type="button" value="탈퇴"/>
	            </p>
	          </li>
	
	        </ul>
    	</div>
    </div>
  </div>

</body>
</html>
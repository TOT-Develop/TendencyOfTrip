<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>tot admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/adminMenu.css" />
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
  <script src="${pageContext.request.contextPath}/static/js/adminMenu.js"></script>
</head>
<body>
  <aside id="aside">
    <div id="wrapper">
      <div id="container1">
        <h1><a href="#"><img src="${pageContext.request.contextPath}/static/image/header_logo.png"></a></h1>
      </div>
      <div id="container2">
        <ul id="list">
          <li id="logOut">LOGOUT</li>
          <li id="user">USER</li>
          <li id="tripreview">TRIPREVIEW</li>
          <li id="reviewcomment">REVIEWCOMMENT</li>
          <li id="report">REPORT</li>
        </ul>
      </div>
    </div>
  </aside>
</body>
</html>
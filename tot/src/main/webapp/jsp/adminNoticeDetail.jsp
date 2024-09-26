<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="initial-scale=1, width=device-width" />
    <title>관리자용 공지사항 상세보기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/noticeDetail.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/noticeDetail.js"></script>
</head>
<body>
    <section class="container">   
        <h2 class="row1Text">NOTICE</h2> 
        <div class="fromHeaderParent">
            <div class="fromHeader">
                <div class="row1">
                    <div class="row1Text1">공지사항</div>
                </div>
                <div class="instruction"></div>
            </div>

            <div class="row2">
                <div class="noticeTitle">
                    <b>제목</b>
                    <p>${notice.notitle}</p> <!-- 공지사항 제목을 표시할 부분 -->
                </div>
                <div class="noticeTitleContent">
                    <b>내용</b>
                    <p>${notice.notext}</p> <!-- 공지사항 내용을 표시할 부분 -->
                </div>
            </div>
        </div>  
        <div class="buttons">
            <button type="button" class="toListBtn" onclick="location.href='${pageContext.request.contextPath}/admin/api/notices'">목록으로</button>
            
        </div>
    </section>
</body>
</html>

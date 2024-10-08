<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="initial-scale=1, width=device-width" />
    <title>QnA</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/qnaDetail.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/qna.js"></script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
    <section class="container">   
        <h2 class="row1Text">Q & A</h2> 
        <div class="fromHeaderParent">
            <div class="fromHeader">
                <div class="row1">
                    <div class="row1Text1">detail</div>
                </div>
                <div class="instruction"></div>
            </div>

            <div class="row2">
            	<div class="qnaTitle">
                    <b>제목</b>
                    <p>${qnaDetail.qnaTitle}</p>
                </div>
                <div class="qnaTitleWriter">
                    <b>작성자</b>
                    <p>${qnaDetail.memNick}</p>
                </div>
                <div class="qnaTitleContent">
                    <b>내용</b>
                    <p>${qnaDetail.qnaText}</p>
                </div>
            </div>
            
             <!-- 댓글 목록을 표시하는 영역 -->
            <div class="row3">
               <h3>댓글 목록</h3>
            <div class="commentList">
                <c:choose>
                    <c:when test="${empty comments}">
                        <p>댓글이 없습니다.</p>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="comment" items="${comments}">
                            <div class="commentItem">
                                <div class="commentText">
                                    <p>${comment.qnacText}</p>
                                </div>
                                <div class="commentDate">
                                    <small><fmt:formatDate value="${comment.qnacRegdate}" pattern="yyyy-MM-dd HH:mm:ss"/></small>
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        </div>
        <div class="buttons">
            <button type="button" class="toListBtn" >목록으로</button>
        </div>
    </section>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
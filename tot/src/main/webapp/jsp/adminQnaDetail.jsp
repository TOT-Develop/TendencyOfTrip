<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="initial-scale=1, width=device-width" />
    <title>관리자용 QnA Detail</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/qnaDetail.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/adminQnaDetail.js"></script>
    <script type="text/javascript">
        // qnaDetail을 JSON 문자열로 변환 후 JavaScript 객체로 파싱
        var qnaDetail = {
            memId: '${qnaDetail.memId}',
            qnaId: '${qnaDetail.qnaId}',
            qnaTitle: '${fn:escapeXml(qnaDetail.qnaTitle)}',
            memNick: '${fn:escapeXml(qnaDetail.memNick)}',
            qnaText: '${fn:escapeXml(qnaDetail.qnaText)}'
        };

        // comments를 JSON 형태로 변환 후 JavaScript 배열로 설정
        var comments = [
            <c:forEach var="comment" items="${comments}">
                {
                    qnacId: '${comment.qnacId}',
                    qnacText: '${fn:escapeXml(comment.qnacText)}',
                    qnacRegdate: '${comment.qnacRegdate}'
                },
            </c:forEach>
        ];
        // JSON 데이터를 확인하기 위한 로그 출력
        console.log("QnA Detail:", qnaDetail);
        console.log("Comments:", comments);
    </script>

</head>

<body>
    <section class="container">
        <h2 class="row1Text">관리자용 Q & A 상세보기</h2>

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
                    <p id='qnaTitleValue'>${qnaDetail.qnaTitle}</p>
                </div>
                <div class="qnaTitleWriter">
                    <b>작성자</b>
                    <p id='memNickValue'>${qnaDetail.memNick}</p>
                </div>
                <div class="qnaTitleContent">
                    <b>내용</b>
                    <p id='qnaTextValue'>${qnaDetail.qnaText}</p>
                </div>
            </div>

            <div class="row3">
                <h3>댓글 목록</h3>
                <div class="commentList">
                    <c:choose>
                        <c:when test="${empty comments}">
                            <p>댓글이 없습니다.</p>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="comment" items="${comments}">
                                <!-- 각 댓글을 한 줄로 표현하기 위한 commentItem div -->
                                <div class="commentItem">
                                    <!-- 댓글 내용 -->
                                    <div class="commentText">
                                        <span>${comment.qnacText}</span>
                                    </div>

                                    <!-- 댓글 작성 시간 (yyyy-MM-dd HH:mm 형식) -->
                                    <div class="commentDate">
                                        <fmt:formatDate value="${comment.qnacRegdate}" pattern="yyyy-MM-dd HH:mm" />
                                    </div>

                                    <!-- 이메일 보내기 버튼 -->
                                    <div class="commentActions">
                                        <button type="button" class="sendCommentEmailButton"
                                            data-comment="${comment.qnacText}">이메일 보내기</button>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="addComment">
                <h3>댓글 작성</h3>
                <form id="commentForm">
                    <div class="commentForm">
                        <input type="hidden" id="qnaIdValue" name="qnaId" value="${qnaDetail.qnaId}" />
                        <input type="text" id="commentText" name="commentText" placeholder="댓글을 입력하세요"
                            class="commentTextInput" />
                        <button type="submit" class="sendCommentButton">댓글 달기</button>
                    </div>
                </form>
            </div>

            <div class="buttons">
                <button type="button" class="toListBtn"
                    onclick="window.location.href='${pageContext.request.contextPath}/admin/qna/1/1'">목록으로</button>
            </div>
        </div>
    </section>
</body>
</html>
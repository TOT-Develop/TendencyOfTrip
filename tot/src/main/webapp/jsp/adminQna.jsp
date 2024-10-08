<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="tot.domain.QnaDTO"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="initial-scale=1, width=device-width" />
    <title>관리자용 QnA 게시판</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/adminQna.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/adminQna.js"></script>
    <%@ include file="adminMenu.jsp" %>
</head>
<body>
    <% session.getAttribute("member"); %>
    <input type="hidden" name="memid" value="<%=session.getAttribute("memid")%>"/>
    <div class="wrapper">
        <div class="container">
            <div class="row1">
                <h2 class="row1Text">Q & A</h2>
            </div>
            <div class="qnaIntroDiv">
                <h2>[관리자 QnA 설명]</h2>
                <hr />
                <div>이 페이지에서는 사용자가 등록한 질의를 확인하고 관리자가 확인 및 답변을 등록함으로써 처리할 수 있습니다.</div>
                <div>클릭시 상세보기 가능하며 검색 기능을 통해 원하는 질의를 찾을 수 있습니다.</div>
                <div>관리자는 질의에 대한 댓글을 달 수 있고 관련 내용을 질의 사용자에게 메일을 보낼 수 있습니다.</div>
                <div>질의에 대한 처리가 완료되면 처리상태 변경을 통해 처리 전, 후의 질의들을 관리할 수 있습니다.</div>
            </div>
            <div class="row2">
                <form id="searchForm" action="${pageContext.request.contextPath}/qna" method="get">
                    <div class="qnaCategories">
                        <select name="category" class="qnaCategory">
                            <option value="ALL">전체</option>
                            <option value="Q01">계정관리</option>
                            <option value="Q02">기술지원</option>
                            <option value="Q03">불만요청</option>
                            <option value="Q04">기타요청</option>
                        </select>

                        <input type="text" placeholder="Search" class="searchBox" />
                        <input type="submit" value="검색" class="searchBtn" />
                        <input type="button" id="allFilterBtn" value="접수전체" />
                        <input type="button" id="pendingFilterBtn" value="접수대기" />
                        <input type="button" id="completedFilterBtn" value="접수완료" />
                    </div>
                </form>

                <div class="questionList">
                    <div class="tableContainer">
                        <table class="table">
                            <thead id="qnaTableHead">
                                <tr>
                                    <th>카테고리</th>
                                    <th>제목</th>
                                    <th>작성자</th>
                                    <th>작성일</th>
                                    <th>처리상태</th>
                                    <th>처리변경</th>
                                </tr>
                            </thead>
                            <form action="/qna" method="post">
                                <tbody id="qnaTableBody">

                                </tbody>
                            </form>
                        </table>
                    </div>
                </div>
            </div>
            <div class="paging">
                <button class="prevBtn"><img
                        src="${pageContext.request.contextPath}/static/image/arrow-left-circle.png"
                        alt="prevbutton" /></button>
                <button class="currentBtn">1</button>
                <button class="nextBtn"><img
                        src="${pageContext.request.contextPath}/static/image/arrow-right-circle.png"
                        alt="prevbutton" /></button>
            </div>
        </div>
    </div>
</body>
</html>
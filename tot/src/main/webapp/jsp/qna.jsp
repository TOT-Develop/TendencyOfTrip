<%@page import="java.util.List"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="tot.domain.Qna"%>
<%
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="initial-scale=1, width=device-width" />
    <title>Trick or Trip</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/qna.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/qna.js"></script>
    
</head>
<body>
<%
	session.setAttribute("memid", "M01"); 
%>
<input type="hidden" name="memid" value="<%=session.getAttribute("memid")%>"/>
	<jsp:include page="header.jsp"></jsp:include>
    <div class="wrapper">
      <div class="container">
          <div class="row1">
            <h2 class="row1Text">Q & A</h2>
          </div>        
          <div class="row2">
            <div class="qnaCategories">
                <select class="qnaCategory">
                    <option value="" selected <c:if test="${qnacatno==null || qnacatno==''}" ></c:if>>전체</option>
                    <option value="1"  <c:if test="${qnacatno==null || qnacatno=='1'}" ></c:if>>계정관리</option>
                    <option value="2" <c:if test="${qnacatno==null || qnacatno=='2'}" ></c:if>>기술지원</option>
                    <option value="3" <c:if test="${qnacatno==null || qnacatno=='3'}" ></c:if>>불만요청</option>
                    <option value="4" <c:if test="${qnacatno==null || qnacatno=='4'}" ></c:if>>기타요청</option>
                </select>
                <input type="text" name="searchValue" placeholder="Search" class="searchBox" />
                <input type="submit" value="검색" class="searchBtn"/>
                <input type="button" value="+ 글쓰기" class="toWrite"/>
            </div>

            <div class="questionList">
                <div class="tableContainer">
                    <table class="table">
                        <thead id="qnaTableHead">
                            <tr>
                                <th>카테고리</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>작성일</th>
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
            <button class="prevBtn"><img src="../static/image/arrow-left-circle.png" alt="prevbutton"/></button>
            <button class="currentBtn">1</button>
            <button class="nextBtn"><img src="../static/image/arrow-right-circle.png" alt="prevbutton"/></button>
          </div>          
      </div>
    </div>
   	<jsp:include page="footer.jsp"></jsp:include>
  </body>
</html>

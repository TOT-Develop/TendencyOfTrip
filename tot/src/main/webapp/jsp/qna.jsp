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
	session.getAttribute("member"); 
%>
<p>안녕하세요, ${member.memNick}님!</p>

<input type="hidden" name="memid" value="<%=session.getAttribute("memid")%>"/>
    <div class="wrapper">
      <div class="container">
          <div class="row1">
            <h2 class="row1Text">Q & A</h2>
          </div>        
          <div class="row2">
          	<form id="searchForm" action="${pageContext.request.contextPath}/qna" method="get">	
	            <div class="qnaCategories">
					<select name="searchType" class="qnaCategory">
					    <option value="ALL" ${pageReqDTO.categoryType == 'ALL' ? 'selected' : ''}>전체</option>
					    <option value="Q01" ${pageReqDTO.categoryType == 'Q01' ? 'selected' : ''}>계정관리</option>
					    <option value="Q02" ${pageReqDTO.categoryType == 'Q02' ? 'selected' : ''}>기술지원</option>
					    <option value="Q03" ${pageReqDTO.categoryType == 'Q03' ? 'selected' : ''}>불만요청</option>
					    <option value="Q04" ${pageReqDTO.categoryType == 'Q04' ? 'selected' : ''}>기타요청</option>
					</select>
					
	                <input type="text"  placeholder="Search" class="searchBox" />
	                <input type="submit" value="검색" class="searchBtn"/>
	                <input type="button" value="+ 글쓰기" class="toWrite"/>
	                <input type="button" value="내 글 보기" id="myQnaBtn"/>
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
  </body>
</html>
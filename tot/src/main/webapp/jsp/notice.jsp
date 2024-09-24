<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="initial-scale=1, width=device-width" />
    <title>Trick or Trip</title>
    <link rel="stylesheet" href="../static/css/global.css" />
    <link rel="stylesheet" href="../static/css/notice.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="../static/js/notice.js"></script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
    <div class="wrapper">
      <div class="container">
          <div class="row1">
            <h2 class="row1Text">NOTICE</h2>
          </div>        
          <div class="row2">
            <div class="qnaCategories">
                <select class="searchKeyword">
                    <option value="" selected>제목</option>
                    <option value="">내용</option>
                </select>
                <input type="text" name="searchValue" placeholder="Search" />
                <input type="submit" value="검색" />
            </div>

            <div class="questionList">
                <div class="tableContainer">
                    <table class="table">
                        <thead id="noticeTableHead">
                            <tr>
                                <th>작성자</th>
                                <th>제목</th>
                                <th>작성일</th>
                            </tr>
                        </thead>
                        <tbody id="noticeTableBody">
                            <tr>
                              <td>계정관리</td>
                              <td>악플 선처 없음</td>
                              <td>2024.07.52</td>
                            </tr>
                            <tr>
                              <td>계정관리</td>
                              <td>공유기능 업데이트</td>
                              <td>2024.07.52</td>
                            </tr>
                        </tbody>
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
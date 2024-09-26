<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="initial-scale=1, width=device-width" />
    <title>Trick or Trip</title>
    <link rel="stylesheet" href="../static/css/global.css" />
    <link rel="stylesheet" href="../static/css/qnaDetail.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="../static/js/adminQnaDetail.js"></script>
</head>
<body>
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
                <!-- 여기에 JavaScript로 QnA 상세 정보와 댓글을 동적으로 표시 -->
            </div>

            <!-- 댓글 목록을 표시하는 영역을 추가 -->
            <div class="commentList">
                <!-- 댓글 목록은 JavaScript에서 동적으로 채워짐 -->
            </div>

            <!-- 댓글 작성 버튼을 여기에 동적으로 추가 -->
        </div>

        <div class="buttons">
            <button type="button" class="toListBtn">목록으로</button>
        </div>
    </section>
</body>
</html>

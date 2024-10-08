<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="initial-scale=1, width=device-width" />
    <title>공지사항 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/adminNoticeRegist.css" />
    
    <!-- CKEditor CDN 추가 -->
    <script src="https://cdn.ckeditor.com/ckeditor5/36.0.1/classic/ckeditor.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/adminNoticeUpdate.js"></script> <!-- JS 파일 포함 -->
</head>
<body>
    <section class="container">
        <h2 class="row1Text">NOTICE 수정</h2>
        <div class="fromHeaderParent">
            <div class="fromHeader">
                <div class="row1">
                    <div class="row1Text1">글 수정</div>
                </div>
                <div class="instruction">
                    <div class="row1Text2">아래 내용을 수정해 주세요.</div>
                </div>
            </div>

            <input type="hidden" id="noId" value="${notice.noId}">

            <div class="row2">
                <div class="noticeTitleHead"> 제목</div>
                <div class="noticeTitle">
                    <input class="noticeTitleText" placeholder="제목을 입력하세요." type="text" id="noTitle" required />
                </div>
            </div>
        </div>

        <div class="row3">
            <div class="noticeContent">
                <textarea id="noticeContent" class="noticeTextarea"></textarea>
            </div>
        </div>

        <div class="actionsArea">
            <div class="toList">
                <button class="toListButton" id="backToListBtn">목록으로</button>
            </div>
            <div class="rowBottom">
                <div class="rowBottomText">
                    <div class="bottomText">건전한 게시글 문화를 응원합니다.</div>
                </div>
                <div class="subMissionButtons">
                    <button id="submitBtn" class="registButton">글 수정하기</button>
                    <button type="button" class="cancelButton" id="cancelBtn">x 취소하기</button>
                </div>
            </div>
        </div>
    </section>
    <!-- CKEditor 초기화 -->
    <script>
        let editor;
        ClassicEditor
            .create(document.querySelector('#noticeContent'), {
                toolbar: {
                    shouldNotGroupWhenFull: true
                }
            })
            .then(newEditor => {
                editor = newEditor;
                editor.editing.view.change(writer => {
                    writer.setStyle('height', '300px', editor.editing.view.document.getRoot());  // CKEditor 높이를 300px로 설정
                });
            })
            .catch(error => {
                console.error(error);
            });
    </script>
</body>
</html>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="initial-scale=1, width=device-width" />
	<title>공지사항 등록</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/adminNoticeRegist.css" />
	<!-- CKEditor CDN 추가 -->
	<script src="https://cdn.ckeditor.com/ckeditor5/36.0.1/classic/ckeditor.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<!-- noticeRegist.js 파일 포함 -->
	<script src="${pageContext.request.contextPath}/static/js/adminNoticeRegist.js"></script>
</head>
<body>
	<section class="container">
		<h2 class="row1Text">NOTICE</h2>
		<div class="fromHeaderParent">
			<div class="fromHeader">
				<div class="row1">
					<div class="row1Text1">글 작성</div>
				</div>
				<div class="instruction">
					<div class="row1Text2">아래 양식을 확인하고 입력해주세요.</div>
				</div>
			</div>

			<div class="row2">
				<div class="noticeTitleHead"> 제목</div>
				<div class="noticeTitle">
					<input class="noticeTitleText" placeholder="공지사항 제목을 작성해주세요." type="text" id="notitle" required />
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
				<div class="subMissionButtons">
					<button id="submitBtn" class="registButton">+ 글 등록하기</button>
					<button class="cancelButton" id="cancelBtn">x 취소하기</button>
				</div>
			</div>
		</div>
	</section>

	<!-- CKEditor 적용 스크립트 -->
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
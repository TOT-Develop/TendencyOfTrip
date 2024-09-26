<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="tot.domain.Member"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="initial-scale=1, width=device-width" />
    <title>Trick or Trip</title>
    <link rel="stylesheet" href="../static/css/global.css" />
    <link rel="stylesheet" href="../static/css/qnaRegist.css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="../static/js/qnaRegist.js"></script>
</head>
<body>    
<%
    // 세션에서 member 객체 가져오기
    Member member = (Member) session.getAttribute("member");
    String memId = member != null ? member.getMemId() : "";
    String memNick = member != null ? member.getMemNick() : "";
%>

<script>
    // JavaScript에서 사용할 변수로 세션 값 할당
    const memId = '<%= member.getMemId() %>';
    const memNick = '<%= member.getMemNick() %>';
</script>

<p>안녕하세요, ${member.memNick}님!</p>
<p>안녕하세요, ${member.memId}님!</p>

	<input type="hidden" name="memid"/>
    <section class="container">   
        <h2 class="row1Text">Q & A</h2> 
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
                <div class="qnaTitleHead"> 제목</div>
                <div class="qnaCategories">
                    <select class="qnaCategory" name="QNA_001">
	                    <option value="" selected>전체</option>
	                    <option value="Q01" >계정관리</option>
	                    <option value="Q02" >기술지원</option>
	                    <option value="Q03" >불만요청</option>
	                    <option value="Q04" >기타요청</option>
                    </select>
                </div>
                <div class="qnaTitle">
                    <input class="qnaTitleText" placeholder="TOT에 문의합니다." type="text" name="qnatitle" required />
                </div>
            </div>
        </div>       

        <div class="row3">
            <div class="qnaContent">
                <textarea class="qnaTextarea" required></textarea>
            </div>
        </div>

        <div class="privacyonsent">
            <div class="row4">
                <div class="row4Text1">개인정보 수집 및<br />이용 동의</div>
            </div>
            <div class="consentOptions">
                <div class="row4Text2">개인정보 수집 및 이용에 동의하십니까?</div>
                <label>
                    <input type="radio" name="consent" value="agree" required> 동의함 [필수]
                </label>
            </div>
        </div>

        <div class="actionsArea">
            <div class="rowBottom">
                <div class="rowBottomText">
                    <div class="bottomText">건전한 게시글 문화를 응원합니다.</div>
                </div>
                <div class="subMissionButtons">
                    <button type="submit" class="registButton" >+ 글 등록하기</button>
                    <button type="button" class="cancelButton" > x 취소하기 </button>
                    </button>
                </div>
            </div>
        </div>
    </section>
</body>
</html>
$(document).ready(function () {
    function closeModal() {
        $("#testModal").css("display", "none");
    }

    // TEST START 버튼 클릭 시 함수 호출
    $("#testStartBtn").on("click", checkTestStatus);

    // 모달 내 버튼 동작 정의
    $("#retestBtn").on("click", function () {
        window.location.href = "/tot/tendency";
    });

    $("#courseBtn").on("click", function () {
        sessionStorage.setItem('returnUrl', window.location.href);
        window.location.href = "/tot/recommendCourseInput";
    });


    // 닫기 버튼
    $(".close").on("click", closeModal);
});

const checkTestStatus = () => {
    // JSP에서 받아온 member_tt 값을 비교
    if (memberTT == "TT02" || memberTT == '') {
        // member_tt가 "TT02"일 경우 바로 테스트 페이지로 이동
        window.location.href = "/tot/tendency";
    } else {
        // TT02가 아닐 경우 모달을 띄움
        document.getElementById("testModal").style.display = "block";
    }
}

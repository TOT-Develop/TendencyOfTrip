<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin User</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/global.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/adminUser.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/adminUser.js"></script>
</head>
<body>
<%@ include file="adminMenu.jsp" %>
<div id="userWrapper">
    <div id="userContainer">
        <h1>회원 조회</h1>
        <form action="adminUser" method="get" id="searchForm">
            <input type="text" name="search" value="${search}" placeholder="닉네임 입력" class="searchInput"/>
            <select name="status" class="statusSelect">
                <option value="">전체</option>
                <option value="M01" ${status == 'M01' ? 'selected' : ''}>정상회원</option>
                <option value="M02" ${status == 'M02' ? 'selected' : ''}>제재회원</option>
                <option value="M03" ${status == 'M03' ? 'selected' : ''}>탈퇴회원</option>
            </select>
            <input type="submit" value="검색" class="searchButton"/>
        </form>

        <table id="userTable">
            <thead>
                <tr>
                    <th>닉네임</th>
                    <th>ID</th>
                    <th>EMAIL</th>
                    <th>STATUS</th>
                    <th>MBTI</th>
                    <th>성향</th>
                    <th>가입일</th>
                    <th>수정일</th>
                    <th>BAN START</th>
                    <th>BAN END</th>
                    <th>ACTION</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="member" items="${members}">
                    <tr>
                        <td>${member.memNick}</td>
                        <td>${member.memId}</td>
                        <td>${member.memEmail}</td>
                        <td class="statusValue">${member.member_status}</td>
                        <td>${member.member_mbti}</td>
                        <td>${member.member_tt}</td>
                        <td>${member.memRegDate}</td>
                        <td>${member.memUpdate}</td>
                        <td>${member.memberBanStart}</td>
                        <td>${member.memberBanEnd}</td>
                        <td>
                            <input type="button" class="statusButton" value="제재확인"/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <c:if test="${pageResDTO.totalPageCount > 1}">
            <div id="pagination">
                <c:if test="${pageResDTO.isPrev}">
                    <a href="?page=${pageResDTO.startBlockPage - 1}&search=${not empty param.search ? param.search : ''}&status=${not empty param.status ? param.status : ''}" class="prev">이전</a>
                </c:if>

                <c:forEach begin="${pageResDTO.startBlockPage}" end="${pageResDTO.endBlockPage}" var="i">
                    <a href="?page=${i}&search=${not empty param.search ? param.search : ''}&status=${not empty param.status ? param.status : ''}" class="${pageResDTO.currentPage == i ? 'active' : ''}">${i}</a>
                </c:forEach>

                <c:if test="${pageResDTO.isNext}">
                    <a href="?page=${pageResDTO.endBlockPage + 1}&search=${not empty param.search ? param.search : ''}&status=${not empty param.status ? param.status : ''}" class="next">다음</a>
                </c:if>
            </div>
        </c:if>

    </div>
</div>

</body>
</html>

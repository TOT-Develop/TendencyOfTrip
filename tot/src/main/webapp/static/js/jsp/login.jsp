<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="tot.domain.MemberVO"%>

<%
	MemberVO member = new MemberVO("user123", "홍길동", "hong@naver.com", "M01", "MB02", "TT02", null, null, null, null, null);

	session.setAttribute("member", member);

	out.print("회원 정보 저장완료");
%>
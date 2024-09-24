<%@page import="tot.domain.Qna"%>
<%@page import="tot.service.QnaServiceImpl"%>
<%@page import="tot.dao.QnaDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="Qna" class="tot.domain.Qna" />
<jsp:setProperty name="Qna" property="QNAID" value='<%= Integer.parseInt(request.getParameter("QNAID")) %>' />
<jsp:setProperty name="Qna" property="MEMID" value='<%= Integer.parseInt(request.getParameter("MEMID")) %>' />
<jsp:setProperty name="Qna" property="QNA_001" value='<%= request.getParameter("QNA_001") %>' />
<jsp:setProperty name="Qna" property="QNATITLE"  value='<%=request.getParameter("QNATITLE")%>' />
<jsp:setProperty name="Qna" property="QNATEXT"  value='<%=request.getParameter("QNATEXT")%>' />

<%
	Qna qna = new Qna();
	QnaServiceImpl bs = new QnaServiceImpl();
	bs.insertQna(qna);
	response.sendRedirect("qnaRegist.jsp");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="./layouts/header.jsp"%>

<%
  String userID = request.getParameter("userID"); // URL 파라미터에서 userID 받기
%>
<h1>환영합니다, <%= userID != null ? userID : "사용자" %>님!</h1>
<br/>
<a href="login"> 로그인 페이지로 돌아가기</a>
<br/>

<%@ include file="./layouts/footer.jsp"%>
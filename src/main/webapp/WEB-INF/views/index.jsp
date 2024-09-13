<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String userID = request.getParameter("userID"); // URL 파라미터에서 userID 받기
%>
<html>
<head>
  <title>Index Page</title>
</head>
<body>
<h1>환영합니다, <%= userID != null ? userID : "사용자" %>님!</h1>
<br/>
<a href="login">로그인 페이지로</a>
<br/>
<a href="assetPage">자산관리 페이지로</a>
<br/>
<a href="newsPage">뉴스 페이지로</a>
<br/>
<a href="policyPage">정책 페이지로</a>
<br/>
<a href="mypage">마이페이지로</a>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String userID = request.getParameter("userID"); // URL 파라미터에서 userID 받기
%>
<html>
<head>
    <title>마이페이지</title>
</head>
<body>
<h1> <%= userID != null ? userID : "사용자" %>님의 마이페이지입니다</h1>

<a href="/">메인 페이지로</a>
</body>
</html>

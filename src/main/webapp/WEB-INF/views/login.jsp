<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그인 페이지</title>
</head>
<body>
<h1>로그인</h1>

<%
    // 로그인 실패 시 에러 메시지 표시
    String error = request.getParameter("error");
    if (error != null) {
%>
<p style="color:red;">로그인에 실패했습니다. 사용자 ID와 비밀번호를 확인하세요.</p>
<%
    }
%>

<form action="/loginAf" method="post">
    <label for="userID">사용자 ID:</label>
    <input type="text" id="userID" name="userID" required>
    <br/><br/>
    <button type="submit">로그인</button>
</form>
</body>
</html>
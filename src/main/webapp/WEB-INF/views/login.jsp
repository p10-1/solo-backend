<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그인 페이지</title>
    <script>
        function handleLogin(event) {
            // 기본 폼 제출 방지
            event.preventDefault();

            // 사용자 ID를 로컬 스토리지에 저장
            const userID = document.getElementById('userID').value;
            localStorage.setItem('userID', userID);

            // 폼을 서버로 제출
            document.getElementById('loginForm').submit();
        }
    </script>
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

<form id="loginForm" action="/loginAf" method="post" onsubmit="handleLogin(event)">
    <label for="userID">사용자 ID:</label>
    <input type="text" id="userID" name="userID" required>
    <br/>
    <label for="userPW">비밀번호:</label>
    <input type="password" id="userPW" name="userPW" required>
    <br/><br/>
    <button type="submit">로그인</button>
</form>
</body>
</html>
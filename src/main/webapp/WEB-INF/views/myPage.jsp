<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String userID = (String) request.getAttribute("userID"); // 모델에서 userID 가져오기
%>
<html>
<head>
    <title>마이페이지</title>
    <script>
        window.onload = function() {
            const userID = '<%= userID != null ? userID : "사용자" %>'; // 모델에서 userID 가져오기
            document.getElementById('userName').innerText = userID + "님의 마이페이지입니다";
        }
    </script>
</head>
<body>
<h1 id="userName">사용자님의 마이페이지입니다</h1>

<a href="/">메인 페이지로</a>
</body>
</html>

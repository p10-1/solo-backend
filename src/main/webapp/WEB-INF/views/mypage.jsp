<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mypage</title>
    <script type="text/javascript">
        window.onload = function() {
            var message = '${sessionScope.message}';
            if (message) {
                alert(message);  // 알림 창 띄우기
                console.log("DB 저장 완료");  // 콘솔에 로그 남기기
                // 세션 메시지 삭제
                <%
                    session.removeAttribute("message");
                %>
            }
        }
    </script>
</head>
<body>
<h1>마이페이지 입니다!</h1>

<form action="${pageContext.request.contextPath}/mypage/saveUserData" method="post">
    <label for="cash">Cash:</label>
    <input type="number" id="cash" name="cash" value="0"><br/>

    <label for="stock">Stock:</label>
    <input type="number" id="stock" name="stock" value="0"><br/>

    <label for="property">Property:</label>
    <input type="number" id="property" name="property" value="0"><br/>

    <label for="deposit">Deposit:</label>
    <input type="number" id="deposit" name="deposit" value="0"><br/>

    <label for="consume">Consume:</label>
    <input type="number" id="consume" name="consume" value="0"><br/>

    <input type="submit" value="Submit">
</form>
</body>
</html>

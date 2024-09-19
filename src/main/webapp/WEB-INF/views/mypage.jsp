<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mypage</title>
</head>
<body>
<h1>마이페이지 입니다!</h1>

<form action="${pageContext.request.contextPath}/member/saveUserData" method="post">
    <label for="userID">User ID:</label>
    <input type="text" id="userID" name="userID" required><br/>

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

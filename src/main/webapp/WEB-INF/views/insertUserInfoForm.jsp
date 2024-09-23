<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>First User Info</title>
</head>
<body>
<h1>User Info Form</h1>
<form action="/member/firstUser" method="POST">
    <label for="name">name:</label>
    <input type="text" id="name" name="name" required><br>

    <label for="birthDate">birthDate:</label>
    <input type="date" id="birthDate" name="birthDate" required><br>

    <button type="submit">submit</button>
</form>
</body>
</html>

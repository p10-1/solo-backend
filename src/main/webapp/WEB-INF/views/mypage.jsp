<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mypage</title>
    <script type="text/javascript">
        function update() {
            // 수정 폼으로 변경
            document.getElementById("cash").disabled = false;
            document.getElementById("stock").disabled = false;
            document.getElementById("property").disabled = false;
            document.getElementById("deposit").disabled = false;
            document.getElementById("consume").disabled = false;

            // 버튼 상태 변경
            document.getElementById("editButton").style.display = "none"; // 수정하기 버튼 숨김
            document.getElementById("submitButton").style.display = "none"; // 제출 버튼 숨김
            document.getElementById("updateButton").style.display = "inline"; // 수정 완료 버튼 보임

            // controller에 요청
            document.getElementById("userDataForm").action = "${pageContext.request.contextPath}/mypage/update";
        }

        window.onload = function() {
            var message = '${sessionScope.message}';
            if (message) {
                alert(message);
                console.log("DB 저장 완료");
                <%
                    session.removeAttribute("message");
                %>
            }
        }
    </script>
</head>
<body>
<h1>마이페이지 입니다!</h1><br/>

<h2>개인정보 입력</h2>
<br/>
<form id="userDataForm" action="${pageContext.request.contextPath}/mypage/insert" method="post">
    <label for="consume">Consume 유형 선택 :</label>
    <select id="consume" name="consume">
        <option value="항목1">유형1</option>
        <option value="항목2">유형2</option>
        <option value="항목3">유형3</option>
        <option value="항목4">유형4</option>
    </select>
    <br/><br/>

    <h2>자산 입력</h2>
    <label for="cash">Cash:</label>
    <input type="number" id="cash" name="cash" value="0"><br/>

    <label for="stock">Stock:</label>
    <input type="number" id="stock" name="stock" value="0"><br/>

    <label for="property">Property:</label>
    <input type="number" id="property" name="property" value="0"><br/>

    <label for="deposit">Deposit:</label>
    <input type="number" id="deposit" name="deposit" value="0"><br/>

    <!-- 기본적으로 보여지는 제출 버튼 -->
    <input type="submit" id="submitButton" value="제출">
    <!-- 수정하기 버튼 -->
    <input type="button" id="editButton" value="수정하기" onclick="update()">
    <!-- 수정 완료 버튼, 초기에는 숨김 -->
    <input type="submit" id="updateButton" value="수정 완료" style="display:none;">
</form>
</body>
</html>

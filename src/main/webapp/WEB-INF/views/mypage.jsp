<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mypage</title>
    <script type="text/javascript">
        function update() {
            document.getElementById("cash").disabled = false;
            document.getElementById("stock").disabled = false;
            document.getElementById("property").disabled = false;
            document.getElementById("deposit").disabled = false;
            document.getElementById("consume").disabled = false;

            // 수정 완료 버튼 숨기기
            document.getElementById("editButton").style.display = "none";
            document.getElementById("submitButton").style.display = "none";
            document.getElementById("updateButton").style.display = "inline";

            // controller에 요청
            document.getElementById("assetForm").action = "${pageContext.request.contextPath}/mypage/updateAsset";
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

<h2>자산 등록하기</h2>
<br/>
<form id="assetForm" action="${pageContext.request.contextPath}/mypage/insertAsset" method="post">
    <label for="consume">Consume 유형 선택 :</label>
    <select id="consume" name="consume">
        <option value="항목1">유형1</option>
        <option value="항목2">유형2</option>
        <option value="항목3">유형3</option>
        <option value="항목4">유형4</option>
    </select>
    <br/>

    <label for="cash">현금:</label>
    <input type="number" id="cash" name="cash" value="0"><br/>

    <label for="stock">증권:</label>
    <input type="number" id="stock" name="stock" value="0"><br/>

    <label for="property">부동산:</label>
    <input type="number" id="property" name="property" value="0"><br/>

    <label for="deposit">예적금:</label>
    <input type="number" id="deposit" name="deposit" value="0"><br/>


    <input type="submit" id="submitButton" value="제출">
    <input type="button" id="editButton" value="수정하기" onclick="update()">
    <input type="submit" id="updateButton" value="수정 완료" style="display:none;">

</form>

<h2>개인정보 수정</h2>
<form id="userForm" action="${pageContext.request.contextPath}/mypage/updateMember" method="post">

<%--    수정할 부분 추가--%>
    <label for="email">이메일:</label>
    <input type="email" id="email" name="email" value="${mypage.email}" required /><br/>

    <input type="submit" value="수정하기">

</form>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String userID = (String) session.getAttribute("userID"); // 세션에서 userID 가져오기
  String reqID = request.getParameter("userID"); // 요청 파라미터에서 reqID 가져오기
%>
<html>
<head>
  <title>Index Page</title>
  <script>
    window.onload = function() {
      // reqID가 존재할 경우 팝업 메시지 표시
      const reqID = '<%= reqID != null ? reqID : "" %>';
      if (reqID) {
        alert(reqID + "님 환영합니다!");
      }
    };

    function handleLogout(event) {
      event.preventDefault(); // 기본 링크 클릭 동작을 막음
      alert("로그아웃 처리 중...");

      // 로그아웃 요청을 서버에 보냄
      fetch('/logout', { method: 'POST' })
              .then(response => {
                if (response.ok) {
                  // 세션 스토리지에서 userID 삭제
                  localStorage.removeItem('userID');
                  window.location.href = '/'; // 메인 페이지로 리디렉션
                } else {
                  alert("로그아웃 실패");
                }
              });
    }
  </script>
</head>
<body>
<h1>
  환영합니다, <%= userID != null && !userID.isEmpty() ? userID : "사용자" %>님!
  <span style="float:right;">
        <a href="login" style="font-size: 14px;">로그인</a>
        <a href="#" onclick="handleLogout(event)" style="font-size: 14px; margin-left: 10px;">로그아웃</a>
    </span>
</h1>
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

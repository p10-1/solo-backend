<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Index Page</title>
</head>
<body>
<c:if test="${empty userInfo}">
  <a href="/member/login">로그인</a>
  <h2>사용자님 반갑습니다!</h2>
  <br/>
  <a href="/member/login">자산관리 페이지로 돌아가기</a>
  <br/>
  <a href="/member/login">게시판 페이지로 돌아가기</a>
  <br/>
  <a href="/member/login">금융 정책 페이지로 돌아가기</a>
  <br/>
  <a href="/member/login">금융 뉴스 페이지로 돌아가기</a>
</c:if>
<c:if test="${not empty userInfo}">
  <a href="/member/logout">로그아웃</a>
  <h2>${userInfo.name}님 반갑습니다!</h2>
  <br/>
  <a href="/mypage">마이 페이지로 돌아가기</a>
  <br/>
  <a href="/asset">자산관리 페이지로 돌아가기</a>
  <br/>
  <a href="/board">게시판 페이지로 돌아가기</a>
  <br/>
  <a href="/policy">금융 정책 페이지로 돌아가기</a>
  <br/>
  <a href="/news">금융 뉴스 페이지로 돌아가기</a>
</c:if>

</body>
</html>
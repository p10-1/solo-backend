<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Policy</title>
</head>
<body>
<h2>
    <a href="/">홈으로</a>
</h2>
<h1>정책 페이지 입니다!</h1>
<form action="/policy" method="get">
    <input type="text" name="keyword" value="${keyword}" placeholder="검색어를 입력하세요">
    <button type="submit">검색</button>
</form>
<table border="1">
    <thead>
    <tr>
        <th>기관 및 지자체</th>
        <th>정책명</th>
        <th>정책 소개</th>
        <th>지원내용</th>
        <th>신청사이트주소</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="policy" items="${policies}">
        <tr>
            <td>${policy.polyBizTy}</td>
            <td>${policy.polyBizSjnm}</td>
            <td>${policy.polyItcnCn}</td>
            <td>${policy.sporCn}</td>
            <td>
                <c:choose>
                    <c:when test="${not empty policy.rqutUrla and policy.rqutUrla != 'null' and policy.rqutUrla != '-'}">
                        <a href="${policy.rqutUrla}" target="_blank">${policy.rqutUrla}</a>
                    </c:when>
                    <c:otherwise>
                        링크 없음
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<div>
    <c:if test="${currentPage > 1}">
        <a href="?page=${currentPage - 1}&keyword=${keyword}">이전</a>
    </c:if>
    <c:forEach begin="1" end="${totalPages}" var="i">
        <a href="?page=${i}&keyword=${keyword}">${i}</a>
    </c:forEach>
    <c:if test="${currentPage < totalPages}">
        <a href="?page=${currentPage + 1}&keyword=${keyword}">다음</a>
    </c:if>
</div>
</body>
</html>

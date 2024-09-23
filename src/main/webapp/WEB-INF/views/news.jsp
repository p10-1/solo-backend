<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>News</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h1>뉴스 페이지 입니다!</h1>

<table>
    <thead>
    <tr>
        <th>No</th>
        <th>제목</th>
        <th>링크</th>
        <th>카테고리</th>
        <th>저자</th>
        <th>발행일</th>
        <th>설명</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="news" items="${newsList}">
        <tr>
            <td>${news.no}</td>
            <td>${news.title}</td>
            <td><a href="${news.link}">${news.link}</a></td>
            <td>${news.category}</td>
            <td>${news.author}</td>
            <td>${news.pubDate}</td>
            <td>${news.description}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>

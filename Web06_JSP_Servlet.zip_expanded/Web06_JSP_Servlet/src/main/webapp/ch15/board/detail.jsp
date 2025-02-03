<%@ page import="web.ch15.board.BoardBean" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 조회</title>
</head>
<body>
    <%
        // request 객체에서 getAttribute를 이용하여 board 데이터 참조
        BoardBean board = (BoardBean) request.getAttribute("board");

        // board 객체가 null인지 확인
        if (board != null) {
    %>
        <p>번호 : <%= board.getNo() %></p>
        <p>제목 : <%= board.getTitle() %></p>
        <p>작성자 : <%= board.getWriter() %></p>
        <p>내용 : <%= board.getContent() %></p>
        <p>작성일 : <%= board.getRegDate() %></p>
    <%
        } else {
    %>
        <p>게시글을 불러오는 데 실패했습니다.</p>
    <%
        }
    %>
</body>
</html>

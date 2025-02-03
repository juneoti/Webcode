<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="edu.web.homework.BoardVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>HW1</title>
</head>
<body>

<h2>게시글 목록</h2>

<table>
    <thead>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일</th>
        </tr>
    </thead>
    <tbody>
        <% 
            // 게시글 목록 예시 (ArrayList 사용)
            ArrayList<BoardVO> lists = new ArrayList<>();
            lists.add(new BoardVO(1, "게시글 1", "작성자 1", new Date()));
            lists.add(new BoardVO(2, "게시글 2", "작성자 2", new Date()));
            lists.add(new BoardVO(3, "게시글 3", "작성자 3", new Date()));
            lists.add(new BoardVO(4, "게시글 4", "작성자 4", new Date()));
            lists.add(new BoardVO(5, "게시글 5", "작성자 5", new Date()));

            // ArrayList에 저장된 게시글 출력
            for (int i = 0; i < lists.size(); i++) {
            	BoardVO post = lists.get(i);
        %>
            <tr>
                <td><%= post.getNumber() %></td>
                <td><%= post.getTitle() %></td>
                <td><%= post.getAuthor() %></td>
                <td><%= post.getDate() %></td>
            </tr>
        <% 
            }
        %>
    </tbody>
</table>

</body>
</html>
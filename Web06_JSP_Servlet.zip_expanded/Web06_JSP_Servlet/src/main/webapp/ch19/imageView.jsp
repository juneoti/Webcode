<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.io.File" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 출력</title>
</head>
<body>
<%
    String fileName = (String) session.getAttribute("fileName");
%>
<%
    if (fileName != null) {
%>		<%-- 절대경로/상대경로/getContextPath 다 작동함 --%>
        <img src="<%= request.getContextPath() %>/images/<%= fileName %>
        " alt="업로드된 이미지" style="max-width:600px; height:auto;">
        <a href="../images/<%=fileName %>">링크</a>
<%
    } else {
%>
        <p>이미지를 찾을 수 없습니다.</p>
<%
    }
%>

</body>
</html>
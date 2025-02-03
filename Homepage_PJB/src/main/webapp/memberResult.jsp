<%@page import="edu.web.member.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 조회</title>
<%
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
%>
<style>
    body {
        font-family: Arial, sans-serif;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
        background-color: #f9f9f9;
    }

    .info-container {
        width: 400px;
        padding: 20px;
        background: #ffffff;
        border: 1px solid #ddd;
        border-radius: 10px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        text-align: center;
    }

    .info-container h2 {
        margin-bottom: 20px;
        font-size: 24px;
        color: #333;
    }

    .info-container form {
        display: flex;
        flex-direction: column;
        gap: 15px;
        margin-bottom: 20px;
    }

    .info-container label {
        text-align: left;
        font-size: 14px;
        color: #555;
        margin-bottom: 5px;
        font-weight: bold;
        margin-left: -5px;
    }

    .info-container input {
        width: 100%;
        padding: 8px;
        font-size: 14px;
        border: 1px solid #ddd;
        border-radius: 5px;
        margin-left: -5px;]
    }

    .info-container button {
        padding: 10px;
        font-size: 14px;
        color: #fff;
        background-color: #007bff;
        border: none;
        border-radius: 5px;
        cursor: pointer;
    }

    .info-container button:hover {
        background-color: #0056b3;
    }

    .info-container .back-button {
        background-color: #6c757d; 
    }

    .info-container .back-button:hover {
        background-color: #5a6268; 
    }

    .info-container hr {
        margin: 20px 0;
        border: none;
        border-top: 1px solid #ddd;
    }

    .info-container p {
        text-align: left;
        font-size: 14px;
        color: #333;
        margin: 10px 0;
    }

    .info-container p span {
        font-weight: bold;
    }
</style>
<script>
    window.onload = function() {
        document.getElementById('username').value = '';
    };
</script>
</head>
<body>
    <div class="info-container">
        <h2>회원정보조회</h2>
        <form action="SelectServlet" method="post" autocomplete="off">
            <label for="username">아이디:</label>
            <input type="text" id="username" name="username" required>
            <button type="submit">조회하기</button>
            <button type="button" class="back-button" onclick="location.href='login.jsp'">돌아가기</button>
        </form>
        <hr>

        <%
            MemberVO member = (MemberVO) session.getAttribute("memberInfo");
            if (member != null) {
            session.removeAttribute("memberInfo");
        %>
            <h2><%= member.getUserid() %>님의 회원 정보</h2>
            <p><span>아이디:</span> <%= member.getUserid() %></p>
            <p><span>이메일:</span> <%= member.getEmail() %></p>
            <p><span>관심사:</span> <%= String.join(", ", member.getInterest()) %></p>
            <p><span>전화번호:</span> <%= member.getPhone() %></p>
            <p><span>자기소개:</span> <%= member.getIntroduce() %></p>
        <%
            }
        %>
    </div>
</body>
</html>

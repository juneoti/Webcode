<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8"); // 한글 인코딩				
	%>
	
	<jsp:useBean id="member" class="edu.web.homework.MemberVO"></jsp:useBean>
	<%-- request로 전송된 모든 form data를 MemberVO 클래스의 각 프로퍼티에 저장 --%>
	<jsp:setProperty property="*" name="member"/>
	<h2>회원 정보</h2>
	<p>아이디 : <%=member.getUserid() %></p>
	<p>비밀번호 : <%=member.getPassword() %></p>
	<p>이메일 : <%=member.getEmail()%></p>
	<p>이메일 수신 여부 : <%=member.getEmailAgree() %></p>
	<p>관심사항: <%=member.getInterestJoin() %></p>
	
<%-- 	<ul>
	<% 
    String[] interests = member.getInterest();
    if (interests != null && interests.length > 0) {
        for (int i = 0; i < interests.length; i++) {
	%>
     <li><%= interests[i] %>
     <% 
     	}
    }
     %>
    </ul> --%>
	<p>핸드폰 : <%=member.getPhone() %></p>
	<p>자기소개 : <%=member.getIntroduce() %></p>
</body>
</html>
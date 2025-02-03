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
		// form input name 이름에 맞게 parameter 데이터 저장
		// 각 변수명은 parameter name과 동일하게 작성(권장)
/* 		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String emailAgree = request.getParameter("emailAgree");
		String introduce = request.getParameter("introduce"); */
				
	%>
	
	<%-- ch10 MemberBean 클래스 연결 --%>
	<jsp:useBean id="member" class="web.ch10.bean.MemberBean"></jsp:useBean>
	<%-- request로 전송된 모든 form data를 MemberBean 클래스의 각 프로퍼티에 저장 --%>
	<jsp:setProperty property="*" name="member"/>
	<h2>회원 정보</h2>
	<p>아이디 : <%=member.getUserid() %></p>
	<p>비밀번호 : <%=member.getPassword() %></p>
	<p>이메일 : <%=member.getEmail()%></p>
	<p>이메일 수신 여부 : <%=member.getEmailAgree() %></p>
	<p>자기소개 : <%=member.getIntroduce() %></p>
</body>
</html>
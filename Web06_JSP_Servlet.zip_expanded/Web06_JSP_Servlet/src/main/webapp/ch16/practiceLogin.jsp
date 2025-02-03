<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<%
		// 저장된 id, pw 쿠키를 꺼내서
		// input 태그에 값 보여주기
		Cookie[] cookies = request.getCookies();
        String savedId = "";
        String savedPw = "";

        if (cookies != null) {
    	    for (Cookie cookie : cookies) {
  	        	if ("id".equals(cookie.getName())) {
  	        		savedId = cookie.getValue();
            	} else if ("pw".equals(cookie.getName())) {
                  	savedPw = cookie.getValue();
                  }
            }
        }
	%>
   <form action="practiceResult.jsp" method="post">
      아이디<br>
      <input type="text" name="id" placeholder=<%=savedId %> required><br>
      비밀번호<br>
      <input type="password" name="pw" placeholder=<%=savedPw %> required><br>
      <input type="checkbox" name="saveAgreed" value="agreed">
      아이디 저장<br><br>
      <input type="submit" value="로그인">
   </form>
</body>
</html>
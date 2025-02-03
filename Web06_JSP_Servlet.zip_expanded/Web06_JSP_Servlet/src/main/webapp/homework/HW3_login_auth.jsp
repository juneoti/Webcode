<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 인증</title>
</head>
<body>
    <%
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        if ("test".equals(id) && "1234".equals(password)) {
            HttpSession login = request.getSession(true);
            session.setAttribute("id", id);

            session.setMaxInactiveInterval(60);
            
            System.out.println("세션 생성 완료: 사용자 ID = " + id + ", 세션 ID = " + session.getId());
            
            out.print("<script>alert('인증되었습니다.');</script>");
            out.print("<script>location.href='HW3_login_result.jsp'</script>");
        } else {
            HttpSession login = request.getSession(false);
            System.out.println("아이디 또는 비밀번호 입력 오류, 세션이 생성되지 않음");
            out.print("<script>alert('아이디 또는 비밀번호를 확인해주세요!!');</script>");
            out.print("<script>location.href='HW3.jsp'</script>");
        }
    %>
</body>
</html>

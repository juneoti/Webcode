z<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 결과 페이지</title>
</head>
<body>
	<%
 		HttpSession login = request.getSession(false);
        String id = null;
        if (session != null) {
            id = (String) session.getAttribute("id");
        }

        if (id == null) {
    	System.out.println("세션이 없을 시 URL 경로 접근 차단");
		System.out.println("생성된 Session이 없습니다.");
    %>
         out.print("<script>alert('로그인 해주세요!!');</script>");
         out.print("<script>location.href='HW3.jsp'</script>");
    <%
        } 
    else {
    %>
    	<%=id %>님이 로그인하셨습니다.
        <form method="post">
            <input type="submit" name="deleteSession" value="세션 삭제">
        </form>
    <%
    	if (request.getParameter("deleteSession") != null) {
        session.invalidate();
        
        System.out.println("세션 삭제 완료");
        out.print("<script>alert('session이 삭제되었습니다.');</script>");
        out.print("<script>location.href='HW3.jsp'</script>");
   		}
    }
    %>
</body>
</html>
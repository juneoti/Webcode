<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 결과 페이지</title>
</head>
<body>
	<%
		// practiceLogin.jsp에서 전송된
		// saveAgreed가 체크 되어 있으면
		// id, pw에 대한 쿠키를 생성한다.
		// 쿠키 만료 시간은 10분으로 설정
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String saveAgreed = request.getParameter("saveAgreed");
		if("agreed".equals(saveAgreed)){
		// 파라미터 값으로 쿠키 생성
		Cookie idCookie = new Cookie("id", id);
		Cookie pwCookie = new Cookie("pw", pw);
		
		// 만료 시간 설정(초단위) : 24시간
		idCookie.setMaxAge(60 * 10);
		pwCookie.setMaxAge(60 * 10);
		
		response.addCookie(idCookie);
		response.addCookie(pwCookie);
		}
	%>
	
	<h2>로그인 결과 화면</h2>
	<%-- [아이디]님, 환영합니다 출력 --%>
    <%
        if (id != null && "agreed".equals(saveAgreed)) {
            out.println(id + "님, 환영합니다. 쿠키가 업데이트 되었습니다.");
        } else {
            Cookie[] cookies = request.getCookies();
            String savedId = null;
            String savedPw = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("id".equals(cookie.getName())) {
                        savedId = cookie.getValue();
                    } else if ("pw".equals(cookie.getName())) {
                        savedPw = cookie.getValue();
                    }
                }
            }


        if (id != null && pw != null && id.equals(savedId) && pw.equals(savedPw)) {
                out.println(id + "님, 환영합니다");
            } else if (id != null && pw != null) {
                out.println("로그인 실패: 아이디 또는 비밀번호가 일치하지 않습니다.");
            } else {
                out.println("로그인 정보가 없습니다.");
            }
        }
    %>
</body>
</html>
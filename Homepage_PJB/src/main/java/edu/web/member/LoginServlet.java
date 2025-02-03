package edu.web.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public LoginServlet() {
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 파라미터 받기
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // 로그 출력
        System.out.println("[LoginServlet] User ID: " + username);
        System.out.println("[LoginServlet] Password: " + password);
        
        // DAO 객체 사용하여 로그인 확인 처리
        MemberDAO dao = MemberDAOImple.getInstance();
        MemberVO member = dao.login(username, password);
        
        // 결과 처리
        if (member != null) {
        	request.getSession().setMaxInactiveInterval(60);
        	request.getSession().setAttribute("userid", username);
        	System.out.println("[LoginServlet] 세션 생성됨: " + request.getSession().getId());
            System.out.println("[LoginServlet] 세션 유지 시간 설정됨: " + request.getSession().getMaxInactiveInterval() + "초");
            System.out.println("[LoginServlet] 로그인 성공");
            response.getWriter().write("<script>alert('로그인 성공'); location.href='loginResult.jsp';</script>");
        } else {
            System.out.println("[LoginServlet] 로그인 실패");
            response.getWriter().write("<script>alert('로그인 실패'); location.href='login.jsp';</script>");
        }
	}

}

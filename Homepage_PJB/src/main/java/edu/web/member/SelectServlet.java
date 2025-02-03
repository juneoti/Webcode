package edu.web.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SelectServlet")
public class SelectServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String username = request.getParameter("username");

        if (username == null || username.isEmpty()) {
            
            response.getWriter().write("<script>alert('아이디를 입력해주세요.'); history.back();</script>");
            return;
        }

        MemberDAO dao = MemberDAOImple.getInstance();
        MemberVO member = dao.select(username);

        if (member != null) {
        	System.out.println("[SelectServlet] 회원 정보 조회 성공");
            request.getSession().setAttribute("memberInfo", member);
            response.sendRedirect("memberResult.jsp");
        } else {
        	System.out.println("[SelectServlet] 회원 정보 조회 실패");
           
            response.getWriter().write("<script>alert('회원 정보를 찾을 수 없습니다.'); history.back();</script>");
        }
    }
}

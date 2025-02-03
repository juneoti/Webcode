package edu.web.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteServlet() {
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession(false);
	        if (session != null) {
	        	System.out.println("[DeleteServlet] 세션 존재 확인");
	        	
	            String userid = (String) session.getAttribute("userid");
	            System.out.println("[DeleteServlet] 세션에서 가져온 userid: " + userid);
	            
	            if (userid != null) {
	                MemberDAO dao = MemberDAOImple.getInstance();
	                int result = dao.delete(userid);

	                if (result > 0) {
	                	System.out.println("[DeleteServlet] 회원 탈퇴 성공: " + userid);
	                } else {
	                	System.out.println("[DeleteServlet] 회원 탈퇴 실패: " + userid);
	                }

	                session.invalidate();
	                System.out.println("[DeleteServlet] 세션 무효화 완료");
	                response.sendRedirect("login.jsp");
	                System.out.println("[DeleteServlet] login.jsp로 리다이렉트 완료");
	                return;
	            }
	        }
	        System.out.println("[DeleteServlet] 세션이 존재하지 않음");
	        response.sendRedirect("login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}

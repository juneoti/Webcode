package edu.web.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UpdateServlet() {
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String emailAgree = request.getParameter("emailAgree") != null ? "YES" : "NO";
        String[] interest = request.getParameterValues("interest");
        String phone = request.getParameter("phone");
        String introduce = request.getParameter("introduce");
        
        MemberVO member = new MemberVO(userid, password, email, emailAgree, interest, phone, introduce);

        MemberDAO dao = MemberDAOImple.getInstance();
        int result = dao.update(member);

        if (result > 0) {
            System.out.println("[UpdateServlet] 회원 정보 업데이트 성공");
            response.getWriter().write("<script>alert('회원 정보가 성공적으로 수정되었습니다.'); location.href='loginResult.jsp';</script>");
        } else {
            System.out.println("[UpdateServlet] 회원 정보 업데이트 실패");
            response.getWriter().write("<script>alert('회원 정보 수정에 실패하였습니다. 다시 시도해 주세요.'); location.href='memberUpdate.jsp';</script>");
        }
	}

}

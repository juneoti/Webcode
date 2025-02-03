package edu.web.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
    public RegisterServlet() {

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
	     
	     System.out.println("[RegisterServlet] User ID: " + userid);
	     System.out.println("[RegisterServlet] Password: " + password);
	     System.out.println("[RegisterServlet] Email: " + email);
	     System.out.println("[RegisterServlet] Email Agree: " + emailAgree);
	     System.out.println("[RegisterServlet] Interest: " + (interest != null ? String.join(", ", interest) : "없음"));
	     System.out.println("[RegisterServlet] Phone: " + phone);
	     System.out.println("[RegisterServlet] Introduce: " + introduce);
	        
	     MemberVO member = new MemberVO(userid, password, email, emailAgree, interest, phone, introduce);
	     
	     System.out.println("[RegisterServlet] MemberVO: " + member);
	     
	     MemberDAO dao = MemberDAOImple.getInstance();
	     int result = dao.insert(member);
	     
	     if (result > 0) {
	    	 System.out.println("[RegisterServlet] 회원가입 성공");

	    	 response.getWriter().write("<script>alert('회원가입 성공'); location.href='login.jsp';</script>");
	     } else {
	    	 System.out.println("[RegisterServlet] 회원가입 실패");

	         response.getWriter().write("<script>alert('회원가입 실패'); location.href='memberRegister.jsp';</script>");
	     }
	}

}

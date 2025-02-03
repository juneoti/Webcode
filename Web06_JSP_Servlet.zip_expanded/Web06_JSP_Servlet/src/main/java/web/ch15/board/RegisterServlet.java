package web.ch15.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ch15/member/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public RegisterServlet() {
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	// register.jsp의 form method="post"이므로 doPost() 호출
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// form에서 보낸 Form Data는 request 객체에 저장되어 있음
		// request parameter로 데이터 참조
		
		// getParameter name : input name과 동일
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String emailAgree = request.getParameter("emailAgree");
		String introduce = request.getParameter("introduce");
		
		// DB 연결 생략
		System.out.println("userid = " + userid);
		System.out.println("password = " + password);
		System.out.println("email = " + email);
		System.out.println("emailAgree = " + emailAgree);
		System.out.println("introduce = " + introduce);
	}

}








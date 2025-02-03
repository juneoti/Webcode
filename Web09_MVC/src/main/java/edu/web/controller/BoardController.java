package edu.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.web.domain.BoardVO;
import edu.web.persistence.BoardDAO;
import edu.web.persistence.BoardDAOImple;
import edu.web.util.PageCriteria;
import edu.web.util.PageMaker;

@WebServlet("*.do") // *.do ~.do로 요청된 HTTP 호출에 대해 반응
public class BoardController extends HttpServlet {
	
	private static final String BOARD_URL = "/WEB-INF/board/";
	private static final String MAIN = "index";
	private static final String LIST = "list";
	private static final String REGISTER = "register";
	private static final String DETAIL = "detail";
	private static final String UPDATE = "update";
	private static final String DELETE = "delete";
	private static final String EXTENSION = ".jsp";
	private static final String SERVER_EXTENSION = ".do";
	
	
	private static BoardDAO dao;
	
	public BoardController() {
		dao = BoardDAOImple.getInstance();
	}
// http://localhost:8080/Web09_MVC/board/register.do
// 위 경로로 get 요청이 발생되면, 아래 페이지를 사용자에게 돌려주도록 구현
// http://localhost:8080/Web09_MVC/WEB-INF/board/register.jsp
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String requestMethod = request.getMethod();
		System.out.println("호출 경로 : " + requestURI);
		System.out.println("호출 방식 : " + requestMethod);
	
		if(requestURI.contains(LIST + SERVER_EXTENSION)) {
			System.out.println("list 호출 확인");
			list(request, response);
		} else if(requestURI.contains(REGISTER + SERVER_EXTENSION)) {
			System.out.println("register 호출 확인");
			if(requestMethod.equals("GET")) { // GET 방식(페이지 호출)
				registerGET(request, response);
			}else if(requestMethod.equals("POST")) { // POST 방식(DB에 저장)
				registerPOST(request, response);
			}
		} else if(requestURI.contains(DETAIL + SERVER_EXTENSION)) {
			System.out.println("detail 호출 확인");
			detail(request, response);
		} else if(requestURI.contains(UPDATE + SERVER_EXTENSION)) {
			System.out.println("update 호출 확인");
			if(requestMethod.equals("GET")) {
				updateGET(request, response);
			} else if(requestMethod.equals("POST")) {
				updatePOST(request, response);
			}
		} else if (requestURI.contains(DELETE + SERVER_EXTENSION)) {
			System.out.println("delete 호출 확인");
			if(requestMethod.equals("POST")) {
				deletePOST(request, response);
			}
		}
	} // end service()
	
	
	
	
	
	private void registerGET(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("registerGET()");
		
		// 로그인 세션 체크
		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("memberId");
		
		if(memberId != null) { // 로그인 상태 일 경우
		String path = BOARD_URL + REGISTER + EXTENSION;
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
		} else { // 로그아웃 상태 일 경우
			// 로그인 페이지로 이동
			response.sendRedirect("login.go?targetURL=" + REGISTER + SERVER_EXTENSION); // 로그인 페이지로 이동
		}
	}
	
	// register.jsp form에서 전송된 데이터를 DB 테이블에 등록
	// 그리고 index.jsp로 이동
	private void registerPOST(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("registerPOST()");
		String boardTitle = request.getParameter("boardTitle");
		String memberId = request.getParameter("memberId");
		String boardContent = request.getParameter("boardContent");
		
		BoardVO vo = new BoardVO(0, boardTitle, boardContent, memberId, null);
		System.out.println(vo);
		int result = dao.insert(vo);
		System.out.println("결과 : " + result);
		
		if(result == 1) {
			response.sendRedirect("index.jsp");
		}
	}
	
	// 전체 게시글 내용(list)을 DB에서 조회하고, 그 데이터를 list.jsp 페이지에 전송
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("list()");
		String page = request.getParameter("page");
		System.out.println("page = " + page);
		
		PageCriteria criteria = new PageCriteria();
		
		if(page != null) {
		criteria.setPage(Integer.parseInt(page));
		}
		
		List<BoardVO> list = dao.select(criteria);
		
		String path = BOARD_URL + LIST + EXTENSION;
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		
		request.setAttribute("list", list);
		
		PageMaker pageMaker = new PageMaker();
		int totalCount = dao.getTotalCount();
		pageMaker.setTotalCount(totalCount);
		pageMaker.setCriteria(criteria);
		pageMaker.setPageData(); // 적용된 값들로 pageMaker의 필드 값 계산
		
		request.setAttribute("pageMaker", pageMaker);
		
		dispatcher.forward(request, response);
	}
	
	// DB 테이블에서 상세 게시글 조회 데이터를 가져와서
	// detail.jsp 페이지로 전송
	private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("detail()");
		
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		BoardVO vo = dao.select(boardId);
		System.out.println(vo);
		
		String path = BOARD_URL + DETAIL + EXTENSION;
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		request.setAttribute("vo", vo);
		dispatcher.forward(request, response);
	}
	
	// DB 테이블에서 상세 게시글 조회 데이터를 가져와서
	// update.jsp 페이지에 전송
	private void updateGET(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("updateGET()");
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		BoardVO vo = dao.select(boardId);
		
		String path = BOARD_URL + UPDATE + EXTENSION;
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		request.setAttribute("vo", vo);
		dispatcher.forward(request, response);
	}
	
	// update.jsp에서 전송된 데이터를 DB 테이블에 전송하여 게시글 수정
	// 수정이 완료되면, detail.jsp로 이동
	private void updatePOST(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		String boardTitle = request.getParameter("boardTitle");
		String boardContent = request.getParameter("boardContent");
		
		BoardVO vo = new BoardVO(boardId, boardTitle, boardContent, null, null);
		int result = dao.update(vo);
		System.out.println("결과 : " + result);
		
		if(result == 1) {
			String path = DETAIL + SERVER_EXTENSION;
			response.sendRedirect(path + "?boardId=" + boardId);
		}
	}
	
	// 선택된 게시글 번호를 전송받아서, DB 테이블에서 게시글 데이터 삭제
	// 삭제가 완료되면, index.jsp로 이동
	private void deletePOST(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		int boardId = Integer.parseInt(request.getParameter("boardId"));
		int result = dao.delete(boardId);
		
		if(result == 1) {
			response.sendRedirect(MAIN + EXTENSION);
		}
	}
} // end BoardController

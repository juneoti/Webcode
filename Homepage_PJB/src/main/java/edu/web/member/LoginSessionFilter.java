package edu.web.member;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginSessionFilter extends HttpFilter implements Filter {

    public LoginSessionFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();

        HttpSession session = httpRequest.getSession(false);
        String userid = (session != null) ? (String) session.getAttribute("userid") : null;

        String[] restrictedPages = { "/loginResult.jsp", "/memberUpdate.jsp" };
        boolean isRestrictedPage = false;

        for (String restrictedPage : restrictedPages) {
            if (uri.endsWith(restrictedPage)) {
                isRestrictedPage = true;
                break;
            }
        }

        if (isRestrictedPage && userid == null) {
            System.out.println("[LoginSessionFilter] 접근 제한된 페이지 요청: " + uri);
            httpResponse.getWriter().println("<script>");
            httpResponse.getWriter().println("alert('로그인이 필요합니다. 로그인 페이지로 이동합니다.');");
            httpResponse.getWriter().println("window.location.href = 'login.jsp';");
            httpResponse.getWriter().println("</script>");
            return;
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}

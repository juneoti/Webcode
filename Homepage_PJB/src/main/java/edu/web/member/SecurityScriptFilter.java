package edu.web.member;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class SecurityScriptFilter extends HttpFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        System.out.println("[SecurityScriptFilter] 필터 실행: " + httpRequest.getRequestURI());

        // 정적 리소스 요청 필터 제외
        if (httpRequest.getRequestURI().matches(".*\\.(css|js|png|jpg|jpeg|gif|ico|webp|woff|woff2|ttf|svg|eot)$")) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(httpResponse) {
            private CharArrayWriter charWriter = new CharArrayWriter();

            @Override
            public PrintWriter getWriter() throws IOException {
                return new PrintWriter(charWriter);
            }

            @Override
            public String toString() {
                return charWriter.toString();
            }
        };

        chain.doFilter(request, responseWrapper);

        String originalContent = responseWrapper.toString();
        if (!originalContent.contains("<!-- Security Script -->")) {
            String securityScripts = getSecurityScripts();
            originalContent = originalContent.replace("</body>", securityScripts + "</body>");
        }

        response.getWriter().write(originalContent);
    }

    private String getSecurityScripts() {
        return "<!-- Security Script -->" +
                "<script>" +
                "document.removeEventListener('keydown', blockKeys);" +
                "document.addEventListener('keydown', blockKeys);" +
                "function blockKeys(e) {" +
                "    if (e.key === 'F12' || " +
                "        (e.ctrlKey && e.shiftKey && (e.key === 'I' || e.key === 'C' || e.key === 'J')) || " +
                "        (e.ctrlKey && e.key === 'U')) {" +
                "        e.preventDefault();" +
                "        alert('개발자 도구 사용이 제한되었습니다.');" +
                "    }" +
                "}" +
                "document.addEventListener('contextmenu', function(e) {" +
                "    e.preventDefault();" +
                "    alert('우클릭이 제한되었습니다.');" +
                "});" +
                "console.log('%c경고: 이 브라우저 도구는 개발자 전용입니다.', 'color: red; font-size: 16px;');" +
                "console.log('%c여기서 코드를 입력하지 마세요. 보안 위험이 있을 수 있습니다.', 'color: orange; font-size: 14px;');" +
                "</script>";
    }
}

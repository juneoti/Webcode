<%@page import="org.apache.commons.fileupload.FileItem"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import="java.io.File"%>
<%@page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
   request.setCharacterEncoding("UTF-8"); // 한글 인코딩
   String realPath = "";
   String savePath = "images";
   String type ="utf-8";
   int maxSize = 10 * 1024 * 1024;

   // 현재 애플리케이션 정보 저장
   ServletContext context = request.getServletContext();
   
   // 실제 경로 확인
   realPath = context.getRealPath(savePath);
   
   out.println(realPath);
   out.print("<hr>");
   
   DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
   diskFileItemFactory.setRepository(new File(realPath));
   diskFileItemFactory.setSizeThreshold(maxSize);
   diskFileItemFactory.setDefaultCharset(type);
   
   // 업로드 핸들러
   ServletFileUpload fileupload
      = new ServletFileUpload(diskFileItemFactory);
   
   // request 정보를 가져와서 upload 형태에 맞게 파싱
   List<FileItem> items = fileupload.parseRequest(request);
   for(FileItem item : items){
      if(item.isFormField()) { // form 데이터인 경우
         out.print(item.getString() + "<br>");      
      } else { // 파일인 경우
         out.print("파일명 : " + item.getName() + ", 파일 크기 : " + item.getSize() + "<br>");
         String separator = File.separator; // \
         int index = item.getName().lastIndexOf(separator);
         String fileName = item.getName().substring(index + 1);
         File uploadFile = new File(realPath + separator + fileName);
         item.write(uploadFile); // 받은 파일을 서버에 업로드
         out.print(uploadFile + "<br>");
         
         // 다른 jsp에서 전송된 이미지 확인
         response.sendRedirect("/Web06_JSP_Servlet/ch19/imageView.jsp");
         session.setAttribute("fileName", fileName);
      }
   }
   
   
%>









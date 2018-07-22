<%-- 
    Document   : upload
    Created on : 19 Jun, 2018, 11:39:55 PM
    Author     : Gunee
--%>

<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
InputStream content = request.getPart("image").getInputStream();
if(content.available()>0){
int a,b=0;
		
		byte [] buffer=new byte[content.available()];
		while((content.available())>0){
			buffer[b]=(byte)content.read();
			b++;
		}
                File file=new File("//upload//"+"filename"+".jpg");
                file.getParentFile().mkdirs();
                FileOutputStream fo=new FileOutputStream(file);
                fo.write(buffer);
                fo.flush();
                response.setStatus(HttpServletResponse.SC_OK);
}else
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
%>

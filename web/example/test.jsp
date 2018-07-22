<%-- 
    Document   : test
    Created on : 9 Mar, 2018, 2:16:54 AM
    Author     : Gunee
--%>


<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.File"%>

<%  
    
    response.setContentType("image/png");
    response.setStatus(200);
    
    out.print("hello");
    
    
    ServletOutputStream output=response.getOutputStream();
    //   BufferedInputStream bin = new BufferedInputStream(fin);  
    //BufferedOutputStream bout = new BufferedOutputStream(out); 
    FileInputStream file=new FileInputStream("c:\\mail_bullet.png");
    while(file.available()>0){
        output.write(file.read());
    }
    file.close();
    

%>
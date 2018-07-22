<%-- 
    Document   : newjsp
    Created on : 16 Mar, 2018, 2:09:53 AM
    Author     : Gunee
--%>

<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.DriverManager"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%
        Connection con=null;
    try{
        if(con==null){
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_site?zeroDateTimeBehavior=convertToNull","root","");
            out.print("con success");
        }
        String query = " select U_name , passwd from usergeneral where U_name== abc && paswd== 123";
        Statement st = con.createStatement();
        ResultSet rs=st.executeQuery(query);
        
        if(rs.first()){
            
            response.setStatus(HttpServletResponse.SC_OK);
           
            out.println("success");
            
            //redirect to a new servlet
            // or
            //load the chats and send the data
        }
        else out.print("not found");
        
        
        
      
    }catch(SQLException e){
            out.print("not found" +e);
        }%>
    </body>
</html>

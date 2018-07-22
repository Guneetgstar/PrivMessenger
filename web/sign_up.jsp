<%-- 
    Document   : sign_up
    Created on : 8 Mar, 2018, 4:00:05 AM
    Author     : Gunee
--%>

<%@page contentType="text" pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>

<%
    String email=request.getParameter("email");
    String number=request.getParameter("number");
    String password=request.getParameter("password");
    

    Connection con=null;
    try{
        if(con==null)
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_site?zeroDateTimeBehavior=convertToNull"
            ,"root","");
         String query = " insert into usergeneral (U_name, email, passwd, Mob)"
        + " values (?,?,?,?)";
        PreparedStatement st = con.prepareStatement(query);
        st.setString(1,email);
        st.setString(2,email);
        st.setString(3,password);
        st.setString(4,number);
        st.execute();
        Cookie e_mail=new Cookie("email",email);
        Cookie pass=new Cookie("pw",password);
        
        response.addCookie(e_mail);
        response.addCookie(pass);
        
      
        
    
    }catch(SQLException e){
            out.print("not found" +e);
        }
    

%>
    
    
    

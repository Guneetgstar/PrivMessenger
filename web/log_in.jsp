<%-- 
    Document   : log_in
    Created on : 8 Mar, 2018, 4:53:37 AM
    Author     : Gunee
--%>
<%@page import="javax.servlet.http.HttpServletResponse.*"%>
<%@page contentType="text" pageEncoding="UTF-8"%>
<%@page import="java.sql.*" %>
<%@ page session="false" %>

<%
    String uname = request.getParameter("uname");
    String password = request.getParameter("password");
    Connection con = null;
    Class c = null;
    try {
        // The newInstance() call is a work around for some
        // broken Java implementations

        c = Class.forName("com.mysql.jdbc.Driver");
        out.print("classfound");
    } catch (Exception ex) {
        out.print("class not found");
    }
    if (c != null) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_site?zeroDateTimeBehavior=convertToNull", "root", "");
            //out.println("success")
            System.out.println(uname+"->"+password);
            String query = "select U_name , passwd from usergeneral where U_name='" + uname + "' and passwd='" + password + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            if (rs.first()) {
                Date date=new Date(System.currentTimeMillis());
                System.out.println(rs.getString(1)+"<-"+rs.getString(2)+date.toString());

                response.setStatus(HttpServletResponse.SC_OK);
                Cookie e_mail = new Cookie("email", uname);
                e_mail.setDomain("localhost");
                e_mail.setPath("/");
                e_mail.setMaxAge(60 * 60 * 60 * 24 * 365);
                Cookie pass = new Cookie("pw", password);
                pass.setDomain("localhost");
                pass.setPath("/");
                pass.setMaxAge(60 * 60 * 60 * 24 * 365);
                response.addCookie(e_mail);
                response.addCookie(pass);
                out.print("created");

                //redirect to a new servlet
                // or
                //load the chats and send the data
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                out.print("not found");
            }

        } catch (SQLException e) {
            out.print("not found" + e);
        }
    }

%>


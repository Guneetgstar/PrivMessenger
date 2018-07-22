<%-- 
    Document   : Redirect
    Created on : 15 Mar, 2018, 1:47:32 AM
    Author     : Gunee
--%>

<%@page import="java.util.Date"%>
<%@page errorPage="//http://localhost:8084/PrivMessenger/log_in.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <body>
        <h1><% 
            //response.setStatus(response.SC_MOVED_TEMPORARILY);
            //response.setHeader("Location", "//www.google.com");
            response.setHeader("Refresh", "3; URL=http://home.netscape.com");
            out.print(new Date());
        %>
        </h1>
    </body>
</html>

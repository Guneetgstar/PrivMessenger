/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LogIn extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                
                e_mail.setMaxAge(60 * 60 * 60 * 24 * 365);
                Cookie pass = new Cookie("pw", password);
               
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

    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
        
    }

  
  
}

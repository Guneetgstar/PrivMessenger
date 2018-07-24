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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gunee
 */
public class SignUp extends HttpServlet {

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
    

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }

    
}

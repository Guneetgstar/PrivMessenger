/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.Utils;

/**
 *
 * @author Gunee
 */
public class Messeges extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String uname = "";
        String password = "";
        String friend=request.getParameter("friend");
        if (cookies != null) {
            HashMap<String, String> map = new HashMap<String,String>();
            for (Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie.getValue());
            }
            uname = map.get("email");
            System.out.println("uname<--"+uname);
            password = map.get("pw");
            System.out.println("pw<--"+password);
        }

        Connection con = null;
        Class c = null;
        try {

            c = Class.forName("com.mysql.jdbc.Driver");
            //System.out.println("classfound");
        } catch (Exception ex) {
            System.out.println("class not found");
        }

        if (c != null) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_site?zeroDateTimeBehavior=convertToNull", "root", "");
                //System.out.println("success");
                if(Utils.validate(uname, password, con)){
                String query = "select * FROM conversations WHERE (sndr_ID='"+uname+"' AND rcvr_ID='"+friend+"') OR "
                        + "(sndr_ID='"+friend+"' AND rcvr_ID='"+uname+"')";
                Statement st=con.createStatement();

                ResultSet rs = st.executeQuery(query);

                int i = 1;

                JSONArray array = new JSONArray();
                while (rs.next()) {
                    if (i == 1) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.setContentType("application/octet-stream");
                    }
                    JSONObject obj = new JSONObject();
                    i++;
                    obj.put("msg_ID", rs.getInt(1));
                    obj.put("sender", rs.getString(2));
                    obj.put("reciver", rs.getString(3));
                    obj.put("msg", rs.getString(4));
                    obj.put("date", rs.getDate(5));
                    array.add(obj);
                    //System.out.print(obj.toJSONString());
                }

                if (!rs.first()) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    System.out.println("not found");
                } else {

                    PrintWriter writer = response.getWriter();
                    //System.out.println(array.toJSONString());
                    writer.println(array.toJSONString());
                }
                    
                }
                

            } catch (SQLException e) {
                System.out.print("not found" + e);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IO error");
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);

    }

}

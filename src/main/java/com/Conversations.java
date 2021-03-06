/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.*;

/**
 *
 * @author Gunee
 */
public class Conversations extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        String uname = "";
        String password = "";
        String fun="";
        if (cookies != null) {
            HashMap<String, String> map = new HashMap<String,String>();
            for (Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie.getValue());
            }
            System.out.println(request.toString());
            uname = map.get("email");
            System.out.println("uname<--"+uname);
            password = map.get("pw");
            System.out.println("pw<--"+password);
            fun=map.get("fun");
            System.out.println("fun<--"+fun);
        }
        Connection con = null;
        Class c = null;
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            c = Class.forName("com.mysql.jdbc.Driver");
            //out.println("classfound");
        } catch (Exception ex) {
            //out.println("class not found");
        }
        if (c != null) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_site?zeroDateTimeBehavior=convertToNull", "root", "");
                //out.println("success");

                if (Utils.validate(uname, password,con)) {
                    String query = "select * FROM conversations WHERE sndr_ID= ? or rcvr_ID= ? GROUP BY sndr_ID, rcvr_ID ORDER by msg_ID DESC";
                    PreparedStatement st = con.prepareStatement(query);
                    st.setString(1, uname);
                    st.setString(2, uname);
                    ResultSet rs = st.executeQuery();
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
                    }

                    if (!rs.first()) {
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                        out.println("not found");
                    } else {

                        PrintWriter writer = response.getWriter();
                        System.out.println(array.toJSONString());
                        writer.println(array.toJSONString());
                    }

                }
            } catch (SQLException s) {
                System.out.print("not found" + s);
                while((s = s.getNextException()) != null) {
                out.println(s.getMessage());
                 }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

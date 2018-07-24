/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.Utils;

/**
 * @author Gunee
 */
public class NewMsg extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            file = new FileInputStream("D:\\Downloads\\nodal-clock-191221-firebase-adminsdk-hgprd-991400b24c.json");
        } catch (FileNotFoundException e1) {
        }
        try {
            options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(file)).build();
            //.setDatabaseUrl("https://nodal-clock-191221.firebaseio.com/").build();
        } catch (Exception e) {
        }
        if(options!=null||app!=null) {
            app=FirebaseApp.initializeApp(options);
        }
    }
    FileInputStream file=null;
    FirebaseOptions options=null;
    FirebaseApp app=null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String friend = request.getParameter("rcvr");
        String msg = request.getParameter("msg");
        Cookie[] cookies = request.getCookies();
        String uname = "";
        String password = "";
        String token = "";
        if (cookies != null) {
            HashMap<String, String> map = new HashMap<String, String>();
            for (Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie.getValue());
            }
            uname = map.get("email");
            System.out.println("uname<--" + uname);
            password = map.get("pw");
            System.out.println("pw<--" + password);
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

                if (Utils.validate(uname, password, con)) {
                    String update = "INSERT INTO `conversations`(`sndr_ID`, `rcvr_ID`, `msg`) VALUES ('" + uname + "','" + friend + "','" + msg + "')";
                    //String query = "select * FROM conversations WHERE (sndr_ID='" + uname + "' AND rcvr_ID='" + friend + "') OR "
                      //      + "(sndr_ID='" + friend + "' AND rcvr_ID='" + uname + "')";
                    String query = "select * FROM conversations WHERE (sndr_ID='" + uname + "' AND rcvr_ID='" + friend + "') OR "
                          + "(sndr_ID='" + friend + "' AND rcvr_ID='" + uname + "') ORDER BY msg_ID DESC limit 1";
                    Statement st = con.createStatement();
                    System.out.println("elements  updated" + st.executeUpdate(update));
                    ResultSet rs=st.executeQuery(query);
                    rs.first();
                    response.setStatus(HttpServletResponse.SC_OK);
                    JSONObject obj = new JSONObject();
                    obj.put("msg_ID", rs.getInt(1));
                    obj.put("sender", rs.getString(2));
                    obj.put("reciver", rs.getString(3));
                    obj.put("msg", rs.getString(4));
                    obj.put("date", rs.getDate(5));
                    token=Utils.findToken(friend);
                    Message message = Message.builder().putData("profile",uname).setToken(token).build();
                    try {
                        System.out.println(FirebaseMessaging.getInstance().send(message));
                    } catch (FirebaseMessagingException e) {
                        System.out.print("Error sending messege :" + e);
                    }
                    try {
                        response.getWriter().write(obj.toJSONString());
                        System.out.println(obj.toJSONString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                /*ResultSet rs = st.executeQuery(query);
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
                        //out.println("not found");
                    } else {
                       
                        PrintWriter writer = response.getWriter();
                        System.out.println(array.toJSONString());
                        writer.println(array.toJSONString());
                    }
*/
                }
            } catch (SQLException s) {
                System.out.print("not found" + s);
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}

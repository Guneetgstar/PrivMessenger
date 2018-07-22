/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import util.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Gunee
 */
public class Token extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token =request.getParameter("token");
        if(token!=null) {
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(cookie);
        }
        System.out.println("android token :"+token);
        response.getWriter().print(token);
        HashMap<String,String> map=Utils.getCookie(request.getCookies());
        if(map!=null){
            if(Utils.validate(map.get("uname"),map.get("pw"),Utils.getConnection())){
                Utils.addToken(token,map);
            }
        }
        
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }


}

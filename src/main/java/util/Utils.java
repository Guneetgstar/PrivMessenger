/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import static java.lang.System.out;

/**
 * @author Gunee
 */
public class Utils {
    public static boolean validate(String uname, String password, Connection con) {
        String login = "select * from usergeneral where U_name='" + uname + "' and passwd='" + password + "'";

        boolean valid = false;

        try {
            //out.println("success");
            Statement stat = con.createStatement();
            ResultSet loginSet = stat.executeQuery(login);
            if (loginSet.first()) {
                valid = true;
            } else
                valid = false;
        } catch (SQLException e) {
            System.out.println("con not found" + e);
        }
        System.out.println(valid+uname+password);
        return valid;

    }

    public static Connection getConnection() {
        Connection con = null;
        Class c = null;
        try {
            // The newInstance() call is a work around for some
            // broken Java implementationsConnection con= Class.forName("com.mysql.jdbc.Driver").newInstance();

            c = Class.forName("com.mysql.jdbc.Driver");
            //out.println("classfound");
        } catch (Exception ex) {
            //out.println("class not found");
        }
        if (c != null) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/social_site?zeroDateTimeBehavior=convertToNull", "root", "");
                //out.println("success");
            } catch (SQLException s) {
                System.out.print("not found" + s);
                while ((s = s.getNextException()) != null) {
                    out.println(s.getMessage());
                }
            }
        }
        return con;
    }

    public static HashMap<String, String> getCookie(Cookie[] cookies)
    {
        String uname = "";
        String password = "";
        String fun = "";
        HashMap<String, String> map = null;
        if (cookies != null) {
            map = new HashMap<String, String>();
            for (Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie.getValue());
            }
            uname = map.get("email");
            System.out.println("uname<--" + uname);
            password = map.get("pw");
            System.out.println("pw<--" + password);
            fun = map.get("fun");
            System.out.println("fun<--" + fun);
            map = new HashMap<>();
            map.put("uname",uname);
            map.put("pw",password);
        }
        return map;
    }
    public static void addToken(String token,HashMap<String,String> user){
        Connection con=Utils.getConnection();
        String update = "UPDATE `usergeneral` SET `token`='"+token+"'WHERE`U_name`='"+user.get("uname")+"'";
        Statement st= null;
        try {
            st = con.createStatement();
            System.out.println("token added"+st.executeUpdate(update));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String findToken(String receiver){
        Connection con=Utils.getConnection();
        String query="select token from `usergeneral` where U_name='"+receiver+"'";
        Statement stat= null;
        try {
            stat = con.createStatement();
            ResultSet st= stat.executeQuery(query);
            st.next();
            return st.getString("token");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
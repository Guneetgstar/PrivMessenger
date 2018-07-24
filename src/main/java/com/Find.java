package com;

import util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Find extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con=Utils.getConnection();
        String keyword=request.getParameter("keyword");
        if(keyword!=null) {
            try {
                Statement statement = con.createStatement();
                String query = "Select * from usergeneral where U_name='" +keyword+ "'";
                ResultSet set=statement.executeQuery(query);
                if(set.first()){
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write(keyword);
                }
                else
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

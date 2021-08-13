package com.tuling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Connect {

    public static void main(String[] args) {
        Connection conn = null;

        try {
            String userName = "root";
            String password = "123456";

            String url = "jdbc:mysql://tlshopdb.com:3306/micromall?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            PreparedStatement preparedStatement=conn.prepareStatement("select * from cms_help");
            preparedStatement.execute();
            System.out.println("Database connection established");
        } catch (Exception e) {
            System.err.println("Cannot connect to database server");
            System.err.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Database Connection Terminated");
                } catch (Exception e) {}
            }
        }
    }
}
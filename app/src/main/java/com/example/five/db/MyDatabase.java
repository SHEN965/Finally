package com.example.five.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MyDatabase {
    static {
        try{
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/guzzi", "sa", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void close(ResultSet rs, PreparedStatement ps,Connection connection){
        try{
            if(rs!=null){
                rs.close();
            }
            if(ps!=null){
                ps.close();
            }
            if(connection!=null){
                ps.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

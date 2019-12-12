package com.example.five;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBUtil
{

    private static Connection getSQLConnection(String ip, String user, String pwd, String db)
    {
        Connection con = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + ip + ":1433/" + db, user, pwd);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return con;
    }

    public static String QuerySQL()
    {
        String result = "";
        try
        {
            Connection conn = getSQLConnection("10.148.56.114", "sa", "123456", "guzzi");
            String sql = "select * from goods";
            Statement stmt = conn.createStatement();//
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                String s1 = rs.getString("goodsname");
                String s2 = rs.getString("goodsprice");
                result += s1 + "  -  " + s2 + "\n";
                System.out.println(s1 + "  -  " + s2);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
            result += "异常!" + e.getMessage();
        }
        return result;
    }

    public static void main(String[] args)
    {
        QuerySQL();
    }
}

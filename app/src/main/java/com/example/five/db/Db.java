package com.example.five.db;
import com.example.five.entity.Production;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Db {
    private static String IP = "10.150.47.161";  //本机ip地址  而不是 127.0.0.1
    private static String DBName = "guzzi";  //数据库名
    private static String USER = "sa";  //账号
    private static String PWD = "123456";   //密码


    /** 创建数据库对象 */
    private static Connection getSQLConnection() {
        Connection con = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //加上 useunicode=true;characterEncoding=UTF-8 防止中文乱码
            con = DriverManager.getConnection("jdbc:jtds:sqlserver://" + IP + ":1433/" + DBName + ";useunicode=true;characterEncoding=UTF-8", USER, PWD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }


    public static List<Production> Query(String sql) {
        List<Production> result = new ArrayList();
        ResultSet rs = null;
        try {
            Connection conn = getSQLConnection();
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();//
            rs = stmt.executeQuery(sql);
            while (rs.next()){
                Production production = new Production();
                production.setProId(rs.getString("goodsid"));
                production.setProName(rs.getString("goodsname"));
                production.setProPrice(rs.getString("goodsprice"));
                production.setImg_url(rs.getString("imgurl"));
                result.add(production);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

//    /**
//     * 返回的List的[1]数据时List<Map>
//     *
//     * @param sql
//     * @return
//     * @throws
//     */
//    //region 传入sql,返回转换成List(查询)
//    public static List<Production> Query(String sql) {
//        List<Production> result = new ArrayList();
//        ResultSet rs = null;
//        try {
//            Connection conn = getSQLConnection();
//            conn.setAutoCommit(false);
//            Statement stmt = conn.createStatement();//
//            rs = stmt.executeQuery(sql);
//            result = convertList(rs);
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            // LogUtil.e("TestDButil", e.getMessage() + "," + sql); //本机打印日志
//            String res = "查询数据异常" + e.getMessage();
//            e.printStackTrace();
//            result.add(res);
//            return result;
//        } catch (Exception e) {
//            // LogUtil.e("TestDButil", e.getMessage() + "," + sql); //本机打印日志
//            String res = "无网络";
//            result.add(res);
//            return result;
//        }
//
//        return result;
//    }
//
//
//
//
//    private static String tostrings(String[] arr){
//        StringBuilder str5 = new StringBuilder();
//        for (String s : arr) {
//            str5.append(s);
//            str5.append(",");
//        }
//        return str5.toString();
//    }
//    /**
//     * 更新数据，新增，修改，删除
//     * 不成功则回滚
//     */
//    //region 更新数据，新增，修改，删除 返回int
//    public static int exeList(String[] sql) throws SQLException {
//        int rs = 0;
//        int index=0;
//        Connection conn = null;
//        try {
//
//            conn = getSQLConnection();
//            conn.setAutoCommit(false);  //不自动提交（默认执行一条成功则提交，现在都执行完成功才手动提交）
//            Statement stmt = conn.createStatement();//
//            for (String item : sql) {
//                rs += stmt.executeUpdate(item);
//                index++;
//            }
//            conn.commit();
//            stmt.close();
//            conn.close();//提交
//        } catch (SQLException e) {
//            //  LogUtil.e("TestDButil", e.getMessage() + ","+tostrings(sql) ); //本机打印日志
//            String res = "查询数据异常" + e.getMessage();
//            e.printStackTrace();
//            conn.rollback();
//            return 0;
//        } catch (Exception e) {
//            // LogUtil.e("TestDButil", e.getMessage() + "," ); //本机打印日志
//            if (conn != null) {
//                conn.rollback();
//            }
//            return 0;
//        }
//
//        return rs;
//    }
//    //endregion
//
//
//    //返回list,ResultSet转List<map>
//    public static List convertList(ResultSet rs) throws SQLException {
//        List all = new ArrayList();
//        List list = new ArrayList();
//        ResultSetMetaData md = rs.getMetaData();//获取键名
//        int columnCount = md.getColumnCount();//获取行的数量
//        String res = "ok";
//
//
//        all.add(res);
//        int coun = 0;
//        while (rs.next()) {
//            Map rowData = new HashMap();//声明Map
//            for (int i = 1; i <= columnCount; i++) {
//                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
//            }
//            list.add(rowData);
//            coun++;
//        }
//        if (coun < 1) {
//            all.set(0, "nodate");
//        }
//        all.add(list);
//
//        return all;
//    }
//    //endregion
//
//
//    /**
//     * 更新数据，新增，修改，删除
//     */
//    //region 更新数据，新增，修改，删除 返回int
//    public static int exesqlint(String sql) {
//        int rs = 0;
//        try {
//
//            Connection conn = getSQLConnection();
//            Statement stmt = conn.createStatement();//
//            rs = stmt.executeUpdate(sql);
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            //      LogUtil.e("TestDButil", e.getMessage() + "," + sql); //本机打印日志
//
//            String res = "查询数据异常" + e.getMessage();
//            e.printStackTrace();
//            return 0;
//        } catch (Exception e) {
//            // LogUtil.e("TestDButil", e.getMessage() + "," + sql); //本机打印日志
//
//            return 0;
//        }
//
//        return rs;
//    }
//    //endregion
//
//    //region 更新数据，新增，修改，删除 返回LIST数据
//    public static List exesql(String sql) {
//        List result = new ArrayList();
//        int rs = 0;
//        try {
//            String ress = "";
//            Connection conn = getSQLConnection();
//
//            Statement stmt = conn.createStatement();//
//            rs = stmt.executeUpdate(sql);
//            if (rs > 0) {
//                ress = "ok";
//            } else {
//                ress = "nodate";
//            }
//            result.add(ress);
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            String res = "请联系管理员，异常" + e.getMessage();
//            // LogUtil.e("TestDButil", e.getMessage() + "," + sql); //本机打印日志
//            e.printStackTrace();
//            result.add(res);
//            return result;
//        } catch (Exception e) {
//            String res = "无网络";
//            //  LogUtil.e("TestDButil", e.getMessage() + "," + sql); //本机打印日志
//
//            result.add(res);
//            return result;
//        }
//        return result;
//    }
//
//
//    //endregion
//
//
//    /**
//     * 查询，有无该条数据
//     *
//     * @param sql
//     * @return
//     * @throws
//     */
//    //region 查询，又多少条行数
//    public static int hasrows(String sql) {
//        int result = 0;
//
//        try {
//            Connection conn = getSQLConnection();
//
//            Statement stmt = conn.createStatement();//
//            ResultSet ss = stmt.executeQuery(sql);
//            if (!ss.next()) {
//                result = 0;
//            } else {
//                result = 1;
//            }
//            ss.close();
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            String res = "查询数据异常" + e.getMessage();
//            //        LogUtil.e("TestDButil", e.getMessage() + "," + sql); //本机打印日志
//
//            return -1;
//        } catch (Exception e) {
//            String res = "无网络";
//            //     LogUtil.e("TestDButil", e.getMessage() + "," + sql); //本机打印日志
//
//            return -1;
//
//        }
//        return result;
//    }
//    //endregion
//
//
//    //region 传入sql,返回转换成List(查询)
//    public static <T> List QueryT(String sql, T t) {
//        List result = new ArrayList();
//
//        ResultSet rs = null;
//        try {
//            Connection conn = getSQLConnection();
//
//            Statement stmt = conn.createStatement();//
//            rs = stmt.executeQuery(sql);
//            result = util(t, rs);
//
//            rs.close();
//            stmt.close();
//            conn.close();
//        } catch (SQLException e) {
//            //  LogUtil.e("TestDButil", e.getMessage() + "," + sql); //本机打印日志
//
////            String res = "查询数据异常" + e.getMessage();
////            e.printStackTrace();
//            String res = "nodate";
//            result.add(res);
//            return result;
//        } catch (Exception e) {
//            // LogUtil.e("TestDButil", e.getMessage() + "," + sql); //本机打印日志
//
//            String res = "无网络";
//            result.add(res);
//            return result;
//        }
//
//        return result;
//    }
//
//
//    /**
//     * ResultSet转List<T>
//     *
//     * @param t
//     * @param rs
//     * @return
//     * @throws
//     */
//    public static <T> List util(T t, ResultSet rs) throws Exception {
//        // 创建一个对应的空的泛型集合
//        List<T> list = new ArrayList<T>();
//        List ALL = new ArrayList();
//        // 反射出类类型（方便后续做操作）
//        Class c = t.getClass();
//        // 获得该类所有自己声明的字段，不问访问权限.所有。所有。所有
//        Field[] fs = c.getDeclaredFields();
//        int count = 0;
//        // 大家熟悉的操作，不用多说
//        ALL.add("nodate");
//        int ros = rs.getRow();
//        if (rs != null) {
//            while (rs.next()) {
//                count++;
//                if (count == 1) {
//                    ALL.set(0, "ok");
//                }
//                // 创建实例
//                t = (T) c.newInstance();
//                // 赋值
//                for (int i = 0; i < fs.length; i++) {
//                    /*
//                     * fs[i].getName()：获得字段名
//                     *
//                     * f:获得的字段信息
//                     */
//                    Field f = t.getClass().getDeclaredField(fs[i].getName());
//                    // 参数true 可跨越访问权限进行操作
//                    f.setAccessible(true);
//                    /*
//                     * f.getType().getName()：获得字段类型的名字
//                     */
//                    // 判断其类型进行赋值操作
//                    if (f.getType().getName().equals(String.class.getName())) {
//                        f.set(t, rs.getString(fs[i].getName()));
//                    } else if (f.getType().getName().equals(int.class.getName())) {
//                        f.set(t, rs.getInt(fs[i].getName()));
//                    }
//                }
//
//                list.add(t);
//            }
//        }
//
//        ALL.add((list));
//        // 返回结果
//        return ALL;
//    }
//
//    //endregion
//
//    /**
//     * List<Map<String, Object>>转List<T>
//     *
//     * @param list
//     * @param clazz
//     * @return
//     * @throws
//     */
//
//    public static <T> List<T> castMapToBean(List<Map<String, Object>> list, Class<T> clazz) throws Exception {
//        if (list == null || list.size() == 0) {
//            return null;
//        }
//        List<T> tList = new ArrayList<T>();
//        // 获取类中声明的所有字段
//        Field[] fields = clazz.getDeclaredFields();
//
//        T t;
//        for (Map<String, Object> map : list) {
//            // 每次都先初始化一遍,然后再设置值
//            t = clazz.newInstance();
//            for (Field field : fields) {
//                // 把序列化的字段去除掉
//                if (!"serialVersionUID".equals(field.getName())) {
//                    // 由于Field都是私有属性，所有需要允许修改
//                    field.setAccessible(true);
//
//                    // 设置值, 类型要和vo对应好,不然会报类型转换错误
//                    field.set(t, map.get(field.getName()));
//                }
//            }
//            tList.add(t);
//        }
//
//        return tList;
//    }

}

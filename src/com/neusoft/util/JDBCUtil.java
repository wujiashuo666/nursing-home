package com.neusoft.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.neusoft.common.Common;

/**
 * JDBC工具类：
 *  1.建立连接
 *  2.获取处理SQL命令的对象(PreparedStatement)
 *  3.把占位符替换成真实的值  preparedStatement.setObject(1, 值)
 *  4.执行SQL
 *  5.处理结果
 *  6.关闭连接
 */
public class JDBCUtil {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Statement statement;
    private static ResultSet resultSet;

    /**
     * 建立和数据库的连接
     */
    public static void initConnnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(Common.DB_URL, Common.DB_USER, Common.DB_PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("数据库连接失败", e);
        }
    }

    /**
     * 获取Statement对象
     */
    public static void initStatement() {
        try {
            statement = connection.createStatement();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 预处理SQL，创建PreparedStatement
     */
    public static void initPreparedStatement(String sql) {
        if (sql == null || "".equals(sql)) {
            return;
        }
        try {
            preparedStatement = connection.prepareStatement(sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把占位符替换成真实的值
     * @param list 参数列表，按SQL中?的顺序
     */
    public static void initPreparedSql(List<Object> list) {
        try {
            for (int i = 0; i < list.size(); i++) {
                preparedStatement.setObject(i + 1, list.get(i));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行查询，返回结果集
     */
    public static ResultSet executeQuery() {
        try {
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行增删改，返回受影响行数
     */
    public static int executeUpdate() {
        try {
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回当前连接(用于事务控制 setAutoCommit/commit/rollback)
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * 关闭打开的资源
     */
    public static void close() {
        try {
            if (resultSet != null) 
                resultSet.close();
            if (preparedStatement != null) 
                preparedStatement.close();
            if (statement != null) 
                statement.close();
            if (connection != null) 
                connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            resultSet = null;
            preparedStatement = null;
            statement = null;
            connection = null;
        }
    }
}

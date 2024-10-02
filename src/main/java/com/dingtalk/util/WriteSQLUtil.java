package com.dingtalk.util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WriteSQLUtil {

    private static final String DB_USERNAME = "root";
    //    private static final String DB_URL = "jdbc:mysql://127.0.0.1:9532/bd?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/AIInspection_project?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false";
    //    private static final String DB_PASSWORD = "ai123456";
    private static final String DB_PASSWORD = "abcd0127";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    // 批量插入
    static void batchInsert(Connection connection, List<String[]> resultList) {
        PreparedStatement preparedStatement = null;

        try {
            connection.setAutoCommit(false); // 关闭自动提交

            // 创建SQL插入语句
            String sql = "INSERT INTO department_list (department_leave, department_id, department_name) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            // 添加批处理
            for (int i = 0; i < resultList.size(); i++) {
                String[] strings = resultList.get(i);
                preparedStatement.setInt(1, Integer.parseInt(strings[0]));
                preparedStatement.setString(2, strings[1]);
                preparedStatement.setString(3, strings[2]);
                preparedStatement.addBatch(); // 添加到批处理

                // 每100条执行一次
                if (i % 100 == 0) {
                    preparedStatement.executeBatch();
                }
            }

            // 执行剩余的批处理
            preparedStatement.executeBatch();

            // 提交事务
            connection.commit();
            System.out.println("批量插入成功!");

        } catch (SQLException e) {
            try {
                connection.rollback(); // 回滚事务
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // 关闭PreparedStatement
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void batchInsertRole(Connection connection, List<String[]> resultData) {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO role_list (user_id, user_name, user_role, user_shop_name) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE user_name = VALUES(user_name), user_role = VALUES(user_role), user_shop_name = VALUES(user_shop_name)";
//            String sql = "INSERT IGNORE INTO role_list (user_id, user_name, user_role, user_shop_name) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false); // 关闭自动提交
            for (String[] resultDatum : resultData) {
                preparedStatement.setString(1, resultDatum[0]); // id
                preparedStatement.setString(2, resultDatum[1]); // name
                preparedStatement.setString(3, resultDatum[2]); // name
                preparedStatement.setString(4, resultDatum[3]); // name
                preparedStatement.addBatch(); // 添加到批处理
            }
            preparedStatement.executeBatch(); // 执行批处理
            connection.commit(); // 提交事务
            System.out.println("批量插入成功");

        } catch (SQLException e) {
            try {
                connection.rollback(); // 回滚事务
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // 关闭PreparedStatement
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String[]> getDepartMentID(Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            String sql = "select department_id,department_name from department_list where department_leave > 2";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<String[]> departmentLists = new ArrayList<>();
            while (resultSet.next()) {
                long departmentId = resultSet.getLong("department_id");
                String departmentName = resultSet.getString("department_name");
                String[] departmentList = {String.valueOf(departmentId), departmentName};
                departmentLists.add(departmentList);
            }
            return departmentLists;
        } catch (SQLException e) {
            try {
                connection.rollback(); // 回滚事务
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return null;
        } finally {
            // 关闭PreparedStatement
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteUnusedIds(Connection connection,List<String> idsToKeep) {
        String placeholders = String.join(",", idsToKeep.stream().map(id -> "?").toArray(String[]::new));
        String sql = "DELETE FROM role_list WHERE user_id NOT IN (" + placeholders + ")";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            // 设置参数
            for (int i = 0; i < idsToKeep.size(); i++) {
                preparedStatement.setString(i + 1, idsToKeep.get(i));
            }

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("删除了 " + rowsAffected + " 行记录。");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 关闭 JDBC 资源的方法
    public static void closeResources(Connection connection) {
        // 关闭 Connection
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing Connection: " + e.getMessage());
            }
        }
    }
}

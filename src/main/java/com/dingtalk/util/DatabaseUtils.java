package com.dingtalk.util;

import java.sql.*;

public class DatabaseUtils {
    private static final String DB_USERNAME = "root";
            private static final String DB_URL = "jdbc:mysql://127.0.0.1:9532/bd?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false";
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/AIInspection_project?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false";
            private static final String DB_PASSWORD = "ai123456";
//    private static final String DB_PASSWORD = "abcd0127";

    public static void writeToDatabase(String tableName, String[] columnNames, String[] values) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // 注册JDBC驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 建立数据库连接
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // 构建插入语句
            StringBuilder sql = new StringBuilder("INSERT INTO ");
            sql.append(tableName).append(" (");
            for (int i = 0; i < columnNames.length; i++) {
                sql.append(columnNames[i]);
                if (i < columnNames.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append(") VALUES (");
            for (int i = 0; i < values.length; i++) {
                sql.append("?");
                if (i < values.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");

            // 创建PreparedStatement并设置参数值
            stmt = conn.prepareStatement(sql.toString());
            for (int i = 0; i < values.length; i++) {
                if ("itemCount".equals(columnNames[i]) || "published".equals(columnNames[i]) || "cardId".equals(columnNames[i])||"cardStatusType".equals(columnNames[i])) {
//                    System.out.println("columnName:"+columnNames[i] + ",value:" + values[i]);
                    stmt.setInt(i + 1, Integer.parseInt(values[i]));
                } else if (columnNames[i].contains("Time")) {
                    stmt.setObject(i + 1, Timestamp.valueOf(values[i]));
                } else stmt.setObject(i + 1, values[i]);
            }

            // 执行插入操作
            stmt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


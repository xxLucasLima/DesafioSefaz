package br.com.sefaz.teste.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCFactory {

    private static Connection con = null;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.hsqldb.jdbc.JDBCDriver");
        System.out.println("HSQLDB JDBCDriver Loaded");
        con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001/db_sefaz;hsqldb.write_delay=false",
                "SA", "");
        System.out.println("HSQLDB Connection Created");

        return con;
    }

    public static void close(ResultSet rs, PreparedStatement st, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
            }
        }
    }

    public static void close(PreparedStatement st, Connection conn) {
        close(null, st, conn);
    }

    public static void close(Connection conn) {
        close(null, null, conn);
    }

    public static void close(PreparedStatement st) {
        close(null, st, null);
    }
}

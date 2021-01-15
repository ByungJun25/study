package com.bj25.study.java.exceptions.h2example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Example {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:h2:mem:";
        Connection con = DriverManager.getConnection(url);
        con.setAutoCommit(false);

        try (Statement stm = con.createStatement()) {
            stm.execute("CREATE TABLE TEST (id INTEGER not NULL, value VARCHAR(255), PRIMARY KEY(id))");
            Example.insert(con, 1, "test1");
            Example.createRuntimException();
            Example.insert(con, 2, "test2");
            con.commit();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (RuntimeException e) {
            System.out.println("RuntimeException 발생");
            con.rollback();
        } catch (Exception exception) {
            System.out.println("Exception 발생");
        }

        try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery("SELECT * FROM TEST")) {
            while (rs.next()) {
                String value = rs.getString("value");
                System.out.println("Value: " + value);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (RuntimeException e) {
            System.out.println("RuntimeException 발생");
        } catch (Exception exception) {
            System.out.println("Exception 발생");
        }

        try {
            con.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public static void insert(Connection con, int id, String value) throws SQLException {
        final String query = "INSERT INTO TEST VALUES (" + id + ", '" + value + "')";
        con.prepareStatement(query).executeUpdate();
    }

    public static void insert(Statement stm, int id, String value) throws SQLException {
        stm.execute("INSERT INTO TEST VALUES (" + id + ", '" + value + "')");
    }

    public static void createRuntimException() {
        throw new RuntimeException();
    }

    public static void createException() throws Exception {
        throw new Exception();
    }
}

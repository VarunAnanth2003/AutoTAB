package com.flns.autotab;
import java.sql.*;
import java.util.HashSet;

public class ConnectionHandler {
    private static Connection conn = null;
    public static boolean establishConnection() {
        String url = "jdbc:mysql://tab-database.csgzfxbe495j.us-east-1.rds.amazonaws.com:2003/tab-schema?user=XXX&password=XXX";
        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } 
    }
    public static boolean sendData(TAB t) {
        Statement basicStatement;
        try {
            basicStatement = (Statement) conn.createStatement();
            String testqString = "INSERT INTO tab_table VALUES ('" + t.getID() + "', '" + t.getName() + "', '" + TABPrinter.printTAB(t) + "');";
            basicStatement.execute(testqString);
            return true;
        } catch (SQLException e) {
            return false;
        } 
    }
    public static boolean sendData(String ID, String name, String tabString) {
        Statement basicStatement;
        try {
            basicStatement = (Statement) conn.createStatement();
            String testqString = "DELETE FROM tab_table WHERE (tab_ID='" + ID + "');";
            basicStatement.execute(testqString);
            System.out.println("Deleted");
            basicStatement = (Statement) conn.createStatement();
            testqString = "INSERT INTO tab_table VALUES ('" + ID + "', '" + name + "', '" + tabString + "');";
            basicStatement.execute(testqString);
            System.out.println("Added");
            return true;
        } catch (SQLException e) {
            return false;
        } 
    }
    public static boolean removeData(String ID) {
        Statement basicStatement;
        try {
            basicStatement = (Statement) conn.createStatement();
            String testqString = "DELETE FROM tab_table WHERE (tab_ID='" + ID + "');";
            basicStatement.execute(testqString);
            return true;
        } catch (SQLException e) {
            return false;
        } 
    }
    public static String[][] pullData() {
        Statement basicStatement;
        try {
            basicStatement = (Statement) conn.createStatement();
            String testqString = "SELECT * FROM tab_table";
            ResultSet rs = basicStatement.executeQuery(testqString);
            HashSet<String[]> hset = new HashSet<>();
            while(rs.next()) {
                String ID = rs.getString("tab_ID");
                String name = rs.getString("tab_name");
                String tabString = rs.getString("tab_string");
                String[] temp = {ID, name, tabString};
                hset.add(temp);
            }
            String[][] ret_val = new String[hset.size()][3];
            int counter = 0;
            for(String[] s : hset) {
                ret_val[counter] = s;
                counter++;
            }
            return ret_val;
        } catch (SQLException e) {
            return null;
        } 
        
    }
    public static void closeConnection() {
        try {
            if (conn != null)
               conn.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }  
    }
}

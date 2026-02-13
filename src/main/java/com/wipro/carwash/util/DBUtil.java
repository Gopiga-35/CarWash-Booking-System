package com.wipro.carwash.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {

    public static Connection getDBConnection() {

        Connection connection = null;

        try {
            
            Class.forName("oracle.jdbc.driver.OracleDriver");

           
            String url = "jdbc:oracle:thin:@//localhost:1521/XE";
            String user = "system";
            String password = "Ramachandran_35";

            
            connection = DriverManager.getConnection(url, user, password);

            
            connection.setAutoCommit(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}

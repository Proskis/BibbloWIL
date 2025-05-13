
package com.mycompany.bibbliotekcaseWIL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jonathan Hedemalm
 */
public class DatabaseConnection {
    
    
    static String DATABASE_URL = "jdbc:mysql://localhost:3306/bibliotek";
    static String username = "root";
    static String password = "WIL123";
    
    public static Connection getConnection() {
        try {
           return DriverManager.getConnection(DATABASE_URL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
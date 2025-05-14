package com.mycompany.bibbliotekcaseWIL;

import com.mycompany.bibbliotekcaseWIL.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Jonathan Hedemalm
 */
public class LoginLogic {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    
    private PreparedStatement verifyUserLogin;
    private PreparedStatement verifyStaffLogin;
    private PreparedStatement verifyManagerLogin;
    private PreparedStatement getStaffID;
    private PreparedStatement getStaffFullName;
    private PreparedStatement getUserFullName;
    private PreparedStatement getUserID;
    
    private Connection connection;
    
    
        public LoginLogic() {
            
        try { 
            connection = DatabaseConnection.getConnection();
            
            getUserID = connection.prepareStatement
            ("SELECT UserId from user WHERE Username = ?");
            
            getStaffID = connection.prepareStatement
            ("SELECT staffID FROM staff WHERE Username = ?");
            
            getStaffFullName = connection.prepareStatement(
            "SELECT FirstName, LastName FROM staff WHERE Username = ?");
            
            getUserFullName = connection.prepareStatement(
            "SELECT FirstName, LastName FROM user WHERE Username = ?");
            
            verifyManagerLogin = connection.prepareStatement
            ("SELECT COUNT(*) FROM manager WHERE staffID = ?");
            
            verifyStaffLogin = connection.prepareStatement 
            ("SELECT COUNT(1) FROM staff WHERE Username = ? AND Password = ?");
            
            verifyUserLogin = connection.prepareStatement
            ("SELECT COUNT(1) FROM user WHERE Username = ? AND Password = ?");
            
            
        
        } catch (SQLException e) {
            e.printStackTrace();
    }
        
}
     /**
     * Verifierar inloggning för vanlig användare.
     *
     * @param username Användarnamn
     * @param password Lösenord
     * @return true om inloggningen är korrekt, annars false
     */
        public boolean validateUserLogin(String username, String password) {
            try {
                verifyUserLogin.setString(1, username);
                verifyUserLogin.setString(2, password);
                
                ResultSet resultSet = verifyUserLogin.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 1;
                } 
            } catch (SQLException e) {
                 e.printStackTrace();
            }
            return false;
        }
        
     /**
     * Verifierar inloggning för personal (staff).
     *
     * @param username Användarnamn
     * @param password Lösenord
     * @return true om inloggningen är korrekt, annars false
     */
        public boolean validateStaffLogin(String username, String password) {
            try {
                verifyStaffLogin.setString(1, username);
                verifyStaffLogin.setString(2, password);
                
                ResultSet resultSet = verifyStaffLogin.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 1;
                } 
            } catch (SQLException e) {
                 e.printStackTrace();
            }
            return false;
        }
        
     /**
     * Kontrollerar om en viss personal är chef.
     *
     * @param staffID ID för personalen
     * @return true om personen är chef, annars false
     */
        public boolean isManager(int staffID) {
            try {
                verifyManagerLogin.setInt(1, staffID);
                
                ResultSet resultSet = verifyManagerLogin.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getInt(1) == 1;
                } 
            } catch (SQLException e) {
                 e.printStackTrace();
            }
            return false;
        }
          public int getUserID(String username) {
            try {
                getUserID.setString(1, username);
                ResultSet resultSet = getUserID.executeQuery();

                if (resultSet.next()) {
                return resultSet.getInt("UserID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }
    return -1;
          }
        public int getStaffID(String username) {
            try {
                getStaffID.setString(1, username);
                ResultSet resultSet = getStaffID.executeQuery();

                if (resultSet.next()) {
                return resultSet.getInt("staffID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }
    return -1;
}
        public String getStaffFullName(String username) {
    try {
        getStaffFullName.setString(1, username);
        ResultSet resultSet = getStaffFullName.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("FirstName") + " " + resultSet.getString("LastName");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return username;
}

        public String getUserFullName(String username) {
    try {
        getUserFullName.setString(1, username);
        ResultSet resultSet = getUserFullName.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("FirstName") + " " + resultSet.getString("LastName");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return username;
}
}
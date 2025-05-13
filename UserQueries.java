package com.mycompany.bibbliotekcaseWIL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathan Hedemalm
 */

public class UserQueries {
    
    
    private Connection connection;
    private PreparedStatement selectAllUsers;
    private PreparedStatement selectUsersByLastName;
    private PreparedStatement insertNewUser;
    private PreparedStatement deleteUser;
   
        
    public UserQueries() {
        try {
            connection = DatabaseConnection.getConnection();

            
            selectAllUsers = connection.prepareStatement
         ("SELECT * FROM User ORDER BY LastName, FirstName");

            
            selectUsersByLastName = connection.prepareStatement
         ("SELECT * FROM User WHERE LastName LIKE ? ORDER BY LastName, FirstName");
            
            insertNewUser = connection.prepareStatement
         ("INSERT INTO user " + "(FirstName, LastName, Email, PhoneNo,Username, Password, UserTypeID) " + "VALUES (?, ?, ?, ?, ?, ?, ?)");
            
            deleteUser = connection.prepareStatement
          ("DELETE FROM User WHERE UserID = ?");

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
        
    /**
     * Hämtar en lista med alla användare i databasen.
     * 
     * @return En lista av User-objekt eller null vid fel.
     */
    public List<User> getAllUsers() {
            try (ResultSet resultSet = selectAllUsers.executeQuery()) {
                List<User> results = new ArrayList<User>();
                
                while (resultSet.next()) {
                    results.add(new User (
                        resultSet.getInt("UserID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Email"),
                        resultSet.getString("PhoneNo"),
                        resultSet.getString("Username"),
                        resultSet.getString("password"),
                        resultSet.getInt("UserTypeID")));
                }
                return results;
                
            }   catch (SQLException e) {
                e.printStackTrace();
            }
                return null;
        }
        
    /**
     * Hämtar användare som matchar ett visst efternamn.
     * 
     * @param lastName Efternamnet att söka efter.
     * @return En lista med användare som matchar efternamnet eller null vid fel.
     */
    public List<User> getUsersByLastName(String lastName) {
            try {
                selectUsersByLastName.setString(1, lastName);
                 
                try (ResultSet resultSet = selectUsersByLastName.executeQuery()) {
                    List<User> results = new ArrayList<User>();
                
                    while (resultSet.next()) {
                        results.add(new User (
                            resultSet.getInt("UserID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Email"),
                            resultSet.getString("PhoneNo"),
                            resultSet.getString("Username"),
                            resultSet.getString("Password"),
                            resultSet.getInt("UserTypeID")));
                }
                return results;
                }   
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    
        
        
     /**
     * Lägger till en ny användare i databasen.
     * 
     * @param firstName Förnamn
     * @param lastName Efternamn
     * @param email E-postadress
     * @param phoneNo Telefonnummer
     * @param username Användarnamn
     * @param password Lösenord
     * @param userTypeID Typ av användare
     * @return Antal rader som påverkades (1 vid lyckad insättning, annars 0)
     */

    public int addUser(String firstName, String lastName, String email, String phoneNo, String username, String password, int userTypeID) {
           try {
            insertNewUser.setString(1, firstName);
            insertNewUser.setString(2, lastName);
            insertNewUser.setString(3, email);
            insertNewUser.setString(4, phoneNo);
            insertNewUser.setString(5, username);
            insertNewUser.setString (6, password);
            insertNewUser.setInt(7, userTypeID);
            
            return insertNewUser.executeUpdate();
        }
           catch (SQLException e) {
               e.printStackTrace();
               return 0;
           }
        }
    
    /**
     * Tar bort en användare från databasen baserat på dess ID.
     * 
     * @param userID ID för användaren som ska tas bort.
     * @return Antal rader som påverkades (1 vid lyckad borttagning, annars 0)
     */
    public int deleteUser(int userID) {
        try {
         deleteUser.setInt(1, userID);
         return deleteUser.executeUpdate(); 
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    } 
        
    /**
     *
     */
    public void close() {
            try {
                connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
    }
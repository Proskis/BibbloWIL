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
public class StaffQueries {
    private Connection connection;
    private PreparedStatement selectAllStaff;
    private PreparedStatement selectStaffByLastName;
    private PreparedStatement insertNewStaff;
    private PreparedStatement deleteStaff;
    
    
   
        
    public StaffQueries() {
        try {
            connection = DatabaseConnection.getConnection();

            
            selectAllStaff = connection.prepareStatement
         ("SELECT * FROM Staff ORDER BY LastName, FirstName");

            selectStaffByLastName = connection.prepareStatement
         ("SELECT * FROM Staff WHERE LastName LIKE ? ORDER BY LastName, FirstName");
            
            insertNewStaff = connection.prepareStatement
         ("INSERT INTO Staff " + "(FirstName, LastName, Email, PhoneNo,Username, Password) " + "VALUES (?, ?, ?, ?, ?, ?)");

            deleteStaff = connection.prepareStatement
         ("DELETE FROM Staff WHERE StaffID = ?");
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
        
     /**
     * Hämtar en lista med all personal från databasen.
     * 
     * @return En lista med Staff-objekt, eller null om ett fel inträffar.
     */
    public List<Staff> getAllStaff() {
            try (ResultSet resultSet = selectAllStaff.executeQuery()) {
                List<Staff> results = new ArrayList<Staff>();
                
                while (resultSet.next()) {
                    results.add(new Staff (
                        resultSet.getInt("StaffID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Email"),
                        resultSet.getString("PhoneNo"),
                        resultSet.getString("Username"),
                        resultSet.getString("password")));
                }
                return results;
                
            }   catch (SQLException e) {
                e.printStackTrace();
            }
                return null;
        }
        
    /**
     * Hämtar personal vars efternamn matchar det angivna namnet.
     * 
     * @param lastName Efternamn att söka efter.
     * @return En lista med Staff-objekt, eller null om ett fel inträffar.
     */
    public List<Staff> getStaffByLastName(String lastName) {
            try {
                selectStaffByLastName.setString(1, lastName);
                 
                try (ResultSet resultSet = selectStaffByLastName.executeQuery()) {
                    List<Staff> results = new ArrayList<Staff>();
                
                    while (resultSet.next()) {
                        results.add(new Staff (
                            resultSet.getInt("StaffID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getString("Email"),
                            resultSet.getString("PhoneNo"),
                            resultSet.getString("Username"),
                            resultSet.getString("Password")));
                }
                return results;
                }   
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    
        
        
     /**
     * Lägger till en ny personalpost i databasen.
     * 
     * @param firstName Förnamn
     * @param lastName Efternamn
     * @param email E-postadress
     * @param phoneNo Telefonnummer
     * @param username Användarnamn
     * @param password Lösenord
     * @return Antal rader som påverkades (1 vid lyckad insättning, annars 0)
     */
    public int addStaff(String firstName, String lastName, String email, String phoneNo, String username, String password) {
           try {
            insertNewStaff.setString(1, firstName);
            insertNewStaff.setString(2, lastName);
            insertNewStaff.setString(3, email);
            insertNewStaff.setString(4, phoneNo);
            insertNewStaff.setString(5, username);
            insertNewStaff.setString (6, password);
            
            return insertNewStaff.executeUpdate();
        }
           catch (SQLException e) {
               e.printStackTrace();
               return 0;
           }
        }
    
    /**
     * Tar bort en personal från databasen baserat på ID.
     * 
     * @param staffID ID för personalen som ska tas bort.
     * @return Antal rader som påverkades (1 vid lyckad borttagning, annars 0)
     */
    public int deleteStaff(int StaffID) {
        try {
         deleteStaff.setInt(1, StaffID);
         return deleteStaff.executeUpdate();
 
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
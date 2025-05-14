
package com.mycompany.bibbliotekcaseWIL;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author William Visscher
 */
public class ReturnLoanController {
    
    private Parent root;
    private Scene scene;
    private Stage stage;
    
    static String DATABASE_URL = "jdbc:mysql://localhost:3306/bibliotek";
    static String username = "root";
    static String password = "WIL123";
    
    private Connection connection;
    
    private PreparedStatement GetMyLoansArea;
    
    @FXML
    private TextField loanIdField;
    
    @FXML
    private TextArea MyLoansArea;
    private int userId;
    
    @FXML
    public void initialize() {
       LoadCurrentLoans();
    }
    
    
    public void LoadCurrentLoans() {
         MyLoansArea.clear();
    userId = Session.userID;

    try {
        connection = DatabaseConnection.getConnection();

        // Query loans based on UserID instead of LoanID
        String sql = "SELECT cl.*, l.Title FROM currentloan cl JOIN loan l ON cl.LoanId = l.LoanId WHERE cl.UserId = ?";
        GetMyLoansArea = connection.prepareStatement(sql);
        GetMyLoansArea.setInt(1, userId);

        ResultSet resultSet = GetMyLoansArea.executeQuery();

        StringBuilder loansText = new StringBuilder();
        while (resultSet.next()) {
            String Title = resultSet.getString("Title");
            int UserId = resultSet.getInt("UserId");
            int LoanId = resultSet.getInt("LoanId");
            Date BorrowDate = resultSet.getDate("BorrowDate");
            Date DueDate = resultSet.getDate("DueDate");

            loansText.append("Title: ").append(Title).append("\n")
                     .append("LoanID: ").append(LoanId).append("\n")
                     .append("UserID: ").append(UserId).append("\n")
                     .append("BorrowDate: ").append(BorrowDate).append("\n")
                     .append("DueDate: ").append(DueDate).append("\n")
                     .append("--------\n");
        }

        if (loansText.length() == 0) {
            MyLoansArea.setText("Inga aktuella lån hittades för användare med ID: " + userId);
        } else {
            MyLoansArea.setText(loansText.toString());
            
        }

    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Fel vid hämtning av lån", "Kunde inte hämta aktuella lån.");
    }
}

    @FXML
    void ReturnLoanItem(ActionEvent event) {
        
        String loanId = loanIdField.getText().trim();
        
        if (loanId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error:", "loanID är nödvändigt för att returnera ett lån");
           return;
        }
        String sql = "CALL bibliotek.return_loan(?)";
    try (Connection conn = DriverManager.getConnection(DATABASE_URL, username, password);
         CallableStatement stmt = conn.prepareCall(sql)) {

        stmt.setString(1, loanId);
        

        stmt.execute();

        showAlert(Alert.AlertType.INFORMATION, "Success", "lånet med LoanId: " + loanId + ". är returnerad");
        loanIdField.clear();
        LoadCurrentLoans();

    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "Det finns inget pågående lån med LoanId: " + loanId);
    }
    
    }
    @FXML
    private AnchorPane scenePane;
    
     public void OpenLoanReturnLoanPage(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoanReturnLoanScene.fxml"));
        
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    public void logout(ActionEvent event) throws IOException {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are logging out");
        alert.setContentText("Are you sure? ");
        
        if(alert.showAndWait().get() == ButtonType.OK) {
            
            Session.isManager = false;
            Session.isStaff = false;
            Session.isUser = false;
            Session.loggedInName = null;
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScene.fxml"));
        
            root = loader.load();
            stage = (Stage) scenePane.getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
            System.out.println("Logged out!");
}
    } 
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
}
}
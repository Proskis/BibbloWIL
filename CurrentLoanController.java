
package com.mycompany.bibbliotekcaseWIL;

import static com.mycompany.bibbliotekcaseWIL.ReturnLoanController.DATABASE_URL;
import static com.mycompany.bibbliotekcaseWIL.ReturnLoanController.password;
import static com.mycompany.bibbliotekcaseWIL.ReturnLoanController.username;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CurrentLoanController {
    
    private Parent root;
    private Scene scene;
    private Stage stage;
    
    @FXML 
    private  AnchorPane scenePane;

    @FXML
    private Button currentLoanBack;
    
    @FXML
    private TextArea loanListArea;

    @FXML
    private CheckBox checkBoxID;
    
     @FXML
    private TextField loanIdField;
    
    //ändra till vad ni har på eran dator
    private final String url = "jdbc:mysql://localhost:3306/bibliotek";
    private final String user = "root";
    private final String password = "WIL123";
    
    FXMLLoader loader;
    
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
        loanListArea.clear();
        loadCurrentLoans();

    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "Det finns inget pågående lån med LoanId: " + loanId);
    }
    }
    
     @FXML
    private void backButton(ActionEvent event)throws IOException {
        
       if (Session.isManager == true){
                     loader = new FXMLLoader(getClass().getResource("ManagerMainScene.fxml"));
                } 
       else {
                     loader = new FXMLLoader(getClass().getResource("StaffMainScene.fxml"));
                }
    
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
     
}

    @FXML
    public void initialize() {
        loadCurrentLoans();
    }

    @FXML
    private void checkbox() {
        if (checkBoxID.isSelected()) {
            loadOverdueItems();
        } else {
            loadCurrentLoans();
        }
    }

    private void loadCurrentLoans() {
        String query = "SELECT * FROM currentloan";
        runQueryAndDisplay(query, false);
    }

    private void loadOverdueItems() {
        String call = "{ call overdue_items() }";
        runQueryAndDisplay(call, true);
    }

    private void runQueryAndDisplay(String sql, boolean isOverdue) {
        loanListArea.clear();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            ResultSet rs;
            if (sql.trim().toLowerCase().startsWith("select")) {
                rs = stmt.executeQuery(sql);
            } else {
                CallableStatement cs = conn.prepareCall(sql);
                rs = cs.executeQuery();
            }

            StringBuilder sb = new StringBuilder();

            while (rs.next()) {
                if (!isOverdue) {
                    // Format for currentLoan
                    int currentLoanId = rs.getInt("CurrentLoanId");
                    int userId = rs.getInt("UserId");
                    int loanId = rs.getInt("LoanId");
                    Date borrowDate = rs.getDate("BorrowDate");
                    Date dueDate = rs.getDate("DueDate");

                    sb.append("Current Loan ID: ").append(currentLoanId).append("\n");
                    sb.append("User ID: ").append(userId).append("\n");
                    sb.append("Loan ID: ").append(loanId).append("\n");
                    sb.append("Borrow Date: ").append(borrowDate).append("\n");
                    sb.append("Due Date: ").append(dueDate).append("\n");
                    sb.append("--------\n");

                } else {
                    // Format for overdue_items
                    int loanId = rs.getInt("LoanId");
                    int userId = rs.getInt("UserID");
                    Date borrowDate = rs.getDate("BorrowDate");
                    Date dueDate = rs.getDate("DueDate");
                    int daysLate = rs.getInt("DaysLate");
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    String email = rs.getString("Email");
                    String phoneNo = rs.getString("PhoneNo");
                    int itemId = rs.getInt("ItemID");
                    String title = rs.getString("Title");

                    sb.append("Loan ID: ").append(loanId).append("\n");
                    sb.append("User ID: ").append(userId).append("\n");
                    sb.append("Borrow Date: ").append(borrowDate).append("\n");
                    sb.append("Due Date: ").append(dueDate).append("\n");
                    sb.append("Days Late: ").append(daysLate).append("\n");
                    sb.append("First Name: ").append(firstName).append("\n");
                    sb.append("Last Name: ").append(lastName).append("\n");
                    sb.append("Email: ").append(email).append("\n");
                    sb.append("Phone No: ").append(phoneNo).append("\n");
                    sb.append("Item ID: ").append(itemId).append("\n");
                    sb.append("Title: ").append(title).append("\n");
                    sb.append("--------\n");
                }
            }

            loanListArea.setText(sb.toString());

        } catch (SQLException e) {
            loanListArea.setText("Fel: " + e.getMessage());
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
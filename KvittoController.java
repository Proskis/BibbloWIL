package com.mycompany.bibbliotekcaseWIL;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class KvittoController {

    private Parent root;
    private Scene scene;
    private Stage stage;
    
    static String DATABASE_URL = "jdbc:mysql://localhost:3306/bibliotek";
    static String username = "root";
    static String password = "WIL123";
    
    private Connection connection;
    
    @FXML
    private TextArea KvittoTextField;
    
    @FXML
    public void initialize() {
    getKvittoInfo();
}
    
    public void getKvittoInfo() {
    String query = "SELECT LoanID, BorrowDate, DueDate, FirstName, LastName, Title, UserID, ItemID " +
                   "FROM loan ORDER BY LoanID DESC LIMIT 1";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL, username, password);
         var stmt = conn.createStatement();
         var rs = stmt.executeQuery(query)) {

        if (rs.next()) {
            int loanID = rs.getInt("LoanID");
            String borrowDate = rs.getString("BorrowDate");
            String dueDate = rs.getString("DueDate");
            String firstName = rs.getString("FirstName");
            String lastName = rs.getString("LastName");
            String title = rs.getString("Title");
            int userID = rs.getInt("UserID");
            int itemID = rs.getInt("ItemID");

            String kvitto = String.format(
                "Loan ID: %d\n" +
                "Name: %s %s\n" +
                "Title: %s\n" +
                "Borrow Date: %s\n" +
                "Due Date: %s\n" +
                "User ID: %d\n" +
                "Item ID: %d",
                loanID, firstName, lastName, title, borrowDate, dueDate, userID, itemID
            );

            KvittoTextField.setText(kvitto);
        } else {
            KvittoTextField.setText("");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load latest loan info.");
    }
}
        private void showAlert(Alert.AlertType type, String title, String content) {
         Alert alert = new Alert(type);
         alert.setTitle(title);
         alert.setHeaderText(null);
         alert.setContentText(content);
         alert.showAndWait();
}

     public void OpenLoanPage(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoanScene.fxml"));
        
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
}
     
}

package com.mycompany.bibbliotekcaseWIL;

import java.io.IOException;
import java.sql.CallableStatement;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
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
    
    @FXML
    private TextField loanIdField;
    
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
        

    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "Det finns inget pågående lån med LoanId: " + loanId);
    }
    }

        public void SwitchToUserList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserListScene.fxml"));
        
        root = loader.load();
        stage = (Stage) scenePane.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("Logged out!");
            stage.close(); 
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
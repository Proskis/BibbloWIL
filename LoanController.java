
package com.mycompany.bibbliotekcaseWIL;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 *
 * @author William Visscher
 */
public class LoanController {
    private Parent root;
    private Scene scene;
    private Stage stage;
    
    static String DATABASE_URL = "jdbc:mysql://localhost:3306/bibliotek";
    static String username = "root";
    static String password = "WIL123";
    
    private Connection connection;
    
    @FXML
    public TextField itemIdField;

    @FXML
    private TextField userIdField;
    
    @FXML
    private void initialize(){
        setUserIdField();
    }
    
    public void setUserIdField(){
    userIdField.setText(String.valueOf(Session.userID));
}
    
     @FXML
    public void LoanItem(ActionEvent event) throws IOException { 
        String itemId = itemIdField.getText().trim();
        String userId = userIdField.getText().trim();
     
        if (itemId.isEmpty() || userId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error:", "Item ID och User ID är nödvändigt för ett lån");
           return;
        }
        String sql = "CALL bibliotek.new_loan(?, ?)";
    try (Connection conn = DriverManager.getConnection(DATABASE_URL, username, password);
         CallableStatement stmt = conn.prepareCall(sql)) {

        stmt.setString(1, itemId);
        stmt.setString(2, userId);

        stmt.execute();

        showAlert(Alert.AlertType.INFORMATION, "Success", "Tack för ditt lån!");
      /*  itemIdField.clear();
        userIdField.clear();*/
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("KvittoScene.fxml"));
        
        root = loader.load();
        stage = (Stage) scenePane.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    } catch (SQLException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", "Användare finns inte, itemId finns inte eller så har användaren uppnått max antal lån");
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
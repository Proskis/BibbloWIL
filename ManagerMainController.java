package com.mycompany.bibbliotekcaseWIL;


import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Jonathan Hedemalm
 */
public class ManagerMainController {
     @FXML
    private TextField searchTextField;
     
    @FXML
    private Label UserNameField;

    @FXML 
    private  AnchorPane scenePane;
    
    private Parent root;
    private Scene scene;
    private Stage stage;
    
    /**
     *
     * @param event
     * @throws IOException
     */
    public void search(ActionEvent event) throws IOException {
        String searchText = searchTextField.getText();
    }
     
    /**
     * laddar och visar scenen för användarlistan
     *  
     * @param event
     * @throws IOException
     */
     
     
    public void OpenCurrentLoanScene(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("currentLoan.fxml"));
        
        root = loader.load();
        stage = (Stage) scenePane.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    public void SwitchToUserList(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserListScene.fxml"));
        
        root = loader.load();
        stage = (Stage) scenePane.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void SwitchToStaffList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StaffListScene.fxml"));
        
        root = loader.load();
        stage = (Stage) scenePane.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
            
    /**
     * Visar en bekräftelsedialog för att logga ut användaren.
     * Rensar sessionen och byter till inloggningsscenen.
     * 
     * @param event Händelse som triggar utloggningen
     * @throws IOException om scenresursen inte hittas
     */
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
}
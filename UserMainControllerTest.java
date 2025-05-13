package com.mycompany.bibbliotekcaseWIL;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

/**
 *
 * @author Jonathan Hedemalm
 */
public class UserMainControllerTest {
    
     @FXML
    private Label UserNameField;
    
    @FXML 
    private  AnchorPane scenePane;
    
    private Parent root;
    private Scene scene;
    private Stage stage;
    
    /**
     * Fungerar inte just nu.
     * 
     * @param event
     * @throws IOException
     */
    public void search(ActionEvent event) throws IOException {
        System.out.println("Hej!");
      
    }
   
    
    public void OpenLoanReturnLoanPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoanReturnLoanScene.fxml"));
        
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


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
import javafx.stage.Stage;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
/**
 *
 * @author William Visscher
 */
public class LoanReturnLoanController {
    private Parent root;
    private Scene scene;
    private Stage stage;
            
  @FXML
    private MenuItem logoutButton;

    @FXML
    private AnchorPane scenePane;
   
    /**
     *Hanterar inloggning
     * laddar och visar huvudscenen
     * 
     * @param event
     * @throws IOException
     */
    public void OpenLoanPage(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoanScene.fxml"));
        
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    public void OpenReturnLoanPage(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReturnLoanScene.fxml"));
        
        root = loader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
     public void OpenMainSceneTest(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainSceneTest.fxml"));
        
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
    }
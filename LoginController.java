
package com.mycompany.bibbliotekcaseWIL;

import java.io.IOException;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author Jonathan Hedemalm
 */
public class LoginController {
    
    private Connection connection;
    
    private Parent root;
    private Scene scene;
    private Stage stage;
    
    @FXML 
    private TextField usernameField;
    
    @FXML 
    private PasswordField passwordField;
    
    @FXML
    private Label loginMessageLabel;
    
    private final LoginLogic loginLogic = new LoginLogic();
    
    /**
     *Hanterar inloggning
     * laddar och visar huvudscenen
     * 
     * @param event
     * @throws IOException
     */
    public void login(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            loginMessageLabel.setText("Enter both username and password.");
            return;
        }
        
            FXMLLoader loader;
            
            if(loginLogic.validateStaffLogin(username, password)) {
                int staffID = loginLogic.getStaffID(username);
                
                Session.isStaff = true;
                Session.staffID = staffID;
                Session.loggedInName = loginLogic.getStaffFullName(username);
                
                if(loginLogic.isManager(staffID)) {
                     Session.isManager = true;
                     loader = new FXMLLoader(getClass().getResource("ManagerMainScene.fxml"));
                } else {
                     loader = new FXMLLoader(getClass().getResource("StaffMainScene.fxml"));
                }
            } else if (loginLogic.validateUserLogin(username, password)) {
                Session.isUser = true;
                Session.loggedInName = loginLogic.getUserFullName(username);
                Session.userID = loginLogic.getUserID(username);
                loader = new FXMLLoader(getClass().getResource("mainSceneTest.fxml"));
            } else {
                loginMessageLabel.setText("Wrong username or password.");
                return;
            }
            
        root = loader.load();
        
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
       } 
    }     
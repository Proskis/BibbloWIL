package com.mycompany.bibbliotekcaseWIL;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Jonathan Hedemalm
 */
public class UserListController {
    @FXML 
    private ListView<User> userListView;
    @FXML 
    private TextField firstNameField;
    @FXML 
    private TextField lastNameField;
    @FXML
    private TextField userTypeIDField;
    @FXML 
    private TextField emailField;
    @FXML 
    private TextField phoneNumberField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField searchByLastNameField;
    @FXML 
    private  AnchorPane scenePane;
    
    private Parent root;
    private Scene scene;
    private Stage stage;
   
   private final UserQueries userQueries = new UserQueries();
   
   private final ObservableList<User> userList = FXCollections.observableArrayList();
   
   
    /**
     * Initialiserar kontrollern
     * 
     */
    public void initialize() {
       userListView.setItems(userList);
       getAllEntries();
       
       userListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
           displayUser(newValue);
       }
       );
   }
    
    /**
     * Hämtar alla användare från databasen.
     */
   private void getAllEntries() {
       userList.setAll(userQueries.getAllUsers());
       selectFirstEntry();
   }
   /**
    * Väljer första användaren i listan.
    */
   private void selectFirstEntry() {
       userListView.getSelectionModel().selectFirst();
   }
   /**
    * Visar användarens information.
    * @param user 
    */
   private void displayUser(User user) {
       if (user != null) {
           firstNameField.setText(user.getFirstName());
           lastNameField.setText(user.getLastName());
           emailField.setText(user.getEmail());
           phoneNumberField.setText(user.getPhoneNo());
           usernameField.setText(user.getUsername());
           passwordField.setText(user.getPassword());
           userTypeIDField.setText(String.valueOf(user.getUserTypeID()));
       }
       else {
           firstNameField.clear();
           lastNameField.clear();
           emailField.clear();
           phoneNumberField.clear();
           usernameField.clear();
           passwordField.clear();
       }
   } 
   
    public void backToMain(ActionEvent event) throws IOException {
            FXMLLoader loader;
        
            if (Session.isManager) {
                loader = new FXMLLoader(getClass().getResource("ManagerMainScene.fxml"));
            } else if (Session.isStaff) { 
                loader = new FXMLLoader(getClass().getResource("StaffMainScene.fxml"));
            } else if (Session.isUser) {
                loader = new FXMLLoader(getClass().getResource("UserMainScene.fxml"));
            } else {
                displayAlert(AlertType.ERROR, "No scene found", "Can not back please restart");
                return;
            }
            
            root = loader.load();
            stage = (Stage) scenePane.getScene().getWindow();
            scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
    }



   
   /**
    * Sökfunktion baserat på efternamn
    * 
    * @param event 
    */
   @FXML 
    void find(ActionEvent event) {
        List<User> users = userQueries.getUsersByLastName(searchByLastNameField.getText() + "%");
        
        if (users.size() > 0) {
            userList.setAll(users);
            selectFirstEntry();
        }
        else {
            displayAlert(AlertType.INFORMATION, "Lastname Not Found", "There are no users with that last name.");
        }
   }
   /**
    * Lägger till nya användare
    * 
    * @param event 
    */
   @FXML
   void addUser(ActionEvent event) {
       int userTypeID = Integer.parseInt(userTypeIDField.getText());
       
       int result = userQueries.addUser(
       firstNameField.getText(), lastNameField.getText(), emailField.getText(), phoneNumberField.getText(), usernameField.getText(), passwordField.getText(), userTypeID);
       
       if (result == 1) {
           displayAlert(AlertType.INFORMATION, "User added", "New User added.");
   }
   else {
    displayAlert(AlertType.ERROR, "User not added", "Failed to add user.");
}
       getAllEntries();
}
   
   @FXML
   void deleteUser(ActionEvent event) {
       User selectedUser = userListView.getSelectionModel().getSelectedItem();
       
       if( selectedUser != null) {
          boolean confirmed =  displayAlert(AlertType.CONFIRMATION, "Confirm Deletion", "Are you sure you want to delete this user?");
           
          if(confirmed) {
           int result = userQueries.deleteUser(selectedUser.getUserID());
           if (result == 1) {
               userList.remove(selectedUser);
               displayAlert(AlertType.INFORMATION, "User Deleted","User deleted successfully,"); 
               getAllEntries();
            } else {
               displayAlert(AlertType.ERROR, "Failed", "Failed to delete User.");
            }
        }
        } else {
           displayAlert(AlertType.ERROR, "No Selection", "Select a user to delete.");
        }  
   }

   
   /**
    * Visar alla användare
    * 
    * @param event 
    */
   @FXML
   void browseAll(ActionEvent event) {
       getAllEntries();
   }
   /**
    * Visar en alert
    * 
    * @param type
    * @param title
    * @param message 
    */
   
 
   
   private boolean displayAlert(AlertType type, String title, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);

    if (type == AlertType.CONFIRMATION) {
        return alert.showAndWait().get() == ButtonType.OK;
    }

    alert.showAndWait();
    return false;
}
   

}
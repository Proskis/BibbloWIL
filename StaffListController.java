package com.mycompany.bibbliotekcaseWIL;

import java.io.IOException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
public class StaffListController {
    @FXML 
    private ListView<Staff> staffListView;
    @FXML 
    private TextField firstNameField;
    @FXML 
    private TextField lastNameField;
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
   
   private final StaffQueries staffQueries = new StaffQueries();
   
   private final ObservableList<Staff> staffList = FXCollections.observableArrayList();
   
    /**
     * Initialiserar kontrollern
     * 
     */
    public void initialize() {
       staffListView.setItems(staffList);
       getAllEntries();
       
       staffListView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
           displayStaff(newValue);
       }
       );
   }
    
    /**
     * Hämtar alla användare från databasen.
     */
   private void getAllEntries() {
       staffList.setAll(staffQueries.getAllStaff());
       selectFirstEntry();
   }
   /**
    * Väljer första användaren i listan.
    */
   private void selectFirstEntry() {
       staffListView.getSelectionModel().selectFirst();
   }
   /**
    * Visar användarens information.
    */
   private void displayStaff(Staff staff) {
       if (staff != null) {
           firstNameField.setText(staff.getFirstName());
           lastNameField.setText(staff.getLastName());
           emailField.setText(staff.getEmail());
           phoneNumberField.setText(staff.getPhoneNo());
           usernameField.setText(staff.getUsername());
           passwordField.setText(staff.getPassword());
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
       
       FXMLLoader loader = new FXMLLoader(getClass().getResource("ManagerMainScene.fxml"));
       
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
        List<Staff> staff = staffQueries.getStaffByLastName(searchByLastNameField.getText() + "%");
        
        if (staff.size() > 0) {
            staffList.setAll(staff);
            selectFirstEntry();
        }
        else {
            displayAlert(Alert.AlertType.INFORMATION, "Lastname Not Found", "There are no staff with that last name.");
        }
   }
 
   @FXML
   void addStaff(ActionEvent event) {
       
       int result = staffQueries.addStaff(
       firstNameField.getText(), lastNameField.getText(), emailField.getText(), phoneNumberField.getText(), usernameField.getText(), passwordField.getText());
       
       if (result == 1) {
           displayAlert(Alert.AlertType.INFORMATION, "Staff added", "New Staff added.");
   }
   else {
    displayAlert(Alert.AlertType.ERROR, "Staff not added", "Failed to add staff.");
}
       getAllEntries();
}
   
    @FXML
   void deleteStaff(ActionEvent event) {
       Staff selectedStaff = staffListView.getSelectionModel().getSelectedItem();
       
       if( selectedStaff != null) {
          boolean confirmed =  displayAlert(Alert.AlertType.CONFIRMATION, "Confirm Deletion", "Are you sure you want to delete this user?");
           
          if(confirmed) {
           int result = staffQueries.deleteStaff(selectedStaff.getStaffID());
           if (result == 1) {
               staffList.remove(selectedStaff);
               displayAlert(Alert.AlertType.INFORMATION, "User Deleted","User deleted successfully,"); 
               getAllEntries();
            } else {
               displayAlert(Alert.AlertType.ERROR, "Failed", "Failed to delete User.");
            }
        }
        } else {
           displayAlert(Alert.AlertType.ERROR, "No Selection", "Select a user to delete.");
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
   private boolean displayAlert(Alert.AlertType type, String title, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);

    if (type == Alert.AlertType.CONFIRMATION) {
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }

    alert.showAndWait();
    return false;
}
}

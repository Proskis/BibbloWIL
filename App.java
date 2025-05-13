package com.mycompany.bibbliotekcaseWIL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

 
public class App extends Application {

        public static void main(String[] args) {
        launch(args);
    }
       
    /**
     * Startar JavaFX scenen.
     * 
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(event -> {
            try {
                event.consume();
                logout(stage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Visar dialog för utloggning när användaren försöker stänga programmet.
     * 
     * @param stage
     * @throws IOException
     */
    public void logout(Stage stage) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are logging out");
        alert.setContentText("Are you sure? ");
        
        if(alert.showAndWait().get() == ButtonType.OK) {
            System.out.println("Logged out!");
            stage.close(); 
        }
   

}
}
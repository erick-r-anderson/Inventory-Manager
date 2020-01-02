/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import java.io.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author erick
 */
public class MainApp extends Application {
    
    public static Stage primaryStage;
    private AnchorPane mainScreen;
    
    
           
    @Override
    public void start(Stage stage) throws Exception {
         this.primaryStage = stage;
        this.primaryStage.setTitle("Inventory Manager");
        
         FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MainScreen.fxml"));
            mainScreen = loader.load();
            
            Scene scene = new Scene(mainScreen);
            scene.getStylesheets().add(getClass().getResource("view/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();             
        
    }
    
    public static void InvalidEntryAlert(String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Entry");
        alert.setContentText("Please enter a valid " + text);

        alert.showAndWait();
       }
   
    
    public static void EmptyStringAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Entry");
        alert.setContentText("Please enter a value in each field");
          
               
    }
    
    public static void MinMaxAlert(String text) {
         Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Entry");
        alert.setContentText("Please make sure " + text);
        
        alert.showAndWait();
    }
    
    public static void NoSelected(String text){
          Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Entry");
        alert.setContentText("No " + text + " selected!");
        
        alert.showAndWait();
    }
    
  

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
               
    }
    
}

    
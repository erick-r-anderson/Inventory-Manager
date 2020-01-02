/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.view;

import inventory.MainApp;
import inventory.model.InHouse;
import inventory.model.Outsourced;
import static inventory.view.MainScreenController.thisInventory;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author erick
 */
public class AddPartController implements Initializable {

    @FXML
    private Label lblAddPart;
    @FXML
    private RadioButton rdInHouse;
    @FXML
    private RadioButton rdOutsourced;
    @FXML
    private Label lblID;
    @FXML
    private Label lblName;
    @FXML
    private Label lblInv;
    @FXML
    private Label lblPrice;
    @FXML
    private Label lblMax;
    @FXML
    private Label lblCompanyOrMachine;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtPartName;
    @FXML
    private TextField txtInv;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtMax;
    @FXML
    private Label lblMin;
    @FXML
    private TextField txtMin;
    @FXML
    private TextField txtCompanyOrMachine;
    @FXML
    private Button btnSavePart;
    @FXML
    private Button btnCancel;

    //create view
    Stage stage;
    Parent root;
    
    //variables for new part
    String inputName; 
    double inputPrice;
    int inputStock;
    int inputMin;
    int inputMax;
    int inputMachineID;
    String inputCompanyName;
    

    @FXML
    private AnchorPane anAddPart;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //auto generates current part ID
        txtID.setText(Integer.toString(MainScreenController.currentPartID));
        
    }    

    @FXML
    private void onInHouse(ActionEvent event) {
        
        //sets radio button, changes interface accordingly
        rdOutsourced.setSelected(false);
        lblCompanyOrMachine.setText("Machine ID");
       
    }

    @FXML
    private void onOutsourced(ActionEvent event) {
           
        //sets radio button, changes interface accordingly
        rdInHouse.setSelected(false);
        lblCompanyOrMachine.setText("Company Name");
       
        
    }

    @FXML
    private void onSave(ActionEvent event) throws Exception {
             
           //checks for an empty name field
        if(txtPartName.getText().isEmpty() == true)
        { 
           Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Entry");
        alert.setContentText("Please enter a Part Name");
        alert.showAndWait();
        
        return;       
           }
           
        String inputName = txtPartName.getText();
        
        //try statements check for vaild number types
        try{
        double inputPrice = Double.parseDouble(txtPrice.getText());
        }
        catch(NumberFormatException e){
            MainApp.InvalidEntryAlert("Price. #.##");
            return;
        }
        
        try{
        int inputStock = Integer.parseInt(txtInv.getText());
        }
        catch(NumberFormatException e){
            MainApp.InvalidEntryAlert("integer into Inv");
            return;
        }
        
        try{
        int inputMin = Integer.parseInt(txtMin.getText());
        }
        catch(NumberFormatException e) {
            MainApp.InvalidEntryAlert("integer into Min");
            return;
        }
        
        try{
        int inputMax = Integer.parseInt(txtMax.getText());
        }
        catch(NumberFormatException e) {
            MainApp.InvalidEntryAlert("integer into Max");
            return;
        }
        
        //as long as all error checks pass, assign the variables
         double inputPrice = Double.parseDouble(txtPrice.getText());
         int inputStock = Integer.parseInt(txtInv.getText());
         int inputMin = Integer.parseInt(txtMin.getText());
         int inputMax = Integer.parseInt(txtMax.getText());
          
         
        //checks if inv is < max and > min
         if (inputMax < inputMin){
            MainApp.MinMaxAlert("Max is greatet than Min");
            return;
        }
                
        if (inputStock < inputMin){
            MainApp.MinMaxAlert("Inv is greater than Min");
            return;
        }
        
        if (inputStock > inputMax){
            MainApp.MinMaxAlert("Inv is less than Max");
            return;
        }
        
        //asks for confimation to add the part
        
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("CONFIRM ADD");
        alertConfirm.setContentText("Are you sure you would like to add this part?");
       
        Optional<ButtonType> result = alertConfirm.showAndWait();
        
        if (result.get() == ButtonType.CANCEL){
            //does not add part, returns to add screen
            return;
        }
        else{
            //proceeds to add part
        }
                     
       
        if(rdInHouse.isSelected() == true){
            try{
            int inputMachineID = Integer.parseInt(txtCompanyOrMachine.getText());
            }
            catch(NumberFormatException e){
                MainApp.InvalidEntryAlert("integer into MachineID");
                return;
            }
             int inputMachineID = Integer.parseInt(txtCompanyOrMachine.getText());
            //creates new part
           thisInventory.addPart(new InHouse(MainScreenController.currentPartID, inputName, inputPrice, inputStock, inputMin, inputMax, inputMachineID));
           
           //increments inputMachineID
           MainScreenController.currentPartID += 1;
           
      
                        
        }  
        else{
            if(txtCompanyOrMachine.getText().isEmpty() == true)
        { 
           Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Entry");
        alert.setContentText("Please enter a Company Name");
        alert.showAndWait();
        
        return;       
           }
            
            String inputCompany = txtCompanyOrMachine.getText();
            MainScreenController.thisInventory.addPart(new Outsourced(MainScreenController.currentPartID, inputName, inputPrice, inputStock, inputMin, inputMax, inputCompany));
           
           //increments inputMachineID
           MainScreenController.currentPartID += 1;
            
        }
            //closes popup
         stage = (Stage)anAddPart.getScene().getWindow();
            stage.close(); 
            
       }
       
        
        
   

    @FXML
    private void onCancel(ActionEvent event) {
        
          //asks confirmation to cancel
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("CONFIRM CANCEL");
        alertConfirm.setContentText("Press okay to discard changes?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        
        if (result.get() == ButtonType.CANCEL){
            //does not discard changes, returns to add screen
            return;
        }
        else{
            //discards changes and returns to main screen
        }
        
        
        //retrieves current scene from controls, then terminates that scene
        stage = (Stage)anAddPart.getScene().getWindow();
        stage.close();
    }
    
}

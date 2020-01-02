/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.view;

import inventory.MainApp;
import inventory.model.InHouse;
import inventory.model.Outsourced;
import inventory.model.Part;

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



public class ModifyPartController implements Initializable {

    @FXML
    private Label lblModifyPart;
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
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private AnchorPane anModifyPart;

Stage stage;
Parent root;
Part thisPart;
Part selectedPart;
int index;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
  
     
        
    }
    
    

    @FXML
    private void onInHouse(ActionEvent event) {
        //sets radio button and changes interfae accordingly
         rdOutsourced.setSelected(false);
        lblCompanyOrMachine.setText("Machine ID");
      
    }

    @FXML
    private void onOutsourced(ActionEvent event) {
        //sets radio button and changes interface accordingly
          rdInHouse.setSelected(false);
        lblCompanyOrMachine.setText("Company Name");
    }

    @FXML
    private void onSave(ActionEvent event) {
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
        
        //asks confirmation to modify part
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("CONFIRM MODIFY");
        alertConfirm.setContentText("Are you sure you want to modify this item?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        
        if (result.get() == ButtonType.CANCEL){
            //does not commit changes, returns to modify screen
            return;
        }
        else{
            //commits changes
        }
            //as long as no errors, resets object's values
        
        int id = Integer.parseInt(txtID.getText());
        String name = txtPartName.getText();
        Double price = Double.parseDouble(txtPrice.getText());
        int stock = Integer.parseInt(txtInv.getText());
        int min = Integer.parseInt(txtMin.getText());
        int max = Integer.parseInt(txtMax.getText());
        int machineId;
        String companyName;
        
        //identifies the type of part to set the final value
        if(rdInHouse.isSelected() == true){
            InHouse newPart; 
            
            try{
            machineId = Integer.parseInt(txtCompanyOrMachine.getText());
            }
            catch(NumberFormatException e){
                MainApp.InvalidEntryAlert("integer into MachineID");
                return;
            }
            newPart = new InHouse(id, name, price, stock, min, max, machineId);
            
          
            thisInventory.updatePart(index, newPart);
            
        }  
        else{
           Outsourced newPart;
           
            try{
                companyName = txtCompanyOrMachine.getText();
            }
        catch(NullPointerException e){
           Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Entry");
        alert.setContentText("Please enter a Company Name");
        alert.showAndWait();
        return;}
                           
        newPart = new Outsourced(id, name, price, stock, min, max, companyName);
            
            //resets pointer for thisPart to newPart
            thisInventory.updatePart(index, newPart);
        
            }
            

            //closes popup
         stage = (Stage)anModifyPart.getScene().getWindow();
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
            //does not discard changes, returns to modify screen
            return;
        }
        else{
            //discards changes and returns to main screen
        }
               
        //retrieves the stage from the current window, and terminates it
        
        stage = (Stage)anModifyPart.getScene().getWindow();
        stage.close();
    }
    
    public void setPart(int index, Part part){
        this.selectedPart = part;
        this.index = index;
        
         try{txtID.setText(Integer.toString(selectedPart.getId()));
        txtPartName.setText(selectedPart.getName());
        txtInv.setText(Integer.toString(selectedPart.getStock()));
        txtPrice.setText(selectedPart.getPrice());
        txtMax.setText(Integer.toString(selectedPart.getMax()));
        txtMin.setText(Integer.toString(selectedPart.getMin()));
        }
       catch(NullPointerException e){
            MainApp.NoSelected("part");
            stage = (Stage)anModifyPart.getScene().getWindow();
            stage.close();
       }  
        
    }
    
    public void setInHouseOrOutsourced(Part part){
        
        
        if(part.isInHouse() == true)
       {    rdInHouse.setSelected(true);
            rdOutsourced.setSelected(false);
            lblCompanyOrMachine.setText("Machine ID");     
                                             
            txtCompanyOrMachine.setText(Integer.toString(part.getMachineID()));
            
       }
       else{rdInHouse.setSelected(false);
            rdOutsourced.setSelected(true);
            lblCompanyOrMachine.setText("Company Name"); 
            
            txtCompanyOrMachine.setText(part.getCompanyName());
           
       }
        
    }
    }
    


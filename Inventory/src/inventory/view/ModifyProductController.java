/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.view;

import inventory.MainApp;
import inventory.model.Part;
import inventory.model.Product;
import static inventory.view.MainScreenController.thisInventory;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author erick
 */
public class ModifyProductController implements Initializable {

    @FXML
    private AnchorPane anModifyProduct;
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
    private Label lblTitle;
    @FXML
    private TableView<Part> tblPart1;
    @FXML
    private TableColumn<?, ?> colPartID1;
    @FXML
    private TableColumn<?, ?> colPartName1;
    @FXML
    private TableColumn<?, ?> colInventoryLevel1;
    @FXML
    private TableColumn<?, ?> colPrice1;
    @FXML
    private TableView<Part> tblPart2;
    @FXML
    private TableColumn<?, ?> colPartID2;
    @FXML
    private TableColumn<?, ?> colPartName2;
    @FXML
    private TableColumn<?, ?> colInventoryLevel2;
    @FXML
    private TableColumn<?, ?> colPrice2;
    @FXML
    private Button btnSearch;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;

  Stage stage;
  Parent root;
  Product thisProduct;
  Part newPart;
  
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           
       //links table cells to variables
        colPartID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        colInventoryLevel1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPrice1.setCellValueFactory(new PropertyValueFactory<>("price"));
             
        colPartID2.setCellValueFactory(new PropertyValueFactory<>("id"));
       colPartName2.setCellValueFactory(new PropertyValueFactory<>("name"));
        colInventoryLevel2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colPrice2.setCellValueFactory(new PropertyValueFactory<>("price"));
        
 
    }    

    @FXML
    private void onSearch(ActionEvent event) {
           int searchInt;
       String searchString;
       ObservableList<Part> searchParts = FXCollections.observableArrayList();
           
      //resets table if search box is empty or blank space
       if(txtSearch.getText().isEmpty() || txtSearch.getText().startsWith(" ")){
       tblPart1.setItems(thisInventory.getAllParts());
       tblPart1.refresh();
       return;
       
       }
     
       //checks if it's an integer or string
      try{searchInt = Integer.parseInt(txtSearch.getText());
       searchParts.add(thisInventory.lookupPart(searchInt));
                     
    }
       catch(NumberFormatException e){
          
           
           searchString = txtSearch.getText();
           searchParts.addAll(thisInventory.lookupPart(searchString));
       }
       
        tblPart1.setItems(searchParts);
        tblPart1.refresh();
    }

    @FXML
    private void onAdd(ActionEvent event) {
        
          //confirms a part is selected
        try{newPart = tblPart1.getSelectionModel().getSelectedItem(); 
        newPart.getName();}
        catch(NullPointerException e){
            MainApp.NoSelected("part");
            return;
        }        
        
           //asks confirmation to modify part
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("CONFIRM MODIFY");
        alertConfirm.setContentText("Are you sure you want to add this item?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        
        if (result.get() == ButtonType.CANCEL){
            //does not commit changes, returns to modify screen
            return;
        }
        else{
            //commits changes
        }
        
        //retrieves selected part from table and adds to the product
        newPart = tblPart1.getSelectionModel().getSelectedItem();
        thisProduct.addAssociatedPart(newPart);
        tblPart2.setItems(thisProduct.getAllAssociatedParts());
        tblPart2.refresh();
        
    }
    

    @FXML
    private void onDelete(ActionEvent event) {
        
          //confirms a part is selected
        try{newPart = tblPart2.getSelectionModel().getSelectedItem(); 
        newPart.getName();}
        catch(NullPointerException e){
            MainApp.NoSelected("part");
            return;
        }        
        
         //asks confirmation to modify part
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("CONFIRM MODIFY");
        alertConfirm.setContentText("Are you sure you want to delete this item?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        
        if (result.get() == ButtonType.CANCEL){
            //does not commit changes, returns to modify screen
            return;
        }
        else{
            //commits changes
        }
     
        
        newPart = tblPart2.getSelectionModel().getSelectedItem(); 

            thisProduct.deleteAssociatedPart(newPart);       
            
            
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
        
        stage = (Stage)anModifyProduct.getScene().getWindow();
        stage.close();
        
    }

    @FXML
    private void onSave(ActionEvent event) throws Exception{
                       //checks for an empty name field
        if(txtPartName.getText().isEmpty() == true)
        { 
           Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Entry");
        alert.setContentText("Please enter a Product Name");
        alert.showAndWait();
        
        return;       
           }
             
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
        
              //asks confirmation to add product
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("CONFIRM MODIFY");
        alertConfirm.setContentText("Are you sure you would like to modify this product?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        
        if (result.get() == ButtonType.CANCEL){
            //discards changes, returns to add screen
            return;
        }
        else{
            //commits changes
        }
        
         //as long as all error checks pass, assign the variables
         thisProduct.setName(txtPartName.getText());
         thisProduct.setPrice(Double.parseDouble(txtPrice.getText()));
         thisProduct.setStock(Integer.parseInt(txtInv.getText()));
         thisProduct.setMin(Integer.parseInt(txtMin.getText()));
         thisProduct.setMax(Integer.parseInt(txtMax.getText()));
                   
        //checks if inv is < max and > min
         if (thisProduct.getMax() < thisProduct.getMin()){
            MainApp.MinMaxAlert("Max is greatet than Min");
            return;
        }
                
        if (thisProduct.getStock() < thisProduct.getMin()){
            MainApp.MinMaxAlert("Inv is greater than Min");
            return;
        }
        
        if (thisProduct.getStock() > thisProduct.getMax()){
            MainApp.MinMaxAlert("Inv is less than Max");
            return;
        }
  
        //closes popuproduct
         stage = (Stage)anModifyProduct.getScene().getWindow();
            stage.close(); 
                  
            
            
            
    }
    
    public void setProduct(Product product){
        this.thisProduct = product;
        
        
        try{txtID.setText(Integer.toString(thisProduct.getId()));
        txtPartName.setText(thisProduct.getName());
        txtInv.setText(Integer.toString(thisProduct.getStock()));
        txtPrice.setText(thisProduct.getPrice());
        txtMax.setText(Integer.toString(thisProduct.getMax()));
        txtMin.setText(Integer.toString(thisProduct.getMin()));
        }
       catch(NullPointerException e){
            MainApp.NoSelected("product");
            stage = (Stage)anModifyProduct.getScene().getWindow();
            stage.close();
       }  
        
        //populates tables
        tblPart1.setItems(thisInventory.getAllParts());
        tblPart2.setItems(thisProduct.getAllAssociatedParts());
        
        
        txtID.setText(Integer.toString((MainScreenController.currentProductID)));
        
    }
    
}

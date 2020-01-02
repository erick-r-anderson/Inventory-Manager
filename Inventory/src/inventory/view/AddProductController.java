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
public class AddProductController implements Initializable {

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
     private TableView<Part> tblParts1;
    @FXML
    private TableColumn<Part, Integer> colPartID;
    @FXML
    private TableColumn<Part, String> colPartName;
    @FXML
    private TableColumn<Part, Integer> colInventoryParts;
    @FXML
    private TableColumn<Part, Double> colCostParts;
    @FXML
     private TableView<Part> tblParts2;
    @FXML
    private TableColumn<Part, Integer> colPartID2;
    @FXML
    private TableColumn<Part, String> colPartName2;
    @FXML
    private TableColumn<Part, Integer> colInventoryParts2;
    @FXML
    private TableColumn<Part, Double> colCostParts2;
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
    Product newProduct = new Product(MainScreenController.currentProductID, "default", 0.00, 0, 0, 0);
    Part newPart;
       
    
    @FXML
    private AnchorPane anAddProduct;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // Part fluxCapacitor = new InHouse(1, "Flux Capacitor", 25.99, 2, 1, 2, 55);
    // newProduct.addAssociatedPart(fluxCapacitor);
     

//links table cells to variables
        colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colInventoryParts.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colCostParts.setCellValueFactory(new PropertyValueFactory<>("price"));
             
        colPartID2.setCellValueFactory(new PropertyValueFactory<>("id"));
       colPartName2.setCellValueFactory(new PropertyValueFactory<>("name"));
        colInventoryParts2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colCostParts2.setCellValueFactory(new PropertyValueFactory<>("price"));
        
         tblParts2.setItems(newProduct.getAllAssociatedParts());
        
//populates tables
        tblParts1.setItems(thisInventory.getAllParts());
        tblParts2.setItems(newProduct.getAllAssociatedParts());
        
        txtID.setText(Integer.toString((MainScreenController.currentProductID)));
        
    }    

    @FXML
    private void onSearch(ActionEvent event) {
        int searchInt;
       String searchString;
       ObservableList<Part> searchParts = FXCollections.observableArrayList();
           
      //resets table if search box is empty or blank space
       if(txtSearch.getText().isEmpty() || txtSearch.getText().startsWith(" ")){
       tblParts1.setItems(thisInventory.getAllParts());
       tblParts1.refresh();
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
       
        tblParts1.setItems(searchParts);
        tblParts1.refresh();
    }

    @FXML
    private void onAdd(ActionEvent event) {
   //confirms a part is selected
        try{newPart = tblParts1.getSelectionModel().getSelectedItem(); 
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
        
            
            newPart = tblParts1.getSelectionModel().getSelectedItem();   
            newProduct.addAssociatedPart(newPart);
      }

    @FXML
    private void onDelete(ActionEvent event) {
        
           //confirms a part is selected
        try{newPart = tblParts2.getSelectionModel().getSelectedItem(); 
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
            
        
        newPart = tblParts1.getSelectionModel().getSelectedItem(); 

            newProduct.deleteAssociatedPart(newPart);        
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
        


        //retrieves current stage from interface, and terminates  it
        
         stage = (Stage)anAddProduct.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onSave(ActionEvent event) throws Exception {
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
        
     
        
        //as long as all error checks pass, assign the variables
         newProduct.setName(txtPartName.getText());
         newProduct.setPrice(Double.parseDouble(txtPrice.getText()));
         newProduct.setStock(Integer.parseInt(txtInv.getText()));
         newProduct.setMin(Integer.parseInt(txtMin.getText()));
         newProduct.setMax(Integer.parseInt(txtMax.getText()));
                   
        //checks if inv is < max and > min
         if (newProduct.getMax() < newProduct.getMin()){
            MainApp.MinMaxAlert("Max is greatet than Min");
            return;
        }
                
        if (newProduct.getStock() < newProduct.getMin()){
            MainApp.MinMaxAlert("Inv is greater than Min");
            return;
        }
        
        if (newProduct.getStock() > newProduct.getMax()){
            MainApp.MinMaxAlert("Inv is less than Max");
            return;
        }
  
             //asks confirmation to add product
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("CONFIRM ADD");
        alertConfirm.setContentText("Are you sure you would like to add this product?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        
        if (result.get() == ButtonType.CANCEL){
            //discards changes, returns to add screen
            return;
        }
        else{
            //commits changes
        }
         
        MainScreenController.thisInventory.addProduct(newProduct);
           
        //increments inputMachineID
        MainScreenController.currentProductID += 1;
            
        //closes popup
         stage = (Stage)anAddProduct.getScene().getWindow();
            stage.close(); 
            
    }
    
}

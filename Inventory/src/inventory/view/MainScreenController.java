/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.view;

import inventory.MainApp;
import inventory.model.InHouse;
import inventory.model.Inventory;
import inventory.model.Outsourced;
import inventory.model.Part;
import inventory.model.Product;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author erick
 */
public class MainScreenController implements Initializable {

    @FXML
    private Label lblTitle;
    @FXML
    private Label lblParts;
    @FXML
    private Button btnSearchParts;
    @FXML
    private TextField txtSearchParts;
    @FXML
    private TableView<Part> tblParts;
    @FXML
    private TableColumn<Part, Integer> colPartID;
    @FXML
    private TableColumn<Part, String> colPartName;
    @FXML
    private TableColumn<Part, Integer> colInventoryParts;
    @FXML
    private TableColumn<Part, Double> colCostParts;
    @FXML
    private Button btnAddPart;
    @FXML
    private Button btnModifyPart;
    @FXML
    private Button btnDeletePart;
    @FXML
    private Label lblProducts;
    @FXML
    private Button btnSearchProduct;
    @FXML
    private TextField txtSearchProducts;
    @FXML
    private TableView<Product> tblProducts;
    @FXML
    private TableColumn<Product, Integer> colProductID;
    @FXML
    private TableColumn<Product, String> colProductName;
    @FXML
    private TableColumn<Product, Double> colCostProducts;
    @FXML
    private Button btnAddProduct;
    @FXML
    private Button btnModifyProduct;
    @FXML
    private Button btnDeleteProduct;
    @FXML
    private Button btnExit;

    //creates the inventory list
    static Inventory thisInventory = new Inventory();
    
    Product selectedProduct;
    Part selectedPart;
    
    //keeps track of most recent part and product IDs for the auto-gen feature
    static int currentPartID;
    static int currentProductID;
    
    @FXML
    private TableColumn<?, ?> colInventoryProducts;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) throws NullPointerException {
        
        ObjectInputStream oi = null;
        try {
            //retrieves serialized inventory from file
            FileInputStream fi = new FileInputStream(new File("InventoryRecord.txt"));
            oi = new ObjectInputStream(fi);
            
            try {
                thisInventory = (Inventory)oi.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            thisInventory.deserialzeAll();
            
            
       /*
            // FIXME add sample data, would be removed in final version
             Part fluxCapacitor = new InHouse(1, "Flux Capacitor", 25.99, 2, 1, 2, 55);
            Part hyperdrive = new Outsourced(2, "HyperDrive", 500.00, 5, 1, 50, "SpaceTech LLC");
            Part magicWand = new Outsourced(3, "Magic Wand", 100.00, 5, 1, 50, "Hogwarts");
            Part invisibilityCloak = new InHouse(4, "Invisibility Cloak", 1000.00, 6, 1, 15, 25);
            thisInventory.addPart(fluxCapacitor);
            thisInventory.addPart(hyperdrive);
            thisInventory.addPart(magicWand);
            thisInventory.addPart(invisibilityCloak);
            Product delorean = new Product(1, "Delorean", 250000.000, 1, 1, 10);
            delorean.addAssociatedPart(fluxCapacitor);
            Product falcon = new Product(2, "Millenium Falcon", 750.00, 1, 1, 20);
            falcon.addAssociatedPart(hyperdrive);
            Product harryPotter = new Product(3, "Harry Potter", 15.00, 1, 1, 30);
            harryPotter.addAssociatedPart(magicWand);
            harryPotter.addAssociatedPart(invisibilityCloak);
            thisInventory.addProduct(delorean);
            thisInventory.addProduct(falcon);
            thisInventory.addProduct(harryPotter); */
            
            //links table cells to variables
            colPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
            colPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colInventoryParts.setCellValueFactory(new PropertyValueFactory<>("stock"));
            colCostParts.setCellValueFactory(new PropertyValueFactory<>("price"));
            colProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
            colProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colInventoryProducts.setCellValueFactory(new PropertyValueFactory<>("stock"));
            colCostProducts.setCellValueFactory(new PropertyValueFactory<>("price"));
                
            
            tblParts.setItems(thisInventory.getAllParts());
            
            tblProducts.setItems(thisInventory.getAllProducts());
        } catch (IOException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                oi.close();
            } catch (IOException ex) {
                Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
              
    }
           
            
    @FXML
    private void onSearchPart(ActionEvent event) {
        int searchInt;
       String searchString;
       ObservableList<Part> searchParts = FXCollections.observableArrayList();
           
      //resets table if search box is empty or blank space
       if(txtSearchParts.getText().isEmpty() || txtSearchParts.getText().startsWith(" ")){
       tblParts.setItems(thisInventory.getAllParts());
       refreshTables();
       return;
       
       }
     
       //checks if it's an integer or string
      try{searchInt = Integer.parseInt(txtSearchParts.getText());
       searchParts.add(thisInventory.lookupPart(searchInt));
                     
    }
       catch(NumberFormatException e){
          
           
           searchString = txtSearchParts.getText();
           searchParts.addAll(thisInventory.lookupPart(searchString));
       }
       
        tblParts.setItems(searchParts);
        refreshTables();
        
    }

    @FXML
    private void onAddPart(ActionEvent event) throws IOException {
        
        //generates Add Part popup window as the current stage
        Stage stage;
        Parent root;
        
       try{ 
   stage = new Stage();
   root = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
   stage.setScene(new Scene(root));
   stage.setTitle("Add Part");
   stage.initModality(Modality.APPLICATION_MODAL);
   stage.initOwner(btnAddPart.getScene().getWindow());
   stage.showAndWait();
    }
       catch(IOException e){
           e.printStackTrace();
           
       }
    }
    

    @FXML
    private void onModifyPart(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;
        
        
           //confirms a part is selected
        try{selectedPart = tblParts.getSelectionModel().getSelectedItem(); 
        selectedPart.getName();}
        catch(NullPointerException e){
            MainApp.NoSelected("part");
            return;
        }        
        
        selectedPart = tblParts.getSelectionModel().getSelectedItem();
        
  
//generates Modify Part popup window as the current stage
  
       try{ 
    stage = new Stage();
    
   FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
   root = loader.load();
    ModifyPartController controller = loader.getController();
        int index = thisInventory.getPartIndex(selectedPart);
        controller.setPart(index, selectedPart);
        controller.setInHouseOrOutsourced(selectedPart);
   stage.setScene(new Scene(root));
   stage.setTitle("Modify Part");
   stage.initModality(Modality.APPLICATION_MODAL);
   stage.initOwner(btnModifyPart.getScene().getWindow());
  
   stage.showAndWait();
   
   
      
    }
       catch(IOException e){
           e.printStackTrace();
           
       }
       
       refreshTables();
   
   
    
       
       refreshTables();
       
    }

    @FXML
    private void onDeletePart(ActionEvent event) {
      
        //confirms a part is selected
        try{selectedPart = tblParts.getSelectionModel().getSelectedItem(); 
        selectedPart.getName();}
        catch(NullPointerException e){
            MainApp.NoSelected("part");
            return;
        }        



//asks confirmation to modify part
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("CONFIRM DELETE");
        alertConfirm.setContentText("Are you sure you want to delete this item?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        
        if (result.get() == ButtonType.CANCEL){
            //does not commit changes, returns to modify screen
            return;
        }
        else{
            //commits changes
        }
        
        //gets a pointer to the part and passes it to the delete function
        selectedPart = tblParts.getSelectionModel().getSelectedItem();
        thisInventory.deletePart(selectedPart);
        
    }

    @FXML
    private void onSearchProduct(ActionEvent event) {
       int searchInt;
       String searchString;
       ObservableList<Product> searchProducts = FXCollections.observableArrayList();
              
       //resets table if search box is empty or blank space, will only execute if search text does not contain characters
       if(txtSearchProducts.getText().isEmpty() || txtSearchProducts.getText().startsWith(" ")){ 
       tblProducts.setItems(thisInventory.getAllProducts());
       refreshTables();
       }
           
       //checks if it's an integer or string
       try{searchInt = Integer.parseInt(txtSearchProducts.getText());
              
       searchProducts.add(thisInventory.lookupProduct(searchInt));
       tblProducts.setItems(searchProducts);
       refreshTables();
              
    }
       catch(NumberFormatException e){
           searchString = txtSearchProducts.getText();
           
           searchProducts.addAll(thisInventory.lookupProduct(searchString));
       }
       
       tblProducts.setItems(searchProducts);
       refreshTables();        

    }
    
    @FXML
    private void onAddProduct(ActionEvent event) throws IOException {
        
        //generates Add Product popup window as the current stage
        Stage stage;
        Parent root;
        
       try{ 
   stage = new Stage();
   root = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
   stage.setScene(new Scene(root));
   stage.setTitle("Add Product");
   stage.initModality(Modality.APPLICATION_MODAL);
   stage.initOwner(btnAddProduct.getScene().getWindow());
   stage.showAndWait();
    }
       catch(IOException e){
           e.printStackTrace();
           
       }
       
       //refreshes table data
       refreshTables();
       
    }

    @FXML
    private void onModifyProduct(ActionEvent event) throws IOException {
          
        //confirms that a product is selected
        try{selectedProduct = tblProducts.getSelectionModel().getSelectedItem(); 
        selectedProduct.getAllAssociatedParts();}
        catch(NullPointerException e){
            MainApp.NoSelected("product");
            return;
        }        

        

//generates Modify Product popup window as the current stage
        Stage stage;
        Parent root;
        
        //gets the selected product from the table
        selectedProduct = tblProducts.getSelectionModel().getSelectedItem();
               
       try{ 
    stage = new Stage();
    
   FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
   root = loader.load();
    ModifyProductController controller = loader.getController();
        
        controller.setProduct(selectedProduct);
   stage.setScene(new Scene(root));
   stage.setTitle("Modify Product");
   stage.initModality(Modality.APPLICATION_MODAL);
   stage.initOwner(btnModifyProduct.getScene().getWindow());
  
   stage.showAndWait();
   
   
      
    }
       catch(IOException e){
           e.printStackTrace();
           
       }
       
       refreshTables();
    }

    @FXML
    private void onDeleteProduct(ActionEvent event) {
//makes sure a product is selected
        try{selectedProduct = tblProducts.getSelectionModel().getSelectedItem(); 
        selectedProduct.getAllAssociatedParts();}
        catch(NullPointerException e){
            MainApp.NoSelected("Product");
            return;
        }        



//asks confirmation to modify part
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("CONFIRM DELETE");
        alertConfirm.setContentText("Are you sure you want to delete this item?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        
        if (result.get() == ButtonType.CANCEL){
            //does not commit changes, returns to modify screen
            return;
        }
        else{
            //commits changes
        }
        
        
        //gets a pointer to the product and passes it to the delete function
        selectedProduct = tblProducts.getSelectionModel().getSelectedItem();
        thisInventory.deleteProduct(selectedProduct);
        
    
        }
            
               
     
     

    @FXML
    private void onExit(ActionEvent event) throws IOException {
        //asks for confirmation to exit 
        
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("CONFIRM EXIT");
        alertConfirm.setContentText("Are you sure you would like to exit?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        
        if (result.get() == ButtonType.CANCEL){
            //does not exit
            return;
        }
        else{
//serializes objects
   thisInventory.serialzeAll();

//terminates application
            System.exit(0);
        }
             
        
    }
    
    //public function for modify views to refresh the table views with new data
    public void refreshTables(){
        tblParts.refresh();
        tblProducts.refresh();       
        
    }
    
      //called when objects are to be serialzed to the file
    
        
    }
    


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.model;

import java.io.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author erick
 */
public class Inventory implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private transient ObservableList<Part> allParts = FXCollections.observableArrayList();
    private transient ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private ArrayList<Part> serializableParts;
    private ArrayList<Product> serializableProducts;
    
    public void addPart(Part part) {this.allParts.add(part);}
    public void addProduct(Product product) {this.allProducts.add(product);}
    public Part lookupPart(int partId) {
        
        Part searchPart;
        
        for(Part p: allParts){
            if(p.getId()==partId){
                
                searchPart = p;
                return searchPart;
            }
           }
        
        searchPart = null;
        return searchPart;
    }
    
    public Product lookupProduct(int productId) {
        Product searchProduct;
        
        for(Product p: allProducts){
            if(p.getId()==productId){
                
                searchProduct = p;
                return searchProduct;
            }
    }
        searchProduct = null;
        return searchProduct;
    }
    
    
  public ObservableList<Part> lookupPart(String partName){
    ObservableList<Part> searchParts = FXCollections.observableArrayList();
    String searchName;
    
    searchName = partName.toUpperCase();
    
    for(Part p: allParts){
        if(p.getName().toUpperCase().contains(searchName))
            searchParts.add(p);
        }
     
    return searchParts;
    }
    
    
    public ObservableList<Product> lookupProduct(String productName){
    
       ObservableList<Product> searchProducts = FXCollections.observableArrayList();
       String searchName;
       
       searchName= productName.toUpperCase();
       for(Product p: allProducts){
        if(p.getName().toUpperCase().contains(searchName))
            searchProducts.add(p);
        }
    
    return searchProducts;
    }
    
    public int getPartIndex(Part part){
        int index = allParts.indexOf(part);
        return index;
    }
    
    public int getProductIndex(Product product){
        int index = allParts.indexOf(product);
        return index;
    }
    
   
    public void updatePart(int index, Part part) { allParts.set(index, part);} 
    public void updateProduct(int index, Product product) {allProducts.set(index, product);}
    
    public void deletePart(Part part) {
        this.allParts.remove(part);
        } 
    
    public void deleteProduct(Product product) {
            this.allProducts.remove(product);
       } 
    
    //accessors
    public ObservableList<Part> getAllParts() {return this.allParts;}
    public ObservableList<Product> getAllProducts() {return this.allProducts;}
    
    public void serialzeAll() throws FileNotFoundException, IOException{
       FileOutputStream f = new FileOutputStream(new File("InventoryRecord.txt"));
       ObjectOutputStream o = new ObjectOutputStream(f);
       
       this.serializableParts = new ArrayList<Part>(allParts);
       this.serializableProducts = new ArrayList<Product>(allProducts);
       
       for (Product p : serializableProducts){
           p.serializeParts();
       }
       
       o.writeObject(this);
       
    //   Part outputPart;
      // Product outputProduct;
         
       //writes inventory to file
       /*for(Part p : allParts){
           outputPart = (Part)p;
         o.writeObject(outputPart);}
       
       for(Product q : allProducts){
           outputProduct = (Product)q;
           q.serializeParts();
           o.writeObject(outputProduct);
       }*/
       
       
       //closes stream
       o.close();
       
       return;
       
        }
    
    public void deserialzeAll(){
        this.allParts = FXCollections.observableArrayList(serializableParts);
        this.allProducts = FXCollections.observableArrayList(serializableProducts);
        
        for(Product p : allProducts){
            p.deserializeParts();
        }
    }
}



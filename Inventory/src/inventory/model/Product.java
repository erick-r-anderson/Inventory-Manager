/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author erick
 */
public class Product implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private transient ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ArrayList<Part> serializableParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    public Product(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;        
       
     }
    
     //mutators
    public void setId(int id){this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setPrice(double price) {this.price = price;}
    public void setStock(int stock) {this.stock = stock;}
    public void setMin(int min) {this.min = min;}
    public void setMax(int max) {this.max = max;}
    public void addAssociatedPart(Part part) {this.associatedParts.add(part);}
    
    public void deleteAssociatedPart(Part part) {
    
    this.associatedParts.remove(part);
    }
    
    
    //accessors
    public int getId(){return this.id;}
    public String getName() {return this.name;}
    public String getPrice(){
        NumberFormat us = NumberFormat.getCurrencyInstance();
        
        return us.format(this.price);
        }
    public int getStock() {return this.stock;}
    public int getMin(){return this.min;}
    public int getMax() {return this.max;}
    public ObservableList<Part> getAllAssociatedParts() {return this.associatedParts;}
    
    public void serializeParts(){
        
        //converts the observable list to a serializable version
    this.serializableParts = new ArrayList<Part>(associatedParts);
}
    
    public void deserializeParts(){
        this.associatedParts = FXCollections.observableArrayList(serializableParts);
    }
        
}

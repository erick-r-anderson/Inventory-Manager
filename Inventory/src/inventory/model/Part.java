/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.model;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author erick
 */
public abstract class Part implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    protected int id;
    protected String name;
    protected double price;
    protected int stock;
    protected int min;
    protected int max;
    
    public Part(int id, String name, double price, int stock, int min, int max){
        //sets all instance variables
        
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
    
    public abstract void setMachineID(int machineID);
    public abstract void setCompanyName(String name);
    
    //accessors
    public int getId(){return this.id;}
    public String getName() {return this.name;}
    public String getPrice(){
        NumberFormat us = NumberFormat.getCurrencyInstance();
        
        return us.format(this.price);}
    public int getStock() {return this.stock;}
    public int getMin(){return this.min;}
    public int getMax() {return this.max;}
    public abstract boolean isInHouse();

    public abstract int getMachineID();
    public abstract String getCompanyName();
    
    
    
}

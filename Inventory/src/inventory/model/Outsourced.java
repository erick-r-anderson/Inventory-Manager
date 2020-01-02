/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.model;

import javafx.scene.control.Alert;

/**
 *
 * @author erick
 */
public class Outsourced extends Part{
    
    String companyName;
    
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        
        this.companyName = companyName;
        }
    
    //mutator
    public void setCompanyName(String companyName) {this.companyName = companyName;}
    
//dummy function, should never be accessed
        public void setMachineID(int machineID) {    Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Entry");
        alert.setContentText("Something went wrong!");
    }
    
    //accessor
    public String getCompanyName() {return this.companyName;}
    
     public boolean isInHouse() {return false;}
     
     //dummy function. in here to make the code compile
     public int getMachineID() {return 0;}
     
}

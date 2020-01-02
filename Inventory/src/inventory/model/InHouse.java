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
public class InHouse extends Part {
    
    private int machineID;
    
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID)
    {
        //sends values to superclass
        super(id, name, price, stock, min, max);
        
        //sets subclass specific value
        this.machineID = machineID;
        
    }
    
    //mutator
    @Override
    public void setMachineID(int machineID) {this.machineID = machineID;}



   
    //dummy function, shouldn't ever be accessed, but alert dialog is in for debug purposes
    public void setCompanyName(String name) {    Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Entry");
        alert.setContentText("Something went wrong!");
    }
    
    //accessor
    public int getMachineID() {return this.machineID;}
    
    //dummy function, in here to make the code compile
    public String getCompanyName() {return " ";}
    
    
    public boolean isInHouse() {return true;}

}


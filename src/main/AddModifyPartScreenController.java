package main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.*;


public class AddModifyPartScreenController implements Initializable {

    @FXML private TextField partId;
    @FXML private TextField partName;
    @FXML private TextField partInv;
    @FXML private TextField partPrice;
    @FXML private TextField partMax;
    @FXML private TextField partMin;
    @FXML private TextField partCompId;
    
    @FXML private Label header;
    @FXML private Label compId;
    
    @FXML private RadioButton inHouse;
    @FXML private RadioButton outsourced;
    @FXML private ToggleGroup partType;

    public void initData(){
        
        header.setText("Add Part");
        
        partType.selectToggle(inHouse);
        
        partId.setText(String.valueOf(Inventory.assignPartId(0)));
        
        radioButtonClicked();
    }
       
    public void initData(Part myPart){
        
        header.setText("Modify Part");
        
        if (myPart instanceof InHouse){
            partType.selectToggle(inHouse);
        }
        else {
            partType.selectToggle(outsourced);
        }
                
        radioButtonClicked();
                
        partId.setText(String.valueOf(myPart.getId()));
        partName.setText(String.valueOf(myPart.getName()));
        partInv.setText(String.valueOf(myPart.getStock()));
        partPrice.setText(String.valueOf(myPart.getPrice()));
        partMax.setText(String.valueOf(myPart.getMax()));
        partMin.setText(String.valueOf(myPart.getMin()));
        
        if (myPart instanceof InHouse){
            partCompId.setText(String.valueOf(((InHouse) myPart).getMachineId()));
        }
        else {
            partCompId.setText(String.valueOf(((Outsourced) myPart).getCompanyName()));
        }       
    }
        
    public void radioButtonClicked(){
                
        if(partType.getSelectedToggle().equals(inHouse)){
            compId.setText("Machine ID");
            partCompId.setPromptText("Mach ID");
        }
        else {
            compId.setText("Company Name");
            partCompId.setPromptText("Comp Name");

        }
        
        partCompId.setText("");
        
    }
    
    public void saveButtonClicked(ActionEvent event) throws Exception {
        
        int id = Integer.parseInt((partId.getCharacters().toString()));
        String name = partName.getCharacters().toString();
        int inv = Integer.parseInt(partInv.getCharacters().toString());
        double price = Double.parseDouble(partPrice.getCharacters().toString());
        int max = Integer.parseInt(partMax.getCharacters().toString());
        int min = Integer.parseInt(partMin.getCharacters().toString());
        int machId;
        String compName;
        
        Part myPart;
        
        if (partType.getSelectedToggle().equals(inHouse)){
            machId = Integer.parseInt(partCompId.getCharacters().toString());
            myPart = new InHouse(id,name,price,inv,min,max,machId);
        }
        else {
            compName = partCompId.getCharacters().toString();
            myPart = new Outsourced(id,name,price,inv,min,max,compName);
        }
        
        if (Inventory.lookupPart(id)!=null) {
           Inventory.deletePart(Inventory.lookupPart(id));
        }
        
        Inventory.addPart(myPart);
        
        cancelButtonClicked(event);

    }
    
    public void cancelButtonClicked(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainScreen.fxml"));
        Main.buttonClickOpen(loader,event);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partType = new ToggleGroup();
        inHouse.setToggleGroup(partType);
        outsourced.setToggleGroup(partType);
        
        partId.setEditable(false);
        
    }    
    
}

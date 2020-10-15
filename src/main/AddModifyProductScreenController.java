package main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

public class AddModifyProductScreenController implements Initializable {
    
    @FXML private TextField prodId;
    @FXML private TextField prodName;
    @FXML private TextField prodInv;
    @FXML private TextField prodPrice;
    @FXML private TextField prodMax;
    @FXML private TextField prodMin;
    
    @FXML private Label header;
    
    @FXML private TextField partsSearchTextField;
    
    @FXML private TableView<Part> partTableAll;
    @FXML private TableColumn<Part, String> partIdColAll;
    @FXML private TableColumn<Part, String> partNameColAll;
    @FXML private TableColumn<Part, String> partInvColAll;
    @FXML private TableColumn<Part, String> partPriceColAll;
    
    @FXML private TableView<Part> partTableAssc;
    @FXML private TableColumn<Part, String> partIdColAssc;
    @FXML private TableColumn<Part, String> partNameColAssc;
    @FXML private TableColumn<Part, String> partInvColAssc;
    @FXML private TableColumn<Part, String> partPriceColAssc;

    public void initData(){
        
        header.setText("Add Product");
        
        prodId.setText(String.valueOf(Inventory.assignProdId(0)));
    }
    
    public void initData(Product myProd){
        
        header.setText("Modify Product");
        
        prodId.setText(String.valueOf(myProd.getId()));
        prodName.setText(String.valueOf(myProd.getName()));
        prodInv.setText(String.valueOf(myProd.getStock()));
        prodPrice.setText(String.valueOf(myProd.getPrice()));
        prodMax.setText(String.valueOf(myProd.getMax()));
        prodMin.setText(String.valueOf(myProd.getMin()));
        
        partTableAssc.setItems(FXCollections.observableArrayList(myProd.getAllAssociatedParts()));
        
    }
    
    public void searchButtonClicked(){
        
        ObservableList<Part> returnedParts = 
                Inventory.lookupPart(partsSearchTextField.getText());
        
        if(!partsSearchTextField.getText().isEmpty()){
            partTableAll.setItems(returnedParts);
        }
        else {
            partTableAll.setItems(Inventory.getAllParts());
        }
        
    }
    
    public void addButtonClicked(){
        
        Part myPart = partTableAll.getSelectionModel().getSelectedItem();
        if (myPart == null) return;

        if (partTableAssc.getItems().contains(myPart)) return;
        
        partTableAssc.getItems().add(myPart);
        
    }
    
    public void deleteButtonClicked(){
        
        Part myPart = partTableAssc.getSelectionModel().getSelectedItem();
        if (myPart == null) return;
        
        partTableAssc.getItems().remove(myPart);

    }
    
    //Save button method.
    public void saveButtonClicked(ActionEvent event) throws Exception {
        
        if(partTableAssc.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error saving part");
            alert.setContentText("Please add at least one part to the product.");
            alert.showAndWait();
            return;
        }
        
        double partCost = 0;
        for(Part myPart:partTableAssc.getItems()){
            partCost+=myPart.getPrice();
        }
                     
        if(Double.parseDouble(prodPrice.getText()) < partCost){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error saving part");
            alert.setContentText("Product price cannot be less than the cost of the parts.");
            alert.showAndWait();
            return;
        }
        
        int id = Integer.parseInt((prodId.getCharacters().toString()));
        String name = prodName.getCharacters().toString();
        int inv = Integer.parseInt(prodInv.getCharacters().toString());
        double price = Double.parseDouble(prodPrice.getCharacters().toString());
        int max = Integer.parseInt(prodMax.getCharacters().toString());
        int min = Integer.parseInt(prodMin.getCharacters().toString());
        
        Product myProd = new Product(id,name,price,inv,min,max);
        for(Part myPart:partTableAssc.getItems()){
            myProd.addAssociatedPart(myPart);
        }
        
        if (Inventory.lookupProduct(id)!=null) {
           Inventory.deleteProduct(Inventory.lookupProduct(id));
        }
        
        Inventory.addProduct(myProd);
        
        cancelButtonClicked(event);
        
    }
    
    public void cancelButtonClicked(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainScreen.fxml"));
        Main.buttonClickOpen(loader,event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partIdColAll.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameColAll.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInvColAll.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partPriceColAll.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        partIdColAssc.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameColAssc.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInvColAssc.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partPriceColAssc.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        partTableAll.setItems(Inventory.getAllParts());
        
        partTableAll.setEditable(true);
        partTableAssc.setEditable(true);

        partTableAll.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        partTableAssc.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        prodId.setEditable(false);
        
    }    
    
}

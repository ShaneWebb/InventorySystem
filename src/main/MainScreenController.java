package main;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

public class MainScreenController implements Initializable {
    
    @FXML private TextField partsSearchTextField;
    @FXML private TextField productsSearchTextField;
    
    @FXML private TableView<Part> partTable;
    @FXML private TableColumn<Part, String> partIdCol;
    @FXML private TableColumn<Part, String> partNameCol;
    @FXML private TableColumn<Part, String> partInvCol;
    @FXML private TableColumn<Part, String> partPriceCol;
    
    @FXML private TableView<Product> prodTable;
    @FXML private TableColumn<Product, String> prodIdCol;
    @FXML private TableColumn<Product, String> prodNameCol;
    @FXML private TableColumn<Product, String> prodInvCol;
    @FXML private TableColumn<Product, String> prodPriceCol;
    
    public void partSearchButtonClicked() {
        
        ObservableList<Part> returnedParts = 
                Inventory.lookupPart(partsSearchTextField.getText());
        
        if(!partsSearchTextField.getText().isEmpty()){
            partTable.setItems(returnedParts);
        }
        else {
            partTable.setItems(Inventory.getAllParts());
        }
    }
    
    public void partAddButtonClicked(ActionEvent event) throws Exception {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddModifyPartScreen.fxml"));
        Main.buttonClickOpen(loader,event);

        AddModifyPartScreenController controller = loader.getController();
        controller.initData();
    }
    
    public void partModifyButtonClicked(ActionEvent event) throws Exception {
        
        Part myPart = partTable.getSelectionModel().getSelectedItem();
        if (myPart == null) return;
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddModifyPartScreen.fxml"));
        Main.buttonClickOpen(loader,event);

        AddModifyPartScreenController controller = loader.getController();
        controller.initData(myPart);

    }
    
    public void partDeleteButtonClicked() {

        Part item = partTable.getSelectionModel().getSelectedItem();
        Inventory.deletePart(item);
                       
    }

    public void productSearchButtonClicked() {
        
        ObservableList<Product> returnedProducts = 
                Inventory.lookupProduct(productsSearchTextField.getText());

        if(!productsSearchTextField.getText().isEmpty()){
            prodTable.setItems(returnedProducts);
        }
        else {
            prodTable.setItems(Inventory.getAllProducts());
        }
        
    }
    
    public void productAddButtonClicked(ActionEvent event) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddModifyProductScreen.fxml"));
        Main.buttonClickOpen(loader,event);

        AddModifyProductScreenController controller = loader.getController();
        controller.initData();
        
    }
    
    public void productModifyButtonClicked(ActionEvent event) throws Exception {
                
        Product myProd = prodTable.getSelectionModel().getSelectedItem();
        if (myProd == null) return;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("AddModifyProductScreen.fxml"));
        Main.buttonClickOpen(loader,event);

        AddModifyProductScreenController controller = loader.getController();
        controller.initData(myProd);
    }

    public void productDeleteButtonClicked() {

        Product item = prodTable.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(item);
                
    }

    public void exitButtonClicked() {
        Platform.exit();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        
        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

        partTable.setItems(Inventory.getAllParts());
        prodTable.setItems(Inventory.getAllProducts());

        partTable.setEditable(true);
        prodTable.setEditable(true);

        partTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        prodTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }    
    
}

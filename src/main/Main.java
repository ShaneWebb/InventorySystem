package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        
        Inventory.addPart(new InHouse(1,"Uranium",3000,20,0,100,77));
        Inventory.addPart(new InHouse(2,"Halfnium",1000,18,0,10,76));
        Inventory.addPart(new Outsourced(3,"Dielectric Mirror",100000,0,0,1,"Optotronics"));
        Inventory.addPart(new Outsourced(4,"Room Temp Superconductor",1000000,2,1,10,"NT Labs"));
        
        Inventory.addProduct(new Product(1,"U 233",3000,12,0,100));
        Inventory.addProduct(new Product(2,"Electrode",1300000,1,0,1));
        Inventory.addProduct(new Product(3,"Doomsday Device",2000000,0,0,1));

        Inventory.lookupProduct("U 233").get(0).addAssociatedPart(
                Inventory.lookupPart("Uranium").get(0));

        Inventory.lookupProduct("Electrode").get(0).addAssociatedPart(
                Inventory.lookupPart("Halfnium").get(0));
        
        Inventory.lookupProduct("Electrode").get(0).addAssociatedPart(
                Inventory.lookupPart("Room Temp Superconductor").get(0));

        Inventory.lookupProduct("Doomsday Device").get(0).addAssociatedPart(
                Inventory.lookupPart("Uranium").get(0));
        
        Inventory.lookupProduct("Doomsday Device").get(0).addAssociatedPart(
                Inventory.lookupPart("Dielectric Mirror").get(0));
        
        Inventory.lookupProduct("Doomsday Device").get(0).addAssociatedPart(
                Inventory.lookupPart("Room Temp Superconductor").get(0));
                
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void buttonClickOpen (FXMLLoader loader, ActionEvent event) 
            throws Exception {
        
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
    
}

package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    public static Part lookupPart(int partId){

        for(Part myPart: allParts){
            if(partId == myPart.getId()){return myPart;}
        }
        return null;
    }
    
    public static Product lookupProduct(int productId){

        for(Product myProduct: allProducts){
            if(productId == myProduct.getId()){return myProduct;}
        }
        return null;
    }
        
    public static ObservableList<Part> lookupPart(String partName){
        
        ObservableList<Part> resultsList = FXCollections.observableArrayList();

        for(Part myPart: allParts){
            if(myPart.getName().contains(partName)){resultsList.add(myPart);}
        }
        return resultsList;
    }
    
    public static ObservableList<Product> lookupProduct (String productName){
        
        ObservableList<Product> resultsList = FXCollections.observableArrayList();

        for(Product myProduct: allProducts){
            if(myProduct.getName().contains(productName)){resultsList.add(myProduct);}
        }
        return resultsList;
    }
    
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
    
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }
    
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }
    
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    
    public static int assignPartId(int trialId){
        int val = trialId;
        
        while(lookupPart(val) != null){
            val++;
        }
        
        return val;
    }
    
    public static int assignProdId(int trialId){
        int val = trialId;
        
        while(lookupProduct(val) != null){
            val++;
        }
        
        return val;
    }
    
}

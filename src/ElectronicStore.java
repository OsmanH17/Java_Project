//Class representing an electronic store
//Has an array of products that represent the items the store can sell
import java.util.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private double revenue;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
    }

    public String getName() {
        return name;
    }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            stock[curProducts] = newProduct;
            curProducts++;
            return true;
        }
        return false;
    }
    public void setRevenue(double amount){revenue = amount;}
    public double getRevenue(){return revenue;}

    public Product[] getproductlist(){
        Product []  listofproducts = new Product[stock.length];
        for (int i=0; i< stock.length;i++){
            if(stock[i] != null){
                listofproducts[i] = stock[i];
            }
        }
        return listofproducts;
    }

    public ArrayList<Product> getmostpoplist(){
        Product [] copylist = this.getproductlist();
        ArrayList<Product> tobesorted = new ArrayList<Product>();
        for (Product p : copylist){
            if (p != null) {
                tobesorted.add(p);
            }
        }

        Collections.sort(tobesorted, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Integer.compare(p2.getSoldQuantity(), p1.getSoldQuantity());
            }
        });

        // Create a list for the top 3 products
        while(tobesorted.size()>3){
            tobesorted.remove(tobesorted.size()-1);
        }

        return tobesorted;
    }

    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }
}

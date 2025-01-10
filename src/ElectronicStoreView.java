
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ElectronicStoreView extends Pane  {
    private TextField nField, rField, sField;
    private ListView<Product> mpList, ssList;
    private ListView<String>cList;
    private ButtonPane buttonPane;
    private double amountprofit ;        // Potential total sale for cart
    private Label label7;
    private ArrayList<Product> itemsremoved;

    public double getAmountprofit(){return amountprofit;}
    public void setAmountprofit(double amount){amountprofit = amount;}
    public Label getLabel7(){return label7;}
    public ArrayList<Product> getItemsremoved(){return itemsremoved;}

    public ListView<Product> getMpList() {return mpList;}
    public ListView<Product> getssList() {return ssList;}
    public ListView<String> getcList() {return cList;}

    public TextField getnField() {return nField;}
    public TextField getrField() {return rField;}
    public TextField getsField() {return sField;}

    public ButtonPane getbuttonpane() {return buttonPane;}

    public double dispalaytotalamount(){
        double total=0.00;
        int quantity;
        Product p=null;
        if(!(cList.getItems().isEmpty())){
            for (int i=0; i< cList.getItems().size();i++) {
                for (int j=0; j< ssList.getItems().size();j++){
                    if(cList.getItems().get(i).endsWith(ssList.getItems().get(j).toString())){
                        p = ssList.getItems().get(j);
                        break;
                    }
                }
                String str = cList.getItems().get(i);
                String[] parts = str.split("X");
                String partBeforeX = parts[0];
                quantity = Integer.parseInt(partBeforeX);
                if(p!=null) {
                    total = total + (quantity * p.getPrice());
                }else {total = total +0;}

            }
        }

        return total;
    }

    public void update(ElectronicStore model){
        Product [] listofproducts = model.getproductlist();

        ArrayList<Product> storestock = new ArrayList<Product>();
        for (Product p : listofproducts){
            if (p != null) {
                storestock.add(p);
            }
        }

        ssList.setItems(FXCollections.observableArrayList(storestock));
        mpList.setItems(FXCollections.observableArrayList(model.getmostpoplist()));

    }

    public void updatemp(ElectronicStore model){
        mpList.setItems(FXCollections.observableArrayList(model.getmostpoplist()));
    }


    public ElectronicStoreView(){
        amountprofit = 0;
        Label label1 = new Label("Store Summary:");
        label1.relocate(30,10);
        Label label2 = new Label("# Sales:");
        label2.relocate(20,35);
        Label label3 = new Label("Revenue:");
        label3.relocate(15, 65);
        Label label4 = new Label("$ / Sale:");
        label4.relocate(20,95);
        Label label5 = new Label("Most Popular Items:");
        label5.relocate(20,120);
        Label label6 = new Label("Store Stock:");
        label6.relocate(280, 10);
        label7 = new Label("Current Cart: ($"+ amountprofit+ ')');
        label7.relocate(610,10);

        itemsremoved = new ArrayList<Product>();


        nField = new TextField();
        nField.relocate(70,30);
        nField.setPrefSize(75,10);
        nField.setEditable(false);
        nField.setText("0");

        rField = new TextField();
        rField.relocate(70,60);
        rField.setPrefSize(75,10);
        rField.setEditable(false);
        rField.setText("0.00");

        sField = new TextField();
        sField.relocate(70,90);
        sField.setPrefSize(75,10);
        sField.setEditable(false);
        sField.setText("N/A");

        mpList = new ListView<Product>();
        mpList.relocate(5,145);
        mpList.setPrefSize(140,185);

        ssList = new ListView<Product>();
        ssList.setPrefSize(300,300);
        ssList.relocate(165,30);

        cList = new ListView<String>();
        cList.setPrefSize(300,300);
        cList.relocate(475, 30);

        buttonPane = new ButtonPane();
        buttonPane.relocate(15,340);
        buttonPane.setPrefSize(780,100);
        buttonPane.getaddcartbutton().setDisable(true);
        buttonPane.getremovebutton().setDisable(true);
        buttonPane.getcompletebutton().setDisable(true);

        getChildren().addAll(label1,label2,label3,label4,label5,label6,label7,nField, rField, sField, mpList, ssList, cList, buttonPane);

        setPrefSize(800,400);


    }
    public void resetview(){
        amountprofit = 0;
        nField.setText("0");
        rField.setText("0.00");
        sField.setText("N/A");
        itemsremoved = new ArrayList<Product>();

        buttonPane.getaddcartbutton().setDisable(true);
        buttonPane.getremovebutton().setDisable(true);
        buttonPane.getcompletebutton().setDisable(true);

    }
}

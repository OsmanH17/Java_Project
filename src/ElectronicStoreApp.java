import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ElectronicStoreApp extends Application {
    private ElectronicStore model;
    public ElectronicStoreApp(){
        model = ElectronicStore.createStore();
    }
    @Override
    public void start(Stage primaryStage){
        Pane aPane = new Pane();

        ElectronicStoreView view = new ElectronicStoreView();

        aPane.getChildren().add(view);
        view.update(model);

        view.getssList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                Product slected = view.getssList().getSelectionModel().getSelectedItem();
                if (slected != null){
                    view.getbuttonpane().getaddcartbutton().setDisable(false);
                    view.getbuttonpane().getaddcartbutton().setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionEvent) {
                            if(view.getcList().getItems().toString().contains(slected.toString())) {
                                int index =-1; // in the case the item is not found

                                for (int i = 0; i < view.getcList().getItems().size(); i++) {
                                    if (view.getcList().getItems().get(i).toString().contains(slected.toString())) {
                                        index = i;
                                        break;
                                    }
                                }
                                int quantity;
                                String str = view.getcList().getItems().get(index);
                                String[] parts = str.split("X");
                                String partBeforeX = parts[0];
                                quantity = Integer.parseInt(partBeforeX);
                                quantity +=1;
                                slected.decreasestock(1);

                                view.getcList().getItems().set(index,quantity+"X "+slected.toString());
                                view.setAmountprofit(view.dispalaytotalamount());
                                view.getLabel7().setText("Current Cart: ($"+view.getAmountprofit()+")");

                            }else {
                                view.getcList().getItems().add("1X "+slected.toString());
                                slected.decreasestock(1);
                                view.setAmountprofit( view.dispalaytotalamount());
                                view.getLabel7().setText("Current Cart: ($"+view.getAmountprofit()+")");
                            }
                            if(slected.getStockQuantity() == 0){
                                view.getItemsremoved().add(slected);
                                view.getssList().getItems().remove(slected);
                            }

                        }
                    });
                }
            }
        });

        view.getcList().getItems().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                int revenueamount;
                view.getbuttonpane().getcompletebutton().setDisable(view.getcList().getItems().isEmpty());
                view.getbuttonpane().getcompletebutton().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        int quantity;
                        Product producttoincrese;
                        producttoincrese = null;
                        for (int k =0;k<view.getItemsremoved().size();k++){
                            for (int i=0; i< view.getcList().getItems().size();i++) {
                                if (view.getcList().getItems().get(i).endsWith(view.getItemsremoved().get(k).toString())) {
                                    producttoincrese = view.getItemsremoved().get(k);
                                    String str = view.getcList().getItems().get(i);
                                    String[] parts = str.split("X");
                                    String partBeforeX = parts[0];
                                    quantity = Integer.parseInt(partBeforeX);
                                    producttoincrese.increaseSoldquantity(quantity);
                                }
                            }
                        }
                        if (producttoincrese == null) {
                            for (int i = 0; i < view.getssList().getItems().size(); i++) {
                                for (int j = 0; j < view.getcList().getItems().size(); j++) {
                                    if (view.getcList().getItems().get(j).endsWith(view.getssList().getItems().get(i).toString())) {
                                        producttoincrese = view.getssList().getItems().get(i);
                                        String str = view.getcList().getItems().get(j);
                                        String[] parts = str.split("X");
                                        String partBeforeX = parts[0];
                                        quantity = Integer.parseInt(partBeforeX);

                                        for (int k = 0; k < view.getItemsremoved().size(); k++) {
                                            if (view.getcList().getItems().get(j).endsWith(view.getItemsremoved().get(k).toString())) {
                                                producttoincrese = view.getItemsremoved().get(k);
                                            }
                                        }
                                        producttoincrese.increaseSoldquantity(quantity);
                                    }
                                }
                            }
                        }
                        int saleamount=0;
                        if(view.getrField().getText().contains("0.00")) {
                            view.getrField().setText(String.valueOf(view.getAmountprofit()));
                            model.setRevenue(view.getAmountprofit());
                        }else {

                            double rev = Double.parseDouble(view.getrField().getText());

                            double amounttoadd = Double.parseDouble(String.valueOf(view.getAmountprofit()));
                            model.setRevenue(rev + amounttoadd);
                            view.getrField().setText(String.valueOf(model.getRevenue()));
                        }
                        view.setAmountprofit(0);
                        if (view.getnField().getText().contains("0")) {
                            view.getnField().setText("1");
                        }else {
                            saleamount = Integer.parseInt(view.getnField().getText());
                            saleamount += 1;
                            view.getnField().setText(String.valueOf(saleamount));
                        }
                        saleamount = Integer.parseInt(view.getnField().getText());
                        System.out.println(model.getRevenue());

                        double avarage =  model.getRevenue()/(double) saleamount;

                        view.getsField().setText(String.valueOf(avarage));
                        view.getLabel7().setText("Current Cart: ($"+view.getAmountprofit()+")");
                        view.getcList().getItems().clear();
                        view.updatemp(model);

                    }
                });
            }
        });

        view.getcList().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String slected = view.getcList().getSelectionModel().getSelectedItem();
                if (slected != null){
                    view.getbuttonpane().getremovebutton().setDisable(false);
                    view.getbuttonpane().getremovebutton().setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionEvent) {
                            int quantity,index;
                            Product producttoincrese;
                            producttoincrese = null;
                                for (int i = 0; i < view.getssList().getItems().size(); i++) {
                                    if (slected.endsWith(view.getssList().getItems().get(i).toString())) {
                                        producttoincrese = view.getssList().getItems().get(i);
                                        break;
                                    }
                                }
                                if (producttoincrese != null) {
                                    producttoincrese.increasestock(1);
                                    String str = slected;
                                    String[] parts = str.split("X");
                                    String partBeforeX = parts[0];
                                    quantity = Integer.parseInt(partBeforeX);
                                    quantity -=1;
                                    if (quantity == 0){
                                        view.getcList().getItems().remove(slected);
                                        view.setAmountprofit(view.getAmountprofit() - producttoincrese.getPrice());
                                        view.getLabel7().setText("Current Cart: ($" + view.getAmountprofit() + ")");
                                    }
                                    if (quantity>0) {
                                        index = view.getcList().getItems().indexOf(slected);
                                        view.getcList().getItems().set(index, quantity + "X " + slected.substring(slected.indexOf('X') + 1));
                                        view.setAmountprofit(view.getAmountprofit() - producttoincrese.getPrice()); // deduct here !!!
                                        view.getLabel7().setText("Current Cart: ($" + view.getAmountprofit() + ")");
                                    }
                                }

                            if (producttoincrese == null){
                                for(int i=0; i<view.getItemsremoved().size(); i++){
                                    if (slected.endsWith(view.getItemsremoved().get(i).toString())){
                                        producttoincrese = view.getItemsremoved().get(i);
                                    }
                                }
                                view.getssList().getItems().add(producttoincrese);
                                producttoincrese.increasestock(1);
                                String str = slected;
                                String[] parts = str.split("X");
                                String partBeforeX = parts[0];
                                quantity = Integer.parseInt(partBeforeX);
                                quantity -=1;
                                if (quantity == 0){
                                    view.getcList().getItems().remove(slected);
                                }
                                index = view.getcList().getItems().indexOf(slected);
                                view.getcList().getItems().set(index, quantity + "X " + slected.substring(slected.indexOf('X')+1));
                                view.setAmountprofit(view.getAmountprofit() - producttoincrese.getPrice()); // deduct here !!!
                                view.getLabel7().setText("Current Cart: ($" + view.getAmountprofit() + ")");
                            }
                        }
                    });
                }else {
                    view.getbuttonpane().getremovebutton().setDisable(true);
                }
            }
        });

        view.getbuttonpane().getresetbutton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                model = ElectronicStore.createStore();
                view.resetview();
                view.update(model);

            }
        });



        primaryStage.setTitle("Electronic Store Application - "+model.getName());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}

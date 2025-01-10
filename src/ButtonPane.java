import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class ButtonPane extends Pane {
    private Button resetbutton, addcartbutton, removebutton, completebutton;

    public Button getresetbutton (){ return resetbutton;}

    public Button getaddcartbutton (){ return addcartbutton;}
    public Button getremovebutton (){ return removebutton;}
    public Button getcompletebutton (){ return completebutton;}

    public ButtonPane(){
        Pane innerpane = new Pane();

        removebutton = new Button("Remove from Cart");
        addcartbutton = new Button("Add to cart");
        resetbutton = new Button("Reset Store");
        completebutton = new Button("Complete Sale");

        resetbutton.relocate(0,0);
        resetbutton.setPrefSize(120,40);


        addcartbutton.setPrefSize(150,40);
        addcartbutton.relocate(230,0);


        removebutton.relocate(460,0);
        removebutton.setPrefSize(150,40);


        completebutton.setPrefSize(150,40);
        completebutton.relocate(610,0);


        innerpane.getChildren().addAll(resetbutton, addcartbutton, removebutton, completebutton);
        getChildren().addAll(innerpane);
    }
}

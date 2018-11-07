package controller;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;


public class TrainStation {

    @FXML
    private AnchorPane ancPane;

    @FXML
    private Button btnAddTrain;

    @FXML
    private Button btnAddPassenger;

    @FXML
    private ComboBox<String> cbxDptStation;

    @FXML
    private ComboBox<String> cbxArrivalStation;

    @FXML
    private ListView<String> LVFeed;
    
    @FXML
    private Label lblStation1;

    @FXML
    private Label lblStation2;

    @FXML
    private Label lblStation3;

    @FXML
    private Label lblStation4;

    @FXML
    private Label lblStation8;

    @FXML
    private Label lblStation7;

    @FXML
    private Label lblStation6;

    @FXML
    private Label lblStation5;
    
    private ObservableList<String> stations = FXCollections.observableArrayList();
    private ArrayList<ImageView> trains = new ArrayList<ImageView>();
    @FXML
    private ImageView iv1;
    
    @FXML
    public void initialize() {
    	stations.add("Station1");
    	stations.add("Station2");
    	stations.add("Station3");
    	stations.add("Station4");
    	stations.add("Station5");
    	stations.add("Station6");
    	stations.add("Station7");
    	stations.add("Station8");
    	
    	cbxArrivalStation.setItems(stations);
    	cbxDptStation.setItems(stations);
    }
    

    @FXML
    public void addPassenger() {
    	moveTrain();
    }

    @FXML
    public void addTrain() {
    	iv1 = new ImageView(new Image("Images/train22.png"));
    	iv1.setX(-100); // train station 1-4
    	iv1.setY(250); // train station 5-8
    	ImageView iv2 = new ImageView(new Image("Images/boy.png"));
    	Random r = new Random();
    
//    	iv2.setY(220); // train station 1 - 4
    	iv2.setY(515); // train station 5 - 8
//    	iv2.setX(r.nextInt((210 - 60) + 1) + 60); // Train station 1 passenger randomizer7
//    	iv2.setX(r.nextInt((540 - 390) + 1) + 390); //Train Station 2 passenger randomizer
//    	iv2.setX(r.nextInt((870 - 720) + 1) + 720); // train station 3 
    	iv2.setX(r.nextInt((1200 - 1050) + 1) + 1050); // train station 4
    	ancPane.getChildren().addAll(iv1);
    	LVFeed.getItems().add("Successfuly Added Train1");
    	LVFeed.getItems().add("Successfully Added Passenger1");
    	lblStation1.setText(Integer.toString(1));
    	moveTrain();
    }
    
    public void moveTrain() {
    	TranslateTransition trans = new TranslateTransition();
	    trans.stop();
	    trans.setDuration(Duration.seconds(1));
	    trans.setNode(iv1);
	    
	    if(iv1.getX() == - 100) {
	    	trans.setByX(170);
	    }
	    else if(iv1.getX() == 70) {
	    	trans.setByX(160);	
	    }
	    else if(iv1.getX() == 230) {
	    	trans.setByX(170);
	    }
	    else if(iv1.getX() == 400) {
	    	trans.setByX(160);
	    }
	    else if(iv1.getX() == 900) {
	    	trans.setByX(160);
	    }
	    else
	    	trans.setByX(170);
	    trans.setOnFinished(event -> {
	    	
	        // The transition works by manipulating translation values,
	        // After the transition is complete, move the node to the new location
	        // and zero the translation after relocating the node.
	    	if(iv1.getX() == 1040) {
	    		System.out.println("Aaaaa");
	    		iv1.setX(-10);
	    		iv1.setY(560);
		        iv1.setTranslateX(0);
		        iv1.setTranslateY(0);
	    	}
	    	else {
	    		System.out.println("bbb");
	    		iv1.setX(iv1.getX() + iv1.getTranslateX());
		        iv1.setY(iv1.getY() + iv1.getTranslateY());
		        iv1.setTranslateX(0);
		        iv1.setTranslateY(0);
	    	}
	        System.out.println(iv1.getX());	
	    });
	    trans.play();
    }

}

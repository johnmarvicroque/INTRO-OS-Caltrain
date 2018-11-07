package application;

import controller.TrainSimulator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

		// TODO Auto-generated method stub
		@Override
		public void start(Stage primaryStage) {
			try {
			    Parent root = FXMLLoader.load(getClass().getResource("/views/TrainStation.fxml")); 
				Scene scene = new Scene(root);
				
				primaryStage.setScene(scene);
				primaryStage.setTitle("CALTRAIN INTR-OS");
				primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
			primaryStage.setOnCloseRequest(event -> System.exit(0));
		}
		public static void main(String[] args) {
			launch(args);
		}	
}
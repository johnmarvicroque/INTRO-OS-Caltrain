package application;

import controller.TrainSimulator;


public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TrainSimulator ts = new TrainSimulator();
		
		ts.start();
		
//		//@param station number where passenger will arrive, 
//		//station number where passenger will get off	
//		ts.station_wait_for_train(1, 7);
//		ts.station_wait_for_train(1, 3);
//		ts.station_wait_for_train(1, 4);
//		ts.station_wait_for_train(1, 5);
//		ts.station_wait_for_train(1, 6);
//		ts.station_wait_for_train(1, 7);
//		ts.station_wait_for_train(7, 8);
//		//@param number of seats in a train
		ts.station_load_train(14);
		

		
	}

}
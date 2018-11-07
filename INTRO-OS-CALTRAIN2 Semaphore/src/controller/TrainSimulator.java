package controller;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import models.Passenger;
import models.RailBetween;
import models.Station;
import models.Train;

public class TrainSimulator implements Runnable{
	
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
    private TextField txtPassengerNo;

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
    
    private ObservableList<String> cbxStations = FXCollections.observableArrayList();
//    private ArrayList<ImageView> trains = new ArrayList<ImageView>();
	
	ArrayList<Station> stations = new ArrayList<Station>();
	ArrayList<RailBetween> railsBetween = new ArrayList<RailBetween>();
	ArrayList<Train> trains = new ArrayList<Train>();
	Thread t;
	String threadName;
	Boolean isThreadDone = false;
	Semaphore sem = new Semaphore(1);
	
	int nextStationIndex = 0;

	int nextRailIndex = 0;
	
	Station s1;
	Station s2;
	Station s3;
	Station s4;
	Station s5;
	Station s6;
	Station s7;
	Station s8;
	
	RailBetween rail0;
	RailBetween rail1;
	RailBetween rail2;
	RailBetween rail3;
	RailBetween rail4;
	RailBetween rail5;
	RailBetween rail6;
	RailBetween rail7;

	
	public TrainSimulator() {
		threadName = "TrainSimulatorThread";
		//Legit train stations
		s1 = new Station(1);
		s2 = new Station(2);
		s3 = new Station(3);
		s4 = new Station(4);
		s5 = new Station(5);
		s6 = new Station(6);
		s7 = new Station(7);
		s8 = new Station(8);
		
		//rail0 means before station 1
		//rail 1 means before station 2
		rail0 = new RailBetween(0);
		rail1 = new RailBetween(1);
		rail2 = new RailBetween(2);
		rail3 = new RailBetween(3);
		rail4 = new RailBetween(4);
		rail5 = new RailBetween(5);
		rail6 = new RailBetween(6);
		rail7 = new RailBetween(7);
		
		//add station to list
		stations.add(s1);
		stations.add(s2);
		stations.add(s3);
		stations.add(s4);
		stations.add(s5);
		stations.add(s6);
		stations.add(s7);
		stations.add(s8);
		
		//add rails between stations to list
		railsBetween.add(rail0);
		railsBetween.add(rail1);
		railsBetween.add(rail2);
		railsBetween.add(rail3);
		railsBetween.add(rail4);
		railsBetween.add(rail5);
		railsBetween.add(rail6);
		railsBetween.add(rail7);
	}
	
    @FXML
    public void initialize() {
    	cbxStations.add("1");
    	cbxStations.add("2");
    	cbxStations.add("3");
    	cbxStations.add("4");
    	cbxStations.add("5");
    	cbxStations.add("6");
    	cbxStations.add("7");
    	cbxStations.add("8");
    	
    	cbxArrivalStation.setItems(cbxStations);
    	cbxDptStation.setItems(cbxStations);
    	this.start();
    }
	
	////////////////////////////THREAD FUNCTION///////////////////
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized(this) {
			try {	
				//get lock
				while(!isThreadDone) {
					sem.acquire();
					//System.out.println("LOCKED: " + threadName);
					//traverse thru all trains
					for(int i = 0; i < trains.size(); i++) {
						if(!trains.get(i).getIsThreadDone()){
							if(!trains.get(i).getIsAnimating()) {
								//check if train is in a Station
								if(trains.get(i).getStation() != null) {
									
									//next station 
									nextStationIndex = trains.get(i).getStation().getStationNumber();
									//next rail
									nextRailIndex = trains.get(i).getStation().getStationNumber();
									
									//check if the train is not in the last station or station number 8
									if(trains.get(i).getStation().getStationNumber() < 8) {
										//check if train is done loading passengers
				////////////////////////NEWLY ADDED CODES///////////////////////////////
										if(trains.get(i).getIsDoneLoading()) {
					////////////////////////NEWLY ADDED CODES///////////////////////////////
											//check if there is train on the NEXT STATION
											if(!stations.get(nextStationIndex).isThereTrain()) {
												//move train to the NEXT STATION
												
												if(trains.get(i).getRail() == null) {
													// TODO: call thread animation of train leaving and arriving station////////////////////////////////////////////
													//CALL THIS FROM t.animateStationToStation();
													trains.get(i).setIsAnimating(true);
													animateStationToStation(trains.get(i));
												}
												else {
													// TODO: call thread animation of train leaving rail and arriving station/////////////////////////////////////
													//CALL THIS FROM t.animateRailToStation();
												}
												
												System.out.println(trains.get(i).getThreadName() + " Left Station " + stations.get(nextStationIndex-1).getStationNumber() + " SEATS: " + trains.get(i).getSeatCount());				
												System.out.println(trains.get(i).getThreadName() + " Arrived Station " + stations.get(nextStationIndex).getStationNumber() + " SEATS: " + trains.get(i).getSeatCount());
												
												trains.get(i).setStation(stations.get(nextStationIndex));
												stations.get(nextStationIndex).setTrain(trains.get(i));
												stations.get(nextStationIndex-1).setTrain(null);
												railsBetween.get(nextRailIndex-1).setTrain(null);
												trains.get(i).setIsDoneLoading(false);
						////////////////////////NEWLY ADDED CODES///////////////////////////////
												trains.get(i).setIsDoneUnloading(false);
						////////////////////////NEWLY ADDED CODES///////////////////////////////
												trains.get(i).notifyPassengers();
												
											}
											//else check if there is train on the NEXT RAIL
//												else if(!railsBetween.get(nextRailIndex).isThereTrain()) {
//													
//													
//													// TODO: call thread animation of train leaving and going to a rail//////////////////////////////////////////////////
//													//CALL THIS FROM t.animateStationToRail();
//													
//													//move train to the NEXT RAIL
//													System.out.println("WAAAAAAAAAAIIIIIIIIIITTTTTIIIINNNNGGG");
//													System.out.println(trains.get(i).getThreadName() + " Leaving Station " + stations.get(nextStationIndex-1).getStationNumber() + " SEATS: " + trains.get(i).getSeatCount());
//													stations.get(nextStationIndex-1).setTrain(null);
//													trains.get(i).setRail(railsBetween.get(nextStationIndex));
//													railsBetween.get(nextStationIndex).setTrain(trains.get(i));
//												}
//												//else let late comer passengers get in if there is still seat left
//												else if(trains.get(i).getSeatCount() > 0 && trains.get(i).getRail() == null){
//													trains.get(i).setIsDoneLoading(false);
//												}
										}
									}
									//else the train is in the last station
									else if(trains.get(i).getStation().getStationNumber() == 8){
										if(trains.get(i).getSeatCount() == trains.get(i).getOriginalSeatCount()) {
											//end train thread
											System.out.println("STATION 8");
											trains.get(i).notifyPassengers();
											trains.get(i).setIsAnimating(true);
											animateStationToStation(trains.get(i));
											stations.get(nextStationIndex-1).setTrain(null);
											railsBetween.get(nextRailIndex-1).setTrain(null);
											trains.get(i).setIsThreadDone(true);
											trains.remove(trains.get(i));
										}
									}
								}
								//else train has not been deployed yet
								else {
									
									// TODO put animation of going to the FIRST Station////////////////////////////////
									//CALL THIS FROM t.animateStationToStation();
									
									//check if there is train on the FIRST station
									if(!stations.get(0).isThereTrain()) {
										trains.get(i).setIsAnimating(true);
										animateStationToStation(trains.get(i));
										trains.get(i).setStation(stations.get(0));
										stations.get(0).setTrain(trains.get(i));
									}
									// TODO put animation of going to the FIRST Rail////////////////////////////////
									//CALL THIS FROM t.animateStationToRail();
									
									//check if there is train on the FIRST rail
//										else if(!railsBetween.get(0).isThereTrain()) {
//											trains.get(i).setRail(railsBetween.get(0));
//											railsBetween.get(0).setTrain(trains.get(i));
//											System.out.println("RAIL DAPAT TO");
//										}
								}
							}
						}
					}
//						Thread.sleep(1000);
					//exit protocol
					sem.release();
					//System.out.println("UNLOCKED: " + threadName);
					
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				sem.release();
			} finally{
				sem.release();
				System.out.println("DONE THREAD " + threadName);
			}
		}
	}
	
	public void start () {
	      System.out.println("Starting " + threadName);
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	}
	
	@FXML
    public void addPassenger() {
		this.station_wait_for_train(Integer.parseInt(cbxDptStation.getSelectionModel().getSelectedItem()), Integer.parseInt(cbxArrivalStation.getSelectionModel().getSelectedItem()));
		
    }

    @FXML
    public void addTrain() {
    	this.station_load_train(Integer.parseInt(txtPassengerNo.getText()));
//    	iv1 = new ImageView(new Image("Images/train22.png"));
//    	iv1.setX(-100); // train station 1-4
//    	iv1.setY(545); // train station 5-8
//    	ImageView iv2 = new ImageView(new Image("Images/boy.png"));
//    	Random r = new Random();
//    
////    	iv2.setY(220); // train station 1 - 4
//    	iv2.setY(515); // train station 5 - 8
////    	iv2.setX(r.nextInt((210 - 60) + 1) + 60); // Train station 1 passenger randomizer7
////    	iv2.setX(r.nextInt((540 - 390) + 1) + 390); //Train Station 2 passenger randomizer
////    	iv2.setX(r.nextInt((870 - 720) + 1) + 720); // train station 3 
//    	iv2.setX(r.nextInt((1200 - 1050) + 1) + 1050); // train station 4
//    	ancPane.getChildren().addAll(iv1);
//    	LVFeed.getItems().add("Successfuly Added Train1");
//    	LVFeed.getItems().add("Successfully Added Passenger1");
//    	lblStation1.setText(Integer.toString(1));
    }
    
//    public void moveTrainToStation(Train train) {
//    	TranslateTransition trans = new TranslateTransition();
//	    trans.stop();
//	    trans.setDuration(Duration.seconds(2));
//	    trans.setNode(train.getPic());
//	    
//	    if(train.getPic().getX() == - 100) {
//	    	trans.setByX(170);
//	    }
//	    else if(train.getPic().getX() == 70) {
//	    	trans.setByX(160);	
//	    }
//	    else if(train.getPic().getX() == 230) {
//	    	trans.setByX(170);
//	    }
//	    else if(train.getPic().getX() == 400) {
//	    	trans.setByX(160);
//	    }
//	    else if(train.getPic().getX() == 900) {
//	    	trans.setByX(160);
//	    }
//	    else
//	    	trans.setByX(170);
//	    trans.setOnFinished(event -> {
//	    	
//	        // The transition works by manipulating translation values,
//	        // After the transition is complete, move the node to the new location
//	        // and zero the translation after relocating the node.
//	    	if(train.getPic().getX() == 1040) {
//	    		System.out.println("Aaaaa");
//	    		train.getPic().setX(-10);
//	    		train.getPic().setY(560);
//		        train.getPic().setTranslateX(0);
//		        train.getPic().setTranslateY(0);
//	    	}
//	    	else {
//	    		System.out.println("bbb");
//	    		train.getPic().setX(train.getPic().getX() + train.getPic().getTranslateX());
//		        train.getPic().setY(train.getPic().getY() + train.getPic().getTranslateY());
//		        train.getPic().setTranslateX(0);
//		        train.getPic().setTranslateY(0);
//	    	}
//	        System.out.println(train.getPic().getX());	
//	    });
//	    trans.play();
//    }
	
	////////////////////////////METHODS///////////////////
	
	public void station_wait_for_train(int stationStart, int stationEnd) {
		Random r = new Random();
		for(Station s : stations) {
			if(s.getStationNumber() == stationStart) {
				Passenger p = new Passenger(s, stationStart, stationEnd, sem);
				ImageView img = new ImageView(new Image("Images/boy.png"));
				if(stationStart == 1) {
					img.setY(220);
					img.setX(r.nextInt((210 - 60) + 1) + 60);
				}
				else if(stationStart == 2) {
					img.setX(r.nextInt((540 - 390) + 1) + 390);
					img.setY(220);
				}
				else if(stationStart == 3) {
					img.setY(220);
					img.setX(r.nextInt((870 - 720) + 1) + 720);
				}
				else if(stationStart == 4) {
					img.setY(220);
					img.setX(r.nextInt((1200 - 1050) + 1) + 1050);
				}
				else if(stationStart == 5) {
					img.setY(515);
					img.setX(r.nextInt((210 - 60) + 1) + 60);
				}
				else if(stationStart == 6) {
					img.setY(515);
					img.setX(r.nextInt((540 - 390) + 1) + 390);
				}
				else if(stationStart == 7) {
					img.setX(r.nextInt((870 - 720) + 1) + 720);
					img.setY(515);
				}
				else if(stationStart == 8) {
					img.setX(r.nextInt((1200 - 1050) + 1) + 1050);
					img.setY(515);
				}
				p.setPic(img);
				p.setMainSimulator(this);
				ancPane.getChildren().add(p.getPic());
				updateLabels();
				System.out.println(stations.get(0).getPassengerCount());
				p.start();
			}
		}
	}
	
	public void updateLabels() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				  lblStation1.setText(Integer.toString(stations.get(0).getPassengerCount()));
				  lblStation2.setText(Integer.toString(stations.get(1).getPassengerCount()));
				  lblStation3.setText(Integer.toString(stations.get(2).getPassengerCount()));
				  lblStation4.setText(Integer.toString(stations.get(3).getPassengerCount()));
				  lblStation5.setText(Integer.toString(stations.get(4).getPassengerCount()));
				  lblStation6.setText(Integer.toString(stations.get(5).getPassengerCount()));
				  lblStation7.setText(Integer.toString(stations.get(6).getPassengerCount()));
				  lblStation8.setText(Integer.toString(stations.get(7).getPassengerCount()));
			}
		});

	}
	
	
	public void station_load_train(int seatCount) {
	
		Train t = new Train(seatCount, sem);
		ImageView img = new ImageView(new Image("Images/train22.png"));
		img.setX(-260);
		img.setY(250);
		t.setPic(img);
		t.setMainSimulator(this);
		trains.add(t);
		ancPane.getChildren().add(t.getPic());
		t.start();
	}
	
	public void animateStationToStation(Train train){
		TranslateTransition trans = new TranslateTransition();
		System.out.println(" CURRENT");
		trans.setDuration(Duration.seconds(2));
	    trans.setNode(train.getPic());
		trans.setByX(330);
	    
		if(train.getStation() == null) {
			trans.setByX(330);
		}
		else if(train.getPic().getX() == 1390 && train.getPic().getY() == 250) {
			train.getPic().setX(70);
			train.getPic().setY(545);
			trans.setByX(330);
		}
		
		else if(train.getPic().getX() == 1390 && train.getPic().getY() == 250) {
			train.setPic(null);
		}
		
		trans.setOnFinished(event -> {
//			if(train.getPic().getX() == 1060 && train.getPic().getY() == 250) {
//				train.getPic().setX(70);
//				train.getPic().setY(545);
//				train.getPic().setTranslateX(0);
//				train.getPic().setTranslateY(0);	
//				train.setIsAnimating(false);
//				LVFeed.getItems().add("Train " + train.getTrainNumber() + " Has Arrived in Station " + train.getStation().getStationNumber());
//			}
//			else {
				train.getPic().setX(train.getPic().getX() + train.getPic().getTranslateX());
				train.getPic().setY(train.getPic().getY() + train.getPic().getTranslateY());
				train.getPic().setTranslateX(0);
				train.getPic().setTranslateY(0);	
				train.setIsAnimating(false);
				System.out.println(train.getPic().getX());
				System.out.println("Train "+ train.getTrainNumber() + " Hass Arrived in Station " + train.getStation().getStationNumber());
				LVFeed.getItems().add("Train " + train.getTrainNumber() + " Has Arrived in Station " + train.getStation().getStationNumber());
//			}
		});
		trans.play();
	}
	
	public void animateStationToRail(Train train){
		//CHANGE ISANIMATING TRUE
				//CHANGE ISANIMATING FALSE AFTER
	}

	public void animateRailToStation(Train train){
		
		
		//CHANGE ISANIMATING TRUE
				//CHANGE ISANIMATING FALSE AFTER
	}
	
	public void animatePassengerDropOff(Passenger passenger) {
		TranslateTransition trans = new TranslateTransition();
		trans.setDuration(Duration.seconds(2));
		Random r = new Random();
		System.out.println("animatePassengerDropOff " + passenger.getStationEnd());
		if(passenger.getStationEnd() == 2) {
			passenger.getPic().setX(r.nextInt((540 - 390) + 1) + 390);
			passenger.getPic().setY(220);
//			try{Thread.sleep(1000);} catch(Exception e) {e.printStackTrace();}
			trans.setByY(-300);
		}
		else if(passenger.getStationEnd() == 3) {
			System.out.println("IM ON 3");
			passenger.getPic().setX(r.nextInt((870 - 720) + 1) + 720);
			passenger.getPic().setY(220);
//			try{Thread.sleep(1000);} catch(Exception e) {e.printStackTrace();}
			trans.setByY(-300);
		}
		else if(passenger.getStationEnd() == 4) {
			passenger.getPic().setX(r.nextInt((1200 - 1050) + 1) + 1050);
			passenger.getPic().setY(220);
//			try{Thread.sleep(1000);} catch(Exception e) {e.printStackTrace();}
			trans.setByY(-300);
		}
		else if(passenger.getStationEnd() == 5) {
			passenger.getPic().setX(r.nextInt((210 - 60) + 1) + 60);
			passenger.getPic().setY(515);		
//			try{Thread.sleep(1000);} catch(Exception e) {e.printStackTrace();}
			trans.setByY(-600);
		}
		else if(passenger.getStationEnd() == 6) {
			passenger.getPic().setX(r.nextInt((540 - 390) + 1) + 390);
			passenger.getPic().setY(515);
//			try{Thread.sleep(1000);} catch(Exception e) {e.printStackTrace();}
			trans.setByY(-600);
		}
		else if(passenger.getStationEnd() == 7) {
			passenger.getPic().setX(r.nextInt((870 - 720) + 1) + 720);
			passenger.getPic().setY(515);
//			try{Thread.sleep(1000);} catch(Exception e) {e.printStackTrace();}
			trans.setByY(-600);
		}
		else if(passenger.getStationEnd() == 8) {
			passenger.getPic().setX(r.nextInt((1200 - 1050) + 1) + 1050);
			passenger.getPic().setY(515);
//			try{Thread.sleep(1000);} catch(Exception e) {e.printStackTrace();}
			trans.setByY(-600);
		}
		
		
		passenger.getPic().setVisible(true);
		trans.play();
		trans.setOnFinished(event -> {
			passenger.getPic().setX(passenger.getPic().getX() + passenger.getPic().getTranslateX());
			passenger.getPic().setY(passenger.getPic().getY() + passenger.getPic().getTranslateY());
			passenger.getPic().setTranslateX(0);
			passenger.getPic().setTranslateY(0);
			passenger.setIsAnimating(false);
		});
		
//		Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                ancPane.getChildren().add(passenger.getPic());
//            }
//        });
	}

	
	////////////////////////////GETTERS AND SETTERS ///////////////////
	
	public ArrayList<Station> getStations() {
		return stations;
	}



	public void setStations(ArrayList<Station> stations) {
		this.stations = stations;
	}



	public ArrayList<Train> getTrains() {
		return trains;
	}



	public void setTrains(ArrayList<Train> trains) {
		this.trains = trains;
	}



	public Thread getT() {
		return t;
	}



	public void setT(Thread t) {
		this.t = t;
	}



	public String getThreadName() {
		return threadName;
	}



	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	
	public Boolean getIsThreadDone() {
		return isThreadDone;
	}

	public void setIsThreadDone(Boolean isThreadDone) {
		this.isThreadDone = isThreadDone;
	}

	public Station getS1() {
		return s1;
	}

	public void setS1(Station s1) {
		this.s1 = s1;
	}

	public Station getS2() {
		return s2;
	}

	public void setS2(Station s2) {
		this.s2 = s2;
	}

	public Station getS3() {
		return s3;
	}

	public void setS3(Station s3) {
		this.s3 = s3;
	}

	public Station getS4() {
		return s4;
	}

	public void setS4(Station s4) {
		this.s4 = s4;
	}

	public Station getS5() {
		return s5;
	}

	public void setS5(Station s5) {
		this.s5 = s5;
	}

	public Station getS6() {
		return s6;
	}

	public void setS6(Station s6) {
		this.s6 = s6;
	}

	public Station getS7() {
		return s7;
	}

	public void setS7(Station s7) {
		this.s7 = s7;
	}

	public Station getS8() {
		return s8;
	}

	public void setS8(Station s8) {
		this.s8 = s8;
	}

	public ListView<String> getLVFeed() {
		return LVFeed;
	}
	
	
}
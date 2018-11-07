package models;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

import controller.TrainSimulator;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

public class Train implements Runnable{
	
	int originalSeatCount;
	int seatCount;
	int trainNumber;
	Station station = null;
	RailBetween rail = null;
	Thread t;
	String threadName;
	//isDone loading passengers?
	Boolean isDoneLoading = false;
	//isThreadDone?
	Boolean isThreadDone = false;
	Semaphore sem;
	ArrayList<Passenger> passengers = new ArrayList<Passenger>();
	Boolean isAnimating = false;
	ImageView pic;
	TrainSimulator mainSimulator;
	Boolean isDoneUnloading = false;
	private static int trainId;
	
	public Train(int seatCount, Semaphore sem) {
		this.seatCount = seatCount;
		originalSeatCount = seatCount;
		this.trainNumber = trainId++;
		threadName = "Train ".concat(Integer.toString(trainNumber));
		this.sem = sem;
		pic = null;
		
	}
	
	
	////////////////////////////THREAD FUNCTION///////////////////
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		synchronized(this) {
			try {	
				
				while(!isThreadDone) {
					//get lock
					sem.acquire();
					//System.out.println("LOCKED: " + threadName);
					//check if train has station
					if(station != null) {
						
						//remove passengers that have arrived at the station
//							for(Passenger p : passengers) {
//								p.notified(station);
//								if(p.getIsArrived())
//									passengers.remove(p);
//							}
						
//							for(int i =0; i<passengers.size(); i++) {
//								passengers.get(i).notified(station);
//								if(passengers.get(i).getIsArrived())
//									passengers.remove(i);
//							}
						
						//check if train can load passengers
						if(!isDoneLoading) {
							if(seatCount == 0 || station.getPassengerCount() == 0) {
								isDoneLoading = true;
							}
						}
						

						
						////////////////////////NEWLY ADDED CODES///////////////////////////////
						
//							isDoneUnloading = true; //??????????
//							for(Passenger p: passengers) {
//								if(p.getIsArrived()) {
//									isDoneUnloading = false;
//								}
//							}
						
						//if(isDoneUnloading) {								
						for(int i =0; i<passengers.size(); i++) {
							//passengers.get(i).notified(station);
							if(passengers.get(i).getIsArrived()) {
								//seatCount++;
								//passengers.get(i).notified(station);
								passengers.remove(i);
							}
						}
						//}
						
						
						
						////////////////////////NEWLY ADDED CODES///////////////////////////////
						
						
					}
					//exit protocol
					//System.out.println("UNLOCKED: " + threadName);
					sem.release();
					
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				sem.release();
			} finally{
				//unlock
				sem.release();
				System.out.println("Done Thread " + threadName);
			}
		}
	}
	
	public void start () {
	      System.out.println("Deployed " + threadName);
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	}
	
	////////////////////////////METHODS///////////////////////////
	
	public void freeSeat() {
		seatCount++;
		System.out.println("nagadd na akooooooooo");
	}
	
	public void occupySeat(Passenger p) {
		seatCount--;
		passengers.add(p);
	}
	
	public void notifyPassengers() {
		// change the station they are currently in
		for(Passenger p : passengers) {
			p.notified(station);
		}
	}
	
	public void animateStationToStation(int currentStation, int nextStation){
		//CHANGE ISANIMATING TRUE
		//CHANGE ISANIMATING FALSE AFTER
//		this.setIsAnimating(true);
//		this.mainSimulator.animateStationToStation(currentStation, nextStation, this);
		
	}
	
	public void animateStationToRail(int currentStation, int nextStation){
		//CHANGE ISANIMATING TRUE
				//CHANGE ISANIMATING FALSE AFTER
	}

	public void animateRailToStation(int currentStation, int nextStation){
		
		
		//CHANGE ISANIMATING TRUE
				//CHANGE ISANIMATING FALSE AFTER
	}
	

	////////////////////////////GETTERS AND SETTERS ///////////////////

	public int getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(int trainNumber) {
		this.trainNumber = trainNumber;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
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


	public Boolean getIsDoneLoading() {
		return isDoneLoading;
	}


	public void setIsDoneLoading(Boolean isDoneLoading) {
		this.isDoneLoading = isDoneLoading;
	}

	public Boolean getIsThreadDone() {
		return isThreadDone;
	}


	public void setIsThreadDone(Boolean isThreadDone) {
		this.isThreadDone = isThreadDone;
	}


	public RailBetween getRail() {
		return rail;
	}


	public void setRail(RailBetween rail) {
		this.rail = rail;
	}

	public Boolean getIsAnimating() {
		return isAnimating;
	}


	public void setIsAnimating(Boolean isAnimating) {
		this.isAnimating = isAnimating;
	}


	public int getOriginalSeatCount() {
		return originalSeatCount;
	}


	public void setOriginalSeatCount(int originalSeatCount) {
		this.originalSeatCount = originalSeatCount;
	}


	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}


	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
	}

	public ImageView getPic() {
		return pic;
	}

	public void setPic(ImageView pic) {
		this.pic = pic;
	}


	public TrainSimulator getMainSimulator() {
		return mainSimulator;
	}


	public void setMainSimulator(TrainSimulator mainSimulator) {
		this.mainSimulator = mainSimulator;
	}


	public Boolean getIsDoneUnloading() {
		return isDoneUnloading;
	}


	public void setIsDoneUnloading(Boolean isDoneUnloading) {
		this.isDoneUnloading = isDoneUnloading;
	}
}

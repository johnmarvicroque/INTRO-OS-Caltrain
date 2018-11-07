package models;

import java.util.concurrent.locks.Lock;

import controller.TrainSimulator;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class Passenger implements Runnable{
	
	int stationStart;
	int stationEnd;
	Station station;
	Train train;
	String threadName;
	Thread t;
	Boolean isThreadDone = false;
	Boolean isInTrain = false;
	Boolean isArrived = false;
	Boolean isDoneAddingPassenger = false;
	Boolean isAnimating = false;
	Lock lock;
	ImageView pic;
	TrainSimulator mainSimulator;
	private static int passengerID = 0;
	int id = 0;
	public Passenger(Station station, int sStart, int sEnd, Lock lock) {
		stationStart = sStart;
		stationEnd = sEnd;
		this.station = station;
		this.id = passengerID++;
		threadName = "Passenger ".concat(Integer.toString(id));
		this.lock = lock;
		pic = null;
		station.addPassenger();
	}

	////////////////////////////THREAD FUNCTION///////////////////

	@Override
	public void run() {
		System.out.println("THREAD PASSENGER");
		// TODO Auto-generated method stub
		synchronized(this) {
			try {	
				while(!isDoneAddingPassenger) {
					if(lock.tryLock()) {
						
						// TODO passenger will go to station////////////////////////////////
						isDoneAddingPassenger = true;
						//exit protocol
						lock.unlock();
					}
				}
					
				while(!isThreadDone) {
					if(lock.tryLock()) {
						//System.out.println("LOCKED: " + threadName);
						/*if the Passenger is not loaded in the train yet*/
						if(!isInTrain) {
							//if there is train in the station
							if(station.getTrain() != null) {
								//if the train still accepts passenger
								if(!station.getTrain().getIsDoneLoading()) {
									//if there is still seat left
									if(station.getTrain().getSeatCount() > 0) {
										// TODO passenger will get in the train/////// change isAnimating = false/////////////////////////
										if(!station.getTrain().getIsAnimating()) {
											// TODO passenger will get in the train/////// change isAnimating = false/////////////////////////
											Thread.sleep(2000);
											this.getPic().setVisible(false);
											station.getTrain().occupySeat(this);
											System.out.println(threadName + "SEATED, SEATS:" + station.getTrain().getSeatCount());
											station.removePassenger();
											isInTrain = true;
											Platform.runLater(new Runnable() {
												@Override
												public void run() {
													Platform.runLater(new Runnable() {
														@Override
														public void run() {
															mainSimulator.getLVFeed().getItems().add(threadName + " Has Boarded Train");
															mainSimulator.updateLabels();
														}
													});
												}
											});
										}
	
									}
								}
								
							}
						}
						
						
						
						if(isArrived) {
							Thread.sleep(2000);
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									mainSimulator.getLVFeed().getItems().add(threadName + " Got Off Station " + stationEnd);
								}
							});
							//station.getTrain().freeSeat();
							isThreadDone = true;
							
						}

						
						//exit protocol
						//System.out.println("UNLOCKED: " + threadName);
						lock.unlock();
					}
				}
				
				System.out.println("wawwaw");
				
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				lock.unlock();
			} finally{
				//unlock

				System.out.println("DONE THREAD " + threadName);
				
			}
		}
	}
	
	public void start () {
	      System.out.println(threadName + " Arrived Station" + station.getStationNumber());
	      this.mainSimulator.getLVFeed().getItems().add(threadName + " Arrived Station" + station.getStationNumber());
	      if (t == null) {
	         t = new Thread (this, threadName);
	         System.out.println("THREADNAME DITO DITO " + threadName);
	         t.start ();
	      }
	}
	
	
	////////////// MIGHT WANT TO RELOCATE METHOD
	public void notified(Station s) {
		if(s.getStationNumber() == stationEnd) {
			s.getTrain().freeSeat();
			isArrived = true;
			
		}
	}

	////////////////////////////GETTERS AND SETTERS ///////////////////
	
	public int getStationStart() {
		return stationStart;
	}

	public void setStationStart(int stationStart) {
		this.stationStart = stationStart;
	}

	public int getStationEnd() {
		return stationEnd;
	}

	public void setStationEnd(int stationEnd) {
		this.stationEnd = stationEnd;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public Thread getT() {
		return t;
	}

	public void setT(Thread t) {
		this.t = t;
	}

	public Boolean getIsThreadDone() {
		return isThreadDone;
	}

	public void setIsThreadDone(Boolean isThreadDone) {
		this.isThreadDone = isThreadDone;
	}

	public Boolean getIsInTrain() {
		return isInTrain;
	}

	public void setIsInTrain(Boolean isInTrain) {
		this.isInTrain = isInTrain;
	}

	public Boolean getIsArrived() {
		return isArrived;
	}

	public void setIsArrived(Boolean isArrived) {
		this.isArrived = isArrived;
	}

	public Lock getLock() {
		return lock;
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public Boolean getIsDoneAddingPassenger() {
		return isDoneAddingPassenger;
	}

	public void setIsDoneAddingPassenger(Boolean isDoneAddingPassenger) {
		this.isDoneAddingPassenger = isDoneAddingPassenger;
	}

	public Boolean getIsAnimating() {
		return isAnimating;
	}

	public void setIsAnimating(Boolean isAnimating) {
		this.isAnimating = isAnimating;
	}

	public ImageView getPic() {
		return pic;
	}

	public void setPic(ImageView pic) {
		this.pic = pic;
	}

	public void setMainSimulator(TrainSimulator mainSimulator) {
		this.mainSimulator = mainSimulator;
	}

	public TrainSimulator getMainSimulator() {
		return mainSimulator;
	}
	
}

package models;



public class Station {
	
	int stationNumber;
	int passengerCount = 0;
	Train train;
	
	public Station(int sNumber) {	
		stationNumber = sNumber;	
	}
	
	////////////////////////////GETTERS AND SETTERS ///////////////////

	public int getStationNumber() {
		return stationNumber;
	}



	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}



	public int getPassengerCount() {
		return passengerCount;
	}



	public void setPassengerCount(int passengerCount) {
		this.passengerCount = passengerCount;
	}



	public Train getTrain() {
		return train;
	}



	public void setTrain(Train train) {
		this.train = train;
	}

	
	////////////////////////////METHODS///////////////////


	public void addPassenger() {
		
		passengerCount++;
	}
	
	public void removePassenger() {
		passengerCount--;
	}
	
	public Boolean isThereTrain() {
		if(train == null)
			return false;
		else
			return true;
	}

}

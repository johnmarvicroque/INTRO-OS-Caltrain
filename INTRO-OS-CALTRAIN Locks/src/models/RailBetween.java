package models;


public class RailBetween {
	
	int railNumber;
	Train train;
	
	public RailBetween(int rNumber) {	
		railNumber = rNumber;	
	}
	
	public boolean isThereTrain() {
		
		if(train == null)
			return false;
		else
			return true;
	}

	public int getRailNumber() {
		return railNumber;
	}

	public void setRailNumber(int railNumber) {
		this.railNumber = railNumber;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

}

package main.com;


public class Ride {

	private int RideID;
	private int ThrillLevel;
	private int HeightRequirement;    //inches
	private int Rating;
    private int Capacity;
    private int RideTime;
    private double AvgWaitTime;
    private String RideName;
	private String RideType;
	
	public Ride(int RideID, int ThrillLevel, int HeightRequirement, int Rating, int Capacity, int RideTime, double AvgWaitTime, String RideName, String RideType) {
		super();
		this.RideID = RideID;
		this.ThrillLevel = ThrillLevel;
		this.HeightRequirement = HeightRequirement;
		this.Rating = Rating;
		this.Capacity = Capacity;
		this.RideTime = RideTime;
		this.AvgWaitTime = AvgWaitTime;
		this.RideName = RideName;
        this.RideType = RideType;
	}

	public int getRideID() {
		return RideID;
	}

	public void getRideID(int RideID) {
		this.RideID = RideID;
	}

	public int getThrillLevel() {
		return ThrillLevel;
	}

	public void getThrillLevel(int ThrillLevel) {
		this.ThrillLevel = ThrillLevel;
	}

	public int getHeightRequirement() {
		return HeightRequirement;
	}

	public void setHeightRequirement(int HeightRequirement) {
		this.HeightRequirement = HeightRequirement;
	}
	public int getRating() {
		return Rating;
	}

	public void setRating(int Rating) {
		this.Rating = Rating;
	}

	public int getCapacity() {
		return Capacity;
	}

	public void setCapacity(int Capacity) {
		this.Capacity = Capacity;
	}

	public int getRideTime() {
		return RideTime;
	}

	public void setRideTime(int RideTime) {
		this.RideTime = RideTime;
	}

    public double getAvgWaitTime() {
		return AvgWaitTime;
	}

	public void setAvgWaitTime(double AvgWaitTime) {
		this.AvgWaitTime = AvgWaitTime;
	}

	public String getRideName() {
		return RideName;
	}

	public void setRideName(String RideName) {
		this.RideName = RideName;
	}

	public String getRideType() {
		return RideType;
	}

	public void setRideType(String RideType) {
		this.RideType = RideType;
	}

	@Override
	public String toString() {
		return "Ride [RideID=" + RideID + ", ThrillLevel=" + ThrillLevel + ", HeightRequirement=" + HeightRequirement + ", Rating=" + Rating
				+ ", Capacity=" + Capacity + ", RideTime=" + RideTime + ", AvgWaitTime=" + AvgWaitTime + ", RideName=" + RideName +", RideType=" + RideType +"]";
	}
	
	
}
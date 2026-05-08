package main.com;

public class RIDE_ON {
    private int RideOnID;
    private int CustomerID;
    private int RideID;
    private String RideDate;

    public RIDE_ON(int RideOnID, int CustomerID, int RideID, String RideDate){
        this.RideOnID = RideOnID;
        this.CustomerID = CustomerID;
        this.RideID = RideID;
        this.RideDate = RideDate;
    }

    public int getRideOnID() {
		return RideOnID;
	}

	public void setRideOnID(int RideOnID) {
		this.RideOnID = RideOnID;
	}

    public int getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(int CustomerID) {
		this.CustomerID = CustomerID;
	}

    public int getRideID() {
		return RideID;
	}

	public void setRideID(int RideID) {
		this.RideID = RideID;
	}

    public String getRideDate() {
		return RideDate;
	}

	public void set(String RideDate) {
		this.RideDate = RideDate;
	}
}

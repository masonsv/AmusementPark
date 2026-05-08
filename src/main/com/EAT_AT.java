package main.com;

public class EAT_AT {
    private int EatID;
    private int CustomerID;
    private int StallID;
    private String EatDate;

    public EAT_AT(int EatID, int CustomerID, int StallID, String EatDate){
        this.EatID = EatID;
        this.CustomerID = CustomerID;
        this.StallID = StallID;
        this.EatDate = EatDate;
    }

    public int getEatID() {
		return EatID;
	}

	public void setEatID(int EatID) {
		this.EatID = EatID;
	}

    public int getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(int CustomerID) {
		this.CustomerID = CustomerID;
	}

    public int getStallID() {
		return StallID;
	}

	public void setStallID(int StallID) {
		this.StallID = StallID;
	}

    public String getEatDate() {
		return EatDate;
	}

	public void set(String EatDate) {
		this.EatDate = EatDate;
	}
}

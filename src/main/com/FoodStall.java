package main.com;

public class FoodStall {

	private int StallID;
	private int AmountSold;
    private double Price;
    private String Name;
	private String FoodType;
	
	public FoodStall(int StallID, int AmountSold, double Price, String Name, String FoodType) {
		super();
		this.StallID = StallID;
		this.AmountSold = AmountSold;
		this.Price = Price;
		this.Name = Name;
		this.FoodType = FoodType;
	}

	public int getStallID() {
		return StallID;
	}

	public void getStallID(int StallID) {
		this.StallID = StallID;
	}

	public int getAmountSold() {
		return AmountSold;
	}

	public void getAmountSold(int AmountSold) {
		this.AmountSold = AmountSold;
	}

    public double getPrice() {
		return Price;
	}

	public void setPrice(double Price) {
		this.Price = Price;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	public String getFoodType() {
		return FoodType;
	}

	public void setFoodType(String FoodType) {
		this.FoodType = FoodType;
	}

	@Override
	public String toString() {
		return "FoodStall [StallID=" + StallID + ", AmountSold=" + AmountSold + ", Price=" + Price + ", StallName=" + Name
				+ ", FoodType=" + FoodType + "]";
	}
	
	
}
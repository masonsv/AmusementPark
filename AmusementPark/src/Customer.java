
public class Customer {

	private int CustomerID;
	private int ThrillLevel;
	private int Height;    //inches
	private int Age;
	private double Budget; //$xxx.xx
	private String TicketType;
	private String FirstName;
	private String LastName;
	
	public Customer(int CustomerID, int ThrillLevel, int Height, int Age, double Budget, String TicketType, String FirstName, String LastName) {
		super();
		this.CustomerID = CustomerID;
		this.ThrillLevel = ThrillLevel;
		this.Height = Height;
		this.Age = Age;
		this.Budget = Budget;
		this.TicketType = TicketType;
		this.FirstName = FirstName;
		this.LastName = LastName;
	}

	public int getCustomerID() {
		return CustomerID;
	}

	public void getCustomerID(int CustomerID) {
		this.CustomerID = CustomerID;
	}

	public int getThrillLevel() {
		return ThrillLevel;
	}

	public void getThrillLevel(int ThrillLevel) {
		this.ThrillLevel = ThrillLevel;
	}

	public int getHeight() {
		return Height;
	}

	public void getHeight(int Height) {
		this.Height = Height;
	}
	public int getAge() {
		return Age;
	}

	public void getAge(int Age) {
		this.Age = Age;
	}
	public double getBudget() {
		return Budget;
	}

	public void getBudget(double Budget) {
		this.Budget = Budget;
	}

	public String getTicketType() {
		return TicketType;
	}

	public void setTicketType(String TicketType) {
		this.TicketType = TicketType;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String FirstName) {
		this.FirstName = FirstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String LastName) {
		this.LastName = LastName;
	}

	@Override
	public String toString() {
		return "Customer [CustomerID=" + CustomerID + ", ThrillLevel=" + ThrillLevel + ", Height=" + Height + ", Age=" + Age
				+ ", Budget=" + Budget + ", TicketType=" + TicketType + ", FirstName=" + FirstName + ", LastName=" + LastName +"]";
	}
	
	
}

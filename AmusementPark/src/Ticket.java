

public class Ticket {

    private double Cost;
	private String TicketType;
	
	public Ticket(double Cost, String TicketType) {
		super();
		this.Cost = Cost;
		this.TicketType = TicketType;
	}

    public double getCost() {
		return Cost;
	}

	public void setCost(double Cost) {
		this.Cost = Cost;
	}

	public String getTicketType() {
		return TicketType;
	}

	public void TicketType(String TicketType) {
		this.TicketType = TicketType;
	}


	@Override
	public String toString() {
		return "Ticket [Cost=" + Cost + ", TicketType=" + TicketType +"]";
	}
	
	
}
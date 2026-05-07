
public class BUY_TICKET {
    private int SaleID;
    private int CustomerID;
    private String TicketType;
    private String PurchaseDate;

    public BUY_TICKET(int SaleID, int CustomerID, String TicketType, String PurchaseDate){
        this.SaleID = SaleID;
        this.CustomerID = CustomerID;
        this.TicketType = TicketType;
        this.PurchaseDate = PurchaseDate;
    }

    public int getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(int CustomerID) {
		this.CustomerID = CustomerID;
	}

    public int getSaleID() {
		return SaleID;
	}

	public void setSaleID(int SaleID) {
		this.SaleID = SaleID;
	}

    public String getTicketType() {
		return TicketType;
	}

	public void setTicketType(String TicketType) {
		this.TicketType = TicketType;
	}

    public String getPurchaseDate() {
		return PurchaseDate;
	}

	public void setPurchaseDate(String PurchaseDate) {
		this.PurchaseDate = PurchaseDate;
	}
}

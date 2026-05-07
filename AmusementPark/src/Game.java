
public class Game {

	private int GameID;
    private double Price;
    private double WinOdds;
    private String Name;
	
	public Game(int GameID, double Price, double WinOdds, String Name) {
		super();
		this.GameID = GameID;
		this.Price = Price;
		this.WinOdds = WinOdds;
		this.Name = Name;
	}

	public int getGameID() {
		return GameID;
	}

	public void getGameID(int GameID) {
		this.GameID = GameID;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double Price) {
		this.Price = Price;
	}

    public double getWinOdds() {
		return WinOdds;
	}

	public void setWinOdds(double WinOdds) {
		this.WinOdds = WinOdds;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}

	@Override
	public String toString() {
		return "Game [GameID=" + GameID + ", Price=" + Price + ", WinOdds=" + WinOdds
				+ ", Name=" + Name + "]";
	}
	
	
}
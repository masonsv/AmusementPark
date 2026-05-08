package main.com;

public class PLAY {
    private int PlayID;
    private int CustomerID;
    private int GameID;
    private String PlayDate;

    public PLAY(int PlayID, int CustomerID, int GameID, String PlayDate){
        this.PlayID = PlayID;
        this.CustomerID = CustomerID;
        this.GameID = GameID;
        this.PlayDate = PlayDate;
    }

    public int getPlayID() {
		return PlayID;
	}

	public void setPlayID(int PlayID) {
		this.PlayID = PlayID;
	}

    public int getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(int CustomerID) {
		this.CustomerID = CustomerID;
	}

    public int getGameID() {
		return GameID;
	}

	public void setGameID(int GameID) {
		this.GameID = GameID;
	}

    public String getPlayDate() {
		return PlayDate;
	}

	public void setPlayDate(String PlayDate) {
		this.PlayDate = PlayDate;
	}
}

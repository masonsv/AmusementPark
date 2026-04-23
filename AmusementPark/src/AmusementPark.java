import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AmusementPark {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Database db = new Database();

		db.connect();
		System.out.println("Successfully connected!\n");

		System.out.println("\n\nAttempting to create a new employee:");
		Game new_game = create_carnival_game_database(db, 1, 1, 7.50, .3, "Ring Toss");

		
		db.disconnect();
		
	}

	/**
	 * Queries the database for the contents of the Ticket table. The tuples are translated into an array list of Ticket objects and then printed.
	 * @param db : database object to interact with
	 */
	public static void read_from_ticket_database(Database db){
		try{
			String query = "SELECT * FROM Ticket";
			ResultSet results = db.runQuery(query);
			 
			ArrayList<Ticket> lst = new ArrayList<>();
			
			while(results.next()) {
				String ticketType = results.getString("TicketType");
				double price = results.getDouble("Price");
					
				Ticket t = new Ticket(price, ticketType);
					
				lst.add(t);
			}
			
			for(Ticket t : lst) {
				System.out.println(t);
			}
		} catch(SQLException e) {
			System.out.println("Something went wrong when reading the contents of the Ticket table!");
			e.printStackTrace();
		}
		
	}

	/**
	 * Creates a new ticket object and adds a tuple to the ticket table.
	 * @param db : database object to interact with
	 * @param TicketType
	 * @param Price
	 * @return the ticket object with the parameters as specified
	 */
	public static Ticket create_ticket_database(Database db, String TicketType, double Price){
		Ticket t = new Ticket(Price, TicketType);
		try {
			db.insertTicket(t);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when inserting a new ticket");
			ex.printStackTrace();
		}
		return t;
	}

	/**
	 * Updates the price of the ticket tuple
	 * @param db : database object to interact with
	 * @param t : ticket object that represents the tuple to update
	 * @param price : new price
	 */
	public static void update_ticket_database(Database db, Ticket t, double price){
		try {
			db.updateTicketCost(t, price);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when updating a price");
			ex.printStackTrace();
		}
	}

	/**
	 * Deletes the tuple represented by the object e
	 * @param db : database object to interact with
	 * @param t : tikcet object that represents the tuple to delete
	 */
	public static void delete_ticket_database(Database db, Ticket t){
		try {
			db.deleteTicket(t);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when deleting a ticket");
			ex.printStackTrace();
		}
	}

	/**
	 * Queries the database for the contents of the Ticket table. The tuples are translated into an array list of Ticket objects and then printed.
	 * @param db : database object to interact with
	 */
	public static void read_from_foodstall_database(Database db){
		try{
			String query = "SELECT * FROM FoodStall";
			ResultSet results = db.runQuery(query);
			 
			ArrayList<FoodStall> lst = new ArrayList<>();
			
			while(results.next()) {
				int StallID = results.getInt("StallID");
				double price = results.getDouble("Price");
				int AmountSold = results.getInt("AmountSold");
				String Name = results.getString("Name");
				String FoodType = results.getString("FoodType");
					
				FoodStall f = new FoodStall(StallID, AmountSold, price, Name, FoodType);
					
				lst.add(f);
			}
			
			for(FoodStall f : lst) {
				System.out.println(f);
			}
		} catch(SQLException e) {
			System.out.println("Something went wrong when reading the contents of the FoodStall table!");
			e.printStackTrace();
		}
		
	}

	/**
	 * Look up an Food stall using a Stlalid.
	 * @param db : database object to interact with
	 */
	public static FoodStall read_from_foodstall_database(Database db, int stallID){
		
		try {
			
			FoodStall f = db.lookupFoodStall(stallID);
			
		 	System.out.println(f.toString());
			return f;
		 		 
		} catch(SQLException e) {
			System.out.println("Something went wrong when looking up an carnival game by gameID!");
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * Creates a new Food sTall object and adds a tuple to the FoodStall table.
	 * @return the food Stall object with the parameters as specified
	 */
	public static FoodStall create_foodstall_database(Database db, int StallID, int amtSold, double price, String name, String FoodType){
		FoodStall f = new FoodStall(StallID, amtSold, price, name, FoodType);
		try {
			db.insertFoodStall(f);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when inserting a new Food Stall");
			ex.printStackTrace();
		}
		return f;
	}

	/**
	 * Queries the database for the contents of the CarnivalGame table. The tuples are translated into an array list of Game objects and then printed.
	 * @param db : database object to interact with
	 */
	public static void read_from_carnival_game_database(Database db){
		try{
			String query = "SELECT * FROM CarnivalGame";
			ResultSet results = db.runQuery(query);
			 
			ArrayList<Game> lst = new ArrayList<>();
			
			while(results.next()) {

				int GameID = results.getInt("GameID");	
				int PrizeID = results.getInt("PrizeID");
    			double Price = results.getDouble("Price");
    			double WinOdds = results.getDouble("WinOdds");
    			String Name = results.getString("GameName");
					
				Game g = new Game(GameID, PrizeID, Price, WinOdds, Name);
					
				lst.add(g);
			}
			
			for(Game g : lst) {
				System.out.println(g);
			}
		} catch(SQLException e) {
			System.out.println("Something went wrong when reading the contents of the Carnival Game table!");
			e.printStackTrace();
		}
		
	}

	/**
	 * Look up an Carnival Game using a GameID.
	 * @param db : database object to interact with
	 */
	public static Game read_from_carival_game_database(Database db, int gameID){
		
		try {
			
			Game g = db.lookupCarnivalGame(gameID);
			
		 	System.out.println(g.toString());
			return g;
		 		 
		} catch(SQLException e) {
			System.out.println("Something went wrong when looking up an carnival game by gameID!");
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * Creates a new Carnival Game object and adds a tuple to the Carnival Game table.
	 * @param db : database object to interact with
	 * @param GameID
	 * @param PrizeID
	 * @param Price
	 * @param WinOdds
	 * @param GameName
	 * @return the Carnival Game object with the parameters as specified
	 */
	public static Game create_carnival_game_database(Database db, int GameID, int PrizeID, double Price, double WinOdds, String GameName){
		Game g = new Game(GameID, PrizeID, Price, WinOdds, GameName);
		try {
			db.insertCarnivalGame(g);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when inserting a new carnival game");
			ex.printStackTrace();
		}
		return g;
	}

	/**
	 * Updates the price of the carnival game tuple
	 * @param db : database object to interact with
	 * @param g : Carnival Game object that represents the tuple to update
	 * @param price : new price
	 */
	public static void update_carnival_game_database(Database db, Game g, double price){
		try {
			db.updateGamePrice(g, price);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when updating a price");
			ex.printStackTrace();
		}
	}

	/**
	 * Deletes the tuple represented by the object e
	 * @param db : database object to interact with
	 * @param g : Carnival Game object that represents the tuple to delete
	 */
	public static void delete_carnival_game_database(Database db, Game g){
		try {
			db.deleteCarnivalGame(g);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when deleting an carnival game");
			ex.printStackTrace();
		}
	}

}

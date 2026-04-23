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
		//Game new_game = create_carnival_game_database(db, 1, 1, 7.50, .3, "Ring Toss");
		Customer cust = create_customer_database(db, 1, 10, 60, 72, 
												 600.00, "Senior", "Bob", "Kirk");
		
		db.disconnect();
		
	}
	/* 
	private int CustomerID;
	private int ThrillLevel;
	private int Height;    //inches
	private int Age;
	private double Budget; //$xxx.xx
	private String TicketType;
	private String FirstName;
	private String LastName;
	*/
	

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
			System.out.println("Something went wrong when reading the contents of the Ticket table!");
			e.printStackTrace();
		}
		
	}

	/**
	 * Look up an Carnival Game using a GameID.
	 * @param db : database object to interact with
	 */
	public static Game read_from_carival_game_database(Database db, int gameID){
		/* STEP FOUR: Run a select and get the results */
		try {
			// call the database using helper function
			Game g = db.lookupCarnivalGame(gameID);
			// print
		 	System.out.println(g.toString());
			return g;
		 		 
		} catch(SQLException e) {
			System.out.println("Something went wrong when looking up an employee by ssn!");
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
	 * @param e : Employee object that represents the tuple to delete
	 */
	public static void delete_carnival_game_database(Database db, Game g){
		try {
			db.deleteCarnivalGame(g);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when deleting an employee");
			ex.printStackTrace();
		}
	}

	/**
	 * Queries the database for the contents of the Customer table. The tuples are translated into an array list 
	 * of Customer objects and then printed.
	 * @param db : database object to interact with
	 */
	public static void read_from_customer_database(Database db){
		try{
			String query = "SELECT * FROM Customer";
			ResultSet results = db.runQuery(query);
			
			ArrayList<Customer> lst = new ArrayList<>();
			
			while(results.next()) {

				int CustomerID = results.getInt("CustomerID");	
				int ThrillLevel = results.getInt("ThrillLevel");
				int Height = results.getInt("Height");	
				int Age = results.getInt("Age");
				double Budget = results.getDouble("Budget");
				String TicketType = results.getString("TicketType");
				String FirstName = results.getString("FirstName");
				String LastName = results.getString("LastName");

				Customer c = new Customer(CustomerID, ThrillLevel, Height, Age, Budget, TicketType, FirstName, LastName);
					
				lst.add(c);
			}
			
			for(Customer c : lst) {
				System.out.println(c);
			}
		} catch(SQLException e) {
			System.out.println("Something went wrong when reading the contents of the Customer table!");
			e.printStackTrace();
		}
		
	}
		/**
	 * Look up an Customer using a CustomerID.
	 * @param db : database object to interact with
	 */
		public static Customer read_from_customer_database(Database db, int CustomerID){
			/* STEP FOUR: Run a select and get the results */
			try {
				// call the database using helper function
				Customer c = db.lookupCustomer(CustomerID);
				// print
				 System.out.println(c.toString());
				return c;
					  
			} catch(SQLException e) {
				System.out.println("Something went wrong when looking up an employee by ssn!");
				e.printStackTrace();
				return null;
			}
			
		}
	
		/**
		 * Creates a new Customer object and adds a tuple to the Customer table.
		 * @param db : database object to interact with
		 * @param CustomerID
		 * @param ThrillLevel
		 * @param Height
		 * @param Age
		 * @param Budget
		 * @param TicketType
		 * @param FirstName
		 * @param LastName
		 * @return the Customer object with the parameters as specified
		 */
		public static Customer create_customer_database(Database db, int CustomerID, int ThrillLevel, int Height, 
													int Age, double Budget, String TicketType, String FirstName, String LastName){
			Customer c = new Customer(CustomerID, ThrillLevel, Height, Age, Budget, TicketType, FirstName, LastName);
			try {
				db.insertCustomer(c);
			} catch(SQLException ex) {
				System.out.println("Something went wrong when inserting a new carnival game");
				ex.printStackTrace();
			}
			return c;
		}
	
		/**
		 * Updates the budget of a Customer tuple
		 * @param db : database object to interact with
		 * @param c : Customer object that represents the tuple to update
		 * @param budget : new budget
		 */
		public static void update_customer_budget_database(Database db, Customer c, double budget){
			try {
				db.updateCustomerBudget(c, budget);
			} catch(SQLException ex) {
				System.out.println("Something went wrong when updating a budget");
				ex.printStackTrace();
			}
		}
		/**
		 * Updates the age of a Customer tuple
		 * @param db : database object to interact with
		 * @param c : Customer object that represents the tuple to update
		 * @param age : new age
		 */
		public static void update_customer_age_database(Database db, Customer c, int age){
			try {
				db.updateCustomerAge(c, age);
			} catch(SQLException ex) {
				System.out.println("Something went wrong when updating a age");
				ex.printStackTrace();
			}
		}
		/**
		 * Updates the height of a Customer tuple
		 * @param db : database object to interact with
		 * @param c : Customer object that represents the tuple to update
		 * @param height : new height
		 */
		public static void update_customer_height_database(Database db, Customer c, int height){
			try {
				db.updateCustomerHeight(c, height);
			} catch(SQLException ex) {
				System.out.println("Something went wrong when updating a height");
				ex.printStackTrace();
			}
		}
	
		/**
		 * Deletes the tuple represented by the object c
		 * @param db : database object to interact with
		 * @param c : Customer object that represents the tuple to delete
		 */
		public static void delete_customer_database(Database db, Customer c){
			try {
				db.deleteCustomer(c);
			} catch(SQLException ex) {
				System.out.println("Something went wrong when deleting a customer");
				ex.printStackTrace();
			}
		}

}





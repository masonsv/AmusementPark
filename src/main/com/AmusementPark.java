package main.com;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.text.DecimalFormat;

public class AmusementPark {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Database db = new Database();

		db.connect();
		System.out.println("Successfully connected!\n");

		System.out.println("Finding average ride rating");
		averageRideRating(db);

		System.out.println("Finding best rated ride types");
		bestRatedTypeOfRide(db);

		System.out.println("Grabbing customer activity report");
		customerActivityReport(db);

		System.out.println("Finding 5 lowest rated rides");
		lowestRatedRides(db);

		System.out.println("Finding above average wait times");
		aboveAvgWaitTimes(db);

		System.out.println("Finding customers who did everything");
		customersWhoDidEverything(db);

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
	 * Deletes the tuple represented by the object t
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
			System.out.println("Something went wrong when looking up an food stal by StallID!");
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
	 * Updates the price of the food stall tuple
	 * @param db : database object to interact with
	 * @param f : food stall object that represents the tuple to update
	 * @param price : new price
	 */
	public static void update_foodstall_database(Database db, FoodStall f, double price){
		try {
			db.updateFoodPrice(f, price);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when updating a price");
			ex.printStackTrace();
		}
	}

	/**
	 * Updates the amoutn sold of the food stall tuple
	 * @param db : database object to interact with
	 * @param f : food stall object that represents the tuple to update
	 * @param amtSold : new anount
	 */
	public static void update_foodstall_database(Database db, FoodStall f, int amtSold){
		try {
			db.updateAmountSold(f, amtSold);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when updating a amount sold");
			ex.printStackTrace();
		}
	}

	/**
	 * Deletes the tuple represented by the object f
	 * @param db : database object to interact with
	 * @param f : foodstall object that represents the tuple to delete
	 */
	public static void delete_foodstall_database(Database db, FoodStall f){
		try {
			db.deleteFoodStall(f);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when deleting a food stall");
			ex.printStackTrace();
		}
	}

	/**
	 * Queries the database for the contents of the Ride table. The tuples are translated into an array list of Ride objects and then printed.
	 * @param db : database object to interact with
	 */
	public static void read_from_ride_database(Database db){
		try{
			String query = "SELECT * FROM Ride";
			ResultSet results = db.runQuery(query);
			 
			ArrayList<Ride> lst = new ArrayList<>();
			
			while(results.next()) {

				int RideID = results.getInt("RideID");
				int ThrillLevel = results.getInt("ThrillLevel");
				int HeightRequirement = results.getInt("HeightRequirement");   
				int Rating = results.getInt("Rating");
    			int Capacity = results.getInt("Capacity");
    			int RideTime = results.getInt("RideTime");
    			double AvgWaitTime = results.getDouble("AvgWaitTime");
    			String RideName = results.getString("RideName");
				String RideType = results.getString("RideType");
					
				Ride r = new Ride(RideID, ThrillLevel, HeightRequirement, Rating, Capacity, RideTime, AvgWaitTime, RideName, RideType);
					
				lst.add(r);
			}
			
			for(Ride r : lst) {
				System.out.println(r);
			}
		} catch(SQLException e) {
			System.out.println("Something went wrong when reading the contents of the Ride table!");
			e.printStackTrace();
		}
		
	}

	/**
	 * Look up an Carnival Game using a GameID.
	 * @param db : database object to interact with
	 */
	public static Ride read_from_ride_database(Database db, int RideID){
		
		try {
			
			Ride r = db.lookupRide(RideID);
			
		 	System.out.println(r.toString());
			return r;
		 		 
		} catch(SQLException e) {
			System.out.println("Something went wrong when looking up an ride by rideID!");
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * Creates a new Ride object and adds a tuple to the Ride table.
	 * @param db : database object to interact with
	 * @return the ride object with the parameters as specified
	 */
	public static Ride create_ride_database(Database db, int RideID, int ThrillLevel, int HeightRequirement, int Rating, int Capacity, int RideTime, double AvgWaitTime, String RideName, String RideType){
		Ride r = new Ride(RideID, ThrillLevel, HeightRequirement, Rating, Capacity, RideTime, AvgWaitTime, RideName, RideType);
		try {
			db.insertRide(r);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when inserting a new ride");
			ex.printStackTrace();
		}
		return r;
	}

	/**
	 * Updates the height req of the ride tuple
	 * @param db : database object to interact with
	 * @param r : Ride Game object that represents the tuple to update
	 * @param height : new hieght req
	 */
	public static void update_ride_height_database(Database db, Ride r, int height){
		try {
			db.updateRideHeightRequirement(r, height);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when updating a height requirement");
			ex.printStackTrace();
		}
	}

	/**
	 * Updates the height req of the ride tuple
	 * @param db : database object to interact with
	 * @param r : Ride Game object that represents the tuple to update
	 * @param capacity : new capacity
	 */
	public static void update_ride_capacity_database(Database db, Ride r, int capacity){
		try {
			db.updateRideCapacity(r, capacity);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when updating a capacity");
			ex.printStackTrace();
		}
	}

	/**
	 * Updates the rating of the ride tuple
	 * @param db : database object to interact with
	 * @param r : Ride Game object that represents the tuple to update
	 * @param rating : new rating
	 */
	public static void update_ride_rating_database(Database db, Ride r, int rating){
		try {
			db.updateRideRating(r, rating);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when updating a rating");
			ex.printStackTrace();
		}
	}

	/**
	 * Deletes the tuple represented by the object e
	 * @param db : database object to interact with
	 * @param r : Carnival Game object that represents the tuple to delete
	 */
	public static void delete_ride_database(Database db, Ride r){
		try {
			db.deleteRide(r);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when deleting a ride");
			ex.printStackTrace();
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
    			double Price = results.getDouble("Price");
    			double WinOdds = results.getDouble("WinOdds");
    			String Name = results.getString("GameName");
					
				Game g = new Game(GameID, Price, WinOdds, Name);
					
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
	public static Game read_from_carnival_game_database(Database db, int gameID){
		
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
	 * @param Price
	 * @param WinOdds
	 * @param GameName
	 * @return the Carnival Game object with the parameters as specified
	 */
	public static Game create_carnival_game_database(Database db, int GameID, double Price, double WinOdds, String GameName){
		Game g = new Game(GameID, Price, WinOdds, GameName);
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
		public static Customer create_customer_database(Database db, int CustomerID, int ThrillLevel, int Height, int Age, double Budget, String TicketType, String FirstName, String LastName){
			Customer c = new Customer(CustomerID, ThrillLevel, Height, Age, Budget, TicketType, FirstName, LastName);
			try {
				db.insertCustomer(c);
			} catch(SQLException ex) {
				System.out.println("Something went wrong when inserting a new customer");
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
		public static void update_customer_spent_database(Database db, Customer c, double spent){
			try {
				db.updateCustomerSpending(c, spent);
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

		/* Advanced Queries */
		public static void averageRideRating(Database db){
			try{
				DecimalFormat df = new DecimalFormat("0.00");
				ResultSet results = db.averageRideRating();
				
				while(results.next()) {
					double AvgRating = results.getDouble("AvgRating");

					System.out.println("Average Ride Rating: [" + df.format(AvgRating) + "]");
				}
			} catch(SQLException e) {
				System.out.println("Something went wrong when executing average ride rating query!");
				e.printStackTrace();
			}
		}

		public static void bestRatedTypeOfRide(Database db){
			try{
				DecimalFormat df = new DecimalFormat("0.00");
				ResultSet results = db.bestRatedTypeOfRide();
				
				while(results.next()) {

					String RideType = results.getString("RideType");
					double AvgRatng = results.getDouble("AvgRating");

					if (RideType.equals("Roller Coaster")){
						System.out.println("RideType: [" + RideType + "]\tAverage Rating: [" + df.format(AvgRatng) + "]");
					}else{
						System.out.println("RideType: [" + RideType + "]\t\tAverage Rating: [" + df.format(AvgRatng) + "]");
					}
				}
			} catch(SQLException e) {
				System.out.println("Something went wrong when executing best rated type of ride query!");
				e.printStackTrace();
			}
		}

		public static void customerActivityReport(Database db){
			try{
				DecimalFormat df = new DecimalFormat("0.00");
				ResultSet results = db.customerActivityReport();
				
				while(results.next()) {
					int CustomerID = results.getInt("CustomerID");
					String FirstName = results.getString("FirstName");
					String LastName = results.getString("LastName");
					String TicketType = results.getString("TicketType");
					int RidesRidden = results.getInt("RidesRidden");
					int GamesPlayed = results.getInt("GamesPlayed");
					int FoodEaten = results.getInt("FoodEaten");

				}
			} catch(SQLException e) {
				System.out.println("Something went wrong when executing customber actiity report query!");
				e.printStackTrace();
			}
		}

		public static void lowestRatedRides(Database db){
			try{
				DecimalFormat df = new DecimalFormat("0.00");
				ResultSet results = db.customerActivityReport();
				
				while(results.next()) {
					int RideID = results.getInt("RideID");   
					int Rating = results.getInt("Rating");
					String RideName = results.getString("RideName");
					String RideType = results.getString("RideType");
				}
			} catch(SQLException e) {
				System.out.println("Something went wrong when executing lowest rated rides query!");
				e.printStackTrace();
			}
		}

		public static void aboveAvgWaitTimes(Database db){
			try{
				DecimalFormat df = new DecimalFormat("0.00");
				ResultSet results = db.customerActivityReport();
				
				while(results.next()) {
					int RideID = results.getInt("RideID");
					int Capacity = results.getInt("Capacity");
					int RideTime = results.getInt("RideTime");; 
					double AvgWaitTime = results.getDouble("AvgWaitTime");;
					String RideName = results.getString("RideName");;
					String RideType = results.getString("RideType");
				}
			} catch(SQLException e) {
				System.out.println("Something went wrong when executing above average wait times query!");
				e.printStackTrace();
			}
		}

		public static void customersWhoDidEverything(Database db){
			try{
				DecimalFormat df = new DecimalFormat("0.00");
				ResultSet results = db.customerActivityReport();
				
				while(results.next()) {
					int CustomerID = results.getInt("CustomerID");
					String FirstName = results.getString("FirstName");
					String LastName = results.getString("LastName");
				}
			} catch(SQLException e) {
				System.out.println("Something went wrong when executing customers who did everything query!");
				e.printStackTrace();
			}
		}

}
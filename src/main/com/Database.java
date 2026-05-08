<<<<<<< HEAD:src/main/com/Database.java
package main.com;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Component;

@Component
public class Database {

	/*
	 * load SQL driver (JDBC: Java Database Connector/ODBC)
	 * - add to build path
	 * 
	 * set up our database (script)
	 * 
	 * connect to the database
	 * 
	 * insert/modify/delete data (Java)
	 * 
	 * query data (Java)
	 * 
	 * disconnect from the database
	 * 
	 */

	 /* SQLite connection to a local database */
//	private String url = "jdbc:sqlite:/Users/asauppe/Documents/teaching/cs364/Company.db";

	/* MySQL connection to a local database */
//	private String url = "jdbc:mysql://localhost/dbName?user=vagle0060&password=6p-uz5sTF7ErGchZW";

	/* MySQL connection to a remote database */
//	private String url = "jdbc:mysql://ipAddress:socket/dbName?user=vagle0060&password=6p-uz5sTF7ErGchZW";

	private String url = "jdbc:mysql://138.49.184.123:3306/";	// URL for server
	private String dbName = "vagle0060_AmusementPark";			// TODO: set to your dbName on the server
	private String username = "vagle0060"; 						// TODO: your username on the server
	private String password; 									// your password on the server, set in constructor for privacy
	private Connection connection;								// connection object for running queries
	
	/**
	 * Constructor for the Database class.
	 * Set the password for the user in this function.
	 */
	public Database() {
		password = "6p-uz5sTF7ErGchZW"; //TODO: set this to your password
	}
	

	public Connection getConnection() {
    	return connection;
	}

	/**
	 * Construct the url to connect to the database, and establish a connection.
	 */
	public void connect() {
		try {
			url = url + dbName + "?";
			String connectionUrl = "jdbc:mysql://138.49.184.123:3306/vagle0060_AmusementPark";
			connection = DriverManager.getConnection(connectionUrl, username, password);

		} catch (SQLException e) {
			System.out.println("Cannot connect!");
			System.out.println(e);
		}
	}
		
	/**
	 * Closes the connection with the database.
	 */
	public void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Cannot disconnect!");
		}
	}
	
	/**
	 * Runs a query with no parameters using prepared statements.
	 * @param query : the query to run
	 * @return the results set from the database
	 * @throws SQLException
	 */
	public ResultSet runQuery(String query) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		return results;
	}

	/**
	 * Insert Carnival Game 
	 * @param g : Game object to add to the database
	 * @throws SQLException
	 */
	public void insertCarnivalGame(Game g) throws SQLException {
		String sql = "INSERT INTO CarnivalGame (GameID, PrizeID, Price, GameName, WinOdds) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, g.getGameID());
		stmt.setDouble(2, g.getPrizeID());
		stmt.setDouble(3, g.getPrice());
		stmt.setString(4, g.getName());
		stmt.setDouble(5, g.getWinOdds());
		int numRowsAffected = stmt.executeUpdate();
		System.out.println("Number of rows affected: " + numRowsAffected);
	}

	/**
	 *  Carnival Game lookup
	 *  @param gameID : gameID for the carnvial game
	 * 	@return a game object
	 * 	@throws SQLException
	 */
	public Game lookupCarnivalGame(int gameID) throws SQLException{
		String query = "SELECT * FROM CarnivalGame WHERE gameID = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, gameID);
		ResultSet results = stmt.executeQuery();
		Game g = null;
		
		if(results.next()) {
			
			int GameID = results.getInt("GameID");	
			int PrizeID = results.getInt("PrizeID");
    		double Price = results.getDouble("Price");
    		double WinOdds = results.getDouble("WinOdds");
    		String Name = results.getString("GameName");
			
			g = new Game(GameID, PrizeID, Price, WinOdds, Name);
		}
		return g;
	}

	public void updateGamePrice(Game g, double price) throws SQLException {
		String sql = "UPDATE CarnivalGame SET Price = ? WHERE GameID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, price);
		stmt.setInt(2, g.getGameID());
		stmt.executeUpdate();
		g.setPrice(price);
	}

	public void updateWinOdds(Game g, double winOdds) throws SQLException {
		String sql = "UPDATE CarnivalGame SET WinOdds = ? WHERE GameID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, winOdds);
		stmt.setInt(2, g.getGameID());
		stmt.executeUpdate();
		g.setPrice(winOdds);
	}

	/**
	 * Delete a carnival game from the CarnivalGame table.
	 * @param g : Game object representing the carnival game to delete
	 * @return how many rows were deleted from the table.
	 * @throws SQLException
	 */
	public boolean deleteCarnivalGame(Game g) throws SQLException {
		String sql = "DELETE FROM CarnivalGame WHERE GameID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, g.getGameID());
		int numRowsAffected = stmt.executeUpdate();
		return numRowsAffected > 0;
	}

	/**
	 * Insert Food Stall 
	 * @param f : Food stall object to add to the database
	 * @throws SQLException
	 */
	public void insertFoodStall(FoodStall f) throws SQLException {
		String sql = "INSERT INTO FoodStall (StallID, Price, StallName, FoodType, AmounttSold) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, f.getStallID());
		stmt.setDouble(2, f.getPrice());
		stmt.setString(3, f.getName());
		stmt.setString(4, f.getFoodType());
		stmt.setInt(5, f.getAmountSold());
		int numRowsAffected = stmt.executeUpdate();
		System.out.println("Number of rows affected: " + numRowsAffected);
	}

	public FoodStall lookupFoodStall(int StallID) throws SQLException{
		String query = "SELECT * FROM FoodStall WHERE StallID = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, StallID);
		ResultSet results = stmt.executeQuery();
		FoodStall f = null;
		
		if(results.next()) {
			
			int AmountSold = results.getInt("AmountSold");
    		double Price = results.getDouble("Price");
    		String Name = results.getString("StallName");
			String FoodType = results.getString("FoodType");

			f = new FoodStall(StallID, AmountSold, Price, Name, FoodType);
		}
		return f;
	}

	public void updateFoodPrice(FoodStall f, double price) throws SQLException {
		String sql = "UPDATE FoodStall SET Price = ? WHERE StallID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, price);
		stmt.setInt(2, f.getStallID());
		stmt.executeUpdate();
		f.setPrice(price);
	}

	public void updateAmountSold(FoodStall f, int amountSold) throws SQLException {
		String sql = "UPDATE FoodStall SET AmountSold = ? WHERE StallID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, amountSold);
		stmt.setInt(2, f.getStallID());
		stmt.executeUpdate();
		f.setPrice(amountSold);
	}

	/**
	 * Delete a food stall from the FoodStall table.
	 * @param f : FoodStall object representing the foodstall to delete
	 * @return how many rows were deleted from the table.
	 * @throws SQLException
	 */
	public boolean deleteFoodStall(FoodStall f) throws SQLException {
		String sql = "DELETE FROM FoodStall WHERE StallID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, f.getStallID());
		int numRowsAffected = stmt.executeUpdate();
		return numRowsAffected > 0;
	}

	public void insertTicket(Ticket t) throws SQLException{
			String sql = "INSERT INTO Ticket (Cost, TicketType) VALUES (?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDouble(1, t.getCost());
			stmt.setString(2, t.getTicketType());
			int numRowsAffected = stmt.executeUpdate();
			System.out.println("Number of rows affected: " + numRowsAffected);
	}

	public void updateTicketCost(Ticket t, double Cost) throws SQLException {
			String sql = "UPDATE Ticket SET Cost = ? WHERE TicketType = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDouble(1, Cost);
			stmt.setString(2, t.getTicketType());
			stmt.executeUpdate();
			t.setCost(Cost);
	}

	public boolean deleteTicket(Ticket t) throws SQLException {
		String sql = "DELETE FROM Ticket WHERE TicketType = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, t.getTicketType());
		int numRowsAffected = stmt.executeUpdate();
		return numRowsAffected > 0;
	}

	public void insertRide(Ride r) throws SQLException {
		String sql = "INSERT INTO Ride (RideID, ThrillLevel, HeightRequirement, Rating, Capacity, RideTime,"
					+ " AvgWaitTime, RideName, RideType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, r.getRideID());
		stmt.setInt(2, r.getThrillLevel());
		stmt.setInt(3, r.getHeightRequirement());
		stmt.setInt(4, r.getRating());
		stmt.setInt(5, r.getCapacity());
		stmt.setInt(6, r.getRideTime());
		stmt.setDouble(7, r.getAvgWaitTime());
		stmt.setString(8, r.getRideName());
		stmt.setString(9, r.getRideType());
		int numRowsAffected = stmt.executeUpdate();
		System.out.println("Number of rows affected: " + numRowsAffected);
	}	

	public Ride lookupRide(int RideID) throws SQLException{
		String query = "SELECT * FROM Ride WHERE RideID = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		Ride r = null;
		
		if(results.next()) {
			int ThrillLevel = results.getInt("ThrillLevel");
			int HeightRequirement = results.getInt("HeightRequirement");
			int Rating = results.getInt("Rating");
			int Capacity = results.getInt("Capacity");
			int RideTime = results.getInt("RideTime");
    		double AvgWaitTime = results.getDouble("AvgWaitTime");
    		String RideName = results.getString("RideName");
			String RideType = results.getString("RideType");

			r = new Ride(RideID, ThrillLevel, HeightRequirement, Rating, Capacity, 
				         RideTime, AvgWaitTime, RideName, RideType);
		}
		return r;
	}

	/**
	 * Changes the height requirement for the ride
	 * @param r : the ride object that represents the tuple in the database
	 * @param heightReq : the new height requirement
	 * @throws SQLException
	 */
	public void updateRideHeightRequirement(Ride r, int heightReq) throws SQLException {
		String sql = "UPDATE Ride SET HeightRequirement = ? WHERE RideID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, heightReq);
		stmt.setInt(2, r.getRideID());
		stmt.executeUpdate();
		r.setHeightRequirement(heightReq);
	}

	/**
	 * Changes the capacity for the ride
	 * @param r : the ride object that represents the tuple in the database
	 * @param capacity : the new capacity
	 * @throws SQLException
	 */
	public void updateRideCapacity(Ride r, int capacity) throws SQLException {
		String sql = "UPDATE Ride SET Capacity = ? WHERE RideID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, capacity);
		stmt.setInt(2, r.getRideID());
		stmt.executeUpdate();
		r.setCapacity(capacity);
	}

	/**
	 * Changes the rating for the ride
	 * @param r : the ride object that represents the tuple in the database
	 * @param rating : the new rating
	 * @throws SQLException
	 */
	public void updateRideRating(Ride r, int rating) throws SQLException {
		String sql = "UPDATE Ride SET Rating = ? WHERE RideID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, rating);
		stmt.setInt(2, r.getRideID());
		stmt.executeUpdate();
		r.setRating(rating);
	}

	public boolean deleteRide(Ride r) throws SQLException {
		String sql = "DELETE FROM Ride WHERE RideID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, r.getRideID());
		int numRowsAffected = stmt.executeUpdate();
		return numRowsAffected > 0;
	}
	
	public void insertCustomer(Customer c) throws SQLException{
		String sql = "INSERT INTO Customer (CustomerID, ThrillLevel, Height, Age, Spent, TicketType,"
					+ " FirstName, LastName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, c.getCustomerID());
		stmt.setInt(2, c.getThrillLevel());
		stmt.setInt(3, c.getHeight());
		stmt.setInt(4, c.getAge());
		stmt.setDouble(5, c.getSpent());
		stmt.setString(6, c.getTicketType());
		stmt.setString(7, c.getFirstName());
		stmt.setString(8, c.getLastName());
		int numRowsAffected = stmt.executeUpdate();
		System.out.println("Number of rows affected: " + numRowsAffected);
	}

	public Customer lookupCustomer(int CustomerID) throws SQLException{
		String query = "SELECT * FROM Customer WHERE CustomerID = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, CustomerID);
		ResultSet results = stmt.executeQuery();
		Customer c = null;
		
		if(results.next()) {
			
			int ThrillLevel = results.getInt("ThrillLevel");
			int Height = results.getInt("Height"); 
			int Age = results.getInt("Age");
			double Spent = results.getDouble("Spent"); 
			String TicketType = results.getString("TicketType");
			String FirstName = results.getString("FirstName");
			String LastName = results.getString("LastName");

			c = new Customer(CustomerID, ThrillLevel, Height, Age, Spent, TicketType, FirstName, LastName);
		}
		return c;
	}

		/**
	 * Changes the amount spent of a customer
	 * @param c : the customer object that represents the tuple in the database
	 * @param spent : the new spent
	 * @throws SQLException
	 */
	public void updateCustomerSpending(Customer c, double spent) throws SQLException {
		String sql = "UPDATE Customer SET Spent = ? WHERE CustomerID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, spent);
		stmt.setInt(2, c.getCustomerID());
		stmt.executeUpdate();
		c.setSpent(spent);
	}

	/**
	 * Changes the height of a customer
	 * @param c : the customer object that represents the tuple in the database
	 * @param height : the new height
	 * @throws SQLException
	 */
	public void updateCustomerHeight(Customer c, int height) throws SQLException {
		String sql = "UPDATE Customer SET Height = ? WHERE CustomerID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, height);
		stmt.setInt(2, c.getCustomerID());
		stmt.executeUpdate();
		c.setHeight(height);
	}

	/**
	 * Changes the height of a customer
	 * @param c : the customer object that represents the tuple in the database
	 * @param height : the new height
	 * @throws SQLException
	 */
	public void updateCustomerAge(Customer c, int age) throws SQLException {
		String sql = "UPDATE Customer SET Age = ? WHERE CustomerID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, age);
		stmt.setInt(2, c.getCustomerID());
		stmt.executeUpdate();
		c.setAge(age);
	}

	public boolean deleteCustomer(Customer c) throws SQLException {
		String sql = "DELETE FROM Customer WHERE CustomerID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, c.getCustomerID());
		int numRowsAffected = stmt.executeUpdate();
		return numRowsAffected > 0;
	}

	public void insertBUY_TICKET(Customer c) throws SQLException{
		String sql = "INSERT INTO BUY_TICKET (CustomerID, ThrillLevel, Height, Age, Spent, TicketType,"
					+ " FirstName, LastName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, c.getCustomerID());
		stmt.setInt(2, c.getThrillLevel());
		stmt.setInt(3, c.getHeight());
		stmt.setInt(4, c.getAge());
		stmt.setDouble(5, c.getSpent());
		stmt.setString(6, c.getTicketType());
		stmt.setString(7, c.getFirstName());
		stmt.setString(8, c.getLastName());
		int numRowsAffected = stmt.executeUpdate();
		System.out.println("Number of rows affected: " + numRowsAffected);
	}

	/* 			Advanced Queries 		  */
	
	/* Average Ride Rating */
	public ResultSet averageRideRating() throws SQLException {
		String sql = "SELECT avg(Rating) as AvgRating FROM Ride";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet averageRideRating = stmt.executeQuery();
		return averageRideRating;
	}

	/* What Ride Classification has the Best Rating */
	public ResultSet bestRatedTypeOfRide() throws SQLException {
		String sql = "SELECT RideType, avg(Rating) as AvgRating " +
					 "FROM Ride " +
					 "GROUP BY RideType " +
					 "ORDER BY avg(Rating) DESC";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet bestRideByClassification = stmt.executeQuery();
		return bestRideByClassification;
	}
	
	/* Customer Activity Report */
	public ResultSet customerActivityReport() throws SQLException {
		String sql = "SELECT c.CustomerID, c.FirstName, c.LastName, t.TicketType, " + 
					 "count(DISTINCT r.RideID) as RidesRidden, " + 
					 "count(DISTINCT p.GameID) as GamesPlayed, " + 
					 "count(DISTINCT e.StallID) as FoodEaten " + 
					 "FROM Customer c JOIN Ticket t ON c.TicketType = t.TicketType " + 
					 "JOIN RIDE_ON r ON c.CustomerID = r.CustomerID " + 
					 "JOIN PLAY p ON c.CustomerID = p.CustomerID " + 
					 "JOIN EAT_AT e ON c.CustomerID = e.CustomerID " + 
					 "GROUP BY c.CustomerID, c.FirstName, c.LastName, t.TicketType";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet customerActivityReport = stmt.executeQuery();
		return customerActivityReport;
	}

	/* Find the 5 Lowest Rated Rides */
	public ResultSet lowestRatedRides() throws SQLException {
		String sql = "SELECT RideName, Rating" +
					 "FROM Ride " +
					 "GROUP BY RideName, Rating " +
					 "HAVING avg(Rating) <= (SELECT Rating FROM Ride ORDER BY Rating ASC LIMIT 4, 1)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet lowestRatedRides = stmt.executeQuery();
		return lowestRatedRides;
	}

	/* Rides With Above Average Wait Times */
	public ResultSet aboveAvgWaitTimes() throws SQLException {
		String sql = "SELECT * " + 
					 "FROM Ride " +
					 "WHERE AvgWaitTime > (SELECT avg(AvgWaitTime) FROM Ride)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet aboveAvgWaitTimes = stmt.executeQuery();
		return aboveAvgWaitTimes;
	}


	/* Find Customers Who Made The Most of The Park */
	public ResultSet customersWhoDidEverything() throws SQLException {
		String sql = "SELECT c.CustomerID, c.FirstName, c.LastName " +
					 "FROM Customer as c "+
					 "WHERE c.CustomerID IN (SELECT CustomerID FROM RIDE_ON) " +
					 "AND c.CustomerID IN (SELECT CustomerID FROM PLAY) " +
					 "AND c.CustomerID IN (SELECT CustomerID FROM EAT_AT)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet customersWhoDidEverything = stmt.executeQuery();
		return customersWhoDidEverything;
	}
}
=======
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class Database {

	/*
	 * load SQL driver (JDBC: Java Database Connector/ODBC)
	 * - add to build path
	 * 
	 * set up our database (script)
	 * 
	 * connect to the database
	 * 
	 * insert/modify/delete data (Java)
	 * 
	 * query data (Java)
	 * 
	 * disconnect from the database
	 * 
	 */

	 /* SQLite connection to a local database */
//	private String url = "jdbc:sqlite:/Users/asauppe/Documents/teaching/cs364/Company.db";

	/* MySQL connection to a local database */
//	private String url = "jdbc:mysql://localhost/dbName?user=vagle0060&password=6p-uz5sTF7ErGchZW";

	/* MySQL connection to a remote database */
//	private String url = "jdbc:mysql://ipAddress:socket/dbName?user=vagle0060&password=6p-uz5sTF7ErGchZW";

	private String url = "jdbc:mysql://138.49.184.123:3306/";	// URL for server
	private String dbName = "vagle0060_AmusementPark";			// TODO: set to your dbName on the server
	private String username = "vagle0060"; 						// TODO: your username on the server
	private String password; 									// your password on the server, set in constructor for privacy
	private Connection connection;								// connection object for running queries
	
	/**
	 * Constructor for the Database class.
	 * Set the password for the user in this function.
	 */
	public Database() {
		password = "6p-uz5sTF7ErGchZW"; //TODO: set this to your password
	}
	

	/**
	 * Construct the url to connect to the database, and establish a connection.
	 */
	public void connect() {
		try {
			url = url + dbName + "?";
			connection = DriverManager.getConnection(url, username, password);

		} catch (SQLException e) {
			System.out.println("Cannot connect!");
			System.out.println(e);
		}
	}
		
	/**
	 * Closes the connection with the database.
	 */
	public void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Cannot disconnect!");
		}
	}
	
	/**
	 * Runs a query with no parameters using prepared statements.
	 * @param query : the query to run
	 * @return the results set from the database
	 * @throws SQLException
	 */
	public ResultSet runQuery(String query) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		return results;
	}

	/**
	 * Insert Carnival Game 
	 * @param g : Game object to add to the database
	 * @throws SQLException
	 */
	public void insertCarnivalGame(Game g) throws SQLException {
		String sql = "INSERT INTO CarnivalGame (GameID, Price, GameName, WinOdds) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, g.getGameID());
		stmt.setDouble(2, g.getPrice());
		stmt.setString(3, g.getName());
		stmt.setDouble(4, g.getWinOdds());
		int numRowsAffected = stmt.executeUpdate();
		System.out.println("Number of rows affected: " + numRowsAffected);
	}

	/**
	 *  Carnival Game lookup
	 *  @param gameID : gameID for the carnvial game
	 * 	@return a game object
	 * 	@throws SQLException
	 */
	public Game lookupCarnivalGame(int gameID) throws SQLException{
		String query = "SELECT * FROM CarnivalGame WHERE gameID = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, gameID);
		ResultSet results = stmt.executeQuery();
		Game g = null;
		
		if(results.next()) {
			
			int GameID = results.getInt("GameID");	
    		double Price = results.getDouble("Price");
    		double WinOdds = results.getDouble("WinOdds");
    		String Name = results.getString("GameName");
			
			g = new Game(GameID, Price, WinOdds, Name);
		}
		return g;
	}

	public void updateGamePrice(Game g, double price) throws SQLException {
		String sql = "UPDATE CarnivalGame SET Price = ? WHERE GameID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, price);
		stmt.setInt(2, g.getGameID());
		stmt.executeUpdate();
		g.setPrice(price);
	}

	public void updateWinOdds(Game g, double winOdds) throws SQLException {
		String sql = "UPDATE CarnivalGame SET WinOdds = ? WHERE GameID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, winOdds);
		stmt.setInt(2, g.getGameID());
		stmt.executeUpdate();
		g.setPrice(winOdds);
	}

	/**
	 * Delete a carnival game from the CarnivalGame table.
	 * @param g : Game object representing the carnival game to delete
	 * @return how many rows were deleted from the table.
	 * @throws SQLException
	 */
	public boolean deleteCarnivalGame(Game g) throws SQLException {
		String sql = "DELETE FROM CarnivalGame WHERE GameID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, g.getGameID());
		int numRowsAffected = stmt.executeUpdate();
		return numRowsAffected > 0;
	}

	/**
	 * Insert Food Stall 
	 * @param f : Food stall object to add to the database
	 * @throws SQLException
	 */
	public void insertFoodStall(FoodStall f) throws SQLException {
		String sql = "INSERT INTO FoodStall (StallID, Price, StallName, FoodType, AmounttSold) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, f.getStallID());
		stmt.setDouble(2, f.getPrice());
		stmt.setString(3, f.getName());
		stmt.setString(4, f.getFoodType());
		stmt.setInt(5, f.getAmountSold());
		int numRowsAffected = stmt.executeUpdate();
		System.out.println("Number of rows affected: " + numRowsAffected);
	}

	public FoodStall lookupFoodStall(int StallID) throws SQLException{
		String query = "SELECT * FROM FoodStall WHERE StallID = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, StallID);
		ResultSet results = stmt.executeQuery();
		FoodStall f = null;
		
		if(results.next()) {
			
			int AmountSold = results.getInt("AmountSold");
    		double Price = results.getDouble("Price");
    		String Name = results.getString("StallName");
			String FoodType = results.getString("FoodType");

			f = new FoodStall(StallID, AmountSold, Price, Name, FoodType);
		}
		return f;
	}

	public void updateFoodPrice(FoodStall f, double price) throws SQLException {
		String sql = "UPDATE FoodStall SET Price = ? WHERE StallID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, price);
		stmt.setInt(2, f.getStallID());
		stmt.executeUpdate();
		f.setPrice(price);
	}

	public void updateAmountSold(FoodStall f, int amountSold) throws SQLException {
		String sql = "UPDATE FoodStall SET AmountSold = ? WHERE StallID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, amountSold);
		stmt.setInt(2, f.getStallID());
		stmt.executeUpdate();
		f.setPrice(amountSold);
	}

	/**
	 * Delete a food stall from the FoodStall table.
	 * @param f : FoodStall object representing the foodstall to delete
	 * @return how many rows were deleted from the table.
	 * @throws SQLException
	 */
	public boolean deleteFoodStall(FoodStall f) throws SQLException {
		String sql = "DELETE FROM FoodStall WHERE StallID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, f.getStallID());
		int numRowsAffected = stmt.executeUpdate();
		return numRowsAffected > 0;
	}

	public void insertTicket(Ticket t) throws SQLException{
			String sql = "INSERT INTO Ticket (Cost, TicketType) VALUES (?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDouble(1, t.getCost());
			stmt.setString(2, t.getTicketType());
			int numRowsAffected = stmt.executeUpdate();
			System.out.println("Number of rows affected: " + numRowsAffected);
	}

	public void updateTicketCost(Ticket t, double Cost) throws SQLException {
			String sql = "UPDATE Ticket SET Cost = ? WHERE TicketType = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDouble(1, Cost);
			stmt.setString(2, t.getTicketType());
			stmt.executeUpdate();
			t.setCost(Cost);
	}

	public boolean deleteTicket(Ticket t) throws SQLException {
		String sql = "DELETE FROM Ticket WHERE TicketType = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, t.getTicketType());
		int numRowsAffected = stmt.executeUpdate();
		return numRowsAffected > 0;
	}

	public void insertRide(Ride r) throws SQLException {
		String sql = "INSERT INTO Ride (RideID, ThrillLevel, HeightRequirement, Rating, Capacity, RideTime,"
					+ " AvgWaitTime, RideName, RideType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, r.getRideID());
		stmt.setInt(2, r.getThrillLevel());
		stmt.setInt(3, r.getHeightRequirement());
		stmt.setInt(4, r.getRating());
		stmt.setInt(5, r.getCapacity());
		stmt.setInt(6, r.getRideTime());
		stmt.setDouble(7, r.getAvgWaitTime());
		stmt.setString(8, r.getRideName());
		stmt.setString(9, r.getRideType());
		int numRowsAffected = stmt.executeUpdate();
		System.out.println("Number of rows affected: " + numRowsAffected);
	}	

	public Ride lookupRide(int RideID) throws SQLException{
		String query = "SELECT * FROM Ride WHERE RideID = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		Ride r = null;
		
		if(results.next()) {
			int ThrillLevel = results.getInt("ThrillLevel");
			int HeightRequirement = results.getInt("HeightRequirement");
			int Rating = results.getInt("Rating");
			int Capacity = results.getInt("Capacity");
			int RideTime = results.getInt("RideTime");
    		double AvgWaitTime = results.getDouble("AvgWaitTime");
    		String RideName = results.getString("RideName");
			String RideType = results.getString("RideType");

			r = new Ride(RideID, ThrillLevel, HeightRequirement, Rating, Capacity, 
				         RideTime, AvgWaitTime, RideName, RideType);
		}
		return r;
	}

	/**
	 * Changes the height requirement for the ride
	 * @param r : the ride object that represents the tuple in the database
	 * @param heightReq : the new height requirement
	 * @throws SQLException
	 */
	public void updateRideHeightRequirement(Ride r, int heightReq) throws SQLException {
		String sql = "UPDATE Ride SET HeightRequirement = ? WHERE RideID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, heightReq);
		stmt.setInt(2, r.getRideID());
		stmt.executeUpdate();
		r.setHeightRequirement(heightReq);
	}

	/**
	 * Changes the capacity for the ride
	 * @param r : the ride object that represents the tuple in the database
	 * @param capacity : the new capacity
	 * @throws SQLException
	 */
	public void updateRideCapacity(Ride r, int capacity) throws SQLException {
		String sql = "UPDATE Ride SET Capacity = ? WHERE RideID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, capacity);
		stmt.setInt(2, r.getRideID());
		stmt.executeUpdate();
		r.setCapacity(capacity);
	}

	/**
	 * Changes the rating for the ride
	 * @param r : the ride object that represents the tuple in the database
	 * @param rating : the new rating
	 * @throws SQLException
	 */
	public void updateRideRating(Ride r, int rating) throws SQLException {
		String sql = "UPDATE Ride SET Rating = ? WHERE RideID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, rating);
		stmt.setInt(2, r.getRideID());
		stmt.executeUpdate();
		r.setRating(rating);
	}

	public boolean deleteRide(Ride r) throws SQLException {
		String sql = "DELETE FROM Ride WHERE RideID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, r.getRideID());
		int numRowsAffected = stmt.executeUpdate();
		return numRowsAffected > 0;
	}
	
	public void insertCustomer(Customer c) throws SQLException{
		String sql = "INSERT INTO Customer (CustomerID, ThrillLevel, Height, Age, Budget, TicketType,"
					+ " FirstName, LastName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, c.getCustomerID());
		stmt.setInt(2, c.getThrillLevel());
		stmt.setInt(3, c.getHeight());
		stmt.setInt(4, c.getAge());
		stmt.setDouble(5, c.getBudget());
		stmt.setString(6, c.getTicketType());
		stmt.setString(7, c.getFirstName());
		stmt.setString(8, c.getLastName());
		int numRowsAffected = stmt.executeUpdate();
		System.out.println("Number of rows affected: " + numRowsAffected);
	}

	public Customer lookupCustomer(int CustomerID) throws SQLException{
		String query = "SELECT * FROM Customer WHERE CustomerID = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setInt(1, CustomerID);
		ResultSet results = stmt.executeQuery();
		Customer c = null;
		
		if(results.next()) {
			
			int ThrillLevel = results.getInt("ThrillLevel");
			int Height = results.getInt("Height"); 
			int Age = results.getInt("Age");
			double Budget = results.getDouble("Budget"); 
			String TicketType = results.getString("TicketType");
			String FirstName = results.getString("FirstName");
			String LastName = results.getString("LastName");

			c = new Customer(CustomerID, ThrillLevel, Height, Age, Budget, TicketType, FirstName, LastName);
		}
		return c;
	}

		/**
	 * Changes the budget of a customer
	 * @param c : the customer object that represents the tuple in the database
	 * @param budget : the new budget
	 * @throws SQLException
	 */
	public void updateCustomerBudget(Customer c, double budget) throws SQLException {
		String sql = "UPDATE Customer SET Budget = ? WHERE CustomerID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, budget);
		stmt.setInt(2, c.getCustomerID());
		stmt.executeUpdate();
		c.setBudget(budget);
	}

	/**
	 * Changes the height of a customer
	 * @param c : the customer object that represents the tuple in the database
	 * @param height : the new height
	 * @throws SQLException
	 */
	public void updateCustomerHeight(Customer c, int height) throws SQLException {
		String sql = "UPDATE Customer SET Height = ? WHERE CustomerID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, height);
		stmt.setInt(2, c.getCustomerID());
		stmt.executeUpdate();
		c.setHeight(height);
	}

	/**
	 * Changes the height of a customer
	 * @param c : the customer object that represents the tuple in the database
	 * @param height : the new height
	 * @throws SQLException
	 */
	public void updateCustomerAge(Customer c, int age) throws SQLException {
		String sql = "UPDATE Customer SET Age = ? WHERE CustomerID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setDouble(1, age);
		stmt.setInt(2, c.getCustomerID());
		stmt.executeUpdate();
		c.setAge(age);
	}

	public boolean deleteCustomer(Customer c) throws SQLException {
		String sql = "DELETE FROM Customer WHERE CustomerID = ?";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, c.getCustomerID());
		int numRowsAffected = stmt.executeUpdate();
		return numRowsAffected > 0;
	}

	public void insertBUY_TICKET(Customer c) throws SQLException{
		String sql = "INSERT INTO BUY_TICKET (CustomerID, ThrillLevel, Height, Age, Budget, TicketType,"
					+ " FirstName, LastName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setInt(1, c.getCustomerID());
		stmt.setInt(2, c.getThrillLevel());
		stmt.setInt(3, c.getHeight());
		stmt.setInt(4, c.getAge());
		stmt.setDouble(5, c.getBudget());
		stmt.setString(6, c.getTicketType());
		stmt.setString(7, c.getFirstName());
		stmt.setString(8, c.getLastName());
		int numRowsAffected = stmt.executeUpdate();
		System.out.println("Number of rows affected: " + numRowsAffected);
	}

	/* 			Advanced Queries 		  */
	
	/* Average Ride Rating */
	public ResultSet averageRideRating() throws SQLException {
		String sql = "SELECT avg(Rating) as AvgRating FROM Ride";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet averageRideRating = stmt.executeQuery();
		return averageRideRating;
	}

	/* What Ride Classification has the Best Rating */
	public ResultSet bestRatedTypeOfRide() throws SQLException {
		String sql = "SELECT RideType, avg(Rating) as AvgRating " +
					 "FROM Ride " +
					 "GROUP BY RideType " +
					 "ORDER BY avg(Rating) DESC";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet bestRideByClassification = stmt.executeQuery();
		return bestRideByClassification;
	}
	
	/* Customer Activity Report */
	public ResultSet customerActivityReport() throws SQLException {
		String sql = "SELECT c.CustomerID, c.FirstName, c.LastName, t.TicketType, " + 
					 "count(DISTINCT r.RideID) as RidesRidden, " + 
					 "count(DISTINCT p.GameID) as GamesPlayed, " + 
					 "count(DISTINCT e.StallID) as FoodEaten " + 
					 "FROM Customer c JOIN Ticket t ON c.TicketType = t.TicketType " + 
					 "JOIN RIDE_ON r ON c.CustomerID = r.CustomerID " + 
					 "JOIN PLAY p ON c.CustomerID = p.CustomerID " + 
					 "JOIN EAT_AT e ON c.CustomerID = e.CustomerID " + 
					 "GROUP BY c.CustomerID, c.FirstName, c.LastName, t.TicketType";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet customerActivityReport = stmt.executeQuery();
		return customerActivityReport;
	}

	/* Find the 5 Lowest Rated Rides */
	public ResultSet lowestRatedRides() throws SQLException {
		String sql = "SELECT r.RideID, r.Rating, r.RideName, r.RideType, " +
					 "FROM Ride as r " +
					 "ORDER BY Rating DESC " +
					 "OFFSET count(Ride) - 5";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet lowestRatedRides = stmt.executeQuery();
		return lowestRatedRides;
	}

	/* Rides With Above Average Wait Times */
	public ResultSet aboveAvgWaitTimes() throws SQLException {
		String sql = "SELECT  r.RideID, r.Capacity, r.RideTime, r.AvgWaitTime, r.RideName, r.RideType, " + 
					 "FROM Ride as r " +
					 "WHERE AvgWaitTime > (SELECT avg(AvgWaitTime) FROM Ride)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet aboveAvgWaitTimes = stmt.executeQuery();
		return aboveAvgWaitTimes;
	}


	/* Find Customers Who Made The Most of The Park */
	public ResultSet customersWhoDidEverything() throws SQLException {
		String sql = "SELECT c.CustomerID, c.FirstName, c.LastName " +
					 "FROM Customer as c "+
					 "WHERE c.CustomerID IN (SELECT CustomerID FROM RIDE_ON) " +
					 "AND c.CustomerID IN (SELECT CustomerID FROM PLAY) " +
					 "AND c.CustomerID IN (SELECT CustomerID FROM EAT_AT)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet customersWhoDidEverything = stmt.executeQuery();
		return customersWhoDidEverything;
	}
}
>>>>>>> 0b34d19fdd0ff6bdb275b92e71a80ad0f222b112:AmusementPark/src/Database.java

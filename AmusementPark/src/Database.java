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

	public static void main(String[] args) {
    	Database db = new Database();
    	db.connect();
    
    	// Test if connection works
    	try {
        	if (db.connection != null && !db.connection.isClosed()) {
            	System.out.println("✅ Database connected successfully!");
            	db.disconnect();
        	} else {
            	System.out.println("❌ Database connection failed!");
        	}
 		} catch (SQLException e) {
        	System.out.println("❌ Error checking connection!");
        	e.printStackTrace();
    }
	}
	
		
}

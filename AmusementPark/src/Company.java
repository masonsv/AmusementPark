import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Company {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Database db = new Database();

		db.connect();
		System.out.println("Successfully connected!\n");
		
		System.out.println(" (BEFORE) Attempting to read the contents of the Employee table:");
		read_from_database(db);

		
		// Add 3 new employees
//		Employee newHire1 = create_employee_database(db, "434-24-1234", 402000, "Jimmy", "John", "Jones");
//		Employee newHire2 = create_employee_database(db, "434-24-1235", 312, "Jake", "Lionhart", "Smith");
//		Employee newHire3 = create_employee_database(db, "434-24-1236", 84354.67, "Issac", "Loves", "Apples");
		
		// Change Salary
//		Employee Jake = read_from_database(db, "123-45-9876");
	//	update_database(db, Jake, 0.01);
		// Change Name
//		Employee Jasper = read_from_database(db, "123-45-6789");
//		update_database(db, Jasper, "Jasper", "NewMiddleName", "Fforde");
		
		// Fire Robin
		Employee Robin = read_from_database(db, "123-45-6799");
		delete_database(db, Robin);
		
		/* Read query Example */

		// System.out.println("\n\nAttempting to look up an employee using a SSN:");
		// // read in ssn from user??
		
		// System.out.print("Enter the SSN of the employee you want to view: ");
		// String ssnInput = in.nextLine();

		// Employee someone = read_from_database(db, ssnInput);

		/* Create query Example */

		// System.out.println("\n\nAttempting to create a new employee:");
		// Employee new_person = create_employee_database(db, "222-22-2222", 60000.00, "J.", "L.", "Collins");
		
		/* Update query Example */

		// System.out.println("\n\nAttempting to change someone's salary:");
		// update_database(db, someone, 78000.00);

		/* Delete query Example */

		// System.out.println("\n\nAttempting to delete someone:");
		// delete_database(db, someone);

		// System.out.println(" (AFTER) Attempting to read the contents of the Employee table:");
		// read_from_database(db);
		
		db.disconnect();
		
	}

	/**
	 * Queries the database for the contents of the Employee table. The tuples are translated into an array list of Employee objects and then printed.
	 * @param db : database object to interact with
	 */
	public static void read_from_database(Database db){
		try{
			String query = "SELECT * FROM Employee";
			ResultSet results = db.runQuery(query);
			 
			ArrayList<Employee> lst = new ArrayList<>();
			
			while(results.next()) {
				String ssn = results.getString("SSN");
				double salary = results.getDouble("Salary");
				String firstName = results.getString("FirstName");
				String middleName;
				try{
					middleName = results.getString("MiddleName");
				}
				catch(SQLException e){
					middleName = "";
				}
		 		
				String lastName = results.getString("LastName");
					
				Employee e = new Employee(ssn, salary, firstName, middleName, lastName);
					
				lst.add(e);
			}
			
			for(Employee e : lst) {
				System.out.println(e);
			}
		} catch(SQLException e) {
			System.out.println("Something went wrong when reading the contents of the Employee table!");
			e.printStackTrace();
		}
		
	}

	/**
	 * Look up an employee using a ssn. Demonstrates packaging and unpacking data on Java and SQL sides, as well as parameterized queries.
	 * @param db : database object to interact with
	 */
	public static Employee read_from_database(Database db, String ssn){
		/* STEP FOUR: Run a select and get the results */
		try {
			// call the database using helper function
			Employee e = db.employeeLookup(ssn);
			// print
		 	System.out.println(e.toString());
			return e;
		 		 
		} catch(SQLException e) {
			System.out.println("Something went wrong when looking up an employee by ssn!");
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * Creates a new Employee object and adds a tuple to the Employee table.
	 * @param db : database object to interact with
	 * @param ssn
	 * @param salary
	 * @param fname
	 * @param mname
	 * @param lname
	 * @return the Employee object with the parameters as specified
	 */
	public static Employee create_employee_database(Database db, String ssn, double salary, String fname, String mname, String lname){
		Employee e = new Employee(ssn, salary, fname, mname, lname);
		try {
			db.insertEmployee(e);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when inserting a new employee");
			ex.printStackTrace();
		}
		return e;
	}

	/**
	 * Updates the salary of the employee tuple
	 * @param db : database object to interact with
	 * @param e : Employee object that represents the tuple to update
	 * @param salary : new salary
	 */
	public static void update_database(Database db, Employee e, double salary){
		try {
			db.updateEmployeeSalary(e, salary);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when updating a salary");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Updates the salary of the employee tuple
	 * @param db : database object to interact with
	 * @param e : Employee object that represents the tuple to update
	 * @param firstName, middleName, lastName : new name
	 */
	public static void update_database(Database db, Employee e, String firstName, String middleName, String lastName){
		try {
			db.updateEmployeeName(e, firstName, middleName, lastName);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when updating a salary");
			ex.printStackTrace();
		}
	}

	/**
	 * Deletes the tuple represented by the object e
	 * @param db : database object to interact with
	 * @param e : Employee object that represents the tuple to delete
	 */
	public static void delete_database(Database db, Employee e){
		try {
			db.deleteEmployee(e);
		} catch(SQLException ex) {
			System.out.println("Something went wrong when deleting an employee");
			ex.printStackTrace();
		}
	}

}

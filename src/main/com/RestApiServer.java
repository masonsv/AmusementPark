package main.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.sql.*;
import java.util.*;

@SpringBootApplication
public class RestApiServer {
    
    public static void main(String[] args) {
        SpringApplication.run(RestApiServer.class, args);
        System.out.println("\n🎢 Amusement Park REST API Running!");
        System.out.println("📍 Server: http://localhost:8080");
        System.out.println("📍 Frontend: http://localhost:8080/frontend/index.html\n");
    }
}

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
class ApiController {
    
    @Autowired
    private Database db;
    
    // ==================== CUSTOMERS ====================
    
   @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomers() {
        List<Map<String, Object>> customers = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;
        
        try {
            // Create our OWN connection - don't use db instance
            String connectionUrl = "jdbc:mysql://138.49.184.123:3306/vagle0060_AmusementPark";
            conn = DriverManager.getConnection(connectionUrl, "vagle0060", "6p-uz5sTF7ErGchZW");
            
            stmt = conn.createStatement();
            results = stmt.executeQuery("SELECT * FROM Customer");
            
            while(results.next()) {
                Map<String, Object> customer = new HashMap<>();
                customer.put("customerID", results.getInt("CustomerID"));
                customer.put("firstName", results.getString("FirstName"));
                customer.put("lastName", results.getString("LastName"));
                customer.put("thrillLevel", results.getInt("ThrillLevel"));
                customer.put("height", results.getInt("Height"));
                customer.put("age", results.getInt("Age"));
                customer.put("spent", results.getDouble("Spent"));
                customer.put("ticketType", results.getString("TicketType"));
                customers.add(customer);
            }
            
            return ResponseEntity.ok(customers);
            
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        } finally {
            try {
                if (results != null) results.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    @PostMapping("/customers")
    public ResponseEntity<?> createCustomer(@RequestBody Map<String, Object> data) {
        db.connect();
        try {
            Customer c = new Customer(
                0,
                data.get("thrillLevel") != null ? ((Number)data.get("thrillLevel")).intValue() : 0,
                ((Number)data.get("height")).intValue(),
                data.get("age") != null ? ((Number)data.get("age")).intValue() : 0,
                data.get("spent") != null ? ((Number)data.get("spent")).doubleValue() : 0.0,
                (String)data.get("ticketType"),
                (String)data.get("firstName"),
                (String)data.get("lastName")
            );
            
            db.insertCustomer(c);
            db.disconnect();
            
            return ResponseEntity.status(201).body(Map.of("status", "success", "message", "Customer added"));
                
        } catch(SQLException e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/customers/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id) {
        db.connect();
        try {
            Customer c = db.lookupCustomer(id);
            if (c == null) {
                db.disconnect();
                return ResponseEntity.status(404).body(Map.of("status", "error", "message", "Not found"));
            }
            
            db.deleteCustomer(c);
            db.disconnect();
            
            return ResponseEntity.ok(Map.of("status", "success", "message", "Deleted"));
            
        } catch(SQLException e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
    
    // ==================== RIDES ====================
    
    @GetMapping("/rides")
    public ResponseEntity<?> getAllRides() {
        List<Map<String, Object>> rides = new ArrayList<>();
        db.connect();
        
        try {
            Statement stmt = db.getConnection().createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM Ride");
            
            while(results.next()) {
                Map<String, Object> ride = new HashMap<>();
                ride.put("rideID", results.getInt("RideID"));
                ride.put("rideName", results.getString("RideName"));
                ride.put("heightRequirement", results.getInt("HeightRequirement"));
                ride.put("rideType", results.getString("RideType"));
                ride.put("rideTime", results.getInt("RideTime"));
                ride.put("thrillLevel", results.getInt("ThrillLevel"));
                ride.put("capacity", results.getInt("Capacity"));
                ride.put("avgWaitTime", results.getDouble("AvgWaitTime"));
                ride.put("rating", results.getDouble("Rating"));
                rides.add(ride);
            }
            
            results.close();
            stmt.close();
            db.disconnect();
            return ResponseEntity.ok(rides);
            
        } catch(Exception e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/rides")
    public ResponseEntity<?> createRide(@RequestBody Map<String, Object> data) {
        db.connect();
        try {
            Ride r = new Ride(
                0,
                data.get("thrillLevel") != null ? ((Number)data.get("thrillLevel")).intValue() : 0,
                data.get("heightRequirement") != null ? ((Number)data.get("heightRequirement")).intValue() : 0,
                data.get("rating") != null ? ((Number)data.get("rating")).intValue() : 0,
                data.get("capacity") != null ? ((Number)data.get("capacity")).intValue() : 0,
                data.get("rideTime") != null ? ((Number)data.get("rideTime")).intValue() : 0,
                data.get("avgWaitTime") != null ? ((Number)data.get("avgWaitTime")).doubleValue() : 0.0,
                (String)data.get("rideName"),
                (String)data.get("rideType")
            );
            
            db.insertRide(r);
            db.disconnect();
            
            return ResponseEntity.status(201).body(Map.of("status", "success", "message", "Ride added"));
                
        } catch(SQLException e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/rides/{id}")
    public ResponseEntity<?> deleteRide(@PathVariable int id) {
        db.connect();
        try {
            Ride r = db.lookupRide(id);
            if (r == null) {
                db.disconnect();
                return ResponseEntity.status(404).body(Map.of("status", "error", "message", "Not found"));
            }
            
            db.deleteRide(r);
            db.disconnect();
            
            return ResponseEntity.ok(Map.of("status", "success", "message", "Deleted"));
            
        } catch(SQLException e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
    
    // ==================== TICKETS ====================
    
    @GetMapping("/tickets")
    public ResponseEntity<?> getAllTickets() {
        List<Map<String, Object>> tickets = new ArrayList<>();
        db.connect();
        
        try {
            Statement stmt = db.getConnection().createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM Ticket");
            
            while(results.next()) {
                Map<String, Object> ticket = new HashMap<>();
                ticket.put("TicketType", results.getString("TicketType"));
                ticket.put("Price", results.getDouble("Price"));
                tickets.add(ticket);
            }
            
            results.close();
            stmt.close();
            db.disconnect();
            return ResponseEntity.ok(tickets);
            
        } catch(Exception e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/tickets")
    public ResponseEntity<?> createTicket(@RequestBody Map<String, Object> data) {
        db.connect();
        try {
            Ticket t = new Ticket(
                ((Number)data.get("price")).doubleValue(),
                (String)data.get("ticketType")
            );
            
            db.insertTicket(t);
            db.disconnect();
            
            return ResponseEntity.status(201).body(Map.of("status", "success", "message", "Ticket added"));
                
        } catch(SQLException e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/tickets/{type}")
    public ResponseEntity<?> deleteTicket(@PathVariable String type) {
        db.connect();
        try {
            Ticket t = new Ticket(0, type);
            db.deleteTicket(t);
            db.disconnect();
            
            return ResponseEntity.ok(Map.of("status", "success", "message", "Deleted"));
            
        } catch(SQLException e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
    
    // ==================== GAMES ====================
    // Updated to match actual schema: GameID, Price, GameName, WinOdds, CustomerPlays
    
    @GetMapping("/games")
    public ResponseEntity<?> getAllGames() {
        List<Map<String, Object>> games = new ArrayList<>();
        db.connect();
        
        try {
            Statement stmt = db.getConnection().createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM CarnivalGame");
            
            while(results.next()) {
                Map<String, Object> game = new HashMap<>();
                game.put("gameID", results.getInt("GameID"));
                game.put("price", results.getDouble("Price"));
                game.put("gameName", results.getString("GameName"));
                game.put("winOdds", results.getDouble("WinOdds"));
                game.put("customerPlays", results.getInt("CustomerPlays"));
                games.add(game);
            }
            
            results.close();
            stmt.close();
            db.disconnect();
            return ResponseEntity.ok(games);
            
        } catch(Exception e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/games")
    public ResponseEntity<?> createGame(@RequestBody Map<String, Object> data) {
        db.connect();
        try {
            // Note: Your Game class might need updating to remove prizeID
            Game g = new Game(
                0,
                0, // prizeID - not used in your schema but might be in Game class
                ((Number)data.get("price")).doubleValue(),
                data.get("winOdds") != null ? ((Number)data.get("winOdds")).doubleValue() : 0.0,
                (String)data.get("gameName")
            );
            
            db.insertCarnivalGame(g);
            db.disconnect();
            
            return ResponseEntity.status(201).body(Map.of("status", "success", "message", "Game added"));
                
        } catch(SQLException e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/games/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable int id) {
        db.connect();
        try {
            Game g = db.lookupCarnivalGame(id);
            if (g == null) {
                db.disconnect();
                return ResponseEntity.status(404).body(Map.of("status", "error", "message", "Not found"));
            }
            
            db.deleteCarnivalGame(g);
            db.disconnect();
            
            return ResponseEntity.ok(Map.of("status", "success", "message", "Deleted"));
            
        } catch(SQLException e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
    
    // ==================== FOOD STALLS ====================
    
    @GetMapping("/foodstalls")
    public ResponseEntity<?> getAllFoodStalls() {
        List<Map<String, Object>> stalls = new ArrayList<>();
        db.connect();
        
        try {
            Statement stmt = db.getConnection().createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM FoodStall");
            
            while(results.next()) {
                Map<String, Object> stall = new HashMap<>();
                stall.put("stallID", results.getInt("StallID"));
                stall.put("price", results.getDouble("Price"));
                stall.put("stallName", results.getString("StallName"));
                stall.put("foodType", results.getString("FoodType"));
                stall.put("amountSold", results.getInt("AmountSold"));
                stalls.add(stall);
            }
            
            results.close();
            stmt.close();
            db.disconnect();
            return ResponseEntity.ok(stalls);
            
        } catch(Exception e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/foodstalls")
    public ResponseEntity<?> createFoodStall(@RequestBody Map<String, Object> data) {
        db.connect();
        try {
            FoodStall f = new FoodStall(
                0,
                data.get("amountSold") != null ? ((Number)data.get("amountSold")).intValue() : 0,
                ((Number)data.get("price")).doubleValue(),
                (String)data.get("stallName"),
                (String)data.get("foodType")
            );
            
            db.insertFoodStall(f);
            db.disconnect();
            
            return ResponseEntity.status(201).body(Map.of("status", "success", "message", "Food stall added"));
                
        } catch(SQLException e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
    
    @DeleteMapping("/foodstalls/{id}")
    public ResponseEntity<?> deleteFoodStall(@PathVariable int id) {
        db.connect();
        try {
            FoodStall f = db.lookupFoodStall(id);
            if (f == null) {
                db.disconnect();
                return ResponseEntity.status(404).body(Map.of("status", "error", "message", "Not found"));
            }
            
            db.deleteFoodStall(f);
            db.disconnect();
            
            return ResponseEntity.ok(Map.of("status", "success", "message", "Deleted"));
            
        } catch(SQLException e) {
            db.disconnect();
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", e.getMessage()));
        }
    }
    
    // ==================== ANALYTICS ====================

    @GetMapping("/analytics/average-ride-rating")
    public ResponseEntity<?> getAverageRideRating() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;
        
        try {
            String connectionUrl = "jdbc:mysql://138.49.184.123:3306/vagle0060_AmusementPark";
            conn = DriverManager.getConnection(connectionUrl, "vagle0060", "6p-uz5sTF7ErGchZW");
            
            stmt = conn.createStatement();
            results = stmt.executeQuery("SELECT avg(Rating) as AvgRating FROM Ride");
            
            if(results.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("avgRating", results.getDouble("AvgRating"));
                return ResponseEntity.ok(data);
            }
            
            return ResponseEntity.ok(Map.of("avgRating", 0.0));
            
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        } finally {
            try {
                if (results != null) results.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/analytics/lowest-rated-rides")
    public ResponseEntity<?> lowestRatedRides() {
        List<Map<String, Object>> rides = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;

        try {
            String connectionUrl = "jdbc:mysql://138.49.184.123:3306/vagle0060_AmusementPark";
            conn = DriverManager.getConnection(connectionUrl, "vagle0060", "6p-uz5sTF7ErGchZW");
            
            String sql = "SELECT RideName, Rating" +
					 "FROM Ride " +
					 "GROUP BY RideName, Rating " +
					 "HAVING avg(Rating) <= (SELECT Rating FROM Ride ORDER BY Rating ASC LIMIT 4, 1)";
            
            stmt = conn.createStatement();
            results = stmt.executeQuery(sql);
            
            while(results.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("RideName", results.getString("RideName"));
                item.put("avgRating", results.getDouble("AvgRating"));
                rides.add(item);
            }
        }
    }

    @GetMapping("/analytics/best-rated-ride-types")
    public ResponseEntity<?> getBestRatedRideTypes() {
        List<Map<String, Object>> rideTypes = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;
        
        try {
            String connectionUrl = "jdbc:mysql://138.49.184.123:3306/vagle0060_AmusementPark";
            conn = DriverManager.getConnection(connectionUrl, "vagle0060", "6p-uz5sTF7ErGchZW");
            
            String sql = "SELECT RideType, avg(Rating) as AvgRating " +
                        "FROM Ride " +
                        "GROUP BY RideType " +
                        "ORDER BY avg(Rating) DESC";
            
            stmt = conn.createStatement();
            results = stmt.executeQuery(sql);
            
            while(results.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("rideType", results.getString("RideType"));
                item.put("avgRating", results.getDouble("AvgRating"));
                rideTypes.add(item);
            }
            
            return ResponseEntity.ok(rideTypes);
            
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        } finally {
            try {
                if (results != null) results.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/analytics/customer-activity-report")
    public ResponseEntity<?> getCustomerActivityReport() {
        List<Map<String, Object>> customers = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;
        
        try {
            String connectionUrl = "jdbc:mysql://138.49.184.123:3306/vagle0060_AmusementPark";
            conn = DriverManager.getConnection(connectionUrl, "vagle0060", "6p-uz5sTF7ErGchZW");
            
            String sql = "SELECT c.CustomerID, c.FirstName, c.LastName, t.TicketType, " + 
                        "count(DISTINCT r.RideID) as RidesRidden, " + 
                        "count(DISTINCT p.GameID) as GamesPlayed, " + 
                        "count(DISTINCT e.StallID) as FoodEaten " + 
                        "FROM Customer c JOIN Ticket t ON c.TicketType = t.TicketType " + 
                        "JOIN RIDE_ON r ON c.CustomerID = r.CustomerID " + 
                        "JOIN PLAY p ON c.CustomerID = p.CustomerID " + 
                        "JOIN EAT_AT e ON c.CustomerID = e.CustomerID " + 
                        "GROUP BY c.CustomerID, c.FirstName, c.LastName, t.TicketType";
            
            stmt = conn.createStatement();
            results = stmt.executeQuery(sql);
            
            while(results.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("customerID", results.getInt("CustomerID"));
                item.put("firstName", results.getString("FirstName"));
                item.put("lastName", results.getString("LastName"));
                item.put("ticketType", results.getString("TicketType"));
                item.put("ridesRidden", results.getInt("RidesRidden"));
                item.put("gamesPlayed", results.getInt("GamesPlayed"));
                item.put("foodEaten", results.getInt("FoodEaten"));
                customers.add(item);
            }
            
            return ResponseEntity.ok(customers);
            
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        } finally {
            try {
                if (results != null) results.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/analytics/above-avg-wait-times")
    public ResponseEntity<?> getAboveAvgWaitTimes() {
        List<Map<String, Object>> rides = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;
        
        try {
            String connectionUrl = "jdbc:mysql://138.49.184.123:3306/vagle0060_AmusementPark";
            conn = DriverManager.getConnection(connectionUrl, "vagle0060", "6p-uz5sTF7ErGchZW");
            
            String sql = "SELECT * " + 
                        "FROM Ride " +
                        "WHERE AvgWaitTime > (SELECT avg(AvgWaitTime) FROM Ride)";
            
            stmt = conn.createStatement();
            results = stmt.executeQuery(sql);
            
            while(results.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("rideID", results.getInt("RideID"));
                item.put("rideName", results.getString("RideName"));
                item.put("avgWaitTime", results.getDouble("AvgWaitTime"));
                item.put("rideType", results.getString("RideType"));
                rides.add(item);
            }
            
            return ResponseEntity.ok(rides);
            
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        } finally {
            try {
                if (results != null) results.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/analytics/customers-who-did-everything")
    public ResponseEntity<?> getCustomersWhoDidEverything() {
        List<Map<String, Object>> customers = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;
        
        try {
            String connectionUrl = "jdbc:mysql://138.49.184.123:3306/vagle0060_AmusementPark";
            conn = DriverManager.getConnection(connectionUrl, "vagle0060", "6p-uz5sTF7ErGchZW");
            
            String sql = "SELECT c.CustomerID, c.FirstName, c.LastName " +
                        "FROM Customer as c "+
                        "WHERE c.CustomerID IN (SELECT CustomerID FROM RIDE_ON) " +
                        "AND c.CustomerID IN (SELECT CustomerID FROM PLAY) " + 
                        "AND c.CustomerID IN (SELECT CustomerID FROM EAT_AT)";
            
            stmt = conn.createStatement();
            results = stmt.executeQuery(sql);
            
            while(results.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("customerID", results.getInt("CustomerID"));
                item.put("firstName", results.getString("FirstName"));
                item.put("lastName", results.getString("LastName"));
                customers.add(item);
            }
            
            return ResponseEntity.ok(customers);
            
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        } finally {
            try {
                if (results != null) results.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
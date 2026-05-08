package com.amusementpark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@SpringBootApplication
public class RestApiServer {
    
    public static void main(String[] args) {
        SpringApplication.run(RestApiServer.class, args);
        System.out.println("\n🎢 Amusement Park REST API Running!");
        System.out.println("📍 Server: http://localhost:8080");
    }
}

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
class ApiController {
    
    @Autowired
    private Database db;
    
    @GetMapping("/test")
    public String test() {
        return "API is working!";
    }
    
    // GET all customers - using YOUR Database.java methods
    @GetMapping("/customers")
    public ResponseEntity<?> getAllCustomers() {
        db.connect();
        try {
            // Using YOUR runQuery method from Database.java line 85
            ResultSet results = db.runQuery("SELECT * FROM Customer");
            List<Map<String, Object>> customers = new ArrayList<>();
            
            while(results.next()) {
                Map<String, Object> customer = new HashMap<>();
                customer.put("customerID", results.getInt("CustomerID"));
                customer.put("firstName", results.getString("FirstName"));
                customer.put("lastName", results.getString("LastName"));
                customer.put("height", results.getInt("Height"));
                customer.put("age", results.getInt("Age"));
                customer.put("ticketType", results.getString("TicketType"));
                customers.add(customer);
            }
            
            db.disconnect();
            return ResponseEntity.ok(customers);
            
        } catch(SQLException e) {
            db.disconnect();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
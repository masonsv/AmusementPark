package main.com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmusementParkWebApp {
    public static void main(String[] args) {
        SpringApplication.run(AmusementParkWebApp.class, args);
        System.out.println("\n🎢 Web Server Running!");
        System.out.println("📍 API: http://localhost:8080/api");
        System.out.println("📍 Open frontend/index.html in your browser\n");
    }
}
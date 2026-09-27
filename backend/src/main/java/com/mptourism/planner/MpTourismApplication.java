package com.mptourism.planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MpTourismApplication {

    public static void main(String[] args) {
        SpringApplication.run(MpTourismApplication.class, args);
        System.out.println("\n=======================================================");
        System.out.println("  MADHYA PRADESH TOURISM TRIP PLANNER API IS RUNNING ");
        System.out.println("  Server URL: http://localhost:8080/");
        System.out.println("  H2 Console: http://localhost:8080/h2-console");
        System.out.println("=======================================================\n");
    }
}

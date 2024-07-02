package com.example.Employee_Scheduling;

import com.example.Employee_Scheduling.Repository.AvailabilityRepository;
import com.example.Employee_Scheduling.Repository.EmployeeRepository;
import com.example.Employee_Scheduling.Repository.ShiftRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@Slf4j
public class EmployeeSchedulingApplication {



	public static void main(String[] args) {
		SpringApplication.run(EmployeeSchedulingApplication.class, args);
	}

}

package com.example.Employee_Scheduling;

import com.example.Employee_Scheduling.Repository.AvailabilityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
public class EmployeeSchedulingApplication {



	public static void main(String[] args) {
		SpringApplication.run(EmployeeSchedulingApplication.class, args);
	}



}

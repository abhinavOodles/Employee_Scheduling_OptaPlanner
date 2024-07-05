package com.example.Employee_Scheduling;

import com.example.Employee_Scheduling.DTOS.EmployeeDTO;
import com.example.Employee_Scheduling.Domain.Availability;
import com.example.Employee_Scheduling.Domain.Employee;
import com.example.Employee_Scheduling.Domain.Shift;
import com.example.Employee_Scheduling.Repository.AvailabilityRepository;
import com.example.Employee_Scheduling.Repository.EmployeeRepository;
import com.example.Employee_Scheduling.Repository.ShiftRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.drools.io.ClassPathResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private AvailabilityRepository availabilityRepository;


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            if (employeeRepository.count() < 1) {
                saveEmployee();
            } else {
                log.info("Employee data already exists.");
            }

            if (shiftRepository.count() < 1) {
                saveShift();
            } else {
                log.info("Shift data already exists.");
            }

            if (availabilityRepository.count() < 1) {
                saveAvailability();
            } else {
                log.info("Availability data already exists.");
            }
        } catch (Exception e) {
            log.error("Error during bootstrap data initialization.", e);
        }
    }

    private void saveEmployee() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<EmployeeDTO> employeeDTOs = mapper.readValue(
                new ClassPathResource("json/Employee.json").getInputStream(),
                new com.fasterxml.jackson.core.type.TypeReference<List<EmployeeDTO>>() {}
        );

        List<Employee> employees = employeeDTOs.stream()
                .map(this::mapEmployeeDTOToEmployee)
                .collect(Collectors.toList());

        employeeRepository.saveAll(employees);
        log.info("Employees saved Total count: {}", employeeRepository.count());
    }

    private Employee mapEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        employee.setName(employeeDTO.getName());
//        employee.setSkills(employeeDTO.getSkills());
      //  employee.setAvailability(employeeDTO.getAvailability());
        return employee;
    }

    private void saveShift() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Shift> shifts = mapper.readValue(
                new ClassPathResource("json/Shift.json").getInputStream(),
                new com.fasterxml.jackson.core.type.TypeReference<List<Shift>>() {}
        );
        shiftRepository.saveAll(shifts);
        log.info("Shifts saved Total count: {}", shiftRepository.count());
    }

    private void saveAvailability() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Availability> availabilities = mapper.readValue(
                new ClassPathResource("json/Availability.json").getInputStream(),
                new com.fasterxml.jackson.core.type.TypeReference<List<Availability>>() {}
        );
        availabilityRepository.saveAll(availabilities);
        log.info("Availabilities saved Total count: {}", availabilityRepository.count());
    }
}


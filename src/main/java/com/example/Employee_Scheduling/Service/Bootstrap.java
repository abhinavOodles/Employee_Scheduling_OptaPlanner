//package com.example.Employee_Scheduling.Service;
//
//import com.example.Employee_Scheduling.Domain.Availability;
//import com.example.Employee_Scheduling.Domain.Employee;
//import com.example.Employee_Scheduling.Domain.Shift;
//import com.example.Employee_Scheduling.Repository.AvailabilityRepository;
//import com.example.Employee_Scheduling.Repository.EmployeeRepository;
//import com.example.Employee_Scheduling.Repository.ShiftRepository;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Service;
//
//
//import java.io.InputStream;
//import java.util.List;
//
//@Service
//
//
//public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {
//    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);
//
//    @Autowired
//    private EmployeeRepository employeeRepository ;
//    @Autowired
//    private ShiftRepository shiftRepository ;
//    @Autowired
//    private AvailabilityRepository availabilityRepository ;
//
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        if (employeeRepository.count() < 1) {
//            log.info("Employee has been  Created.");
//        } else {
//            log.info("Employee already exist.");
//        }
//
//        if (shiftRepository.count()<1){
//            log.info("Shift has been created");
//        }
//        else{
//            log.info("Shift already exist");
//        }
//
//        if(availabilityRepository.count()<1){
//            log.info("Availability has been created");
//        }
//        else{
//            log.info("Availability already exist");
//        }
//    }
//
//    private void saveEmployee() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        com.fasterxml.jackson.core.type.TypeReference<List<Employee>> mapType = new com.fasterxml.jackson.core.type.TypeReference<List<Employee>>() {};
//        InputStream is = TypeReference.class.getResourceAsStream("/json/Employee.json");
//
//        try {
//
//            List<Employee> employees = mapper.readValue(is, mapType);
//            employeeRepository.saveAll(employees) ;
//            log.info("size of Data: "+employeeRepository.count());
//        }
//        catch (Exception e){
//            throw new Exception("Some Error occured");
//        }
//    }
//    private void saveShift() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        com.fasterxml.jackson.core.type.TypeReference<List<Shift>> mapType = new com.fasterxml.jackson.core.type.TypeReference<List<Shift>>() {};
//        InputStream is = TypeReference.class.getResourceAsStream("/json/Shift.json");
//
//        try {
//
//            List<Shift> shifts = mapper.readValue(is, mapType);
//            shiftRepository.saveAll(shifts) ;
//            log.info("size of Data: "+shiftRepository.count());
//
//        }
//        catch (Exception e){
//            throw new Exception("Some Error occured");
//        }
//    }
//    private void saveAvailability() throws Exception {
//        ObjectMapper mapper = new ObjectMapper();
//        TypeReference<List<Availability>> mapType = new TypeReference<List<Availability>>() {};
//        InputStream is = TypeReference.class.getResourceAsStream("/json/Availability.json");
//
//        try {
//
//            List<Availability> availabilities = mapper.readValue(is, mapType);
//            availabilityRepository.saveAll(availabilities) ;
//
//        }
//        catch (Exception e){
//            throw new Exception("Some Error Occurred");
//        }
//    }
//
//
//}
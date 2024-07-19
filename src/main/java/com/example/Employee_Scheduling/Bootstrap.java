//package com.example.Employee_Scheduling;
//
//import com.example.Employee_Scheduling.DTOS.AvailabilityDTO;
//import com.example.Employee_Scheduling.DTOS.EmployeeDTO;
//import com.example.Employee_Scheduling.DTOS.ShiftDTO;
//import com.example.Employee_Scheduling.DTOS.SkillDTO;
//import com.example.Employee_Scheduling.Domain.Availability;
//import com.example.Employee_Scheduling.Domain.Employee;
//import com.example.Employee_Scheduling.Domain.Shift;
//import com.example.Employee_Scheduling.Domain.Skill;
//import com.example.Employee_Scheduling.Repository.AvailabilityRepository;
//import com.example.Employee_Scheduling.Repository.EmployeeRepository;
//import com.example.Employee_Scheduling.Repository.ShiftRepository;
//import com.example.Employee_Scheduling.Repository.SkillRepository;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import jakarta.transaction.Transactional;
//import org.drools.io.ClassPathResource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.stereotype.Service;
//
//
//import java.io.IOException;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Service
//public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {
//
//    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);
//
//    @Autowired
//    private EmployeeRepository employeeRepository;
//
//    @Autowired
//    private ShiftRepository shiftRepository;
//
//    @Autowired
//    private AvailabilityRepository availabilityRepository;
//
//    @Autowired
//    private SkillRepository skillRepository;
//
//
//    @Override
// /*   @Transactional*/
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//        try {
//            if (employeeRepository.count() < 1) {
//                saveEmployee();
//            } else {
//                log.info("Employee data already exists.");
//            }
//
//            if (shiftRepository.count() < 1) {
//                saveShift();
//            } else {
//                log.info("Shift data already exists.");
//            }
//
//            if (availabilityRepository.count() < 1) {
//                saveAvailability();
//            } else {
//                log.info("Availability data already exists.");
//            }
////            if (skillRepository.count() < 1) {
////                saveSkill();
////            } else {
////                log.info("Skill data already exists.");
////
////            }
//        } catch (Exception e) {
//            log.error("Error during bootstrap data initialization.", e);
//        }
//    }
//
//    private void saveEmployee() throws IOException {
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule with ObjectMapper
//
//        // Your deserialization code
//        List<EmployeeDTO> employeeDTOs = mapper.readValue(
//                new ClassPathResource("json/Employee.json").getInputStream(),
//                new com.fasterxml.jackson.core.type.TypeReference<List<EmployeeDTO>>() {
//                }
//        );
//
//
//        List<Employee> employees = employeeDTOs.stream()
//                .map(this::mapEmployeeDTOToEmployee)
//                .collect(Collectors.toList());
//
//        employeeRepository.saveAll(employees);
//        log.info("Employees saved Total count: {}", employeeRepository.count());
//    }
//
//
//    private Employee mapEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
//        Employee employee = new Employee();
//
//        employee.setName(employeeDTO.getName());
//        employee.setSkills(employeeDTO.getSkills());
//        employee.setAvailability(employeeDTO.getAvailability());
//        //employee.setShifts(employeeDTO.getShifts());
//        return employee;
//    }
//
//    private void saveShift() throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        List<ShiftDTO> shiftDTO = mapper.readValue(
//                new ClassPathResource("json/Shift.json").getInputStream(),
//                new TypeReference<List<ShiftDTO>>() {
//                }
//        );
//        List<Shift> shift = shiftDTO.stream()
//                .map(this::mapShiftDTOToEmployee)
//                .collect(Collectors.toList());
//
//        shiftRepository.saveAll(shift);
//
//        log.info("Shifts saved Total count: {}", shiftRepository.count());
//    }
//
//    private Shift mapShiftDTOToEmployee(ShiftDTO shiftDTO) {
//        Shift shift = new Shift();
//
//        shift.setStartTime(shiftDTO.getStartTime());
//        shift.setEndTime(shiftDTO.getEndTime());
//        shift.setRequiredSkill(shiftDTO.getRequiredSkill());
//        shift.setLocation(shiftDTO.getLocation());
//
//        return shift;
//    }
//
//    private void saveAvailability() throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        List<AvailabilityDTO> availabilities1 = mapper.readValue(
//                new ClassPathResource("json/Availability.json").getInputStream(),
//                new com.fasterxml.jackson.core.type.TypeReference<List<AvailabilityDTO>>() {}
//        );
//
//        List<Availability> availabilities = availabilities1.stream()
//                        .map(this::mapAvailabilityDTOTOAvailability)
//                                .collect(Collectors.toList()) ;
//
//        availabilityRepository.saveAll(availabilities);
//        log.info("Availabilities saved Total count: {}", availabilityRepository.count());
//    }
//
//    private Availability mapAvailabilityDTOTOAvailability (AvailabilityDTO availabilityDTO) {
//
//        Availability availability = new Availability();
//
//        availability.setEmployee(availabilityDTO.getEmployee());
//        availability.setStartTime(availabilityDTO.getStartTime());
//        availability.setEndTime(availabilityDTO.getEndTime());
//        availability.setAvailabilityType(availabilityDTO.getAvailabilityType());
//
//
//        return availability ;
//    }
//
////    private void saveSkill () throws  IOException{
////        ObjectMapper objectMapper = new ObjectMapper();
////        List<SkillDTO> skillDTOS =objectMapper.readValue(
////                new ClassPathResource("json/Skill.json").getInputStream(),
////                new TypeReference<List<SkillDTO>>() {}
////        );
////
////        List<Skill> shifts = skillDTOS.stream()
////                .map(this::mapSkillDTOTOSkill)
////                .collect(Collectors.toList()) ;
////
////        skillRepository.saveAll(shifts)  ;
////        log.info("Skill saved Total count: {}", skillRepository.count());
////
////    }
////
////    private Skill mapSkillDTOTOSkill(SkillDTO skillDTO) {
////      Skill skill = new Skill() ;
////
////      skill.setSkillName(skillDTO.getSkillName());
////      skill.setEmployee(skillDTO.getEmployee());
////
////      return skill ;
////    }
//
//}
//

package com.example.Employee_Scheduling.Service;


import com.example.Employee_Scheduling.DTOS.*;
import com.example.Employee_Scheduling.Domain.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.optaplanner.core.api.score.ScoreExplanation;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.constraint.ConstraintMatch;
import org.optaplanner.core.api.score.constraint.Indictment;
import org.optaplanner.core.api.solver.SolutionManager;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.SolverManagerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Transactional
@Service
public class SolverService {


    private static final Logger log = LoggerFactory.getLogger(SolverService.class);
//    @Autowired
//    private EmployeeRepository employeeRepository ;
//    @Autowired
//    private AvailabilityRepository availabilityRepository ;
//    @Autowired
//    private ShiftRepository shiftRepository ;
//    @Autowired
//    private SkillRepository skillRepository ;

//    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


    private SolutionManager<EmployeeScheduling, HardSoftScore> solutionManager;
    private SolverManager<EmployeeScheduling, String> solverManager;


    SolverService() {
        SolverConfig solverConfig = SolverConfig.createFromXmlResource("SolverConfig.xml");
        this.solverManager = SolverManager.create(solverConfig, new SolverManagerConfig());
        SolverFactory<EmployeeScheduling> solverFactory = SolverFactory.create(solverConfig);
        this.solutionManager = SolutionManager.create(solverFactory);
    }

    public List<Shift> solver(EmployeeSchedulingDTO employeeSchedulingDTO) {
        EmployeeScheduling employeeScheduling = new EmployeeScheduling ();

//        List<Employee> employees = employeeRepository.findAll();
//        List<Availability> availabilities = availabilityRepository.findAll();

        // Map<Employee,List<Availability>> map = availabilities.stream().collect(Collectors.groupingBy(Availability::getEmployee));
//        for(Employee employee : employees){
//            if(Objects.nonNull(map.get(employee))){
//                List<OptAvailability> optAvailabilities = new ArrayList<>();
//                for(Availability availability : map.get(employee)){
//                    OptAvailability optAvailability = new OptAvailability();
//                    optAvailability.setStartTime(availability.getStartTime());
//                    optAvailability.setEndTime(availability.getEndTime());
//                    optAvailability.setAvailabilityType(availability.getAvailabilityType());
//                    optAvailabilities.add(optAvailability);
//                }
//                employee.setOptAvailabilities(optAvailabilities);
//
//            }
//        }

//        List<Shift> shifts = shiftRepository.findAll();
//        List<Skill> skills = skillRepository.findAll();




        List<EmployeeDTO> employee1 = employeeSchedulingDTO.getEmployeeDTOList() ;
        List<Employee> employee  = new ArrayList<>()  ;

        for (EmployeeDTO employee2 : employee1){
        Employee employee3  = new Employee() ;

        //employee3.setId(employee2.getId());
        employee3.setName(employee2.getName());
        employee3.setSkills(employee2.getSkills());
        employee3.setAvailabilityType(employee2.getAvailabilityType());

        employee.add(employee3) ;
        }

        List<AvailabilityDTO> availabilityDTOList = employeeSchedulingDTO.getAvailabilityDTOS() ;
        List<Availability> availability = new ArrayList<>() ;

        for (AvailabilityDTO availabilityDTO : availabilityDTOList){
            Availability availability1 = new Availability() ;

            availability1.setStartTime(availabilityDTO.getStartTime());
            availability1.setEndTime(availabilityDTO.getEndTime());
            availability1.setId(availabilityDTO.getId());

            Employee employee2  = new Employee() ;
            //employee2.setId(availabilityDTO.getEmployee().getId());
            employee2.setName(availabilityDTO.getEmployee().getName());
            employee2.setSkills(availabilityDTO.getEmployee().getSkills());
            availability1.setEmployee(employee2);

            availability1.setAvailabilityType(availabilityDTO.getAvailabilityType());

            availability.add(availability1) ;
        }


        List<ShiftDTO> shiftDTOList = employeeSchedulingDTO.getShiftDTOS() ;
        List<Shift> shift = new ArrayList<>() ;

        for (ShiftDTO shiftDTO : shiftDTOList){
            Shift shift1 = new Shift() ;

            shift1.setId(shiftDTO.getId());
            shift1.setStartTime(shiftDTO.getStartTime());
            shift1.setEndTime(shiftDTO.getEndTime());
            shift1.setLocation(shiftDTO.getLocation());
            shift1.setRequiredSkill(shiftDTO.getRequiredSkill());

            shift.add(shift1) ;
        }

        List<SkillDTO> skillDTOList = employeeSchedulingDTO.getSkillDTOList() ;
        List<Skill> skill = new ArrayList<>() ;

        for (SkillDTO skillDTO : skillDTOList){
            Skill skill1 = new Skill() ;

            //skill1.setId(skillDTO.getId());
            skill1.setSkillName(skillDTO.getSkillName());

            skill.add(skill1) ;
        }

        employeeScheduling.setEmployees(employee);
        employeeScheduling.setAvailabilities(availability);
        employeeScheduling.setShifts(shift);
        employeeScheduling.setSkills(skill);


        String jobId = UUID.randomUUID().toString();
        SolverJob<EmployeeScheduling, String> solverJob = solverManager.solve(jobId, employeeScheduling);


        EmployeeScheduling solution;
        try {
            // Returns only after solving terminates
            solution = solverJob.getFinalBestSolution();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

        // Convert solution to JSON string
        String json = null;
        try {
            json = objectMapper.writeValueAsString(solverJob);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Print or save the JSON string
        System.out.println(json);

        // Optionally, save JSON to a file
        try {
            objectMapper.writeValue(new File("solution.json"), solverJob);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ScoreExplanation<EmployeeScheduling, HardSoftScore> scoreExplanation = solutionManager.explain(solution);
        Map<Object, Indictment<HardSoftScore>> indictmentMap = scoreExplanation.getIndictmentMap();

        for (Shift process : solution.getShifts()) {
            Indictment<HardSoftScore> indictment = indictmentMap.get(process);
            if (indictment == null) {
                continue;
            }
            // The score impact of that planning entity
            HardSoftScore totalScore = indictment.getScore();

            for (ConstraintMatch<HardSoftScore> constraintMatch : indictment.getConstraintMatchSet()) {
                String constraintName = constraintMatch.getConstraintName();
                HardSoftScore score = constraintMatch.getScore();
                log.info("ShiftID::::::::::{},Constraint name ::::::{},:::::::Score{}", process.getId(), constraintName, score);
            }
        }

    return solution.getShifts() ;
    }
}

//    public void getEmployee() {
//
////        List<Employee> employeeList = employeeRepository.findAll();
////        List<Availability> availabilities = availabilityRepository.findAll();
////        List<Shift> shifts = shiftRepository.findAll() ;
////        List<Skill> skills = skillRepository.findAll() ;
//
//
//        for (Employee employee : employeeList){
//            System.out.print(employee);
//            System.out.println();
//        }
//
//        for (Availability availability: availabilities){
//            System.out.print(availability);
//            System.out.println();
//        }
//
//        for (Shift shift : shifts){
//            System.out.println(shift);
//            System.out.println();
//        }
//
//        for (Skill skill : skills){
//            System.out.print(skill);
//            System.out.println();
//        }
//
//    }
//}

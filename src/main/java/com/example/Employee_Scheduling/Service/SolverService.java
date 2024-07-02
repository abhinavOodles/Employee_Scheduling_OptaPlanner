package com.example.Employee_Scheduling.Service;


import com.example.Employee_Scheduling.Domain.*;
import com.example.Employee_Scheduling.Repository.AvailabilityRepository;
import com.example.Employee_Scheduling.Repository.EmployeeRepository;
import com.example.Employee_Scheduling.Repository.ShiftRepository;
import jakarta.transaction.Transactional;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolutionManager;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.SolverManagerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class SolverService {


    private static final Logger log = LoggerFactory.getLogger(SolverService.class);
    @Autowired
    private EmployeeRepository employeeRepository ;
    @Autowired
    private AvailabilityRepository availabilityRepository ;
    @Autowired
    private ShiftRepository shiftRepository ;

   private SolutionManager<EmployeeScheduling, HardSoftScore> solutionManager ;
    private SolverManager<EmployeeScheduling,String>  solverManager ;


    SolverService() {
        SolverConfig solverConfig = SolverConfig.createFromXmlResource("SolverConfig.xml");
        this.solverManager  = SolverManager.create(solverConfig, new SolverManagerConfig());
        SolverFactory<EmployeeScheduling> solverFactory = SolverFactory.create(solverConfig);
        this.solutionManager = SolutionManager.create(solverFactory);
    }

    public List<Shift> solver ()  {
        EmployeeScheduling employeeScheduling = new EmployeeScheduling() ;

        List<Employee> employees = employeeRepository.findAll() ;
        List<Availability> availabilities = availabilityRepository.findAll();

        Map<Employee,List<Availability>> map = availabilities.stream().collect(Collectors.groupingBy(Availability::getEmployee));
        for(Employee employee : employees){
            if(Objects.nonNull(map.get(employee))){
                List<OptAvailability> optAvailabilities = new ArrayList<>();
                for(Availability availability : map.get(employee)){
                    OptAvailability optAvailability = new OptAvailability();
                    optAvailability.setStartTime(availability.getStartTime());
                    optAvailability.setEndTime(availability.getEndTime());
                    optAvailability.setAvailabilityType(availability.getAvailabilityType());
                    optAvailabilities.add(optAvailability);
                }
                employee.setAvailabilities(optAvailabilities);

            }
        }

        List<Shift> shifts = shiftRepository.findAll() ;



        employeeScheduling.setEmployees(employees);
        employeeScheduling.setAvailabilities(availabilities);
        employeeScheduling.setShifts(shifts);

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

        return solution.getShifts() ;
    }
}

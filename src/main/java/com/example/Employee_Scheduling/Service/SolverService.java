package com.example.Employee_Scheduling.Service;


import com.example.Employee_Scheduling.Domain.Availability;
import com.example.Employee_Scheduling.Domain.Employee;
import com.example.Employee_Scheduling.Domain.EmployeeScheduling;
import com.example.Employee_Scheduling.Domain.Shift;
import com.example.Employee_Scheduling.Repository.AvailabilityRepository;
import com.example.Employee_Scheduling.Repository.EmployeeRepository;
import com.example.Employee_Scheduling.Repository.ShiftRepository;
import lombok.AllArgsConstructor;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolutionManager;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.SolverManagerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.List;
import java.util.UUID;

public class SolverService {

    @Autowired
    SolutionManager<EmployeeScheduling, HardSoftScore> solutionManager ;
    @Autowired
    SolverManager<EmployeeScheduling,String>  solverManager ;
    @Autowired
    EmployeeRepository employeeRepository ;
    AvailabilityRepository availabilityRepository ;
    ShiftRepository shiftRepository ;

    SolverService() {
        SolverConfig solverConfig = SolverConfig.createFromXmlResource("SolverConfig.xml");
        this.solverManager  = SolverManager.create(solverConfig, new SolverManagerConfig());
        SolverFactory<EmployeeScheduling> solverFactory = SolverFactory.create(solverConfig);
        this.solutionManager = SolutionManager.create(solverFactory);
    }

    public List<Shift> solver (){

        List<Employee> employees = employeeRepository.findAll();
        List<Availability> availabilities = availabilityRepository.findAll();

        List<Shift> shifts = shiftRepository.findAll();

        EmployeeScheduling employeeScheduling = new EmployeeScheduling() ;
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

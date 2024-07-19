package com.example.Employee_Scheduling.Domain;

import lombok.Data;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverStatus;

import java.util.List;

@PlanningSolution
@Data
public class EmployeeScheduling {

    private String name;

    @ProblemFactCollectionProperty
    @ValueRangeProvider(id = "availabilityRange")
    private List<Availability> availabilities;

    @ProblemFactCollectionProperty
    @ValueRangeProvider
    private List<Employee> employees;

    @PlanningEntityCollectionProperty
    private List<Shift> shifts;

    @ProblemFactCollectionProperty
    private List<Skill> skills ;

    @PlanningScore
    private HardSoftScore score;

    private SolverStatus solverStatus;

    public EmployeeScheduling(){
    }

    public EmployeeScheduling(List<Availability> availabilities, List<Employee> employees, List<Shift> shifts) {
        this.availabilities = availabilities;
        this.employees = employees;
        this.shifts = shifts;
    }


}

package com.example.Employee_Scheduling.Controller;

import com.example.Employee_Scheduling.DTOS.EmployeeDTO;
import com.example.Employee_Scheduling.Service.SolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public class SolverController {

    @Autowired
    private SolverService solverService ;


    @PostMapping("/startSolving")
    private ResponseEntity<?> solving (@RequestBody EmployeeDTO employeeDTO){
        return ResponseEntity.ok(solverService.solver());
    }
}

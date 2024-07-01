package com.example.Employee_Scheduling.Controller;

import com.example.Employee_Scheduling.DTOS.EmployeeDTO;
import com.example.Employee_Scheduling.Service.SolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/solver")
public class SolverController {


    @Autowired
    private SolverService solverService ;


    @PostMapping("/start")
    private ResponseEntity<?> solving ()  {
        return ResponseEntity.ok(solverService.solver());
    }
}

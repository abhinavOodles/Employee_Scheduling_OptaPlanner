package com.example.Employee_Scheduling.Controller;

import com.example.Employee_Scheduling.Service.SolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/solver")
public class SolverController {

    private static final long PROBLEM_ID = 0L;

     @Autowired
     SolverService solverService ;

    @PostMapping("/start")
    private ResponseEntity<?> solving ()  {
        return ResponseEntity.ok(solverService.solver());
    }


    @GetMapping
    private  void getEmployee(){
         solverService.getEmployee();
    }
}

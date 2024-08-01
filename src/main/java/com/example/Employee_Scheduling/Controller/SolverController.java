package com.example.Employee_Scheduling.Controller;

import com.example.Employee_Scheduling.DTOS.EmployeeSchedulingDTO;
import com.example.Employee_Scheduling.Service.SolverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;




@RestController
@RequestMapping("/solver")
public class SolverController {

    private static final long PROBLEM_ID = 0L;

    @Autowired
     SolverService solverService ;

    @PostMapping("/start")
    private ResponseEntity<?> solving (@RequestBody EmployeeSchedulingDTO employeeSchedulingDTO) {
        return ResponseEntity.ok(solverService.solver(employeeSchedulingDTO));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }
}

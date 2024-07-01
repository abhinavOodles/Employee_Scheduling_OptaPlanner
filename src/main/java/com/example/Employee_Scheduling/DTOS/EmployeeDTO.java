package com.example.Employee_Scheduling.DTOS;

import com.example.Employee_Scheduling.Domain.Availability;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class EmployeeDTO {
    private String name;

    private Set<String> skills;

    private List<Availability> availability;
}

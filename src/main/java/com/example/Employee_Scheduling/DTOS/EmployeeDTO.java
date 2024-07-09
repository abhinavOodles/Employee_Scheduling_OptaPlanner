package com.example.Employee_Scheduling.DTOS;

import com.example.Employee_Scheduling.Domain.Availability;
import com.example.Employee_Scheduling.Domain.Shift;
import com.example.Employee_Scheduling.Domain.Skill;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDTO {

    private String name;

    private List<Skill> skills;


    private List<Availability> availability;

    private Set<Shift> shifts ;
}

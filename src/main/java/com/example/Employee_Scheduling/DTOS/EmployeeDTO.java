package com.example.Employee_Scheduling.DTOS;

import com.example.Employee_Scheduling.Domain.Availability;
import com.example.Employee_Scheduling.Domain.Skill;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties
public class EmployeeDTO {

    private String name;

    private List<Skill> skills;

    private Availability availability;
}

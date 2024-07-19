package com.example.Employee_Scheduling.DTOS;


import com.example.Employee_Scheduling.Domain.Skill;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.Set;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDTO {

    private String name;
    private Set<Skill> skills ;


}

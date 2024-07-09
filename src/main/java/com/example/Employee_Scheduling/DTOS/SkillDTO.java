package com.example.Employee_Scheduling.DTOS;

import com.example.Employee_Scheduling.Domain.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillDTO {

    private String skillName ;

    private Employee employee ;

}

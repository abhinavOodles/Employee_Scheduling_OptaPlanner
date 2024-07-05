package com.example.Employee_Scheduling.DTOS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillDTO {

    private String skillName ;
}

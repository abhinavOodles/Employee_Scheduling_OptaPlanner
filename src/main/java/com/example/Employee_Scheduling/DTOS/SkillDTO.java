package com.example.Employee_Scheduling.DTOS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class SkillDTO {

    public String skillName ;
}

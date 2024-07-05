package com.example.Employee_Scheduling.DTOS;

import com.example.Employee_Scheduling.Domain.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties
public class ShiftDTO {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private String requiredSkill;
    private Employee employee ;

}

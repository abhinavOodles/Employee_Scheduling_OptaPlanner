package com.example.Employee_Scheduling.DTOS;

import com.example.Employee_Scheduling.DateTimeDeserializer;
import com.example.Employee_Scheduling.Domain.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties
public class ShiftDTO {

    @JsonDeserialize(using = DateTimeDeserializer.class)
    private LocalDateTime startTime;
    @JsonDeserialize(using = DateTimeDeserializer.class)
    private LocalDateTime endTime;
    private String location;
    private String requiredSkill;
    private Employee employee ;

}

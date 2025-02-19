package com.example.Employee_Scheduling.DTOS;

import com.example.Employee_Scheduling.DateTimeDeserializer;
import com.example.Employee_Scheduling.Domain.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShiftDTO {


    private Long id  ;

    @JsonDeserialize(using = DateTimeDeserializer.class)
    private LocalDateTime startTime;

    @JsonDeserialize(using = DateTimeDeserializer.class)
    private LocalDateTime endTime;

    private String location;

    private String requiredSkill;


}

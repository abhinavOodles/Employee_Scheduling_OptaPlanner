package com.example.Employee_Scheduling.DTOS;

import com.example.Employee_Scheduling.Domain.AvailabilityType;
import com.example.Employee_Scheduling.Domain.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@JsonIgnoreProperties
public class AvailabilityDTO {

private Employee employee ;
//    @JsonFormat(pattern = "HH:mm:ss")
//    private LocalTime startTime ;
//
//    @JsonFormat(pattern = "HH:mm:ss")
//    private LocalTime endTime ;

    @Enumerated(EnumType.STRING)
    private AvailabilityType availabilityType ;
}

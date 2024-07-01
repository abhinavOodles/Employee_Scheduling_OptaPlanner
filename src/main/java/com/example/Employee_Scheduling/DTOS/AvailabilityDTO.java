package com.example.Employee_Scheduling.DTOS;

//import com.example.Employee_Scheduling.Domain.AvailabilityType;
import com.example.Employee_Scheduling.Domain.Employee;
import lombok.Data;

import java.time.LocalTime;

@Data
public class AvailabilityDTO {

    private Employee employee ;
    private LocalTime startTime ;
    private LocalTime endTime ;
//    private AvailabilityType availabilityType ;
}

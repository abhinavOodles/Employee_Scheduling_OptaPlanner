package com.example.Employee_Scheduling.DTOS;

import com.example.Employee_Scheduling.DateTimeDeserializer;
import com.example.Employee_Scheduling.Domain.AvailabilityType;
import com.example.Employee_Scheduling.Domain.Employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailabilityDTO {

   private long id ;

   @JsonDeserialize(using = DateTimeDeserializer.class)
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime ;

   @JsonDeserialize(using = DateTimeDeserializer.class)
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime ;

    @Enumerated(EnumType.STRING)
    private AvailabilityType availabilityType ;

    private EmployeeDTO employee ;


}

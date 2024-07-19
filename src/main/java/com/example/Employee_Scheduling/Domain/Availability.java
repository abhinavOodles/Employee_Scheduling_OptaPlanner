package com.example.Employee_Scheduling.Domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.optaplanner.core.api.domain.lookup.PlanningId;


import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Availability {


    @PlanningId
    @Id
    @GeneratedValue
    Long id;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime ;

    @Enumerated(EnumType.STRING)
    private AvailabilityType availabilityType;

    private Employee employee ;



    public Availability(AvailabilityType availabilityType, LocalDateTime startTime, LocalDateTime endTime, Employee employee) {
        this.availabilityType = availabilityType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee;
    }

    public LocalDate getDate() {
        return startTime.toLocalDate();
    }
}
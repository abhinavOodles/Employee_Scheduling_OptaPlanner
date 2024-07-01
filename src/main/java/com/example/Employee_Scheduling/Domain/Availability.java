package com.example.Employee_Scheduling.Domain;


import jakarta.persistence.*;
import lombok.*;
import org.optaplanner.core.api.domain.lookup.PlanningId;


import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Availability {

    @PlanningId
    @Id
    @GeneratedValue
    private Long id  ;

    private LocalDateTime startTime ;

    private LocalDateTime endTime ;

    @Enumerated(EnumType.STRING)
    private AvailabilityType availabilityType;

    @ManyToOne
    private Employee employee ;



    public Availability(AvailabilityType availabilityType, LocalDateTime startTime, LocalDateTime endTime, Employee employee) {
        this.availabilityType = availabilityType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee;
    }



}

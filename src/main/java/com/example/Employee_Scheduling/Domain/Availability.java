package com.example.Employee_Scheduling.Domain;


import jakarta.persistence.*;
import lombok.*;
import org.optaplanner.core.api.domain.lookup.PlanningId;


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

    private LocalTime startTime ;

    private LocalTime endTime ;

    @Enumerated(EnumType.STRING)
    private AvailabilityType availabilityType;

    @ManyToOne
    private Employee employee ;



    public Availability(AvailabilityType availabilityType, LocalTime startTime, LocalTime endTime, Employee employee) {
        this.availabilityType = availabilityType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee;
    }



}

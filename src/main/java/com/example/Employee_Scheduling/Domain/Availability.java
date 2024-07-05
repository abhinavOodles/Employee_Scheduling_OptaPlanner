package com.example.Employee_Scheduling.Domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Availability {

    @PlanningId
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id  ;

    @JsonFormat(pattern = "HH:mm:ss")

    private LocalDateTime startTime ;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalDateTime endTime ;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private AvailabilityType availabilityType;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Employee employee ;



    public Availability(AvailabilityType availabilityType, LocalDateTime startTime, LocalDateTime endTime, Employee employee) {
        this.availabilityType = availabilityType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee;
    }



}

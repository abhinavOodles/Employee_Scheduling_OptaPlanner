package com.example.Employee_Scheduling.Domain;


import com.example.Employee_Scheduling.DateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
    private Long id  ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime ;

    @Enumerated(EnumType.STRING)
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

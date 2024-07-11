package com.example.Employee_Scheduling.Domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;


import java.time.LocalDateTime;


@PlanningEntity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Shift {

    @Id
    @PlanningId
    @GeneratedValue
    @JsonIgnore
    private Long id;


    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location ;
    private String requiredSkill ;


    @PlanningVariable
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Employee employee ;


    public Shift(LocalDateTime endTime, String location, String requiredSkill, LocalDateTime startTime) {
        this.endTime = endTime;
        this.location = location;
        this.requiredSkill = requiredSkill;
        this.startTime = startTime;
    }



}

package com.example.Employee_Scheduling.Domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.lookup.PlanningId;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Skill {

    @PlanningId
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String skillName;

    @ManyToOne(cascade = CascadeType.ALL)
    private Employee employee ;
}
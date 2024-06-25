package com.example.Employee_Scheduling.Domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.lookup.PlanningId;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Skill {

    @PlanningId
    @Id
    @GeneratedValue
    private Long id;

    private String skillName;

    @ManyToOne
    private Employee employee ;
}
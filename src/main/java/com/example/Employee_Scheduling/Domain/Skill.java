package com.example.Employee_Scheduling.Domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import org.optaplanner.core.api.domain.lookup.PlanningId;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Skill {

   @PlanningId
    private String skillName;

    public Skill() {
    }
}


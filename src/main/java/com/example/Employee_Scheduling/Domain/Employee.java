package com.example.Employee_Scheduling.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//JSON -> Java-Script Object Notation

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee  {



    @PlanningId
    private String name;

   private Set<Skill> skills;

    @InverseRelationShadowVariable(sourceVariableName = "employee")
    private Set<Shift> shifts ;

    public Employee(String name) {
        this.name = name;
    }




//    @JsonIgnore
//    @Transient
   private AvailabilityType availabilityType ;
    private List<Availability> availability;
}

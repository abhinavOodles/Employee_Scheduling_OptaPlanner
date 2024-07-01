package com.example.Employee_Scheduling.Domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.InverseRelationShadowVariable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

//JSON -> Java-Script Object Notation

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Employee  {

    @Id
    @PlanningId
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Skill> skills = new HashSet<>();

    @InverseRelationShadowVariable(sourceVariableName = "employee")
    @OneToMany
    private Set<Shift> shifts = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<OptAvailability> availabilities;

//    @OneToOne(cascade = CascadeType.ALL)
//    private Availability availability;
}

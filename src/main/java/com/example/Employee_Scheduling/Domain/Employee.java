package com.example.Employee_Scheduling.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee  {

    @Id
    @PlanningId
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String name;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Skill> skills ;

    @InverseRelationShadowVariable(sourceVariableName = "employee")
    @OneToMany
    @JsonIgnore
    private Set<Shift> shifts = new HashSet<>();

    public Employee(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OptAvailability> availabilities;

    @OneToOne(cascade = CascadeType.ALL)
    private Availability availability;
}

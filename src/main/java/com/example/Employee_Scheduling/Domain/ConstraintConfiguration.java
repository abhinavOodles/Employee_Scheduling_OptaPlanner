package com.example.Employee_Scheduling.Domain;

import org.optaplanner.core.api.domain.constraintweight.ConstraintWeight;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

@org.optaplanner.core.api.domain.constraintweight.ConstraintConfiguration
public class ConstraintConfiguration {


    public static final String Required_Skill = "requiredSkill";
    public static final String Break_Not_Planned= "breakNotPlanned";

    @ConstraintWeight(Required_Skill)
    HardSoftScore requiredSkill =  HardSoftScore.ofHard(2) ;

    @ConstraintWeight(Break_Not_Planned)
    HardSoftScore breakNotPlanned = HardSoftScore.ofSoft(3) ;
}
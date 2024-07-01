package com.example.Employee_Scheduling.SolutuionProvider;

import com.example.Employee_Scheduling.Domain.Availability;
import com.example.Employee_Scheduling.Domain.AvailabilityType;
import com.example.Employee_Scheduling.Domain.OptAvailability;
import com.example.Employee_Scheduling.Domain.Shift;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;
import org.optaplanner.core.api.score.stream.Joiners;

import java.time.LocalDateTime;


public class constraintProvider implements ConstraintProvider {
    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                requiredSkill(constraintFactory),
                unavailableEmployee(constraintFactory),
                assignEveryShift(constraintFactory),
                undesiredEmployeeTimeSlot(constraintFactory),
                breakShouldNotBeOverlapWithShift(constraintFactory),
                unavailableEmployeeTimeSlot(constraintFactory),
                absentEmployeeTimeSlot(constraintFactory),

        };
    }

    private Constraint absentEmployeeTimeSlot(ConstraintFactory constraintFactory) {
            return constraintFactory
                    .forEach(Shift.class)
                    .filter(shift -> checkAvailabilityAtATimeSlot(shift))
                    .penalize(HardSoftScore.ONE_SOFT)
                    .asConstraint("Employee Is Absent For Given TimeSlot") ;
        }

    private boolean checkAvailabilityAtATimeSlot(Shift shift) {
        if (shift.getEmployee().getAvailabilities().equals(AvailabilityType.ABSENT)){
            return true ;
        }
        return false ;
    }


    private Constraint unavailableEmployeeTimeSlot(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Shift.class)
                .filter(shift -> checkTimings(shift))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Employee Is Unavailable") ;
    }

    private boolean checkTimings (Shift shift){
        if(shift.getEmployee().getAvailabilities().equals(AvailabilityType.UNAVAILABLE)){
            return true ;
        }
        else {
            return false ;
        }

    }

    private boolean checkOverLappingShift (Shift shift1 , Shift shift2) {
        LocalDateTime firstStartingTime = shift1.getStartTime();
        LocalDateTime firstEndingTime = shift1.getEndTime();
        LocalDateTime secondStartingTime = shift2.getStartTime();
        LocalDateTime secondEndingTime = shift2.getEndTime();


        if ((firstStartingTime.isAfter(secondStartingTime)) && (firstEndingTime.isBefore(secondEndingTime))) {
            return true;
        }
        return false ;
    }

    private Constraint requiredSkill(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Shift.class)
                .filter(shift -> shift.getEmployee().getSkills().contains(shift.getRequiredSkill()))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Missing required skill");
    }


    private Constraint unavailableEmployee(ConstraintFactory constraintFactory) {

        return constraintFactory
                .forEach(Shift.class)
                .filter(shift -> getPenaltyForEmployeeAvailability(shift, AvailabilityType.UNAVAILABLE.name()))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Unavailable Employee");
    }

    private boolean getPenaltyForEmployeeAvailability(Shift shift, String name) {
        if (shift.getEmployee().getAvailabilities().equals(name)){
            return true  ;
        }
        else{
            return false ;
        }
    }


    private Constraint assignEveryShift(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Shift.class)
                .filter(shift -> (shift.getEmployee() == null))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Assign every shift");
    }

    private Constraint undesiredEmployeeTimeSlot(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Shift.class)
                .filter(shift ->  checkAvailabilityType(shift,AvailabilityType.DESIRED))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Undesired TimeSlot for the employee") ;
    }

    private boolean checkAvailabilityType(Shift shift, AvailabilityType availabilityType) {
        if (shift.getEmployee().getAvailabilities().equals(availabilityType)){
            return true ;
        }
        else{
            return false ;
        }
    }

    Constraint noOverlappingShifts(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEachUniquePair(Shift.class, Joiners.equal(Shift::getEmployee))
                .filter((shift1, shift2)-> checkOverLappingShift(shift1,shift2))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Overlapping shift");
    }

    private Constraint breakShouldNotBeOverlapWithShift(ConstraintFactory constraintFactory) {

        return
                constraintFactory
                        .forEachUniquePair(Shift.class,Joiners.equal(Shift::getEmployee))
                        .filter((shift1,shift2) -> checkOverLappingShift(shift1 , shift2))
                        .penalize(HardSoftScore.ONE_SOFT)
                        .asConstraint("Break is not overlapped") ;

    }

    private Constraint breakNotPlanned (ConstraintFactory constraintFactory){
        return constraintFactory
                .forEach(Shift.class)
                .filter(shift -> checkAvailability(shift))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Break is not planned") ;
    }

    private boolean checkAvailability(Shift shift) {
        if (shift.getEmployee().getAvailabilities().equals(AvailabilityType.UNAVAILABLE)){
            return true ;
    }
        else if (shift.getEmployee().getAvailabilities().equals(AvailabilityType.DESIRED)){
            return true ;
        }
        else if (shift.getEmployee().getAvailabilities().equals(AvailabilityType.UNDESIRED)){
            return true ;
        }
        else if (shift.getEmployee().getAvailabilities().equals(AvailabilityType.ABSENT)){
            return true ;
        }
        else {
            return false;
        }
    }
}

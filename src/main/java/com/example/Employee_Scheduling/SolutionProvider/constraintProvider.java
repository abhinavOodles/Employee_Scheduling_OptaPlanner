package com.example.Employee_Scheduling.SolutionProvider;

import com.example.Employee_Scheduling.Domain.Availability;
import com.example.Employee_Scheduling.Domain.AvailabilityType;
import com.example.Employee_Scheduling.Domain.Employee;
import com.example.Employee_Scheduling.Domain.Shift;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.util.Locale.filter;


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
                breakNotPlanned(constraintFactory),
                oneShiftPerDay(constraintFactory),
               // assignEveryEmployeeToShift(constraintFactory),
                uniqueShiftAssignmentConstraint(constraintFactory),
                noShiftAllocationPenalty(constraintFactory)

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
        if (shift.getEmployee().getAvailability().equals(AvailabilityType.ABSENT)){
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
        if(shift.getEmployee().getAvailability().equals(AvailabilityType.UNAVAILABLE)){
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


            // Check if shift1 starts before shift2 ends and shift1 ends after shift2 starts
            return firstStartingTime.isBefore(secondEndingTime) && firstEndingTime.isAfter(secondStartingTime);
        }

    //before assigning any shift check it for required skill
    private Constraint requiredSkill(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Shift.class)
                .filter(shift -> shift.getEmployee().getSkills().contains(shift.getRequiredSkill()))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Missing required skill");
    }

   //check it for  availability of employee for the shifts
    private Constraint unavailableEmployee(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Shift.class)
                .join(Availability.class,
                        Joiners.equal((Shift shift) -> shift.getStartTime().toLocalDate(), Availability::getDate),
                        Joiners.equal(Shift::getEmployee, Availability::getEmployee))
                .filter((shift, availability) -> getPenaltyForEmployeeAvailability(shift, AvailabilityType.UNAVAILABLE.name()))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Unavailable Employee");
    }

    private boolean getPenaltyForEmployeeAvailability(Shift shift, String name) {


        if (shift.getEmployee().getAvailability().equals(name)){
            return true  ;
        }
        else{
            return false ;
        }
    }

   // assigning every shift  to  according to their skills
    private Constraint assignEveryShift(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Shift.class)
                .filter(shift -> ((shift.getEmployee() == null) && !(shift.getRequiredSkill().equals(shift.getEmployee().getSkills()))))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Assign every shift");
    }

    private Constraint undesiredEmployeeTimeSlot(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Shift.class)
                .filter(shift ->  checkAvailabilityType(shift,AvailabilityType.DESIRED))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Undesired TimeSlot for the employee") ;
    }

    private boolean checkAvailabilityType(Shift shift, AvailabilityType availabilityType) {
        if (shift.getEmployee().getAvailability().equals(availabilityType)){
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

        return constraintFactory
                        .forEachUniquePair(Shift.class,Joiners.equal(Shift::getEmployee))
                        .filter((shift1,shift2) -> checkOverLappingShift(shift1 , shift2))
                        .penalize(HardSoftScore.ONE_SOFT)
                        .asConstraint("Break is not overlapped") ;

    }

    private Constraint breakNotPlanned (ConstraintFactory constraintFactory){
        return constraintFactory
                .forEachUniquePair(Shift.class)
                .filter((shift1 , shift2) -> checkBreakTimingWithShifts(shift1 , shift2 , 15))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Break is not planned") ;
    }

    private boolean checkAvailability(Shift shift) {
        if (shift.getEmployee().getAvailability().equals(AvailabilityType.UNAVAILABLE) || (shift.getEmployee().getAvailability().equals(AvailabilityType.ABSENT))){
            return true ;
    }
    else {
            return false;
        }
    }

    private boolean checkBreakTimingWithShifts(Shift shift1 , Shift shift2 , int breakTiming){

        LocalDateTime startTimeOfShift1 = shift1.getStartTime() ;
        LocalDateTime endTimeOfShift1 = shift1.getEndTime() ;
        LocalDateTime startTimeOfShift2 = shift2.getStartTime() ;
        LocalDateTime endTimeOfShift2  = shift2.getEndTime() ;

        long totalDurationOfShift1 = Duration.between(startTimeOfShift1 , endTimeOfShift1).toMinutes() ;
        long totalDurationOfShift2 = Duration.between(startTimeOfShift2,endTimeOfShift2).toMinutes() ;
        long differenceBetweenStartingAndEndingOfShifts = Duration.between(endTimeOfShift1 , startTimeOfShift2).toMinutes();

//        long totalHoursInBetweenInComplianceOfShift1WithBreak  = totalDurationOfShift1 + breakTiming ;
//        long differenceOfTimingInTwoShift  = totalDurationOfShift2 - totalDurationOfShift1 ;

        if (differenceBetweenStartingAndEndingOfShifts < breakTiming){
            return true ;
        }
        else{
            return false ;
        }

    }

    Constraint oneShiftPerDay(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEachUniquePair(Shift.class, Joiners.equal(Shift::getEmployee),
                        Joiners.equal(shift -> shift.getStartTime().toLocalDate()))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Max one shift per day");
    }


    private Constraint uniqueShiftAssignmentConstraint(ConstraintFactory constraintFactory) {
        // Penalize when multiple employees are assigned to the same shift
        return constraintFactory
                .forEachUniquePair(Employee.class)
                .filter(((employee, employee2) -> checkShifts(employee , employee2)))
                .penalize( HardSoftScore.ONE_SOFT).asConstraint("Shift can only be assigned to one employee");
    }

    private boolean checkShifts(Employee e1, Employee e2) {
        return e1.getShifts().equals(e2.getShifts()) && !e1.equals(e2) ;
    }

    private Constraint noShiftAllocationPenalty(ConstraintFactory constraintFactory) {
        //if any shift is left without assigning to any employee then this constraint is applied
        return constraintFactory
                .forEach(Shift.class)
                .groupBy(Shift::getEmployee,
                        ConstraintCollectors.count())
                .filter((employee, shiftCount) -> shiftCount == 0) // Filter employees with no shifts
                .penalize( HardSoftScore.ONE_SOFT)
                .asConstraint("No shift allocation penalty");
    }
}

package com.example.Employee_Scheduling.SolutionProvider;

import com.example.Employee_Scheduling.Domain.*;

import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class constraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{

                shiftShouldHaveEmployee(constraintFactory),
                requiredSkill(constraintFactory),
                unavailableEmployee(constraintFactory),
             assignOneShiftToOneEmployee(constraintFactory),
               undesiredEmployeeTimeSlot(constraintFactory),
               breakShouldNotBeOverlapWithShift(constraintFactory),
                unavailableEmployeeTimeSlot(constraintFactory),
              absentEmployeeTimeSlot(constraintFactory),
                breakNotPlanned(constraintFactory),
               oneShiftPerDay(constraintFactory),
            checkEveryEmployeeHaveShift(constraintFactory),
                uniqueShiftAssignmentConstraint(constraintFactory),
               noShiftAllocationPenalty(constraintFactory),
              employeeNotDoubleBooked(constraintFactory)

        };
    }

    private Constraint shiftShouldHaveEmployee(ConstraintFactory constraintFactory) {
        return constraintFactory.forEachIncludingNullVars(Shift.class)
                .filter(shift -> shift.getEmployee()==null)
                .penalize(HardSoftScore.ofHard(1)).
                asConstraint("Deployments should have employee");
    }
    private Constraint requiredSkill(ConstraintFactory constraintFactory) {
        return constraintFactory.forEach(Shift.class)
                .filter(shift -> !shift.getEmployee().getSkills().stream().map(Skill::getSkillName).collect(Collectors.toSet()).contains(shift.getRequiredSkill()))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Missing required skill");
    }

    private Constraint absentEmployeeTimeSlot(ConstraintFactory constraintFactory) {
            return constraintFactory
                    .forEach(Shift.class)
                    .filter(shift -> checkAvailabilityAtATimeSlot(shift))
                    .penalize(HardSoftScore.ONE_SOFT)
                    .asConstraint("Employee Is Absent For Given TimeSlot") ;
        }

   private boolean checkAvailabilityAtATimeSlot(Shift shift) {

       List<Availability> availabilities = shift.getEmployee().getAvailability();

       if (availabilities == null) {
           return true;
       }

       Availability availability = null;


       for (Availability availability1 : availabilities) {
           if (shift.getEmployee() == availability1.getEmployee()) {
               availability = availability1;
               break;
           } else {
               continue;
           }
       }

       if (availability.getAvailabilityType().equals(AvailabilityType.UNAVAILABLE)) {
           return true;
       } else {
           return false;
       }
   }

//        private boolean checkAvailabilityAtATimeSlot(Shift shift) {
//            List<Availability> availabilities = shift.getEmployee().getAvailability();
//
//            if (availabilities == null) {
//                return true;
//            }
//
//            Availability availability = null;
//
//            for (Availability availability1 : availabilities) {
//                if (shift.getEmployee().equals(availability1.getEmployee())) {
//                    availability = availability1;
//                    break;
//                }
//            }
//
//            if (availability == null) {
//                return true;
//            }
//
//            return availability.getAvailabilityType().equals(AvailabilityType.UNAVAILABLE);
//        }




    private Constraint unavailableEmployeeTimeSlot(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Shift.class)
                .filter(shift -> {
                    Employee employee = shift.getEmployee();
                    return employee != null && employee.getAvailability() != null &&
                            employee.getAvailability().stream()
                                    .anyMatch(availability -> availability.getAvailabilityType() == AvailabilityType.UNAVAILABLE);
                })
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Employee Is Unavailable");
    }



//    private boolean checkTimings (Shift shift){
//        if(shift.getEmployee().getAvailability().equals(AvailabilityType.UNAVAILABLE)){
//            return true ;
//        }
//        else {
//            return false ;
//        }
//
//
//    }

    private boolean checkOverLappingShift (Shift shift1 , Shift shift2) {

        LocalDateTime firstStartingTime = shift1.getStartTime();
        LocalDateTime firstEndingTime = shift1.getEndTime();
        LocalDateTime secondStartingTime = shift2.getStartTime();
        LocalDateTime secondEndingTime = shift2.getEndTime();

            // Check if shift1 starts before shift2 ends and shift1 ends after shift2 starts
        return firstStartingTime.isBefore(secondEndingTime) && firstEndingTime.isAfter(secondStartingTime);

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
   private Constraint assignOneShiftToOneEmployee(ConstraintFactory constraintFactory) {
       return constraintFactory
               .forEach(Shift.class) // Iterate over each shift
               .filter(shift -> shift.getEmployee() == null) // Filter shifts that are not yet assigned
               .penalize(HardSoftScore.ONE_HARD, (shift) -> {
                   // The penalty is applied if the shift's assigned employee does not meet the required skill
                   Employee employee = shift.getEmployee();
                   if (employee != null && !employee.getSkills().contains(shift.getRequiredSkill())) {
                       return 1; // Penalty of 1 for each violation
                   }
                   return 0;
               })
               .asConstraint("Assign every shift to an employee with required skills");
   }



    private Constraint undesiredEmployeeTimeSlot(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(Shift.class)
                .filter(shift ->  checkAvailabilityType(shift,AvailabilityType.DESIRED))
                .penalize(HardSoftScore.ONE_SOFT)
                .asConstraint("Undesired TimeSlot for the employee") ;
    }

    private boolean checkAvailabilityType(Shift shift, AvailabilityType availabilityType) {
        if (shift.getEmployee().getAvailability() != null && shift.getEmployee().getAvailability().equals(availabilityType)){
            return false ;
        }
        else{
            return true ;
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
        return constraintFactory.forEach(Shift.class)
                .join(Shift.class,
                        Joiners.equal(Shift::getEmployee),
                        Joiners.lessThan(Shift::getId))
                .filter((shift1, shift2) -> shift1.getEmployee() != null && shift1.getEmployee().equals(shift2.getEmployee()))
                .penalize( HardSoftScore.ONE_SOFT)
                .asConstraint("Unique shift assignment");
    }





//    private boolean checkShifts(Shift s1, Shift s2) {
//       Employee employee1 = s1.getEmployee() ;
//       Employee employee2 = s2.getEmployee() ;
//
////       Set<Shift> shifts = new HashSet<>(employee.getShifts()) ;
////       Set<Shift> shifts1 = new HashSet<>(employee1.getShifts()) ;
////
////       shifts.retainAll(shifts1) ;
////
////
////         return !shifts.isEmpty();
//
//        return (employee1 != null) && (employee2 != null) && employee1.equals(employee2);
//    }



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

  private Constraint checkEveryEmployeeHaveShift(ConstraintFactory constraintFactory){
        return constraintFactory
                .forEach(Employee.class)
                .filter(employee -> employee.getShifts() == null )
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Employee without shift") ;
    }

    private Constraint employeeNotDoubleBooked(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEachUniquePair(Shift.class)
                .filter((shift1, shift2) ->
                        shift1.getEmployee().equals(shift2.getEmployee())
                                && !shift1.equals(shift2)) // Ensure the same employee is not assigned to two different shifts
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Ensure no employee is double-booked");
    }
}

package com.example.Employee_Scheduling.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeSchedulingDTO {

    private List<EmployeeDTO> employeeDTOList;
    private List<AvailabilityDTO> availabilityDTOS;
    private List<ShiftDTO> shiftDTOS;
    private List<SkillDTO> skillDTOList;
}

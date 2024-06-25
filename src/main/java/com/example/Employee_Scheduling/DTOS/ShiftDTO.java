package com.example.Employee_Scheduling.DTOS;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShiftDTO {
    private LocalDateTime start;
    private LocalDateTime end;

    private String location;
    private String requiredSkill;
}

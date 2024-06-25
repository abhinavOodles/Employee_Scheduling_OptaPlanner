package com.example.Employee_Scheduling.Repository;

import com.example.Employee_Scheduling.Domain.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift,Long> {

}

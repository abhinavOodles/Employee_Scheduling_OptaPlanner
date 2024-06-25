package com.example.Employee_Scheduling.Repository;

import com.example.Employee_Scheduling.Domain.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability,Long> {
}

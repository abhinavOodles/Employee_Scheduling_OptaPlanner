package com.example.Employee_Scheduling.Repository;

import com.example.Employee_Scheduling.Domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}

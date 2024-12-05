package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.backend.models.Job;
import vn.edu.iuh.fit.backend.models.JobSkill;
import vn.edu.iuh.fit.backend.models.Skill;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill, Skill> {

    List<JobSkill> findAllByJob(Job job);
}
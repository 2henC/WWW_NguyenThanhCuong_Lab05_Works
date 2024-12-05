package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.backend.models.Candidate;
import vn.edu.iuh.fit.backend.models.CandidateSkill;
import vn.edu.iuh.fit.backend.models.Skill;

import java.util.List;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Skill> {

    List<CandidateSkill> findByCandidate(Candidate candidate);

}
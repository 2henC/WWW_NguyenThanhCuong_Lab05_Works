package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.backend.models.Candidate;
import vn.edu.iuh.fit.backend.models.Experience;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    Page<Experience> findByCandidate(Pageable pageable, Candidate candidate);

    List<Experience> findAllByCandidate(Candidate candidate);

    Experience findByExperienceId(long id);
}
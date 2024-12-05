package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.backend.models.Address;
import vn.edu.iuh.fit.backend.models.Candidate;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {


    boolean existsCandidateByEmailOrPhone(String email, String phone);

    Candidate findCandidateByEmailOrPhone(String email, String phone);

    Candidate findByCandidateId(long candidateId);

    @Query("SELECT DISTINCT c FROM Candidate c JOIN c.candidateSkills cs WHERE cs.skill.skillId IN :skillIds")
    Page<Candidate> findCandidatesBySkills(@Param("skillIds") List<Long> skillIds, Pageable pageable);



}
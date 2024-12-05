package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.edu.iuh.fit.backend.models.Company;
import vn.edu.iuh.fit.backend.models.Job;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findAllByCompany(Pageable pageable, Company company);

    @Query("SELECT j FROM Job j JOIN j.jobSkills js WHERE js.skill.skillId IN :skillIds GROUP BY j.jobId")
    Page<Job> findJobsBySkills(List<Long> skillIds, Pageable pageable);

    Job findByJobId(Long jobId);
}
package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.CandidateSkill;
import vn.edu.iuh.fit.backend.models.Company;
import vn.edu.iuh.fit.backend.models.Job;
import vn.edu.iuh.fit.backend.repositories.JobRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Page<Job> findAllJobPaging(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return jobRepository.findAll(pageable);
    }

    public Page<Job> findAllJobPagingByCompany(int pageNo, int pageSize, String sortBy, String sortDirection, Company company) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return jobRepository.findAllByCompany(pageable, company);
    }

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    public Page<Job> findSuitableJobs(List<CandidateSkill> candidateSkills, int page, int size) {
        List<Long> skillIds = candidateSkills.stream()
                .map(candidateSkill -> candidateSkill.getSkill().getSkillId())
                .collect(Collectors.toList());
        return jobRepository.findJobsBySkills(skillIds, PageRequest.of(page, size));
    }

    public Job findJobById(Long jobId) {
        return jobRepository.findByJobId(jobId);
    }
}

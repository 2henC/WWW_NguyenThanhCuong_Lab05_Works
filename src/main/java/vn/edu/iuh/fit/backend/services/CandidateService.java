package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.Candidate;
import vn.edu.iuh.fit.backend.models.Job;
import vn.edu.iuh.fit.backend.repositories.CandidateRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public Page<Candidate> findAll(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return candidateRepository.findAll(pageable);
    }

    public Candidate findCandidateByEmailOrPhone(String email, String phone) {
        return candidateRepository.findCandidateByEmailOrPhone(email, phone);
    }

    public Candidate findByCandidateId(long candidateId) {
        return candidateRepository.findByCandidateId(candidateId);
    }

    public Page<Candidate> findSuitableCandidates(Job job, int page, int size) {
        List<Long> skillIds = job.getJobSkills().stream()
                .map(jobSkill -> jobSkill.getSkill().getSkillId())
                .collect(Collectors.toList());
        Pageable pageable = PageRequest.of(page, size);
        return candidateRepository.findCandidatesBySkills(skillIds, pageable);
    }
}

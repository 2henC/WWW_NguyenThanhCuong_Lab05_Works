package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.Candidate;
import vn.edu.iuh.fit.backend.models.CandidateSkill;
import vn.edu.iuh.fit.backend.repositories.CandidateSkillRepository;

import java.util.List;

@Service
public class CandidateSkillService {

    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    public List<CandidateSkill> findAllByCandidate(Candidate candidate) {
        return candidateSkillRepository.findByCandidate(candidate);
    }

    public void save(CandidateSkill candidateSkill) {
        candidateSkillRepository.save(candidateSkill);
    }

}

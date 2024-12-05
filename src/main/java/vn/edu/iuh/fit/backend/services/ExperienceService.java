package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.Candidate;
import vn.edu.iuh.fit.backend.models.Experience;
import vn.edu.iuh.fit.backend.repositories.ExperienceRepository;

import java.util.List;

@Service
public class ExperienceService {

    @Autowired
    private ExperienceRepository experienceRepository;

    public Page<Experience> findAllExperienceByCandidatePaging(int pageNo, int pageSize, String sortBy, String sortDirection, Candidate candidate) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return experienceRepository.findByCandidate(pageable, candidate);
    }

    public List<Experience> findAllExperienceByCandidate(Candidate candidate) {
        return experienceRepository.findAllByCandidate(candidate);
    }

    public Experience saveExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    public  void save (Experience experience){
        experienceRepository.save(experience);
    }
}

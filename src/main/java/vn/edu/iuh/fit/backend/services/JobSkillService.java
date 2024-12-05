package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.JobSkill;
import vn.edu.iuh.fit.backend.repositories.JobSkillRepository;

import java.util.List;

@Service
public class JobSkillService {

    @Autowired
    private JobSkillRepository jobSkillRepository;

    public List<JobSkill> findAll() {
        return  jobSkillRepository.findAll();
    }
}

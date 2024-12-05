package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.dtos.SkillDTO;
import vn.edu.iuh.fit.backend.models.Skill;
import vn.edu.iuh.fit.backend.repositories.SkillRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    public Skill findBySkillId(long skillId) {
        return skillRepository.findBySkillId(skillId);
    }

    public List<Skill> findAllSkills() {
        return skillRepository.findAll();
    }

    public List<SkillDTO> findAllSkillDTOs() {
        return skillRepository.findAll().stream()
                .map(skill -> new SkillDTO(skill.getSkillName(), skill.getSkillType().name()))
                .collect(Collectors.toList());
    }
}

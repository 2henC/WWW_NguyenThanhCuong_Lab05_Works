package vn.edu.iuh.fit.frontend.controllers;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.dtos.SkillDTO;
import vn.edu.iuh.fit.backend.enums.SkillLevel;
import vn.edu.iuh.fit.backend.models.Candidate;
import vn.edu.iuh.fit.backend.models.CandidateSkill;
import vn.edu.iuh.fit.backend.models.Skill;
import vn.edu.iuh.fit.backend.services.CandidateSkillService;
import vn.edu.iuh.fit.backend.services.SkillService;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/api")
public class SkillController {

    @Autowired
    private SkillService skillService;
    @Autowired
    private CandidateSkillService candidateSkillService;

    @RequestMapping(value = "candidate/manageSkills", method = RequestMethod.GET)
    public ModelAndView manageCandidateSkills(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("skill/candidate-skill");
        Object loggedInUser = session.getAttribute("user");
        modelAndView.addObject("user", loggedInUser);
        if (loggedInUser == null) {
            return new ModelAndView("redirect:/login");
        }
        List<CandidateSkill> candidateSkills = candidateSkillService.findAllByCandidate((Candidate) loggedInUser);
        modelAndView.addObject("candidateSkills", candidateSkills);
        List<Skill> skills = new ArrayList<>();
        candidateSkills.forEach(candidateSkill -> {
            skills.add(candidateSkill.getSkill());
        });
        modelAndView.addObject("skills", skills);
        return modelAndView;
    }

    @RequestMapping(value = "candidate/addSkill", method = RequestMethod.GET)
    public ModelAndView showFormAddSkill(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("skill/add-candidate-skill");
        modelAndView.addObject("candidateSkill", new CandidateSkill());
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null) {
            return new ModelAndView("redirect:/login");
        }
        modelAndView.addObject("user", loggedInUser);
        List<Skill> skills = skillService.findAllSkills();
        modelAndView.addObject("skills", skills);
        modelAndView.addObject("skillLevels", SkillLevel.values());
        return modelAndView;
    }

    @RequestMapping(value = "candidate/addSkill", method = RequestMethod.POST)
    public String addCandidateSkill(HttpSession session, @ModelAttribute CandidateSkill candidateSkill) {
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null) return ("redirect:/login");
        candidateSkill.setCandidate((Candidate) loggedInUser);
        candidateSkillService.save(candidateSkill);
        return "redirect:/candidate/manageSkills";
    }
}

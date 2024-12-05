package vn.edu.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.enums.SkillLevel;
import vn.edu.iuh.fit.backend.ids.JobSkillId;
import vn.edu.iuh.fit.backend.models.*;
import vn.edu.iuh.fit.backend.services.CandidateSkillService;
import vn.edu.iuh.fit.backend.services.JobService;
import vn.edu.iuh.fit.backend.services.JobSkillService;
import vn.edu.iuh.fit.backend.services.SkillService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
//@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private CandidateSkillService candidateSkillService;

    @Autowired
    private JobSkillService jobSkillService;

    @RequestMapping(value = {"/job/listJobPage", "", "/"}, method = RequestMethod.GET)
    public ModelAndView showJobPaging(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, HttpSession session) {
        ModelAndView mav = new ModelAndView("job/jobs-paging");
        Object loggedInUser = session.getAttribute("user");
        mav.addObject("user", loggedInUser);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage = jobService.findAllJobPaging(currentPage - 1, pageSize, "jobId", "asc");
        mav.addObject("fullJobs", jobPage);
        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            mav.addObject("pageNumbers", pageNumbers);
        }
        return mav;
    }

    @RequestMapping(value = "/job/manageJob", method = RequestMethod.GET)
    public ModelAndView mangeJob(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, HttpSession session) {
        ModelAndView mav = new ModelAndView("job/jobs-paging");
        Object loggedInUser = session.getAttribute("user");
        mav.addObject("user", loggedInUser);
        if (loggedInUser == null) return new ModelAndView("redirect:/login");
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage = jobService.findAllJobPagingByCompany(currentPage - 1, pageSize, "jobId", "asc", (Company) loggedInUser);
        mav.addObject("managedJobs", jobPage);
        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            mav.addObject("pageNumbers", pageNumbers);
        }
        return mav;
    }

    @RequestMapping(value = "/job/addJob", method = RequestMethod.GET)
    public ModelAndView showAddJobForm(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("job/add-job");
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null) return new ModelAndView("redirect:/login");
        modelAndView.addObject("job", new Job());
        modelAndView.addObject("user", loggedInUser);
        List<Skill> skills = skillService.findAllSkills();
        modelAndView.addObject("skills", skills);
        modelAndView.addObject("skillLevels", SkillLevel.values());
        return modelAndView;
    }

    @RequestMapping(value = "/job/addJob", method = RequestMethod.POST)
    public String addJob(@ModelAttribute Job job, HttpSession session) {
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null)  return "redirect:/login";
        job.setCompany((Company) loggedInUser);
        for (JobSkill jobSkill : job.getJobSkills()) {
            Skill skill = skillService.findBySkillId(jobSkill.getSkill().getSkillId());
            jobSkill.setSkill(skill);
            jobSkill.setJob(job);
        }
        jobService.saveJob(job);
        return "redirect:/job/manageJob";
    }

    @RequestMapping(value = "/job/suitableJob", method = RequestMethod.GET)
    public ModelAndView showSuitableJob(HttpSession session, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        ModelAndView modelAndView = new ModelAndView("job/jobs-paging");
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null) return new ModelAndView("redirect:/login");
        modelAndView.addObject("user", loggedInUser);

        List<CandidateSkill> candidateSkills = candidateSkillService.findAllByCandidate((Candidate) loggedInUser);
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Job> jobPage = jobService.findSuitableJobs(candidateSkills, currentPage - 1, pageSize);
        int totalPages = jobPage.getTotalPages();
        modelAndView.addObject("fullJobs", jobPage);
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("pageSize", pageSize);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        return modelAndView;
    }
}

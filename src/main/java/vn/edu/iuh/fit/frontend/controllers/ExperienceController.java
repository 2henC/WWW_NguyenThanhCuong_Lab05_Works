package vn.edu.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.models.Candidate;
import vn.edu.iuh.fit.backend.models.Company;
import vn.edu.iuh.fit.backend.models.Experience;
import vn.edu.iuh.fit.backend.services.CompanyService;
import vn.edu.iuh.fit.backend.services.ExperienceService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ExperienceController {

    @Autowired
    private  ExperienceService experienceService;
    @Autowired
    private CompanyService companyService;


    @RequestMapping(value = "candidate/manageExperience", method = RequestMethod.GET)
    public ModelAndView manageCandidateExperience(HttpSession session, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        ModelAndView modelAndView = new ModelAndView("experience/candidate-experience");
        Object loggedInUser = session.getAttribute("user");
        modelAndView.addObject("user", loggedInUser);
        if (loggedInUser == null) return new ModelAndView("redirect:/login");

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Experience> experiencePage = experienceService.findAllExperienceByCandidatePaging(currentPage - 1, pageSize, "experienceId", "asc", (Candidate) loggedInUser);
        modelAndView.addObject("experiencePage", experiencePage);
        int totalPages = experiencePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        return modelAndView;
    }

    @RequestMapping(value = "candidate/addExperience", method = RequestMethod.GET)
    public ModelAndView showFormAddExperience(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("experience/add-candidate-experience");
        modelAndView.addObject("experience", new Experience());
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null) return new ModelAndView("redirect:/login");
        modelAndView.addObject("user", loggedInUser);
        List<Company> companies = companyService.findAllCompany();
        modelAndView.addObject("companies", companies);
        return modelAndView;
    }

    @RequestMapping(value = "candidate/addExperience", method = RequestMethod.POST)
    public String addCandidateExperience(HttpSession session, @ModelAttribute Experience experience, @RequestParam("companyId") Long companyId) {
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null) return "redirect:/login";
        experience.setCandidate((Candidate) loggedInUser);
        System.out.println("Company ID: " + companyId);
        Company company = companyService.findCompanyById(companyId);
        if (company != null) {
            experience.setCompany(company);
        }
        experienceService.save(experience);
        return "redirect:/candidate/manageExperience";
    }
}
package vn.edu.iuh.fit.frontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.enums.SkillLevel;
import vn.edu.iuh.fit.backend.models.*;
import vn.edu.iuh.fit.backend.repositories.AddressRepository;
import vn.edu.iuh.fit.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.backend.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/candidate")
public class CandidateController {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CandidateSkillService candidateSkillService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private ExperienceService experienceService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobService jobService;


    @RequestMapping(value = "/listCandidate", method = RequestMethod.GET)
    public ModelAndView showCandidateList() {
        ModelAndView mav = new ModelAndView("candidate/candidates");
        mav.addObject("candidates", candidateRepository.findAll());
        return mav;
    }

    @RequestMapping(value = {"/listCandidatePage", "/", ""}, method = RequestMethod.GET)
    public ModelAndView showCandidateListPage(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        ModelAndView mav = new ModelAndView("candidate/candidates-paging");
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Candidate> candidatePage = candidateService.findAll(currentPage, pageSize, "candidateId", "asc");
        mav.addObject("candidatePage", candidatePage);

        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            mav.addObject("pageNumbers", pageNumbers);
        }
        return mav;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showAddCandidateForm() {
        ModelAndView modelAndView = new ModelAndView("candidate/add-candidate"); // Tên của view
        modelAndView.addObject("candidate", new Candidate()); // Thêm đối tượng rỗng để form liên kết
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView addCandidate(@ModelAttribute Candidate candidate) {
        boolean existingAddress = addressService.checkExistingAddress(candidate.getAddress());
        if (existingAddress) {
            candidate.getAddress().setCountry(CountryCode.VN);
            candidate.setAddress(candidate.getAddress());
        } else {
            candidate.getAddress().setCountry(CountryCode.VN);
            addressRepository.save(candidate.getAddress());
        }
        candidateRepository.save(candidate);
        return new ModelAndView("redirect:/candidate/listCandidatePage"); // Chuyển hướng đến danh sách ứng viên
    }

    @RequestMapping(value = "/manageProfile", method = RequestMethod.GET)
    public ModelAndView manageCandidateProfile(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("candidate/candidate-profile");
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null) {
            return new ModelAndView("redirect:/login");
        }
        modelAndView.addObject("user", (Candidate) loggedInUser);
        List<CandidateSkill> candidateSkills = candidateSkillService.findAllByCandidate((Candidate) loggedInUser);
        modelAndView.addObject("candidateSkills", candidateSkills);
        List<Experience> experiences = experienceService.findAllExperienceByCandidate((Candidate) loggedInUser);
        modelAndView.addObject("experiences", experiences);
        return modelAndView;
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public ModelAndView showFormEditCandidate(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("candidate/candidate-update");
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null) {
            return new ModelAndView("redirect:/login");
        }
        modelAndView.addObject("user", loggedInUser);
        Candidate candidate = candidateService.findByCandidateId(((Candidate) loggedInUser).getCandidateId());
        modelAndView.addObject("candidate", candidate);
        return modelAndView;
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public String editCandidateProfile(HttpSession session, @ModelAttribute Candidate candidate) {
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null) return "redirect:/login";

        Candidate updateCandidate = candidateService.findByCandidateId(((Candidate) loggedInUser).getCandidateId());
        Address address = candidate.getAddress();
        if (address != null) {
            address.setCountry(CountryCode.VN);
            addressRepository.save(address);
        }
        updateCandidate.setAddress(address);
        updateCandidate.setPhone(candidate.getPhone());
        updateCandidate.setFullName(candidate.getFullName());
        updateCandidate.setDob(candidate.getDob());
        updateCandidate.setEmail(candidate.getEmail());
        candidateRepository.save(updateCandidate);

        session.setAttribute("user", updateCandidate);

        return "redirect:/candidate/manageProfile";
    }

//    @RequestMapping(value = "/manageSkills", method = RequestMethod.GET)
//    public ModelAndView manageCandidateSkills(HttpSession session) {
//        ModelAndView modelAndView = new ModelAndView("skill/candidate-skill");
//        Object loggedInUser = session.getAttribute("user");
//        modelAndView.addObject("user", loggedInUser);
//        if (loggedInUser == null) {
//            return new ModelAndView("redirect:/login");
//        }
//        List<CandidateSkill> candidateSkills = candidateSkillService.findAllByCandidate((Candidate) loggedInUser);
//        modelAndView.addObject("candidateSkills", candidateSkills);
//        List<Skill> skills = new ArrayList<>();
//        candidateSkills.forEach(candidateSkill -> {
//            skills.add(candidateSkill.getSkill());
//        });
//        modelAndView.addObject("skills", skills);
//        return modelAndView;
//    }

//    @RequestMapping(value = "/addSkill", method = RequestMethod.GET)
//    public ModelAndView showFormAddSkill(HttpSession session) {
//        ModelAndView modelAndView = new ModelAndView("skill/add-candidate-skill");
//        modelAndView.addObject("candidateSkill", new CandidateSkill());
////        modelAndView.addObject("candidateSkills", new ArrayList<CandidateSkill>());
//        Object loggedInUser = session.getAttribute("user");
//        if (loggedInUser == null) {
//            return new ModelAndView("redirect:/login");
//        }
//        modelAndView.addObject("user", loggedInUser);
//        List<Skill> skills = skillService.findAllSkills();
//        modelAndView.addObject("skills", skills);
//        modelAndView.addObject("skillLevels", SkillLevel.values());
//        return modelAndView;
//    }

//    @RequestMapping(value = "/addSkill", method = RequestMethod.POST)
//    public String addCandidateSkill(HttpSession session, @ModelAttribute CandidateSkill candidateSkill) {
//        Object loggedInUser = session.getAttribute("user");
//        if (loggedInUser == null) return ("redirect:/login");
//        candidateSkill.setCandidate((Candidate) loggedInUser);
//        candidateSkillService.save(candidateSkill);
//        return "redirect:/candidate/manageSkills";
//    }

//    @RequestMapping(value = "/manageExperience", method = RequestMethod.GET)
//    public ModelAndView manageCandidateExperience(HttpSession session, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
//        ModelAndView modelAndView = new ModelAndView("experience/candidate-experience");
//        Object loggedInUser = session.getAttribute("user");
//        modelAndView.addObject("user", loggedInUser);
//        if (loggedInUser == null) return new ModelAndView("redirect:/login");
//
//        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(10);
//        Page<Experience> experiencePage = experienceService.findAllExperienceByCandidatePaging(currentPage - 1, pageSize, "experienceId", "asc", (Candidate) loggedInUser);
//        modelAndView.addObject("experiencePage", experiencePage);
//        int totalPages = experiencePage.getTotalPages();
//        if (totalPages > 0) {
//            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
//            modelAndView.addObject("pageNumbers", pageNumbers);
//        }
//        return modelAndView;
//    }

//    @RequestMapping(value = "/addExperience", method = RequestMethod.GET)
//    public ModelAndView showFormAddExperience(HttpSession session) {
//        ModelAndView modelAndView = new ModelAndView("experience/add-candidate-experience");
//        modelAndView.addObject("experience", new Experience());
//        Object loggedInUser = session.getAttribute("user");
//        if (loggedInUser == null) return new ModelAndView("redirect:/login");
//        modelAndView.addObject("user", loggedInUser);
//        List<Company> companies = companyService.findAllCompany();
//        modelAndView.addObject("companies", companies);
//        return modelAndView;
//    }

//    @RequestMapping(value = "/addExperience", method = RequestMethod.POST)
//    public String addCandidateExperience(HttpSession session, @ModelAttribute Experience experience, @RequestParam("companyId") Long companyId) {
//        Object loggedInUser = session.getAttribute("user");
//        if (loggedInUser == null) return "redirect:/login";
//        experience.setCandidate((Candidate) loggedInUser);
//        System.out.println("Company ID: " + companyId);
//        Company company = companyService.findCompanyById(companyId);
//        if (company != null) {
//            experience.setCompany(company);
//        }
//        experienceService.save(experience);
//        return "redirect:/candidate/manageExperience";
//    }

    @RequestMapping(value = "/suitableCandidate", method = RequestMethod.GET)
    public ModelAndView findSuitableCandidate(HttpSession session, @RequestParam("jobId") Long jobId, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        ModelAndView modelAndView = new ModelAndView("candidate/suitable-candidate");
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null) return new ModelAndView("redirect:/login");
        modelAndView.addObject("user", loggedInUser);

        Job job = jobService.findJobById(jobId);
        modelAndView.addObject("job", job);

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Candidate> suitableCandidates = candidateService.findSuitableCandidates(job, currentPage - 1, pageSize);
        modelAndView.addObject("suitableCandidates", suitableCandidates);
        int totalPages = suitableCandidates.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }

        return modelAndView;
    }
}
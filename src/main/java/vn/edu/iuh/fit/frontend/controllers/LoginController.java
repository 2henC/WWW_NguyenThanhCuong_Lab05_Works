package vn.edu.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.edu.iuh.fit.backend.models.Candidate;
import vn.edu.iuh.fit.backend.models.Company;
import vn.edu.iuh.fit.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.backend.services.CandidateService;
import vn.edu.iuh.fit.backend.services.CompanyService;

@Controller
public class LoginController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CandidateService candidateService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Xóa tất cả dữ liệu trong session
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("loginType") String loginType, Model model, HttpSession session) {
        if (loginType.equalsIgnoreCase("candidate")){
            Candidate user = candidateService.findCandidateByEmailOrPhone(username, username);
            if (user != null) {
                session.setAttribute("user", user);
                session.setAttribute("loginType", "candidate");
                return "redirect:/job/listJobPage";
            } else {
                model.addAttribute("error", "No account found with this email or phone number");
                return "index";
            }
        }
        else if (loginType.equalsIgnoreCase("employer")){
            Company user = companyService.findCompanyByEmailOrPhone(username, username);
            if (user != null) {
                session.setAttribute("user", user);
                session.setAttribute("loginType", "employer");
                return "redirect:/job/listJobPage";
            } else {
                model.addAttribute("error", "No account found with this email or phone number");
                return "index";
            }
        }
        return "index";
    }


}


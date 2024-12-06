package vn.edu.iuh.fit.frontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.models.Address;
import vn.edu.iuh.fit.backend.models.Company;
import vn.edu.iuh.fit.backend.repositories.CompanyRepository;
import vn.edu.iuh.fit.backend.services.AddressService;
import vn.edu.iuh.fit.backend.services.CompanyService;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/company/register", method = RequestMethod.GET)
    public ModelAndView showFormAddCompany() {
        ModelAndView model = new ModelAndView("company/add-company");
        model.addObject("company", new Company());
        return model;
    }

    @RequestMapping(value = "/company/register", method = RequestMethod.POST)
    public ModelAndView addCompany(@ModelAttribute Company company) {
        Address address = company.getAddress();
        boolean existingAddress = addressService.checkExistingAddress(address);
        address.setCountry(CountryCode.VN);
        if (!existingAddress) {
            addressService.save(address);
        }
        company.setAddress(address);
        companyService.save(company);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(value = "/company/editProfile", method = RequestMethod.GET)
    public ModelAndView showFormEditCompany(HttpSession session) {
        ModelAndView model = new ModelAndView("company/company-update");
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null) {
            return new ModelAndView("redirect:/login");
        }
        model.addObject("user", (Company) loggedInUser);
        return model;
    }

    @RequestMapping(value = "/company/updateProfile", method = RequestMethod.POST)
    public ModelAndView updateCompany(@ModelAttribute Company company, HttpSession session) {
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null) return new ModelAndView("redirect:/login");

        Company updateCompany = companyService.findCompanyById(((Company) loggedInUser).getCompanyId());
        Address address = company.getAddress();
        if (address != null) {
            address.setCountry(CountryCode.VN);
            addressService.save(address);
        }
        updateCompany.setAddress(address);
        updateCompany.setPhone(company.getPhone());
        updateCompany.setCompanyName(company.getCompanyName());
        updateCompany.setEmail(company.getEmail());
        updateCompany.setAbout(company.getAbout());
        updateCompany.setWebUrl(company.getWebUrl());
        companyService.save(updateCompany);

        session.setAttribute("user", updateCompany);
        return new ModelAndView("redirect:/company/manageProfile");
    }

    @RequestMapping(value = "/company/manageProfile", method = RequestMethod.GET)
    public ModelAndView manageCompany(HttpSession session) {
        ModelAndView model = new ModelAndView("company/company-profile");
        Object loggedInUser = session.getAttribute("user");
        if (loggedInUser == null) {
            return new ModelAndView("redirect:/login");
        }
        model.addObject("user", (Company) loggedInUser);
        return model;
    }

}

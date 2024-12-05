package vn.edu.iuh.fit.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.Company;
import vn.edu.iuh.fit.backend.repositories.CompanyRepository;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company findCompanyByEmailOrPhone(String email, String phone) {
        return companyRepository.findCompanyByEmailOrPhone(email, phone);
    }

    public List<Company> findAllCompany() {
        return companyRepository.findAll();
    }

    public Company findCompanyById(Long id) {
        return companyRepository.findByCompanyId(id);
    }

    public List<Company> findCompanyByCompanyName(String companyName) {
        return companyRepository.findCompaniesByCompanyNameLikeIgnoreCase(companyName);
    }

    public Company findCompanyByName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }
}

package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.backend.models.Company;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findCompanyByEmailOrPhone(String email, String phone);

    List<Company> findCompaniesByCompanyNameLikeIgnoreCase(String companyName);

    Company findByCompanyName(String companyName);

    Company findByCompanyId(Long id);
}
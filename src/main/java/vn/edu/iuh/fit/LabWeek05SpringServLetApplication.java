package vn.edu.iuh.fit;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import vn.edu.iuh.fit.backend.models.Address;
import vn.edu.iuh.fit.backend.models.Candidate;
import vn.edu.iuh.fit.backend.models.Skill;
import vn.edu.iuh.fit.backend.repositories.AddressRepository;
import vn.edu.iuh.fit.backend.repositories.CandidateRepository;
import vn.edu.iuh.fit.backend.repositories.SkillRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

@SpringBootApplication
public class LabWeek05SpringServLetApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LabWeek05SpringServLetApplication.class, args);
    }

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello Spring Boot");
        Random random = new Random();
//        for (int i = 0; i < 100; i++) {
//            Address address = new Address();
//            address.setCity("HCM");
//            address.setCountry(CountryCode.VN);
//            address.setNumber(random.nextInt(1,1000) + "");
//            address.setStreet("Phạm Văn Đồng");
//            address.setZipcode(random.nextInt(16000, 80000)+"");
//            addressRepository.save(address);
//
//            Candidate candidate = new Candidate();
//            candidate.setDob(Date.valueOf(LocalDate.of(2000, random.nextInt(1,13), random.nextInt(1,29))).toLocalDate());
//            candidate.setEmail("email" + i + "@example.com");
//            candidate.setFullName("First Name " + i);
//            candidate.setAddress(address);
//            candidate.setPhone("Phone " + i);
//            candidateRepository.save(candidate);
//
//            System.out.println("Saved candidate: " + candidate);
//        }
//
//        String[] skillNames = {"Java", "Python", "JavaScript", "C#", "C++", "Ruby", "Go", "Swift", "Kotlin", "PHP"};
//        for (int i = 0; i < 10; i++) {
//            Skill skill = new Skill();
//            skill.setSkillName(skillNames[random.nextInt(skillNames.length)]);
//            skillRepository.save(skill);
//            System.out.println("Saved skill: " + skill);
//        }
    }
}

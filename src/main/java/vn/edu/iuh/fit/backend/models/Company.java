package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @ToString
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long companyId;
    private String webUrl;
    private String phone;
    private String email;
    private String companyName;
    private String about;

    @OneToOne
    @JoinColumn(name = "addressId", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "company")
    private List<Job> jobs;

    @OneToMany(mappedBy = "company")
    private List<Experience> experiences;
}

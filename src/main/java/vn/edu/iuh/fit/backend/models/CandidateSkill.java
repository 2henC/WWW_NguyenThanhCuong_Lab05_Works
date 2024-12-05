package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.iuh.fit.backend.enums.SkillLevel;
import vn.edu.iuh.fit.backend.ids.CandidateSkillId;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@IdClass(CandidateSkillId.class)
public class CandidateSkill {
    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;
    private String moreInfo;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;
}

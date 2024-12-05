package vn.edu.iuh.fit.backend.models;

import jakarta.persistence.*;
import lombok.*;
import vn.edu.iuh.fit.backend.enums.SkillLevel;
import vn.edu.iuh.fit.backend.ids.JobSkillId;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@IdClass(JobSkillId.class)
public class JobSkill {
    @Id
    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Id
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    private String moreInfo;

    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel;
}

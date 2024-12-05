package vn.edu.iuh.fit.backend.ids;

import java.io.Serializable;
import java.util.Objects;

public class CandidateSkillId implements Serializable {
    private Long skill;
    private Long candidate;

    // Default constructor
    public CandidateSkillId() {}

    // Parameterized constructor
    public CandidateSkillId(Long skill, Long candidate) {
        this.skill = skill;
        this.candidate = candidate;
    }

    // Getters and setters
    public Long getSkill() {
        return skill;
    }

    public void setSkill(Long skill) {
        this.skill = skill;
    }

    public Long getCandidate() {
        return candidate;
    }

    public void setCandidate(Long candidate) {
        this.candidate = candidate;
    }

    // hashCode and equals methods
    @Override
    public int hashCode() {
        return Objects.hash(skill, candidate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CandidateSkillId that = (CandidateSkillId) obj;
        return Objects.equals(skill, that.skill) && Objects.equals(candidate, that.candidate);
    }
}

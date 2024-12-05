package vn.edu.iuh.fit.backend.ids;

import java.io.Serializable;
import java.util.Objects;

public class JobSkillId implements Serializable {
    private Long skill;
    private Long job;

    // Default constructor
    public JobSkillId() {}

    // Parameterized constructor
    public JobSkillId(Long skill, Long job) {
        this.skill = skill;
        this.job = job;
    }

    // Getters and setters
    public Long getSkill() {
        return skill;
    }

    public void setSkill(Long skill) {
        this.skill = skill;
    }

    public Long getJob() {
        return job;
    }

    public void setJob(Long job) {
        this.job = job;
    }

    // hashCode and equals methods
    @Override
    public int hashCode() {
        return Objects.hash(skill, job);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JobSkillId that = (JobSkillId) obj;
        return Objects.equals(skill, that.skill) && Objects.equals(job, that.job);
    }
}
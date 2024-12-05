package vn.edu.iuh.fit.backend.dtos;

public class SkillDTO {
    private String skillName;
    private String skillLevel;

    // Constructor
    public SkillDTO(String skillName, String skillLevel) {
        this.skillName = skillName;
        this.skillLevel = skillLevel;
    }

    // Getters and Setters
    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }
}


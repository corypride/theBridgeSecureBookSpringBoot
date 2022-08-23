package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class CourseLevel extends AbstractEntity{
    @NotBlank(message = "level cannot be blank")
    @NotNull(message = "Level cannot be null")
    private String level;

    public CourseLevel(){}

    public CourseLevel(String courseLevel, String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

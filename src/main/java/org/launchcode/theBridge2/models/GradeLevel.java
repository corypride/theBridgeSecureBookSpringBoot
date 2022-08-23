package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GradeLevel extends AbstractEntity{

    @NotNull(message = "must enter a grade level")
    @NotBlank(message = "must enter a grade level")
    private String level;

    @OneToMany(mappedBy = "gradeLevel")
    private List<Student> students = new ArrayList<>();

    public GradeLevel(){}

    public GradeLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

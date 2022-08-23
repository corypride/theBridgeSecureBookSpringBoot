package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CourseGenre extends AbstractEntity{

    @NotBlank(message="Must enter a genre")
    @NotNull(message="Must enter a genre")
    private String genre;

    @OneToMany(mappedBy = "courseGenre")
    private final List<Course> courses = new ArrayList<>();

    public CourseGenre(String genre) {
        this.genre = genre;
    }

    public CourseGenre(){}

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Course> getCourses() {
        return courses;
    }
    public void addCourse(Course course){
        this.courses.add(course);
    }
}

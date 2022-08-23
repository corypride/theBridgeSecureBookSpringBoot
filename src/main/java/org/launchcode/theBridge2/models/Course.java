package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course extends AbstractEntity{
    @ManyToMany
    private final List<School> schools = new ArrayList<>();

    @NotNull(message = "Course name cannot be null")
    @NotBlank(message = "Course name cannot be blank")
    private String courseName;

    @NotNull(message = "Course genre cannot be null")
    @ManyToOne
    private CourseGenre courseGenre;

    @NotNull(message = "Course level cannot be null")
    @ManyToOne
    private CourseLevel courseLevel;


    @OneToMany(mappedBy = "course")
    List<Class> classes = new ArrayList<>();

    public Course(){ }

    public Course(String courseName, CourseGenre genre, CourseLevel courseLevel) {

        this.courseName = courseName;
        this.courseGenre = genre;
        this.courseLevel = courseLevel;

    }

    public List<School> getSchools() {
        return schools;
    }

    public void addSchool(School school){
        this.schools.add(school);
    }

    public void addSchools(List<School> schools){
        for (School school: schools){
            this.addSchool(school);
        }
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public CourseGenre getCourseGenre() {
        return courseGenre;
    }

    public void setCourseGenre(CourseGenre courseGenre) {
        this.courseGenre = courseGenre;
    }

    public CourseLevel getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(CourseLevel courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getDisplayName(){
        return this.courseLevel.getLevel()+ " - "+this.getCourseName();
    }
}


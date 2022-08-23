package org.launchcode.theBridge2.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class School extends AbstractEntity{

    @NotBlank(message = "School name cannot be null")
    private String schoolName;

    @Valid
    @OneToMany(mappedBy = "school")
    private  List<Student> students = new ArrayList<>();

    @Valid
    @OneToMany(mappedBy = "school")
    private final List<Administrator> administrators = new ArrayList<>();

    @Valid
    @ManyToMany(mappedBy = "schools")
    private final List<Course> courses = new ArrayList<>();

    @Valid
    @OneToMany(mappedBy = "school")
    private  List<Class> classes = new ArrayList<>();

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    private SchoolDetails schoolDetails;

    public School (){ }

    public School(String schoolName, SchoolDetails schoolDetails) {
        this.schoolName = schoolName;
        this.schoolDetails = schoolDetails;

    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }



    public List<Administrator> getAdministrators() {
        return administrators;
    }

    public void addAdministrator(Administrator a) {
        administrators.add(a);
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void addCourse (Course course){
        this.courses.add(course);
    }

    public SchoolDetails getSchoolDetails() {
        return schoolDetails;
    }

    public void setSchoolDetails(SchoolDetails schoolDetails) {
        this.schoolDetails = schoolDetails;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudents(List<Student> students){
        this.students = students;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void addClasses(List<Class> classes){
        this.classes = classes;
    }



    @Override
    public String toString() {
        return "School{" +
                "schoolName='" + schoolName + '\'' +
                ", students=" + students +
                ", administrators=" + administrators +
                ", courses=" + courses +
                ", schoolDetails=" + schoolDetails +
                '}';
    }
}

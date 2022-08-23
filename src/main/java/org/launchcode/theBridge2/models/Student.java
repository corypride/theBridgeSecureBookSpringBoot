package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Student extends AbstractEntity{

    @Valid
    @ManyToOne
    private School school;


    private String bridgeIDNum;

    @NotEmpty(message = "Students last name cannot be empty")
    private String studentsLastName;

    @NotEmpty(message = "Students first name cannot be empty")
    private String studentsFirstName;

    private String studentsMiddleName;

    @NotEmpty(message = "DOB cannot be empty")
    private String dateOfBirth;

    private String profilePic; //Url of the students picture

    @Valid
    @ManyToOne
    private GradeLevel gradeLevel;

    //add the relationship for classes
    @ManyToMany(mappedBy = "students")
    private List<Class> classes = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<User> parentsOrGuardians;

    public Student(){}

    public Student(School aSchool, String studentsLastName,
                   String studentsFirstName, String studentsMiddleName,
                   String dateOfBirth, String profilePic,
                   GradeLevel gradeLevel) {
        this.school = aSchool;
        this.studentsLastName = studentsLastName;
        this.studentsFirstName = studentsFirstName;
        this.studentsMiddleName = studentsMiddleName;
        this.bridgeIDNum =
                this.studentsFirstName +"-SC"+ aSchool.getId()+"-ST"+this.getId();
        this.dateOfBirth = dateOfBirth;
        this.profilePic = profilePic;
        this.gradeLevel = gradeLevel;
    }



    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public String getStudentsLastName() {
        return studentsLastName;
    }

    public void setStudentsLastName(String studentsLastName) {
        this.studentsLastName = studentsLastName;
    }

    public String getStudentsFirstName() {
        return studentsFirstName;
    }

    public void setStudentsFirstName(String studentsFirstName) {
        this.studentsFirstName = studentsFirstName;
    }

    public String getStudentsMiddleName() {
        return studentsMiddleName;
    }

    public void setStudentsMiddleName(String studentsMiddleName) {
        this.studentsMiddleName = studentsMiddleName;
    }

    public String getFullName(){
        return this.studentsFirstName +" "+ this.studentsMiddleName+" "+this.studentsLastName;
    }

    public String getLastNameFirstName(){
        return this.studentsLastName +", "+ this.studentsFirstName +" "+ this.studentsMiddleName;
    }
    public String getFullNmNoSpaces(){
        return this.studentsFirstName+this.studentsMiddleName+this.studentsLastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public List<User> getParentsOrGuardians() {
        return parentsOrGuardians;
    }

    public void setParentsOrGuardians(List<User> parentsOrGuardians) {
        this.parentsOrGuardians = parentsOrGuardians;
    }


}

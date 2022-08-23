package org.launchcode.theBridge2.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Administrator extends AbstractEntity{
    @OneToOne(cascade = CascadeType.ALL)
    User user;

    @NotNull(message = "DOB cannot be null")
    @NotBlank(message="DOB cannot be blank")
    private String dateOfBirth;

    private String dateHired;

    private String expertise;

    @Valid
    @ManyToOne
    private AdminPosition adminPosition;

    @Valid
    @ManyToOne
    private School school; //link to a school in the schools table

    @OneToMany(mappedBy = "instructor")
    private List<Class> classes = new ArrayList<>();

    @ManyToMany(mappedBy = "otherStaff")
    private List<Class> classList = new ArrayList<>();

    public Administrator() { }

    public Administrator(String dateOfBirth, String dateHired,
                         String expertise, AdminPosition adminPosition,
                         School school) {
//
        this.dateOfBirth = dateOfBirth;
        this.dateHired = dateHired == null? "N/A": dateHired ;
        this.expertise = expertise;
        this.adminPosition = adminPosition;
        this.school = school;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //    public String getATLastName() {
//        return ATLastName;
//    }
//
//    public void setATLastName(String ATLastName) {
//        this.ATLastName = ATLastName;
//    }
//
//    public String getATFirstName() {
//        return ATFirstName;
//    }
//
//    public void setATFirstName(String ATFirstName) {
//        this.ATFirstName = ATFirstName;
//    }
//
//    public String getATMiddleName() {
//        return ATMiddleName;
//    }
//
//    public void setATMiddleName(String ATMiddleName) {
//        this.ATMiddleName = ATMiddleName;
//    }
//
    public String getDisplayName() {
        return this.getUser().getDisplayName();
    }
//

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateHired() {
        return dateHired;
    }

    public void setDateHired(String dateHired) {
        this.dateHired = dateHired;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public AdminPosition getAdminPosition() {
        return adminPosition;
    }

    public void setAdminPosition(AdminPosition adminPosition) {
        this.adminPosition = adminPosition;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }

    public List<Class> getClassList() {
        return classList;
    }

    public void setClassList(List<Class> classList) {
        this.classList = classList;
    }



    @Override
    public String toString() {
        return "Administrator{" +
                ", ATLastName='" + this.getUser().getLastName() + '\'' +
                ", ATFirstName='" + this.getUser().getFirstName() + '\'' +
                ", ATMiddleName='" + this.getUser().getMiddleName() + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", dateHired='" + dateHired + '\'' +
                ", expertise='" + expertise + '\'' +
                "adminPosition=" + adminPosition +
                ", school=" + school +
                '}';
    }
}

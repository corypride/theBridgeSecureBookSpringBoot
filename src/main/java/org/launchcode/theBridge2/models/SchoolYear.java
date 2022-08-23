package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SchoolYear extends AbstractEntity{

    @Min(2022)
    private int year;

    @OneToMany(mappedBy = "schoolYear")
    private List<Class> classes = new ArrayList<>();


    public SchoolYear(){}

    public SchoolYear(int year) {
        this.year = year;
    }


    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }
}

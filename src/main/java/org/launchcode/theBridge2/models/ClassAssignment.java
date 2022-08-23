package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class ClassAssignment extends AbstractEntity{
    @ManyToOne
    private Class aClass;

    //@NotNull (message = "Assignment name be null")
    @NotBlank(message = "Assignment name cannot be blank")
    private String assignmentName;


    //@NotNull (message = "Description can not be null")
    @NotBlank(message = "Description name cannot be blank")
    private String description;

    //@NotNull (message = "Assignment name must not be null")
    @NotBlank(message = "Due date cannot be blank")
    private String dueDate;

    @NotNull(message="Must enter into the program")
    @Min(1)
    private double maxPoints;

    //@NotNull(message = "Must enter a value")
    private boolean isExtraCredit;

    @OneToMany(mappedBy = "classAssignment")
    private List<AssignmentStatus> assignmentStatuses;

    public ClassAssignment() { }

    public ClassAssignment(Class aClass, String assignmentName, String description,
                           String dueDate, double maxPoints,
                           boolean isExtraCredit) {
        this.aClass = aClass;
        this.assignmentName = assignmentName;
        this.description = description;
        this.dueDate = dueDate;
        this.maxPoints = maxPoints;
        this.isExtraCredit = isExtraCredit;
    }


    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public double getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(double maxPoints) {
        this.maxPoints = maxPoints;
    }

    public String getIsExtraCredit(){
        if(this.isExtraCredit){
            return "Yes";
        }
        return "No";
    }

    public boolean isExtraCredit() {
        return isExtraCredit;
    }

    public void setExtraCredit(boolean extraCredit) {
        isExtraCredit = extraCredit;
    }

    public List<AssignmentStatus> getAssignmentStatuses() {
        return assignmentStatuses;
    }

    public void setAssignmentStatuses(List<AssignmentStatus> assignmentStatuses) {
        this.assignmentStatuses = assignmentStatuses;
    }

    public String getInfo(){
        String info = this.getaClass().getSemesterInfo();
        return info;
    }

}

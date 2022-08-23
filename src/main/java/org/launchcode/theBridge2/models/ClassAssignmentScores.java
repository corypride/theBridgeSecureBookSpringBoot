package org.launchcode.theBridge2.models;

import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class ClassAssignmentScores extends AbstractEntity{


    @NotNull(message = "Class assignment id cannot be null")
    @NotBlank(message = "Class assignment id cannot be blank")
    private  int catid; //link to the class assignments table

    @NotNull(message = "Student id cannot be null")
    @NotBlank(message = "Student id cannot be blank")
    private int bstid; //link to the students table

    @Min(value = 0,message = "Total points must be greater than or equal to " +
            "zero")
    private double totalPointsEarned;

    private String notes;

    public ClassAssignmentScores(){}

    public ClassAssignmentScores(int catid, int bstid, double totalPointsEarned, String notes) {
        this.catid = catid;
        this.bstid = bstid;
        this.totalPointsEarned = totalPointsEarned;
        this.notes = notes;
    }


    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public int getBstid() {
        return bstid;
    }

    public void setBstid(int bstid) {
        this.bstid = bstid;
    }

    public double getTotalPointsEarned() {
        return totalPointsEarned;
    }

    public void setTotalPointsEarned(double totalPointsEarned) {
        this.totalPointsEarned = totalPointsEarned;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
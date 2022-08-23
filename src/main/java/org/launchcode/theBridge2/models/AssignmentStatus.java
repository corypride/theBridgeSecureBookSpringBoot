package org.launchcode.theBridge2.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.HashMap;

@Entity
public class AssignmentStatus extends AbstractEntity{
    @ManyToOne
    private ClassAssignment classAssignment;

    @OneToOne(cascade = CascadeType.ALL)
    private Student student;

    @ManyToOne
    private Status status;

    private String notes;

    private String dateCompleted;

    //todo totalPointsScored is to be used in a later version of the app
    private double totalPointsScored;

    public AssignmentStatus() {}

    public AssignmentStatus(ClassAssignment aClassAssignment, Student aStudent
            , Status aStatus, String notes,String dateCompleted,double totalPointsScored){
        this.classAssignment = aClassAssignment;
        this.student = aStudent;
        this.status = aStatus;
        this.notes = notes;
        this.dateCompleted = (dateCompleted.isEmpty()?"Not yet completed":
                dateCompleted);
        this.totalPointsScored = totalPointsScored;

    }

    public ClassAssignment getClassAssignment() {
        return classAssignment;
    }

    public void setClassAssignment(ClassAssignment classAssignment) {
        this.classAssignment = classAssignment;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public double getTotalPointsScored() {
        return totalPointsScored;
    }

    public void setTotalPointsScored(double totalPointsScored) {
        this.totalPointsScored = totalPointsScored;
    }

    public HashMap<String,Double> getStudentScoreMap(){
        HashMap<String,Double> score = new HashMap<>();

        Double aValue = 0.0;

        if(this.status.getStatusName().equals("Good")){
            aValue = 95.0;
        } else if (this.status.getStatusName().equals("Average")){
            aValue = 75.0;
        } else if (this.status.getStatusName().equals("Below Average")) {
            aValue = 49.0;
        } else if (this.status.getStatusName().equals("N/A")){
            aValue = 0.0;
        }
        score.put(this.getStudent().getFullName(),aValue);

        return score;

    }

    public Double getStudentScore(){
        Double aValue = 0.0;
        if(this.status.getStatusName().equals("Good")){
            aValue = 95.0;
        } else if (this.status.getStatusName().equals("Average")){
            aValue = 75.0;
        } else if (this.status.getStatusName().equals("Below Average")) {
            aValue = 49.0;
        } else {
            aValue = 0.0;
        }
        return aValue;
    }

}

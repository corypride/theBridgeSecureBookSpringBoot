package org.launchcode.theBridge2.models;

import javax.persistence.Entity;

@Entity
public class StudentStatus extends AbstractEntity{

    //@OneToOne Student
    //@ManyToMany - Class
    //@ManyToOne - Status

    //YTDTardy maybe make this boolean and do a count in the controller?
    //YTDAbsent maybe make this boolean and do a count in the controller?

    //Grade to be added at a later date(this can be eventually calculated by
    // using the assignmentStatus totalPointsScored and ClassAssignment
    // maxPoints),at this point just use the status in
    // the place of grade or an image that represents good, fair, bad

    //String date
    //String note

    public StudentStatus(){}


}

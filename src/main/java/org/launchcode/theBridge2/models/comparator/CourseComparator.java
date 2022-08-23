package org.launchcode.theBridge2.models.comparator;

import org.launchcode.theBridge2.models.Course;

import java.util.Comparator;

public class CourseComparator implements Comparator<Course> {

    @Override
    public int compare(Course o1, Course o2) {
        if(o1.getCourseName().trim().toLowerCase().compareTo(o2.getCourseName().trim().toLowerCase())<0){
            return -1;
        } else if (o1.getCourseName().trim().toLowerCase().compareTo(o2.getCourseName().trim().toLowerCase())>0){
            return 1;
        }
        return 0;
    }
}

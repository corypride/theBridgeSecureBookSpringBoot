package org.launchcode.theBridge2.models.comparator;

import org.launchcode.theBridge2.models.Student;

import java.util.Comparator;

public class StudentNmComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        String s1 = o1.getStudentsLastName().toLowerCase();
        String s2 = o2.getStudentsLastName().toLowerCase();
        if(s1.compareTo(s2) < 0){
            return -1;
        } else if (s1.compareTo(s2) >0){
            return 1;
        }
        return 0;
    }
}

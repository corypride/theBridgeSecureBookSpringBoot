package org.launchcode.theBridge2.models.comparator;

import org.launchcode.theBridge2.models.School;

import java.util.Comparator;

public class SchoolNmComparator implements Comparator<School> {
    @Override
    public int compare(School o1, School o2) {
        String string1 = o1.getSchoolName().toLowerCase();
        String string2 = o2.getSchoolName().toLowerCase();

        if(string1.compareTo(string2) < 0){
            return -1;
        } else if (string1.compareTo(string2) > 0){
            return 1;
        }
        return 0;
    }
}

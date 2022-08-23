package org.launchcode.theBridge2.models.comparator;

import org.launchcode.theBridge2.models.Administrator;

import java.util.Comparator;

public class AdministratorLastNmComparator implements Comparator<Administrator> {
    @Override
    public int compare(Administrator o1, Administrator o2) {
        String name1 = o1.getUser().getLastName().toLowerCase();
        String name2 = o2.getUser().getLastName().toLowerCase();
        if ( name1.compareTo(name2) <0){
            return -1;
        } else if (name1.compareTo(name2) >0){
            return 1;
        }
        return 0;
    }
}

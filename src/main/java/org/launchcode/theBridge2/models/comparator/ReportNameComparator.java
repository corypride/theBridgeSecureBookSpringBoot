package org.launchcode.theBridge2.models.comparator;

import org.launchcode.theBridge2.models.FormName;

import java.util.Comparator;

public class ReportNameComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        Integer test = Integer.parseInt(o1.substring(0,2).trim()) ;
        Integer test2 = Integer.parseInt(o2.substring(0,2).trim());

        if(test - test2 > 0){

            return 1;
        } else if(test - test2 < 0){
            return -1;
        }
        return 0;
    }
}

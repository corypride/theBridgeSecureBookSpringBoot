package org.launchcode.theBridge2.models;

import java.util.Calendar;

public class GetTodaysDate {
    private static String date;

    public GetTodaysDate() {

    }

    public static String getDate() {
        Calendar c = Calendar.getInstance();
        String today =
                (c.get(c.YEAR)+ "-"+(c.get(c.MONTH)+1)+"-"+ c.get(c.DATE)) ;

        date = today;


        return date;
    }
}

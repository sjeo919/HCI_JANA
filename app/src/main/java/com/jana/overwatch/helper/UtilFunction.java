package com.jana.overwatch.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Andy Choi on 10/12/2016.
 */

public class UtilFunction {
    public static String formatLastTimeUsed(String compareDate) {
        long compareDateTime = 0, currentDateTime = 0;
        String formattedString = "";

        SimpleDateFormat compareDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss.SSS'Z'");
        try {
            Date compareDateObj = compareDateFormatter.parse(compareDate);
            compareDateTime = compareDateObj.getTime();

            Date currentDateObj = new Date();
            currentDateTime = currentDateObj.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int time = (int) (currentDateTime - compareDateTime)/1000;
        int number = 0;
        if ((number = time/(24*60*60)) >= 1) {
            formattedString = number + "d";
        } else if ((number = time/(60*60)) >= 1) {
            formattedString = number + "h";
        } else if ((number = time/60) >= 1) {
            formattedString = number + "m";
        } else if (time >= 1) {
            formattedString = time + "s";
        }

        return formattedString + " ago";
    }
}

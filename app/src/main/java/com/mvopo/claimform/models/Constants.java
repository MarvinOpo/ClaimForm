package com.mvopo.claimform.models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Constants {

    public static ArrayList<String> getInclusiveDates(String startDate, String endDate) {
        ArrayList<String> datesInRange = new ArrayList<>();

        try {
            Date from = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).parse(startDate);
            Date to = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).parse(endDate);

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(from);

            Calendar endCalendar = new GregorianCalendar();
            endCalendar.setTime(to);

            while (!calendar.after(endCalendar)) {
                Date date = calendar.getTime();
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                String strDate = dateFormat.format(date);

                datesInRange.add(strDate);
                calendar.add(Calendar.DATE, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return datesInRange;
    }
}

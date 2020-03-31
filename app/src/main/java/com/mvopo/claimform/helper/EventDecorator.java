package com.mvopo.claimform.helper;

import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.List;
import java.util.Locale;

public class EventDecorator implements DayViewDecorator {
    private List<String> markedDays;

    private Drawable drawable;

    public EventDecorator(Drawable drawable, List<String> markedDays) {
        this.markedDays = markedDays;
        this.drawable = drawable;

    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        String date = String.format(Locale.getDefault(), "%02d", (day.getMonth())) + "/" + String.format(Locale.US,"%02d", day.getDay()) +
                "/" + day.getYear();

        return markedDays.contains(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
    }
}

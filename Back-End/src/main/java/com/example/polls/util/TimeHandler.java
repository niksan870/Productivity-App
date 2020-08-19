package com.example.polls.util;

import com.example.polls.dto.TimeRequest;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class TimeHandler {
    int days = 0;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;

    public static TimeHandler parse(String in) {
        if (in != null) {
            String[] arr = in.split(":");
            TimeHandler tp = new TimeHandler();
            tp.days = ((arr.length >= 1) ? Integer.parseInt(arr[0]) : 0);
            tp.hours = ((arr.length >= 2) ? Integer.parseInt(arr[1]) : 0);
            tp.minutes = ((arr.length >= 3) ? Integer.parseInt(arr[2]) : 0);
            tp.seconds = ((arr.length >= 4) ? Integer.parseInt(arr[3]) : 0);
            return tp;
        }
        return null;
    }

    public TimeHandler add(TimeHandler a) {
        this.seconds += a.seconds;
        int of = 0;
        while (this.seconds >= 60) {
            of++;
            this.seconds -= 60;
        }
        this.minutes += a.minutes + of;
        of = 0;
        while (this.minutes >= 60) {
            of++;
            this.minutes -= 60;
        }
        this.hours += a.hours + of;
        of = 0;
        while (this.hours >= 24) {
            of++;
            this.hours -= 24;
        }
        this.days += a.days + of;
        return this;
    }

    public static List<DateTime> getDateRange(DateTime start, DateTime end) {

        List<DateTime> ret = new ArrayList<DateTime>();
        DateTime tmp = start;
        while(tmp.isBefore(end) || tmp.equals(end)) {
            ret.add(tmp);
            tmp = tmp.plusDays(1);
        }
        return ret;
    }


    public static String formattedTime(TimeRequest time){
        float hours = time.getTime() / 3600;
        float minutes = (time.getTime() % 3600) / 60;
        float seconds = time.getTime() % 60;
        String formattedTime = "00" + ":" + Helper.twoDigitString(hours) + ":" + Helper.twoDigitString(minutes) + ":" + Helper.twoDigitString(seconds);

        return formattedTime;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d:%02d", days, hours, minutes,
                seconds);
    }
}

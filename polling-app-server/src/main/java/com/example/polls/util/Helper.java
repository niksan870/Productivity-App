package com.example.polls.util;

public class Helper {

    public static String twoDigitString(int number) {
        if (number == 0)
            return "00";
        if (number / 10 == 0)
            return "0" + number;

        return String.valueOf(number);
    }
}

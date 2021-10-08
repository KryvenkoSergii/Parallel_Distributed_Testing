package com.softserve.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static int getTime(String time) {
        int timeSec = 0;
//        // text = "8 hours, 44 minutes, 26 seconds";
//        final Matcher matcherHours = Pattern.compile("(\\d*) hours").matcher(time);
//        final Matcher matcherMinutes = Pattern.compile("(\\d*) minutes").matcher(time);
//        final Matcher matcherSeconds = Pattern.compile("(\\d*) seconds").matcher(time);
//        if (matcherHours.find()) {
////            System.out.println(matcherHours.group(1));
//            timeSec += 3600 * Integer.parseInt(matcherHours.group(1));
//        }
//        if (matcherMinutes.find()) {
////            System.out.println(matcherMinutes.group(1));
//            timeSec += 60 * Integer.parseInt(matcherMinutes.group(1));
//        }
//        if (matcherSeconds.find()) {
////            System.out.println(matcherSeconds.group(1));
//            timeSec += Integer.parseInt(matcherSeconds.group(1));
//        }

        // text = " 8:44:26 ";
        final Matcher matcher1 = Pattern.compile("(\\d*):(\\d*):(\\d*)").matcher(time);
        final Matcher matcher2 = Pattern.compile("^\\s+(\\d*):(\\d*)\\s").matcher(time);
        final Matcher matcher3 = Pattern.compile("^\\s+(\\d*)\\s").matcher(time);
        if (matcher1.find()) {
//            System.out.println("hours = " + matcher1.group(1) + "; minutes = " + matcher1.group(2) + "; sec = " + matcher1.group(3));
            timeSec = 3600 * Integer.parseInt(matcher1.group(1)) + 60 * Integer.parseInt(matcher1.group(2))
                    + Integer.parseInt(matcher1.group(3));
        } else if (matcher2.find()) {
//            System.out.println("minutes = " + matcher2.group(1) + "; sec = " + matcher2.group(2));
            timeSec = 60 * Integer.parseInt(matcher2.group(1)) + Integer.parseInt(matcher2.group(2));
        } else if (matcher3.find()) {
//          System.out.println("minutes = " + matcher2.group(1) + "; sec = " + matcher2.group(2));
          timeSec = Integer.parseInt(matcher3.group(1));
      }
        
        //
        // text = '8 минут 26 секунд'
        final Matcher matcher4 = Pattern.compile("^(\\d+)\\D+(\\d+)\\D+(\\d+)").matcher(time);
        final Matcher matcher5 = Pattern.compile("^(\\d+)\\D+(\\d+)").matcher(time);
        final Matcher matcher6 = Pattern.compile("^(\\d+)\\D+").matcher(time);
        if (matcher4.find()) {
//          System.out.println("hours = " + matcher3.group(1) + "; minutes = " + matcher3.group(2) + "; sec = " + matcher3.group(3));
          timeSec = 3600 * Integer.parseInt(matcher4.group(1)) + 60 * Integer.parseInt(matcher4.group(2))
                  + Integer.parseInt(matcher4.group(3));
      } else if (matcher5.find()) {
//          System.out.println("minutes = " + matcher4.group(1) + "; sec = " + matcher4.group(2));
          timeSec = 60 * Integer.parseInt(matcher5.group(1)) + Integer.parseInt(matcher5.group(2));
      } else if (matcher6.find()) {
//        System.out.println("minutes = " + matcher4.group(1) + "; sec = " + matcher4.group(2));
        timeSec = Integer.parseInt(matcher6.group(1));
    }

//        System.out.println("timeSec = " + timeSec);
        return timeSec;
    }

    public static void main(String[] args) {
//        getTime("1 hours, 01 minutes, 05 seconds");
//        getVideoDuration("  1:01:05 ");
        getTime("59 секунд");
    }
}
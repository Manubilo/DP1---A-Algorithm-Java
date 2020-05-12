package utils.utils;

public class hoursProblem {
    public static int restHours(String hour1, String hour2){
        int hourA = Integer.parseInt(hour1.substring(0,2));
        int minuteA = Integer.parseInt(hour1.substring(3,5));

        int hourB = Integer.parseInt(hour2.substring(0,2));
        int minuteB = Integer.parseInt(hour2.substring(3,5));
        int diff = 0;

        if(hourB < hourA) diff = (24 - hourA + hourB)*60;
        else diff = (hourB - hourA)*60;
        diff +=minuteB - minuteA;
        return diff;
    }
}

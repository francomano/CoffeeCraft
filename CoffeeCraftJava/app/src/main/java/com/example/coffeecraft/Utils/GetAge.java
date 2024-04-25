package com.example.coffeecraft.Utils;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class GetAge {

    public static Integer getAge(String dateString){

        // String = YYYY-MM-DD

        int age;

        int year = Integer.parseInt(dateString.split("-")[0]);
        int month = Integer.parseInt(dateString.split("-")[1]);
        int day = Integer.parseInt(dateString.split("-")[2]);

        LocalDate date = LocalDate.now();

        int currentYear = date.getYear();
        int currentMonth = date.getMonth().getValue();
        int currentDay = date.getDayOfMonth();

        if(month > currentMonth){
            age = currentYear - year;
        } else if (month == currentMonth) {
            if(currentDay >= day){
                age = currentYear - year;
            } else {
                age = currentYear - year - 1;
            }
        } else {
            age = currentYear - year - 1;
        }

        return age;
    }
}

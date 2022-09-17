package com.overloaded.overloaded;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TestCalendarDays {

    @Test
    public void testCalculatePartOne1() {
        String expected = "       ,       ,       ,       ,       ,       ,       ,       ,       ,       ,       ,       ,";
        int year = 2022;
        List<Integer> days = new ArrayList<>();
        Assertions.assertEquals(expected, CalendarDays.calculatePartOne(year, days));
    }

    @Test
    public void testCalculatePartOne2() {
        String expected = "     s ,       ,       ,       ,       ,       ,       ,       ,       ,       ,       ,       ,";
        int year = 2022;
        List<Integer> days = List.of(1);
        Assertions.assertEquals(expected, CalendarDays.calculatePartOne(year, days));
    }

    @Test
    public void testCalculatePartOne3() {
        String expected = "weekend,       ,       ,       ,       ,       ,       ,       ,       ,       ,       ,       ,";
        int year = 2022;
        List<Integer> days = List.of(1, 2, 8, 9, 15, 16, 22, 23, 29, 30);
        Assertions.assertEquals(expected, CalendarDays.calculatePartOne(year, days));
    }

    @Test
    public void testCalculatePartOne4() {
        String expected = "       ,weekday,       ,       ,       ,       ,       ,       ,       ,       ,       ,       ,";
        int year = 2022;
        List<Integer> days = List.of(38, 39, 40, 42, 41);
        Assertions.assertEquals(expected, CalendarDays.calculatePartOne(year, days));
    }

    @Test
    public void testCalculatePartOne5() {
        String expected = "mtw f  , tw  s ,mt tf  ,m  tfs ,m   fs ,   tf s,m  tfss,    fs ,m  t s ,     s ,    fs , tw fss,";
        int year = 2094;
        List<Integer> days = List.of(
                4,5,6,1,33,34,37,60,61,63,64,95,91,92,93,123,127,121,154,155,157,186,182,183,184,185,218,219,249,245,247,275,309,310,341,335,337,338,339
        );
        Assertions.assertEquals(expected, CalendarDays.calculatePartOne(year, days));
    }

    @Test
    public void testCalculatePartTwo1() {
        String expected = "mtw f  , tw  s ,mt tf  ,m  tfs ,m   fs ,   tf s,m  tfss,    fs ,m  t s ,     s ,    fs , tw fss,";
        CalendarDays.Input input = new CalendarDays.Input();
        input.numbers = CalendarDays.partTwo(expected);
        String calculated = CalendarDays.partOne(input);
        Assertions.assertEquals(expected, calculated);
    }

    @Test
    public void testCalculatePartTwo2() {
        String expected = "weekend,weekday, t     ,alldays,alldays,alldays,alldays,alldays,alldays,alldays,alldays,       ,";
        CalendarDays.Input input = new CalendarDays.Input();
        input.numbers = CalendarDays.partTwo(expected);
        String calculated = CalendarDays.partOne(input);
        Assertions.assertEquals(expected, calculated);
    }


    @Test
    public void working() {
//        GregorianCalendar cal = new GregorianCalendar(2022, Calendar.SEPTEMBER, 17);
//        System.out.println(cal.getFirstDayOfWeek()==Calendar.SUNDAY);
//        System.out.println(cal.get(Calendar.DAY_OF_YEAR));
//        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
//        System.out.println(cal.get(Calendar.DAY_OF_MONTH));
//        System.out.println(cal.get(Calendar.DAY_OF_YEAR));
    }
}

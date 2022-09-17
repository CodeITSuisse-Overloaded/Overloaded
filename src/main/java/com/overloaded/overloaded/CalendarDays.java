package com.overloaded.overloaded;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarDays {
    static class Input {
        public List<Integer> numbers;
    }
    static class Output {
        public String part1;
        public List<Integer> part2;
        Output(String part1, List<Integer> part2) {
            this.part1 = part1;
            this.part2 = part2;
        }
    }

    static class MonthRecord {
        boolean Mon;
        boolean Tue;
        boolean Wed;
        boolean Thu;
        boolean Fri;
        boolean Sat;
        boolean Sun;

        void addDay(int day) {
            switch (day) {
            case Calendar.MONDAY:
                Mon = true;
                break;
            case Calendar.TUESDAY:
                Tue = true;
                break;
            case Calendar.WEDNESDAY:
                Wed = true;
                break;

            case Calendar.THURSDAY:
                Thu = true;
                break;

            case Calendar.FRIDAY:
                Fri = true;
                break;

            case Calendar.SATURDAY:
                Sat = true;
                break;

            case Calendar.SUNDAY:
                Sun = true;
                break;

            default:

            }
        }

        String genString() {
            String res;
            if (Mon && Tue && Wed && Thu && Fri && Sat && Sun) {
                return "alldays,";
            } else if (!Mon && !Tue && !Wed && !Thu && !Fri && Sat && Sun) {
                return "weekend,";
            } else if (Mon && Tue && Wed && Thu && Fri && !Sat && !Sun) {
                return "weekday,";
            } else {
                res = (Mon ? "m" : " ") +
                        (Tue ? "t" : " ") +
                        (Wed ? "w" : " ") +
                        (Thu ? "t" : " ") +
                        (Fri ? "f" : " ") +
                        (Sat ? "s" : " ") +
                        (Sun ? "s" : " ") +
                        ",";
            }
            return res;
        }
    }

    public static String partOne(CalendarDays.Input input) {
        int year = input.numbers.get(0);
        List<Integer> days = parseInput(input);

        return calculatePartOne(year, days);
    }

    public static String calculatePartOne(int year, List<Integer> days) {
        List<MonthRecord> yearRecord = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            yearRecord.add(new MonthRecord());
        }

        for (int day : days) {
            GregorianCalendar cal = new GregorianCalendar(year, Calendar.JANUARY, 1);
            cal.set(Calendar.DAY_OF_YEAR, day);
            int month = cal.get(GregorianCalendar.MONTH);
            yearRecord.get(month).addDay(cal.get(GregorianCalendar.DAY_OF_WEEK));
        }

        StringBuilder sb = new StringBuilder();
        for (MonthRecord record : yearRecord) {
            sb.append(record.genString());
        }
        return sb.toString();
    }

    public static List<Integer> partTwo(String input) {
        List<Integer> res = new ArrayList<>();
        int year = 2001 + parseFirstSpace(input);  // `2001` is given from the question
        res.add(year);
        String[] monthRequirements = input.split(",");
        for (int month = 0; month < 12; month++) {
            buildRes(res, year, month, monthRequirements[month]);
        }
        return res;
    }

    static List<Integer> parseInput(CalendarDays.Input input) {
        List<Integer> days = new ArrayList<>();
        for (int i = 1; i < input.numbers.size(); i++) {
            int num = input.numbers.get(i);
            if (num > 0 && num < 367) {
                days.add(num);
            }
        }
        return days;
    }

    static int parseFirstSpace(String input) {
        int res = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                res = i;
                break;
            }
        }
        return res;
    }
    
    static void buildRes(List<Integer> res, int year, int month, String requirement) {
        GregorianCalendar cal = new GregorianCalendar(year, month, 15);
        int currOrdinal = cal.get(Calendar.DAY_OF_YEAR);
        int currDay = cal.get(Calendar.DAY_OF_WEEK);

        if (requirement.equals("alldays")) {
            for (int i = 0; i < 7; i++) {
                res.add(currOrdinal + i);
            }
        } else if (requirement.equals("weekend")) {
            for (int i = 0; i < 7; i++) {
                if (currDay == Calendar.SATURDAY || currDay == Calendar.SUNDAY) {
                    res.add(currOrdinal);
                }
                currOrdinal++;
                currDay++;
                if (currDay > Calendar.SATURDAY) {
                    currDay = Calendar.SUNDAY;
                }
            }
        } else if (requirement.equals("weekday")) {
            for (int i = 0; i < 7; i++) {
                if (currDay != Calendar.SATURDAY && currDay != Calendar.SUNDAY) {
                    res.add(currOrdinal);
                }
                currOrdinal++;
                currDay++;
                if (currDay > Calendar.SATURDAY) {
                    currDay = Calendar.SUNDAY;
                }
            }
        } else {
            for (int i = 0; i < requirement.length(); i++) {
//                if (requirement.charAt(i) != ' ') {
//                    int day = 1 + ((i+1) % 7);
//                    while (day != currDay) {
//                        currOrdinal++;
//                        currDay++;
//                        if (currDay > Calendar.SATURDAY) {
//                            currDay = Calendar.SUNDAY;
//                        }
//                    }
//                    res.add(currOrdinal);
//                }
                if (requirement.charAt(i) != ' ') {
                    int day = 1 + ((i+1) % 7) ;
                    cal.set(Calendar.DAY_OF_WEEK, day);
                    res.add(cal.get(Calendar.DAY_OF_YEAR));
                }
            }

        }
        System.out.println(res);
        
    }
}

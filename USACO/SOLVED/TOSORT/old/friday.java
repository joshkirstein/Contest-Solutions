package old;

/*
ID: joshkir1
LANG: JAVA
TASK: friday
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class friday {

    public enum Month {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;
    }

    public enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner sc = new Scanner(new FileReader(new File("friday.in")));
        PrintWriter out = new PrintWriter(new FileWriter("friday.out"));
        //Scanner sc = new Scanner(System.in);
        int[] freq = new int[Day.values().length];
        int N = Integer.parseInt(sc.nextLine());
        
        Month startMonth = Month.JANUARY;
        Day startDay = Day.MONDAY;
        int startYear = 1900;

        Month goalMonth = Month.DECEMBER;
        int goalYear = 1900+N-1;

        while (true) {
            int days = daysForMonth(startMonth, startYear);
            for (int i = 1; i <= days; i++) {
                if (i == 13) {
                    freq[indexOf(startDay)] += 1;
                }
                if (startDay == Day.SUNDAY) {
                    startDay = Day.MONDAY;
                } else {
                    startDay = Day.values()[indexOf(startDay) + 1];
                }
            }
            if (startMonth == Month.DECEMBER) {
                startMonth = Month.JANUARY;
                startYear += 1;
            } else {
                startMonth = Month.values()[indexOf(startMonth) + 1];
            }
            if (startMonth == Month.JANUARY && startYear == goalYear + 1) {
                break;
            }
        }
        out.println(freq[5] + " " + freq[6] + " " + freq[0] + " " + freq[1] + " " + freq[2] + " " + freq[3] + " " + freq[4]);
        out.close();
        //System.out.print(freq[5] + " " + freq[6] + " " + freq[0] + " " + freq[1] + " " + freq[2] + " " + freq[3] + " " + freq[4]);
        System.exit(0);
    }

    private static int indexOf(Month day) {
        for (int i = 0; i < Month.values().length; i++) {
            if (Month.values()[i] == day) {
                return i;
            }
        }
        return -1;
    }


    private static int indexOf(Day day) {
        for (int i = 0; i < Day.values().length; i++) {
            if (Day.values()[i] == day) {
                return i;
            }
        }
        return -1;
    }

    private static int daysForMonth(Month month, int year) {
        switch (month) {
            case SEPTEMBER:
            case APRIL:
            case JUNE:
            case NOVEMBER:
                return 30;
            case FEBRUARY:
                if (isLeapYear(year)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return 31;
        }
    }

    private static boolean isLeapYear(int year) {
        if (year % 100 == 0) {
            return year % 400 == 0;
        } else {
            return year % 4 == 0;
        }
    }
}

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

class Scratch {
    public static void main(String[] args) {
        System.out.println(getDateAfterToday(1, 3, 12));
    }
    public static String getDateAfterToday(int years, int months, int days) {
        return LocalDate.now()
                .plus(Period.of(years, months, days))
                .format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

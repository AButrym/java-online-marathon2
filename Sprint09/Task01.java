import java.time.LocalDate;

class Scratch {
    public static void main(String[] args) {
        System.out.println(isLeapYear(2020));
    }
    public static boolean isLeapYear(int year) {
        return LocalDate.ofYearDay(year, 1).isLeapYear();
    }
}

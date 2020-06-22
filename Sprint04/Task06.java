import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;


class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age;
    }
}

class Employee extends Person {
    private final double salary;

    public Employee(String name, int age, double salary) {
        super(name, age);
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return super.toString() + ", Salary: " + salary;
    }
}

class Developer extends Employee {
    private final Level level;

    public Developer(String name, int age, double salary, Level level) {
        super(name, age, salary);
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return super.toString() + ", Level: " + level;
    }
}

enum Level {
    JUNIOR, MIDDLE, SENIOR
}

class PersonComparator implements Comparator<Person> {
    final static Comparator<Person> INSTANCE = Comparator.nullsFirst(
            Comparator.comparing(Person::getName)
                    .thenComparingInt(Person::getAge));

    @Override
    public int compare(Person o1, Person o2) {
        return INSTANCE.compare(o1, o2);
    }
}

class EmployeeComparator implements Comparator<Employee> {
    final static Comparator<Employee> INSTANCE =
            Comparator.<Employee, Person>comparing(Function.identity(),
                    PersonComparator.INSTANCE)
                    .thenComparingDouble(Employee::getSalary);

    @Override
    public int compare(Employee o1, Employee o2) {
        return INSTANCE.compare(o1, o2);
    }
}

class DeveloperComparator implements Comparator<Developer> {
    final static Comparator<Developer> INSTANCE =
            Comparator.<Developer, Employee>comparing(Function.identity(),
                    EmployeeComparator.INSTANCE)
                    .thenComparing(Developer::getLevel);

    @Override
    public int compare(Developer o1, Developer o2) {
        return INSTANCE.compare(o1, o2);
    }
}

class Utility {
    public static <T extends Person> void sortPeople(T[] people, Comparator<? super T> comparator) {
        Arrays.sort(people, comparator);
    }
}

class Main {
    public static void main(String[] args) {
        // smoke test
        Person[] persons = new Person[]{
                new Person("B", 2),
                new Person("B", 1),
                new Person("A", 2)
        };
        System.out.println(Arrays.toString(persons));
        Utility.sortPeople(persons, new PersonComparator());
        System.out.println(Arrays.toString(persons));

        Developer[] developers = new Developer[] {
                new Developer("B", 2, 10_000, Level.MIDDLE),
                new Developer("B", 2, 10_000, Level.JUNIOR),
                new Developer("B", 2, 20_000, Level.JUNIOR),
                new Developer("B", 1, 10_000, Level.JUNIOR),
                new Developer("A", 2, 10_000, Level.SENIOR)
        };
        System.out.println(Arrays.stream(developers).map(Developer::toString)
                .collect(Collectors.joining(",\n","[", "]")));
        Utility.sortPeople(developers, new DeveloperComparator());
        System.out.println(Arrays.stream(developers).map(Developer::toString)
                .collect(Collectors.joining(",\n","[", "]")));
    }
}

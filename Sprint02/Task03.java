import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("Person [name=%s]", getName());
    }

    public String getName() {
        return name;
    }
}

class Student extends Person {
    private final String studyPlace;
    private final int studyYears;

    public Student(String name, String studyPlace, int studyYears) {
        super(name);
        this.studyPlace = studyPlace;
        this.studyYears = studyYears;
    }

    public String getStudyPlace() {
        return studyPlace;
    }

    public int getStudyYears() {
        return studyYears;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getName(), student.getName()) &&
                studyYears == student.studyYears &&
                Objects.equals(studyPlace, student.studyPlace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), studyPlace, studyYears);
    }

    @Override
    public String toString() {
        return String.format("Student [name=%s, studyPlace=%s, studyYears=%d]",
                getName(), getStudyPlace(), getStudyYears());
    }
}

class Worker extends Person {
    private final String workPosition;
    private final int experienceYears;

    public Worker(String name, String workPosition, int experienceYears) {
        super(name);
        this.workPosition = workPosition;
        this.experienceYears = experienceYears;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return Objects.equals(getName(), worker.getName()) &&
                experienceYears == worker.experienceYears &&
                Objects.equals(workPosition, worker.workPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), workPosition, experienceYears);
    }

    @Override
    public String toString() {
        return String.format("Worker [name=%s, workPosition=%s, experienceYears=%d]",
                getName(), getWorkPosition(), getExperienceYears());
    }

    public String getWorkPosition() {
        return workPosition;
    }

    public int getExperienceYears() {
        return experienceYears;
    }
}

class MyUtils {
    public List<Person> maxDuration(List<Person> persons) {
        int maxStudyYear = persons.stream()
                .filter(Student.class::isInstance)
                .map(Student.class::cast)
                .mapToInt(Student::getStudyYears)
                .max().orElse(0);
        int maxExperience = persons.stream()
                .filter(Worker.class::isInstance)
                .map(Worker.class::cast)
                .mapToInt(Worker::getExperienceYears)
                .max().orElse(0);
        Predicate<Person> filter = p ->
                p instanceof Student && ((Student) p).getStudyYears() == maxStudyYear ||
                p instanceof Worker && ((Worker) p).getExperienceYears() == maxExperience;
        return persons.stream()
                .filter(filter)
                .distinct()
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Person> personList = List.of(
                new Person("Ivan"),
                new Student("Petro", "University", 3),
                new Worker("Andriy", "Developer", 12),
                new Student("Stepan", "College", 4),
                new Worker("Ira", "Manager", 12),
                new Student("Ihor", "University", 1)
        );
        new MyUtils().maxDuration(personList).forEach(System.out::println);
    }
}

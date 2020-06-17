import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Employee {
    private final String name;
    private final int experience;
    private final BigDecimal basePayment;

    public Employee(String name, int experience, BigDecimal basePayment) {
        this.name = name == null ? "" : name;
        this.experience = experience;
        this.basePayment = basePayment == null ? BigDecimal.ZERO : basePayment;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public BigDecimal getPayment() {
        return basePayment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return experience == employee.experience &&
                Objects.equals(name, employee.name) &&
                Objects.equals(basePayment, employee.basePayment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, experience, basePayment);
    }

    @Override
    public String toString() {
        return String.format("Employee [name=%s, experience=%d, basePayment=%.2f]",
                name, experience, basePayment.doubleValue());
    }
}

class Manager extends Employee {
    private final double coefficient;

    public Manager(String name, int experience, BigDecimal basePayment, double coefficient) {
        super(name, experience, basePayment);
        this.coefficient = coefficient;
    }

    public double getCoefficient() {
        return coefficient;
    }

    @Override
    public BigDecimal getPayment() {
        return super.getPayment().multiply(
                BigDecimal.valueOf(coefficient).setScale(2, RoundingMode.HALF_UP));
    }

    @Override
    public String toString() {
        return String.format("Manager [name=%s, experience=%d, basePayment=%.2f, coefficient=%.1f]",
                getName(), getExperience(), super.getPayment().doubleValue(), coefficient);
    }
}

class MyUtils {
    public List<Employee> largestEmployees(List<Employee> workers) {
        if (workers == null || workers.isEmpty()) {
            return List.of();
        }
        BigDecimal maxPayment = workers.stream()
                .filter(Objects::nonNull)
                .map(Employee::getPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::max);
        int maxExperience = workers.stream()
                .filter(Objects::nonNull)
                .mapToInt(Employee::getExperience)
                .max().orElse(0);
        Predicate<Employee> filter = employee ->
                employee.getExperience() == maxExperience ||
                        employee.getPayment().compareTo(maxPayment) == 0;
        return workers.stream()
                .filter(Objects::nonNull)
                .filter(filter)
                .distinct()
                .collect(Collectors.toList());
    }
    
    public static void main(String[] args) {
        // smoke test
        List<Employee> employeeList = Arrays.asList(
                new Employee("Ivan", 10, new BigDecimal("3000.00")),
                new Manager("Petro", 9, new BigDecimal("3000.00"), 1.5),
                new Employee("Stepan", 8, new BigDecimal("4000.00")),
                new Employee("Andriy", 7, new BigDecimal("3500.00")),
                new Employee("Ihor", 5, new BigDecimal("4500.00")),
                new Manager("Vasyl", 8, new BigDecimal("2000.00"), 2.0),
                null
        );
        new MyUtils().largestEmployees(employeeList).forEach(System.out::println);

        // test null
        List<Employee> originList = new ArrayList<>();
        originList.add(null);
        List<Employee> actual = new MyUtils().largestEmployees(originList);
        actual.forEach(System.out::println);
    }
}

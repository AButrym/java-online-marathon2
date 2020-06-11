class Employee {
  public String fullName;
  public float salary;
  
  public static void main(String[] args) {
    Employee emp1 = new Employee();
    emp1.fullName = "Smith";
    emp1.salary = 10_000.01f;

    Employee emp2 = new Employee();
    emp2.fullName = "Wesson";
    emp2.salary = 11_000.01f;

    Employee[] employees = new Employee[] {emp1, emp2};
    
    String employeesInfo = "[";
    boolean notFirst = false;
    for (Employee emp : employees) {
        if (notFirst) { employeesInfo += ", "; }
        else { notFirst = true; }
        employeesInfo += String.format("{fullName: \"%s\", salary: %.2f}",
            emp.fullName, emp.salary);
    }
    employeesInfo += "]";
    
    System.out.println(employeesInfo);
  }
}

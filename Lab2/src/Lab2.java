public class Lab2 {
    //Q1
static class Circle {
    private static int count = 0;
    double radius;
    Circle() {
        this(1.0);
    }
    Circle(double r) {
        radius = r;
        count++;
    }
    static int getCount() {
        return count;
    }
}
//Q2
static class Employee {
    private static int nextId = 1000;
    private final int employeeId;
    String name;
    double salary;
    String dept;
    Employee(String name) {
        this(name, 0);
    }
    Employee(String name, double salary) {
        this(name, salary, "General");
    }
    Employee(String name, double salary, String dept) {
        employeeId = nextId++;
        this.name = name;
        this.salary = salary;
        this.dept = dept; }
    Employee(Employee o) {
        this(o.name, o.salary, o.dept);
    }   // copy gets a NEW id
    public String toString() {
        return employeeId + " " + name + " " + salary + " " + dept;
    }
}
//Q3
static class InitDemo {
    static {
        System.out.println("static block 1");
    }
    {
        System.out.println("instance block 1");
    }
    static {
        System.out.println("static block 2");
    }
    {
        System.out.println("instance block 2");
    }
    InitDemo() {
        System.out.println("constructor");
    }
}
//Q4
static class Temperature {
    final double celsius;
    private Temperature(double c) {
        celsius = c;
    }
    static Temperature fromCelsius(double c) {
        return new Temperature(c);
    }
    static Temperature fromFahrenheit(double f) {
        return new Temperature((f - 32) * 5 / 9);
    }
}
public static void main(String[] args) {
        Circle a = new Circle(), b = new Circle(2.5);
        System.out.println(a.radius + " " + b.radius + " count=" + Circle.getCount());

        Employee e1 = new Employee("Amai");
        Employee e2 = new Employee("Tino", 1200);
        Employee e3 = new Employee("Rudo", 1500, "IT");
        Employee e4 = new Employee(e3);   // copy
        for (Employee e : new Employee[]{e1, e2, e3, e4}) System.out.println(e);

        System.out.println("-- init order --");
        new InitDemo(); new InitDemo();

        Temperature t = Temperature.fromFahrenheit(212);
        System.out.println(t.celsius + " C");
        System.out.println(Temperature.fromCelsius(37).celsius + " C");
    }
}
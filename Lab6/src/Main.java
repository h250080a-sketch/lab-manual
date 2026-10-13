import java.util.*;
public class Main {
    //  QUESTION 1
abstract static class Shape {
    abstract double area();
    abstract double perimeter();
}
static class Circle extends Shape {
    double r;
    Circle(double r) {
        this.r = r;
    }
    double area() {
        return Math.PI * r * r;
    }
    double perimeter() {
        return 2 * Math.PI * r;
    }
}
static class Rect extends Shape {
    double w, h;
    Rect(double w, double h) {
        this.w = w;
        this.h = h;
    }
    double area() {
        return w * h;
    }
    double perimeter() {
        return 2 * (w + h);
    }
}
static class Square extends Rect {
    Square(double s) {
        super(s, s);
    }
}
//QUESTION 2
interface Flyable {
    void fly();
}
static class Bird implements Flyable {
    public void fly() {
        System.out.println("Bird flaps its wings");
    }
}
static class Aeroplane implements Flyable {
    public void fly() {
        System.out.println("Aeroplane uses jet engines");
    }
}
//QUESTION 3
abstract static class Account {
    protected double balance;
    Account(double b) {
        balance = b;
    }
    void deposit(double amt) {
        balance += amt;
    }
    abstract double monthlyFee();
}
static class SavingsAccount extends Account {
    SavingsAccount(double b) {
        super(b);
    }
    double monthlyFee() {
        return 0;
    }
}
static class CurrentAccount extends Account {
    CurrentAccount(double b) {
        super(b);
    }
    double monthlyFee() {
        return 5.0;
    }
}
//QUESTION 4

static class Student implements Comparable<Student> {
    String name;
    int marks;
    Student(String n, int m) {
        name = n;
        marks = m;
    }
    public int compareTo(Student o) {
        return name.compareTo(o.name);
    }
    public String toString() {
        return name + "(" + marks + ")";
    }
}
//QUESTION 5
interface A {
    default void hello() {
        System.out.println("Hello from A");
    }
}
interface B {
    default void hello() {
        System.out.println("Hello from B");
    }
}
static class Both implements A, B {
    public void hello() {
        A.super.hello();
        B.super.hello();
    }
}
    public static void main(String[] args) {
        Shape[] shapes = { new Circle(2), new Rect(3, 4), new Square(5) };
        for (Shape s : shapes) System.out.printf("%s area=%.2f perimeter=%.2f%n", s.getClass().getSimpleName(), s.area(), s.perimeter());

        Flyable[] fl = { new Bird(), new Aeroplane() };
        for (Flyable f : fl) f.fly();

        Account[] accts = { new SavingsAccount(1000), new CurrentAccount(1000) };
        for (Account a : accts) { a.deposit(200); System.out.println(a.getClass().getSimpleName() + " balance=" + a.balance + " fee=" + a.monthlyFee()); }

        List<Student> list = new ArrayList<>(List.of(new Student("Tino", 90), new Student("Amai", 60), new Student("Rudo", 75)));
        Collections.sort(list);                                   // natural order: name (Comparable)
        System.out.println("By name: " + list);
        list.sort(Comparator.comparingInt((Student s) -> s.marks).reversed());   // Comparator: marks desc
        System.out.println("By marks desc: " + list);

        new Both().hello();
    }
}
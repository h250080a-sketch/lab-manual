import java.util.*;
public class Main {
static class Student {
    private String name;
    private int marks;

    Student(String name, int marks) {
        this.name = name;
        setMarks(marks);
    }

    String getName() {
        return name;
    }
    int getMarks() {
        return marks;
    }
    void setMarks(int m) {
        if (m < 0 || m > 100) throw new IllegalArgumentException("marks must be 0-100, got " + m); marks = m;
    }
    String getGrade() {
        return marks >= 75 ? "A" : marks >= 60 ? "B" : marks >= 50 ? "C" : "F";
    }
}

static class BankAccount {
    private final String id;
    private double balance;
    private final List<String> history = new ArrayList<>();

    BankAccount(String id, double opening) {
        this.id = id;
        balance = opening;
        history.add("OPEN " + opening);
    }
    void deposit(double amt) {
        if (amt <= 0) throw new IllegalArgumentException("amount must be positive"); balance += amt; history.add("DEPOSIT " + amt);
    }
    boolean withdraw(double amt) {
        if (amt <= 0 || amt > balance) { history.add("FAILED WITHDRAW " + amt); return false;
        }
        balance -= amt;
        history.add("WITHDRAW " + amt);
        return true;
    }
    double getBalance() {
        return balance;
    }
    List<String> getHistory() {
        return Collections.unmodifiableList(new ArrayList<>(history));
    }
}
static final class ImmutablePerson {
    private final String name;
    private final int age;
    ImmutablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }
    String getName() {
        return name;
    } int getAge() {
        return age;
    }
    ImmutablePerson withAge(int newAge) {
        return new ImmutablePerson(name, newAge);
    }
}
static class KelvinTemp {
    private double kelvin;
    KelvinTemp(double k) {
        setKelvin(k);
    }
    void setKelvin(double k) {
        if (k < 0) throw new IllegalArgumentException("below absolute zero: " + k);
        kelvin = k;
    }
    double getCelsius() {
        return kelvin - 273.15;
    }
    double getFahrenheit() {
        return getCelsius() * 9 / 5 + 32;
    }
    public static void main(String[] args) {
            Student s = new Student("Tanaka", 78);
            System.out.println(s.getName() + " " + s.getMarks() + " grade " + s.getGrade());
            try { s.setMarks(150); } catch (IllegalArgumentException e) { System.out.println("Rejected: " + e.getMessage()); }
            try { new Student("X", -5); } catch (IllegalArgumentException e) { System.out.println("Rejected in constructor: " + e.getMessage()); }

            BankAccount acc = new BankAccount("ACC-1", 100);
            acc.deposit(50); acc.withdraw(30); acc.withdraw(500);
            List<String> h = acc.getHistory();
            h.forEach(System.out::println);
            try { h.add("hack"); } catch (UnsupportedOperationException e) { System.out.println("History is read-only"); }

            ImmutablePerson p = new ImmutablePerson("Rudo", 20);
            System.out.println(p.getName() + " " + p.getAge() + " -> older: " + p.withAge(21).getAge() + " (original still " + p.getAge() + ")");

            KelvinTemp t = new KelvinTemp(300);
            System.out.printf("%.2f C, %.2f F%n", t.getCelsius(), t.getFahrenheit());
            try { new KelvinTemp(-1); } catch (IllegalArgumentException e) { System.out.println("Rejected: " + e.getMessage()); }
        }
    }
}

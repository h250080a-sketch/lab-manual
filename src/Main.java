import java.util.*;
public class Main {
    //QUESTION 1
static class Person {
    protected String name;
    protected int age;
    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    void display() {
        System.out.println(getClass().getSimpleName() + ": " + name + ", " + age + details()); }
    String details() { return ""; }
}
//QUESTION 2
static class Student extends Person {
    private String course;
    Student(String n, int a, String course) {
        super(n, a);
        this.course = course;
    }
    @Override
    String details() {
        return ", course=" + course;
    }
}
//QUESTION 3
static class Teacher extends Person {
    private String subject;
    Teacher(String n, int a, String subject) {
        super(n, a);
        this.subject = subject;
    }
    @Override
    String details() {
        return ", teaches=" + subject;
    }
}
//QUESTION 4
class Vehicle {
    Vehicle() {
        System.out.println("Vehicle constructor");
    }
}

    public class Car extends Vehicle {
    Car() {
        super();
        System.out.println("Car constructor");
    }
}
static class ElectricCar {
    ElectricCar() {
        super();
        System.out.println("ElectricCar constructor");
    }
}
//QUESTION 5
static class MyStack<T> {
    private final ArrayList<T> items = new ArrayList<>();
    void push(T x) {
        items.add(x);
    }
    T pop() {
        if (items.isEmpty()) throw new RuntimeException("empty stack");
        return items.remove(items.size() - 1);
    }
    T peek() {
        return items.get(items.size() - 1);
    }
    boolean isEmpty() {
        return items.isEmpty();
    }
    int size() {
        return items.size();
    }
}
    public static void main(String[] args) {
        Person p = new Person("Amai", 40); p.display();
        Student s = new Student("Tino", 20, "Software Eng"); s.display();
        Teacher t = new Teacher("Mr Moyo", 45, "Mathematics"); t.display();   // all reuse Person.display()

        System.out.println("-- constructor order --");
        ElectricCar ec = new ElectricCar();

        System.out.println("-- composition stack --");
        MyStack<String> st = new MyStack<>();
        st.push("a"); st.push("b");
        System.out.println(st.pop() + " " + st.peek() + " size=" + st.size());

    }
}

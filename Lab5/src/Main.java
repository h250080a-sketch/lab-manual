class Printer {
    void print(int x) {
        System.out.println("int: " + x);
    }
    void print(double x) {
        System.out.println("double: " + x);
    }
    void print(String x) {
        System.out.println("String: " + x);
    }
    void print(int[] x) {
        System.out.println("int[]: " + java.util.Arrays.toString(x));
    }
}

abstract class Animal {
    abstract String sound();
}
class Cat extends Animal {
    String sound() {
        return "Meow";
    }
}
class Cow extends Animal {
    String sound() {
        return "Moo";
    }
}
class Duck extends Animal {
    String sound() {
        return "Quack";
    }
}

abstract class Employee {
    String name;
    Employee(String n) {
        name = n;
    }
    abstract double pay();
}
class Manager extends Employee {
    double base, bonus;
    Manager(String n, double b, double bo) {
        super(n);
        base = b;
        bonus = bo;
    }
    double pay() {
        return base + bonus;
    }
}
class Developer extends Employee {
    double base;
    Developer(String n, double b) {
        super(n);
        base = b;
    } double pay() {
        return base;
    }
}
class Intern extends Employee {
    double stipend;
    Intern(String n, double s) {
        super(n);
        stipend = s;
    } double pay() {
        return stipend;
    }
}

class Parent {
    String name = "Parent-field";
    String who() {
        return "Parent-method";
    }
    static String stat() {
        return "Parent-static";
    }
}
class Child extends Parent {
    String name = "Child-field";
    @Override
    String who() {
        return "Child-method";
    }
    static String stat() {
        return "Child-static";
    }
}

abstract class Payment {
    double amount;
    Payment(double a) {
        amount = a;
    }
    abstract String process();
}
class CardPayment extends Payment {
    CardPayment(double a) { super(a);
    }
    String process() {
        return "Charged card $" + amount;
    }
}
class MobileMoneyPayment extends Payment {
    MobileMoneyPayment(double a) {
        super(a);
    }
    String process() {
        return "Sent mobile money $" + amount;
    }
}
class PaymentProcessor {
    static String payV1(Payment p) {
        if (p instanceof CardPayment c) return "V1 card " + c.amount;
        else if (p instanceof MobileMoneyPayment m) return "V1 mobile " + m.amount;
        return "V1 unknown";
    }

    static String payV2(Payment p) {
        return "V2 " + p.process();
    }
}
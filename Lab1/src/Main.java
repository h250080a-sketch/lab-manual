
import java.time.Year;
public class Main {
    static class Car {
        String brand;
        int year;
        int mileage;

        Car(String b, int y, int m) {
            brand = b;
            year = y;
            mileage = m;
        }

        void display() {
            System.out.println(brand + " (" + year + "), " + mileage + " km");
        }

        boolean isAntique() {
            return Year.now().getValue() - year > 25;
        }
    }

    static class Rectangle {
        double width, height;

        Rectangle(double w, double h) {
            width = w;
            height = h;
        }

        double area() {
            return width * height;
        }

        double perimeter() {
            return 2 * (width + height);
        }
    }

    static class Address {
        String city;

        Address(String c) {
            city = c;
        }
    }

    static class Person {
        String name;
        Address address;

        Person(String n, Address a) {
            name = n;
            address = a;
        }
    }

    public static void main(String[] args) {
        // Ex 1 & 2: Car
        Car[] cars = {new Car("Toyota", 2018, 85000), new Car("Ford", 1995, 210000), new Car("Honda", 2005, 150000)};
        for (Car c : cars) {
            c.display();
            System.out.println("  antique? " + c.isAntique());
        }

        // Ex 3: Rectangles, largest area
        Rectangle[] rs = {new Rectangle(2, 3), new Rectangle(5, 4), new Rectangle(1, 9), new Rectangle(6, 6), new Rectangle(3, 3)};
        Rectangle largest = rs[0];
        for (Rectangle r : rs) if (r.area() > largest.area()) largest = r;
        System.out.println("Largest: " + largest.width + "x" + largest.height + " area=" + largest.area() + " perimeter=" + largest.perimeter());

        // Ex 4: aliasing
        Rectangle r1 = new Rectangle(10, 5);
        Rectangle r2 = r1;      // copies the reference, not the object
        r2.width = 99;
        System.out.println("r1.width = " + r1.width);   // 99

        // Ex 5: Person has Address, chained dots, null
        Person p = new Person("Tanaka", new Address("Harare"));
        System.out.println("City: " + p.address.city);
        Person q = new Person("Ruva", null);
        try {
            System.out.println(q.address.city);
        } catch (NullPointerException e) {
            System.out.println("NPE: address is null");
        }
        System.out.println("Safe: " + (q.address != null ? q.address.city : "unknown"));
    }
}

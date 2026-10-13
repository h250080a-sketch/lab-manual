public class Lab5 {
    public static void main(String[] args) {
        // Ex1 overloading
        Printer pr = new Printer();
        pr.print(5); pr.print(2.5); pr.print("hi"); pr.print(new int[]{1, 2, 3});

        // Ex2 animals
        Animal[] zoo = { new Cat(), new Cow(), new Duck() };
        for (Animal a : zoo) System.out.println(a.getClass().getSimpleName() + ": " + a.sound());

        // Ex3 payroll with Intern, loop unchanged
        Employee[] staff = { new Manager("Amai", 3000, 500), new Developer("Tino", 2000), new Intern("Rudo", 400) };
        double total = 0;
        for (Employee e : staff) total += e.pay();   // loop never mentions subclasses
        System.out.println("Total payroll: " + total);

        // Ex4 fields vs methods
        Parent ref = new Child();
        System.out.println(ref.name + " | " + ref.who() + " | " + ref.stat());
        System.out.println(((Child) ref).name);

        // Ex5 payments
        Payment[] pays = { new CardPayment(100), new MobileMoneyPayment(50) };
        for (Payment p : pays) { System.out.println(PaymentProcessor.payV1(p)); System.out.println(PaymentProcessor.payV2(p)); }
    }
}

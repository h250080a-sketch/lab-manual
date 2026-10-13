import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Ex1: Scanner division
        Scanner in = new Scanner(System.in);
        try {
            System.out.print("First integer: ");
            int a = Integer.parseInt(in.nextLine().trim());
            System.out.print("Second integer: ");
            int b = Integer.parseInt(in.nextLine().trim());
            System.out.println("Result: " + (a / b));
        } catch (NumberFormatException e) {
            System.out.println("Not a valid integer: " + e.getMessage());
        }
        catch (ArithmeticException e)   {
            System.out.println("Cannot divide by zero");
        }

        // Ex2: array index
        int[] arr = {1, 2, 3};
        try {
            System.out.println(arr[5]);
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Bad index: " + e.getMessage());
        }

        // Ex3: checked exception
        for (int age : new int[]{25, 130}) {
            try {
                validateAge(age);
                System.out.println(age + " ok");
            }
            catch (InvalidAgeException e) {
                System.out.println("Caught: " + e.getMessage());
            }
        }

        // Ex4: withdraw throws
        Account acc = new Account(100);
        try {
            acc.withdraw(40);
            acc.withdraw(100);
        }
        catch (InsufficientFundsException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        // Ex5: finally
        System.out.println("returned " + test());
    }
    static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 120) throw new InvalidAgeException("Age out of range 0-120: " + age);
    }
    @SuppressWarnings("finally")
    static int test() {
        try {
            System.out.println("in try");
            return 1;
        }
        finally {
            System.out.println("in finally (runs BEFORE the method actually returns)");
        }
    }
}
class InvalidAgeException extends Exception {
    InvalidAgeException(String m) {
        super(m);
    }
}
class InsufficientFundsException extends Exception {
    InsufficientFundsException(double needed, double available) {
        super("Need " + needed + " but only " + available + " available");
    }
}
class Account {
    private double balance;
    Account(double b) {
        balance = b;
    }
    void withdraw(double amt) throws InsufficientFundsException {
        if (amt > balance) throw new InsufficientFundsException(amt, balance);
        balance -= amt;
        System.out.println("Withdrew " + amt + ", balance " + balance);
    }
}

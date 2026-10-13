import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Ex1 enum + switch
        for (Level l : Level.values()) {
            switch (l) {
                case LOW -> System.out.println(l + ": relax, nothing to do");
                case MEDIUM -> System.out.println(l + ": keep an eye on it");
                case HIGH -> System.out.println(l + ": act now");
            }
        }
        // Ex2 record
        StudentRec a = new StudentRec("Tino", 20), b = new StudentRec("Tino", 20);
        System.out.println(a + " equal=" + a.equals(b) + " name=" + a.name());

        // Ex3 equals/hashCode
        Set<Book> books = new HashSet<>();
        books.add(new Book("111", "Java"));
        System.out.println("added duplicate? " + books.add(new Book("111", "Java (2nd copy)")) + " size=" + books.size());

        // Ex4 LinkedList with nested Node and inner Iterator
        MyList<String> list = new MyList<>();
        list.add("one"); list.add("two"); list.add("three");
        for (String s : list) System.out.println(s);

        // Ex5 coins
        System.out.println(Coin.fewest(68));
    }
}
enum Level { LOW, MEDIUM, HIGH }
record StudentRec(String name, int age) {}
class Book {
    private final String isbn, title;
    Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
    }
    @Override
    public boolean equals(Object o) {
        return o instanceof Book b && isbn.equals(b.isbn);
    }
    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
    @Override
    public String toString() {
        return title + " [" + isbn + "]";
    }
}
class MyList<T> implements Iterable<T> {
    private static class Node<T> {
        T value;
        Node<T> next;
        Node(T v) { value = v;
        }
    }
    private Node<T> head, tail;
    void add(T v) {
        Node<T> n = new Node<>(v);
        if (head == null) head = tail = n;
        else {
            tail.next = n;
            tail = n;
        }
    }
    @Override public Iterator<T> iterator() { return new ListIterator(); }
    private class ListIterator implements Iterator<T> {
        private Node<T> cur = head;
        public boolean hasNext() {
            return cur != null;
        }
        public T next() {
            if (cur == null) throw new NoSuchElementException();
            T v = cur.value;
            cur = cur.next;
            return v;
        }
    }
}
enum Coin {
    PENNY(1), NICKEL(5), DIME(10), QUARTER(25);
    private final int value;
    Coin(int v) { value = v; }
    static Map<Coin, Integer> fewest(int amount) {
        Map<Coin, Integer> result = new LinkedHashMap<>();
        Coin[] all = values();
        for (int i = all.length - 1; i >= 0; i--) {
            int n = amount / all[i].value;
            if (n > 0) {
                result.put(all[i], n);
                amount %= all[i].value;
            }
        }
        return result;
    }
}

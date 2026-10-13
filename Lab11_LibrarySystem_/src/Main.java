import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Path dir = Paths.get(args.length > 0 ? args[0] : "library-data");
        Library lib = new Library();
        try { lib.load(dir); System.out.println("Loaded data from " + dir.toAbsolutePath()); }
        catch (IOException e) { System.out.println("Could not load data: " + e.getMessage()); }

        Scanner in = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n1 Add item  2 Register member  3 Borrow  4 Return  5 List overdue  6 Search by title  0 Save & exit");
            System.out.print("Choice: ");
            if (!in.hasNextLine()) break;
            String choice = in.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> {
                        String type = ask(in, "Type (BOOK/DVD): "), id = ask(in, "Item id: "), title = ask(in, "Title: ");
                        lib.addItem(ItemFactory.create(type, id, title));
                        System.out.println("Added.");
                    }
                    case "2" -> { lib.addMember(new Member(ask(in, "Member id: "), ask(in, "Name: "))); System.out.println("Registered."); }
                    case "3" -> { lib.borrow(ask(in, "Member id: "), ask(in, "Item id: ")); System.out.println("Borrowed."); }
                    case "4" -> { lib.giveBack(ask(in, "Member id: "), ask(in, "Item id: ")); System.out.println("Returned."); }
                    case "5" -> {
                        List<Loan> overdue = lib.overdueLoans();
                        if (overdue.isEmpty()) System.out.println("No overdue items.");
                        overdue.forEach(System.out::println);
                    }
                    case "6" -> {
                        List<LibraryItem> found = lib.searchByTitle(ask(in, "Search text: "));
                        if (found.isEmpty()) System.out.println("No matches.");
                        found.forEach(System.out::println);
                    }
                    case "0" -> running = false;
                    default -> System.out.println("Unknown option.");
                }
            } catch (ItemNotAvailableException | MemberLimitExceededException e) {
                System.out.println("Cannot borrow: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        try { lib.save(dir); System.out.println("Saved to " + dir.toAbsolutePath()); }
        catch (IOException e) { System.out.println("Could not save: " + e.getMessage()); }
    }
    private static String ask(Scanner in, String prompt) { System.out.print(prompt); return in.hasNextLine() ? in.nextLine().trim() : ""; }
}

// ---------- exceptions ----------
class ItemNotAvailableException extends Exception { ItemNotAvailableException(String m) { super(m); } }
class MemberLimitExceededException extends Exception { MemberLimitExceededException(String m) { super(m); } }

// ---------- interface + abstract class (abstraction) ----------
interface Borrowable {
    void borrow(Member m) throws ItemNotAvailableException, MemberLimitExceededException;
    void giveBack();
}

abstract class LibraryItem implements Borrowable {
    private final String id;
    private final String title;
    private boolean available = true;

    protected LibraryItem(String id, String title) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("item id is required");
        if (title == null || title.isBlank()) throw new IllegalArgumentException("title is required");
        this.id = id.trim();
        this.title = title.trim().replace(",", " ");   // commas would break our CSV format
    }
    public abstract int loanPeriodDays();               // each subclass decides (polymorphism)
    public abstract String typeName();

    @Override public void borrow(Member m) throws ItemNotAvailableException, MemberLimitExceededException {
        if (!available) throw new ItemNotAvailableException("'" + title + "' is already on loan");
        if (m.getLoans().size() >= Member.MAX_LOANS) throw new MemberLimitExceededException(m.getName() + " already has " + Member.MAX_LOANS + " loans");
        available = false;
    }
    @Override public void giveBack() { available = true; }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public boolean isAvailable() { return available; }
    void setAvailable(boolean available) { this.available = available; }   // package-private: used when reloading from file

    @Override public String toString() { return typeName() + " " + id + ": " + title + " (" + (available ? "available" : "on loan") + ", " + loanPeriodDays() + " days)"; }
}
class Book extends LibraryItem {
    Book(String id, String title) { super(id, title); }
    @Override public int loanPeriodDays() { return 14; }
    @Override public String typeName() { return "BOOK"; }
}
class Dvd extends LibraryItem {
    Dvd(String id, String title) { super(id, title); }
    @Override public int loanPeriodDays() { return 7; }
    @Override public String typeName() { return "DVD"; }
}

// ---------- Factory pattern ----------
class ItemFactory {
    static LibraryItem create(String type, String id, String title) {
        return switch (type.trim().toUpperCase()) {
            case "BOOK" -> new Book(id, title);
            case "DVD" -> new Dvd(id, title);
            default -> throw new IllegalArgumentException("unknown item type: " + type);
        };
    }
}

// ---------- Member and Loan ----------
class Member {
    static final int MAX_LOANS = 3;
    private final String id, name;
    private final List<Loan> loans = new ArrayList<>();

    Member(String id, String name) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("member id is required");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("member name is required");
        this.id = id.trim(); this.name = name.trim().replace(",", " ");
    }
    String getId() { return id; }
    String getName() { return name; }
    List<Loan> getLoans() { return Collections.unmodifiableList(loans); }   // no leaking of internal list
    void addLoan(Loan l) { loans.add(l); }
    void removeLoan(Loan l) { loans.remove(l); }
    @Override public String toString() { return "Member " + id + ": " + name + " (" + loans.size() + " loans)"; }
}
class Loan {
    private final Member member;
    private final LibraryItem item;
    private final LocalDate borrowedOn;
    Loan(Member member, LibraryItem item, LocalDate borrowedOn) { this.member = member; this.item = item; this.borrowedOn = borrowedOn; }
    Member getMember() { return member; }
    LibraryItem getItem() { return item; }
    LocalDate getBorrowedOn() { return borrowedOn; }
    LocalDate getDueDate() { return borrowedOn.plusDays(item.loanPeriodDays()); }
    boolean isOverdue(LocalDate today) { return today.isAfter(getDueDate()); }
    @Override public String toString() { return "Due " + getDueDate() + " | " + member.getName() + " has '" + item.getTitle() + "'"; }
}

// ---------- core service ----------
class Library {
    private final Map<String, LibraryItem> items = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();

    void addItem(LibraryItem i) {
        if (items.containsKey(i.getId())) throw new IllegalArgumentException("item id already exists: " + i.getId());
        items.put(i.getId(), i);
    }
    void addMember(Member m) {
        if (members.containsKey(m.getId())) throw new IllegalArgumentException("member id already exists: " + m.getId());
        members.put(m.getId(), m);
    }
    private Member member(String id) { Member m = members.get(id); if (m == null) throw new IllegalArgumentException("no such member: " + id); return m; }
    private LibraryItem item(String id) { LibraryItem i = items.get(id); if (i == null) throw new IllegalArgumentException("no such item: " + id); return i; }

    void borrow(String memberId, String itemId) throws ItemNotAvailableException, MemberLimitExceededException { borrow(memberId, itemId, LocalDate.now()); }
    void borrow(String memberId, String itemId, LocalDate when) throws ItemNotAvailableException, MemberLimitExceededException {
        Member m = member(memberId); LibraryItem i = item(itemId);   // 1. look up (throws IllegalArgumentException if missing)
        i.borrow(m);                                                  // 2. checks availability + limit, marks unavailable
        m.addLoan(new Loan(m, i, when));                              // 3. record the loan
    }
    void giveBack(String memberId, String itemId) {
        Member m = member(memberId);
        Loan loan = m.getLoans().stream().filter(l -> l.getItem().getId().equals(itemId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(m.getName() + " does not have item " + itemId));
        m.removeLoan(loan);
        loan.getItem().giveBack();
    }
    List<Loan> overdueLoans() {
        LocalDate today = LocalDate.now();
        return members.values().stream().flatMap(m -> m.getLoans().stream())
                .filter(l -> l.isOverdue(today)).sorted(Comparator.comparing(Loan::getDueDate)).toList();
    }
    List<LibraryItem> searchByTitle(String text) {
        return items.values().stream()
                .filter(i -> i.getTitle().toLowerCase().contains(text.toLowerCase()))
                .sorted(Comparator.comparing(LibraryItem::getTitle)).toList();
    }

    // ---------- CSV persistence (Lab 10) ----------
    void save(Path dir) throws IOException {
        Files.createDirectories(dir);
        Files.write(dir.resolve("items.csv"), items.values().stream().map(i -> i.typeName() + "," + i.getId() + "," + i.getTitle()).sorted().toList());
        Files.write(dir.resolve("members.csv"), members.values().stream().map(m -> m.getId() + "," + m.getName()).sorted().toList());
        Files.write(dir.resolve("loans.csv"), members.values().stream().flatMap(m -> m.getLoans().stream())
                .map(l -> l.getMember().getId() + "," + l.getItem().getId() + "," + l.getBorrowedOn()).sorted().toList());
    }
    void load(Path dir) throws IOException {
        if (!Files.exists(dir.resolve("items.csv"))) return;   // first run: nothing to load
        for (String line : Files.readAllLines(dir.resolve("items.csv"))) {
            if (line.isBlank()) continue;
            String[] p = line.split(",", -1); addItem(ItemFactory.create(p[0], p[1], p[2]));
        }
        for (String line : Files.readAllLines(dir.resolve("members.csv"))) {
            if (line.isBlank()) continue;
            String[] p = line.split(",", -1); addMember(new Member(p[0], p[1]));
        }
        for (String line : Files.readAllLines(dir.resolve("loans.csv"))) {
            if (line.isBlank()) continue;
            String[] p = line.split(",", -1);
            Member m = member(p[0]); LibraryItem i = item(p[1]);
            i.setAvailable(false); m.addLoan(new Loan(m, i, LocalDate.parse(p[2])));
        }
    }
}

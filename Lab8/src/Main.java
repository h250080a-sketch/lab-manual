import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Ex1
        List<Integer> nums = new ArrayList<>(List.of(4, 9, 1, 7, 3, 8, 2, 10, 6, 5));
        int sum = 0, max = nums.get(0);
        for (int n : nums) {
            sum += n;
            if (n > max) max = n;
        }
        List<Integer> rev = new ArrayList<>(nums); 
        Collections.reverse(rev);
        System.out.println("sum=" + sum + " max=" + max + " reversed=" + rev);

        // Ex2
        List<String> names = List.of("Amai", "Tino", "Amai", "Rudo", "Tino", "Farai");
        Set<String> unique = new LinkedHashSet<>(names);
        System.out.println("unique=" + unique);

        // Ex3
        Stack2<String> st = new Stack2<>();
        st.push("x"); st.push("y");
        System.out.println(st.peek() + " " + st.pop() + " " + st.pop() + " empty=" + st.isEmpty());

        // Ex4
        String text = "the cat and the dog and the bird";
        Map<String, Integer> freq = new HashMap<>();
        for (String w : text.toLowerCase().split("\\s+")) freq.merge(w, 1, Integer::sum);
        freq.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> System.out.println(e.getKey() + " = " + e.getValue()));

        // Ex5
        Integer[] a = {5, 2, 9, 1}; String[] s = {"pear", "apple", "fig"};
        bubbleSort(a); bubbleSort(s);
        System.out.println(Arrays.toString(a) + " " + Arrays.toString(s));
    }
    static <T extends Comparable<T>> void bubbleSort(T[] arr) {
        for (int i = 0; i < arr.length - 1; i++)
            for (int j = 0; j < arr.length - 1 - i; j++)
                if (arr[j].compareTo(arr[j + 1]) > 0) { T t = arr[j]; arr[j] = arr[j + 1]; arr[j + 1] = t; }
    }
}
class Stack2<T> {
    private final List<T> items = new ArrayList<>();
    void push(T x) {
        items.add(x);
    }
    T pop() {
        if (isEmpty()) throw new RuntimeException("stack empty"); return items.remove(items.size() - 1);
    }
    T peek() {
        if (isEmpty()) throw new RuntimeException("stack empty"); return items.get(items.size() - 1);
    }
    boolean isEmpty() {
        return items.isEmpty();
    }
}

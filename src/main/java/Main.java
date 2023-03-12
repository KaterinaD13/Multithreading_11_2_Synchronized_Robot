import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final int THREADS = 1000;
    private static final char TURN_RIGHT = 'R';
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> list = new ArrayList<>();
        List<Thread> list2 = Collections.synchronizedList(list);
        for (int i = 0; i < THREADS; i++) {
            list2.add(new Thread(() -> countTheNumberOfRightTurnCommands(generateRoute("RLRFR", 100))));
        }
        for (Thread thread : list2) {
            thread.start();
            thread.join();
        }
        messageOutput();
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    private static void countTheNumberOfRightTurnCommands(String path) {
        int count = (int) path.chars().filter(r -> r == TURN_RIGHT).count();
        synchronized (sizeToFreq) {
            sizeToFreq.merge(count, 1, Integer::sum);
        }
    }

    private static void messageOutput() {
        LinkedList<Map.Entry<Integer, Integer>> sorted = getSortedFromMap();
        Map.Entry<Integer, Integer> firstEntry = sorted.pop();
        System.out.printf("Самое частое количество повторений %d (встретилось %d раз)\n", firstEntry.getKey(), firstEntry.getValue());
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> current : sorted) {
            System.out.printf(" - %d (%d раз)\n", current.getKey(), current.getValue());
        }
    }

    private static LinkedList<Map.Entry<Integer, Integer>> getSortedFromMap() {
        return sizeToFreq
                .entrySet()
                .stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
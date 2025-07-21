package kevinBacon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KevinBacon {

    private Map<String, Set<Pair>> graph;
    private Map<String, Integer> baconNumbers;
    private Map<String, String> prevMovie;

    public KevinBacon() {
        graph = new HashMap<>();
        baconNumbers = new HashMap<>();
        prevMovie = new HashMap<>();
    }

    public void buildGraph(String filePath) throws IOException {
        System.out.println("Reading file: " + filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("Processing line: " + line);
                String[] parts = line.split("/");
                String movie = parts[0];
                for (int i = 1; i < parts.length; i++) {
                    String actor1 = parts[i].trim();
                    for (int j = i + 1; j < parts.length; j++) {
                        String actor2 = parts[j].trim();
                        addEdge(actor1, actor2, movie);
                        addEdge(actor2, actor1, movie);
                    }
                }
            }
        }

        System.out.println("\nGraph content:");
        for (String actor : graph.keySet()) {
            System.out.println(actor + " -> " + graph.get(actor));
        }
    }

    private void addEdge(String actor1, String actor2, String movie) {
        graph.computeIfAbsent(actor1, k -> new HashSet<>()).add(new Pair(actor2, movie));
        graph.computeIfAbsent(actor2, k -> new HashSet<>()).add(new Pair(actor1, movie));
    }

    public void computeBaconNumbers(String startActor) {
        if (!graph.containsKey(startActor)) {
            System.out.println("Error: Start actor " + startActor + " not found in the graph.");
            return;
        }

        Queue<String> queue = new LinkedList<>();
        queue.add(startActor);
        baconNumbers.put(startActor, 0);

        System.out.println("\nStarting BFS from: " + startActor);
        while (!queue.isEmpty()) {
            String currentActor = queue.poll();
            System.out.println("Visiting: " + currentActor);
            int currentNumber = baconNumbers.get(currentActor);
            for (Pair neighbor : graph.getOrDefault(currentActor, new HashSet<>())) {
                if (!baconNumbers.containsKey(neighbor.actor)) {
                    baconNumbers.put(neighbor.actor, currentNumber + 1);
                    prevMovie.put(neighbor.actor, neighbor.movie);
                    queue.add(neighbor.actor);
                }
            }
        }
    }

    public void printBaconDistribution() {
        Map<Integer, Integer> distribution = new HashMap<>();
        for (int number : baconNumbers.values()) {
            distribution.put(number, distribution.getOrDefault(number, 0) + 1);
        }
        int infinityCount = 0;

        for (String actor : graph.keySet()) {
            if (!baconNumbers.containsKey(actor)) {
                infinityCount++;
            }
        }

        System.out.println("\nBacon number Frequency");
        System.out.println("-------------------------");
        for (int number : new TreeSet<>(distribution.keySet())) {
            System.out.println(number + "\t" + distribution.get(number));
        }
        System.out.println("infinity\t" + infinityCount);
    }

    public void printActorChain(String actor) {
        if (!graph.containsKey(actor)) {
            System.out.println("Error: Actor " + actor + " not found in the graph.");
            return;
        }
        if (!baconNumbers.containsKey(actor)) {
            System.out.println(actor + " has a Bacon number of infinity");
            return;
        }
        int number = baconNumbers.get(actor);
        System.out.println(actor + " has a Bacon number of " + number);
        if (number == 0) {
            System.out.println(actor + " is Kevin Bacon!");
            return;
        }

        List<String> path = new ArrayList<>();
        String currentActor = actor;
        while (!currentActor.equals("Bacon, Kevin")) {
            for (Pair neighbor : graph.get(currentActor)) {
                if (baconNumbers.containsKey(neighbor.actor) &&
                    baconNumbers.get(neighbor.actor) == baconNumbers.get(currentActor) - 1) {
                    path.add(currentActor + " was in \"" + neighbor.movie + "\" with " + neighbor.actor);
                    currentActor = neighbor.actor;
                    break;
                }
            }
        }
        Collections.reverse(path);
        for (String step : path) {
            System.out.println(step);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java KevinBacon <movie_file>");
            return;
        }
        String movieFile = args[0];
        KevinBacon kb = new KevinBacon();
        try {
            kb.buildGraph(movieFile);
            kb.computeBaconNumbers("Bacon, Kevin");
            kb.printBaconDistribution();

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter actor's name (or 'E' to exit): ");
            while (scanner.hasNextLine()) {
                String actor = scanner.nextLine().trim();
                if (actor.equalsIgnoreCase("E")) {
                    break;
                }
                kb.printActorChain(actor);
                System.out.print("Enter actor's name (or 'E' to exit): ");
            }
            scanner.close();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    private static class Pair {
        String actor;
        String movie;

        Pair(String actor, String movie) {
            this.actor = actor;
            this.movie = movie;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return Objects.equals(actor, pair.actor) && Objects.equals(movie, pair.movie);
        }

        @Override
        public int hashCode() {
            return Objects.hash(actor, movie);
        }

        @Override
        public String toString() {
            return "(" + actor + ", " + movie + ")";
        }
    }
}

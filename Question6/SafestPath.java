import java.util.*;

public class SafestPath {

    static class Edge {
        int to;
        double probability;

        Edge(int t, double p) {
            to = t;
            probability = p;
        }
    }

    public static double[] safestPath(List<Edge>[] graph, int source) {
        int n = graph.length;
        double[] dist = new double[n];
        Arrays.fill(dist, Double.MAX_VALUE);

        PriorityQueue<Integer> pq = new PriorityQueue<>(
                (a, b) -> Double.compare(dist[a], dist[b]));

        dist[source] = 0;
        pq.add(source);

        while (!pq.isEmpty()) {
            int u = pq.poll();

            for (Edge e : graph[u]) {
                double weight = -Math.log(e.probability);
                if (dist[u] + weight < dist[e.to]) {
                    dist[e.to] = dist[u] + weight;
                    pq.add(e.to);
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {

        int n = 5; // 0=KTM, 1=JA, 2=JB, 3=PH, 4=BS
        List<Edge>[] graph = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        graph[0].add(new Edge(1, 0.9)); // KTM -> JA
        graph[0].add(new Edge(2, 0.8)); // KTM -> JB
        graph[1].add(new Edge(3, 0.95)); // JA -> PH
        graph[2].add(new Edge(3, 0.85)); // JB -> PH
        graph[1].add(new Edge(4, 0.7)); // JA -> BS
        graph[2].add(new Edge(4, 0.9)); // JB -> BS

        double[] result = safestPath(graph, 0);

        System.out.println("Safest probabilities from KTM:");

        for (int i = 0; i < n; i++) {
            double probability = Math.exp(-result[i]);
            System.out.println("To node " + i + " : " + probability);
        }
    }
}
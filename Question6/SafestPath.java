package Question6;

import java.util.*;

class Edge {
    String to;
    double probability;

    Edge(String to, double probability) {
        this.to = to;
        this.probability = probability;
    }
}

class Node implements Comparable<Node> {
    String name;
    double distance;

    Node(String name, double distance) {
        this.name = name;
        this.distance = distance;
    }

    public int compareTo(Node other) {
        return Double.compare(this.distance, other.distance);
    }
}

public class SafestPath {

    public static Map<String, Double> dijkstra(
            Map<String, List<Edge>> graph, String source) {

        Map<String, Double> dist = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();

        for (String node : graph.keySet()) {
            dist.put(node, Double.POSITIVE_INFINITY);
        }

        dist.put(source, 0.0);
        pq.add(new Node(source, 0.0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            for (Edge edge : graph.get(current.name)) {

                double weight = -Math.log(edge.probability);
                double newDist = dist.get(current.name) + weight;

                if (newDist < dist.get(edge.to)) {
                    dist.put(edge.to, newDist);
                    pq.add(new Node(edge.to, newDist));
                }
            }
        }

        return dist;
    }

    public static double getOriginalProbability(double transformedDistance) {
        return Math.exp(-transformedDistance);
    }

    public static void main(String[] args) {

        Map<String, List<Edge>> graph = new HashMap<>();

        graph.put("KTM", new ArrayList<>());
        graph.put("JA", new ArrayList<>());
        graph.put("JB", new ArrayList<>());
        graph.put("PH", new ArrayList<>());
        graph.put("BS", new ArrayList<>());

        graph.get("KTM").add(new Edge("JA", 0.9));
        graph.get("KTM").add(new Edge("JB", 0.8));
        graph.get("JA").add(new Edge("PH", 0.95));
        graph.get("JB").add(new Edge("PH", 0.85));
        graph.get("JA").add(new Edge("BS", 0.7));
        graph.get("JB").add(new Edge("BS", 0.9));

        Map<String, Double> result = dijkstra(graph, "KTM");

        System.out.println("Safest probabilities from KTM:");

        for (String node : result.keySet()) {
            double probability = getOriginalProbability(result.get(node));
            System.out.println("To " + node + " : " + probability);
        }
    }
}
package Question6;

import java.util.*;

public class MaxFlow {

    static class Edge {
        int to, capacity, flow;
        Edge reverse;

        Edge(int to, int capacity) {
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }
    }

    static class Graph {
        List<Edge>[] adj;

        Graph(int n) {
            adj = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                adj[i] = new ArrayList<>();
            }
        }

        void addEdge(int u, int v, int capacity) {
            Edge forward = new Edge(v, capacity);
            Edge backward = new Edge(u, 0);

            forward.reverse = backward;
            backward.reverse = forward;

            adj[u].add(forward);
            adj[v].add(backward);
        }
    }

    public static int edmondsKarp(Graph graph, int source, int sink) {
        int maxFlow = 0;

        while (true) {
            int[] parent = new int[graph.adj.length];
            Edge[] parentEdge = new Edge[graph.adj.length];
            Arrays.fill(parent, -1);

            Queue<Integer> queue = new LinkedList<>();
            queue.add(source);
            parent[source] = source;

            while (!queue.isEmpty() && parent[sink] == -1) {
                int u = queue.poll();
                for (Edge e : graph.adj[u]) {
                    if (parent[e.to] == -1 && e.capacity - e.flow > 0) {
                        parent[e.to] = u;
                        parentEdge[e.to] = e;
                        queue.add(e.to);
                    }
                }
            }

            if (parent[sink] == -1)
                break;

            int pathFlow = Integer.MAX_VALUE;
            int v = sink;

            while (v != source) {
                pathFlow = Math.min(pathFlow,
                        parentEdge[v].capacity - parentEdge[v].flow);
                v = parent[v];
            }

            v = sink;
            while (v != source) {
                parentEdge[v].flow += pathFlow;
                parentEdge[v].reverse.flow -= pathFlow;
                v = parent[v];
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    public static void main(String[] args) {

        Graph graph = new Graph(5);

        graph.addEdge(0, 1, 10); // KTM -> JA
        graph.addEdge(0, 2, 8); // KTM -> JB
        graph.addEdge(1, 4, 5); // JA -> BS
        graph.addEdge(2, 4, 10); // JB -> BS
        graph.addEdge(1, 3, 7); // JA -> PH
        graph.addEdge(2, 3, 5); // JB -> PH

        int maxFlow = edmondsKarp(graph, 0, 4);

        System.out.println("Maximum trucks per hour from KTM to BS: " + maxFlow);
    }
}
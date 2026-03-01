import java.util.*;

public class MaxFlow {

    static int bfs(int[][] capacity, int source, int sink, int[] parent) {
        int n = capacity.length;
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < n; v++) {
                if (!visited[v] && capacity[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }

        return visited[sink] ? 1 : 0;
    }

    static int edmondsKarp(int[][] capacity, int source, int sink) {
        int n = capacity.length;
        int[] parent = new int[n];
        int maxFlow = 0;

        while (bfs(capacity, source, sink, parent) == 1) {

            int pathFlow = Integer.MAX_VALUE;
            int v = sink;

            while (v != source) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacity[u][v]);
                v = u;
            }

            v = sink;
            while (v != source) {
                int u = parent[v];
                capacity[u][v] -= pathFlow;
                capacity[v][u] += pathFlow;
                v = u;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    public static void main(String[] args) {

        int n = 5; // 0=KTM, 1=JA, 2=JB, 3=PH, 4=BS
        int[][] capacity = new int[n][n];

        capacity[0][1] = 10; // KTM -> JA
        capacity[0][2] = 8; // KTM -> JB
        capacity[1][4] = 5; // JA -> BS
        capacity[2][4] = 10; // JB -> BS
        capacity[1][3] = 7; // JA -> PH
        capacity[2][3] = 5; // JB -> PH

        int maxFlow = edmondsKarp(capacity, 0, 4);

        System.out.println("Maximum trucks per hour from KTM to BS: " + maxFlow);
    }
}
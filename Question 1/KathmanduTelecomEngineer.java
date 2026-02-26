import java.util.*;

public class KathmanduTelecomEngineer {

    public int maxPointsOnLine(int[][] points) {
        int n = points.length;
        if (n <= 2)
            return n;

        int maxOverall = 0;

        for (int i = 0; i < n; i++) {

            Map<String, Integer> slopeMap = new HashMap<>();
            int localMax = 0;

            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];

                int common = gcd(Math.abs(dx), Math.abs(dy));
                String slopeKey = (dy / common) + "/" + (dx / common);

                slopeMap.put(slopeKey, slopeMap.getOrDefault(slopeKey, 0) + 1);
                localMax = Math.max(localMax, slopeMap.get(slopeKey));
            }

            maxOverall = Math.max(maxOverall, localMax + 1);
        }
        return maxOverall;
    }

    private int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        KathmanduTelecomEngineer solver = new KathmanduTelecomEngineer();

        int[][] example1 = { { 1, 1 }, { 2, 2 }, { 3, 3 } };
        System.out.println("Example 1 Output: " + solver.maxPointsOnLine(example1));

        int[][] example2 = { { 1, 1 }, { 3, 2 }, { 5, 3 }, { 4, 1 }, { 2, 3 }, { 1, 4 } };
        System.out.println("Example 2 Output: " + solver.maxPointsOnLine(example2));

    }
}
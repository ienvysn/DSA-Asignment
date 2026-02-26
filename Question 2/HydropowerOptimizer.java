// Definition for a hydropower plant node
class PlantNode {
    int val;
    PlantNode left;
    PlantNode right;

    PlantNode(int val) {
        this.val = val;
    }
}

public class HydropowerOptimizer {

    private int maxPower = Integer.MIN_VALUE;

    public int findMaxPower(PlantNode root) {
        calculateMaxAtNode(root);
        return maxPower;
    }

    private int calculateMaxAtNode(PlantNode node) {
        if (node == null)
            return 0;

        // GREEDY APPROACH
        int leftGain = Math.max(calculateMaxAtNode(node.left), 0);
        int rightGain = Math.max(calculateMaxAtNode(node.right), 0);

        int currentPathSum = node.val + leftGain + rightGain;

        maxPower = Math.max(maxPower, currentPathSum);

        return node.val + Math.max(leftGain, rightGain);
    }

    public static void main(String[] args) {
        HydropowerOptimizer optimizer = new HydropowerOptimizer();

        PlantNode root1 = new PlantNode(1);
        root1.left = new PlantNode(2);
        root1.right = new PlantNode(3);
        System.out.println("Example 1 Max Power: " + optimizer.findMaxPower(root1)); // Output: 6

        PlantNode root2 = new PlantNode(-10);
        root2.left = new PlantNode(9);
        root2.right = new PlantNode(20);
        root2.right.left = new PlantNode(15);
        root2.right.right = new PlantNode(7);
        System.out.println("Example 2 Max Power: " + optimizer.findMaxPower(root2)); // Output: 42
    }
}
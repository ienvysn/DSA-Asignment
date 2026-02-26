public class CommodityTrader {

    public int maxProfit(int max_trades, int[] daily_prices) {
        int n = daily_prices.length;
        if (n == 0 || max_trades == 0)
            return 0;

        int[][] dp = new int[max_trades + 1][n];

        for (int k = 1; k <= max_trades; k++) {
            int maxDiff = -daily_prices[0];
            for (int i = 1; i < n; i++) {

                dp[k][i] = Math.max(dp[k][i - 1], daily_prices[i] + maxDiff);

                maxDiff = Math.max(maxDiff, dp[k - 1][i] - daily_prices[i]);
            }
        }
        return dp[max_trades][n - 1];
    }

    public static void main(String[] args) {
        CommodityTrader trader = new CommodityTrader();

        int[] prices1 = { 2000, 4000, 1000 };
        int trades1 = 2;
        System.out.println("Example 1 Max Profit: " + trader.maxProfit(trades1, prices1) + " NPR");

        int[] prices2 = { 1000, 5000, 2000, 8000 };
        int trades2 = 2;
        System.out.println("Example 2 Max Profit: " + trader.maxProfit(trades2, prices2) + " NPR");

    }
}
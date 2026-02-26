import java.util.*;

public class MarketingAgency {
    private Map<String, List<String>> memo = new HashMap<>();

    public List<String> segmentQuery(String user_query, List<String> dictionary) {
        memo.clear();
        Set<String> wordSet = new HashSet<>(dictionary);
        return backtrack(user_query, wordSet);
    }

    private List<String> backtrack(String query, Set<String> wordSet) {

        if (memo.containsKey(query)) {
            return memo.get(query);
        }

        if (query.isEmpty()) {
            return Arrays.asList("");
        }

        List<String> validSentences = new ArrayList<>();

        for (int i = 1; i <= query.length(); i++) {
            String prefix = query.substring(0, i);

            if (wordSet.contains(prefix)) {

                List<String> suffixes = backtrack(query.substring(i), wordSet);

                for (String suffix : suffixes) {
                    validSentences.add(prefix + (suffix.isEmpty() ? "" : " ") + suffix);
                }
            }
        }

        memo.put(query, validSentences);
        return validSentences;
    }

    public static void main(String[] args) {
        MarketingAgency splitter = new MarketingAgency();

        String query1 = "nepaltrekkingguide";
        List<String> dict1 = Arrays.asList("nepal", "trekking", "guide", "nepaltrekking");
        System.out.println("Example 1 Output: " + splitter.segmentQuery(query1, dict1));

        String query2 = "visitkathmandunepal";
        List<String> dict2 = Arrays.asList("visit", "kathmandu", "nepal", "visitkathmandu", "kathmandunepal");
        System.out.println("Example 2 Output: " + splitter.segmentQuery(query2, dict2));

        String query3 = "everesthikingtrail";
        List<String> dict3 = Arrays.asList("everest", "hiking", "trek");
        System.out.println("Example 3 Output: " + splitter.segmentQuery(query3, dict3));

    }
}
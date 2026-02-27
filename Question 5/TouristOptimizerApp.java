import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

// Model for a Tourist Spot [cite: 289]
class TouristSpot {
    String name;
    double lat, lon, fee;
    int open, close;
    List<String> tags;

    TouristSpot(String name, double lat, double lon, double fee, String tags) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.fee = fee;
        this.tags = Arrays.asList(tags.split(";"));
    }
}

public class TouristOptimizerApp extends JFrame {
    private List<TouristSpot> spots = new ArrayList<>();
    private JTextArea resultArea = new JTextArea(10, 30);

    public TouristOptimizerApp() {
        setTitle("Kathmandu Tourist Spot Optimizer");
        setLayout(new FlowLayout());
        setupData();

        JTextField budgetInput = new JTextField(5);
        JTextField timeInput = new JTextField(5);
        JButton planBtn = new JButton("Generate Itinerary");

        add(new JLabel("Budget (Rs):"));
        add(budgetInput);
        add(new JLabel("Time (Hours):"));
        add(timeInput);
        add(planBtn);
        add(new JScrollPane(resultArea));

        planBtn.addActionListener(e -> {
            double budget = Double.parseDouble(budgetInput.getText());
            double time = Double.parseDouble(timeInput.getText());
            runGreedyOptimizer(budget, time);
        });

        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void setupData() {

        spots.add(new TouristSpot("Pashupatinath", 27.7104, 85.3488, 100, "culture;religious"));
        spots.add(new TouristSpot("Swayambhunath", 27.7149, 85.2906, 200, "culture;heritage"));
        spots.add(new TouristSpot("Garden of Dreams", 27.7125, 85.3170, 150, "nature;relaxation"));
    }

    private void runGreedyOptimizer(double budget, double time) {
        double currentBudget = 0;
        double currentTime = 0;
        StringBuilder sb = new StringBuilder("Suggested Itinerary:\n");

        for (TouristSpot s : spots) {

            if (currentBudget + s.fee <= budget && currentTime + 2 <= time) {
                currentBudget += s.fee;
                currentTime += 2;
                sb.append("- ").append(s.name).append(" (Fee: Rs.").append(s.fee).append(")\n");
            }
        }
        sb.append("\nTotal Cost: Rs.").append(currentBudget);
        sb.append("\nTotal Time: ").append(currentTime).append(" hours");
        resultArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        new TouristOptimizerApp();
    }
}
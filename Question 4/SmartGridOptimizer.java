import java.util.*;

class EnergySource {
    String id, type;
    double capacity, cost;
    int startHour, endHour;

    EnergySource(String id, String type, double capacity, int start, int end, double cost) {
        this.id = id;
        this.type = type;
        this.capacity = capacity;
        this.startHour = start;
        this.endHour = end;
        this.cost = cost;
    }
}

public class SmartGridOptimizer {
    public void optimizeLoad(int hour, double[] demands, List<EnergySource> sources) {

        List<EnergySource> available = new ArrayList<>();
        for (EnergySource s : sources) {
            if (hour >= s.startHour && hour <= s.endHour)
                available.add(s);
        }

        available.sort(Comparator.comparingDouble(s -> s.cost));

        double totalDemand = 0;
        for (double d : demands)
            totalDemand += d;

        System.out.println("Hour " + hour + " | Total Demand: " + totalDemand + " kWh");
        double remainingDemand = totalDemand;
        double totalCost = 0;

        for (EnergySource s : available) {
            if (remainingDemand <= 0)
                break;

            double taken = Math.min(s.capacity, remainingDemand);
            remainingDemand -= taken;
            totalCost += taken * s.cost;

            System.out.println("Used " + taken + " kWh from " + s.type + " (Rs. " + s.cost + "/kWh)");
        }

        if (remainingDemand > 0) {
            double tenPercentAllowedMissing = totalDemand * 0.10;
            if (remainingDemand <= tenPercentAllowedMissing) {
                System.out.println("Demand met within 10% tolerance.");
            } else {
                System.out.println("CRITICAL: Demand not met. Shortage: " + remainingDemand + " kWh");
            }
        }
        System.out.println("Total Hourly Cost: Rs. " + totalCost + "\n");
    }

    public static void main(String[] args) {
        SmartGridOptimizer grid = new SmartGridOptimizer();
        List<EnergySource> sources = new ArrayList<>();
        sources.add(new EnergySource("S1", "Solar", 50, 6, 18, 1.0));
        sources.add(new EnergySource("S2", "Hydro", 40, 0, 24, 1.5));
        sources.add(new EnergySource("S3", "Diesel", 60, 17, 23, 3.0));

        double[] demandsH06 = { 20, 15, 25 };
        grid.optimizeLoad(6, demandsH06, sources);
    }
}
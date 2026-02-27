import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherDataCollector extends JFrame {
    private JTextArea weatherDisplay;
    private JButton fetchButton;
    private final String API_KEY = "6b3fa25fefa36278493fade477c55649";

    public WeatherDataCollector() {
        setTitle("Real-Time Weather Collector");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        weatherDisplay = new JTextArea();
        weatherDisplay.setFont(new Font("Monospaced", Font.PLAIN, 12));
        weatherDisplay.setEditable(false);
        fetchButton = new JButton("Fetch Weather Concurrently");

        add(new JScrollPane(weatherDisplay), BorderLayout.CENTER);
        add(fetchButton, BorderLayout.SOUTH);

        fetchButton.addActionListener(e -> startMultiThreadedFetch());
        setVisible(true);
    }

    private void startMultiThreadedFetch() {
        weatherDisplay.setText("Starting Concurrent Fetch...\n");
        String[] cities = { "Kathmandu", "Pokhara", "Biratnagar", "Nepalgunj", "Dhangadhi" };

        long startTime = System.currentTimeMillis();
        // Create 5 threads, one for each city
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (String city : cities) {
            executor.execute(() -> {
                String result = getRealWeather(city);
                SwingUtilities.invokeLater(() -> {
                    weatherDisplay.append(result + "\n------------------\n");
                });
            });
        }

        executor.shutdown();

        new Thread(() -> {
            while (!executor.isTerminated()) {
            }
            long totalTime = System.currentTimeMillis() - startTime;
            SwingUtilities.invokeLater(() -> weatherDisplay.append("\nAll threads finished in: " + totalTime + " ms"));
        }).start();
    }

    private String getRealWeather(String cityName) {
        try {
            // OpenWeather API URL [cite: 376]
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q="
                    + cityName + "&appid=" + API_KEY + "&units=metric";

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String json = response.toString();
            String temp = extractValue(json, "\"temp\":", ",");
            String humidity = extractValue(json, "\"humidity\":", "}");
            String pressure = extractValue(json, "\"pressure\":", ",");

            return String.format("%-12s | Temp: %s°C | Humidity: %s%% | Pressure: %shPa",
                    cityName, temp, humidity, pressure);

        } catch (Exception e) {
            return "Error fetching " + cityName + ": " + e.getMessage();
        }
    }

    private String extractValue(String json, String key, String delimiter) {
        int start = json.indexOf(key) + key.length();
        int end = json.indexOf(delimiter, start);
        return json.substring(start, end).trim();
    }

    public static void main(String[] args) {
        new WeatherDataCollector();
    }
}
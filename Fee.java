import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fee {
    private String feeType;
    private String feeName;
    private double feeAmount;

    // Constructor
    public Fee(String feeType, String feeName, double feeAmount) {
        this.feeType = feeType;
        this.feeName = feeName;
        this.feeAmount = feeAmount;
    }

    // Getters 
    public String getFeeType() {
        return feeType;
    }


    public String getFeeName() {
        return feeName;
    }


    public double getFeeAmount() {
        return feeAmount;
    }



    // Method to read fee information from a file
    public static List<Fee> readFeesFromFile(String fileName) throws IOException {
        List<Fee> fees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Fee fee = new Fee(parts[0], parts[1], Double.parseDouble(parts[2]));
                    fees.add(fee);
                }
            }
        }
        return fees;
    }
}



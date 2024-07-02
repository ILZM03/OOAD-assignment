
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
}



public class OptionalFee extends Fee {
    private boolean isSelected;

    public OptionalFee(String feeType, String feeName, double feeAmount, boolean selected) {
        super(feeType, feeName, feeAmount);
    }

    public boolean isSelected() {

        return isSelected;
    }

     
}

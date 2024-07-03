public class OptionalFee extends Fee {
    private boolean isSelected;

    public OptionalFee(String feeType, String feeName, double feeAmount, boolean selected) {
        super(feeType, feeName, feeAmount);
        this.isSelected = selected;
    }

    public boolean isSelected() {

        return isSelected;
    }

    public void setSelected(boolean selected){
        this.isSelected = selected;
        
    }
     
}

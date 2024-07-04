

public class Discount{
    private String discountText = "discount 10%*";
    private double discountAmount;
    private int courseLevel;
    private double discountedAmount;
    
    public Discount(double discountAmount,int courselevel,double discountedAmount){
        this.discountAmount = discountAmount;
        this.courseLevel = courselevel;
        this.discountedAmount = discountedAmount;
    }

    //getters
    public String getDiscountText(){
        return discountText;
    }

    public double getDiscountAmount(){
        return discountAmount;
    }

    public double getdiscountedAmount(){
        return discountedAmount;
    }

    public int getCourseLevel(){
        return courseLevel;
    }   

    public double getDiscountedAmount(){
        return discountedAmount;
    }

    public static double calculateDiscount(double totalFee) {
        return totalFee * 0.1; // 10% discount
    }
 
}

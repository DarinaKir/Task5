import java.util.Date;

public abstract class User {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private int amountOfPurchases;
    private double totalCostOfAll;
    private Date date;

    public User(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.amountOfPurchases = 0;
        this.totalCostOfAll = 0;
        this.date = null;
    }

    public abstract String appealTo();

    public String fullName() {
        return firstName +" " + lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getAmountOfPurchases() {
        return amountOfPurchases;
    }

    public double getTotalCostOfAll() {
        return totalCostOfAll;
    }

    public void addPurchase (double purchasePrice){
        this.amountOfPurchases++;
        this.totalCostOfAll+=purchasePrice;
        this.date = new Date();
    }

    public String toString() {
        return appealTo()+"\nThe amount of purchases made : "+amountOfPurchases+"\n"+ "Total cost of all purchases made : "+totalCostOfAll+"\n"
                +"Date of last purchase made : "+this.date;
    }
}

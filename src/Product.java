public class Product {
    private String description;
    private double price;
    private boolean isInStoke;
    private int discountPercentages;

    public Product(String description, double price, int discountPercentages) {
        this.description = description;
        this.price = price;
        this.isInStoke = true;
        this.discountPercentages = discountPercentages;
    }

    public double getPrice() {
        return price;
    }

    public boolean isInStoke() {
        return isInStoke;
    }

    public int getDiscountPercentages() {
        return discountPercentages;
    }

    public void setInStoke(boolean isInStoke) {
        this.isInStoke = isInStoke;
    }

    public String toString() {
        return description + " -> " + price;
    }
}

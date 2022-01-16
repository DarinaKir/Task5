public class Cart {
    private Product[] products;
    private double finalPrice;
    private int[] quantities;

    public Cart (){
        this.products = new Product[0];
        this.finalPrice = 0;
        this.quantities = new int[0];
    }

    public void addProduct (Product product, int amount , User user){
        Product[] updateProducts = new Product[this.products.length+1];
        int[] updateQuantities = new int[quantities.length+1];
        for (int i = 0; i < this.products.length; i++) {
            updateProducts[i]=this.products[i];
            updateQuantities[i]=this.quantities[i];
        }
        updateProducts[this.products.length] = product;
        updateQuantities[this.quantities.length]=amount;
        this.products = updateProducts;
        this.quantities = updateQuantities;
        double discountPercentages = 0;
        if (user instanceof Worker){
            discountPercentages = ((Worker) user).getRank() * 10;
        }else if (user instanceof Client && ((Client) user).isClubMember()){
            discountPercentages = product.getDiscountPercentages();
        }
        this.finalPrice +=(product.getPrice() - ((product.getPrice()*discountPercentages)/100))*amount;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public Product[] getProducts() {
        return products;
    }

    public String toString (){
        String string = "The contents of the cart :\n";
        for (int i = 0; i < products.length; i++) {
            string += ("* "+products[i]+" X"+quantities[i]+"\n");
        }
        return string + "The final price of the products: "+this.finalPrice +" $";
    }
}

import java.util.Scanner;
public class Store {
    private User[] users;
    private Product[] products;

    private static final int END_PURCHASE = -1;

    public Store (){
        this.users = new User[0];
        this.products = new Product[0];
    }

    private String clientOrWorker (){
        Scanner scanner = new Scanner(System.in);
        String answer = "---";
        do {
            System.out.println("Are you a client or a worker? \n"+"(Type 'client' or 'worker')");
            answer = scanner.nextLine();
        }while (!answer.equalsIgnoreCase("client") && !answer.equalsIgnoreCase("worker"));
        return answer;
    }

    private boolean haveNumbers (String string){
        boolean isNumberExist = false;
        for (int i = 0; i < string.length() && !isNumberExist; i++) {
            isNumberExist = (Character.isDigit(string.charAt(i)));
        }
        return isNumberExist;
    }

    private String stringWithoutNumbers (String inputName){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your "+inputName+" :");
        String answer = scanner.nextLine();
        while (haveNumbers(answer)){
            System.out.println("The input should be without numbers.\n" +
                    "Type again your "+inputName+" :");
            answer = scanner.nextLine();
        }
        return answer;
    }

    private int indexOfUserName(String userName) {
        int index = -1;
        for (int i = 0; i < users.length; i++) {
            if (userName.equals(users[i].getUserName())){
                index = i;
                break;
            }
        }
        return index;
    }

    private void addUser (User user){
        User[] updateUsers = new User[this.users.length+1];
        for (int i = 0; i < this.users.length; i++) {
            updateUsers[i] = this.users[i];
        }
        updateUsers[this.users.length]=user;
        this.users = updateUsers;
    }

    public void creatAccount (){
        Scanner scanner = new Scanner(System.in);
        String clientOrWorker = clientOrWorker();
        String firstName = stringWithoutNumbers("first name");
        String lastName = stringWithoutNumbers("last name");
        System.out.println("Create username :");
        String userName = scanner.nextLine();
        while (indexOfUserName(userName) != -1){
            System.out.println("That username is already taken.\n" +
                    "Choose another username:");
            userName = scanner.nextLine();
        }
        System.out.println("Create password (at least 6 characters):");
        String password = scanner.nextLine();
        while (password.length()<6){
            System.out.println("Your password is too short (at least 6 characters).\n" +
                    "Create another password:");
            password = scanner.nextLine();
        }
        User newUser = null;
        if (clientOrWorker.equalsIgnoreCase("client")){
            System.out.println("Are you a club member?\n" +
                    "Type 'Yes' or 'No':");
            String answer = scanner.nextLine();
            while (!answer.equalsIgnoreCase("yes") && !answer.equalsIgnoreCase("no")){
                System.out.println("The answer is not accepted ...\n" +
                        "You only have to type 'yes' or 'no':");
                answer = scanner.nextLine();
            }
            newUser = new Client(firstName,lastName,userName,password,(answer.equalsIgnoreCase("yes")));
        }else {
            int rank = 0;
            do{
                System.out.println("Type your rank: (type the option number)\n" +
                        "1- Regular worker\n" +
                        "2- Director\n" +
                        "3- Member of the management team");
                rank = scanner.nextInt();
            }while (rank>3 || rank<1);
            newUser = new Worker(firstName,lastName,userName,password,rank);
        }
        addUser(newUser);
        System.out.println("The account was created successfully !\n");
    }

    public User loginToAccount (){
        User user = null;
        Scanner scanner = new Scanner(System.in);
        String clientOrWorker = clientOrWorker();
        System.out.println("Type your username :");
        String userName = scanner.nextLine();
        int userNameIndex = indexOfUserName(userName);
        if (userNameIndex == -1){
            System.out.println("There is no account with such a username.");
        }else if ((clientOrWorker.equalsIgnoreCase("client")&&(users[userNameIndex] instanceof Client))  || (clientOrWorker.equalsIgnoreCase("worker")&&(users[userNameIndex] instanceof Worker))) {
            System.out.println("Type your password :");
            String password = scanner.nextLine();
            if (password.equals(users[userNameIndex].getPassword())){
                user = users[userNameIndex];
            }else {
                System.out.println("The password is incorrect.");
            }
        }else {
            System.out.println("Incompatible user type.");
        }

        return user;
    }

    public void purchase (User user){
        Scanner scanner = new Scanner(System.in);
        Cart cart = new Cart();
        Product[] productsInStoke = existProducts();
        if (productsInStoke.length == 0){
            System.out.println("There is no product in stock at the moment.");
        }else {
            int chosenProduct = 0;
            do {
                System.out.println("Type the product number from the list that you want to add to your shopping cart: (or type -1 if you want to end the purchase)");
                for (int i = 0; i < productsInStoke.length; i++) {
                    System.out.println((i+1)+" - "+productsInStoke[i]);
                }
                chosenProduct = scanner.nextInt();
                if (chosenProduct>0 && chosenProduct<=productsInStoke.length){
                    int amount = 0;
                    do {
                        System.out.println("How many items of this product are you interested in purchasing? (Type a positive number greater than 0)");
                        amount = scanner.nextInt();
                    }while (amount<=0);
                    cart.addProduct(productsInStoke[chosenProduct-1],amount,user);
                    System.out.println(cart +((user instanceof Client && !((Client) user).isClubMember()) ? "" : " (After discounts)")+"\n");
                }
            }while (chosenProduct !=END_PURCHASE);
            if (cart.getProducts().length!=0) {
                user.addPurchase(cart.getFinalPrice());
                System.out.println("Purchase completed ... Final cost of purchase: "+cart.getFinalPrice()+" $");
            }else {
                System.out.println("You did not select anything - so the purchase is canceled.");
            }
        }
    }

    private Product[] existProducts (){
        Product[] exists = new Product[0];
        for (int i = 0; i < this.products.length; i++) {
            if (this.products[i].isInStoke()){
                exists = addProductToArray(exists,this.products[i]);
            }
        }
        return exists;
    }

    private Product[] addProductToArray (Product[] products, Product product){
        Product[] updateProducts = new Product[products.length+1];
        for (int i = 0; i < products.length; i++) {
            updateProducts[i] = products[i];
        }
        updateProducts[products.length] = product;
        return updateProducts;
    }

    public void printAllCustomers (){
        boolean empty = true;
        for (int i = 0; i < users.length; i++) {
            if (users[i] instanceof Client){
                System.out.println(users[i]+"\n");
                if (empty) empty = false;
            }
        }
        if (empty){
            System.out.println("No customers.");
        }
    }

    public void printClubMembersCustomers(){
        boolean noClubMembers = true;
        for (int i = 0; i < users.length; i++) {
            if (users[i] instanceof Client && ((Client) users[i]).isClubMember()) {
                System.out.println(users[i] + "\n");
                if (noClubMembers) noClubMembers = false;
            }
        }
        if (noClubMembers) {
            System.out.println("There are no customers who are club members.");
        }
    }

    public void printPurchasedCustomers (){
        boolean noPurchased = true;
        for (int i = 0; i < users.length; i++) {
            if (users[i] instanceof Client && users[i].getAmountOfPurchases()>0){
                System.out.println(users[i]+"\n");
                if (noPurchased) noPurchased = false;
            }
        }
        if (noPurchased){
            System.out.println("There are no customers who have made purchases.");
        }
    }

    public User highestPurchasesAmountCustomer (){
        User user = null;
        for (int i = 0; i < users.length; i++) {
            if (users[i] instanceof Client){
                if(user == null || (user!=null && user.getTotalCostOfAll()<users[i].getTotalCostOfAll())){
                    user = users[i];
                }
            }
        }
        return user;
    }

    public void addProductToStore (){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type the product description you want to add:");
        String description = scanner.nextLine();
        double price = 0;
        do {
            System.out.println("What is the price of the product?\n" +
                    "(Type a number greater than 0)");
            price = scanner.nextDouble();
        }while (price<=0);
        int discountPercentages = -1;
        do {
            System.out.println("What is the discount percentage that club members receive for this product?\n" +
                    "(Type a number in the range 0-100)");
            discountPercentages = scanner.nextInt();
        }while (discountPercentages<0 || discountPercentages>100);
        Product newProduct = new Product(description,price,discountPercentages);
        this.products = addProductToArray(this.products,newProduct);
        System.out.println("Product added successfully!");
    }

    public void changeStockStatus (){
        if (this.products.length == 0){
            System.out.println("There are no products in the store.");
        }else {
            Scanner scanner = new Scanner(System.in);
            for (int i = 0; i < this.products.length; i++) {
                System.out.println((i+1)+". "+this.products[i]+" ("+(this.products[i].isInStoke() ? "in stock" : "not in stock")+")");
            }
            System.out.println("\nSelect a product that you want to update:\n"+
                    "(Type the number)");
            int selectedProduct = scanner.nextInt();
            while (selectedProduct<1 || selectedProduct>this.products.length){
                System.out.println("There is no such number of product on the list.\n" +
                        "Type a number that appears in the list.");
                selectedProduct=scanner.nextInt();
            }
            String yesOrNo;
            do {
                System.out.println("Is the product in stock?\n" +
                        "(Type 'yes' or 'no')");
                yesOrNo = scanner.next();
            }while (!yesOrNo.equalsIgnoreCase("yes") && !yesOrNo.equalsIgnoreCase("no"));
            this.products[selectedProduct-1].setInStoke(yesOrNo.equalsIgnoreCase("yes"));
            System.out.println("Product inventory status successfully updated!");
        }
    }
}

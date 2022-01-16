import java.util.Scanner;
public class Main {
    final static int CREATE_ACCOUNT = 1;
    final static int LOGIN_TO_ACCOUNT = 2;
    final static int EXIT = 3;
    final static int PRINT_ALL_CUSTOMERS = 1;
    final static int PRINT_CLUB_MEMBERS_CUSTOMERS =2;
    final static int PRINT_PURCHASED_CUSTOMERS = 3;
    final static int PRINT_CUSTOMER_WITH_THE_HIGHEST_PURCHASES_AMOUNT = 4;
    final static int ADD_NEW_PRODUCT = 5;
    final static int CHANGE_STOCK_STATUS = 6;
    final static int PURCHASE =7;
    final static int LOGOUT = 8;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store store = new Store();
        int answer = 0;
        do {
            System.out.println("What do you want to do ?\n" +
                    "1 - Create a new account\n" +
                    "2 - Login to an existing account\n" +
                    "3 - Exit");
            answer = scanner.nextInt();
            switch (answer){
                case CREATE_ACCOUNT:
                    System.out.println("Create new account :");
                    store.creatAccount();
                    break;
                case LOGIN_TO_ACCOUNT:
                    System.out.println("Login to account :");
                    User user = store.loginToAccount();
                    if (user != null){
                        System.out.println("Hello "+user.appealTo()+" !");
                        if (user instanceof Client){
                            store.purchase(user);
                        }else{
                            answer =0;
                            do {
                                System.out.println("1 - Print a list of all customers.\n" +
                                        "2 - Print the list of customers who are members of the club only.\n" +
                                        "3 - Print the list of customers who have made at least one purchase.\n" +
                                        "4 - Print the customer whose purchase amount is the highest.\n" +
                                        "5 - Adding a new product to the store.\n" +
                                        "6 - Change stock status for product.\n" +
                                        "7 - Making a purchase.\n" +
                                        "8 - Logout.");
                                System.out.println("(Type the number one of the option)");
                                answer = scanner.nextInt();
                                switch (answer){
                                    case PRINT_ALL_CUSTOMERS:
                                        store.printAllCustomers();
                                        break;
                                    case PRINT_CLUB_MEMBERS_CUSTOMERS:
                                        store.printClubMembersCustomers();
                                        break;
                                    case PRINT_PURCHASED_CUSTOMERS:
                                        store.printPurchasedCustomers();
                                        break;
                                    case PRINT_CUSTOMER_WITH_THE_HIGHEST_PURCHASES_AMOUNT:
                                        User customer = store.highestPurchasesAmountCustomer();
                                        System.out.println((customer==null) ? "There is no such customer." : customer+"\n");
                                        break;
                                    case ADD_NEW_PRODUCT:
                                        store.addProductToStore();
                                        break;
                                    case CHANGE_STOCK_STATUS:
                                        store.changeStockStatus();
                                        break;
                                    case PURCHASE:
                                        store.purchase(user);
                                        break;
                                    case LOGOUT:
                                        System.out.println("Returning to the main menu :");
                                        break;
                                    default:
                                        System.out.println("Error.\n" +
                                                "You need to type the number of the option you have selected (1-8)");
                                        break;
                                }
                            }while (answer!=LOGOUT);
                        }
                    }
                    break;
                case EXIT:
                    System.out.println("You left the program...");
                    break;
                default:
                    System.out.println("Your answer is incorrect." +
                            "Type the number of one of the options:");
                    break;
            }
        }while (answer != EXIT);
    }
}

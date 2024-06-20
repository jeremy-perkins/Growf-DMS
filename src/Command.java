/*
    Jeremy Perkins
    202430-CEN-3024C-31950 1
    Software Development 1

    This is where all the logic of the program is nested. Data is input, sorted, and removed using this class.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Command {
    static ArrayList<Stock> stockList = new ArrayList<Stock>();

    /*
    This method prints the current arrayList of stocks for the user. It has no arguments or return value.
     */
    public static void listStocks() {
        System.out.println("Current stockList: " + stockList);
    }

    /*
    This method sorts the arrayList alphabetically, also printing and displaying a before and after for the user.
    It has no arguments or return value.
     */
    public static void sortAlpha(){
        System.out.println("Stocklist Prior to sorting: " + stockList);
        Collections.sort(stockList, Comparator.comparing(Stock::getStock));
        System.out.println("Stocklist after sorting (A-Z): " + stockList + "\n");
    }

    /*
    This method sorts the arrayList based on the number of each stock owned,
    also printing and displaying a before and after for the user.
    It has no arguments or return value.
     */
    static void sortNumbOwned(){
        System.out.println("Stocklist Prior to sorting: " + stockList);
        Collections.sort(stockList, Comparator.comparing(Stock::getNumbPurchased));
        System.out.println("Stocklist after sorting (least owned to most owned): " + stockList + "\n");
    }

    /*
    This method sorts the arrayList based on the difference in the current and purchased price,
    also printing and displaying a before and after for the user.
    It has no arguments or return value.
     */
    static void sortPriceChange(){
        System.out.println("Stocklist Prior to sorting: " + stockList);
        Collections.sort(stockList, Comparator.comparing(Stock::getPriceChange));
        System.out.println("Stocklist after sorting: (least change to most change)" + stockList + "\n");
    }

    /*
    This method removes a stock from the arrayList based on user input ticker.
    It has no arguments or return value.
     */
    static void removeStock(){
        System.out.println("Please enter the stock ticker or exact stock name you wish to remove or 0 to exit: ");
        Scanner sc = new Scanner(System.in);
        String ans = sc.next();
        if (stockList.removeIf(n -> n.ticker.equals(ans.toUpperCase()))){
            System.out.println("Stocklist after removing: " + stockList + "\n");
        } else if (Objects.equals(ans, "0")) {
            System.out.println("Returning to main menu.\n");
            Main.printMenu();

        } else if (stockList.removeIf(n -> n.stock.equals(ans))) {
            System.out.println("Stocklist after removing: " + stockList + "\n");

        } else {
            System.out.println("Stock ticker not found, please enter a valid stock ticker.\n");
            removeStock();
        };
    }

    /*
    This method allows the user to update the purchase price, number bought, and current price of a stock object.
     */
    public static void updateStock() {
        System.out.println("Please enter the stock ticker you wish to update or 0 to exit: ");
        Scanner sc = new Scanner(System.in);
        String ans = sc.next();

        for (Stock stock : stockList) {
            if (stock.ticker.equals(ans.toUpperCase())) {
                System.out.println("Stock: [" + stock.getStock() + "] found please select which value you would like to update.\n");
                System.out.println("1: Change current price.");
                System.out.println("2: Change purchase price.");
                System.out.println("3: Change number purchased.");
                System.out.println("4: Return to main menu.");
                int userIn2 = 0;
                while (userIn2 != 4) {


                    userIn2 = sc.nextInt();
                    switch (userIn2) {
                        case 1:
                            System.out.println("Current price is currently: " + stock.getCurrentPrice());
                            System.out.println("New current price:");
                            Scanner sc2 = new Scanner(System.in);

                            do {try {
                                double ans2 = Double.parseDouble(sc2.next());
                                stock.setCurrentPrice(ans2);
                                break;
                            }
                            catch (Exception e) {
                                System.out.println("Invalid input, please enter a numeric value for current stock price.");
                            }
                            }
                            while(true);
                            System.out.println("Current updated to: " + stock.getCurrentPrice());

                            userIn2 = 0;
                            break;

                        case 2:
                            System.out.println("Purchase price is currently: " + stock.getPurchasePrice());
                            System.out.println("New purchase price:");
                            Scanner sc3 = new Scanner(System.in);
                            do {try {
                                double ans3 = Double.parseDouble(sc3.next());

                                stock.setPurchasePrice(ans3);
                                break;
                            }
                            catch (Exception e) {
                                System.out.println("Invalid input, please enter a numeric value for current stock price.");
                            }
                            }
                            while(true);
                            System.out.println("Purchase price updated to: " + stock.getPurchasePrice());

                            userIn2 = 0;
                            break;

                        case 3:
                            System.out.println("Number purchased is currently: " + stock.getNumbPurchased());
                            System.out.println("New number purchased:");
                            Scanner sc4 = new Scanner(System.in);
                            do {try {
                                double ans4 = Double.parseDouble(sc4.next());

                                stock.setNumbPurchased(ans4);
                                break;
                            }
                            catch (Exception e) {
                                System.out.println("Invalid input, please enter a numeric value for current stock price.");
                            }
                            }
                            while(true);
                            System.out.println("Number purchased updated to: " + stock.getNumbPurchased());

                            userIn2 = 0;
                            break;

                        case 4:
                            System.out.println("Returning to main menu.\n");
                            Main.printMenu();
                            break;
                    }
                }
            }
        }
    }

    /*
    This method reads data from a text file and sorts it into an arrayList of stock objects.
    It has no arguments or return value.
     */
    public static void importData() throws FileNotFoundException {
        String stock, ticker, description;
        double currentPrice, numbPurchased, purchasePrice, priceChange;
        Float dividend;

        File file = new File("SampleData.txt");
        if(file.exists()) {
            Scanner sc = new Scanner(file);
            sc.useDelimiter(";|\\n");

            while (sc.hasNext()) {
                stock = sc.next();
                System.out.println("Added Stock " + stock + " with values:");
                ticker = sc.next();
                System.out.println(ticker);
                description = sc.next();
                System.out.println(description);
                currentPrice = Double.parseDouble(sc.next());
                System.out.println(currentPrice);
                numbPurchased = Double.parseDouble(sc.next());
                System.out.println(numbPurchased);
                purchasePrice = Double.parseDouble(sc.next());
                System.out.println(purchasePrice);
                priceChange = currentPrice - purchasePrice;
                System.out.println(priceChange);
                dividend = Float.valueOf(sc.next());
                System.out.println(dividend + "\n");

                stockList.add(new Stock(stock, ticker, description, currentPrice, numbPurchased, purchasePrice, dividend));
            }
        }
        else {
            System.out.println("File not found, please verify your directory.\n");
            System.exit(0);
        }
    }

    /*
    This method allows users to manually input values for a new stock object and adds it
    to the arrayList. It prints an updated list afterwards.
    It has no arguments or return value.
     */
    static void addStock(){
        String stock, ticker, description;
        double currentPrice, numbPurchased, purchasePrice, priceChange;
        float dividend;

        Scanner addStockScanner = new Scanner(System.in);

        System.out.println("Please enter stock name:");
        stock = addStockScanner.nextLine();
        System.out.println("Please enter stock ticker:");
        ticker = addStockScanner.nextLine().toUpperCase();
        System.out.println("Please enter stock description:");
        description = addStockScanner.nextLine();
        System.out.println("Please enter current stock price:");
        do {try {
            currentPrice = Double.parseDouble(addStockScanner.next());
            break;
        }
        catch (Exception e) {
            System.out.println("Invalid input, please enter a numeric value for current stock price.");
        }
        }
        while(true);

        System.out.println("Please enter current number purchased:");
        do {try {
            numbPurchased = Double.parseDouble(addStockScanner.next());
            break;
        }
        catch (Exception e) {
            System.out.println("Invalid input, please enter a numeric value for number purchased.");
        }
        }
        while(true);

        System.out.println("Please enter stock purchase price:");
        do {try {
            purchasePrice = Double.parseDouble(addStockScanner.next());
            break;
        }
        catch (Exception e) {
            System.out.println("Invalid input, please enter a numeric value for stock purchase price.");
        }
        }
        while(true);

        priceChange = currentPrice-purchasePrice;
        System.out.println("priceChange updated to: " + priceChange);
        System.out.println("Please enter dividend rate:");
        do {try {
            dividend = Float.parseFloat(addStockScanner.next());
            break;
        }
        catch (Exception e) {
            System.out.println("Invalid input, please enter a numeric value for dividend amount.");
        }
        }
        while(true);

        System.out.println("Added Stock " + stock + " with values:");
        System.out.println(stock);
        System.out.println(ticker);
        System.out.println(description);
        System.out.println(currentPrice);
        System.out.println(numbPurchased);
        System.out.println(purchasePrice);
        System.out.println(priceChange);
        System.out.println(dividend + "\n");

        stockList.add(new Stock(stock, ticker, description, currentPrice, numbPurchased, purchasePrice, dividend));
        System.out.println("Updated stockList: " + stockList);
    }


}
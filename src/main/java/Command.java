/*
    Jeremy Perkins
    202430-CEN-3024C-31950 1
    Software Development 1

    This is where all the logic of the program is nested. Data is input, sorted, and removed using this class.
 */

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;


/**
 * Contains the logic of the program.
 */
public class Command {

    //static String url = "jdbc:sqlite:C:/Users/Jeremy/Desktop/Data Source/GrowfDMS.db";
    //C:/Users/Jeremy/Desktop/Data Source/GrowfDMS.db
    static String userIn = JOptionPane.showInputDialog("Enter URL");
    static String url = "jdbc:sqlite:" + userIn;
    static ArrayList<Stock> stockList = new ArrayList<Stock>();

    /**
     * This method prints the current arrayList of stocks for the user. It has no arguments or return value.
     *
     */
    public static void listStocks() {
        System.out.println("Current stockList: " + stockList);
    }

    /**
     *
     *     This method sorts the arrayList alphabetically, also printing and displaying a before and after for the user.
     *     It has no arguments or return value.
     *
     */
    public static void sortAlpha(){
        System.out.println("Stocklist Prior to sorting: " + stockList);
        Collections.sort(stockList, Comparator.comparing(Stock::getStock));
        System.out.println("Stocklist after sorting (A-Z): " + stockList + "\n");
    }

    /**
     * This method sorts the arrayList based on the number of each stock owned,
     *     also printing and displaying a before and after for the user.
     *     It has no arguments or return value.
     */
    static void sortNumbOwned(){
        System.out.println("Stocklist Prior to sorting: " + stockList);
        Collections.sort(stockList, Comparator.comparing(Stock::getNumbPurchased));
        System.out.println("Stocklist after sorting (least owned to most owned): " + stockList + "\n");
    }

    /**
     * This method sorts the arrayList based on the difference in the current and purchased price,
     *     also printing and displaying a before and after for the user.
     *     It has no arguments or return value.
     */
    static void sortPriceChange(){
        System.out.println("Stocklist Prior to sorting: " + stockList);
        Collections.sort(stockList, Comparator.comparing(Stock::getPriceChange));
        System.out.println("Stocklist after sorting: (least change to most change)" + stockList + "\n");
    }

    /**
     * This method removes a stock from the arrayList based on user input ticker.
     *     It has no arguments or return value.
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

    /**
     * Overloaded version of removeStock method using values from JTextField to select ticker to remove
     * @param ans (String)
     * @throws SQLException
     */
    static void removeStock(String ans) throws SQLException {
        // Database Update
        String sql = "DELETE FROM GrowfDMS WHERE Ticker = (?)";
        String sql2 = "DELETE FROM GrowfDMS WHERE Stock = (?)";
        // create a connection to the database


        if (stockList.removeIf(n -> n.ticker.equals(ans.toUpperCase()))){
            try(var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, ans.toUpperCase());
                pstmt.executeUpdate();

            }
            System.out.println("Stocklist after removing: " + stockList + "\n");

        }
        else if (stockList.removeIf(n -> n.stock.equals(ans))){
            try(var conn = DriverManager.getConnection(url);
                var pstmt = conn.prepareStatement(sql2)) {
                pstmt.setString(1, ans);
                pstmt.executeUpdate();

            }
            System.out.println("Stocklist after removing: " + stockList + "\n");
        };
    }

    /**
     * This method allows the user to update the purchase price, number bought, and current price of a stock object.
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

    /**
     * Overloaded update stock method, updates stock arrayList and Sqlit3 values using JTextield values.
     * @param ticker The exact ticker value meant to be used to search the ArrayList
     * @param update The value to be updated.
     * @param price The number the price is being changed to.
     */
    public static void updateStock(String ticker, int update, double price){
        // Database Update
        String sql1 = "UPDATE GrowfDMS SET [Current Price] = ? WHERE Ticker = ?";
        String sql2 = "UPDATE GrowfDMS SET [Purchase Price] = ? WHERE Ticker = ?";
        String sql3 = "UPDATE GrowfDMS SET [Number Purchased] = ? WHERE Ticker = ?";
        // create a connection to the database

        for (Stock stock : stockList) {
            if (stock.ticker.equals(ticker.toUpperCase())) {
                switch (update) {
                    case 1:
                        System.out.println("Current price is currently: " + stock.getCurrentPrice());

                        do {try {
                            stock.setCurrentPrice(price);
                            try(var conn = DriverManager.getConnection(url);
                                var pstmt = conn.prepareStatement(sql1)) {
                                pstmt.setDouble(1, price);
                                pstmt.setString(2, ticker);
                                pstmt.executeUpdate();

                            } catch (SQLException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        }
                        catch (Exception e) {
                            System.out.println("Invalid input, please enter a numeric value for current stock price.");
                            JOptionPane.showMessageDialog(null,"Invalid input, please enter a numeric value for current stock price.");
                        }
                        }
                        while(true);
                        System.out.println("Current updated to: " + stock.getCurrentPrice());
                        update = 4;
                        break;

                    case 2:
                        System.out.println("Purchase price is currently: " + stock.getPurchasePrice());

                        do {try {
                            stock.setPurchasePrice(price);
                            try(var conn = DriverManager.getConnection(url);
                                var pstmt = conn.prepareStatement(sql2)) {
                                pstmt.setDouble(1, price);
                                pstmt.setString(2, ticker);
                                pstmt.executeUpdate();

                            } catch (SQLException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        }
                        catch (Exception e) {
                            System.out.println("Invalid input, please enter a numeric value for current stock price.");
                            JOptionPane.showMessageDialog(null,"Invalid input, please enter a numeric value for current stock price.");
                        }
                        }
                        while(true);
                        System.out.println("Purchase price updated to: " + stock.getPurchasePrice());

                        update = 4;
                        break;

                    case 3:
                        System.out.println("Number purchased is currently: " + stock.getNumbPurchased());
                        do {try {
                            stock.setNumbPurchased(price);
                            try(var conn = DriverManager.getConnection(url);
                                var pstmt = conn.prepareStatement(sql3)) {
                                pstmt.setDouble(1, price);
                                pstmt.setString(2, ticker);
                                pstmt.executeUpdate();

                            } catch (SQLException e) {
                                System.err.println(e.getMessage());
                            }
                            break;
                        }
                        catch (Exception e) {
                            System.out.println("Invalid input, please enter a numeric value for current stock price.");
                            JOptionPane.showMessageDialog(null,"Invalid input, please enter a numeric value for current stock price.");
                        }
                        }
                        while(true);
                        System.out.println("Number purchased updated to: " + stock.getNumbPurchased());

                        update = 4;
                        break;

                    case 4:
                        System.out.println("Returning to main menu.\n");
                        Main.printMenu();
                        break;
                }
            }
        }
    }

    /**
     * This method reads data from a text file in a set location within the src and sorts it into an arrayList of stock objects.
     *     It has no arguments or return value.
     * @throws FileNotFoundException If text file is not found to import data, exception is thrown.
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

                stockList.add(new Stock(stock, ticker, description, currentPrice, numbPurchased, purchasePrice, priceChange, dividend));
            }
        }
        else {
            System.out.println("File not found, please verify your directory.\n");
            JOptionPane.showMessageDialog(null,"File not found, please verify your directory.");
            System.exit(0);
        }
    }

    /**
     * New importData for handling SQLit3. Tries to create a connection to the Database then moves
     * data into the stockList for use in the rest of the program.
     */
    public static void importDataDB() {
        Connection conn = null;
        String query = "select * from GrowfDMS";
        String stock, ticker, description;
        double currentPrice, numbPurchased, purchasePrice, priceChange;
        float dividend;
        try {
            // db parameters
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("Connection to SQLite has been established.");

            while (rs.next()) {
                ticker = rs.getString(1);
                stock = rs.getString(2);
                description = rs.getString(3);
                currentPrice = rs.getDouble(4);
                numbPurchased = rs.getDouble(5);
                purchasePrice = rs.getDouble(6);
                priceChange = rs.getDouble(7);
                dividend = rs.getFloat(8);

                System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));
                Command.stockList.add(new Stock(stock, ticker, description, currentPrice, numbPurchased, purchasePrice, priceChange, dividend));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * This method allows users to manually input values for a new stock object and adds it
     *     to the arrayList. It prints an updated list afterwards.
     *     It has no arguments or return value.
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

        stockList.add(new Stock(stock, ticker, description, currentPrice, numbPurchased, purchasePrice, priceChange, dividend));
        System.out.println("Updated stockList: " + stockList);
    }

    /**
     * Overloaded version of addStock method, allows adding of objects to the arrayList from
     *     Jtextfields in GUI.
     * @param stock (String) Stock name to be added to arrayList, table, table view and database.
     * @param ticker (String) Stock ticker to be added to arrayList, table, table view and database.
     * @param description (String) Description to be added to arrayList, table, table view and database.
     * @param currentPrice (Double) Current price to be added to arrayList, table, table view and database.
     * @param numbPurchased (Double) Number of stocks purchase to be added to arrayList, table, table view and database.
     * @param purchasePrice (Double) Purchase price to be added to arrayList, table, table view and database.
     * @param priceChange (Double) Difference in prices to be added to arrayList, table, table view and database.
     * @param dividend (Double) Percentage of stock value as dividend to be added to arrayList, table, table view and database.
     * @throws SQLException If database connection not found, exception thrown.
     */
    static void addStock(String stock,String ticker,String description,double currentPrice,double numbPurchased,double purchasePrice, double priceChange, float dividend) throws SQLException {
        priceChange = currentPrice - purchasePrice;
        // StockList Update
        stockList.add(new Stock(stock, ticker, description, currentPrice, numbPurchased, purchasePrice, priceChange, dividend));

        // Database Update
        String sql = "INSERT INTO GrowfDMS (Ticker,Stock,Description,[Current Price],[Number Purchased],[Purchase Price],[Price Change],Dividend) VALUES (?,?,?,?,?,?,?,?)";
        // create a connection to the database


        try(var conn = DriverManager.getConnection(url);
            var pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, ticker);
                pstmt.setString(2, stock);
                pstmt.setString(3, description);
                pstmt.setDouble(4, currentPrice);
                pstmt.setDouble(5, numbPurchased);
                pstmt.setDouble(6, purchasePrice);
                pstmt.setDouble(7, priceChange);
                pstmt.setFloat(8, dividend);
                pstmt.executeUpdate();

        }


        //Logging
        System.out.println("Updated stockList: " + stockList);
    }


}
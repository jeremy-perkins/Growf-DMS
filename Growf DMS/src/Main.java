/*
    Jeremy Perkins
    202430-CEN-3024C-31950 1
    Software Development 1

    This is the main class and administrator of our program. It calls other class methods using its text menu.

    The Growf Database Management System will take stock data input from the user and
    display and sort it in ways to make it easier for the end-user to make decisions with their
    investments. Most notably the system will allow end users to compare their stock
    holdings and determine and track over time which are performing well by comparing current and purchased prices.

 */

import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner scn = new Scanner(System.in);
        int userIn = 0;

        printMenu();

        // Menu operation logic
        while (true) {
            switch (userIn) {
                case 1:
                    System.out.println("Running importData()");
                    Command.importData();

                    System.out.println("Stocks added from file.");
                    printMenu();

                    userIn = 0;
                    break;

                case 2:
                    Command.listStocks();

                    userIn = 0;
                    break;

                case 3:
                    Command.sortAlpha();

                    userIn = 0;
                    break;

                case 4:
                    Command.sortNumbOwned();

                    userIn = 0;
                    break;

                case 5:
                    Command.sortPriceChange();

                    userIn = 0;
                    break;

                case 6:
                    Command.addStock();

                    userIn = 0;
                    break;

                case 7:
                    Command.removeStock();

                    userIn = 0;
                    break;

                case 8:
                    Command.updateStock();

                    userIn = 0;
                    break;

                case 9:
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;

                default: try {
                    System.out.println("Please enter number.");
                    userIn = Integer.parseInt(scn.nextLine());

                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Please enter a valid number.");
                    userIn = Integer.parseInt(scn.nextLine());

                }
            }
        }
    }

    /*
     This method prints the menu we use to interact with the program. It has no arguments or return value.
     */
    static void printMenu() {
        System.out.println("-------------------Welcome to the Growf Data Management System--------------------");
        System.out.println("Initial stocks are input by the SampleData.txt file located in this folder.");
        System.out.println("Press the corresponding inputs to navigate the system.");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("Press 1 to load data from the text file to the system.");
        System.out.println("Press 2 to list all stocks currently in the system.");
        System.out.println("Press 3 to sort all stocks alphabetically by name.");
        System.out.println("Press 4 to sort all stocks based on the number owned.");
        System.out.println("Press 5 to sort all stocks based on the the change in value since purchase.");
        System.out.println("Press 6 to manually add a stock to the system.");
        System.out.println("Press 7 to remove a stock from the system.");
        System.out.println("Press 8 to update a stock already in the system.");
        System.out.println("Press 9 to exit the program.");
        System.out.println("----------------------------------------------------------------------------------");
    }
}
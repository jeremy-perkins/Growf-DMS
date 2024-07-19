import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;



class CommandTest {

    /**
     * Utilizes same logic as Command's addStock method without user input to verify.
     */
    @Test
    void addStock() {
        System.out.println("addStock");
        String stock = "Test", ticker = "TST", description = "Test Stock";
        double currentPrice = 100, numbPurchased = 5, purchasePrice = 85, priceChange =currentPrice-purchasePrice;
        float dividend = .04F;

        Command.stockList.add(new Stock(stock, ticker, description, currentPrice, numbPurchased, purchasePrice, priceChange, dividend));
        assertEquals("Test", Command.stockList.getLast().getStock());
    }

    /**
     * Checks first member of arrayList value to ensure it has been re-arranged.
     * @throws FileNotFoundException if importData does not find text file.
     */
    @Test
    void sortAlpha() throws FileNotFoundException {
        System.out.println("sortAlpha");
        Command.importData();
        Command.sortAlpha();
            assertEquals("AT&T Inc.", Command.stockList.getFirst().stock);
    }

    /**
     * Utilizes same logic as our removeStock without userinput, tests stock is indeed removed or fails.
     * @throws FileNotFoundException if text file not found.
     */
    @Test
    void removeStock() throws FileNotFoundException {
        System.out.println("removeStock");
        String ans2 = "AT&T Inc.";
        if (Command.stockList.removeIf(n -> n.stock.equals(ans2))) {
            System.out.println("Stocklist after removing: " + Command.stockList + "\n");
        }
        assertNotEquals  ("AT&T Inc.",Command.stockList.getFirst().getStock());
        Command.listStocks();
    }

    /**
     * Utilizes same logic as updateStock without user input, tests current price against the one updated here.
     * @throws FileNotFoundException if text file not found.
     */
    @Test
    void updateStock() throws FileNotFoundException {
        Command.importData();
        System.out.println("updateStock");
        String ans = "TST";
        double ans2 = 1.1;
        for (Stock stock : Command.stockList) {
            if (stock.ticker.equals(ans.toUpperCase())) {
                System.out.println("Stock: [" + stock.getStock() + "] found.\n");
                stock.setCurrentPrice(ans2);
                System.out.println("Current price is currently: " + stock.getCurrentPrice());
            }
            assertEquals(1.1, Command.stockList.getFirst().currentPrice);

        }
    }
}
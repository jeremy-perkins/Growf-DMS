/*
    Jeremy Perkins
    202430-CEN-3024C-31950 1
    Software Development 1

    This is the class we are using to store our Data.
 */

public class Stock {
    String stock;
    String ticker;
    String description;
    double currentPrice;
    double numbPurchased;
    double purchasePrice;
    double priceChange = currentPrice-purchasePrice;
    Float dividend;

    /*
    This method retrieves the stock name for the comparison operators when sorting and for display.
    This method has no arguments or return value.
     */
    public String getStock() {
        return stock;
    }

    /*
    This method retrieves the ticker both for display and for selection when removing stocks.
    This method has no arguments or return value.
     */
    public String getTicker() {
        return ticker;
    }

    /*
    This method retrieves the number of a stock owned for the comparison operators when sorting.
    This method has no arguments or return value.
     */
    public double getNumbPurchased() {
        return numbPurchased;
    }

    /*
    This method retrieves difference in the purchase and current value for the comparison operators when sorting.
    This method has no arguments or return value.
     */
    public double getPriceChange() {
        return purchasePrice;
    }

    /*
    This override allows our stock name and ticker to be printed instead of the Object type and hash code.
    It has no arguments or return value.
     */
    @Override
    public String toString() {
        return this.getStock() + " (" + this.getTicker() + ")";
    }

    public Stock(String stock, String ticker, String description, double currentPrice, double numbPurchased, double purchasePrice, Float dividend) {
        this.stock = stock;
        this.ticker = ticker;
        this.description = description;
        this.currentPrice = currentPrice;
        this.numbPurchased = numbPurchased;
        this.purchasePrice = purchasePrice;
        this.priceChange = currentPrice-purchasePrice;
        this.dividend = dividend;
    }
}

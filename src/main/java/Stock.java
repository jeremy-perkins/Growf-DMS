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
    double priceChange;
    Float dividend;

    /**
     * Allows current price of a stock object to be updated.
     * @param currentPrice the new price to be inserted into stock object.
     */
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    /**
     * This method retrieves the current price for display.
     * @return Returns the currentPrice value from the stock object.
     */
    public double getCurrentPrice() {
        return currentPrice;
    }

    /**
     * This method allows purchase price of a stock object to be updated.
     * @param purchasePrice the new purchasePrice to be inserted into stock object.
     */
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * This method retrieves the purchase price of the stock object for display.
     * @return Returns the purchasePrice value from the stock object.
     */
    public double getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * This method allows number purchased of a stock object to be updated.
     * @param numbPurchased Returns the numbPurchased value from the stock object.
     */
    public void setNumbPurchased(double numbPurchased) {
        this.numbPurchased = numbPurchased;
    }

    /**
     * This method retrieves the number of a stock purchased from a stock object for display.
     * @return Returns the numbPurchased value from the stock object.
     */
    public double getNumbPurchased() {
        return numbPurchased;
    }


    /**
     * This method retrieves the stock name for the comparison operators when sorting and for display.
     *         This method has no arguments or return value.
     * @return (String) stock from stock object.
     */
    public String getStock() {
        return stock;
    }

    /**
     * This method retrieves the ticker both for display and for selection when removing stocks.
     *     This method has no arguments or return value.
     * @return (String) ticker from stock object.
     */
    public String getTicker() {
        return ticker;
    }

    /*
    This method retrieves the number of a stock owned for the comparison operators when sorting.
    This method has no arguments or return value.
     */

    /**
     * This method retrieves difference in the purchase and current value for the comparison operators when sorting.
     *     This method has no arguments.
     * @return the pricePurchased from stock object.
     */
    public double getPriceChange() {
        return purchasePrice;
    }

    /**
     * This override allows our stock name and ticker to be printed instead of the Object type and hash code.
     *     It has no arguments.
     * @return the stock and ticker concatenated in a string.
     */
    @Override
    public String toString() {
        return this.getStock() + " (" + this.getTicker() + ")";
    }

    public Stock(String stock, String ticker, String description, double currentPrice, double numbPurchased, double purchasePrice, double priceChange, Float dividend) {
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

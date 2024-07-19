import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Objects;

public class MainForm extends JFrame {
    private JPanel mainPanel;
    JTable StockList;
    private JButton loadDataButton;
    private JButton sortAlphaButton;
    private JButton sortPriceChangeButton;
    private JButton sortNumOwnedButton;
    private JPanel sortPanel;
    private JScrollPane StockListPane;
    private JPanel removeStockPanel;
    private JTextField removeField;
    private JButton removeStockButton;
    private JLabel removalConfirmation;
    private JPanel stockView;
    private JLabel stockStock;
    private JLabel stockTicker;
    private JLabel stockDescription;
    private JLabel stockCurrentPrice;
    private JLabel stockNumPurchased;
    private JLabel stockPurchasePrice;
    private JLabel stockPriceChange;
    private JLabel stockDividend;
    private JLabel stockStockValue;
    private JLabel stockTickerValue;
    private JLabel stockDescriptionValue;
    private JLabel stockCurrentPriceValue;
    private JLabel stockNumPurchasedValue;
    private JLabel stockPurchasePriceValue;
    private JLabel stockPriceChangeValue;
    private JLabel stockDividendValue;
    private JTextField updateCurrentPrice;
    private JButton updateCurrentPriceButton;
    private JTextField updateNumPurchased;
    private JTextField updatePurchasePrice;
    private JButton updateNumPurchasedButton;
    private JButton updatePurchasePriceButton;
    private JTextField addStock;
    private JTextField addTicker;
    private JButton addButton;
    private JTextField addDescription;
    private JTextField addCurrentPrice;
    private JTextField addNumbPurchased;
    private JTextField addPurchasePrice;
    private JTextField addDividendRate;

    public MainForm() throws ClassNotFoundException {
        //Class.forName("org.sqlite.JDBC");
        $$$setupUI$$$();
        setContentPane(mainPanel);
        setTitle("Growf DMS");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        /**
         * Loads data from file, exception in Command file brings up JOptionPane if unsuccessful
         */
        loadDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command.importDataDB();
            }
        });

        /**
         * Sorts both the arrayList and JTable alphabetically
         */
        sortAlphaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockList.getRowSorter().toggleSortOrder(0);
                Command.sortAlpha();
            }
        });

        /**
         * Sorts both the arrayList and JTable based on price difference
         */
        sortPriceChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockList.getRowSorter().toggleSortOrder(5);
                Command.sortPriceChange();
            }
        });

        /**
         * Sorts both the arrayList and JTable based on number owned
         */
        sortNumOwnedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockList.getRowSorter().toggleSortOrder(4);
                Command.sortNumbOwned();
            }
        });

        /**
         *  Runs overloaded removeStock command using value from JTextField, also gives confirmation using JLabel
         */
        removeStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ticker = removeField.getText();

                DefaultTableModel model = (DefaultTableModel) StockList.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (((String) model.getValueAt(i, 1)).equals(ticker.toUpperCase())) {
                        model.removeRow(i);
                        removalConfirmation.setText("Stock with ticker [" + ticker.toUpperCase() + "] removed from list");
                        try {
                            Command.removeStock(ticker);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    } else if ((((String) model.getValueAt(i, 0)).equals(ticker))) {
                        model.removeRow(i);
                        removalConfirmation.setText("Stock with name [" + ticker + "] removed from list");
                        try {
                            Command.removeStock(ticker);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        removalConfirmation.setText("Stock ticker not found, please enter a valid stock ticker.");
                    }
                }

            }
        });

        /**
         * Checks name of selected table item against arrayList and displays arraylist values in a series
         *          of JLabels.
         */
        StockList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String ans = StockList.getValueAt(StockList.getSelectedRow(), 0).toString();
                for (int i = 0; i < Command.stockList.size(); i++) {
                    String stock = Command.stockList.get(i).stock;

                    if (Objects.equals(stock, ans)) {
                        stockStockValue.setText(Command.stockList.get(i).stock);
                        stockTickerValue.setText(Command.stockList.get(i).ticker);
                        stockDescriptionValue.setText("<html>" + Command.stockList.get(i).description + "</html>");
                        stockCurrentPriceValue.setText(String.valueOf(Command.stockList.get(i).currentPrice));
                        stockNumPurchasedValue.setText(String.valueOf(Command.stockList.get(i).numbPurchased));
                        stockPurchasePriceValue.setText(String.valueOf(Command.stockList.get(i).purchasePrice));
                        stockDividendValue.setText(String.valueOf(Command.stockList.get(i).dividend));

                        double priceChange = Command.stockList.get(i).priceChange;
                        priceChange = priceChange * 100;
                        priceChange = (double) ((int) priceChange);
                        priceChange = priceChange / 100;
                        stockPriceChangeValue.setText(String.valueOf(priceChange));
                    }
                }
            }
        });

        /**
         * Changes value of both the viewable table and the underlying model based on JTextField Value,
         *          also calls overloaded version of updateStock to change the arrayList value.
         */
        updateCurrentPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double price = Double.parseDouble(updateCurrentPrice.getText());
                    int update = 1;
                    DefaultTableModel model = (DefaultTableModel) StockList.getModel();
                    int i = StockList.getSelectedRow();
                    i = StockList.convertRowIndexToModel(i);

                    String ticker = stockTickerValue.getText();
                    Command.updateStock(ticker, update, price);
                    model.setValueAt(price, i, 2);
                    stockCurrentPriceValue.setText(String.valueOf(price));
                    updateCurrentPrice.setText("");
                } catch (Exception l) {
                    System.err.println(l.getMessage());
                    JOptionPane.showMessageDialog(null, "Invalid input, please enter a numeric value for current stock price.");
                    updateCurrentPrice.setText("");
                }
            }
        });

        /**
         * Changes value of both the viewable table and the underlying model based on JTextField Value,
         *          also calls overloaded version of updateStock to change the arrayList value.
         */
        updateNumPurchasedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double price = Double.parseDouble(updateNumPurchased.getText());
                    int update = 3;
                    DefaultTableModel model = (DefaultTableModel) StockList.getModel();
                    int i = StockList.getSelectedRow();
                    i = StockList.convertRowIndexToModel(i);

                    String ticker = stockTickerValue.getText();
                    Command.updateStock(ticker, update, price);
                    model.setValueAt(price, i, 4);
                    stockNumPurchasedValue.setText(String.valueOf(price));
                    updateNumPurchased.setText("");
                } catch (NumberFormatException ex) {
                    System.err.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Invalid input, please enter a numeric value for current stock price.");
                    updateNumPurchased.setText("");
                }
            }
        });

        /**
         * Changes value of both the viewable table and the underlying model based on JTextField Value,
         *          also calls overloaded version of updateStock to change the arrayList value.
         */
        updatePurchasePriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double price = Double.parseDouble(updatePurchasePrice.getText());
                    int update = 2;
                    DefaultTableModel model = (DefaultTableModel) StockList.getModel();
                    int i = StockList.getSelectedRow();
                    i = StockList.convertRowIndexToModel(i);

                    String ticker = stockTickerValue.getText();
                    Command.updateStock(ticker, update, price);
                    model.setValueAt(price, i, 3);
                    stockPurchasePriceValue.setText(String.valueOf(price));
                    updatePurchasePrice.setText("");
                } catch (NumberFormatException ex) {
                    System.err.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Invalid input, please enter a numeric value for current stock price.");
                    updatePurchasePrice.setText("");
                }
            }
        });

        /**
         * Uses a series of JTextFields to add a new stock object to the JTable Model. Also
         *         calls the overloaded addStock method using those values so the arrayList is updated.
         */
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = null;
                String stock = null;
                String ticker = null;
                String description = null;
                double currentPrice = 0;
                double purchasePrice = 0;
                double numbPurchased = 0;
                double priceChange = 0;
                float dividend = 0;
                try {
                    model = (DefaultTableModel) StockList.getModel();
                    stock = String.valueOf(addStock.getText());
                    ticker = String.valueOf(addTicker.getText()).toUpperCase();
                    description = String.valueOf(addDescription.getText());
                    currentPrice = Double.parseDouble(addCurrentPrice.getText());
                    purchasePrice = Double.parseDouble(addPurchasePrice.getText());
                    numbPurchased = Double.parseDouble(addNumbPurchased.getText());
                    priceChange = currentPrice - purchasePrice;
                    dividend = Float.parseFloat(addDividendRate.getText());

                    priceChange = priceChange * 100;
                    priceChange = (double) ((int) priceChange);
                    priceChange = priceChange / 100;
                    Object[] data = {stock, ticker, currentPrice, purchasePrice, numbPurchased, priceChange};
                    model.addRow(data);
                    Command.addStock(stock, ticker, description, currentPrice, numbPurchased, purchasePrice, priceChange, dividend);

                } catch (NumberFormatException ex) {
                    System.err.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Invalid input, please enter a numeric value for any price or number inputs.");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                addStock.setText("");
                addTicker.setText("");
                addDescription.setText("");
                addCurrentPrice.setText("");
                addNumbPurchased.setText("");
                addPurchasePrice.setText("");
                addDividendRate.setText("");
            }
        });
    }

    /**
     * Initiates the GUI
     * @param args
     * @throws ClassNotFoundException Throws exception upon java.swing loading issues.
     */
    public static void main(String[] args) throws ClassNotFoundException {
        MainForm myForm = new MainForm();

    }

    /**
     * Custom constructor for JTable, loops through the arrayList to add values to table model.
     */
    private void createUIComponents() {
        Command.importDataDB();
        // TODO: place custom component creation code here
        String[] col = {"Stock", "Ticker", "Current Price", "Purchase Price", "Number Purchased", "Gain / Loss"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0) {
            @Override
            public Class getColumnClass(int col) {
                switch (col) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return Double.class;
                    case 3:
                        return Double.class;
                    case 4:
                        return Double.class;
                    case 5:
                        return Double.class;
                }
                return null;
            }

            ;
        };
        StockList = new JTable(tableModel);
        for (int i = 0; i < Command.stockList.size(); i++) {
            String stock = Command.stockList.get(i).stock;
            String ticker = Command.stockList.get(i).ticker;
            double currentPrice = Command.stockList.get(i).currentPrice;
            double purchasePrice = Command.stockList.get(i).purchasePrice;
            double numbPurchased = Command.stockList.get(i).numbPurchased;
            double priceChange = Command.stockList.get(i).priceChange;
            priceChange = priceChange * 100;
            priceChange = (double) ((int) priceChange);
            priceChange = priceChange / 100;
            Object[] data = {stock, ticker, currentPrice, purchasePrice, numbPurchased, priceChange};
            tableModel.addRow(data);
        }
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setForeground(new Color(-14277590));
        mainPanel.setMinimumSize(new Dimension(1920, 1080));
        mainPanel.setPreferredSize(new Dimension(1920, 1080));
        sortPanel = new JPanel();
        sortPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(sortPanel, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(640, 34), null, 0, false));
        sortAlphaButton = new JButton();
        sortAlphaButton.setText("Sort A-Z");
        sortPanel.add(sortAlphaButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sortPriceChangeButton = new JButton();
        sortPriceChangeButton.setText("Sort Gain/Loss");
        sortPanel.add(sortPriceChangeButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sortNumOwnedButton = new JButton();
        sortNumOwnedButton.setText("Sort # Owned");
        sortPanel.add(sortNumOwnedButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeStockPanel = new JPanel();
        removeStockPanel.setLayout(new GridLayoutManager(15, 13, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(removeStockPanel, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(640, 600), new Dimension(640, 60), null, 0, false));
        removeStockPanel.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label1 = new JLabel();
        label1.setText("Ticker to remove:");
        removeStockPanel.add(label1, new GridConstraints(1, 0, 1, 12, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 2, false));
        final Spacer spacer1 = new Spacer();
        removeStockPanel.add(spacer1, new GridConstraints(1, 12, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        removeStockPanel.add(spacer2, new GridConstraints(14, 0, 1, 12, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        removeField = new JTextField();
        removeStockPanel.add(removeField, new GridConstraints(2, 0, 1, 12, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 2, false));
        removeStockButton = new JButton();
        removeStockButton.setText("Remove");
        removeStockPanel.add(removeStockButton, new GridConstraints(3, 3, 1, 9, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Remove Stock");
        removeStockPanel.add(label2, new GridConstraints(0, 0, 1, 12, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JLabel label3 = new JLabel();
        label3.setText("Add Stock");
        removeStockPanel.add(label3, new GridConstraints(5, 0, 1, 12, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        final JLabel label4 = new JLabel();
        label4.setText("Stock Name:");
        removeStockPanel.add(label4, new GridConstraints(6, 0, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(119, 17), null, 2, false));
        final JLabel label5 = new JLabel();
        label5.setText("Description:");
        removeStockPanel.add(label5, new GridConstraints(8, 0, 1, 7, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 2, false));
        final JLabel label6 = new JLabel();
        label6.setText("Current Price:");
        removeStockPanel.add(label6, new GridConstraints(9, 0, 1, 8, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 2, false));
        final JLabel label7 = new JLabel();
        label7.setText("Number Purchased:");
        removeStockPanel.add(label7, new GridConstraints(10, 0, 1, 9, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 2, false));
        final JLabel label8 = new JLabel();
        label8.setText("Purchase Price:");
        removeStockPanel.add(label8, new GridConstraints(11, 0, 1, 10, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 2, false));
        final JLabel label9 = new JLabel();
        label9.setText("Dividend Rate:");
        removeStockPanel.add(label9, new GridConstraints(12, 0, 1, 11, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 2, false));
        final JLabel label10 = new JLabel();
        label10.setText("Ticker:");
        removeStockPanel.add(label10, new GridConstraints(7, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 2, false));
        addStock = new JTextField();
        removeStockPanel.add(addStock, new GridConstraints(6, 4, 1, 8, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addTicker = new JTextField();
        removeStockPanel.add(addTicker, new GridConstraints(7, 5, 1, 7, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addButton = new JButton();
        addButton.setText("Add");
        removeStockPanel.add(addButton, new GridConstraints(13, 6, 1, 6, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addDescription = new JTextField();
        removeStockPanel.add(addDescription, new GridConstraints(8, 7, 1, 5, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addCurrentPrice = new JTextField();
        removeStockPanel.add(addCurrentPrice, new GridConstraints(9, 8, 1, 4, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addNumbPurchased = new JTextField();
        removeStockPanel.add(addNumbPurchased, new GridConstraints(10, 9, 1, 3, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addPurchasePrice = new JTextField();
        removeStockPanel.add(addPurchasePrice, new GridConstraints(11, 10, 1, 2, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        addDividendRate = new JTextField();
        removeStockPanel.add(addDividendRate, new GridConstraints(12, 11, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final Spacer spacer3 = new Spacer();
        removeStockPanel.add(spacer3, new GridConstraints(4, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        removalConfirmation = new JLabel();
        removalConfirmation.setText("");
        removeStockPanel.add(removalConfirmation, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        mainPanel.add(spacer4, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        StockListPane = new JScrollPane();
        mainPanel.add(StockListPane, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(640, 500), new Dimension(640, 500), null, 0, false));
        StockListPane.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        StockList.setAutoCreateColumnsFromModel(true);
        StockList.setAutoCreateRowSorter(true);
        StockList.setColumnSelectionAllowed(false);
        StockList.setPreferredScrollableViewportSize(new Dimension(800, 400));
        StockList.putClientProperty("Table.isFileList", Boolean.FALSE);
        StockListPane.setViewportView(StockList);
        stockView = new JPanel();
        stockView.setLayout(new GridLayoutManager(10, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(stockView, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(640, 600), new Dimension(640, 600), null, 0, false));
        stockStock = new JLabel();
        stockStock.setText("Stock");
        stockView.add(stockStock, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(18, 17), null, 0, false));
        stockTicker = new JLabel();
        stockTicker.setText("Ticker");
        stockView.add(stockTicker, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(18, 17), null, 0, false));
        stockDescription = new JLabel();
        stockDescription.setText("Description");
        stockView.add(stockDescription, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stockCurrentPrice = new JLabel();
        stockCurrentPrice.setText("Current Price");
        stockView.add(stockCurrentPrice, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stockNumPurchased = new JLabel();
        stockNumPurchased.setText("Number Purchased");
        stockView.add(stockNumPurchased, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(120, 54), null, 0, false));
        stockPurchasePrice = new JLabel();
        stockPurchasePrice.setText("Purchase Price");
        stockView.add(stockPurchasePrice, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(93, 62), null, 0, false));
        stockPriceChange = new JLabel();
        stockPriceChange.setText("Gain / Loss");
        stockView.add(stockPriceChange, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(70, 50), null, 0, false));
        stockDividend = new JLabel();
        stockDividend.setText("Dividend Rate");
        stockView.add(stockDividend, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stockStockValue = new JLabel();
        stockStockValue.setText("");
        stockView.add(stockStockValue, new GridConstraints(0, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stockTickerValue = new JLabel();
        stockTickerValue.setText("");
        stockView.add(stockTickerValue, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stockCurrentPriceValue = new JLabel();
        stockCurrentPriceValue.setText("");
        stockView.add(stockCurrentPriceValue, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(71, 0), null, 0, false));
        stockDividendValue = new JLabel();
        stockDividendValue.setText("");
        stockView.add(stockDividendValue, new GridConstraints(7, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stockDescriptionValue = new JLabel();
        stockDescriptionValue.setAutoscrolls(false);
        stockDescriptionValue.setHorizontalTextPosition(10);
        stockDescriptionValue.setInheritsPopupMenu(true);
        stockDescriptionValue.setText("");
        stockDescriptionValue.setVerticalAlignment(1);
        stockDescriptionValue.setVerticalTextPosition(1);
        stockView.add(stockDescriptionValue, new GridConstraints(2, 1, 1, 3, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(300, 100), null, 0, false));
        updateCurrentPrice = new JTextField();
        stockView.add(updateCurrentPrice, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_NORTHEAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        updateCurrentPriceButton = new JButton();
        updateCurrentPriceButton.setText("Update Price");
        stockView.add(updateCurrentPriceButton, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        stockNumPurchasedValue = new JLabel();
        stockNumPurchasedValue.setText("");
        stockView.add(stockNumPurchasedValue, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(71, 0), null, 0, false));
        stockPurchasePriceValue = new JLabel();
        stockPurchasePriceValue.setText("");
        stockView.add(stockPurchasePriceValue, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(71, 0), null, 0, false));
        stockPriceChangeValue = new JLabel();
        stockPriceChangeValue.setText("");
        stockView.add(stockPriceChangeValue, new GridConstraints(6, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(71, 0), null, 0, false));
        updateNumPurchased = new JTextField();
        stockView.add(updateNumPurchased, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        updatePurchasePrice = new JTextField();
        stockView.add(updatePurchasePrice, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        updateNumPurchasedButton = new JButton();
        updateNumPurchasedButton.setText("Update Price");
        stockView.add(updateNumPurchasedButton, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        updatePurchasePriceButton = new JButton();
        updatePurchasePriceButton.setText("Update Price");
        stockView.add(updatePurchasePriceButton, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadDataButton = new JButton();
        loadDataButton.setText("Load Data");
        stockView.add(loadDataButton, new GridConstraints(9, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        stockView.add(spacer5, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Objects;

public class MainForm extends JFrame{
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
    Object[] tableData;

    public MainForm() {
        setContentPane(mainPanel);
        setTitle("Growf DMS");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


        // Loads data from file, exception in Command file brings up JOptionPane if unsuccessful
        loadDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command.importDataDB();
            }
        });

        // Sorts both the arrayList and JTable alphabetically
        sortAlphaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockList.getRowSorter().toggleSortOrder(0);
                Command.sortAlpha();
            }
        });

        // Sorts both the arrayList and JTable based on price difference
        sortPriceChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockList.getRowSorter().toggleSortOrder(5);
                Command.sortPriceChange();
            }
        });

        // Sorts both the arrayList and JTable based on number owned
        sortNumOwnedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StockList.getRowSorter().toggleSortOrder(4);
                Command.sortNumbOwned();
            }
        });

        // Runs overloaded removeStock command using value from JTextField, also gives confirmation using JLabel
        removeStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ticker = removeField.getText();

                DefaultTableModel model = (DefaultTableModel)StockList.getModel();
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (((String)model.getValueAt(i,1)).equals(ticker.toUpperCase())) {
                        model.removeRow(i);
                        removalConfirmation.setText("Stock with ticker [" + ticker.toUpperCase() + "] removed from list");
                        try {
                            Command.removeStock(ticker);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    }
                    else if ((((String)model.getValueAt(i,0)).equals(ticker))) {
                        model.removeRow(i);
                        removalConfirmation.setText("Stock with name [" + ticker + "] removed from list");
                        try {
                            Command.removeStock(ticker);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else {
                        removalConfirmation.setText("Stock ticker not found, please enter a valid stock ticker.");
                    }
                }

            }
        });

        /*
         Checks name of selected table item against arrayList and displays arraylist values in a series
         of JLabels.
         */
        StockList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                String ans = StockList.getValueAt(StockList.getSelectedRow(), 0).toString();
                for(int i = 0; i < Command.stockList.size(); i++) {
                    String stock = Command.stockList.get(i).stock;

                    if (Objects.equals(stock, ans)) {
                        stockStockValue.setText(Command.stockList.get(i).stock);
                        stockTickerValue.setText(Command.stockList.get(i).ticker);
                        stockDescriptionValue.setText("<html>"+ Command.stockList.get(i).description +"</html>");
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
        /*
         Changes value of both the viewable table and the underlying model based on JTextField Value,
         also calls overloaded version of updateStock to change the arrayList value.
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
                    model.setValueAt(price,i,2);
                    stockCurrentPriceValue.setText(String.valueOf(price));
                    updateCurrentPrice.setText("");
                } catch (java.lang.Exception l) {
                    System.err.println(l.getMessage());
                    JOptionPane.showMessageDialog(null,"Invalid input, please enter a numeric value for current stock price.");
                    updateCurrentPrice.setText("");
                }
            }
        });

        /*
         Changes value of both the viewable table and the underlying model based on JTextField Value,
         also calls overloaded version of updateStock to change the arrayList value.
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
                    model.setValueAt(price,i,4);
                    stockNumPurchasedValue.setText(String.valueOf(price));
                    updateNumPurchased.setText("");
                } catch (NumberFormatException ex) {
                    System.err.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null,"Invalid input, please enter a numeric value for current stock price.");
                    updateNumPurchased.setText("");
                }
            }
        });

        /*
         Changes value of both the viewable table and the underlying model based on JTextField Value,
         also calls overloaded version of updateStock to change the arrayList value.
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
                    model.setValueAt(price,i,3);
                    stockPurchasePriceValue.setText(String.valueOf(price));
                    updatePurchasePrice.setText("");
                } catch (NumberFormatException ex) {
                    System.err.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null,"Invalid input, please enter a numeric value for current stock price.");
                    updatePurchasePrice.setText("");
                }
            }
        });

        /*
        Uses a series of JTextFields to add a new stock object to the JTable Model. Also
        calls the overloaded addStock method using those values so the arrayList is updated.
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
                    priceChange = currentPrice-purchasePrice;
                    dividend = Float.parseFloat(addDividendRate.getText());

                    priceChange = priceChange*100;
                    priceChange = (double)((int) priceChange);
                    priceChange = priceChange /100;
                    Object[] data ={stock, ticker, currentPrice, purchasePrice, numbPurchased, priceChange};
                    model.addRow(data);
                    Command.addStock(stock,ticker,description,currentPrice,numbPurchased,purchasePrice, priceChange, dividend);

                } catch (NumberFormatException ex) {
                    System.err.println(ex.getMessage());
                    JOptionPane.showMessageDialog(null,"Invalid input, please enter a numeric value for any price or number inputs.");
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

    // Initiates the GUI
    public static void main(String[] args) {
        MainForm myForm = new MainForm();

    }

    // Custom constructor for JTable, loops through the arrayList to add values to table model.
    private void createUIComponents() {
        Command.importDataDB();
        // TODO: place custom component creation code here
        String[] col = {"Stock", "Ticker", "Current Price", "Purchase Price", "Number Purchased", "Gain / Loss"};
        DefaultTableModel tableModel = new DefaultTableModel(col, 0) {
            @Override
            public Class getColumnClass(int col) {
                switch (col){
                    case 0: return String.class;
                    case 1: return String.class;
                    case 2: return Double.class;
                    case 3: return Double.class;
                    case 4: return Double.class;
                    case 5: return Double.class;
                }
                return null;
            };
        };
        StockList = new JTable(tableModel);
        for(int i = 0; i < Command.stockList.size(); i++) {
            String stock = Command.stockList.get(i).stock;
            String ticker = Command.stockList.get(i).ticker;
            double currentPrice = Command.stockList.get(i).currentPrice;
            double purchasePrice = Command.stockList.get(i).purchasePrice;
            double numbPurchased = Command.stockList.get(i).numbPurchased;
            double priceChange = Command.stockList.get(i).priceChange;
            priceChange = priceChange*100;
            priceChange = (double)((int) priceChange);
            priceChange = priceChange /100;
            Object[] data ={stock, ticker, currentPrice, purchasePrice, numbPurchased, priceChange};
            tableModel.addRow(data);
        }
    }
}

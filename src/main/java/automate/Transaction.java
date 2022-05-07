package automate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public final class Transaction {
    private final String orderID, stock, market, transactionType, currency, method;
    private final double quantity, price, exchangeRate, fees, amount;
    private final LocalDate tradeDate;
    
    /**
     * Constructor - Creates a single Transaction object
     * 
     * @param orderID String The Transaction order ID
     * @param tradeDate String The date at which the transaction was made 
     * @param stock String The stock ticker symbol
     * @param market String The stock exchange symbol
     * @param quantity double The amount of shares sold or purchased
     * @param price double The price at which the transaction occured
     * @param transactionType String The transaction type e.g. BUY, SELL
     * @param exchangeRate double The currency exchange rate 
     * @param fees double The fees paid during this transaction
     * @param currency String The currency used for this transaction
     * @param amount double the total currency amount for this transaction
     * @param method String The transaction method used
     */
    public Transaction (String orderID, String tradeDate, String stock, String market, 
                        double quantity, double price, String transactionType, double exchangeRate, 
                        double fees, String currency, double amount, String method) {

        this.orderID = orderID; this.stock = stock; this.market = market; this.quantity = quantity;
        this.price = price; this.transactionType = transactionType; this.exchangeRate = exchangeRate;
        this.fees = fees; this.currency = currency; this.amount = amount; this.method = method;
        
        LocalDate date;
        try {
            date = LocalDate.parse(tradeDate, DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss (z)"));
        } catch (DateTimeParseException e) {
            date = LocalDate.parse(tradeDate.split(" ")[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        this.tradeDate = date;
    }

    /**
     * Read the CSV Transaction report downloaded from Sharesies. Save the 
     * information in the report by creating Transaction objects, these objects
     * will be returned in the form of a Map with the stock as the key and its
     * value being a list of transactions regarding that stock.
     * 
     * @param filePath The file path of the CSV Transaction report
     * @return Map<String, List<Transaction>>
     */
    public static Map<String, List<Transaction>> parseTransactions(String filePath) {
        Map<String, List<Transaction>> result = new HashMap<>();

        try {
            BufferedReader buffer = new BufferedReader(new FileReader(filePath));
            String line = "";
            buffer.readLine(); // skip first line
            
            while ((line = buffer.readLine()) != null) {
                String [] values = line.split(",");

                // Assign values to create Transaction objects
                String orderID = values[0];
                String tradeDate = values[1];
                String stock = values[2];
                String market = values[3];
                double quantity = Double.parseDouble(values[4]);
                double price = Double.parseDouble(values[5]);
                String transactionType = values[6];
                double exchangeRate = values[7].isEmpty() ? 0 : Double.parseDouble(values[7]);
                double fees = values[8].isEmpty() ? 0 : Double.parseDouble((values[8]));
                String currency = values[9];
                double amount = Double.parseDouble(values[10]);
                String method = values[11];

                // Create key - Ensures ticker is compatible with yahoo finance
                String key = market.equals("NZX") ? stock + ".NZ" : stock; 

                // Add Key if not present
                if (!result.keySet().contains(key)) { result.put(key, new ArrayList<Transaction>()); }

                Transaction transaction = new Transaction(orderID, tradeDate, stock, market, quantity, price, 
                transactionType, exchangeRate, fees, currency, amount, method);

                // Add transaction
                if (result.get(key) != null) { result.get(key).add(transaction); }
            }

            buffer.close();
            
            new File(filePath).delete();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /*
     * Getter methods
    */

    public String getOrderID() {
        return this.orderID;
    }

    public LocalDate getTradeDate() {
        return this.tradeDate;
    }

    public String getStock() {
        return this.stock;
    }

    public String getMarket() {
        return this.market;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public double getPrice() {
        return this.price;
    }

    public String getTransactionType() {
        return this.transactionType;
    }

    public double getExchangeRate() {
        return this.exchangeRate;
    }

    public double getFees() {
        return this.fees;
    }

    public String getCurrency() {
        return this.currency;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getTransactionMethod() {
        return this.method;
    }

    public LocalDate getDate() {
        return this.tradeDate;
    }
}

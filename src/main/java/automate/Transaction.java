package automate;

import java.time.LocalDate;


public final class Transaction {
    private final String orderID;
    private final LocalDate tradeDate;
    private final String stock;
    private final String market;
    private final double quantity;
    private final double price;
    private final String transactionType;
    private final double exchangeRate;
    private final double fees;
    private final String currency;
    private final double amount;
    private final String method;

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
        this.orderID = orderID;
        this.tradeDate = LocalDate.parse(tradeDate);
        this.stock = stock;
        this.market = market;
        this.quantity = quantity;
        this.price = price;
        this.transactionType = transactionType;
        this.exchangeRate = exchangeRate;
        this.fees = fees;
        this.currency = currency;
        this.amount = amount;
        this.method = method;
    }

}

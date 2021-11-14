package automate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.BasePage;
import sharesies.SharesiesApp;
import sharesies.SharesiesHome;
import sharesies.SharesiesLogIn;
import sharesies.SharesiesReports;
import sharesies.SharesiesSettings;
import yahoo.YahooFinance;
import yahoo.YahooHome;
import yahoo.YahooLogin;
import yahoo.YahooPortfolioData;
import yahoo.YahooPortfolios;

public class SharesiesAutomation {    
    public static void main(String[] args) {
        // Initialize base page and config properties
        BasePage testBase = new BasePage();
        // testBase.initialize("url.sharesies");

        // // Navigate to login page and Log in as user
        // SharesiesHome homePage = new SharesiesHome();
        // SharesiesLogIn loginPage = homePage.clickLoginButton();
        // SharesiesApp app = loginPage.logIn();
        
        // // Navigate to reports page
        // SharesiesSettings settingsPage = app.clickSettings();
        // SharesiesReports reportsPage = settingsPage.clickReports();
        
        // // Enter Report details
        // reportsPage.enterReportDetails("January", "October", "2019", "2021");
        
        // // Export report and wait for download
        // reportsPage.clickCSVReport();
        // reportsPage.clickExport();

        // try {
        //     Thread.sleep(3000);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        // // Close browser
        // app.clickLogOut();
        // testBase.tearDown();

        // Parse and enter transaction details into excel
        Map<String, List<Transaction>> transactions = parseTransactions(BasePage.config.getProperty("reports.csv.file"));

        testBase.initialize("url.yahoo.finance");
        
        // Log into Yahoo account
        YahooLogin yahooLogin = new YahooLogin();
        YahooHome yahooHome = yahooLogin.login();

        // Navigate to portfolio page
        YahooFinance yahooFinance = yahooHome.goToYahooFinance();
        YahooPortfolios allPortfolios = yahooFinance.goToPortfolioPage();
        
        // Navigate to Portfolio data tab with all transactions 
        YahooPortfolioData portfolioData = allPortfolios.clickPortfolio();
        portfolioData.clickHoldingsTab();

        for (String stock : transactions.keySet()) {
            System.out.println("Entering data for: " + stock);
            int row = portfolioData.getStockRow(stock);

            if (row == -1) { continue; }

            boolean clicked = portfolioData.clickDropdown(row);

            for (Transaction t : transactions.get(stock)) {
                if (clicked) { portfolioData.addLot(row); }
                portfolioData.enterTransaction(row, t.getDate(), t.getQuantity(), t.getPrice(), t.getOrderID());  
                clicked = true; 
            }
            
            portfolioData.clickDropdown(row);

        }

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
    private static Map<String, List<Transaction>> parseTransactions(String filePath) {
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
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}

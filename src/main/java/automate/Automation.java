package automate;

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

public class Automation {    
    public static void main(String[] args) {
        // Initialize base page and config properties
        BasePage testBase = new BasePage();

        try {
            testBase.initialize("url.sharesies");

            // Navigate to login page and Log in as user
            SharesiesHome homePage = new SharesiesHome();
            SharesiesLogIn loginPage = homePage.clickLoginButton();
            SharesiesApp app = loginPage.logIn();
            
            // Navigate to reports page
            SharesiesSettings settingsPage = app.clickSettings();
            SharesiesReports reportsPage = settingsPage.clickReports();
            
            // Download transaction report for given period
            reportsPage.downloadCSVReport("January", "October", "2019", "2022");

            // Close browser
            app.clickLogOut();
            testBase.tearDown();

            // Parse and enter transaction details into excel
            String filePath = System.getProperty("user.dir") + "/" +BasePage.config.getProperty("reports.csv.file");
            Map<String, List<Transaction>> transactions = Transaction.parseTransactions(filePath);

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

            // Data entry loop
            for (String stock : transactions.keySet()) {
                System.out.println("Entering data for: " + stock);
                int row = portfolioData.getStockRow(stock);

                if (row == -1) { 
                    System.out.println(stock + " Not found");
                    continue; 
                }

                boolean clicked = portfolioData.clickDropdown(row);

                for (Transaction t : transactions.get(stock)) {
                    if (clicked) { portfolioData.addLot(row); }
                    portfolioData.enterTransaction(row, t.getDate(), t.getQuantity(), t.getPrice(), t.getOrderID());  
                    clicked = true; 
                }
                
                portfolioData.clickDropdown(row);
            }

            // Sign out of Yahoo
            yahooFinance.signOut();
        } catch (Exception e) {
            testBase.takeScreenshot();
            throw e;
        }
    }
}
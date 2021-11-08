package yahoo;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;


public class YahooPortfolios extends BasePage {

    private final String portfolioName = config.getProperty("yahoo.portfolio.name");

    /**
     * Constructor
     */
    public YahooPortfolios() {
        if (!driver.getTitle().equals("Stock portfolio & tracker – Yahoo Finance")) {
            throw new IllegalStateException("This is not the Yahoo Portfolios Page, current Page is " 
            + driver.getCurrentUrl()); 
        }
    }

    /**
     * Clicks the Portfolio specifed in the config file.
     * Returns a page containing the portfolio data
     * @return YahooPortfolioData
     */
    public YahooPortfolioData clickPortfolio() {
        driver.findElement(By.xpath("//a[text()='" + portfolioName +  "']")).click();
        new WebDriverWait(driver, 8).until(ExpectedConditions.titleIs(portfolioName + " - Stock portfolio & tracker – Yahoo Finance"));
        return new YahooPortfolioData();
    }
    
}

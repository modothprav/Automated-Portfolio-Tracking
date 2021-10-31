package yahoo;

import org.openqa.selenium.By;

import base.BasePage;


public class YahooPortfolios extends BasePage {

    private final String name = config.getProperty("yahoo.portfolio.name");

    public YahooPortfolios() {
        if (!driver.getTitle().equals("Stock portfolio & tracker – Yahoo Finance")) {
            throw new IllegalStateException("This is not the Yahoo Portfolios Page, current Page is " 
            + driver.getCurrentUrl()); 
        }
    }

    public void clickPortfolio() {
        driver.findElement(By.xpath("//a[text()='" + name +  "']")).click();
    }
    
}

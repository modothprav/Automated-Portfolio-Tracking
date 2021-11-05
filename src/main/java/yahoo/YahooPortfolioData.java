package yahoo;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BasePage;

public class YahooPortfolioData extends BasePage {

    // Object Repository
    @FindBy(xpath = "//span[text()='My holdings']")
    private WebElement holdingsTab;

    // @FindAll({@FindBy(xpath = "//table/tbody[*]/tr[2]/td/button")})
    // private List<WebElement> dropdownButtons;
    
    // @FindAll({@FindBy(xpath = "//table/tbody[*]/tr[2]/td[2]/div/a")})
    // private List<WebElement> stockTickers;


    /**
     * Constructor
     */
    public YahooPortfolioData() {
        PageFactory.initElements(driver, this);
    }

    public void clickHoldingsTab() {
        this.holdingsTab.click();
    }

    public void clickDropdown(int stockRow) {
        WebElement button = driver.findElement(By.xpath("//table/tbody["+ stockRow +"]/tr[2]/td/button"));
        button.click();
    }

    public List<WebElement> getStockHoldings() {
        return driver.findElements(By.xpath("//table/tbody/*"));
    }

    public void addLot(int stockRow) {

        By transactions = By.xpath("//table/tbody["+ stockRow +"]/tr[3]/td/table/tbody/*");
        By buttonPath = By.xpath("//table/tbody["+ stockRow +"]/tr[3]/td/table/tbody/tr[last()]/td/button");

        int count = driver.findElements(transactions).size();
        WebElement addLotButton = driver.findElement(buttonPath);
        addLotButton.click();
        
        new WebDriverWait(driver, 8).until(ExpectedConditions.numberOfElementsToBe(transactions, count + 1));
    }

    private void enterDate(int stockRow, String date) {
        WebElement dateInput = driver.findElement(By.xpath("//table/tbody[" + stockRow + "]/tr[3]/td/table/tbody/tr[last()-1]/td[1]/input[@type='date']"));
        dateInput.sendKeys(date);
    }

    public void enterTransaction(int stockRow, String date) {
        this.enterDate(stockRow, date);
    }



}
